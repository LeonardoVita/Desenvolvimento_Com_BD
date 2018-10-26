/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Leo
 */
public class TelaVendaDAO {

    FabricaConexao fabr= new FabricaConexao();;
    
    
    public boolean verificaQTD(int tabela,int pedido) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public String descreveProd(String produto) throws SQLException, ClassNotFoundException {
        
        /* realizando operaçoes ao BD*/
        String desc;       
        
        Connection conn = fabr.conexao();   
        Statement stmt = conn.createStatement();
        
        ResultSet rs = stmt.executeQuery("select descricao,qtd_estoque,"
                + "preco_unitario from produto where descricao ='"+produto+"';");
        
        /*Montando String*/
        rs.next();        
        
        desc = rs.getString(1)+ " - R$ "+rs.getString(2)+
                 " - Quantidade("+rs.getString(3)+")";      
        return desc;
    }

    public float attTotal(float total,String produto, int qtd) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    /* VENDA DO PRODUTO */
    
    /* calcular desconto*/
    /* calcular preço qtd*unid */    
    /* inseri na tabela venda */   
    /*atualiza tabela produto*/
    
    /*atualiza o valor TOTAL*/
    
    
}
