package br.com.jvn.estacionajuntos.model;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Lugar implements Parcelable {
    private String nome;
    private String endereco;
    private String telefone;
    private String descricao;
    private double rating;
    private boolean isOpen;
    private boolean is24H;
    private boolean isEspacoAberto;
    private String OpenCloseTime;
    private double distance;
    private double precoCarro;
    private double precoMoto;
    private double mensalidadeCarro;
    private double mensalidadeMoto;

    public Lugar() {
        setOpen(false);
        setIs24H(false);
        setEspacoAberto(false);
    }

    protected Lugar(Parcel in) {
        nome = in.readString();
        endereco = in.readString();
        telefone = in.readString();
        descricao = in.readString();
        rating = in.readDouble();
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.Q){
            isOpen = in.readBoolean();
            is24H = in.readBoolean();
            isEspacoAberto = in.readBoolean();
        } else{
            isOpen = in.readInt() !=0;
            is24H = in.readInt() !=0;
            isEspacoAberto = in.readInt() !=0;
        }

        OpenCloseTime = in.readString();
        distance = in.readDouble();
        precoCarro = in.readDouble();
        precoMoto = in.readDouble();
        mensalidadeCarro = in.readDouble();
        mensalidadeMoto = in.readDouble();
    }

    public static final Creator<Lugar> CREATOR = new Creator<Lugar>() {
        @Override
        public Lugar createFromParcel(Parcel in) {
            return new Lugar(in);
        }

        @Override
        public Lugar[] newArray(int size) {
            return new Lugar[size];
        }
    };

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = (rating<0.00 || rating>5.0) ? rating : 0.1;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }

    public boolean isIs24H() {
        return is24H;
    }

    public void setIs24H(boolean is24H) {
        this.is24H = is24H;
    }

    public boolean isEspacoAberto() {
        return isEspacoAberto;
    }

    public void setEspacoAberto(boolean espacoAberto) {
        isEspacoAberto = espacoAberto;
    }

    public String getOpenCloseTime() {
        return OpenCloseTime;
    }

    public void setOpenCloseTime(String openCloseTime) {
        OpenCloseTime = openCloseTime;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public double getPrecoCarro() {
        return precoCarro;
    }

    public void setPrecoCarro(double precoCarro) {
        this.precoCarro = precoCarro;
    }

    public double getPrecoMoto() {
        return precoMoto;
    }

    public void setPrecoMoto(double precoMoto) {
        this.precoMoto = precoMoto;
    }

    public double getMensalidadeCarro() {
        return mensalidadeCarro;
    }

    public void setMensalidadeCarro(double mensalidadeCarro) {
        this.mensalidadeCarro = mensalidadeCarro;
    }

    public double getMensalidadeMoto() {
        return mensalidadeMoto;
    }

    public void setMensalidadeMoto(double mensalidadeMoto) {
        this.mensalidadeMoto = mensalidadeMoto;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(nome);
        dest.writeString(endereco);
        dest.writeString(telefone);
        dest.writeString(descricao);
        dest.writeDouble(rating);
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.Q){
            dest.writeBoolean(isOpen);
            dest.writeBoolean(is24H);
            dest.writeBoolean(isEspacoAberto);
        }else{
            if(isOpen){
                dest.writeInt(1);
            } else{
                dest.writeInt(0);
            }
            if(is24H) {
                dest.writeInt(1);
            } else{
                dest.writeInt(0);
            }
            if(isEspacoAberto){
                dest.writeInt(1);
            } else{
                dest.writeInt(0);
            }
        }
        dest.writeDouble(distance);
        dest.writeString(OpenCloseTime);
        dest.writeDouble(precoCarro);
        dest.writeDouble(precoMoto);
        dest.writeDouble(mensalidadeCarro);
        dest.writeDouble(mensalidadeMoto);
    }
}