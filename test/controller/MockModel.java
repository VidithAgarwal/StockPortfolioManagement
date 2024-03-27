package controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import model.Portfolio;
import model.PortfolioDir;
import model.PortfolioImpl;

/**
 * MockModel class is a mock implementation of the PortfolioDir interface.
 * is used for testing purposes.
 */
public class MockModel implements PortfolioDir {

  /**
   * Represents a mock name used for testing purposes.
   */
  private String mockName;

  /**
   * Represents a list of portfolios in the directory.
   */
  private ArrayList<Portfolio> portfolioDirectory = new ArrayList<>();

  /**
   * Represents a mock composition map for testing purposes.
   * map stores stock names as keys and their quantities as values.
   */
  private Map<String, Integer> mockComposition = new HashMap<>();


  private StringBuilder mockOutputFormat = new StringBuilder();

  /**
   * Represents a mock total value for testing purposes.
   */
  private double mockValue;

  /**
   * Represents a mock integer value for testing purposes.
   */
  private int intMockValue;

  /**
   * Represents a logger for recording events and messages.
   */
  private final StringBuilder logger = new StringBuilder();

  /**
   * Represents a mock map for testing purposes.
   */
  private TreeMap<String, String> mockMap = new TreeMap<>();

  /**
   * Represents a mock map with string keys and integer values for testing purposes.
   */
  private TreeMap<String,Integer> mockStringIntMap = new TreeMap<>();

  /**
   * Constructs a MockModel with the specified composition map.
   * @param map The map representing the mock composition.
   * @param mockName The name of the mock, string type.
   * @param mockValue The value of the mock, double type.
   * @param intMockValue The integer value of the mock, integer type.
   * @param mockTreeMap The TreeMap for the mock, treemap of strings.
   * @param mockIntMap The integer TreeMap for the mock, treemap of string,integer.
   */
  public MockModel(Map<String, Integer> map, String mockName, double mockValue, int intMockValue,
                   TreeMap<String,String> mockTreeMap, TreeMap<String,Integer> mockIntMap,
                   StringBuilder mockSave) {
    mockComposition = map;
    this.mockName = mockName;
    this.mockValue = mockValue;
    this.intMockValue = intMockValue;
    mockMap = mockTreeMap;
    mockStringIntMap = mockIntMap;
    mockOutputFormat = mockSave;
  }

  /**
   * Constructs a MockModel with the specified mock value.
   * @param mockValue The mock value.
   */
  public MockModel(double mockValue) {
    this.mockValue = mockValue;
  }

  /**
   * Constructs a MockModel with the specified mock name.
   * @param mockName The mock name.
   */
  public MockModel(String mockName) {
    this.mockName = mockName;
  }

  /**
   * Constructs a MockModel with the specified integer mock value.
   * @param intMockValue The integer mock value.
   */
  public MockModel(int intMockValue) {
    this.intMockValue = intMockValue;
  }

  /**
   * Retrieves the logger.
   * @return The logger.
   */
  public StringBuilder getLogger() {
    return logger;
  }

  /**
   * Adds a new portfolio to the mock portfolio directory.
   * @param newBuilder The builder object used to construct the new portfolio.
   */
  @Override
  public void addPortfolio(PortfolioImpl.PortfolioBuilder newBuilder) {
    portfolioDirectory.add(newBuilder.build());
    logger.append(portfolioDirectory.get(portfolioDirectory.size() - 1).getName()).append('\n');
  }

  /**
   * Creates a flexible portfolio with the given name.
   * @param portfolioName The name of the flexible portfolio.
   */
  @Override
  public void createFlexiblePortfolio(String portfolioName) {
    logger.append("Creating flexible portfolio with name: ")
            .append(portfolioName).append("\n");
  }

  /**
   * Retrieves the list of names of all portfolios in the mock portfolio directory.
   * @return A map containing the name of the mock portfolio.
   */
  @Override
  public Map<String, String> getListOfPortfoliosName() {
    Map<String, String> listOfPortfolios = new HashMap<>();
    listOfPortfolios.put(mockName, "inflexible");
    return listOfPortfolios;
  }

  /**
   * this method retrieves composition of mock portfolio at the specified index for the given date.
   * @param input The index of the mock portfolio.
   * @param date The date for which the composition is retrieved.
   * @return A map containing the composition of the mock portfolio.
   */
  @Override
  public Map<String, Integer> portfolioComposition(int input, LocalDate date) {
    logger.append("Retrieving composition for portfolio at index: ").append(input)
            .append(" on date: ").append(date.toString()).append('\n');
    return mockComposition;
  }

  /**
   * Retrieves the size of the mock portfolio directory.
   * @return The size of the mock portfolio directory.
   */
  @Override
  public int getSize() {
    return portfolioDirectory.size();
  }


