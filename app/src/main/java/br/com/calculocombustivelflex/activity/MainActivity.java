package br.com.calculocombustivelflex.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;

import java.lang.reflect.Array;

import br.com.calculocombustivelflex.R;
import br.com.calculocombustivelflex.fragment.AdicionaItemFragment;
import br.com.calculocombustivelflex.fragment.ListaCadastradaFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adiciona);

        trocaFragment(new ListaCadastradaFragment());

    }

    private void trocaFragment(Fragment fragment) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.adiciona_activity, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }


}
