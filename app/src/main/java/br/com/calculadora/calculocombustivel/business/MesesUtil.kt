package br.com.calculadora.calculocombustivel.business

import android.util.Log

class MesesUtil {
    companion object{
        fun retorna(
            listaDeMesesCadastrados: List<String>,
            mesesDoAno: Array<String>
        ): ArrayList<String> {
            var mesesParaExibicaoDoSpinner: ArrayList<String> = ArrayList()
            var mesCadastrado:Int = 0

//            Log.i("VIEW-MESES-REC","TAMANHO DA LISTA: ${listaDeMesesCadastrados.size}")
            for(mes:String in listaDeMesesCadastrados ){
                val mesFatiado = mes.substring(5, 7)

                mesCadastrado = retornaMesNaPosicaoDaLista(mesFatiado, mesCadastrado)

//                Log.i("VIEW-MESESUTIL","MES CORTADO ${mesFatiado}")
                verificaSeJaPossuiMesNaListaDeRetorno(
                    mesesParaExibicaoDoSpinner,
                    mesCadastrado,
                    mesesDoAno
                )
            }

            return mesesParaExibicaoDoSpinner
        }

        private fun verificaSeJaPossuiMesNaListaDeRetorno(
            mesesParaExibicaoDoSpinner: ArrayList<String>,
            mesCadastrado: Int,
            mesesDoAno: Array<String>
        ) {
            if (mesesParaExibicaoDoSpinner.getOrNull(mesCadastrado).isNullOrEmpty()) {
                // if( retorno.get(m.toInt()-1).equals(null) || retorno.get(m.toInt()-1).equals(""))
//                Log.i("VIEW-MESESUTIL", "POSICAO DO MES SELECIONADO ${mesCadastrado}")
//                Log.i("VIEW-MESESUTIL", "MES ADICIONADO ${mesesDoAno.get(mesCadastrado)}")
                mesesParaExibicaoDoSpinner.add(mesesDoAno.get(mesCadastrado))
            }
        }

        private fun retornaMesNaPosicaoDaLista(
            mesFatiado: String,
            mesCadastrado: Int
        ): Int {
            var mesCadastrado1 = mesCadastrado
            if (mesFatiado.toInt() != 0) {
                mesCadastrado1 = mesFatiado.toInt() - 1
            }
            return mesCadastrado1
        }
    }

}