  /**
   * Retrieves the value of the mock portfolio at the specified index for the given date.
   * @param input The index of the mock portfolio.
   * @param day The day for which the value is retrieved.
   * @param month The month for which the value is retrieved.
   * @param year The year for which the value is retrieved.
   * @param api The StockData object used for retrieving stock data.
   * @return The value of the mock portfolio at the specified index.
   */
  @Override
  public double portfolioValue(int input, int day, int month, int year, StockData api) {
    logger.append("Retrieving composition for portfolio at index: ").append(input)
            .append(" For " + "the day: ")
            .append(day).append(" month: ")
            .append(month).append(" year: ")
            .append(year);
    return mockValue;
  }


  /**
   * Checks if the mock portfolio directory is empty.
   * @return True if the mock portfolio directory is empty, false otherwise.
   */
  @Override
  public boolean isEmpty() {
    return portfolioDirectory.isEmpty();
  }

  /**
   * Checks if a portfolio with the specified name exists in the mock portfolio directory.
   * @param portfolioName The name of the portfolio to check.
   * @return True if a portfolio with the specified name exists, false otherwise.
   */
  @Override
  public boolean portfolioNameExists(String portfolioName) {
    return false;
  }

  /**
   * this method buys a specified quantity of a stock for portfolio at given index.
   * @param input  index of the portfolio.
   * @param stock  ticker symbol of the stock to buy.
   * @param quantity  quantity of the stock to buy.
   * @param buyDate  date of the purchase.
   * @param api  StockData object used for retrieving stock data.
   */
  @Override
  public void buyStock(int input, String stock, int quantity, LocalDate buyDate, StockData api) {
    logger.append("Buying ").append(quantity).append(" shares of ").append(stock)
            .append(" for portfolio at index: ").append(input)
            .append(" on ").append(buyDate.toString()).append("\n");
  }

  /**
   * this method sells a specified quantity of a stock for portfolio at given index.
   * @param input index of the portfolio.
   * @param stock  ticker symbol of the stock to sell.
   * @param quantity  quantity of the stock to sell.
   * @param sellDate  date of the sale.
   * @param api StockData object used for retrieving stock data.
   */
  @Override
  public void sellStock(int input, String stock, int quantity, LocalDate sellDate, StockData api) {
    logger.append("Selling ").append(quantity).append(" shares of ").append(stock)
            .append(" for portfolio at index: ").append(input)
            .append(" on ").append(sellDate.toString()).append("\n");
  }

  /**
   * this method calculates cost basis of the portfolio at specified index for given date.
   * @param input index of the portfolio.
   * @param date date for which the cost basis is calculated.
   * @param api StockData object used for retrieving stock data.
   * @return cost basis of the portfolio at the specified index.
   */
  @Override
  public double costBasis(int input, LocalDate date, StockData api) {
    logger.append("Calculating cost basis for portfolio at index: ").append(input)
            .append("on date: ").append(date.toString()).append('\n');
    return mockValue;
  }


  /**
   * this method calculates gain or loss for a specified stock on a given date.
   * @param tickerSymbol The ticker symbol of the stock.
   * @param date date for which the gain or loss is calculated.
   * @param api StockData object used for retrieving stock data.
   * @return gain or loss for the specified stock.
   */
  @Override
  public String gainOrLose(String tickerSymbol, LocalDate date, StockData api) {
    logger.append("Calculating gain or loss for ").append(tickerSymbol)
            .append(" on ").append(date.toString()).append("\n");
    return mockName;
  }


  /**
   * this method calculates gain or loss for a specified stock over a period of time.
   * @param tickerSymbol The ticker symbol of the stock.
   * @param date1 The start date of the period.
   * @param date2 The end date of the period.
   * @param api The StockData object used for retrieving stock data.
   * @return The gain or loss for the specified stock over the period.
   */
  @Override
  public String gainOrLoseOverAPeriod(String tickerSymbol, LocalDate date1, LocalDate date2,
                                      StockData api) {
    logger.append("Calculating gain or loss for ").append(tickerSymbol)
            .append(" from ").append(date1.toString()).append(" to ")
            .append(date2.toString()).append("\n");
    return mockName;
  }

  /**
   * this method calculates x-day moving average for a specified stock on a given date.
   * @param tickerSymbol  ticker symbol of the stock.
   * @param date  date for which the moving average is calculated.
   * @param x  number of days for the moving average.
   * @param api  StockData object used for retrieving stock data.
   * @return  x-day moving average for the specified stock.
   */
  @Override
  public double xDayMovingAvg(String tickerSymbol, LocalDate date, int x, StockData api) {
    logger.append("Calculating ").append(x).append("-day moving average for ")
            .append(tickerSymbol).append(" on ").append(date.toString()).append("\n");
    return mockValue;
  }


