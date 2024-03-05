package view;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Map;

public class IViewImpl implements IView{

  private final PrintStream out;

  public IViewImpl(PrintStream out) {
    this.out = out;
  }

  @Override
  public void showPrimaryMenu() {
    out.println("Main Menu");
    out.println("1. Create a portfolio");
    out.println("2. Load a portfolio");
    out.println("3. Exit");
    out.print("Enter your choice: ");
  }

  @Override
  public void showSecondaryMenu() {
    out.println("Main Menu");
    out.println("1. Create a portfolio");
    out.println("2. Load a portfolio");
    out.println("3. Exit");
    out.println("4. Get composition of a portfolio");
    out.println("5. Get total value of a portfolio for certain date");
    out.println("6. Save a portfolio");
    out.print("Enter your choice: ");
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
    out.println("The total value of portfolio is:" + value);
  }

  @Override
  public void showListOfPortfolios(ArrayList<String> listOfPortfolios) {
    out.println("The list of existing portfolios:");
    out.println("No. of Portfolio\tPortfolio Name");
    for (int i = 0; i < listOfPortfolios.size(); i++) {
      out.println(i + "\t\t\t\t\t" + listOfPortfolios.get(i));
    }
    out.println("Enter the Portfolio number you want to select.");
  }

  @Override
  public void displayError(String error) {
    out.println(error);
  }

  @Override
  public void print(String message) {
    out.println(message);
  }
}
