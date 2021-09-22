package com.mwguerra.models;

import com.mwguerra.database.Database;
import com.mwguerra.interfaces.IModel;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

public abstract class Model<T extends Model<T>> implements IModel<T> {
  public UUID id = UUID.randomUUID();
  public Database database = null;
  public String table = "";
  public Integer accountNumber = null;
  public String owner = null;

  public void setDatabase(Database database) {
    this.database = database;
  }

  public Database getDatabase() {
    return database;
  }

  public String getTable() {
    return table;
  }

  public void setTable(String table) {
    this.table = table;
  }

  public void setAccountNumber(Integer accountNumber) {
    this.accountNumber = accountNumber;
  }

  public void setOwner(String owner) {
    this.owner = owner;
  }

  public Integer getAccountNumber() {
    return accountNumber;
  }

  public String getOwner() {
    return owner;
  }

  protected boolean databaseNotFound() {
    if (database == null) {
      System.out.println("ERRO: O banco de dados " + table + " não foi inicializado.");
      return true;
    }
    return false;
  }

  public List<T> all() {
    if (databaseNotFound()) { return null; }

    return database.getTable(table)
      .stream()
      .map(item -> (T) item)
      .peek(item -> item.setDatabase(database))
      .collect(Collectors.toList());
  }

  public List<T> allWhereAccountNumber(Integer accountNumber) {
    if (databaseNotFound()) { return null; }

    return database.getTable(table)
      .stream()
      .map(item -> (T) item)
      .filter(item -> {
        if (item.accountNumber == null) {
          return false;
        }
        return item.accountNumber.intValue() == accountNumber.intValue();
      })
      .peek(item -> item.setDatabase(database))
      .collect(Collectors.toList());
  }

  public List<T> allWhereOwner(String owner) {
    if (databaseNotFound()) { return null; }

    return database.getTable(table)
      .stream()
      .map(item -> (T) item)
      .filter(item -> Objects.equals(item.owner, owner))  // se for history, tem que consultar o owner pelo número da conta antes.
      .peek(item -> item.setDatabase(database))
      .collect(Collectors.toList());
  }

  public T first() {
    if (databaseNotFound()) { return null; }

    return database.getTable(table)
      .stream()
      .map(item -> (T) item)
      .peek(item -> item.setDatabase(database))
      .findFirst()
      .orElse(null);
  }

  public T create(T record) {
    if (databaseNotFound()) { return null; }

    database.getTable(table).add(record);

    return findById(record.id);
  }

  public T updateById(T record) {
    if (databaseNotFound()) { return null; }


    return database.getTable(table)
      .stream()
      .map(item -> (T) item)
      .filter(item -> item.id == record.id)
      .map(item -> record)
      .peek(item -> item.setDatabase(database))
      .findFirst()
      .orElse(null);
  }

  public T findById(UUID id) {
    if (databaseNotFound()) { return null; }

    return database.getTable(table)
      .stream()
      .map(item -> (T) item)
      .filter(item -> item.id == id)
      .peek(item -> item.setDatabase(database))
      .findFirst()
      .orElse(null);
  }

  public T findByAccountNumber(Integer accountNumber) {
    if (databaseNotFound()) { return null; }

    return database.getTable(table)
      .stream()
      .map(item -> (T) item)
      .filter(item -> item.accountNumber.intValue() == accountNumber.intValue())
      .peek(item -> item.setDatabase(database))
      .findFirst()
      .orElse(null);
  }

  public T findByOwner(String owner) {
    if (databaseNotFound()) { return null; }

    return database.getTable(table)
      .stream()
      .map(item -> (T) item)
      .filter(item -> Objects.equals(item.owner, owner))
      .peek(item -> item.setDatabase(database))
      .findFirst()
      .orElse(null);
  }
}
