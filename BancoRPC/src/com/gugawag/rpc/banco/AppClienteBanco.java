package com.gugawag.rpc.banco;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class AppClienteBanco {

    public static void main(String[] args) throws RemoteException, NotBoundException, MalformedURLException {
        // procura o serviço no RMI Registry local. Perceba que o cliente não connhece a implementação do servidor,
        // apenas a interface

        if (args.length < 1) {
            System.out.println("Erro: Informe o IP do servidor como argumento.");
            return;
        }

        String ipServidor = args[0];
        System.out.println("Conectando ao servidor RMI no IP: " + ipServidor);

        Registry registry = LocateRegistry.getRegistry(ipServidor);
        BancoServiceIF banco = (BancoServiceIF) registry.lookup("BancoService");

        menu();
        Scanner entrada = new Scanner(System.in);
        int opcao = entrada.nextInt();

        while(opcao != 9) {
            switch (opcao) {
                case 1: {
                    System.out.println("Digite o número da conta:");
                    String conta = entrada.next();
                    //chamada ao metodo remoto, como se fosse executar localmente
                    System.out.println(banco.saldo(conta));
                    break;
                }
                case 2: {
                    //chamada ao metodo remoto, como se fosse executar localmente
                    System.out.println(banco.quantidadeContas());
                    break;
                }
                case 3: {
                    System.out.println("Digite o número da nova conta: ");
                    String conta = entrada.next();
                    System.out.println("Digite o saldo inicial: ");
                    double saldo = entrada.nextDouble();

                    try {
                        banco.adicionarConta(new Conta (conta, saldo));
                        System.out.println("Conta cadastrada com sucesso!");
                    } catch (RemoteException e) {
                        System.out.println("Erro: " + e.getMessage());
                    }
                    break;
                }
                case 4: {
                    System.out.println("Digite o número da conta para pesquisar: ");
                    String conta = entrada.next();
                    boolean existe = banco.pesquisarConta(conta);
                    System.out.println(existe ? "Conta encontrada!" : "Conta não encontrada.");
                    break;
                }
                case 5: {
                    System.out.println("Digite o número da conta para remover: ");
                    String conta = entrada.next();
                    boolean removida = banco.removerConta(conta);
                    System.out.println(removida ? "Conta removida com sucesso!" : "Conta não encontrada.");
                    break;
                }
                default:
                    System.out.println("Opção inválida!");
            }
            menu();
            opcao = entrada.nextInt();
        }
    }

    public static void menu() {
        System.out.println("\n=== BANCO RMI (ou FMI?!) ===");
        System.out.println("\n=== USUÁRIO: Clovis Sergio Correia Lima Junior ===");
        System.out.println("1 - Saldo da conta");
        System.out.println("2 - Quantidade de contas");
        System.out.println("3 - Cadastrar nova conta");
        System.out.println("4 - Pesquisar conta");
        System.out.println("5 - Remover conta");
        System.out.println("9 - Sair");
    }

}
