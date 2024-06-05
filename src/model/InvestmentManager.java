package model;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import controller.IStockData;
import controller.StockData;

/**
 * The InvestmentManager interface represents a portfolio manager.
 * It provides methods for managing portfolios, such as adding portfolio, getting list of portfolio.
 * as well as retrieving portfolio composition and total value of portfolio in portfolio directory.
 * It also shows the stock trends, and buy, sell, cost basis option for flexible portfolio.
 * additionally with above methods for both inflexible and flexible portfolio.
 */
public interface InvestmentManager {

  /**
   * Adds a new portfolio to the portfolio directory.
   * @param newBuilder The builder object used to construct the new portfolio.
   */
  void addPortfolio(InflexiblePortfolioImpl.PortfolioBuilder newBuilder);

  /**
   * this method creates a flexible portfolio with given name.
   * @param portfolioName  name of the flexible portfolio to be created.
   */
  void createFlexiblePortfolio(String portfolioName);

  /**
   * Retrieves the list of names of all portfolios in the directory, currently created.
   * @return Map of portfolio names and their types.
   */
  Map<String, String> getListOfPortfoliosName();

  /**
   * this method retrieves the composition of the portfolio at the specified index.
   * @param input  index of the portfolio.
   * @param date  date for which composition is to be retrieved.
   * @return Map containing the composition of the portfolio (share name and quantity).
   */
  Map<String, Double> portfolioComposition(int input, LocalDate date);

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
   * @param month The month of the date for which total value is to be found.
   * @param year The year of the date for which total value is to be found.
   * @param api stockData object for fetching stock prices to calculate total value.
   * @return total value of the portfolio.
   */
  double portfolioValue(int input, int day, int month, int year, IStockData api);

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

  /**
   * this method helps in buying stocks and adds them to the specified portfolio.
   * @param input index of the portfolio in which stocks are bought.
   * @param stock name of the stock to buy.
   * @param quantity quantity of the stock to buy.
   * @param buyDate date of the purchase.
   * @param api StockData object for fetching that stock prices.
   */
  void buyStock(int input, String stock, int quantity, LocalDate buyDate, IStockData api);

  /**
   * this method sells stocks from specified portfolio.
   * @param input index of the portfolio, from which stocks are sold.
   * @param stock name of the stock to sell.
   * @param quantity quantity of the stock to sell.
   * @param sellDate date of the selling of stock.
   * @param api StockData object for fetching stock prices.
   */
  void sellStock(int input, String stock, int quantity, LocalDate sellDate, IStockData api);

  /**
   * this method calculates cost basis of the portfolio at the specified index for the given date.
   * @param input index of the portfolio, for which cost basis is to be found.
   * @param date date for which the cost basis is to be calculated.
   * @param api StockData object for fetching that stock prices.
   * @return cost basis of the portfolio.
   */
  double costBasis(int input, LocalDate date, IStockData api);

  /**
   * this method calculates gain or loss of a stock in portfolio at specified index for given date.
   * @param tickerSymbol ticker symbol of the stock.
   * @param date date for which the gain or loss is to be calculated.
   * @param api StockData object for fetching that stock prices.
   * @return string indicating whether the stock gained or lost.
   */
  String gainOrLose(String tickerSymbol, LocalDate date, IStockData api);

  /**
   * this method calculates gain or loss of a stock in portfolio over a specified period.
   * @param tickerSymbol The ticker symbol of the stock.
   * @param date1 The start date of the period.
   * @param date2 The end date of the period.
   * @param api The StockData object for fetching stock prices.
   * @return A string indicating whether the stock gained or lost over the period.
   */
  String gainOrLoseOverAPeriod(String tickerSymbol, LocalDate date1, LocalDate date2,
                               IStockData api);

  /**
   * this method calculates X-day moving average for a given stock on a specific date.
   * @param tickerSymbol ticker symbol of stock.
   * @param date date for which the moving average is calculated.
   * @param x number of days to consider for the moving average.
   * @param api StockData object for fetching that stock prices.
   * @return X-day moving average of the stock on the specified date.
   */
  double xDayMovingAvg(String tickerSymbol, LocalDate date, int x, IStockData api);

  /**
   * this method calculates crossover events over a specified period for a given stock.
   * crossover event occurs when stock's price crosses over its moving average.
   * @param tickerSymbol ticker symbol of the stock.
   * @param api StockData object for fetching stock prices.
   * @param startDate start date of the period.
   * @param endDate  end date of the period.
   * @return TreeMap containing dates of crossover events and whether it is buy/ sell opportunity.
   */
  TreeMap<String, String> crossoverOverPeriod(String tickerSymbol,
                                              IStockData api,
                                              LocalDate startDate, LocalDate endDate);

