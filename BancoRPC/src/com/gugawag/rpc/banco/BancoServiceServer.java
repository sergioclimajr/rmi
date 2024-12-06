package com.gugawag.rpc.banco;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BancoServiceServer extends UnicastRemoteObject implements BancoServiceIF {

    private List<Conta> contas;

    public BancoServiceServer() throws RemoteException {
        contas = new ArrayList<>();

        contas.add(new Conta("1", 100.00));
        contas.add(new Conta("2", 156.00));
        contas.add(new Conta("3", 950.00));
    }

    @Override
    public double saldo(String numero) throws RemoteException {
        Conta conta = pesquisarContaPorNumero(numero);
        if (conta != null) {
            return conta.getSaldo();
        }
        throw new RemoteException("Conta não encontrada");
    }

    @Override
    public int quantidadeContas() throws RemoteException {
        return contas.size();
    }

    @Override
    public void cadastrarConta(String numero, double saldoInicial) throws RemoteException {
        if (pesquisarContaPorNumero(numero) != null) {
            throw new RemoteException("Conta já existe!");
        }
        contas.add(new Conta(numero, saldoInicial));
    }

    @Override
    public boolean pesquisarConta(String numero) throws RemoteException {
        return pesquisarContaPorNumero(numero) != null;
    }

    @Override
    public boolean removerConta(String numero) throws RemoteException {
        Conta conta = pesquisarContaPorNumero(numero);
        if (conta != null) {
            contas.remove(conta);
            return true;
        }
        return false;
    }

    @Override
    public void adicionarConta(Conta conta) throws RemoteException {
        if (pesquisarContaPorNumero(conta.getNumero()) != null) {
            throw new RemoteException("Conta já existe!");
        }
        contas.add(conta);
    }

    private Conta pesquisarContaPorNumero(String numero) {
        return contas.stream()
                .filter(c -> c.getNumero().equals(numero))
                .findFirst().
                orElse(null);
    }

}
