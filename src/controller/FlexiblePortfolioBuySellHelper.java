package controller;

import java.time.LocalDate;
import java.util.Scanner;

import model.PortfolioDir;
import view.IView;

/**
 * FlexiblePortfolioBuySellHelper class provides helper methods for buying & selling stocks.
 * in flexible portfolios.
 * It extends AbsHelperController class, it uses the methods of view and model.
 */
class FlexiblePortfolioBuySellHelper extends AbsHelperController {


  /**
   * constructs FlexiblePortfolioBuySellHelper object with specified view, model, and scanner.
   * @param view view component for user interaction.
   * @param model  model component for managing portfolios.
   * @param scan scanner object for user input.
   */
  FlexiblePortfolioBuySellHelper(IView view, PortfolioDir model, Scanner scan) {
    super(view, model, scan);
  }

  /**
   * this method allows user to buy stocks for a flexible portfolio.
   * Prompts user for portfolio choice, share name, quantity, and purchase date.
   * Calls model's buyStock method to handle purchase, and view to displays the messages.
   */
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

  /**
   * this method allows user to sell stocks from a flexible portfolio.
   * Prompts user for portfolio choice, share name, quantity, and sale date.
   * Calls model's sellStock method to handle the sale, and view to displays the messages.
   */
  void sellStock() {
    int choice = inputPortfolioChoice();

    view.print("Enter the name of the share or ticker symbol: ");
    String shareName = scan.nextLine();
    int quantity = inputPositiveInteger("Enter the quantity of " + shareName + " you want to "
            + "sell:");
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
