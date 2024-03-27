package controller;

import java.time.LocalDate;
import java.util.Scanner;

import model.PortfolioDir;
import view.IView;

class FlexiblePortfolioBuySellHelper extends AbsHelperController{
  FlexiblePortfolioBuySellHelper(IView view, PortfolioDir model, Scanner scan) {
    super(view, model, scan);
  }

  void buyStock() {
    int choice = inputPortfolioChoice();

    view.print("Enter the name of the share or ticker symbol: ");
    String shareName = scan.nextLine();
    int quantity = inputPositiveInteger("Enter the quantity of " + shareName + " you want to buy:");
    int[] date = inputDate("Enter the date of your purchase");
    LocalDate buyDate = LocalDate.of(date[2], date[1],date[0]);

    try {
      model.buyStock(choice, shareName, quantity, buyDate, new StockData());
      view.print(quantity + " " + shareName + " bought successfully");
    } catch (RuntimeException e) {
      view.displayError(e.getMessage());
    }
  }

  void sellStock() {
    int choice = inputPortfolioChoice();

    view.print("Enter the name of the share or ticker symbol: ");
    String shareName = scan.nextLine();
    int quantity = inputPositiveInteger("Enter the quantity of " + shareName + " you want to " +
            "sell:");
    int[] date = inputDate("Enter the date of your sale");
    LocalDate sellDate = LocalDate.of(date[2], date[1],date[0]);

    try {
      model.sellStock(choice, shareName, quantity, sellDate, new StockData());
      view.print(quantity + " " + shareName + " sold successfully");
    } catch (RuntimeException e) {
      view.displayError(e.getMessage());
    }
  }
}
