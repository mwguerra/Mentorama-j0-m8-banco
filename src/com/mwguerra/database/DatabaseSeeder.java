package com.mwguerra.database;

import com.mwguerra.models.account.CheckingAccount;
import com.mwguerra.models.account.PaycheckAccount;
import com.mwguerra.models.account.SavingsAccount;

public class DatabaseSeeder {
  public void handle(Database db) {
    CheckingAccount checkingAccount = new CheckingAccount(
      "123.456.789-00",
      200.00
    );
    db.getTable(checkingAccount.getTable()).add(checkingAccount);

    CheckingAccount checkingAccount2 = new CheckingAccount(
      "234.567.890-00",
      10000.00
    );
    db.getTable(checkingAccount2.getTable()).add(checkingAccount2);

    PaycheckAccount paycheckAccount = new PaycheckAccount(
      "123.456.789-00",
      5000.00
    );
    db.getTable(paycheckAccount.getTable()).add(paycheckAccount);

    SavingsAccount savingsAccount = new SavingsAccount(
      "123.456.789-00",
      13000.00
    );
    db.getTable(savingsAccount.getTable()).add(savingsAccount);
  }
}
