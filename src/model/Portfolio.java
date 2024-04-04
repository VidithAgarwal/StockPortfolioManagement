package model;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import controller.IStockData;
import controller.StockData;

/**
 * Portfolio interface has the methods that are related to single portfolio object.
 * The portfolio can be flexible portfolio or inflexible portfolio.
 * It gets the composition, portfolio value, name of a single portfolio and saves a portfolio.
 * Buying, selling stock & cost basis for portfolio can also be carried out for flexible portfolio.
 */
public interface Portfolio {

  /**
   * portfolioValue method is used to get the value of a portfolio on a particular date.
   * @param date is the date for which the portfolio value is to be determined.
   * @param api IStockData object used for fetching stock data.
   * @return the price of the portfolio on the particular date entered.
   */
  double portfolioValue(String date, IStockData api);

  /**
   * it gets the name of the portfolio.
   * @return the name of the portfolio.
   */
  String getName();

  /**
   * this method generates a StringBuilder containing symbol and quantity data for each share,
   * and for flexible portfolio it has transaction data and transaction type.
   * in the portfolio. StringBuilder is returned for further processing or storage.
   * @return StringBuilder containing symbol and quantity data for each share in the portfolio.
   */
  StringBuilder save();

  /**
   * this method buys stock for portfolio and is implemented in flexible portfolio.
   * it is used to buy stock in a flexible portfolio.
   * @param ticker ticker symbol of the stock to be bought.
   * @param quantity  quantity of stock to be bought.
   * @param date date on which the stock is bought.
   * @param api IStockData object used for fetching stock data.
   * @throws IllegalArgumentException if the method is called by inflexible portfolio.
   */
  void buyStock(String ticker, int quantity, LocalDate date, IStockData api);

  /**
   * this method sells stock from the portfolio is implemented in flexible portfolio.
   * It is used to sell stock in a flexible portfolio.
   * @param ticker ticker symbol of the stock to be sold.
   * @param quantity quantity of stock to be sold.
   * @param date date on which the stock is sold.
   * @param api IStockData object used for fetching stock data.
   * @throws IllegalArgumentException if the method is called by inflexible portfolio.
   */
  void sellStock(String ticker, int quantity, LocalDate date, IStockData api);

  /**
   * portfolio composition method is used to get the composition of a portfolio.
   * The stock name and its quantity is returned for the composition.
   * @return the map of stock name and the quantity of stock.
   */
  Map<String, Integer> portfolioComposition(LocalDate date);

  /**
   * this method calculates the cost basis of the portfolio, for flexible portfolio.
   * @param date date for which the cost basis is calculated.
   * @param api StockData object used for fetching stock data.
   * @return cost basis of the portfolio.
   * @throws IllegalArgumentException if the method is called by inflexible portfolio.
   */
  double costBasis(LocalDate date, IStockData api);

  /**
   * Checks if the portfolio is flexible.
   * @return true if it is a flexible portfolio or else returns false.
   */
  boolean isFlexible();

  /**
   * the load method to load portfolio data from a list of string arrays representing lines of data.
   * and the StockData object used for fetching stock data.
   * @param lines A list of string arrays representing lines of portfolio data to be loaded.
   * @param api The StockData object used for fetching stock data.
   */
  void load(List<String[]> lines, IStockData api);

  void dollarCostAverage(LocalDate today, BuyingStrategy schedule, StockData api);
}
