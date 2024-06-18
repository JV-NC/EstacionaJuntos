package br.com.jvn.estacionajuntos.model;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Cadastro implements Parcelable {
    private int id;
    private String nome;
    private String email;
    private String senhaMD5;
    private boolean newsletterAgree;

    public Cadastro() {
        id = -1;
        newsletterAgree = false;
    }

    public Cadastro(String nome, String email, String senhaMD5, boolean newsletterAgree) {
        setNome(nome);
        setEmail(email);
        setSenhaMD5(senhaMD5);
        setNewsletterAgree(newsletterAgree);
    }

    private Cadastro(Parcel in){
        id = in.readInt();
        nome = in.readString();
        email = in.readString();
        senhaMD5 = in.readString();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            newsletterAgree = in.readBoolean();
        }else{
            newsletterAgree = in.readInt() != 0;
        }
    }

    public static final Creator<Cadastro> CREATOR = new Creator<Cadastro>() {
        @Override
        public Cadastro createFromParcel(Parcel in) {
            return new Cadastro(in);
        }

        @Override
        public Cadastro[] newArray(int size) {
            return new Cadastro[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenhaMD5() {
        return senhaMD5;
    }

    public void setSenhaMD5(String senhaMD5) {
        this.senhaMD5 = senhaMD5;
    }

    public boolean isNewsletterAgree() {
        return newsletterAgree;
    }

    public void setNewsletterAgree(boolean newsletterAgree) {
        this.newsletterAgree = newsletterAgree;
    }

    @NonNull
    @Override
    public String toString() {
        return "Nome: "+nome+", Email: "+email+", SenhaMD5: "+senhaMD5+", Newsletter: "+newsletterAgree;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(nome);
        dest.writeString(email);
        dest.writeString(senhaMD5);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            dest.writeBoolean(newsletterAgree);
        }
        else{
            if(newsletterAgree){
                dest.writeInt(1);
            }
            else{
                dest.writeInt(0);
            }
        }
    }
}
