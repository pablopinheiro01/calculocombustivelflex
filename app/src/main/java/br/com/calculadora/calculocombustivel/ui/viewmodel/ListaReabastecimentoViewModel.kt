package br.com.calculadora.calculocombustivel.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import br.com.calculadora.calculocombustivel.di.repositoryModule
import br.com.calculadora.calculocombustivel.model.Reabastecimento
import br.com.calculadora.calculocombustivel.repository.ReabastecimentoRepository
import br.com.calculadora.calculocombustivel.repository.Resource

class ListaReabastecimentoViewModel(private val repository: ReabastecimentoRepository): ViewModel() {

    val todas: LiveData<List<Reabastecimento>> = repository.buscaTodas()

    fun exclui(reabastecimento: Reabastecimento): LiveData<Resource<Unit>> = repository.exclui(reabastecimento)


}