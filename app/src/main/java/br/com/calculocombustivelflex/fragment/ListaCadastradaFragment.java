package br.com.calculocombustivelflex.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import br.com.calculocombustivelflex.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Pablo on 20/01/2018.
 */

public class ListaCadastradaFragment extends Fragment {

    @BindView(R.id.lista_itens)
    RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_lista_cadastrada, container, false);
        ButterKnife.bind(this, view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        colocaBotaoDeVoltar();
        return view;
    }

    private void colocaBotaoDeVoltar(){
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //informa que o fragment manipula um menu
        setHasOptionsMenu(true);
    }

    @OnClick(R.id.lista_alunos_floating_button)
    public void adicionarItem(){
        
    }


}
