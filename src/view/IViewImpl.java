package view;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

/**
 * Implementation of the IView interface providing methods to display menus, portfolio composition,
 * total portfolio value, list of portfolios, and error messages.
 */
public class IViewImpl implements IView {

  /**
   * PrintStream for standard output.
   */
  private final PrintStream out;

  /**
   * PrintStream for error output.
   */
  private final PrintStream err;

  /**
   * Constructor to initialize the IViewImpl object with PrintStreams for output and error.
   * @param out PrintStream for standard output, initializes out parameter.
   * @param err PrintStream for error output, initializes in parameter.
   */
  public IViewImpl(PrintStream out, PrintStream err) {
    this.out = out;
    this.err = err;
  }

  @Override
  public void showPrimaryMenu() {
    out.println("Main Menu");
    out.println("1. Create a portfolio");
    out.println("2. Load a portfolio");
    out.println("3. Get Stock Statistics");
    out.println("4. Exit");
  }

  @Override
  public void showSecondaryMenu() {
    out.println("Main Menu");
    out.println("1. Create a portfolio");
    out.println("2. Load a portfolio");
    out.println("3. Get composition of a portfolio");
    out.println("4. Get total value of a portfolio for certain date");
    out.println("5. Save a portfolio");
    out.println("6. Buy stock");
    out.println("7. Sell stock");
    out.println("8. Get cost basis");
    out.println("9. Get Stock Statistics");
    out.println("10. Checkout Portfolio Performance");
    out.println("11. Exit");
  }

  @Override
  public void showComposition(Map<String, Integer> composition) {
    out.println("Composition of Portfolio");
    out.println("Stock Name\tStock Quantity");
    for (Map.Entry<String, Integer> entry : composition.entrySet()) {
      out.printf("%-20s %d\n", entry.getKey(), entry.getValue());
    }
  }

  @Override
  public void choosePortfolioType() {
    out.println("1. Inflexible Portfolio");
    out.println("2. Flexible Portfolio");
    out.println("3. Go back to main menu");
  }


  @Override
  public void showStockStat() {
    out.println("1. Get gain or lose on date for a stock");
    out.println("2. Get gain or lose over a period for a stock");
    out.println("3. Get x-day moving average on a date for a stock");
    out.println("4. Over a specific time-period get crossover days for a stock");
    out.println("5. Over a specific time-period get moving crossover for a stock");
    out.println("6. View stock performance for a stock");
    out.println("7. Exit");

  }
  @Override
  public void showTotalValue(double value) {
    out.println("The total value of portfolio is: $" + value);
  }

  @Override
  public void showXDayMovingAvg(double value) {
    out.println("The X-day moving average is: $" + value);
  }


//  @Override
//  public void showListOfPortfolios(ArrayList<String> listOfPortfolios) {
//    //can print this message using print that list of flexible portfolio, list of inflexible portfolio
//    // or can have a parameter as string type that gives portfolio type, but it will be changed in existing code.
//    out.println("The list of existing portfolios:");
//    out.println("No. of Portfolio\tPortfolio Name");
//    for (int i = 0; i < listOfPortfolios.size(); i++) {
//      out.printf("%-20s %s\n", i, listOfPortfolios.get(i));
//    }
//  }

  @Override
  public void showListOfPortfolios(Map<String, String> listOfPortfolios) {
    //can print this message using print that list of flexible portfolio, list of inflexible portfolio
    // or can have a parameter as string type that gives portfolio type, but it will be changed in existing code.
    out.println("The list of existing portfolios:");
    out.println("No. of Portfolio\t Portfolio Name");


    int index = 0;
    for (Map.Entry<String, String> entry : listOfPortfolios.entrySet()) {
      String key = entry.getKey();
      String value = entry.getValue();
      out.printf("%-20s %s\n", index, key + " - " + value);
      index++;
    }
  }


  @Override
  public void displayError(String error) {
    err.println(error);
  }

  @Override
  public void print(String message) {
    out.println(message);
  }

  @Override
  public void barGraph(int scale, Map<String, Integer> data, String stockOrPortfolio, String startDate, String endDate) {
    out.println("Performance of portfolio " + stockOrPortfolio + " from " + startDate + " to " + endDate + "\n");
    for (Map.Entry<String, Integer> entry : data.entrySet()) {
      String timestamp = entry.getKey();
      int quantity = entry.getValue();
      StringBuilder asterisks = new StringBuilder();
      for (int i = 0; i < quantity; i++) {
        asterisks.append("*");
      }
      out.println(timestamp + ": " + asterisks);
    }
    out.print("\nScale: * = \n"+ scale);
  }

  @Override
  public void printTreeMapEntries(TreeMap<String, String> treeMap) {
    for (Map.Entry<String, String> entry : treeMap.entrySet()) {
      out.println(entry.getKey() + " : " + entry.getValue());
    }
  }


}
