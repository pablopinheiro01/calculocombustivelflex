package br.com.calculadora.calculocombustivel.business

import java.text.SimpleDateFormat
import java.util.*

class DataUtil {

    companion object{
        fun pegaDataAtual(): String {
            val timeInMillis = Calendar.getInstance().timeInMillis
            val format = SimpleDateFormat("YYYY-MM-dd", Locale.getDefault()).format(timeInMillis)
            return format
        }
    }
}