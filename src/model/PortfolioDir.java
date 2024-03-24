package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

import controller.StockData;

/**
 * The PortfolioDir interface represents a directory of portfolios.
 * It provides methods for managing portfolios, such as adding portfolio, getting list of portfolio.
 * as well as retrieving portfolio composition and total value of portfolio in portfolio directory.
 */
public interface PortfolioDir {

  /**
   * Adds a new portfolio to the portfolio directory.
   * @param newBuilder The builder object used to construct the new portfolio.
   */
  void addPortfolio(PortfolioImpl.PortfolioBuilder newBuilder);

  void createFlexiblePortfolio(String portfolioName);

  /**
   * Retrieves the list of names of all portfolios in the directory.
   * @return the list of portfolio names.
   */
  Map<String, String> getListOfPortfoliosName();

  /**
   * Retrieves the composition of the portfolio at the specified index in the directory.
   * @param input the index of the portfolio.
   * @return map containing composition of the portfolio (share name and quantity in a portfolio.)
   */
  Map<String, Integer> portfolioComposition(int input, LocalDate date);

  /**
   * Retrieves the number of portfolios in the directory.
   * @return the number of portfolios in the directory list.
   */
  int getSize();

  /**
   * portfolio value method gets the total value of portfolio present in portfolio directory at.
   * specified index for a given date
   * @param input the index of the portfolio in portfolio directory.
   * @param day the day of date for which total value is to be found.
   * @param month the month of date for which total value is to be found.
   * @param year the year of date for which total value is to be found.
   * @return the total value of portfolio.
   */
  double portfolioValue(int input, int day, int month, int year, StockData api);

  /**
   * Checks if the directory is empty (contains no portfolios).
   * @return true if the directory is empty, or else false.
   */
  boolean isEmpty();

  /**
   * checks if a portfolio name exists or not in the portfolio directory.
   * @param portfolioName the portfolio name that is to be checked in the directory.
   * @return true if portfolio name is present or returns false.
   */
  boolean portfolioNameExists(String portfolioName);

  void buyStock(int input, String stock, int quantity, LocalDate buyDate, StockData api);
  void sellStock(int input, String stock, int quantity, LocalDate sellDate, StockData api);

  double costBasis(int input, LocalDate date, StockData api);

  String gainOrLose(String tickerSymbol, LocalDate date, StockData api);
  String gainOrLoseOverAPeriod(String tickerSymbol, LocalDate date1, LocalDate date2, StockData api);

  double xDayMovingAvg(String tickerSymbol, LocalDate date, int x, StockData api);
  TreeMap<String, String> crossoverOverPeriod (String tickerSymbol,
                                           StockData api,
                                           LocalDate startDate, LocalDate endDate);

  TreeMap<String, String> movingCrossOver (String tickerSymbol, StockData api, LocalDate startDate,
                                           LocalDate endDate
          , int x, int y);

  TreeMap<String, Integer> stockPerformance (String stock, StockData api,
                                             LocalDate start, LocalDate end) ;

  TreeMap<String, Integer> portfolioPerformance ( int input,
                                                         LocalDate start,  LocalDate end);

}
