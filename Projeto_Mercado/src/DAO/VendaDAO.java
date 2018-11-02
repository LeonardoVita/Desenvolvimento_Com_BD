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
                    + "v.qtd_venda, p.preco_unitario, v.valor_total from venda as v \n" +
                    "inner join produto as p on p.CodProd = v.CodProd\n" +
                    "where CodCli in (select CodCli from Cliente where nome = '"+Cliente+"');");
            ResultSet rs = stmt.executeQuery();    
            
            while(rs.next()){
                ItemTabela iTable = new ItemTabela();
                
                iTable.setDescricao(rs.getString(1));
                iTable.setQtd_venda(rs.getInt(2));
                iTable.setValor_unitario(3);
                iTable.setValor_Total(rs.getFloat(4));             
                
                lista.add(iTable);
            } 
            
            con.close();
            System.out.println("Conexão fechada //listar vendas");
            
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(VendaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
      return lista;
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
