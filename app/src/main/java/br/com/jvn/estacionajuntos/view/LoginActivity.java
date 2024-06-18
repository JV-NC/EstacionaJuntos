package br.com.jvn.estacionajuntos.view;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import br.com.jvn.estacionajuntos.R;
import br.com.jvn.estacionajuntos.controller.ControllerCadastro;
import br.com.jvn.estacionajuntos.database.DataBase;
import br.com.jvn.estacionajuntos.model.Cadastro;
import br.com.jvn.estacionajuntos.utils.Utility;

public class LoginActivity extends AppCompatActivity {
    EditText tfEmail, tfSenha;
    TextView lblEsqueceuSenha;
    Button btnEntrar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setLayout();
    }

    private void setLayout() {
        tfEmail = findViewById(R.id.tfEmailLogin);
        tfSenha = findViewById(R.id.tfSenhaLogin);
        btnEntrar = findViewById(R.id.btnEntrarLogin);
        lblEsqueceuSenha = findViewById(R.id.lblEsqueceuSenha);

        btnEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        lblEsqueceuSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                esqueceuSenha();
            }
        });
    }

    private void login(){
        if (checkFields()){
            ControllerCadastro controller = new ControllerCadastro();
            DataBase db = new DataBase(LoginActivity.this);

            Cadastro cadastro = controller.getItemByEmail(tfEmail.getText().toString(),db);

            if(cadastro!=null){
                if(cadastro.getSenhaMD5().compareTo(Utility.convertMD5(tfSenha.getText().toString()))==0){
                    goToHome(cadastro);
                } else{
                    Toast.makeText(LoginActivity.this, "Email ou senha incorretos!", Toast.LENGTH_SHORT).show();
                    cadastro = null;
                }
            }
            else{
                Toast.makeText(LoginActivity.this, "Email ou senha incorretos!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private boolean checkFields(){
        boolean check = true;
        if(TextUtils.isEmpty(tfEmail.getText())){
            tfEmail.setError("Obrigatório");
            check = false;
        } else if(!Utility.isValidEmail(tfEmail.getText())){
            tfEmail.setError("Email inválido");
            check = false;
        }

        if(TextUtils.isEmpty(tfSenha.getText())){
            tfSenha.setError("Obrigatório");
            check = false;
        }

        return check;
    }

    private void esqueceuSenha() {
        Toast.makeText(LoginActivity.this, "Esqueceu a Senha", Toast.LENGTH_SHORT).show();
    }

    private void goToHome(Cadastro cadastro){
        Intent it = new Intent(LoginActivity.this,HomeActivity.class);
        it.putExtra("Cadastro",cadastro);
        startActivity(it);
        finish();
    }
}