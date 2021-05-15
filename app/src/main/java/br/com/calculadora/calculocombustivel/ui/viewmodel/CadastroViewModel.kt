package br.com.calculadora.calculocombustivel.ui.viewmodel

import android.util.Log
import android.view.View
import android.widget.AdapterView
import androidx.databinding.Observable
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import br.com.calculadora.calculocombustivel.model.Reabastecimento
import br.com.calculadora.calculocombustivel.repository.ReabastecimentoRepository
import br.com.calculadora.calculocombustivel.repository.Resource

class CadastroViewModel(
    private val repository : ReabastecimentoRepository
): ViewModel() {

    fun buscaPorId(id: Long) = repository.buscaPorId(id)

    fun salva(item: Reabastecimento): LiveData<Resource<Unit>> = repository.salva(item)


}