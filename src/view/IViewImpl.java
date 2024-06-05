package view;

import java.io.PrintStream;
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

  /**
   * Displays the primary menu options, when any portfolio is not created, at start.
   */
  @Override
  public void showPrimaryMenu() {
    out.println("Main Menu");
    out.println("1. Create a portfolio");
    out.println("2. Load a portfolio");
    out.println("3. Get Stock Statistics");
    out.println("4. Exit");
  }

  /**
   * Displays the secondary menu options, which is shown once at least one portfolio is created.
   */
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
    out.println("10. Get Portfolio Performance");
    out.println("11. Exit");
  }


  /**
   * Displays the composition of a portfolio.
   * @param composition Map containing stock names and quantities.
   */
  @Override
  public void showComposition(Map<String, Double> composition) {
    out.println("Composition of Portfolio");
    out.println("Stock Name\tStock Quantity");
    for (Map.Entry<String, Double> entry : composition.entrySet()) {
      out.printf("%-20s %d\n", entry.getKey(), entry.getValue());
    }
  }

  /**
   * Displays the option to choose the type of portfolio, flexible or inflexible.
   */
  @Override
  public void choosePortfolioType() {
    out.println("1. Inflexible Portfolio");
    out.println("2. Flexible Portfolio");
    out.println("3. Go back to main menu");
  }


  /**
   * Displays the options for stock statistics for stock trend statistics.
   */
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

  /**
   * Displays the total value of a portfolio.
   * @param value Total value of the portfolio to be printed.
   */
  @Override
  public void showTotalValue(double value) {
    out.println("The total value of portfolio is: $" + value);
  }

  /**
   * Displays the X-day moving average value.
   * @param value The X-day moving average value that is to be printed.
   */
  @Override
  public void showXDayMovingAvg(double value) {
    out.println("The X-day moving average is: $" + value);
  }



  /**
   * Displays the list of existing portfolios.
   * @param listOfPortfolios Map containing portfolio names and their types.
   */
  @Override
  public void showListOfPortfolios(Map<String, String> listOfPortfolios) {
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


  /**
   * Displays an error message.
   * @param error Error message to be displayed.
   */
  @Override
  public void displayError(String error) {
    err.println(error);
  }

  /**
   * Prints a message.
   * @param message Message to be printed.
   */
  @Override
  public void print(String message) {
    out.println(message);
  }

  /**
   * Displays a bar graph using * for representation of performance of stock/portfolio.
   *
   * @param scale           Scaling factor for the bar graph.
   * @param data Data for the bar graph that has represents the date and number of * to be printed.
   * @param stockOrPortfolio Name of the stock or portfolio.
   * @param startDate       Start date from which graph is represented.
   * @param endDate         End date which is the end date until which graph is represented.
   */
  @Override
  public void barGraph(int scale, TreeMap<String, Integer> data, String stockOrPortfolio,
                       String startDate, String endDate) {
    out.println("Performance of " + stockOrPortfolio + " from " + startDate
            + " to " + endDate + "\n");
    for (Map.Entry<String, Integer> entry : data.entrySet()) {
      String timestamp = entry.getKey();
      int quantity = entry.getValue();
      StringBuilder asterisks = new StringBuilder();
      for (int i = 0; i < quantity; i++) {
        asterisks.append("*");
      }
      out.println(timestamp + ": " + asterisks);
    }
    out.print("\nScale: * = " + scale + "\n");
  }

  /**
   * Prints entries of a TreeMap in proper format.
   * @param treeMap TreeMap whose entries which are to be printed.
   */
  @Override
  public void printTreeMapEntries(TreeMap<String, String> treeMap) {
    out.print("The crossover opportunities are:\n");
    for (Map.Entry<String, String> entry : treeMap.entrySet()) {
      out.println(entry.getKey() + " : " + entry.getValue());
    }
  }


}