  /**
   * this method calculates crossover over a period of time for a specified stock.
   * @param tickerSymbol ticker symbol of the stock.
   * @param api  StockData object used for retrieving stock data.
   * @param startDate  start date of the period.
   * @param endDate  end date of the period.
   * @return  TreeMap containing the crossover information over the period.
   */
  @Override
  public TreeMap<String, String> crossoverOverPeriod(String tickerSymbol, StockData api,
                                                     LocalDate startDate, LocalDate endDate) {
    logger.append("Calculating crossover over period for ").append(tickerSymbol)
            .append(" from ").append(startDate.toString()).append(" to ")
            .append(endDate.toString()).append("\n");
    return mockMap;
  }

  /**
   * this method calculates moving crossover for a specified stock over a period of time.
   * @param tickerSymbol ticker symbol of the stock.
   * @param api  StockData object used for retrieving stock data.
   * @param startDate  start date of the period.
   * @param endDate  end date of the period.
   * @param x  value of the shorter moving average.
   * @param y  value of the longer moving average.
   * @return  TreeMap containing the moving crossover information.
   */
  @Override
  public TreeMap<String, String> movingCrossOver(String tickerSymbol, StockData api,
                              LocalDate startDate, LocalDate endDate, int x, int y) {
    logger.append("Calculating moving crossover for ").append(tickerSymbol)
            .append(" from ").append(startDate.toString()).append(" to ")
            .append(endDate.toString()).append(" for x = ").append(x).append(" and y = ")
            .append(y).append(
                    "\n");
    return mockMap;
  }

  /**
   * this method calculates performance of a specified stock over a given period.
   * @param stock ticker symbol of the stock.
   * @param api StockData object used for retrieving stock data.
   * @param start start date of the period.
   * @param end end date of the period.
   * @return TreeMap containing the performance data for the stock over the period.
   */
  @Override
  public TreeMap<String, Integer> stockPerformance(String stock, StockData api,
                                                   LocalDate start, LocalDate end) {
    logger.append("Calculating stock performance for ").append(stock)
            .append(" from ").append(start.toString()).append(" to ")
            .append(end.toString()).append("\n");
    return mockStringIntMap;
  }

  /**
   * this method calculates performance of a portfolio over a given period.
   * @param input index of the portfolio.
   * @param start start date of the period.
   * @param end end date of the period.
   * @return TreeMap containing the performance data for the portfolio over the period.
   */
  @Override
  public TreeMap<String, Integer> portfolioPerformance(int input, LocalDate start, LocalDate end) {
    logger.append("Calculating portfolio performance for portfolio at index: ").append(input)
            .append(" from ").append(start.toString()).append(" to ")
            .append(end.toString()).append("\n");
    return mockStringIntMap;
  }

  /**
   * this method scales the data for stock performance calculation over a given period.
   * @param stock ticker symbol of the stock.
   * @param api StockData object used for retrieving stock data.
   * @param start start date of the period.
   * @param end end date of the period.
   * @return scaled value for stock performance.
   */
  @Override
  public int scaleForStockPerformance(String stock, StockData api, LocalDate start, LocalDate end) {
    logger.append("Scaling for stock performance of ").append(stock)
            .append(" from ").append(start.toString()).append(" to ")
            .append(end.toString()).append("\n");
    return intMockValue;
  }


  /**
   * this method scales data for portfolio performance calculation over a given period.
   * @param input index of the portfolio.
   * @param start start date of the period.
   * @param end end date of the period.
   * @return scaled value for portfolio performance.
   */
  @Override
  public int scaleForPortfolioPerformance(int input, LocalDate start, LocalDate end) {
    logger.append("Scaling for portfolio performance at index: ").append(input)
            .append(" from ").append(start.toString()).append(" to ")
            .append(end.toString()).append("\n");
    return intMockValue;
  }

  /**
   * this method saves portfolio at the specified index.
   * @param input index of the portfolio to be saved.
   * @return StringBuilder containing data about the portfolio to be saved.
   */
  @Override
  public StringBuilder save(int input) {
    logger.append("Portfolio to be saved is at index: ").append(input).append("\n");
    return mockOutputFormat;
  }

  /**
   * this method loads a portfolio with the specified name and lines.
   * @param portfolioName name of the portfolio to be loaded.
   * @param lines list of lines representing the portfolio data.
   * @param api StockData object used for retrieving stock data.
   */
  @Override
  public void loadPortfolio(String portfolioName, List<String[]> lines, StockData api) {
    logger.append("Name of portfolio to be saved: ").append(portfolioName).append(" and "
            + "lines to be appended are: ").append(lines).append("\n");

  }
}
