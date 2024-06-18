package br.com.jvn.estacionajuntos.view;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import br.com.jvn.estacionajuntos.R;
import br.com.jvn.estacionajuntos.model.Cadastro;

public class HomeActivity extends AppCompatActivity {
    Toolbar toolbar;
    SearchView searchHome;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Cadastro login = getIntent().getParcelableExtra("Cadastro");
        Log.i("login", login != null ? login.toString() : "null");

        setLayout();
    }

    private void setLayout() {
        toolbar = findViewById(R.id.toolbarHome);
        searchHome = findViewById(R.id.searchHome);
    }
}