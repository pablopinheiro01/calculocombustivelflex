package br.com.calculadora.calculocombustivel.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import br.com.calculadora.calculocombustivel.model.Reabastecimento


@Dao
abstract class ReabastecimentoDAOTest {

    @Query("SELECT * FROM Reabastecimento ORDER BY dataGravacao ASC")
    abstract fun buscaTodas(): LiveData<List<Reabastecimento>>

}