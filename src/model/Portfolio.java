package model;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
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
   * @return the price of the portfolio on the particular date entered.
   */
  double portfolioValue(String date, StockData api);

  /**
   * it gets the name of the portfolio.
   * @return the name of the portfolio.
   */
  String getName();

  StringBuilder save();

  /**
   * this method buys stock for portfolio and is implemented in flexible portfolio.
   * it is used to buy stock in a flexible portfolio.
   * @param ticker ticker symbol of the stock to be bought.
   * @param quantity  quantity of stock to be bought.
   * @param date date on which the stock is bought.
   * @param api StockData object used for fetching stock data.
   * @throws IllegalArgumentException if the method is called by inflexible portfolio.
   */
  void buyStock(String ticker, int quantity, LocalDate date, StockData api);

  /**
   * this method sells stock from the portfolio is implemented in flexible portfolio.
   * It is used to sell stock in a flexible portfolio.
   * @param ticker ticker symbol of the stock to be sold.
   * @param quantity quantity of stock to be sold.
   * @param date date on which the stock is sold.
   * @param api StockData object used for fetching stock data.
   * @throws IllegalArgumentException if the method is called by inflexible portfolio.
   */
  void sellStock(String ticker, int quantity, LocalDate date, StockData api);

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
  double costBasis(LocalDate date, StockData api);

  /**
   * Checks if the portfolio is flexible.
   * @return always returns false, as this is an inflexible portfolio.
   */
  boolean isFlexible();
  void load(List<String[]> lines, StockData api);
}
