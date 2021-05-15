package br.com.calculadora.calculocombustivel.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import br.com.calculadora.calculocombustivel.model.Reabastecimento

@Dao
abstract class ReabastecimentoDAO {

    @Query("SELECT * FROM Reabastecimento ORDER BY dataGravacao ASC")
    abstract fun buscaTodas(): LiveData<List<Reabastecimento>>

    @Insert(onConflict = REPLACE)
    abstract fun salva(reabastecimento: Reabastecimento)

    @Query("SELECT * FROM Reabastecimento WHERE id = :id")
    abstract fun buscaPorId(id: Long): LiveData<Reabastecimento?>

    @Delete
    abstract fun exclui(reabastecimento: Reabastecimento)

    @Query("SELECT * FROM Reabastecimento WHERE dataGravacao BETWEEN :dataInicial AND :dataFinal ")
    abstract fun buscaPorData(dataInicial:String, dataFinal: String): LiveData<List<Reabastecimento>>

    @Query("SELECT dataGravacao FROM Reabastecimento WHERE strftime('%m',dataGravacao) ")
    abstract fun buscaTodosOsMesesCadastrados(): LiveData<List<String>>

    @Query("SELECT * FROM Reabastecimento  WHERE strftime('%m',dataGravacao) = :mesPesquisado")
    abstract fun buscaListaPorMes(mesPesquisado: String): LiveData<List<Reabastecimento>>


}