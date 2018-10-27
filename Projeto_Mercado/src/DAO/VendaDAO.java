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
    
    FabricaConexao fabr = new FabricaConexao();
    
    
    public VendaDAO(){
        
    }
    
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
            
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(VendaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
      return lista;
    }
}
