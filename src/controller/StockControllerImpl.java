package controller;

import model.PortfolioBuilder;
import java.util.Scanner;


public class StockControllerImpl implements StockController {

  public void createPortfolio() {
    Scanner scanner = new Scanner(System.in);

    System.out.print("Enter the name of the portfolio ");

    String name = scanner.nextLine();

    System.out.print("Enter the number of shares ");

    int numShares = scanner.nextInt();

    try {
      PortfolioBuilder newBuilder = new PortfolioBuilder(name, numShares);
      String shareName;
      int quantity;
      for (int i = 0; i < numShares; i++) {
        scanner = new Scanner(System.in);

        System.out.println("Enter the name of the share ");

        shareName = scanner.nextLine();

        System.out.println("Enter the quantity of the share ");

        quantity = scanner.nextInt();
        scanner.nextLine();

        newBuilder.addShare(shareName, quantity);
      }
      newBuilder.createPortfolio();
    } catch (IllegalArgumentException e) {
      System.out.println("The portfolio name already exists! Please given another name.");
    }

  }
}
