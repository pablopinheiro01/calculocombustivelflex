package br.com.calculadora.calculocombustivel.business

import br.com.calculadora.calculocombustivel.model.Reabastecimento
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class CalculoCombustivelTest {


    @Test
    fun testaCalculoKmPorLitro(){
        val calculadora: CalculoCombustivel = CalculoCombustivel()
        for (i in 0..5){
            var doubleAlterado = ("0."+i).toDouble()
            var kmRodadoCalculado = 1.52 + i.toDouble() + doubleAlterado
            var litrosCalculado = 0.9 + i.toDouble() + doubleAlterado
            var item = Reabastecimento(tipoCombustivel = "Gasolina",kmRodados = 1.52 + i.toDouble() + doubleAlterado,litros = 0.9 + i.toDouble() + doubleAlterado)

            System.out.println("item antes da alteração = ${item.toString()}")

            System.out.println("consumo antes do calculo = ${item.consumoKilometrosPorLitro}")
            item = calculadora.calculaKmPorLitro(item)
            System.out.println("consumo depois do calculo = ${item.consumoKilometrosPorLitro}")

            System.out.println("kmRodadoCalculado = ${kmRodadoCalculado} litrosCalculado ${litrosCalculado}")

            var calculoKmRodadoPorLitro = kmRodadoCalculado / litrosCalculado
            val formatador = String.format("%.2f", calculoKmRodadoPorLitro).replace(",",".")
            var kmPorLitroCalculado = formatador.format(calculoKmRodadoPorLitro).toDouble()

            System.out.println("Comparando o valor calculado ${kmPorLitroCalculado} pelo valor calculado na classe ${item.consumoKilometrosPorLitro}")

            System.out.println("item depois da alteração = ${item.toString()}")

            assertEquals(kmRodadoCalculado, item.kmRodados, 0.0001)
            assertEquals(litrosCalculado, item.litros, 0.0001)
            assertEquals(kmPorLitroCalculado, item.consumoKilometrosPorLitro, 0.0001)
        }
    }
}