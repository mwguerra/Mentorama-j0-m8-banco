package com.mwguerra.domains;

import com.mwguerra.database.Database;
import com.mwguerra.enums.OperationType;
import com.mwguerra.models.account.Account;
import com.mwguerra.models.account.CheckingAccount;
import com.mwguerra.models.account.PaycheckAccount;
import com.mwguerra.models.account.SavingsAccount;
import com.mwguerra.models.transactions.History;

import java.util.Arrays;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Scanner;

public class Customer {
  String ssn = null;
  private final CheckingAccount checkingAccounts = CheckingAccount.database();
  private final PaycheckAccount paycheckAccounts = PaycheckAccount.database();
  private final SavingsAccount savingsAccounts = SavingsAccount.database();
  private final History history = History.database();
  private CheckingAccount checkingAccount = null;
  private PaycheckAccount paycheckAccount = null;
  private SavingsAccount savingsAccount = null;

  public void main(Database db) {
    int option;

    ssn = Menu.customerSSN();

    if (!loadAccounts(db)) {
      return;
    }

    do {
      option = Menu.customer(ssn);

      switch (option) {
        case 1:
          withdraw();
          break;
        case 2:
          deposit();
          break;
        case 3:
          transfer();
        case 4:
          statement();
          break;
        case 5:
          balance();
          break;
        case 0:
        default:
          break;
      }
    } while (option != 0);
  }

  private boolean loadAccounts(Database db) {
    checkingAccounts.setDatabase(db);
    paycheckAccounts.setDatabase(db);
    savingsAccounts.setDatabase(db);
    history.setDatabase(db);

    checkingAccount = (CheckingAccount) checkingAccounts.findByOwner(ssn);
    paycheckAccount = (PaycheckAccount) paycheckAccounts.findByOwner(ssn);
    savingsAccount = (SavingsAccount) savingsAccounts.findByOwner(ssn);

    if (checkingAccount == null && paycheckAccount == null && savingsAccount == null) {
      System.out.println("Não há um cliente cadastrado sob o CPF " + ssn + ".");
      return false;
    }

    System.out.println(checkingAccount != null ? checkingAccount.toString() : "Não há uma conta corrente para este CPF.");
    System.out.println(paycheckAccount != null ? paycheckAccount.toString() : "Não há uma conta salário para este CPF.");
    System.out.println(savingsAccount != null ? savingsAccount.toString() : "Não há uma conta poupança para este CPF.");

    return true;
  }

  private void balance() {
    System.out.println("");
    System.out.println("----------------------------------------");

    if (paycheckAccount != null) {
      System.out.println("-- Conta Salário -----------------------");
      System.out.println("");
      System.out.println("Saldo: " + paycheckAccount.getBalance());
      System.out.println("");
    }

    if (checkingAccount != null) {
      System.out.println("-- Conta Corrente ----------------------");
      System.out.println("");
      System.out.println("Saldo: " + checkingAccount.getBalance());
      System.out.println("");
    }

    if (savingsAccount != null) {
      System.out.println("-- Conta Poupança ----------------------");
      System.out.println("");
      System.out.println("Saldo: " + savingsAccount.getBalance());
      System.out.println("");
    }
  }

  private void statement() {

    System.out.println("");
    System.out.println("----------------------------------------");

    if (paycheckAccount != null) {
      System.out.println("-- Conta Salário -----------------------");
      System.out.println("");
      System.out.println("Saldo inicial: " + paycheckAccount.getInitialBalance());

      List<History> historyPaycheck = history.allWhereAccountNumber(paycheckAccount.getAccountNumber());

      for (History item : historyPaycheck) {
        System.out.println(item.toString());
      }

      System.out.println("Saldo: " + paycheckAccount.getBalance());
      System.out.println("");
    }

    if (checkingAccount != null) {
      System.out.println("-- Conta Corrente ----------------------");
      System.out.println("");
      System.out.println("Saldo inicial: " + checkingAccount.getInitialBalance());

      List<History> historyChecking = history.allWhereAccountNumber(checkingAccount.getAccountNumber());
      for (History item : historyChecking) {
        System.out.println(item.toString());
      }

      System.out.println("Cheque especial: " + checkingAccount.getOverdraft());
      System.out.println("Saldo: " + checkingAccount.getBalance());
      System.out.println("");
    }

    if (savingsAccount != null) {
      System.out.println("-- Conta Poupança ----------------------");
      System.out.println("");
      System.out.println("Saldo inicial: " + savingsAccount.getInitialBalance());

      List<History> historySavings = history.allWhereAccountNumber(savingsAccount.getAccountNumber());
      for (History item : historySavings) {
        System.out.println(item.toString());
      }

      System.out.println("Saldo: " + savingsAccount.getBalance());
      System.out.println("");
    }
  }

