package com.mwguerra.database;

import com.mwguerra.interfaces.IModel;
import com.mwguerra.models.account.CheckingAccount;
import com.mwguerra.models.transactions.History;
import com.mwguerra.models.account.PaycheckAccount;
import com.mwguerra.models.account.SavingsAccount;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Database {
  public Map<String, List<? extends IModel<?>>> databaseTables = new HashMap<>();

  public Database() {
    List<CheckingAccount> checkingAccounts = new ArrayList<>();
    List<PaycheckAccount> paycheckAccounts = new ArrayList<>();
    List<SavingsAccount> savingsAccounts = new ArrayList<>();
    List<History> history = new ArrayList<>();

    databaseTables.put(CheckingAccount.database().getTable(), checkingAccounts);
    databaseTables.put(PaycheckAccount.database().getTable(), paycheckAccounts);
    databaseTables.put(SavingsAccount.database().getTable(), savingsAccounts);
    databaseTables.put(History.database().getTable(), history);
  }

  public <T> List<T> getTable(String tableName) {
    return (List<T>) databaseTables.get(tableName);
  }

  // TABLE STRUCTURE
  // checking_accounts
    // id
    // owner
    // accountNumber
    // type
    // balance
    // overdraft
  // paycheck_accounts
    // id
    // owner
    // accountNumber
    // type
    // balance
    // withdrawsCount
    // WITHDRAWS_PER_MONTH
  // savings_accounts
    // id
    // owner
    // accountNumber
    // type
    // balance
    // interestRate
  // history
    // id
    // owner
    // accountNumber
    // description
    // date
    // operationType
    // amount
}
