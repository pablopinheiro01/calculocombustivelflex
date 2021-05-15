package br.com.calculadora.calculocombustivel.business

import android.util.Log
import br.com.calculadora.calculocombustivel.exception.GenericException
import br.com.calculadora.calculocombustivel.model.Reabastecimento
import java.text.DecimalFormat
import java.text.NumberFormat


class CalculoCombustivel() {

    fun calculaKmPorLitro(item: Reabastecimento) : Reabastecimento{

        try{
            Log.i("ITEM-CALCULO","ITEM KM RODADO ${item.kmRodados.toString()}")
            Log.i("ITEM-CALCULO","ITEM LITROS ${item.litros.toString()}")
            var kmPorLitro : Double = item.kmRodados / item.litros
            Log.i("ITEM-CALCULO","CALCULADO ESSE EO VALOR: ${kmPorLitro}")
            Log.i("ITEM-CALCULO","TENTANDO FORMATAR ...")
            val formatador = String.format("%.2f", kmPorLitro).replace(",",".")
            Log.i("ITEM-FORMATADO", "Formatacao do item: ${formatador}")

            item.consumoKilometrosPorLitro = formatador.toDouble()
        }catch(e: Exception){
            Log.e("ITEM-ERRO","Erro: ${e.toString()}")
            throw GenericException()
        }
        return item
    }

}