  private void deposit() {
    String description = "Depósito em espécie";

    Double amount = amountPrompt("Valor");
    Account account = selectAccountPrompt("Destino");

    if (account == null) {
      System.out.println("-- Conta inválida");
      return;
    }

    History newRecord = new History(account.getAccountNumber(), description, new GregorianCalendar(), OperationType.DEPOSIT, amount);
    History created = history.create(newRecord);

    account.deposit(amount);
  }

  private Double amountPrompt(String title) {
    System.out.print("-- " + title + ": ");
    Scanner scanner = new Scanner(System.in);
    return scanner.nextDouble();
  }

  private Account selectAccountPrompt(String title) {
    System.out.println("");

    int option = Menu.customerAccountType(title, new boolean[]{
      paycheckAccount != null,
      checkingAccount != null,
      savingsAccount != null
    });

    boolean validOption = Arrays.asList(1, 2, 3).contains(option);

    System.out.println("");

    switch (option) {
      case 1:
        return paycheckAccount;
      case 2:
        return checkingAccount;
      case 3:
        return savingsAccount;
    }

    return null;
  }

  private Double withdraw() {
    String description = "Saque em espécie";

    Account account = selectAccountPrompt("Origem");

    if (account == null) {
      System.out.println("-- Conta inválida");
      return null;
    }

    System.out.println("Saldo disponível: " + account.getBalance());

    Double amount = amountPrompt("Valor para saque");

    if(!account.withdraw(amount)) {
      System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
      System.out.println(">> ALERTA: Operação não realizada. Saldo insuficiente.");
      System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
      return null;
    }

    History newRecord = new History(account.getAccountNumber(), description, new GregorianCalendar(), OperationType.WITHDRAW, amount);
    History created = history.create(newRecord);

    return amount;
  }

  private boolean withdraw(Double amount) {
    String description = "Saque em espécie";

    Account account = selectAccountPrompt("Origem");

    if (account == null) {
      System.out.println("-- Conta inválida");
      return false;
    }

    if(!account.withdraw(amount)) {
      System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
      System.out.println(">> ALERTA: Operação não realizada. Saldo insuficiente.");
      System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
      return false;
    }

    History newRecord = new History(account.getAccountNumber(), description, new GregorianCalendar(), OperationType.WITHDRAW, amount);
    History created = history.create(newRecord);

    return true;
  }

  private void deposit(Integer destinationAccountNumber, Double amount) {
    String description = "Depósito em espécie";

    History newRecord = new History(destinationAccountNumber, description, new GregorianCalendar(), OperationType.DEPOSIT, amount);
    History created = history.create(newRecord);

    Account account = findAccountByNumber(destinationAccountNumber);

    if (account == null) {
      System.out.println("-- Conta inválida");
      return;
    }

    account.deposit(amount);
  }

  private void transfer() {
    Integer destinationAccountNumber = Menu.destinationAccount();

    if (destinationAccountNumber == null) {
      return;
    }

    boolean accountNumberExists = verifyAccountNumber(destinationAccountNumber);

    if (!accountNumberExists) {
      System.out.println("A conta " + destinationAccountNumber + " não existe.");
      return;
    }

    Double amount = withdraw();

    if (amount == null) {
      return;
    }

    deposit(destinationAccountNumber, amount);
  }

  private boolean verifyAccountNumber(Integer destinationAccountNumber) {
    CheckingAccount checking = (CheckingAccount) checkingAccounts.findByAccountNumber(destinationAccountNumber);
    PaycheckAccount paycheck = (PaycheckAccount) paycheckAccounts.findByAccountNumber(destinationAccountNumber);
    SavingsAccount savings = (SavingsAccount) savingsAccounts.findByAccountNumber(destinationAccountNumber);

    return checking != null || paycheck != null || savings != null;
  }

  private Account findAccountByNumber(Integer accountNumber) {
    Account account = null;

    account = checkingAccounts.findByAccountNumber(accountNumber);

    if (account == null) {
      account = paycheckAccounts.findByAccountNumber(accountNumber);
    }

    if (account == null) {
      account = savingsAccounts.findByAccountNumber(accountNumber);
    }

    return account;
  }
}
