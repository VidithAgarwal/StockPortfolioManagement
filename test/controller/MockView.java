package controller;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import view.IView;

/**
 * The MockView class is a mock implementation of the IView interface.
 * It is used for testing purposes.
 */
public class MockView implements IView {

  /**
   * The StringBuilder object used to store printed output.
   */
  private final StringBuilder printedOutput = new StringBuilder();

  /**
   * Retrieves the printed output.
   * @return The printed output.
   */
  public StringBuilder getPrintedOutput() {
    return printedOutput;
  }

  /**
   * Displays the primary menu.
   */
  @Override
  public void showPrimaryMenu() {
    // has no input and no output.
  }

  /**
   * Displays the secondary menu.
   */
  @Override
  public void showSecondaryMenu() {
    // has no input and no output.
  }

  /**
   * Displays the composition of the portfolio.
   * @param composition The map containing the composition of the portfolio.
   *                   (stock names and quantities).
   */
  @Override
  public void showComposition(Map<String, Integer> composition) {
    for (Map.Entry<String, Integer> entry : composition.entrySet()) {
      printedOutput.append(entry.getKey()).append(" ").append(entry.getValue()).append("\n");
    }
  }

  /**
   * Displays the total value of the portfolio.
   * @param value The total value of the portfolio.
   */
  @Override
  public void showTotalValue(double value) {
    printedOutput.append(value).append("\n");
  }

  /**
   * Displays the list of portfolios.
   * @param listOfPortfolios The list of portfolios.
   */
  @Override
  public void showListOfPortfolios(Map<String, String> listOfPortfolios) {
    for (Map.Entry<String, String> entry : listOfPortfolios.entrySet()) {
      printedOutput.append(entry.getKey()).append(" ").append(entry.getValue()).append("\n");
    }
  }


  /**
   * Displays an error message.
   * @param error The error message to display.
   */
  @Override
  public void displayError(String error) {
    printedOutput.append("Error").append("\n");
  }


  /**
   * Prints a message.
   * @param message The message to print.
   */
  @Override
  public void print(String message) {
    printedOutput.append(message).append("\n");
  }


  /**
   * this method of mock view displays a bar graph for the given data.
   * @param scale scale of the bar graph.
   * @param data data to be displayed in the bar graph.
   * @param stockOrPortfolio name of the stock or portfolio.
   * @param startDate start date for the bar graph.
   * @param endDate end date for the bar graph.
   */
  @Override
  public void barGraph(int scale, TreeMap<String, Integer> data, String stockOrPortfolio,
                       String startDate, String endDate) {
    printedOutput.append("Bar Graph for ").append(stockOrPortfolio).append(" from ")
            .append(startDate).append(" to ").append(endDate).append(":\n");

    for (Map.Entry<String, Integer> entry : data.entrySet()) {
      printedOutput.append(entry.getKey()).append(" ").append(entry.getValue()).append("\n");
    }

    printedOutput.append("scale: ").append(scale).append("\n");
  }

  /**
   * Prints entries from a TreeMap.
   * @param treeMap TreeMap containing entries to print.
   */
  @Override
  public void printTreeMapEntries(TreeMap<String, String> treeMap) {
    for (Map.Entry<String, String> entry : treeMap.entrySet()) {
      printedOutput.append(entry.getKey()).append(" ").append(entry.getValue()).append("\n");
    }

  }

  /**
   * this method displays a message to choose portfolio type.
   */
  @Override
  public void choosePortfolioType() {
    // has no input and no output.
  }

  /**
   * this method displays stock statistics.
   */
  @Override
  public void showStockStat() {
    // has no input and no output.
  }

  /**
   * this method displays X-Day Moving Average value.
   * @param value value of X-Day Moving Average.
   */
  @Override
  public void showXDayMovingAvg(double value) {
    printedOutput.append(value).append("\n");
  }
}
