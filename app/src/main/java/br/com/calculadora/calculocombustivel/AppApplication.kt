package br.com.calculadora.calculocombustivel

import android.app.Application
import br.com.calculadora.calculocombustivel.di.testeAppModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class AppApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(applicationContext)
            modules(testeAppModule)
        }
    }

}