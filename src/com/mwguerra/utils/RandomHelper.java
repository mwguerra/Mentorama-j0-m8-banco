package com.mwguerra.utils;

public class RandomHelper {
  public static Integer accountNumber() {
    java.util.Random r = new java.util.Random();
    String randomNumber = String.format("%08d", r.nextInt(10000001));
    return Integer.parseInt(randomNumber);
  }
}
