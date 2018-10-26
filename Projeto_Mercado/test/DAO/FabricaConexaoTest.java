/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
public class FabricaConexaoTest {
    private final FabricaConexao instance = new FabricaConexao(); ;
    private final Connection conn;
    
    public FabricaConexaoTest() throws SQLException, ClassNotFoundException {
        this.conn = instance.conexao();
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

    /**
     * Test of conexao method, of class FabricaConexao.
     */
    @Test
    public void testConexao() throws Exception {
        System.out.println("conexao");
        
        Statement stmt = (Statement) conn.createStatement();    
                
        ResultSet result = stmt.executeQuery("show tables");
        
        assertEquals(true,result.next());        
    }

    /**
     * Test of getServidor method, of class FabricaConexao.
     */
    @Test
    public void testGetServidor() {
        System.out.println("getServidor");
        FabricaConexao instance = new FabricaConexao();
        String expResult = "jdbc:mysql://localhost:3306/";
        String result = instance.getServidor();
        assertEquals(expResult, result);
        
    }

    /**
     * Test of getBanco method, of class FabricaConexao.
     */
    @Test
    public void testGetBanco() {
        System.out.println("getBanco");
        FabricaConexao instance = new FabricaConexao();
        String expResult = "aps_mercado";
        String result = instance.getBanco();
        assertEquals(expResult, result);
        
    }

    /**
     * Test of getLogin method, of class FabricaConexao.
     */
    @Test
    public void testGetLogin() {
        System.out.println("getLogin");
        FabricaConexao instance = new FabricaConexao();
        String expResult = "root";
        String result = instance.getLogin();
        assertEquals(expResult, result);        
    }   
    
}
