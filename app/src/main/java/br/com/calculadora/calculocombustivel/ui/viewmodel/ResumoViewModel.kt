package br.com.calculadora.calculocombustivel.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import br.com.calculadora.calculocombustivel.model.Reabastecimento
import br.com.calculadora.calculocombustivel.repository.ReabastecimentoRepository

class ResumoViewModel(
    private val repository : ReabastecimentoRepository
): ViewModel() {

    fun buscaPorData(dataInicio:String, dataFim:String): LiveData<List<Reabastecimento>> = repository.buscaPorDataNovo(dataInicio, dataFim)

    fun buscaTodos(): LiveData<List<Reabastecimento>> = repository.buscaTodas()

    fun buscaTodosOsMesesCadastrados():LiveData<List<String>> = repository.buscaTodosOsMesesCadastrados()

    fun buscaListaDeReabastecimentoPorMes(mesPesquisado:String):LiveData<List<Reabastecimento>> = repository.buscaListaDeReabastecimentoPorMes(mesPesquisado)


}