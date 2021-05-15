package br.com.calculadora.calculocombustivel.model

import java.math.BigDecimal

class ResumoGastosData : ArrayList<ResumoGastosDataItem>()

data class ResumoGastosDataItem(
    val valor: BigDecimal,
    val data: String
){}
