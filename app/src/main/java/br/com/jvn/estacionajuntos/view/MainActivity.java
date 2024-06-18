package br.com.jvn.estacionajuntos.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import br.com.jvn.estacionajuntos.R;

public class MainActivity extends AppCompatActivity {
    Button btnCadastrar, btnEntrar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //TODO: Find what is data_extraction_rules.xml
        setLayout();
    }

    private void setLayout() {
        btnCadastrar = findViewById(R.id.btnCadastrar);
        btnEntrar = findViewById(R.id.btnEntrar);

        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cadastrar();
            }
        });

        btnEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                entrar();
            }
        });
    }

    private void cadastrar() {
        Intent it = new Intent(MainActivity.this,RegisterActivity.class);
        startActivity(it);
    }

    private void entrar() {
        Intent it = new Intent(MainActivity.this,LoginActivity.class);
        startActivity(it);
    }
}