  /**
   * this method calculates moving crossovers over specified period for given stock.
   * moving crossover occurs when one moving average crosses over another moving average.
   * @param tickerSymbol ticker symbol of stock.
   * @param api StockData object for fetching that stock prices.
   * @param startDate start date of the period.
   * @param endDate end date of the period.
   * @param x number of days for the first moving average.
   * @param y number of days for the second moving average.
   * @return TreeMap containing dates of moving crossovers and whether it is buy/ sell opportunity.
   */
  TreeMap<String, String> movingCrossOver(String tickerSymbol, IStockData api, LocalDate startDate,
                                          LocalDate endDate, int x, int y);

  /**
   * this method calculates performance of a specific stock over a given period.
   * @param stock name of the stock.
   * @param api StockData object for fetching stock prices.
   * @param start start date of the period.
   * @param end end date of the period.
   * @return TreeMap containing dates & values determined by scale for bar chart representation.
   */
  TreeMap<String, Integer> stockPerformance(String stock, IStockData api,
                                            LocalDate start, LocalDate end) ;

  /**
   * this method calculates performance of a portfolio over a given period.
   * @param input index of the portfolio, for which performance is to be found.
   * @param start start date of the period.
   * @param end end date of the period.
   * @return TreeMap containing dates & values determined by scale for bar chart representation.
   */
  TreeMap<String, Integer> portfolioPerformance( int input,
                                                 LocalDate start,  LocalDate end);

  /**
   * this method determines appropriate scale for displaying performance of a specific stock.
   * scale is determined by the highest value of stock on a date and used to find scale.
   * @param stock name of the stock.
   * @param api StockData object for fetching stock prices.
   * @param start start date of the period.
   * @param end end date of the period.
   * @return appropriate scale factor for displaying the performance.
   */
  int scaleForStockPerformance(String stock, IStockData api,
                               LocalDate start, LocalDate end);

  /**
   * this method determines appropriate scale for displaying the performance of a portfolio.
   * scale is determined by the highest value of portfolio on a date and used to find scale.
   * @param input index of the portfolio, for which performance and scale is found.
   * @param start start date of the period.
   * @param end end date of the period.
   * @return appropriate scale factor for displaying the performance.
   */
  int scaleForPortfolioPerformance(int input, LocalDate start,  LocalDate end);

  /**
   * this method saves portfolio data associated with specified index to a StringBuilder object.
   * saved data includes symbol and quantity of shares in the portfolio.
   * and additionally save transaction data and date for flexible portfolio.
   * @param input The index of the portfolio for which data is to be saved.
   * @return StringBuilder object containing the saved portfolio data in CSV format.
   */
  StringBuilder save(int input);

  /**
   * this method loads portfolio data from a list of string arrays representing lines of data,
   * using the provided portfolio name, StockData object for fetching stock data,
   * and associates it with the specified portfolio.
   * @param portfolioName name of the portfolio to load data into.
   * @param lines  list of string arrays representing lines of portfolio data to be loaded.
   * @param api StockData object used for fetching stock data.
   */
  void loadPortfolio(String portfolioName, List<String[]> lines, IStockData api);


  /**
   * Adds a new investment strategy to portfolio in portfolio directory.
   * executes dollar-cost averaging investment strategy for the given portfolio.
   * @param input          index of the portfolio in the portfolio directory.
   * @param buyingList     map of stocks to buy, along with their respective percentages.
   * @param startDate      start date of the investment strategy.
   * @param endDate        end date of the investment strategy.
   * @param frequencyDays  number of days between each investment.
   * @param amount        total investment amount.
   * @param api           stock data API to fetch historical prices.
   */
  void createDollarCostAverageStrategy(int input, Map<String, Double> buyingList,
                                       LocalDate startDate,
                                       LocalDate endDate,
                                       int frequencyDays, double amount,
                                       StockData api);

  /**
   * this method executes an investment using Dollar Cost Averaging (DCA) strategy.
   * @param input         frequency of investment, e.g., monthly, weekly, based on number of days.
   * @param investingList  map containing stocks and their corresponding percentages of investment.
   * @param date          date of investment.
   * @param amount        total amount to be invested.
   * @param api           interface for accessing stock data.
   */
  void investWithDCAStrategy(int input, Map<String, Double> investingList, LocalDate date,
                             double amount, StockData api);

}
