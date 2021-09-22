package com.mwguerra.interfaces;

import com.mwguerra.database.Database;

import java.util.List;
import java.util.UUID;

public interface IModel<T> {
  List<T> all();
  List<T> allWhereAccountNumber(Integer accountNumber);
  List<T> allWhereOwner(String owner);
  T first();
  T create(T record);
  T updateById(T record);
  T findById(UUID id);
  T findByAccountNumber(Integer accountNumber);
  T findByOwner(String owner);
}
