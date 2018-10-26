/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;


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
       
       assertEquals("Short - R$ 30.99 - Quantidade(60)",
               str = venda.descreveProd("Short"));
       assertEquals("Boné - R$ 15 - Quantidade(80)",
               str = venda.descreveProd("Boné"));
        
    }
    
    @Test
    public void attTotal(){
        
        float pr;
        assertEquals(309.9,pr = venda.attTotal(0,"Short",10),0.09f);
        assertEquals(409.4,pr = venda.attTotal(0,"Teclado",5),0.09f);
        assertEquals(99.5,pr = venda.attTotal(0,"Short",-10),0.09f);
    }
            
            
   
    
    
    
    
}
