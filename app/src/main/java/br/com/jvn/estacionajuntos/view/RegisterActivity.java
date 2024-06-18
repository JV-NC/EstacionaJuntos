package br.com.jvn.estacionajuntos.view;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
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

public class RegisterActivity extends AppCompatActivity {
    EditText tfNome,tfEmail,tfSenha;
    CheckBox cbNewsletter;
    Button btnCadastrar;

    ControllerCadastro controller;
    DataBase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        controller = new ControllerCadastro();
        db = new DataBase(RegisterActivity.this);

        setLayout();
    }

    private void setLayout() {
        tfNome = findViewById(R.id.tfNome);
        tfEmail = findViewById(R.id.tfEmail);
        tfSenha = findViewById(R.id.tfSenha);
        cbNewsletter = findViewById(R.id.cbNewsletter);
        btnCadastrar = findViewById(R.id.btnCadastrarRegister);

        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cadastrar();
            }
        });
    }

    private void cadastrar(){
        if(checkFields()){
            String MD5 = Utility.convertMD5(tfSenha.getText().toString());
            Cadastro cadastro = new Cadastro(tfNome.getText().toString(),tfEmail.getText().toString(),MD5,cbNewsletter.isChecked());

            controller.salvar(cadastro,db);

            cleanFields();

            Toast.makeText(RegisterActivity.this, "Cadastro feito com sucesso!", Toast.LENGTH_SHORT).show();

            Intent it = new Intent(RegisterActivity.this,LoginActivity.class);
            startActivity(it);
            finish();
        }
        else{
            Toast.makeText(RegisterActivity.this, "Falha ao realizar o cadastro!", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean checkFields(){
        boolean check = true;
        if(TextUtils.isEmpty(tfNome.getText())){
            tfNome.setError("Obrigatório");
            check = false;
        }

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
        } else if(tfSenha.getText().toString().length()<6 || !Utility.isValidPassword(tfSenha.getText().toString())){
            tfSenha.setError("Senha inválida");
            check = false;
        }
        return check;
    }

    private void cleanFields(){
        tfNome.setText("");
        tfEmail.setText("");
        tfSenha.setText("");
        cbNewsletter.setChecked(false);
    }
}