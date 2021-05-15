package br.com.calculadora.calculocombustivel.ui.databinding

import android.util.Log
import androidx.lifecycle.MutableLiveData
import br.com.calculadora.calculocombustivel.model.Reabastecimento
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

class ReabastecimentoData(
    private var reabastecimento: Reabastecimento = Reabastecimento(),
    var tipoCombustivel: MutableLiveData<String> = MutableLiveData<String>().also { it.value = reabastecimento.tipoCombustivel },
    val tipoCombustivelPosition:  MutableLiveData<Int> = MutableLiveData<Int>().also { it.value = reabastecimento.posicaoTipoDeCombustivel },
    var kmRodados: MutableLiveData<String> = MutableLiveData<String>().also { it.value = reabastecimento.kmRodados.toString() },
    val litros: MutableLiveData<String> = MutableLiveData<String>().also { it.value = reabastecimento.litros.toString() },
    val consumoKilometrosPorLitro: MutableLiveData<String> = MutableLiveData<String>().also { it.value = reabastecimento.consumoKilometrosPorLitro.toString() },
    val dataGravacao: MutableLiveData<String> = MutableLiveData<String>().also { it.value = reabastecimento.dataGravacao },
    val valor: MutableLiveData<String> = MutableLiveData<String>().also{ it.value = reabastecimento.valor}
) {

    fun atualiza(reabastecimento: Reabastecimento){
        this.reabastecimento = reabastecimento
        Log.i("ITEM","metodo atualiza recebeu: ${reabastecimento.toString()}")
        tipoCombustivel.postValue(this.reabastecimento.tipoCombustivel)
        kmRodados.postValue(this.reabastecimento.kmRodados.toString())
        litros.postValue(this.reabastecimento.litros.toString())
        consumoKilometrosPorLitro.postValue(this.reabastecimento.consumoKilometrosPorLitro.toString())
        tipoCombustivelPosition.postValue(this.reabastecimento.posicaoTipoDeCombustivel)
        valor.postValue(this.reabastecimento.valor)
    }

    fun paraReabastecimento() : Reabastecimento?{

        return this.reabastecimento.copy(
            tipoCombustivel = tipoCombustivel.value ?: return null,
            kmRodados = kmRodados.value?.toDouble() ?: return null,
            litros = litros.value?.toDouble() ?: return null,
            consumoKilometrosPorLitro = consumoKilometrosPorLitro.value?.toDouble() ?: return null,
            posicaoTipoDeCombustivel = tipoCombustivelPosition.value ?: return null,
            valor = valor.value ?: return null
        )
    }

    override fun toString(): String {
        return " tipoCombustivel ${tipoCombustivel.value}, kmRodados ${kmRodados.value},valor ${valor.value} litros ${litros.value}, consumoKilometroPorLitro ${consumoKilometrosPorLitro.value}, posicao do item no spinner: ${tipoCombustivelPosition.value}"
    }
}