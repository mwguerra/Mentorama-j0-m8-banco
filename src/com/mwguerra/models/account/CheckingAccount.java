package com.mwguerra.models.account;

import com.mwguerra.database.Database;
import com.mwguerra.enums.AccountType;
import com.mwguerra.interfaces.IModel;
import com.mwguerra.interfaces.ITaxable;

// Conta corrente
public class CheckingAccount extends Account implements ITaxable {
  private Double overdraft = 1000.0;

  public Double getOverdraft() {
    return overdraft;
  }

  public CheckingAccount(String owner, Double balance) {
    super(owner, balance, AccountType.CHECKING);
    setup();
  }

  private CheckingAccount() {
    super();
    setup();
  }

  protected void setup() {
    setTable("checking_account");
  }

  public static CheckingAccount database(Database db) {
    CheckingAccount account = new CheckingAccount();
    account.setDatabase(db);
    return account;
  }

  public static CheckingAccount database() {
    return new CheckingAccount();
  }

  @Override
  public Double getBalance() {
    return this.balance;
  }

  @Override
  public boolean withdraw(Double amount) {
    if (amount > balance + overdraft) {
      return false;
    }

    balance = balance - (amount * TAXES);

    return true;
  }
}
