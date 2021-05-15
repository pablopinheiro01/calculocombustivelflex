package br.com.calculadora.calculocombustivel.business

import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import br.com.calculadora.calculocombustivel.model.Reabastecimento
import br.com.calculadora.calculocombustivel.model.ResumoGastosData
import br.com.calculadora.calculocombustivel.model.ResumoGastosDataItem
import br.com.calculadora.calculocombustivel.ui.viewmodel.ResumoViewModel
import com.google.gson.Gson
import com.yabu.livechart.model.DataPoint
import com.yabu.livechart.model.Dataset
import java.math.BigDecimal
import java.util.*
import kotlin.collections.ArrayList

class ResumoGastosBusiness(){


        fun preparaDados(): Dataset {

//
//            for( todos in buscaTodosOsMesesCadastrados){
//
//                Log.i("VIEW-MESES","${todos}")
//            }

            val resumoGastosDataList = Arrays.asList(
                ResumoGastosDataItem(BigDecimal(250.0), "2020-01-21"),
                ResumoGastosDataItem(BigDecimal(350.0), "2020-03-22"),
                ResumoGastosDataItem(BigDecimal(150.0), "2020-01-23"),
                ResumoGastosDataItem(BigDecimal(140.0), "2020-01-24"),
                ResumoGastosDataItem(BigDecimal(140.0), "2020-01-21"),
                ResumoGastosDataItem(BigDecimal(490.0), "2020-03-22"),
                ResumoGastosDataItem(BigDecimal(150.0), "2020-01-23"),
                ResumoGastosDataItem(BigDecimal(50.0), "2020-01-24"),
                ResumoGastosDataItem(BigDecimal(20.0), "2020-01-21"),
                ResumoGastosDataItem(BigDecimal(30.0), "2020-03-22"),
                ResumoGastosDataItem(BigDecimal(10.0), "2020-01-23"),
                ResumoGastosDataItem(BigDecimal(30.0), "2020-01-24"),
                ResumoGastosDataItem(BigDecimal(100.0), "2020-01-21"),
                ResumoGastosDataItem(BigDecimal(90.0), "2020-03-22"),
                ResumoGastosDataItem(BigDecimal(220.0), "2020-01-23"),
                ResumoGastosDataItem(BigDecimal(70.0), "2020-01-24"),
                ResumoGastosDataItem(BigDecimal(290.0), "2020-01-21"),
                ResumoGastosDataItem(BigDecimal(200.0), "2020-03-22"),
                ResumoGastosDataItem(BigDecimal(50.0), "2020-01-23"),
                ResumoGastosDataItem(BigDecimal(50.0), "2020-01-24")
            )

            val gsonDoResumoDosGastos = Gson().toJson(resumoGastosDataList)
//
//            Log.i("VIEW-JSON", gsonDoResumoDosGastos)
//            Log.i("VIEW-JSON", gsonDoResumoDosGastos.toString())

            val data = Gson().fromJson(gsonDoResumoDosGastos, ResumoGastosData::class.java)
//            Log.i("VIEW-JSON", data.toString())

            return Dataset(data.mapIndexed { index, resumoGastosDataItem ->
                DataPoint(index.toFloat(), resumoGastosDataItem.valor.toFloat())
            }.toMutableList())


        }

    fun prepara(listaItensPorMes: List<Reabastecimento>): Dataset {

        Log.i("VIEW-PREPARA","Preparando a brincadeira com a lista de tamanho ${listaItensPorMes.size}")

        var listaItens: ArrayList<ResumoGastosDataItem> = ArrayList()

        for( reabastecimento: Reabastecimento in listaItensPorMes){
            Log.i("VIEW-ITERANDO","ITEM RECEBIDO: ${reabastecimento}")
            var item = ResumoGastosDataItem(BigDecimal(reabastecimento.valor), reabastecimento.dataGravacao)
            Log.i("VIEW-ITERANDO","Adicionado o item ${item}")
            listaItens.add(item)
        }

        val gsonDoResumoDosGastos = Gson().toJson(listaItens)

        Log.i("VIEW-JSON", gsonDoResumoDosGastos.toString())

        val data = Gson().fromJson(gsonDoResumoDosGastos, ResumoGastosData::class.java)
        Log.i("VIEW-JSON", data.toString())

        return Dataset(data.mapIndexed { index, resumoGastosDataItem ->
            DataPoint(index.toFloat(), resumoGastosDataItem.valor.toFloat())
        }.toMutableList())

    }


}