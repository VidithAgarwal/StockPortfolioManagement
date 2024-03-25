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
 * The MockModel class is a mock implementation of the PortfolioDir interface.
 * It is used for testing purposes.
 */
public class MockModel implements PortfolioDir {

  private String mockName;
  private ArrayList<Portfolio> portfolioDirectory = new ArrayList<>();
  private Map<String, Integer> mockComposition = new HashMap<>();
  private double mockValue;

  private int intMockValue;
  private final StringBuilder logger = new StringBuilder();

  private TreeMap<String, String> mockTreeMap = new TreeMap<>();

  /**
   * Constructs a MockModel with the specified composition map.
   * @param map The map representing the mock composition.
   */
  public MockModel(Map<String, Integer> map, String mockName, double mockValue, int intMockValue) {
    mockComposition = map;
    this.mockName = mockName;
    this.mockValue = mockValue;
    this.intMockValue = intMockValue;
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

  public MockModel(int intMockValue) {this.intMockValue = intMockValue;}

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

  @Override
  public void createFlexiblePortfolio(String portfolioName) {
    logger.append("Creating flexible portfolio with name: ")
            .append(portfolioName).append("\n");
  }

  @Override
  public Map<String, String> getListOfPortfoliosName() {
    Map<String, String> listOfPortfolios = new HashMap<>();
    listOfPortfolios.put(mockName, "inflexible");
    return listOfPortfolios;
  }

  /**
   * Retrieves the list of names of all portfolios in the mock portfolio directory.
   * @return An ArrayList containing the name of the mock portfolio.
   */
//  @Override
//  public ArrayList<String> getListOfPortfoliosName() {
//    ArrayList<String> listOfPortfolios = new ArrayList<>();
//    listOfPortfolios.add(mockName);
//    return listOfPortfolios;
//  }

  /**
   * Retrieves the composition of the mock portfolio at the specified index.
   * @param input The index of the mock portfolio.
   * @return A map containing the composition of the mock portfolio.
   */
//  @Override
//  public Map<String, Integer> portfolioComposition(int input) {
//    logger.append("Retrieving composition for portfolio at index: ").append(input).append('\n');
//    return mockComposition;
//  }

  @Override
  public Map<String, Integer> portfolioComposition(int input, LocalDate date) {
    logger.append("Retrieving composition for portfolio at index: ").append(input)
            .append("on date: ").append(date.toString()).append('\n');
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

  @Override
  public void buyStock(int input, String stock, int quantity, LocalDate buyDate, StockData api) {
    logger.append("Buying ").append(quantity).append(" shares of ").append(stock)
            .append(" for portfolio at index: ").append(input)
            .append(" on ").append(buyDate.toString()).append("\n");
  }

  @Override
  public void sellStock(int input, String stock, int quantity, LocalDate sellDate, StockData api) {
    logger.append("Selling ").append(quantity).append(" shares of ").append(stock)
            .append(" for portfolio at index: ").append(input)
            .append(" on ").append(sellDate.toString()).append("\n");
  }

  @Override
  public double costBasis(int input, LocalDate date, StockData api) {
    logger.append("Calculating cost basis for portfolio at index: ").append(input)
            .append("on date: ").append(date.toString()).append('\n');
    return mockValue;
  }

  @Override
  public String gainOrLose(String tickerSymbol, LocalDate date, StockData api) {
    logger.append("Calculating gain or loss for ").append(tickerSymbol)
            .append(" on ").append(date.toString()).append("\n");
    return mockName;
  }

  @Override
  public String gainOrLoseOverAPeriod(String tickerSymbol, LocalDate date1, LocalDate date2, StockData api) {
    logger.append("Calculating gain or loss for ").append(tickerSymbol)
            .append(" from ").append(date1.toString()).append(" to ")
            .append(date2.toString()).append("\n");
    return mockName;
  }

  @Override
  public double xDayMovingAvg(String tickerSymbol, LocalDate date, int x, StockData api) {
    logger.append("Calculating ").append(x).append("-day moving average for ")
            .append(tickerSymbol).append(" on ").append(date.toString()).append("\n");
    return mockValue;
  }

  @Override
  public TreeMap<String, String> crossoverOverPeriod(String tickerSymbol, StockData api, LocalDate startDate, LocalDate endDate) {
    logger.append("Calculating crossover over period for ").append(tickerSymbol)
            .append(" from ").append(startDate.toString()).append(" to ")
            .append(endDate.toString()).append("\n");
    return null;
  }

  @Override
  public TreeMap<String, String> movingCrossOver(String tickerSymbol, StockData api, LocalDate startDate, LocalDate endDate, int x, int y) {
    logger.append("Calculating moving crossover for ").append(tickerSymbol)
            .append(" from ").append(startDate.toString()).append(" to ")
            .append(endDate.toString()).append("\n");
    return null;
  }

  @Override
  public TreeMap<String, Integer> stockPerformance(String stock, StockData api, LocalDate start, LocalDate end) {
    logger.append("Calculating stock performance for ").append(stock)
            .append(" from ").append(start.toString()).append(" to ")
            .append(end.toString()).append("\n");
    return null;
  }

  @Override
  public TreeMap<String, Integer> portfolioPerformance(int input, LocalDate start, LocalDate end) {
    logger.append("Calculating portfolio performance for portfolio at index: ").append(input)
            .append(" from ").append(start.toString()).append(" to ")
            .append(end.toString()).append("\n");
    return null;
  }

  @Override
  public int scaleForStockPerformance(String stock, StockData api, LocalDate start, LocalDate end) {
    logger.append("Scaling for stock performance of ").append(stock)
            .append(" from ").append(start.toString()).append(" to ")
            .append(end.toString()).append("\n");
    return intMockValue;
  }

  @Override
  public int scaleForPortfolioPerformance(int input, LocalDate start, LocalDate end) {
    logger.append("Scaling for portfolio performance at index: ").append(input)
            .append(" from ").append(start.toString()).append(" to ")
            .append(end.toString()).append("\n");
    return intMockValue;
  }

  @Override
  public StringBuilder save(int input) {
    return null;
  }

  @Override
  public void loadPortfolio(String portfolioName, List<String[]> lines, StockData api) {

  }
}
