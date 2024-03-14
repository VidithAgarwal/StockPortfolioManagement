package view;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Map;

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
    out.println("3. Exit");
  }

  @Override
  public void showSecondaryMenu() {
    out.println("Main Menu");
    out.println("1. Create a portfolio");
    out.println("2. Load a portfolio");
    out.println("3. Get composition of a portfolio");
    out.println("4. Get total value of a portfolio for certain date");
    out.println("5. Save a portfolio");
    out.println("6. Exit");
  }

  @Override
  public void showComposition(Map<String, Integer> composition) {
    out.println("Composition of Portfolio");
    out.println("Stock Name\tStock Quantity");
    for (Map.Entry<String, Integer> entry : composition.entrySet()) {
      out.println(entry.getKey() + "\t\t\t\t" + entry.getValue());
    }
  }

  @Override
  public void showTotalValue(double value) {
    out.println("The total value of portfolio is: $" + value);
  }

  @Override
  public void showListOfPortfolios(ArrayList<String> listOfPortfolios) {
    out.println("The list of existing portfolios:");
    out.println("No. of Portfolio\tPortfolio Name");
    for (int i = 0; i < listOfPortfolios.size(); i++) {
      out.println(i + "\t\t\t\t\t" + listOfPortfolios.get(i));
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
}
