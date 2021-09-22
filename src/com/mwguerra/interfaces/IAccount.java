package com.mwguerra.interfaces;

public interface IAccount {
  Integer getAccountNumber();
  Double getInitialBalance();
  Double getBalance();
  boolean withdraw(Double amount);
  void deposit(Double amount);
}
