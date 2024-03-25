package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import controller.StockData;

/**
 * An abstract class implementing common functionality for flexible and inflexible portfolio.
 */
abstract class AbstractPortfolio implements Portfolio {

  /**
   * The name of the portfolio.
   */
  protected final String portfolioName;

  /**
   * constructs an AbstractPortfolio object with the specified name.
   * @param portfolioName name of the portfolio.
   */
  protected AbstractPortfolio(String portfolioName) {
    this.portfolioName = portfolioName;
  }

  /**
   * retrieves name of the portfolio.
   * @return name of the portfolio.
   */
  @Override
  public String getName() {
    return portfolioName;
  }

  /**
   * this method retrieves closing price of a stock on a specified date.
   * @param ticker ticker symbol of the stock.
   * @param api  StockData object used to fetch historical data.
   * @param date date for which the closing price is to be retrieved.
   * @return closing price of the stock on the specified date.
   */
  protected double getClosingPriceOnDate(String ticker, StockData api, String date) {
    Map<String, ArrayList<Double>> priceData = api.fetchHistoricalData(ticker);
    return priceData.get(date).get(3);
  }

  /**
   * this method computes total value of the portfolio on a specified date.
   * @param date date for which the portfolio value is to be computed.
   * @param composition composition of the portfolio (stock ticker symbols and quantities).
   * @param api StockData object used to fetch historical data.
   * @return total value of the portfolio on the specified date.
   */
  protected double computeValue(String date, Map<String, Integer> composition, StockData api) {
    double totalValue = 0;
    for (Map.Entry<String, Integer> entry : composition.entrySet()) {
      String ticker = entry.getKey();
      Integer quantity = entry.getValue();
      try {
        Double closingPrice = getClosingPriceOnDate(ticker, api, date);
        totalValue += closingPrice * quantity;
      } catch (RuntimeException e) {
        throw new IllegalArgumentException(ticker);
      }
    }
    return totalValue;
  }

  /**
   * this method validates if the share name exists in the stocks.csv file.
   * @param shareName The name of the share to be validated.
   * @return ticker symbol of the share if found, otherwise null.
   */
  static String validateStockName(String shareName) {
    FileHandler fileHandler = new FileHandler();
    List<String[]> lines = fileHandler.load("stocks.csv");
    for (String[] line : lines) {
      if (line.length >= 2) {
        String tickerSymbol = line[0].trim();
        String companyName = line[1].trim().replaceAll("\\s", "");

        // Check if the entered share name matches either the company name or ticker symbol
        if (companyName.equalsIgnoreCase(shareName.trim().replaceAll("\\s", ""))
                || tickerSymbol.equalsIgnoreCase(shareName.trim().replaceAll("\\s",
                ""))) {
          return tickerSymbol;
        }
      }
    }
    return null; // Return null if no matching ticker symbol or company name is found
  }

  /**
   * this method creates a deep copy of a map.
   * @param map1 the map to be copied.
   * @return new map containing same key-value pairs as input map.
   */
  protected Map<String, Integer> deepCopy(Map<String, Integer> map1) {
    Map<String, Integer> map2 = new HashMap<>();
    for (Map.Entry<String, Integer> entry : map1.entrySet()) {
      map2.put(entry.getKey(), entry.getValue());
    }
    return map2;
  }
}
