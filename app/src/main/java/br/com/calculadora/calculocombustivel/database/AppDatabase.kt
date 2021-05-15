package br.com.calculadora.calculocombustivel.database

import androidx.room.Database
import androidx.room.RoomDatabase
import br.com.calculadora.calculocombustivel.database.dao.ReabastecimentoDAO
import br.com.calculadora.calculocombustivel.model.Reabastecimento

@Database(version = 1, exportSchema = false, entities = [Reabastecimento::class])
abstract class AppDatabase: RoomDatabase() {

    abstract val dao: ReabastecimentoDAO

}