package br.com.calculadora.calculocombustivel.business

class NumberUtil {

    companion object{
        fun transformaPosicaoDoArrayParaPosicaoDoMes(posicao: Int):String{
//
//            if(posicao == 0){
//                return "01"
//            }else{
//                var pos = posicao
//                pos++
//                return "0$pos"
//            }


            return "0"+(posicao+1)

        }
    }
}