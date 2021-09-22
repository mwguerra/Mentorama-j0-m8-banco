package com.mwguerra.models.account;

import com.mwguerra.database.Database;
import com.mwguerra.enums.AccountType;
import com.mwguerra.models.transactions.History;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Objects;

// Conta poupança
public class SavingsAccount extends Account {
  private static final double interestRate = 0.05;

  public SavingsAccount(String owner, Double balance) {
    super(owner, balance, AccountType.SAVINGS);
    setup();
  }

  private SavingsAccount() {
    super();
    setup();
  }

  protected void setup() {
    setTable("savings_account");
  }

  public static SavingsAccount database(Database db) {
    SavingsAccount account = new SavingsAccount();
    account.setDatabase(db);
    return account;
  }

  public static SavingsAccount database() {
    return new SavingsAccount();
  }

  @Override
  public Double getBalance() {
    if (getDatabase() == null) {
      System.out.println("## Banco de dados não iniciado.");
    }

    History lastHistoryRecord = getDatabase()
      .getTable(History.database().getTable())
      .stream()
      .map(item -> (History) item)
      .filter(h -> Objects.equals(h.getAccountNumber(), getAccountNumber()))
      .findFirst()
      .orElse(null);

    Calendar lastDate = new GregorianCalendar();
    if (lastHistoryRecord != null) {
      lastDate = lastHistoryRecord.getDate();
    }

    int months = monthsBetween(new GregorianCalendar(), lastDate);
    return balance + balance * (interestRate * months);
  }

  private static int monthsBetween(Calendar date1, Calendar date2){
    int months1 = 12 * date1.get(Calendar.YEAR) + date1.get(Calendar.MONTH);
    int months2 = 12 * date2.get(Calendar.YEAR) + date2.get(Calendar.MONTH);

    return Math.abs(months2 - months1);
  }

  @Override
  public boolean withdraw(Double amount) {
    if (amount > balance) {
      return false;
    }

    this.balance = balance - amount;

    return true;
  }
}
