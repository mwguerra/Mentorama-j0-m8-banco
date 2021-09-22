package com.mwguerra.models.account;

import com.mwguerra.database.Database;
import com.mwguerra.enums.AccountType;
import com.mwguerra.interfaces.IAccount;
import com.mwguerra.interfaces.IModel;
import com.mwguerra.models.Model;
import com.mwguerra.utils.RandomHelper;
import com.mwguerra.utils.StringHelper;

public abstract class Account extends Model<Account> implements IAccount, IModel<Account> {
  protected Double initialBalance;
  protected Double balance;
  protected AccountType type;

  public Account(String owner, Double balance, AccountType type) {
    setOwner(StringHelper.cpfFormatter(owner));
    setAccountNumber(RandomHelper.accountNumber());
    this.initialBalance = balance;
    this.balance = balance;
    this.type = type;
  }

  protected Account() {
    // Para uso das funções estáticas de busca ao banco
  }

  public abstract Double getBalance();

  public void setBalance(Double balance) {
    this.balance = balance;
  }

  public abstract boolean withdraw(Double amount);

  public void deposit(Double amount) {
    balance = balance + amount;
  }

  public Double getInitialBalance() {
    return initialBalance;
  }

  public Integer getAccountNumber() {
    return accountNumber;
  }

  @Override
  public String toString() {
    String translatedType = "";
    translatedType = type == AccountType.PAYCHECK ? "Salário" : translatedType;
    translatedType = type == AccountType.CHECKING ? "Corrente" : translatedType;
    translatedType = type == AccountType.SAVINGS ? "Poupança" : translatedType;

    return "[" + owner + "] " + accountNumber + ": Conta " + translatedType;
  }
}
