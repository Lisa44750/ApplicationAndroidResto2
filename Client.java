package com.example.appmysql;

public class Client {
    private String nomCli;
    private String prenomCli;
    private String telCli;

    public Client(String nomCli, String prenomCli, String telCli) {
        this.nomCli = nomCli;
        this.prenomCli = prenomCli;
        this.telCli = telCli;
    }

    public String getNomCli() {
        return nomCli;
    }

    public void setNomCli(String nomCli) {
        this.nomCli = nomCli;
    }

    public String getPrenomCli() {
        return prenomCli;
    }

    public void setPrenomCli(String prenomCli) {
        this.prenomCli = prenomCli;
    }

    public String getTelCli() {
        return telCli;
    }

    public void setTelCli(String telCli) {
        this.telCli = telCli;
    }

    @Override
    public String toString() {
        return nomCli + " , " + prenomCli +" , " + telCli;
    }
}
