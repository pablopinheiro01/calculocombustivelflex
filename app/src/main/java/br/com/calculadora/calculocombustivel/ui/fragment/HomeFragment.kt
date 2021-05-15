package br.com.calculadora.calculocombustivel.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import br.com.calculadora.calculocombustivel.databinding.ListaReabastecimentoBinding
import br.com.calculadora.calculocombustivel.ui.recyclerview.adapter.ListaReabastecimentoAdapter
import br.com.calculadora.calculocombustivel.ui.viewmodel.AppBar
import br.com.calculadora.calculocombustivel.ui.viewmodel.AppViewModel
import br.com.calculadora.calculocombustivel.ui.viewmodel.ComponentesVisuais
import br.com.calculadora.calculocombustivel.ui.viewmodel.ListaReabastecimentoViewModel
import kotlinx.android.synthetic.main.lista_reabastecimento.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.sharedViewModel
import org.koin.android.viewmodel.ext.android.viewModel


class HomeFragment : Fragment() {

    private val listaReabastecimentoAdapter: ListaReabastecimentoAdapter by inject()
    private val viewModel: ListaReabastecimentoViewModel by viewModel()
    private val controlador by lazy{
        findNavController()
    }
    private val appViewModel: AppViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val viewBinding = ListaReabastecimentoBinding.inflate(inflater, container, false)

        viewBinding.listaFloatActionButton.setOnClickListener {
            HomeFragmentDirections.actionListaReabastecimentoFragmentToCadastroFragment(0L).run{
                controlador.navigate(this)
            }
        }

        val itemTouchHelper = ItemTouchHelper(
            object : ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.RIGHT or
                ItemTouchHelper.LEFT
            ) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    return true
                }
                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val posicaoDoItemDeslizado: Int = viewHolder.adapterPosition
                    Log.i("ITEM-SWIPED","o id da brincadeira: "+ viewHolder.itemId)
                    Log.i("ITEM-SWIPED","o id da view: "+ posicaoDoItemDeslizado)
                    viewHolder as ListaReabastecimentoAdapter.ViewHolder
                    val itemParaExclusao = viewHolder.pegaItemDaView()
                    viewModel.exclui(itemParaExclusao)
                }

            })

        itemTouchHelper.attachToRecyclerView(viewBinding.listaReabastecimentoRecyclerview)

        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        appViewModel.temComponentes = ComponentesVisuais(appBar = AppBar("Lista de Reabastecimentos"))

        //configuro a recyclerview com o adapter
        lista_reabastecimento_recyclerview.run{
            adapter=listaReabastecimentoAdapter
        }

        lista_reabastecimento_recyclerview.visibility = View.INVISIBLE
        lista_reabastecimento_primeiro_acesso.visibility = View.INVISIBLE

        listaReabastecimentoAdapter.onItemClickListener = {
            itemSelecionado ->
            Log.i("ITEM","Clicado no item selecionado ${itemSelecionado}")
            //funcao responsavel por direcionar o clique do cliente para a tela de cadastr/edicao
            HomeFragmentDirections.actionListaReabastecimentoFragmentToCadastroFragment(itemSelecionado.id).run {
                controlador.navigate(this)
            }
        }

        //busco todos os itens da base
//        viewModel.todas.observe(this, Observer(listaReabastecimentoAdapter::submitList))

        viewModel.todas.observe(this, Observer {
            if(it.size == 0){
                lista_reabastecimento_recyclerview.visibility = View.INVISIBLE
                lista_reabastecimento_primeiro_acesso.visibility = View.VISIBLE
            }else{
                lista_reabastecimento_recyclerview.visibility = View.VISIBLE
                lista_reabastecimento_primeiro_acesso.visibility = View.INVISIBLE
            }
            listaReabastecimentoAdapter.submitList(it)
        })
    }
}