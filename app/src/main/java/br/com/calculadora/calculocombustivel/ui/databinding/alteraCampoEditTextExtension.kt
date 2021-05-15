package br.com.calculadora.calculocombustivel.ui.databinding

import android.util.Log
import android.widget.EditText
import androidx.databinding.BindingAdapter

@BindingAdapter(value = ["inserePontoEZero"])
fun EditText.inserePontoEZero(habilita: Boolean){

    this.setOnFocusChangeListener { v, hasFocus ->

//        if(hasFocus and v.isPressed){
//            this.validaCampo()
//        }

        if(!hasFocus){
            this.validaCampo()
        }else{
            this.selectionStart
        }
    }

    /*if(habilita){
        if(this.text.contains(".")){
            Log.i("ITEM-MASCARA","texto contem ponto")
        }else{
            var texto = this.text.toString()
            if(texto.length > -1){
                texto = texto + ".0"
            }else{
                texto = "0.0"
            }
            this.setText(texto)
        }
    }*/
}

fun EditText.validaCampo(): Boolean {
    Log.i("ITEM-ERRO","o campo impresso: ${this.text}")
    if(this.text.isNullOrEmpty() || this.text.toString().equals("0.0") || this.text.toString().equals("0") || this.text.isBlank()){
        this.error = "Preencha um valor válido!"
        this.isFocusable=true
        return false
    }else{
        if(this.text.toString().toDouble() > 2000.0){
            Log.i("ITEM-VALORMAXIMO","VALOR MAXIMO ENTROU ACIMA DE 200")
            this.error = "Valor máximo permitido é 200"
            return false
        }
    }
    return true
}

//fun EditText.valorMaximo():Boolean{
//        Log.i("ITEM-VALORMAXIMO","ITEM DIGITADO: ${this.text.toString()}")
//
//    if(this.text.isNullOrEmpty() || this.text.toString().equals("0.0") || this.text.toString().equals("0") || this.text.isBlank()){
//        validaCampo()
//    }else{
//        if(this.text.toString().toDouble() > 1000.0){
//            Log.i("ITEM-VALORMAXIMO","VALOR MAXIMO ENTROU ACIMA DE 200")
//            this.error = "Valor máximo permitido é 200"
//            return false
//        }
//    }
//    return true
//}