package br.com.calculadora.calculocombustivel.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.math.BigDecimal
import java.text.SimpleDateFormat
import java.util.*

@Entity
data class Reabastecimento(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val tipoCombustivel: String = "",
    var kmRodados: Double = 0.0,
    var litros: Double = 0.0,
    var consumoKilometrosPorLitro: Double = 0.0,
    val posicaoTipoDeCombustivel:Int = 0,
    var dataGravacao: String = "",
    val valor: String = "0"

){

    override fun toString(): String {
        return "ID: ${id}, " +
                "tipoCombustivel ${tipoCombustivel}, " +
                "kmRodados ${kmRodados}, " +
                "valor ${valor}, "+
                "litros ${litros}, " +
                "consumoKilometrosPorLitro ${consumoKilometrosPorLitro}, " +
                "posicaoTipoDeCombustivel ${posicaoTipoDeCombustivel}, "
                "Data da gravação ${dataGravacao}"
    }


}