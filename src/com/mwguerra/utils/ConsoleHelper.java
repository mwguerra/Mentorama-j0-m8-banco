package com.mwguerra.utils;

import java.io.Console;

public class ConsoleHelper {
  public static void clear()
  {
    try
    {
      final String os = System.getProperty("os.name");

      // System.out.println(" >> " + os + " << "); // "Mac OS X", "Windows"
      if (isRunningFromIntelliJ()) {
        System.out.println("");
      }

      if (os.contains("Windows"))
      {
        Runtime.getRuntime().exec("cls");
      }
      else
      {
        Runtime.getRuntime().exec("clear");
      }
    }
    catch (final Exception e)
    {
      e.printStackTrace();
    }
  }

  private static boolean isRunningFromIntelliJ()
  {
    String classPath = System.getProperty("java.class.path");
    return classPath.contains("idea_rt.jar");
  }
}
