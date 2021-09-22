package com.mwguerra.utils;

public class StringHelper {
  public static String addChar(java.lang.String string, char character, int position) {
    StringBuilder stringBuilder = new StringBuilder(string);
    stringBuilder.insert(position, character);
    return stringBuilder.toString();
  }

  public static String cpfFormatter(String rawCpfString) {
    String cpf = rawCpfString.replaceAll("[^0-9]", "");

    if (cpf.length() > 11) {
      cpf = cpf.substring(0, 11);
    }

    if (cpf.length() < 11) {
      cpf = String.format("%1$" + 11 + "s", cpf).replace(' ', '0');
    }

    cpf = StringHelper.addChar(cpf, '.', 3);
    cpf = StringHelper.addChar(cpf, '.', 7);
    cpf = StringHelper.addChar(cpf, '-', 11);

    return cpf;
  }
}
