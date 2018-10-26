/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Mercado;

import java.sql.Date;

/**
 *
 * @author Leo
 */
public class Venda {
    
    private int CodCli;
    private int CodProd;
    private int CodLocal;
    private int qtd_venda;
    private float valor_total;
    private Date data_venda;
    
    Venda(){
        System.out.println(this.data_venda);  
    }
    
       
}
