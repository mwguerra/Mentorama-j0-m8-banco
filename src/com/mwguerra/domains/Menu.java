package com.mwguerra.domains;

import com.mwguerra.utils.ConsoleHelper;
import com.mwguerra.utils.StringHelper;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Menu {
  public static int main() {
    ConsoleHelper.clear();
    System.out.println("----------------------------------------");
    System.out.println("-- OMEGA BANK --------------------------");
    System.out.println("----------------------------------------");
    System.out.println("-- 1. Menu do gerente");
    System.out.println("-- 2. Menu do cliente");
    System.out.println("-- 0. Sair");
    System.out.println("--");

    return securePrompt(Arrays.asList(0, 1, 2));
  }

  public static String customerSSN() {
    Scanner scanner = new Scanner(System.in);
    System.out.print("-- Digite o CPF do cliente: ");
    String input = scanner.nextLine();
    return StringHelper.cpfFormatter(input);
  }

  public static Integer destinationAccount() {
    Scanner scanner = new Scanner(System.in);
    System.out.println("-- Conta de destino --------------------");
    System.out.println("-- 1. Mesma titularidade");
    System.out.println("-- 2. Outra pessoa");
    System.out.println("-- 0. Sair");

    int option = securePrompt(Arrays.asList(0, 1, 2));

    if (option == 0) {
      return null;
    }

    System.out.print("-- Digite o número da conta: ");
    return scanner.nextInt();
  }

  public static int customer(String ownerSSN) {
    ConsoleHelper.clear();
    System.out.println("----------------------------------------");
    System.out.println("-- OMEGA BANK -------- " + ownerSSN + " --");
    System.out.println("----------------------------------------");
    System.out.println("-- 1. Saque");
    System.out.println("-- 2. Depósito");
    System.out.println("-- 3. Transferência");
    System.out.println("-- 4. Extrato");
    System.out.println("-- 5. Saldo");
    System.out.println("-- 0. Voltar ao menu inicial");
    System.out.println("--");

    return securePrompt(Arrays.asList(0, 1, 2, 3, 4, 5));
  }

  public static int customerAccountType(String title, boolean[] hasAccounts) {
    Scanner scanner = new Scanner(System.in);
    int option;
    boolean validOption;

    do {
      System.out.println("-- " + title.toUpperCase() + " -----------------------------");

      if(hasAccounts[0]) {
        System.out.println("-- 1. Conta Salário");
      } else {
        System.out.println("-- X. CONTA SALÁRIO INEXISTENTE");
      }

      if(hasAccounts[1]) {
        System.out.println("-- 2. Conta Corrente");
      } else {
        System.out.println("-- X. CONTA CORRENTE INEXISTENTE");
      }

      if(hasAccounts[2]) {
        System.out.println("-- 3. Conta Poupança");
      } else {
        System.out.println("-- X. CONTA POUPANÇA INEXISTENTE");
      }

      System.out.print("-- Selecione o tipo de conta: ");
      option = scanner.nextInt();
      validOption = Arrays.asList(1, 2, 3).contains(option);

      if (!validOption) {
        System.out.println("Opção " + option + " inválida.");
      }
    } while(!validOption);

    return option;
  }

  public static int customerAccountType(String title) {
    return customerAccountType(title, new boolean[]{true, true, true});
  }

  public static int manager() {
    ConsoleHelper.clear();
    System.out.println("----------------------------------------");
    System.out.println("-- OMEGA BANK --------------- GERENTE --");
    System.out.println("----------------------------------------");
    System.out.println("-- 1. Criar conta");
    System.out.println("-- 2. Mostrar montante nas contas");
    System.out.println("-- 3. Mostrar montante por tipo de conta");
    System.out.println("-- 0. Voltar ao menu inicial");
    System.out.println("--");

    return securePrompt(Arrays.asList(0, 1, 2, 3));
  }

  private static int securePrompt(List<Integer> options) {
    Scanner scanner = new Scanner(System.in);
    int option;
    boolean validOption;

    do {
      System.out.print("-- Digite a opção desejada: ");
      option = scanner.nextInt();
      validOption = options.contains(option);

      if (!validOption) {
        System.out.println("Opção " + option + " inválida.");
      }
    } while(!validOption);

    return option;
  }
}
