package model;

import java.util.Map;

/**
 * Portfolio interface has the methods that are related to single portfolio object.
 * It gets the composition, portfolio value, name of a single portfolio and saves a portfolio.
 */
public interface Portfolio {

  /**
   * portfolio composition method is used to get the composition of a portfolio.
   * The stock name and its quantity is returned for the composition.
   * @return the map of stock name and the quantity of stock.
   */
  Map<String, Integer> portfolioComposition();

  /**
   * portfolioValue method is used to get the value of a portfolio on a particular date.
   * @param date is the date for which the portfolio value is to be determined.
   * @return the price of the portfolio on the particular date entered.
   */
  double portfolioValue(String date);

  /**
   * it gets the name of the portfolio.
   * @return the name of the portfolio.
   */
  String getName();
}
