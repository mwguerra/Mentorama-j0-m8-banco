package com.mwguerra;

import com.mwguerra.domains.Customer;
import com.mwguerra.domains.Manager;
import com.mwguerra.domains.Menu;
import com.mwguerra.database.Database;
import com.mwguerra.database.DatabaseSeeder;

public class Main {
  public static void main(String[] args) {
    Database db = new Database();

    DatabaseSeeder seeder = new DatabaseSeeder();
    seeder.handle(db);

    do {
      int option = Menu.main();

      switch (option) {
        case 1:
          Manager manager = new Manager();
          manager.main(db);
          break;
        case 2:
          Customer customer = new Customer();
          customer.main(db);
          break;
        case 0:
          System.out.println("\nFim do programa. Obrigado por utilizar o Omega Bank.");
          return;
      }
    } while (true);
  }
}
