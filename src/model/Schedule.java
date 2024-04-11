package model;

import java.time.LocalDate;
import java.util.Map;

/**
 * Interface representing a buying strategy for investments .
 * This class represents ability to invest a specific amount in an existing flexible portfolio on.
 * a specific date by specifying the weights of how that money should be invested in each stock.
 * inside that portfolio.
 */
interface Schedule {

  /**
   * this method returns amount of money to be invested in each transaction.
   * @return amount of money to invest.
   */
  double getAmount();

  /**
   * the method returns frequency of transactions in days.
   * @return frequency of transactions in days.
   */
  int getFrequencyDays();

  /**
   * the method returns the start date of the buying strategy.
   * @return start date of the buying strategy.
   */
  LocalDate getStartDate();

  /**
   * the method returns the end date of the buying strategy.
   * @return end date of the buying strategy.
   */
  LocalDate getEndDate();

  /**
   * the method returns the date when the strategy was last executed.
   * @return last execution date.
   */
  LocalDate getLastRunDate();

  /**
   * the method returns a map representing buying list, where keys are symbols of assets to buy
   * and values are corresponding weights to invest in each asset.
   * @return buying list as a map.
   */
  Map<String, Double> getBuyingList();

  String getName();

  void setLastRunDate(LocalDate date);
}
