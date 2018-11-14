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
import javax.swing.JOptionPane;
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

    public TelaVendaDAO(String login, String senha) {
        setLogin(login);
        setSenha(senha);
        this.fabr = new FabricaConexao(getLogin().toString(), getSenha());
    }

        /*APRESENTA A DESCRIÇÃO DO PRODUTO SELECIONADO*/
    public String descreveProd(String produto) {

        
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
            
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(TelaVendaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return desc;
    }
    
    /*APRESENTA A TABELA DE VENDAS DE ACORDO COM O USUARIO SELECIONADO*/
    public DefaultTableModel attTabelaVenda(JTable Table, String Cliente)  {

        Connection conn;
        DefaultTableModel modelo = null;
        try {
            conn = fabr.conexao();
            Statement stmt = conn.createStatement();

            ResultSet rs = stmt.executeQuery("select * from venda where CodCli in"
                    + "(select CodCli from Cliente where nome = '" + Cliente + "' );");

            Table.setAutoResizeMode(Table.AUTO_RESIZE_ALL_COLUMNS);
            modelo = (DefaultTableModel) Table.getModel();
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
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(TelaVendaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        

        return modelo;
    }

    /*TRANSAÇÃO REFERENTE A VENDA DE PRODUTOS AO CLIENTE*/
    public void venderProd(Venda vd) {

        Connection con;
        Statement stmt;
        int localFab;
        int bonus;

        try {
            con = fabr.conexao();
            stmt = con.createStatement();
            ResultSet rs;

            try {
                con.setAutoCommit(false);
                
                /*Verificando chaves primarias*/
                
                rs=stmt.executeQuery("select CodCli,CodProd,CodLocal from venda"
                        + " where CodCli="+vd.getCodCli());
                
                while(rs.next()){
                    if(rs.getInt("CodProd") == vd.getCodProd() &&    
                            rs.getInt("CodLocal") == vd.getCodLocal())
                        throw new Exception("Ja existe uma compra desse produto "
                                + "nesta localidade");
                }
                
                
                /*UPDATE NA TABELA PRODUTO*/
                rs=stmt.executeQuery("select qtd_estoque from produto "
                        + "where CodProd="+vd.getCodProd()+";");
                rs.next();
                if(rs.getInt(1)< vd.getQtd_venda()){
                    throw  new Exception("Quantidade de venda excede ao estoque");
                }
                
                stmt.executeUpdate("update produto set qtd_estoque = qtd_estoque - " + vd.getQtd_venda()
                        + " where CodProd =" + vd.getCodProd() + ";");

                /*CALCULANDO DESCONTO (LOCAL)*/
                rs = stmt.executeQuery("select Codlocal from produto where CodProd =" + vd.getCodProd());
                rs.next();
                localFab = rs.getInt(1);

                if (localFab == vd.getCodLocal()) {
                    float valorIni = vd.getValor_total();
                    vd.setValor_total((float) (vd.getValor_total() - (vd.getValor_total() * 0.10)));
                    float valorDes = vd.getValor_total(); 
                    JOptionPane.showMessageDialog(null,"Valor Inicial: "+valorIni
                            +" Desconto local: "+valorDes);
                } else {
                    System.out.println("Produto nao fab no local de compra");
                }

                /*CALCULANDO DESCONTO BONUS*/
                rs = stmt.executeQuery("select bonus from cliente where CodCli =" + vd.getCodCli() + ";");
                rs.next();
                bonus = rs.getInt(1);

                if (bonus >= 100) {
                    
                    rs = stmt.executeQuery("select qtd_min,qtd_max,percentual from desconto"
                            + " where CodProd =" + vd.getCodProd());
                    
                    rs.next();                   
                        
                    if(rs.getInt("qtd_min") > vd.getQtd_venda()){

                        JOptionPane.showMessageDialog(null,"Você nao comprou"
                                + " quantidade suficiente para ganhar "
                                + "desconto (quantidade:" +vd.getQtd_venda()
                                +")","quantidade insuficiente",
                                JOptionPane.ERROR_MESSAGE);

                    }else{
                        do{

                            if (rs.getInt("qtd_min") <= vd.getQtd_venda()
                                    && rs.getInt("qtd_max") >= vd.getQtd_venda()) {


                                System.out.print("valor Original:" + vd.getValor_total());

                                float desconto = (float) vd.getValor_total() * (rs.getInt(3) / 100f);
                                vd.setValor_total((float) vd.getValor_total() - desconto);

                                System.out.println("//valor com desconto bonus:"
                                        + vd.getValor_total());

                                /*UPDATE NA TABELA CLIENTE*/
                                stmt.executeUpdate("update cliente set bonus ="
                                        + "bonus-100 where CodCli =" + vd.getCodCli() + ";");
                                break;
                            }

                        }while(rs.next());
                    }
                } else {
                    JOptionPane.showMessageDialog(null,"Você nao tem bonus "
                            + "suficiente para desconto","Bonus insuficiente",
                            JOptionPane.ERROR_MESSAGE);
                }

                /*INSERINDO NA TABELA VENDA*/
                stmt.executeUpdate("insert into venda values ("
                        + vd.getCodCli() + "," + vd.getCodProd() + ","
                        + vd.getCodLocal() + "," + vd.getQtd_venda() + ","
                        + vd.getValor_total() + ",'" + vd.getData_venda()+"');");

                con.commit();
                System.out.println("commit");
                JOptionPane.showMessageDialog(null,"Venda realizada com sucesso");
            } catch (SQLException ex) {
                con.rollback();
                System.out.println("rollback");
                Logger.getLogger(TelaVendaDAO.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                con.rollback();
                System.out.println("rollback");
                JOptionPane.showMessageDialog(null,ex,"ERROR",JOptionPane.ERROR_MESSAGE);
            } finally {
                con.setAutoCommit(true);
            }

        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(TelaVendaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void excluirVenda(String nomeCli,String nomeProd,int CodLocal) {

        Connection con;
        Statement stmt;
        

        try {
            con = fabr.conexao();
            stmt = con.createStatement();
            ResultSet rs;
            
            int CodCli;
            int CodProd;            
            
            rs=stmt.executeQuery("select CodCli from Cliente where nome='"+nomeCli+"';");
            rs.next();
            CodCli=rs.getInt("CodCli");
            rs=stmt.executeQuery("select CodProd from Produto where descricao='"+nomeProd+"';");
            rs.next();
            CodProd=rs.getInt("CodProd");            
            
            try {
                con.setAutoCommit(false);

                /* TRATAR ESTOQUE */
                rs=stmt.executeQuery("select qtd_venda from venda"
                        + " where CodCli ="+CodCli
                        +" and CodProd ="+CodProd
                        +" and CodLocal ="+CodLocal+";");
                rs.next();
                
                int qtd = rs.getInt(1);
                
                stmt.executeUpdate("update produto set qtd_estoque ="
                        + " qtd_estoque+"+qtd+" where CodProd ="+CodProd+";");        
                
                
                /* DEVOLVER BONUS DO CLIENTE */                      
                
                
                rs=stmt.executeQuery("select qtd_venda,valor_total "
                        + "from venda where CodCli ="+CodCli
                        +" and CodProd ="+CodProd
                        +" and CodLocal ="+CodLocal+";");
                
                rs.next();
                float vdValorTotal = rs.getFloat("valor_total");
                int qtdVenda =rs.getInt("qtd_venda");
                
                rs= stmt.executeQuery("select preco_unitario from produto "
                        + "where CodProd ="+CodProd);
                rs.next();
                float valorCal = qtd*rs.getFloat("preco_unitario");
                
                rs = stmt.executeQuery("select Codlocal from produto where CodProd =" +CodProd);
                rs.next();
                int localFabProd = rs.getInt(1);
                
                if(localFabProd == CodLocal)
                    valorCal -= valorCal * 0.10;
                
                if(valorCal > vdValorTotal){
                    stmt.executeUpdate("update Cliente set bonus=bonus + 100"
                            + " where CodCli="+CodCli+";" ); 
                    JOptionPane.showMessageDialog(null,"100 pontos Bonus "
                            + "devolvido ao Cliente");
                }else{
                   JOptionPane.showMessageDialog(null,"Nenhum bonus devolvido ao"
                           + " Cliente","Devolução",JOptionPane.ERROR_MESSAGE); 
                }
                
                /* DELETAR A VENDA */
                
                stmt.executeUpdate("delete from venda where "
                        + "CodCli ="+CodCli
                        +" and CodProd ="+CodProd
                        +" and CodLocal ="+CodLocal+";");          
                
                
                con.commit();
                System.out.println("commit");
                JOptionPane.showMessageDialog(null,"Venda Excluida com sucesso.");
            } catch (SQLException ex) {
                con.rollback();
                System.out.println("rollback");
                Logger.getLogger(TelaVendaDAO.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
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
