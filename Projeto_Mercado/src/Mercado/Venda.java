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
    private String data_venda;
    private String bonus;

    
    public Venda(){
        
        //System.out.println(this.data_venda);  
    }

    public String getBonus() {
        return bonus;
    }

    public void setBonus(String bonus) {
        this.bonus = bonus;
    }
    
    public int getCodCli() {
        return CodCli;
    }

    public void setCodCli(int CodCli) {
        this.CodCli = CodCli;
    }

    public int getCodProd() {
        return CodProd;
    }

    public void setCodProd(int CodProd) {
        this.CodProd = CodProd;
    }

    public int getCodLocal() {
        return CodLocal;
    }

    public void setCodLocal(int CodLocal) {
        this.CodLocal = CodLocal;
    }

    public int getQtd_venda() {
        return qtd_venda;
    }

    public void setQtd_venda(int qtd_venda) {
        this.qtd_venda = qtd_venda;
    }

    public float getValor_total() {
        return valor_total;
    }

    public void setValor_total(float valor_total) {
        this.valor_total = valor_total;
    }

    public String getData_venda() {
        return data_venda;
    }

    public void setData_venda(String data_venda) {
        this.data_venda = data_venda;
    }
    
    
    
       
}
