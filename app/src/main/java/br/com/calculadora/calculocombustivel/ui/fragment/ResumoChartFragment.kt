package br.com.calculadora.calculocombustivel.ui.fragment

import android.graphics.Color
import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.*
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import br.com.calculadora.calculocombustivel.R
import br.com.calculadora.calculocombustivel.business.MesesUtil
import br.com.calculadora.calculocombustivel.business.NumberUtil
import br.com.calculadora.calculocombustivel.business.ResumoGastosBusiness
import br.com.calculadora.calculocombustivel.databinding.ResumoConsumoBarchartBinding
import br.com.calculadora.calculocombustivel.ui.viewmodel.ResumoViewModel
import com.yabu.livechart.model.DataPoint
import com.yabu.livechart.model.Dataset
import com.yabu.livechart.view.LiveChart
import com.yabu.livechart.view.LiveChartStyle
import kotlinx.android.synthetic.main.resumo_consumo_barchart.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.sharedViewModel
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class ResumoChartFragment(): Fragment(){

    private val dados: ResumoGastosBusiness by inject()
    private val view: ResumoViewModel by sharedViewModel()
    private var dadosPreparados: Dataset = dados.preparaDados()
    private var chartStyle = LiveChartStyle().apply{
        mainColor - R.color.cerulean_crayola_blue
        pathStrokeWidth = 8f
        secondPathStrokeWidth = 5f
        textHeight = 80f
        textColor = Color.BLACK
        overlayLineColor = Color.BLUE
        overlayCircleDiameter = 32f
        overlayCircleColor = Color.RED
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val viewBinding = ResumoConsumoBarchartBinding.inflate(inflater, container, false)

        var mesAtualDoSistema: String = SimpleDateFormat("MM", Locale.getDefault()).format(Calendar.getInstance().time)
        Log.i("VIEW-MESATUAL-SYS","MES ATUAL: ${mesAtualDoSistema}")

        view.buscaTodosOsMesesCadastrados().observe(this,
            Observer { listaDeMeses ->
                Log.i("VIEW-CICLODEVIDA"," ESTAMOS NO CICLO MESESCADASTRADOS ${this.lifecycle.currentState}")
                Log.i("VIEW-MESES", listaDeMeses.toString())
                Log.i("VIEW-MESES", "Tamanho da lista ${listaDeMeses.size}")

                var mesesDoAno: Array<String> = resources.getStringArray(R.array.meses_busca_resumo_gastos)

                var mesesParaSeremInseridos: ArrayList<String> = MesesUtil.retorna(listaDeMeses, mesesDoAno)

                var arrayAdapter = context?.let { ArrayAdapter<String>(it, android.R.layout.simple_spinner_item, mesesParaSeremInseridos ) }

                resumo_consumo_lista_meses_spinner.run {
                    adapter  = arrayAdapter
                }

                resumo_consumo_lista_meses_spinner.setSelection(mesAtualDoSistema.toInt()-1)
                resumo_consumo_lista_meses_spinner.onItemSelectedListener = object : OnItemSelectedListener{
                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
//                        montaChart(viewBinding, chartStyle,)
                        Log.i("VIEW-SPINNER", "CLICANDO NO MES QUE FOI PEGO DA LISTA : ${mesesParaSeremInseridos.get(position)}")
                        Log.i("VIEW-SPINNER","onItemSelected")
                        Log.i("VIEW-SPINNER","posicao do position: ${position}")
                        var posicaoClicada = NumberUtil.transformaPosicaoDoArrayParaPosicaoDoMes(position)
                        Log.i("VIEW-SPINNER","posicao da variavel posicaoClicada: ${posicaoClicada}")
                        montaChart(viewBinding, chartStyle, posicaoClicada, viewLifecycleOwner )
                    }

                    override fun onNothingSelected(parent: AdapterView<*>?) {
                       Log.i("VIEW-SPINNER","onNothingSelected")
                    }
                }
        })

        //monta o chart.
        montaChart(viewBinding, chartStyle, mesAtualDoSistema,this)

        return viewBinding.root
    }

    private fun montaChart(
        viewBinding: ResumoConsumoBarchartBinding,
        chartStyle: LiveChartStyle,
        mes: String,
        owner: LifecycleOwner
    ) {

        view.buscaListaDeReabastecimentoPorMes(mes).observe(owner,
            Observer { listaItensPorMes ->
//                Log.i("VIEW-CICLODEVIDA"," ESTAMOS NO CICLO ${owner.lifecycle.currentState}")
//                Log.i("VIEW-LISTASDEITENS", "pesquisando o mês ${mes}")
//                Log.i("VIEW-LISTASDEITENS", "listaItensPorMes do tamanho ${listaItensPorMes.size}")
                dados.preparaDados()
                if (listaItensPorMes.size > 1) {
                    resumo_conta_texto_posicao.text = "Selecione a posição no gráfico"
                    resumo_conta_livechart.visibility = View.VISIBLE

                    var dados = dados.prepara(listaItensPorMes)
                    Log.i("VIEW-DADOSPREPARADOS", "Dados preparados: ${dados}")
                    viewBinding.resumoContaLivechart
                        .setDataset(dados)
                        .setLiveChartStyle(chartStyle)
                        .drawSmoothPath()
                        .drawStraightPath()
                        .drawYBounds()
                        .drawLastPointLabel()
                        .drawHorizontalGuidelines(steps = 10)
                        .drawVerticalGuidelines(steps = 10)
                        .drawBaselineFromFirstPoint()
                        .setOnTouchCallbackListener(object : LiveChart.OnTouchCallback {
                            override fun onTouchCallback(point: DataPoint) {
                                viewBinding.resumoContaLivechart.parent.requestDisallowInterceptTouchEvent(
                                    true
                                )
                                resumo_conta_texto_posicao.text =
                                    "(${"%.2f".format(point.x)}, ${"%.2f".format(point.y)})"
                            }

                            override fun onTouchFinished() {
                                viewBinding.resumoContaLivechart.parent.requestDisallowInterceptTouchEvent(
                                    false
                                )
                                resumo_conta_texto_posicao.text = "Selecione a posição no gráfico"
                            }

                        })
                        .drawDataset()
                }else{
                    resumo_conta_texto_posicao.text = "Não encontramos dados suficientes para carregar o grafico!"
                    resumo_conta_livechart.visibility = View.INVISIBLE
                }
            })


    }

}
