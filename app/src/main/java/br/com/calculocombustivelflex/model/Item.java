package br.com.calculocombustivelflex.model;

import java.math.BigDecimal;
import java.util.Calendar;

/**
 * Created by Pablo on 18/01/2018.
 */

public class Item {

    private BigDecimal valorLitro;
    private Calendar data;
    private BigDecimal valorReabastecimento;
    private Double litros;
    private Double quilometrosRodados;
    private Double quilometroInicial;
    private Double quilometroParadaParaAbastecer;
    private Combustivel combustivel;


    public Item(BigDecimal valorReabastecimento, Double litros, Double quilometrosRodados, Combustivel combustivel){
        this.valorReabastecimento = valorReabastecimento;
        this.litros = litros;
        this.quilometrosRodados = quilometrosRodados;
        this.combustivel = combustivel;

    }

    public Item(BigDecimal valorReabastecimento, BigDecimal valorLitro, Double litros, Double quilometrosRodados){
        this.valorReabastecimento = valorReabastecimento;
        this.valorLitro = valorLitro;
        this.litros = litros;
        this.quilometrosRodados = quilometrosRodados;
    }

    public Item(BigDecimal valorReabastecimento, Double litros, Double quilometroInicial, Double quilometroParadaParaAbastecer){

    }

    public void insereDataAtual(){
        this.data = Calendar.getInstance();
    }

    public Calendar getData(){
        return this.data;
    }

}
