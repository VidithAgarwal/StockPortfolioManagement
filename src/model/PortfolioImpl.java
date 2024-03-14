package model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PortfolioImpl implements Portfolio {


  private final Map<StockImpl, Integer> sharesList;
  private final String portfolioName;

  private PortfolioImpl(String portfolioName, Map<StockImpl, Integer> list) {
    this.sharesList = new HashMap<>();
    this.portfolioName = portfolioName;
    for (Map.Entry<StockImpl, Integer> entry : list.entrySet()) {
      this.sharesList.put(entry.getKey(), entry.getValue());
    }
  }


  @Override
  public Map<String, Integer> portfolioComposition() {
    Map<String, Integer> composition = new HashMap<>();
    for (Map.Entry<StockImpl, Integer> entry : this.sharesList.entrySet()) {
      StockImpl stock = entry.getKey();
      Integer value = entry.getValue();
      composition.put(stock.getTicker(), value);
    }
    return composition;
  }

  @Override
  public double portfolioValue(String date) {
    double totalValue = 0;
    for (Map.Entry<StockImpl, Integer> entry : this.sharesList.entrySet()) {
      StockImpl stock = entry.getKey();
      Integer quantity = entry.getValue();
      totalValue += stock.returnPrice(date) * quantity;
    }
    return totalValue;
  }


  @Override
  public String getName() {
    return this.portfolioName;
  }

  public static class PortfolioBuilder {
    private final String portfolioName;
    private Map<StockImpl, Integer> shareList;

    public PortfolioBuilder(String portfolioName) {
      shareList = new HashMap<>();
      this.portfolioName = portfolioName;
    }

    public void addShare(String shareName, int quantity) {
      String tickerSymbol = validateStockName(shareName);

      if (tickerSymbol == null) {
        throw new IllegalArgumentException("Share name not found in stocks.csv");
      }
      StockImpl stock = new StockImpl(tickerSymbol);

      boolean flag = false;
      for (Map.Entry<StockImpl, Integer> entry : this.shareList.entrySet()) {
        if (entry.getKey().getTicker().equals(tickerSymbol)) {
          int existingQuantity = this.shareList.get(entry.getKey());
          this.shareList.put(entry.getKey(), existingQuantity + quantity);
          flag = true;
        }
      }

      if (!flag) {
        this.shareList.put(stock, quantity);
      }
    }

    private String validateStockName(String shareName) {
      FileHandler fileHandler = new FileHandler();
      List<String[]> lines = fileHandler.load("stocks.csv");
      for (String[] line : lines) {
        if (line.length >= 2) {
          String tickerSymbol = line[0].trim();
          String companyName = line[1].trim().replaceAll("\\s", "");

          // Check if the entered share name matches either the company name or ticker symbol
          if (companyName.equalsIgnoreCase(shareName.trim().replaceAll("\\s", "")) ||
                  tickerSymbol.equalsIgnoreCase(shareName.trim().replaceAll("\\s", ""))) {
            return tickerSymbol;
          }
        }
      }
      return null; // Return null if no matching ticker symbol or company name is found
    }

    public void load(List<String[]> lines) {

      for (String[] line : lines) {
        if (line.length == 2) {
          String key = line[0].trim();
          String value = line[1].trim();
          //validation to check correct key entered that is stock name is remaining.
          String tickerSymbol = validateStockName(key);
          if (tickerSymbol == null) {
            throw new IllegalArgumentException("Share name not found in stocks.csv");
          }
          // Validate value is a positive whole number
          try {
            int intValue = Integer.parseInt(value);
            if (intValue <= 0) {
              throw new IllegalArgumentException();
            }
          } catch (NumberFormatException e) {
            throw new IllegalArgumentException();
          }
          StockImpl stock = new StockImpl(tickerSymbol);
          this.shareList.put(stock, Integer.parseInt(value));
        } else {
          // Handle invalid line
          throw new IllegalArgumentException();
        }
      }
    }

    public Portfolio build() {
      if (this.shareList.isEmpty()) {
        throw new IllegalArgumentException();
      }
      return new PortfolioImpl(this.portfolioName, shareList);
    }
  }

}
