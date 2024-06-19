package br.com.jvn.estacionajuntos.view;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

import br.com.jvn.estacionajuntos.R;
import br.com.jvn.estacionajuntos.interfaces.DialogFragmentAdapter;
import br.com.jvn.estacionajuntos.interfaces.LugarAdapterListener;
import br.com.jvn.estacionajuntos.model.Cadastro;
import br.com.jvn.estacionajuntos.model.Lugar;
import br.com.jvn.estacionajuntos.model.LugarAdapter;

public class HomeActivity extends AppCompatActivity implements DialogFragmentAdapter {
    ImageButton imgMenu, imgConta;
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

        //Requerir permissões, se necessário
        if(!isAllPermissionsGranted()){
            requestAllPermissions();
        } else{
            Log.i("Permissions","Todas as permissões concedidas!");
        }

        list = fakePlaces();

        lugarAdapter = new LugarAdapter(list, new LugarAdapterListener() {
            @Override
            public void onItemClick(int position) {
                Intent it = new Intent(HomeActivity.this, PlaceActivity.class);
                it.putExtra("Lugar",lugarAdapter.getLugares().get(position));
                it.putExtra("position",position);

                startActivity(it);
            }
        });

        setLayout();
    }

    private boolean isAllPermissionsGranted() {
        return ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.INTERNET
        ) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_NETWORK_STATE
        ) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestAllPermissions(){
        ActivityCompat.requestPermissions(this,new String[]{
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.INTERNET,
                Manifest.permission.ACCESS_NETWORK_STATE,
        },1);
    }

    private void setOrdemFilter() {
        ordem =0;
        filtro=-1;
    }

    private void setLayout() {
        imgMenu = findViewById(R.id.imgMenu);
        imgConta = findViewById(R.id.imgConta);
        searchHome = findViewById(R.id.searchHome);
        btnOrdenarHome = findViewById(R.id.btnOrdenarHome);
        btnFiltrarHome = findViewById(R.id.btnFiltrarHome);
        recyclerHome = findViewById(R.id.recyclerHome);
        recyclerHome.setAdapter(lugarAdapter);

        imgMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(HomeActivity.this, "Abrir menu!", Toast.LENGTH_SHORT).show();
            }
        });

        imgConta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(HomeActivity.this, "Abrir conta!", Toast.LENGTH_SHORT).show();
            }
        });

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
        boolean isOrdemChanged = false, isFilterChanged = false;
        if(origin==0){
            if(ordem!=input){
                isOrdemChanged = true;
            }
            ordem = input;
        } else{
            if(ordem!=input){
                isFilterChanged = true;
            }
            filtro = input;
        }
        Toast.makeText(this, "Ordem: "+ordem+", Filtro: "+filtro, Toast.LENGTH_SHORT).show();

        if(isOrdemChanged){
            Collections.sort(list, new Comparator<Lugar>() {
                @Override
                public int compare(Lugar o1, Lugar o2) {
                    if(ordem==0){
                        return Double.compare(o1.getPrecoCarro(),o2.getPrecoCarro()); //<
                    } else if(ordem==1){
                        return Double.compare(o2.getPrecoCarro(),o1.getPrecoCarro()); //>
                    } else if(ordem==2){
                        return Double.compare(o2.getRating(),o1.getRating()); //>
                    } else{
                        return Double.compare(o1.getDistance(),o2.getDistance());  //<
                    }
                }
            });
            lugarAdapter.notifyDataSetChanged();
        } else if(isFilterChanged){
            if(filtro==-1){
                lugarAdapter.resetLugares(list);
            } else{
                ArrayList<Lugar> filteredList = new ArrayList<>();
                if(filtro==0){
                    for(Lugar lugar : list){
                        if(lugar.isEspacoAberto()){
                            filteredList.add(lugar);
                        }
                    }
                    lugarAdapter.resetLugares(filteredList);
                } else if(filtro==1){
                    for(Lugar lugar : list){
                        if(lugar.isIs24H()){
                            filteredList.add(lugar);
                        }
                    }
                    lugarAdapter.resetLugares(filteredList);
                } else{
                    for(Lugar lugar : list){
                        if(lugar.isOpen()){
                            filteredList.add(lugar);
                        }
                    }
                    lugarAdapter.resetLugares(filteredList);
                }
            }
        }
    }

    private ArrayList<Lugar> fakePlaces() { //gambiarra
        Random random = new Random();
        String[] horarios = {"08:00", "07:00", "24:00", "06:00", "22:00", "21:00", "18:00"};

        ArrayList<Lugar> lista = new ArrayList<>();

        Lugar aux = new Lugar();
        aux.setNome("Estacionamento Mattioli");
        aux.setEndereco("Rua A, 123");
        aux.setTelefone("(11) 1234-5678");
        aux.setDescricao("Coberto e seguro");
        aux.setRating(1.0 + (5.0 - 1.0) * random.nextDouble());
        aux.setOpen(random.nextBoolean());
        aux.setIs24H(random.nextBoolean());
        aux.setEspacoAberto(random.nextBoolean());
        aux.setOpenCloseTime((aux.isOpen()) ? (". Fecha às "+(horarios[random.nextInt(horarios.length)])) : (". Abre às "+(horarios[random.nextInt(horarios.length)])));
        aux.setDistance(1.0 + (200.0 - 1.0) * random.nextDouble());
        aux.setPrecoCarro(5.0 + (50.0 - 5.0) * random.nextDouble());
        aux.setPrecoMoto(2.0 + (20.0 - 2.0) * random.nextDouble());
        aux.setMensalidadeCarro(100.0 + (500.0 - 100.0) * random.nextDouble());
        aux.setMensalidadeMoto(50.0 + (300.0 - 50.0) * random.nextDouble());
        lista.add(aux);

        aux = new Lugar();
        aux.setNome("Estacionamento Central");
        aux.setEndereco("Avenida B, 456");
        aux.setTelefone("(22) 2345-6789");
        aux.setDescricao("Espaçoso");
        aux.setRating(1.0 + (5.0 - 1.0) * random.nextDouble());
        aux.setOpen(random.nextBoolean());
        aux.setIs24H(random.nextBoolean());
        aux.setEspacoAberto(random.nextBoolean());
        aux.setOpenCloseTime((aux.isOpen()) ? (". Fecha às "+(horarios[random.nextInt(horarios.length)])) : (". Abre às "+(horarios[random.nextInt(horarios.length)])));
        aux.setDistance(1.0 + (200.0 - 1.0) * random.nextDouble());
        aux.setPrecoCarro(5.0 + (50.0 - 5.0) * random.nextDouble());
        aux.setPrecoMoto(2.0 + (20.0 - 2.0) * random.nextDouble());
        aux.setMensalidadeCarro(100.0 + (500.0 - 100.0) * random.nextDouble());
        aux.setMensalidadeMoto(50.0 + (300.0 - 50.0) * random.nextDouble());
        lista.add(aux);

        aux = new Lugar();
        aux.setNome("Estacionamento Paraiso");
        aux.setEndereco("Rua C, 789");
        aux.setTelefone("(33) 3456-7890");
        aux.setDescricao("Acesso rápido");
        aux.setRating(1.0 + (5.0 - 1.0) * random.nextDouble());
        aux.setOpen(random.nextBoolean());
        aux.setIs24H(random.nextBoolean());
        aux.setEspacoAberto(random.nextBoolean());
        aux.setOpenCloseTime((aux.isOpen()) ? (". Fecha às "+(horarios[random.nextInt(horarios.length)])) : (". Abre às "+(horarios[random.nextInt(horarios.length)])));
        aux.setDistance(1.0 + (200.0 - 1.0) * random.nextDouble());
        aux.setPrecoCarro(5.0 + (50.0 - 5.0) * random.nextDouble());
        aux.setPrecoMoto(2.0 + (20.0 - 2.0) * random.nextDouble());
        aux.setMensalidadeCarro(100.0 + (500.0 - 100.0) * random.nextDouble());
        aux.setMensalidadeMoto(50.0 + (300.0 - 50.0) * random.nextDouble());
        lista.add(aux);

        aux = new Lugar();
        aux.setNome("Estacionamento Novo");
        aux.setEndereco("Avenida D, 101");
        aux.setTelefone("(44) 4567-8901");
        aux.setDescricao("Tarifas econômicas");
        aux.setRating(1.0 + (5.0 - 1.0) * random.nextDouble());
        aux.setOpen(random.nextBoolean());
        aux.setIs24H(random.nextBoolean());
        aux.setEspacoAberto(random.nextBoolean());
        aux.setOpenCloseTime((aux.isOpen()) ? (". Fecha às "+(horarios[random.nextInt(horarios.length)])) : (". Abre às "+(horarios[random.nextInt(horarios.length)])));
        aux.setDistance(1.0 + (200.0 - 1.0) * random.nextDouble());
        aux.setPrecoCarro(5.0 + (50.0 - 5.0) * random.nextDouble());
        aux.setPrecoMoto(2.0 + (20.0 - 2.0) * random.nextDouble());
        aux.setMensalidadeCarro(100.0 + (500.0 - 100.0) * random.nextDouble());
        aux.setMensalidadeMoto(50.0 + (300.0 - 50.0) * random.nextDouble());
        lista.add(aux);

        aux = new Lugar();
        aux.setNome("Estacionamento Premium");
        aux.setEndereco("Rua E, 202");
        aux.setTelefone("(55) 5678-9012");
        aux.setDescricao("Serviço premium");
        aux.setRating(1.0 + (5.0 - 1.0) * random.nextDouble());
        aux.setOpen(random.nextBoolean());
        aux.setIs24H(random.nextBoolean());
        aux.setEspacoAberto(random.nextBoolean());
        aux.setOpenCloseTime((aux.isOpen()) ? (". Fecha às "+(horarios[random.nextInt(horarios.length)])) : (". Abre às "+(horarios[random.nextInt(horarios.length)])));
        aux.setDistance(1.0 + (200.0 - 1.0) * random.nextDouble());
        aux.setPrecoCarro(5.0 + (50.0 - 5.0) * random.nextDouble());
        aux.setPrecoMoto(2.0 + (20.0 - 2.0) * random.nextDouble());
        aux.setMensalidadeCarro(100.0 + (500.0 - 100.0) * random.nextDouble());
        aux.setMensalidadeMoto(50.0 + (300.0 - 50.0) * random.nextDouble());
        lista.add(aux);

        aux = new Lugar();
        aux.setNome("Estacionamento Rápido");
        aux.setEndereco("Avenida F, 303");
        aux.setTelefone("(66) 6789-0123");
        aux.setDescricao("Funcionamento 24h");
        aux.setRating(1.0 + (5.0 - 1.0) * random.nextDouble());
        aux.setOpen(random.nextBoolean());
        aux.setIs24H(random.nextBoolean());
        aux.setEspacoAberto(random.nextBoolean());
        aux.setOpenCloseTime((aux.isOpen()) ? (". Fecha às "+(horarios[random.nextInt(horarios.length)])) : (". Abre às "+(horarios[random.nextInt(horarios.length)])));
        aux.setDistance(1.0 + (200.0 - 1.0) * random.nextDouble());
        aux.setPrecoCarro(5.0 + (50.0 - 5.0) * random.nextDouble());
        aux.setPrecoMoto(2.0 + (20.0 - 2.0) * random.nextDouble());
        aux.setMensalidadeCarro(100.0 + (500.0 - 100.0) * random.nextDouble());
        aux.setMensalidadeMoto(50.0 + (300.0 - 50.0) * random.nextDouble());
        lista.add(aux);

        aux = new Lugar();
        aux.setNome("Estacionamento Seguro");
        aux.setEndereco("Rua G, 404");
        aux.setTelefone("(77) 7890-1234");
        aux.setDescricao("Próximo ao centro");
        aux.setRating(1.0 + (5.0 - 1.0) * random.nextDouble());
        aux.setOpen(random.nextBoolean());
        aux.setIs24H(random.nextBoolean());
        aux.setEspacoAberto(random.nextBoolean());
        aux.setOpenCloseTime((aux.isOpen()) ? (". Fecha às "+(horarios[random.nextInt(horarios.length)])) : (". Abre às "+(horarios[random.nextInt(horarios.length)])));
        aux.setDistance(1.0 + (200.0 - 1.0) * random.nextDouble());
        aux.setPrecoCarro(5.0 + (50.0 - 5.0) * random.nextDouble());
        aux.setPrecoMoto(2.0 + (20.0 - 2.0) * random.nextDouble());
        aux.setMensalidadeCarro(100.0 + (500.0 - 100.0) * random.nextDouble());
        aux.setMensalidadeMoto(50.0 + (300.0 - 50.0) * random.nextDouble());
        lista.add(aux);

        return lista;
    }
}