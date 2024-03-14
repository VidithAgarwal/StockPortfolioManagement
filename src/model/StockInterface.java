package model;

/**
 * StockInterface class is used to implement methods for fetching the stock price data.
 */
public interface StockInterface {

  /**
   * return price method is used to get the price of a particular stock for entered date.
   * @param date is the date for which user wants to get the stock price.
   * @return the price value of the stock on a particular date.
   */
  double returnPrice(String date);
}
