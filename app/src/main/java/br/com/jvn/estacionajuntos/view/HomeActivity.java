package br.com.jvn.estacionajuntos.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import br.com.jvn.estacionajuntos.FilterFragment;
import br.com.jvn.estacionajuntos.OrderFragment;
import br.com.jvn.estacionajuntos.R;
import br.com.jvn.estacionajuntos.interfaces.DialogFragmentAdapter;
import br.com.jvn.estacionajuntos.model.Cadastro;

public class HomeActivity extends AppCompatActivity implements DialogFragmentAdapter {
    Toolbar toolbar;
    SearchView searchHome;
    Button btnOrdenarHome, btnFiltrarHome;
    int ordem, filtro;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        setOrdemFilter();

        Cadastro login = getIntent().getParcelableExtra("Cadastro");
        Log.i("login", login != null ? login.toString() : "null");

        setLayout();
    }

    private void setOrdemFilter() {
        ordem =0;
        filtro=-1;
    }

    private void setLayout() {
        toolbar = findViewById(R.id.toolbarHome);
        searchHome = findViewById(R.id.searchHome);
        btnOrdenarHome = findViewById(R.id.btnOrdenarHome);
        btnFiltrarHome = findViewById(R.id.btnFiltrarHome);

        btnOrdenarHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ordenar();
            }
        });

        btnFiltrarHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filtrar();
            }
        });
    }

    private void ordenar() {
        OrderFragment fragment = new OrderFragment();
        fragment.show(getSupportFragmentManager(),"orderFragment");
    }

    private void filtrar() {
        FilterFragment fragment = new FilterFragment();
        fragment.show(getSupportFragmentManager(),"filterFragment");
    }

    @Override
    public void sendInput(int origin, int input) {
        if(origin==0){
            ordem = input;
        } else{
            filtro = input;
        }
        Toast.makeText(this, "Ordem: "+ordem+", Filtro: "+filtro, Toast.LENGTH_SHORT).show();
    }
}