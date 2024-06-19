package br.com.jvn.estacionajuntos.model;

public class Lugar {
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
}