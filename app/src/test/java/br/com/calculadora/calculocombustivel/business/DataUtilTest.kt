package br.com.calculadora.calculocombustivel.business

import org.junit.Assert
import org.junit.Test
import java.text.SimpleDateFormat
import java.util.*

class DataUtilTest {

    @Test
    public fun deve_FormatarData_QuandoRecebeDataAtual(){
        var dataAtual = DataUtil.pegaDataAtual()

        val timeInMillis = Calendar.getInstance().timeInMillis
        val format = SimpleDateFormat("YYYY-MM-dd", Locale.getDefault()).format(timeInMillis)

        System.out.println(dataAtual)
        System.out.println(format)

        Assert.assertEquals(format, dataAtual)

    }
}