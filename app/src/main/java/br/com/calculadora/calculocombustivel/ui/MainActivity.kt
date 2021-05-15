package br.com.calculadora.calculocombustivel.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import br.com.calculadora.calculocombustivel.R
import br.com.calculadora.calculocombustivel.ui.viewmodel.AppViewModel
import br.com.calculadora.calculocombustivel.ui.viewmodel.ComponentesVisuais
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val appViewModel: AppViewModel by viewModel()

    private val controlador by lazy{
        findNavController(R.id.main_activity_nav_host)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        controlador.addOnDestinationChangedListener(){
            _, _, _ ->

            appViewModel.componentes.observe(this, Observer {
                it?.let{
                    componente -> configuraAppBar(componente)
                }
            })
        }

    }

    private fun configuraAppBar(componentes: ComponentesVisuais) {
        componentes.appBar?.let{
            appBarConfigurada -> title = appBarConfigurada.titulo
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_home, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.menu_cadastro) controlador.navigate(R.id.listaReabastecimentoFragment)
        if(item.itemId == R.id.menu_grafico_uso) controlador.navigate(R.id.resumoCadastroFragment)
        return super.onOptionsItemSelected(item)
    }





}