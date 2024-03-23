package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import controller.StockData;

abstract class AbstractPortfolio implements Portfolio {
  protected final String portfolioName;

  protected AbstractPortfolio(String portfolioName) {
    this.portfolioName = portfolioName;
  }

  @Override
  public String getName() {
    return portfolioName;
  }

  protected double getClosingPriceOnDate(String ticker, StockData api, String date) {
    Map<String, ArrayList<Double>> priceData = api.fetchHistoricalData(ticker);
    return priceData.get(date).get(1);
  }

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

  protected Map<String, Integer> deepCopy(Map<String, Integer> map1) {
    Map<String, Integer> map2 = new HashMap<>();
    for (Map.Entry<String, Integer> entry : map1.entrySet()) {
      map2.put(entry.getKey(), entry.getValue());
    }
    return map2;
  }
}
