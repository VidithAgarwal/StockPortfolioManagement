package view;

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
   * Displays the composition of a portfolio.
   * @param composition Map containing stock names and quantities.
   */
  void showComposition(Map<String, Integer> composition);

  /**
   * this method displays the total value of a portfolio.
   * @param value The total value of the portfolio.
   */
  void showTotalValue(double value);

  /**
   * Displays the list of existing portfolios.
   * @param listOfPortfolios Map containing portfolio names and their types.
   */
  //void showListOfPortfolios(ArrayList<String> listOfPortfolios);

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

  /**
   * Displays a bar graph using *.
   *
   * @param scale           Scaling factor for the bar graph.
   * @param data Data for the bar graph that has represents the date and number of * to be printed.
   * @param stockOrPortfolio Name of the stock or portfolio.
   * @param startDate       Start date from which graph is represented.
   * @param endDate         End date which is the end date until which graph is represented.
   */
  void barGraph(int scale, TreeMap<String, Integer> data, String stockOrPortfolio, String startDate, String endDate);

  /**
   * Prints entries of a TreeMap in proper format.
   * @param treeMap TreeMap whose entries which are to be printed.
   */
  void printTreeMapEntries(TreeMap<String, String> treeMap);

  /**
   * Displays the option to choose the type of portfolio, flexible or inflexible.
   */
  void choosePortfolioType();

  /**
   * Displays the options for stock statistics for stock trend statistics.
   */
  void showStockStat();

  /**
   * Displays the X-day moving average value.
   * @param value The X-day moving average value that is to be printed.
   */
  void showXDayMovingAvg(double value);

}
