package controller;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import model.InflexiblePortfolioImpl;

public interface Features {

  /**
   * takes input from gui as portfolio name, delegates this input to model methods.
   * and helps in creating a flexible portfolio.
   * @param name name of the new flexible portfolio to be created.
   */
  void createFlexiblePortfolio(String name);

  /**
   * this method delegates the index of portfolio selected and path of file.
   * it gets input from the gui and uses model methods to export the portfolio data.
   * Exports portfolio data to a file at the specified path.
   * @param input index of the portfolio to export.
   * @param path file path to export portfolio data to.
   */
  void export(int input, String path);

  /**
   * buys specified quantity of given stock on a specified date, gets the input using the GUI.
   * and uses model methods and delegate this input to buy stocks.
   * @param date       date of the stock purchase.
   * @param quantity   quantity of shares to purchase.
   * @param shareName  name of the stock to purchase.
   * @param choice     index of portfolio in which buying is carried out.
   */
  void buyStock(String date, String quantity, String shareName, int choice);

  /**
   * this method sells specified quantity of given stock on specified date, gets input using GUI.
   * and uses model methods and delegate this input to sell stocks.
   * @param date       date of the stock sale.
   * @param quantity    quantity of shares to sell.
   * @param shareName  name of the stock to sell.
   * @param choice     index of portfolio in which buying is carried out.
   */
  void sellStock(String date, String quantity, String shareName, int choice);

  /**
   * this method examines composition of a portfolio on a specified date, for this it gets input.
   * from the gui and further uses model methods and delegate this input to examine composition.
   * @param input index of the portfolio to examine.
   * @param date  date for examining the portfolio composition.
   */
  void examineComposition(int input, String date);

  /**
   * this method retrieves total value of a portfolio on a specified date,for this it gets input.
   * from the gui and further uses model methods and delegate this input to get total value.
   * @param choice index of  portfolio to get total value of.
   * @param date  date for retrieving total value.
   */
  void getTotalValue(int choice, String date);

  /**
   * this method retrieves cost basis of a portfolio on a specified date, for this it gets input.
   *from the gui and further uses model methods and delegate this input to get cost basis.
   * @param choice index of the portfolio to get cost basis.
   * @param date    date for retrieving cost basis.
   */
  void getCostBasis(int choice, String date);

  /**
   * this method retrieves gain or loss of a specific stock on a given date, for this it gets input.
   * from the gui and further uses model methods and delegate this input to gain or lose method.
   * @param date   date for calculating the gain or loss.
   * @param ticker ticker symbol of  stock.
   */
  void gainOrLose(String date, String ticker);

  /**
   * this method retrieves the gain or loss of a specific stock over a period of time.
   * for this it gets input from the gui and further uses model methods and delegate this.
   * input to model method to get output.
   * @param startDateArray start date for calculating the gain or loss.
   * @param endDateArray   end date for calculating the gain or loss.
   * @param ticker         ticker symbol of the stock.
   */
  void gainOrLoseOverPeriod(String startDateArray, String endDateArray,String ticker );


  /**
   * this method retrieves x-day moving average of a specific stock starting from a given date.
   *  for this it gets input from the gui and further uses model methods and delegate this.
   * input to model method to get output.
   * @param ticker   ticker symbol of the stock.
   * @param x       number of days for the moving average.
   * @param startDateArray  start date for calculating moving average.
   */
  void xDayMovingAvg(String ticker, String x, String startDateArray);

  /**
   * this method retrieves crossover points over a period of time for a specific stock.
   * for this it gets input from the gui and further uses model methods and delegate this.
   * input to model method to get output.
   * @param startDateArray start date for calculating the crossover points.
   * @param endDateArray  end date for calculating the crossover points.
   * @param ticker  ticker symbol of the stock.
   * @return a TreeMap containing the crossover points and their corresponding dates.
   */
  TreeMap<String, String> crossoverOverPeriod(String startDateArray, String endDateArray,
                                              String ticker);

  /**
   * this method retrieves moving crossover points over a period of time for a specific stock.
   * for this it gets input from the gui and further uses model methods and delegate this.
   * input to model method to get output.
   * @param startDateArray start date for calculating the moving crossover points.
   * @param endDateArray   end date for calculating the moving crossover points.
   * @param x              number of days for the first moving average.
   * @param y             number of days for the second moving average.
   * @param ticker     ticker symbol of the stock.
   * @return TreeMap containing moving crossover points and their corresponding dates.
   */
  TreeMap<String, String> movingCrossoversOverPeriod(String startDateArray, String endDateArray,
                                                     String x,String y,
                                                     String ticker );

  /**
   * this method retrieves any error message associated with last operation.
   * @return error message, or null if no error occurred.
   */
  String getErrorMessage();

  /**
   * this method retrieves a list of names of all portfolios using model method.
   * @return a list of portfolio names.
   */
  ArrayList<String> getPortfolioNames();

  /**
   * this method retrieves any success message associated with last operation.
   * @return the success message, or null if no success occurred.
   */
  String  getSuccessMessage();

  /**
   * this method loads a portfolio from a file, 
   * for this it gets input from the gui and further uses model methods and delegate this.
   * input to model method to load the portfolio and create a new flexible portfolio from it.
   * @param name          name of the portfolio.
   * @param portfolioPath  file path from which to load the portfolio data.
   */
  void loadPortfolio(String name, String portfolioPath);
}
