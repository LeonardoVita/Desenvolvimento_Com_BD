/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Mercado.Venda;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Leo
 */
public class TelaVendaDAO {
    
    private String login;
    private String senha;
    FabricaConexao fabr;

    public TelaVendaDAO(String login,String senha){        
       setLogin(login);
       setSenha(senha); 
       this.fabr = new FabricaConexao(getLogin().toString(),getSenha());
    }   

    public String descreveProd(String produto)  {

        /* realizando operaçoes ao BD*/
        String desc = null;

        Connection conn;
        try {
            conn = fabr.conexao();
            Statement stmt = conn.createStatement();

            ResultSet rs = stmt.executeQuery("select descricao,preco_unitario ,"
                + "qtd_estoque from produto where descricao ='" + produto + "';");

            /*Montando String*/
            rs.next();

            desc = rs.getString(1) + " - R$ " + rs.getString(2)
                    + " - Quantidade(" + rs.getString(3) + ")";
            conn.close();
            System.out.println("conexão fechada // descrição de produtos");
        } catch (SQLException ex) {
            Logger.getLogger(TelaVendaDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(TelaVendaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return desc;
    }   

    /* VENDA DO PRODUTO */
    /* calcular desconto1*/
    /* calcular desconto2*/
    /* calcular preço qtd*unid */
    
    
    
    public DefaultTableModel attTabelaVenda(JTable Table, String Cliente) throws SQLException, ClassNotFoundException {

        Connection conn = fabr.conexao();
        Statement stmt = conn.createStatement();

        ResultSet rs = stmt.executeQuery("select * from venda where CodCli in"
                + "(select CodCli from Cliente where nome = '" + Cliente + "' );");

        Table.setAutoResizeMode(Table.AUTO_RESIZE_ALL_COLUMNS);
        DefaultTableModel modelo = (DefaultTableModel) Table.getModel();
        modelo.setNumRows(0);

        while (rs.next()) {
            modelo.addRow(new Object[]{
                rs.getString(1),
                rs.getString(2),
                rs.getString(3),
                rs.getString(4),
                rs.getString(5),
                rs.getString(6)
            });
        }
        
        return  modelo;      
    }
    
    
    public void venderProd(Venda vd){
            
        
        Connection con;
        Statement stmt;
        int localFab;
        int bonus;
            
            
        try {
            con = fabr.conexao();
            stmt = con.createStatement();
            ResultSet rs;
            
            try{
               con.setAutoCommit(false);
               
               /*UPDATE NA TABELA PRODUTO*/
                stmt.executeUpdate("update produto set qtd_estoque = qtd_estoque - "+vd.getQtd_venda()
                       +" where CodProd ="+vd.getCodProd()+";");
                          
               
               /*CALCULANDO DESCONTO (LOCAL)*/
               rs=stmt.executeQuery("select Codlocal from produto where CodProd ="+vd.getCodProd());
               rs.next();
               localFab = rs.getInt(1);
               
               if(localFab == vd.getCodLocal()){
                   System.out.print("valor original: "+vd.getValor_total()+" ");
                   vd.setValor_total((float) (vd.getValor_total()-(vd.getValor_total()*0.10)));
                   System.out.println("Valor com desconto Local: "+vd.getValor_total());      
               }else
                    System.out.println("Produto nao fab no local de compra");
               
               /*CALCULANDO DESCONTO BONUS*/
               
               rs=stmt.executeQuery("select bonus from cliente where CodCli ="+vd.getCodCli()+";");
               rs.next();
               bonus = rs.getInt(1);
               
               if(bonus >= 100){
                   rs=stmt.executeQuery("select qtd_min,qtd_max,percentual from desconto"
                           + " where CodProd ="+vd.getCodProd());
                   
                   while(rs.next()){                         
                        
                        if(rs.getInt("qtd_min") <= vd.getQtd_venda() && 
                               rs.getInt("qtd_max") >= vd.getQtd_venda()){
                            
                            vd.setBonus("S");
                            System.out.println("valor total:"+vd.getValor_total());                          
                            
                            float desconto= (float) vd.getValor_total()*(rs.getInt(3)/100f);                     
                            vd.setValor_total((float)vd.getValor_total()-desconto);
                            
                            System.out.println("valor total com desconto bonus:"
                                    +vd.getValor_total());
                            
                            stmt.executeUpdate("update cliente set bonus ="
                            +"bonus-100 where CodCli ="+vd.getCodCli()+";");                            
                            break;
                        }
                        
                   }
                   
               }
                  
               
               /*INSERINDO NA TABELA VENDA*/
                stmt.executeUpdate("insert into venda values (default,"+
                       vd.getCodCli()+","+vd.getCodProd()+","+
                       vd.getCodLocal()+","+vd.getQtd_venda()+","+
                       vd.getValor_total()+",'"+vd.getData_venda()+"','"+
                       vd.getBonus()+"');");   
               
               con.commit();
                System.out.println("commit");
            }catch (SQLException  ex){                
               con.rollback();
               System.out.println("rollback");                             
               Logger.getLogger(TelaVendaDAO.class.getName()).log(Level.SEVERE, null, ex); 
            }finally{
                con.setAutoCommit(true);
            }       
            
        } catch (SQLException | ClassNotFoundException ex) { 
            Logger.getLogger(TelaVendaDAO.class.getName()).log(Level.SEVERE, null, ex);            
        }  
    }
    
    

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
    
    
}
