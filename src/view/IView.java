package view;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

/**
 * Interface representing the view in the Model-View-Controller architecture.
 * The interface has methods that are used to show menu options, portfolio composition,total value.
 * showing the list of current portfolios and displaying the error message & simple print messages.
 */
public interface IView {

  /**
   * this method displays the primary menu, when there is no portfolio currently created.
   */
  void showPrimaryMenu();

  /**
   * this method displays the secondary menu when at least a single portfolio exists.
   */
  void showSecondaryMenu();

  /**
   * this method displays the composition of a portfolio.
   * @param composition Map that has composition of portfolio with stock names as keys .
   *                    & quantities as values.
   */
  void showComposition(Map<String, Integer> composition);

  /**
   * this method displays the total value of a portfolio.
   * @param value The total value of the portfolio.
   */
  void showTotalValue(double value);

  /**
   * this method displays a list of portfolios that have been created.
   * @param listOfPortfolios ArrayList containing names of portfolios.
   */
  void showListOfPortfolios(Map<String, String> listOfPortfolios);

  /**
   * this method displays an error message.
   * @param error The error message to be displayed.
   */
  void displayError(String error);

  /**
   * this method prints a message.
   * @param message The message to be printed.
   */
  void print(String message);

  void barGraph(int scale, Map<String, Integer> data, String stockOrPortfolio, String startDate, String endDate);

  void printTreeMapEntries(TreeMap<String, String> treeMap);

  void choosePortfolioType();

  void showStockStat();
}