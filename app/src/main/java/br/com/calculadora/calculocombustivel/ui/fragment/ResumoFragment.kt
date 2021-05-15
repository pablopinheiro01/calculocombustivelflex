package br.com.calculadora.calculocombustivel.ui.fragment

import android.content.res.Resources
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import br.com.calculadora.calculocombustivel.R
import br.com.calculadora.calculocombustivel.databinding.ResumoConsumoBinding
import br.com.calculadora.calculocombustivel.model.Reabastecimento
import br.com.calculadora.calculocombustivel.ui.viewmodel.ResumoViewModel
import com.faskn.lib.PieChart
import com.faskn.lib.Slice
import com.faskn.lib.buildChart
import kotlinx.android.synthetic.main.resumo_consumo.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel
import java.util.*
import kotlin.collections.ArrayList

class ResumoFragment : Fragment() {

    private val viewModel: ResumoViewModel by viewModel()
    private lateinit var binding: ResumoConsumoBinding
    private lateinit var chart2: PieChart

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = ResumoConsumoBinding.inflate(inflater, container, false)

        var dadosParaExibicao: ArrayList<Reabastecimento> = ArrayList()

//            viewModel.buscaPorData("2020-07-01", "2021-04-01").observe(this, Observer { dadoRecebido ->
//                Log.i("VIEW","entrou na lista")
//                Log.i("VIEW","lista recebida da pesquisa: ${dadoRecebido.size}")
//
//                if (dadoRecebido != null && dadoRecebido.isNotEmpty()) {
//                    for (i in dadoRecebido) {
//                        Log.i("VIEW", i.toString())
//                        dadosParaExibicao.add(i)
//                    }
//                    Log.i("VIEW", "busca por data com observer: ${dadoRecebido.size}")
//                    Log.i("VIEW", "busca por dadosParaExibicao: ${dadosParaExibicao.size}")
//                }
//
//            })

        viewModel.buscaPorData("2020-07-01", "2021-04-01").observe(this, Observer { dadoRecebido ->
            Log.i("VIEW","entrou na lista")
            Log.i("VIEW","lista recebida da pesquisa: ${dadoRecebido.size}")

            if (dadoRecebido != null && dadoRecebido.isNotEmpty()) {
                for (i in dadoRecebido) {
                    Log.i("VIEW", i.toString())
                    dadosParaExibicao.add(i)
                }
                Log.i("VIEW", "busca por data com observer: ${dadoRecebido.size}")
                Log.i("VIEW", "busca por dadosParaExibicao: ${dadosParaExibicao.size}")
            }

            Log.i("VIEW", "PROCESSANDO O CHART")

            chart2 = buildChart {
                slices { provideSlices(dadosParaExibicao) }
                sliceWidth { 80f }
                sliceStartPoint { 0f }
            }
            var chart = binding.resumoConsumoPieChart
            chart.setPieChart(chart2)
            chart.showLegend(legendLayout2)
        })


//        chart.showLegend(legendLayout)

        return binding.root
    }

    private fun provideSlices(
        dadosParaExibicao: ArrayList<Reabastecimento>
    ): ArrayList<Slice> {

        Log.i("VIEW","ENTRAMOS NO METODO PROVIDESLICES")
        Log.i("VIEW-provideSlices","ENTRAMOS Segue variavel dadosParaExibicao  ${dadosParaExibicao.size}")

        var listaSlices: ArrayList<Slice> = ArrayList()
        var sequenciaDeCores: ArrayList<Int> = ArrayList()
//        var stringArray = resources.getStringArray(R.array.colors_pie_one_ref)
        var intArray = resources.getIntArray(R.array.colors_pie_one)

        var controle: Int = dadosParaExibicao.size
        var indice: Int = intArray.size
        Log.i("VIEW","Valor da variavel controle: ${controle}")
        Log.i("VIEW","Valor da variavel indice: ${indice}")


        Log.i("VIEW","valor total do tamanho da lista de cores do pie: ${intArray.size}")

        for(i in 0..intArray.size-1){
            Log.i("VIEW","Imprimindo ${intArray.get(i)}")
        }

        while( controle != 0 ){
            if(indice != 0) indice-- else indice = intArray.size
        Log.i("VIEW","Estamos no indice: ${indice}")
        Log.i("VIEW","Adicionamos na lista: ${intArray.get(controle)}")
            sequenciaDeCores.add(intArray.get(controle))
            controle--
        }

         
//
//        Log.i("VIEW",)
//        Log.i("VIEW",)
//        Log.i("VIEW",)
        Log.i("VIEW-SLICES","Preparando para fatiar as fatias ...")
        Log.i("VIEW-SLICES","o tamanho dos dados : ${dadosParaExibicao.size} ...")

        indice = 0
        for(i in dadosParaExibicao){
            Log.i("VIEW-SLICES","ITERANDO OS SLICES COMEÇANDO COM O OBJETO: ${i}")
            var sliceConfigurado : Slice
            sliceConfigurado = Slice(i.litros.toFloat(), sequenciaDeCores.get(indice) ,i.dataGravacao)
            Log.i("VIEW-SLICES","adicionando slices -> dataPoint: ${sliceConfigurado.dataPoint}, color: ${sliceConfigurado.color}, name: ${sliceConfigurado.name}")
            Log.i("VIEW-SLICES","indice antes: ${indice}")
            indice++
            Log.i("VIEW-SLICES","indice depois: ${indice}")
            listaSlices.add(sliceConfigurado)
        }

      // return listaSlices
        Log.i("VIEW-SLICES","VAMOS PERCORRER A LISTA DE SLICES de tamanho ${listaSlices.size}")
        for(i in listaSlices){
            Log.i("VIEW-SLICES","Lista de Slice -> dataPoint: ${i.dataPoint}, color: ${i.color}, name: ${i.name}")
        }

        Log.i("VIEW-COLOR","Cor: ${R.color.xiketic}")
        Log.i("VIEW-COLOR","Cor: ${R.color.darksienna}")
        Log.i("VIEW-COLOR","Cor: ${R.color.rosewood}")
        Log.i("VIEW-COLOR","Cor: ${R.color.darkred}")
        Log.i("VIEW-COLOR","Cor: ${R.color.rossocorsa}")
        Log.i("VIEW-COLOR","Cor: ${R.color.vermilion}")
        Log.i("VIEW-COLOR","Cor: ${R.color.persimmon}")
        Log.i("VIEW-COLOR","Cor: ${R.color.carrotorange}")
        Log.i("VIEW-COLOR","Cor: ${R.color.orangeweb}")
        Log.i("VIEW-COLOR","Cor: ${R.color.selectiveyellow}")

        // 30 registros -> 10 cores

          arrayListOf(
            Slice(
                150f,
                2131099895,
                "Janeiro"
            ),
            Slice(
                200f,
                R.color.celadon_blue,
                "Facebook"
            ),
            Slice(
                150f,
                R.color.imperial_red,
                "Twitter"
            ),
            Slice(
                200f,
                R.color.honeydew,
                "Other"
            )
        )

        return listaSlices

    }

}