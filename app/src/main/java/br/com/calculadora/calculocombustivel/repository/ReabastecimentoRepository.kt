package br.com.calculadora.calculocombustivel.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.calculadora.calculocombustivel.database.dao.ReabastecimentoDAO
import br.com.calculadora.calculocombustivel.model.Reabastecimento
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.lang.Exception

class ReabastecimentoRepository(private val dao: ReabastecimentoDAO) {

    fun buscaTodas() = dao.buscaTodas()

    fun buscaPorId(id: Long) = dao.buscaPorId(id)

    fun salva(item: Reabastecimento, job: Job = Job()) =
        MutableLiveData<Resource<Unit>>().also { liveData ->
            CoroutineScope(IO+job).launch {
                val resource: Resource<Unit> = try {
                    dao.salva(item)
                    Sucesso()
                }catch (e:Exception){
                    Falha("Falha ao salvar")
                }
                liveData.postValue(resource)
            }
        }

    fun exclui(reabastecimento: Reabastecimento, job: Job = Job()) =
        MutableLiveData<Resource<Unit>>().also { liveData ->
            CoroutineScope(IO+job).launch {
                val resource: Resource<Unit> = try {
                    dao.exclui(reabastecimento)
                    Sucesso()
                }catch(e: Exception){
                    Falha("Falha ao deletar")
            }
                liveData.postValue(resource)
        }
    }

    fun buscaPorData(dataInicial: String, dataFinal: String, job: Job = Job())=
        MutableLiveData<Resource<List<Reabastecimento>>>().also { liveData ->
            CoroutineScope(IO+job).launch {
                val resource: Resource<List<Reabastecimento>> = try{
                    dao.buscaPorData(dataInicial, dataFinal)
                    Sucesso()
                }catch(e : Exception){
                    Falha("Falha ao buscar")
                }
                Log.i("Repository",resource.dado.toString())
                liveData.postValue(resource)
            }
        }

    fun buscaPorDataNovo(dataInicial: String, dataFinal: String): LiveData<List<Reabastecimento>> = dao.buscaPorData(dataInicial,dataFinal)

    fun buscaTodosOsMesesCadastrados(): LiveData<List<String>> = dao.buscaTodosOsMesesCadastrados()

    fun buscaListaDeReabastecimentoPorMes(mesPesquisado:String):LiveData<List<Reabastecimento>> = dao.buscaListaPorMes(mesPesquisado)




}