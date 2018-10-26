/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import com.mysql.jdbc.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Leo
 */
public class FabricaConexao {

    private String servidor = "jdbc:mysql://localhost:3306/";
    private String banco = "aps_mercado";
    private String login = "root";
    private String senha = null;

    public Connection conexao() throws SQLException, ClassNotFoundException {

        Class.forName("com.mysql.jdbc.Driver");
        
        return (Connection) DriverManager.getConnection(getServidor()+getBanco()
                ,getLogin(),getSenha());
        
        
    }

    public String getServidor() {
        return servidor;
    }

    public String getBanco() {
        return banco;
    }

    public String getLogin() {
        return login;
    }

    public String getSenha() {
        return senha;
    }

}
