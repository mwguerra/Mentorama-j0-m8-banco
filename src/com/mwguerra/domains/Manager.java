package com.mwguerra.domains;

import com.mwguerra.database.Database;
import com.mwguerra.models.account.Account;
import com.mwguerra.models.account.CheckingAccount;
import com.mwguerra.models.account.PaycheckAccount;
import com.mwguerra.models.account.SavingsAccount;
import com.mwguerra.models.transactions.History;

public class Manager {
  private PaycheckAccount paycheckAccount = PaycheckAccount.database();
  private CheckingAccount checkingAccount = CheckingAccount.database();
  private SavingsAccount savingsAccount = SavingsAccount.database();
  private History history = History.database();

  public void main(Database db) {
    int option;

    checkingAccount.setDatabase(db);
    paycheckAccount.setDatabase(db);
    savingsAccount.setDatabase(db);
    history.setDatabase(db);

    do {
      option = Menu.manager();

      switch (option) {
        case 1:
          newAccount();
          break;
        case 2:
          accountsReport(true);
          break;
        case 3:
          accountsReport();
          break;
        case 0:
        default:
          break;
      }
    } while (option != 0);
  }

  private void newAccount() {
    String ssn = Menu.customerSSN();

    boolean hasPaycheckAccount = paycheckAccount.findByOwner(ssn) != null;
    boolean hasCheckingAccount = checkingAccount.findByOwner(ssn) != null;
    boolean hasSavingsAccount = savingsAccount.findByOwner(ssn) != null;

    if (hasCheckingAccount && hasSavingsAccount && hasPaycheckAccount) {
      System.out.println("-- O CPF " + ssn + " já possui as contas salário, corrente e poupança.");
    }

    int option = Menu.customerAccountType("Tipo de Conta");

    if (option == 1 && !hasPaycheckAccount) {
      paycheckAccount.create(new PaycheckAccount(ssn, 0.0));
    }

    if (option == 2 && !hasCheckingAccount) {
      checkingAccount.create(new CheckingAccount(ssn, 0.0));
    }

    if (option == 3 && !hasSavingsAccount) {
      savingsAccount.create(new SavingsAccount(ssn, 0.0));
    }
  }

  private void accountsReport() {
    accountsReport(false);
  }

  private void accountsReport(boolean withDetails) {
    Double totalBalanceForAllAccounts = 0.0;

    System.out.println("");
    System.out.print("-- Contas Salário");
    totalBalanceForAllAccounts += showBalance(paycheckAccount, withDetails);

    System.out.print("-- Contas Corrente");
    totalBalanceForAllAccounts += showBalance(checkingAccount, withDetails);

    System.out.print("-- Contas Poupança");
    totalBalanceForAllAccounts += showBalance(savingsAccount, withDetails);

    System.out.println("\n-- TOTAL GERAL: " + totalBalanceForAllAccounts);
  }

  private Double showBalance(Account accountTable, boolean withDetails) {
    Double totalBalance = 0.0;

    if (withDetails) {
      System.out.println("");
    } else {
      System.out.print(": ");
    }

    for(Account account: accountTable.all()) {
      if (withDetails) {
        System.out.println(account.owner + ":" + account.accountNumber + ":" + account.getBalance());
      }
      totalBalance += account.getBalance();
    }

    if (withDetails) {
      System.out.print(">> TOTAL: ");
    }

    System.out.println(totalBalance);

    if (withDetails) {
      System.out.println("");
    }

    return totalBalance;
  }
}
