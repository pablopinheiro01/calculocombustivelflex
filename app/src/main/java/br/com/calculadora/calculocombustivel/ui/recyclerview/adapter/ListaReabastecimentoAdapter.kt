package br.com.calculadora.calculocombustivel.ui.recyclerview.adapter


import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import br.com.calculadora.calculocombustivel.databinding.ItemCadastradoBinding
import br.com.calculadora.calculocombustivel.model.Reabastecimento
import br.com.calculadora.calculocombustivel.ui.databinding.ReabastecimentoData
import br.com.calculadora.calculocombustivel.ui.viewmodel.ListaReabastecimentoViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class ListaReabastecimentoAdapter(
    private val context: Context,
    private val viewModel: ListaReabastecimentoViewModel,
    var onItemClickListener: (itemSelecionado: Reabastecimento) -> Unit = {}
) : ListAdapter<Reabastecimento, ListaReabastecimentoAdapter.ViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(context)
        val viewDataBinding = ItemCadastradoBinding.inflate(inflater, parent, false)
        return ViewHolder(viewDataBinding).also {
            viewDataBinding.lifecycleOwner = it
        }

        return ViewHolder(viewDataBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let {item ->
            Log.i("ITEM","Vinculando o item: ${item}")
            holder.vincula(item)
        }
    }

    //a partir da sobreescrita deste metodo do adapter vamos conseguir identificar onde se encontra a view no momento da execução
    override fun onViewAttachedToWindow(holder: ViewHolder) {
        super.onViewAttachedToWindow(holder)
        //trabalharemos com o lifecycle para tratar o uso dentro do adapter
        holder.marcaComoAtivo()
    }

    override fun onViewDetachedFromWindow(holder: ViewHolder) {
        super.onViewDetachedFromWindow(holder)
        holder.marcaComoDesativado()
    }


    inner class ViewHolder(private val viewDataBinding: ItemCadastradoBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root), View.OnClickListener, LifecycleOwner{

        private val registry = LifecycleRegistry(this)

        override fun getLifecycle(): Lifecycle = registry

        init {
            registry.markState(Lifecycle.State.INITIALIZED)
        }

        fun marcaComoAtivo() {
            registry.markState(Lifecycle.State.STARTED)
        }

        fun marcaComoDesativado() {
            registry.markState(Lifecycle.State.DESTROYED)
        }

        private lateinit var item: Reabastecimento

        init {
            viewDataBinding.clicaNoItem = this
        }

        fun vincula(reabastecimento: Reabastecimento) {
            this.item = reabastecimento
            val reabastecimentoData: ReabastecimentoData = ReabastecimentoData(reabastecimento)
            viewDataBinding.item = reabastecimentoData
            Log.i("ITEM", "item do this: ${this.item}")
            Log.i("ITEM", "item vinculado: ${viewDataBinding.item}")
            Log.i("ITEM", "item recebido e vinculado: ${reabastecimentoData}")

            viewDataBinding.itemCadastradoBotaoExcluir.setOnClickListener {
                Log.i("ITEM-EXCLUI","Excluindo a parada de id: ${item.id}")
                viewModel.exclui(item)
            }
        }


        override fun onClick(v: View?) {
            Log.i("ITEM", "Vamos verificar se o item foi clicado")
            Log.i("ITEM", "viewDataBinding clicado: ${viewDataBinding.clicaNoItem}")
            Log.i("ITEM", "item vinculado: ${viewDataBinding.item}")
            Log.i("ITEM", "view recebida : ${v}")

            if (::item.isInitialized) {
                Log.i("ITEM", "Foi inicializado o item ${item}")
                Toast.makeText(context, "clicou no texto", Toast.LENGTH_LONG).show()
                onItemClickListener(item)
            } else {
                Log.i("ITEM", "Não Foi inicializado o item")
            }
        }

         fun pegaItemDaView(): Reabastecimento {
            return this.item
        }
    }

}

object DiffCallback : DiffUtil.ItemCallback<Reabastecimento>() {
    override fun areItemsTheSame(
        oldItem: Reabastecimento,
        newItem: Reabastecimento
    ) = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Reabastecimento, newItem: Reabastecimento) =
        oldItem == newItem

}