package br.itcampos.mycepapplication.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ViaCepResponse {
    @SerializedName("cep")
    @Expose
    private String cep;

    @SerializedName("logradouro")
    private String address;

    @SerializedName("complemento")
    private String complement;

    @SerializedName("bairro")
    private String neighborhood;

    @SerializedName("localidade")
    private String city;

    @SerializedName("uf")
    private String state;

    private String source;

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getComplement() {
        return complement;
    }

    public void setComplement(String complement) {
        this.complement = complement;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "cep:" + getCep() +
                "\nlogradouro:" + getAddress()+
                "\ncomplemento:" + getComplement()+
                "\nbairro:" + getNeighborhood()+
                "\nlocalidade:" + getCity()+
                "\nuf:" + getState();
    }
}
