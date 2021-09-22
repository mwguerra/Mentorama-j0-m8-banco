package com.mwguerra.models.transactions;

import com.mwguerra.database.Database;
import com.mwguerra.enums.OperationType;
import com.mwguerra.interfaces.IModel;
import com.mwguerra.models.Model;
import com.mwguerra.models.account.CheckingAccount;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class History extends Model<History> implements IModel<History> {
  public static final String table = "history";

  private String description;
  private Calendar date;
  private OperationType operationType;
  private Double amount;

  public String getDescription() {
    return description;
  }

  public Calendar getDate() {
    return date;
  }

  @Override
  public String toString() {
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    return "[" + dateFormat.format(this.getDate().getTime()) + "] " +
      (this.getOperationType() == OperationType.DEPOSIT ? "+" : "-") +
      this.getAmount() + " ("+ this.getDescription() + ")";
  }

  public Integer getAccountNumber() {
    return accountNumber;
  }

  public OperationType getOperationType() {
    return operationType;
  }

  public Double getAmount() {
    return amount;
  }

  public History(Integer accountNumber, String description, Calendar date, OperationType operationType, Double amount) {
    this.accountNumber = accountNumber;
    this.description = description;
    this.date = date;
    this.operationType = operationType;
    this.amount = amount;
    setup();
  }

  private History() {
    // Para uso das funções estáticas de busca ao banco
    setup();
  }

  protected void setup() {
    setTable("history");
  }

  public static History database(Database db) {
    History history = new History();
    history.setDatabase(db);
    return history;
  }

  public static History database() {
    return new History();
  }
}
