package br.com.calculadora.calculocombustivel.exception

import java.lang.Exception

class GenericException(var mensagem: String = "Ocorreu algum problema na sua solicitação."): Exception(mensagem) {
}