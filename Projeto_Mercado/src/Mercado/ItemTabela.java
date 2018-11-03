/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Mercado;

/**
 *
 * @author Leo
 */
public class ItemTabela {
    private String descricao;
    private int qtd_venda;
    private float valor_unitario;
    private float valor_Total;
    private String bonus;

    public String getBonus() {
        return bonus;
    }

    public void setBonus(String bonus) {
        this.bonus = bonus;
    }

    

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getQtd_venda() {
        return qtd_venda;
    }

    public void setQtd_venda(int qtd_venda) {
        this.qtd_venda = qtd_venda;
    }

    public float getValor_unitario() {
        return valor_unitario;
    }

    public void setValor_unitario(float valor_unitario) {
        this.valor_unitario = valor_unitario;
    }

    public float getValor_Total() {
        return valor_Total;
    }

    public void setValor_Total(float valor_Total) {
        this.valor_Total = valor_Total;
    }
    
    
}
