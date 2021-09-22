package com.mwguerra.models.account;

import com.mwguerra.database.Database;
import com.mwguerra.enums.AccountType;
import com.mwguerra.interfaces.IModel;
import com.mwguerra.interfaces.ITaxable;

// Conta salário
public class PaycheckAccount extends Account implements ITaxable {
  private static final int WITHDRAWS_PER_MONTH = 2;
  private static int withdrawsCount = 0;

  public PaycheckAccount(String owner, Double balance) {
    super(owner, balance, AccountType.PAYCHECK);
    setup();
  }

  private PaycheckAccount() {
    super();
    setup();
  }

  protected void setup() {
    setTable("paycheck_account");
  }

  public static PaycheckAccount database(Database db) {
    PaycheckAccount account = new PaycheckAccount();
    account.setDatabase(db);
    return account;
  }

  public static PaycheckAccount database() {
    return new PaycheckAccount();
  }

  @Override
  public Double getBalance() {
    return this.balance;
  }

  @Override
  public boolean withdraw(Double amount) {
    if (withdrawsCount >= WITHDRAWS_PER_MONTH) {
      return false;
    }

    if (amount > balance) {
      return false;
    }

    this.balance = balance - (amount * TAXES);

    withdrawsCount++;
    return true;
  }
}
