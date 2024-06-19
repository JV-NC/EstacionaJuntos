package br.com.jvn.estacionajuntos.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

import br.com.jvn.estacionajuntos.R;
import br.com.jvn.estacionajuntos.model.Lugar;

public class PlaceActivity extends AppCompatActivity {
    TextView lblNomePlace, lblEnderecoPlace, lblRatingPlace, lblOpen, lblCloseTime,lblTelefone, lblDescricao, lblPrecoCarro, lblPrecoMoto, lblMensCarro, lblMensMoto, lblDistancia;
    ImageView imgFotoPlace;
    ImageButton imgVoltar;
    Button btnMostrarMapa;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place);

        Lugar lugar = getIntent().getParcelableExtra("Lugar"); //recebe lugar
        int position = getIntent().getIntExtra("position",-1);
        if(lugar==null){
            Log.e("ParcelableExtra","lugar parsed é nulo");
        }

        setLayout(lugar);
    }

    private void setLayout(Lugar lugar) {
        lblNomePlace = findViewById(R.id.lblNomePlace);
        lblEnderecoPlace = findViewById(R.id.lblEnderecoPlace);
        lblRatingPlace = findViewById(R.id.lblRatingPlace);
        lblOpen = findViewById(R.id.lblOpen);
        lblCloseTime = findViewById(R.id.lblCloseTime);
        lblTelefone = findViewById(R.id.lblTelefone);
        lblDescricao = findViewById(R.id.lblDescricao);
        lblPrecoCarro = findViewById(R.id.lblPrecoCarro);
        lblPrecoMoto = findViewById(R.id.lblPrecoMoto);
        lblMensCarro = findViewById(R.id.lblMensCarro);
        lblMensMoto = findViewById(R.id.lblMensMoto);
        lblDistancia = findViewById(R.id.lblDistancia);
        imgFotoPlace = findViewById(R.id.imgFotoPlace);
        imgVoltar = findViewById(R.id.imgVoltar);
        btnMostrarMapa = findViewById(R.id.btnMostrarMapa);

        lblNomePlace.setText(lugar.getNome());
        lblEnderecoPlace.setText(lugar.getEndereco());
        DecimalFormat df = new DecimalFormat("#");
        DecimalFormat star = new DecimalFormat("0.0");
        DecimalFormat money = new DecimalFormat("0.00");
        lblRatingPlace.setText(star.format(lugar.getRating()));
        if(lugar.isOpen()){
            lblOpen.setText("Aberto");
            lblOpen.setTextColor(getColor(R.color.Green));
        } else{
            lblOpen.setText("Fechado");
            lblOpen.setTextColor(getColor(R.color.darkRed));
        }
        lblCloseTime.setText(lugar.getOpenCloseTime());
        lblTelefone.setText(lugar.getTelefone());
        lblDescricao.setText(lugar.getDescricao());
        lblDistancia.setText(df.format(lugar.getDistance())+"m");
        lblPrecoCarro.setText("R$"+money.format(lugar.getPrecoCarro())+" : 1 hora");
        lblPrecoMoto.setText("R$"+money.format(lugar.getPrecoMoto())+" : 1 hora");
        lblMensCarro.setText("R$"+money.format(lugar.getMensalidadeCarro())+" : mês");
        lblMensMoto.setText("R$"+money.format(lugar.getMensalidadeMoto())+" : mês");
        if(lugar.isEspacoAberto()){ //gambiarra
            imgFotoPlace.setImageResource(R.drawable.estacionamento2);
        } else if(lugar.isIs24H()){
            imgFotoPlace.setImageResource(R.drawable.estacionamento3);
        }

        imgVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        
        btnMostrarMapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(PlaceActivity.this, "Mostra o mapa", Toast.LENGTH_SHORT).show();
            }
        });
    }
}