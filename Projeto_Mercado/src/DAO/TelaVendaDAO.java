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
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Leo
 */
public class TelaVendaDAO {

    FabricaConexao fabr = new FabricaConexao();

    ;
    
    
    public boolean verificaQTD(int tabela, int pedido) {

        boolean bol = (tabela >= pedido) ? true : false;
        return bol;

    }

    public String descreveProd(String produto) throws SQLException, ClassNotFoundException {

        /* realizando operaçoes ao BD*/
        String desc;

        Connection conn = fabr.conexao();
        Statement stmt = conn.createStatement();

        ResultSet rs = stmt.executeQuery("select descricao,preco_unitario ,"
                + "qtd_estoque from produto where descricao ='" + produto + "';");

        /*Montando String*/
        rs.next();

        desc = rs.getString(1) + " - R$ " + rs.getString(2)
                + " - Quantidade(" + rs.getString(3) + ")";
        conn.close();
        return desc;
    }   

    /* VENDA DO PRODUTO */
    /* calcular desconto1*/
    /* calcular desconto2*/
    /* calcular preço qtd*unid */
    /* inseri na tabela venda */
    /*atualiza tabela produto*/
    
    
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

}
