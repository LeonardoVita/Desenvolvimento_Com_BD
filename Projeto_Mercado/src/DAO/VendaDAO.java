/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Mercado.ItemTabela;
import Mercado.Venda;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Leo
 */
public class VendaDAO {
    
    private String login;
    private String senha;    
    FabricaConexao fabr;
    
    
    public VendaDAO(String login,String senha){        
       setLogin(login);
       setSenha(senha);
       this.fabr = new FabricaConexao(getLogin(),getSenha());
    }
    
    /*RETORNA UMA LISTA PARA A TABELA VENDA*/
    public List<ItemTabela> listarVenda(String Cliente){
        
        List<ItemTabela> lista = new ArrayList<>();       
        
        try {
            
            Connection con = fabr.conexao();
            PreparedStatement stmt = con.prepareStatement("select p.descricao, "
                    + "v.qtd_venda, p.preco_unitario, v.valor_total,v.CodLocal from venda as v \n" +
                    "inner join produto as p on p.CodProd = v.CodProd\n" +
                    "where CodCli in (select CodCli from Cliente where nome = '"+Cliente+"');");
            ResultSet rs = stmt.executeQuery();    
            
            while(rs.next()){
                ItemTabela iTable = new ItemTabela();
                
                iTable.setDescricao(rs.getString(1));
                iTable.setQtd_venda(rs.getInt(2));
                iTable.setValor_unitario(rs.getFloat(3));
                iTable.setValor_Total(rs.getFloat(4));               
                iTable.setCodLocal(rs.getInt(5));
                lista.add(iTable);
            } 
            
            con.close();
            System.out.println("Conexão fechada //listar vendas");
            
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(VendaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
      return lista;
    }
    
    /*MONTANDO UM OBJETO DO TIPO VENDA*/
    public Venda getVenda(String cli,String local,String prod, int qtd){
     
        Venda vd = new Venda(); 
        Connection con;            
            
        try {
            
            
            con = fabr.conexao();
            Statement stmt = con.createStatement();
            ResultSet rs;
            
            rs=stmt.executeQuery("select CodCli from Cliente where nome ='"+cli+"';");
            rs.next();
            vd.setCodCli(rs.getInt(1));
            System.out.println("vd cliente "+vd.getCodCli());
            rs=stmt.executeQuery("select CodLocal from localidade where nome ='"+local+"';");
            rs.next();
            vd.setCodLocal(rs.getInt(1));
            rs=stmt.executeQuery("select CodProd from produto where descricao ='"+prod+"';");
            rs.next();
            vd.setCodProd(rs.getInt(1));
            
            vd.setQtd_venda(qtd);
            vd.setData_venda("2018-10-06"); 
            
            rs=stmt.executeQuery("select preco_unitario from produto where descricao ='"+prod+"';");
            rs.next();
            vd.setValor_total(qtd*rs.getFloat(1)); 
            
            con.close();
            System.out.println("Contruindo objeto Venda // conexão fechada");
            
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(VendaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }      
        System.out.println("IMPRIMINDO OBJETO VENDA");
        System.out.println("CodCli: "+vd.getCodCli());
        System.out.println("CodProd: "+vd.getCodProd());
        System.out.println("CodLocal: "+vd.getCodLocal());
        System.out.println("Quantidade: "+vd.getQtd_venda());
        System.out.println("Data: "+vd.getData_venda());
        System.out.println("Valor Total: "+vd.getValor_total());
        return vd;
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
