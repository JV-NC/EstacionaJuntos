package br.com.jvn.estacionajuntos.controller;

import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;

import br.com.jvn.estacionajuntos.database.DataBase;
import br.com.jvn.estacionajuntos.model.Cadastro;

public class ControllerCadastro {
    public ControllerCadastro() {
        Log.d("MVC_Controller","ControllerCadastro iniciada!");
    }

    public void salvar(Cadastro cadastro, DataBase db){
        String sql = "INSERT INTO Cadastro " +
                "(nome, email, senha, newsletter) " +
                "VALUES ('" +
                cadastro.getNome() + "', '" +
                cadastro.getEmail() + "', '" +
                cadastro.getSenhaMD5() + "', '";
        if(cadastro.isNewsletterAgree()){
            sql+= "1')";
        } else{
            sql+= "0')";
        }
        db.runSQL(sql);
    }

    public void limpar(DataBase db){
        db.runSQL("DELETE FROM Cadastro");
    }

    public ArrayList<Cadastro> getListaDados(DataBase db,String order){
        ArrayList<Cadastro> list = new ArrayList<>();

        Cadastro registro;
        String sql  = "SELECT * FROM Cadastro order by "+order;

        Cursor cursor = db.abreRAWSQL(sql);

        if(cursor.getCount()>0){
            if (cursor.moveToFirst()){
                do{
                    registro = new Cadastro();
                    registro.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));

                    registro.setNome(cursor.getString(cursor.getColumnIndexOrThrow("nome")));
                    registro.setEmail(cursor.getString(cursor.getColumnIndexOrThrow("email")));
                    registro.setSenhaMD5(cursor.getString(cursor.getColumnIndexOrThrow("senha")));
                    int i = cursor.getInt(cursor.getColumnIndexOrThrow("newsletter"));
                    registro.setNewsletterAgree(i == 1);

                    list.add(registro);
                }while(cursor.moveToNext());
            }
        }

        return list;
    }

    public Cadastro getItemByEmail(String email, DataBase db){
        String sql = "SELECT * FROM Cadastro WHERE email LIKE '%"+ email + "%'";

        Cadastro cadastro = new Cadastro();

        Cursor cursor = db.abreRAWSQL(sql);
        if(cursor.moveToFirst()){
            cadastro.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
            cadastro.setNome(cursor.getString(cursor.getColumnIndexOrThrow("nome")));
            cadastro.setEmail(cursor.getString(cursor.getColumnIndexOrThrow("email")));
            cadastro.setSenhaMD5(cursor.getString(cursor.getColumnIndexOrThrow("senha")));
            cadastro.setNewsletterAgree(cursor.getInt(cursor.getColumnIndexOrThrow("newsletter")) == 1);

            return cadastro;
        }
        return null;
    }

    public void alterar(Cadastro cadastro, DataBase db){
        String sql = "UPDATE Cadastro " +
                "SET nome = '"+ cadastro.getNome() + "', " +
                "email = '"+ cadastro.getEmail() +"', "+
                "senha = '"+ cadastro.getSenhaMD5() +"', ";

        if(cadastro.isNewsletterAgree()){
            sql+="newsletter = '1' ";
        }
        else{
            sql+="newsletter = '0' ";
        }
        sql += "WHERE id = '"+cadastro.getId()+"'";

        db.runSQL(sql);
    }

    public void deletar(int id, DataBase db){
        String sql = "DELETE FROM Cadastro WHERE id = '"+id+"'";

        db.runSQL(sql);
    }

    public int count(DataBase db){
        String sql = "SELECT * FROM Cadastro";
        Cursor cursor = db.abreRAWSQL(sql);

        int count = cursor.getCount();
        cursor.close();
        return count;
    }
}
