/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;


import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Leo
 */
public class TelaVendaDAOTest {
    
    private final DAO.TelaVendaDAO venda = new TelaVendaDAO();
    
    public TelaVendaDAOTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void verificaQTD() {    //Retorna verdadeiro caso tenha estoque >= pedido   
        
      boolean bol;
      
      assertTrue(bol = venda.verificaQTD(50,15));
      assertFalse(bol = venda.verificaQTD(15,50));
      assertTrue(bol = venda.verificaQTD(50,50));      
        
    }
    
    @Test
    public void descreveProd(){
       String str ;
       
        try {
            
            assertEquals("Short - R$ 30.99 - Quantidade(60)",
                    str = venda.descreveProd("Short"));
            assertEquals("Boné - R$ 15 - Quantidade(80)",
               str = venda.descreveProd("Boné"));
        
        } catch (SQLException ex) {
            Logger.getLogger(TelaVendaDAOTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(TelaVendaDAOTest.class.getName()).log(Level.SEVERE, null, ex);
        }
       
        
    }
    
    
            
    
   
    
    
    
    
}
