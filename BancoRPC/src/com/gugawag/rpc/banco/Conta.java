package com.gugawag.rpc.banco;

public class Conta {

    public String numero;
    private Double saldo;

    public Conta(String numero, Double saldo) {
        this.numero = numero;
        this.saldo = saldo;
    }

    public String getNumero() {
        return numero;
    }

    public double getSaldo() {
        return saldo;
    }

    @Override
    public String toString() {
        return "Conta {"
                + "numero = " + numero +
                ", saldo = " + saldo +
                '}';
    }
}
