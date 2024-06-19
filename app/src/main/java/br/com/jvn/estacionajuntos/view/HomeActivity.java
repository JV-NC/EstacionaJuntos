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
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import br.com.jvn.estacionajuntos.FilterFragment;
import br.com.jvn.estacionajuntos.OrderFragment;
import br.com.jvn.estacionajuntos.R;
import br.com.jvn.estacionajuntos.interfaces.DialogFragmentAdapter;
import br.com.jvn.estacionajuntos.model.Cadastro;
import br.com.jvn.estacionajuntos.model.Lugar;
import br.com.jvn.estacionajuntos.model.LugarAdapter;

public class HomeActivity extends AppCompatActivity implements DialogFragmentAdapter {
    Toolbar toolbar;
    SearchView searchHome;
    Button btnOrdenarHome, btnFiltrarHome;
    RecyclerView recyclerHome;
    LugarAdapter lugarAdapter;
    int ordem, filtro;
    ArrayList<Lugar> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        setOrdemFilter();

        Cadastro login = getIntent().getParcelableExtra("Cadastro");
        Log.i("login", login != null ? login.toString() : "null");

        list = fakePlaces();

        lugarAdapter = new LugarAdapter(list);

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
        recyclerHome = findViewById(R.id.recyclerHome);
        recyclerHome.setAdapter(lugarAdapter);

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

    private ArrayList<Lugar> fakePlaces() { //gambiarra
        ArrayList<Lugar> lista = new ArrayList<>();
        for(int i=0;i<10;i++){
            Lugar aux = new Lugar();
            aux.setNome("Estacionamento "+(i+1));
            aux.setEndereco("Endereço "+(i+1));
            aux.setTelefone("9"+(i+2)+"3"+"5"+(i+1)+"0"+i);
            aux.setDescricao("Descrição "+(10-i));
            aux.setRating((i%2==0) ? (double) i+1 : ((double) 1 /(i+1)));
            aux.setOpen(i%2==0);
            aux.setIs24H(i%2!=0);
            aux.setEspacoAberto(i%3==0);
            aux.setDistance((Math.pow(i,2)));
            aux.setPrecoCarro(Math.pow(i,3));
            aux.setPrecoMoto(Math.pow(i,3));
            aux.setMensalidadeCarro(Math.sqrt(i+1000));
            aux.setMensalidadeMoto(Math.sqrt(i+500));

            lista.add(aux);
        }

        return lista;
    }
}