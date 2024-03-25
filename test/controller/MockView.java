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

  @Override
  public void showPrimaryMenu() {
    // has no input and no output.
  }

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

  @Override
  public void showListOfPortfolios(Map<String, String> listOfPortfolios) {
    for (Map.Entry<String, String> entry : listOfPortfolios.entrySet()) {
      printedOutput.append(entry.getKey()).append(" ").append(entry.getValue()).append("\n");
    }
  }

  /**
   * Displays the list of portfolios.
   * @param listOfPortfolios The list of portfolios.
   */
//  @Override
//  public void showListOfPortfolios(ArrayList<String> listOfPortfolios) {
//    for (int i = 0; i < listOfPortfolios.size(); i++) {
//      printedOutput.append(listOfPortfolios.get(0)).append("\n");
//    }
//  }

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

  @Override
  public void barGraph(int scale, TreeMap<String, Integer> data, String stockOrPortfolio, String startDate, String endDate) {
    printedOutput.append("Bar Graph for ").append(stockOrPortfolio).append(" from ").append(startDate).append(" to ").append(endDate).append(":\n");

    for (Map.Entry<String, Integer> entry : data.entrySet()) {
      printedOutput.append(entry.getKey()).append(" ").append(entry.getValue()).append("\n");
    }

    printedOutput.append("scale: ").append(scale).append("\n");
  }

  @Override
  public void printTreeMapEntries(TreeMap<String, String> treeMap) {
    for (Map.Entry<String, String> entry : treeMap.entrySet()) {
      printedOutput.append(entry.getKey()).append(" ").append(entry.getValue()).append("\n");
    }

  }

  @Override
  public void choosePortfolioType() {
    // has no input and no output.
  }

  @Override
  public void showStockStat() {
    // has no input and no output.
  }

  @Override
  public void showXDayMovingAvg(double value) {
    printedOutput.append(value).append("\n");
  }
}
