package controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
  private final StringBuilder logger = new StringBuilder();


  /**
   * Constructs a MockModel with the specified composition map.
   * @param map The map representing the mock composition.
   */
  public MockModel(Map<String, Integer> map, String mockName, double mockValue) {
    mockComposition = map;
    this.mockName = mockName;
    this.mockValue = mockValue;
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
   * Retrieves the list of names of all portfolios in the mock portfolio directory.
   * @return An ArrayList containing the name of the mock portfolio.
   */
  @Override
  public ArrayList<String> getListOfPortfoliosName() {
    ArrayList<String> listOfPortfolios = new ArrayList<>();
    listOfPortfolios.add(mockName);
    return listOfPortfolios;
  }

  /**
   * Retrieves the composition of the mock portfolio at the specified index.
   * @param input The index of the mock portfolio.
   * @return A map containing the composition of the mock portfolio.
   */
  @Override
  public Map<String, Integer> portfolioComposition(int input) {
    logger.append("Retrieving composition for portfolio at index: ").append(input).append('\n');
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
}
