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
    private String login ;
    private String senha ;
    
    public FabricaConexao(String login,String senha){       
        setLogin(login);
        setSenha(senha);
        
        
    }

    public Connection conexao() throws SQLException, ClassNotFoundException {

        Class.forName("com.mysql.jdbc.Driver");
        
        System.out.println("Abrindo conexão // login: "+getLogin()+" Senha: "+getSenha());
        
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

    private String getSenha() {
        return senha;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
    
    

}
