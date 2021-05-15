package br.com.calculadora.calculocombustivel.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import br.com.calculadora.calculocombustivel.R
import br.com.calculadora.calculocombustivel.business.CalculoCombustivel
import br.com.calculadora.calculocombustivel.business.DataUtil
import br.com.calculadora.calculocombustivel.databinding.CadastroItensBinding
import br.com.calculadora.calculocombustivel.model.Reabastecimento
import br.com.calculadora.calculocombustivel.repository.Falha
import br.com.calculadora.calculocombustivel.repository.Sucesso
import br.com.calculadora.calculocombustivel.ui.databinding.ReabastecimentoData
import br.com.calculadora.calculocombustivel.ui.databinding.validaCampo
import br.com.calculadora.calculocombustivel.ui.viewmodel.AppBar
import br.com.calculadora.calculocombustivel.ui.viewmodel.AppViewModel
import br.com.calculadora.calculocombustivel.ui.viewmodel.CadastroViewModel
import br.com.calculadora.calculocombustivel.ui.viewmodel.ComponentesVisuais
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.sharedViewModel
import org.koin.android.viewmodel.ext.android.viewModel
import java.lang.Exception


class CadastroFragment : Fragment() {

    private val arguments by navArgs<CadastroFragmentArgs>()
    private val viewModel: CadastroViewModel by sharedViewModel()
    private val appViewModel: AppViewModel by sharedViewModel()

    private lateinit var viewDataBinding: CadastroItensBinding
    private val itemId by lazy {
        arguments.itemId
    }

    private val controlador by lazy {
        findNavController()
    }

    private val itemData by lazy {
        ReabastecimentoData()
    }

    private val calculoCombustivel: CalculoCombustivel by inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewDataBinding = CadastroItensBinding.inflate(inflater, container, false)
        viewDataBinding.lifecycleOwner = this
        viewDataBinding.item = itemData
        Log.i("ITEM-CADASTRO", "item onCreateView:" + itemData.toString())

        viewDataBinding.salvaCadastro = View.OnClickListener {
            Log.i("ITEM-CADASTRO", "Item carregado no modo by lazy ${itemData.toString()}")
            itemData?.let{
                if(viewDataBinding.cadastroItemKmRodados.validaCampo() and viewDataBinding.cadastroItemQuantidadeLitros.validaCampo()){
                    viewDataBinding.cadastroItensBotaoSalvar.error = null
                    viewDataBinding.cadastroItemErroGenericoTextoEscondido.visibility = View.INVISIBLE
                    Log.i("ITEM-VALIDA","DEU BOM ENTROU")
                    salva(itemData)
                }else{
                    Log.i("ITEM-VALIDA","DEU RUIM")
                }
            }
        }

        return viewDataBinding.root
    }

    private fun salva(itemData: ReabastecimentoData) {
        Log.i("ITEM-CADASTRO","item recebido: "+itemData.toString())
        Log.i("ITEM-CADASTRO","item do viewbinding: "+ viewDataBinding.cadastroItemKmRodados.text )


        val itemNovo: Reabastecimento? = itemData.paraReabastecimento()
        itemNovo?.let{
            Log.i("ITEM-CADASTRO", "BORA CADASTRAR UM PEAO NOVO")
            Log.i("ITEM-CADASTRO", itemNovo.toString())
            Log.i("ITEM-CADASTRO", itemNovo.tipoCombustivel)
            Log.i("ITEM-CADASTRO", itemNovo.consumoKilometrosPorLitro.toString())
            Log.i("ITEM-CADASTRO", itemNovo.litros.toString())
            Log.i("ITEM-CADASTRO", itemNovo.kmRodados.toString())
            Log.i("ITEM-CADASTRO", "km rodado" + itemNovo.kmRodados)

            var itemAlterado: Reabastecimento = Reabastecimento()

            try{
                itemAlterado = calculoCombustivel.calculaKmPorLitro(itemNovo)
                itemAlterado.dataGravacao  = DataUtil.pegaDataAtual()

                viewModel.salva(itemAlterado).observe(this, Observer {
                        resource ->
                    when(resource){
                        is Sucesso -> controlador.popBackStack()
                        is Falha -> resource.erro?.run { Log.i("ITEM-ERRO","Erro ao gravar") }
                    }
                })
            }catch(e: Exception){
                viewDataBinding.cadastroItensBotaoSalvar.error = e.message
                viewDataBinding.cadastroItemErroGenericoTextoEscondido.visibility = View.VISIBLE
                viewDataBinding.cadastroItemErroGenericoTextoEscondido.setText(e.message)
            }
        }
    }

    private fun appBarParaEdicao() = ComponentesVisuais(appBar = AppBar(titulo = "Editando nota"))

    private fun appBarParaCriacao() = ComponentesVisuais(appBar = AppBar(titulo = "Criando nota"))

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        appViewModel.temComponentes = ComponentesVisuais(appBar = AppBar(titulo = "Cadastrar"))
        Log.i("ITEM-CADASTRO", "item onViewCreated:" + itemData.toString())
        Log.i("ITEM-CADASTRO","view renderizada: ${view.toString()}")
        //tentativa de carregar o item
        if (itemId != 0L) {
            viewModel.buscaPorId(itemId).observe(this, Observer {
                it?.let { itemEncontrado ->
                    itemData.atualiza(itemEncontrado)
                    appViewModel.temComponentes = ComponentesVisuais(appBar = AppBar(titulo = "Editar"))

                }
            })
        }

        viewDataBinding.item?.tipoCombustivelPosition?.observe(this, object: Observer<Int> {
            override fun onChanged(t: Int?) {
                val stringArray = resources.getStringArray(R.array.tipos_combustiveis)
                if (t != null) {
                    Log.i("ITEM-CADASTRO","ATE QUE ENFIMMMMMMMMMMMM"+ stringArray.get(t))
                    Log.i("ITEM-CADASTRO", "OBJETO MUTABLELIVEDATA O ITEM: ${itemData.litros.value}")
                    itemData.tipoCombustivel.value = stringArray.get(t)
                }
            }
        })
    }


}