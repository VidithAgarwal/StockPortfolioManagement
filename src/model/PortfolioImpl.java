package model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

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
      try {
        totalValue += stock.returnPrice(date) * quantity;
      } catch (NullPointerException e) {
        //System.out.println("No price data found for one or more stocks on the date: " + date);
        throw e;
      }

    }
    return totalValue;
  }



  @Override
  public void savePortfolio(String filePath) {
    File file = new File(filePath);
    File parentDir = file.getParentFile();

    if (parentDir != null && !parentDir.exists()) {
      parentDir.mkdirs(); // Create parent directories recursively
    }


    try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
      for (Map.Entry<StockImpl, Integer> entry : this.sharesList.entrySet()) {
        writer.write(entry.getKey().getTicker() + ": " + entry.getValue());
        writer.newLine();
      }
      System.out.println("Portfolio exported to " + filePath + " successfully.");
    } catch (IOException e) {
      System.err.println("Error exporting portfolio to file: " + e.getMessage());
    }
  }



  @Override
  public String getName() {
    return this.portfolioName;
  }

  public static class PortfolioBuilder {
    private Map<StockImpl, Integer> shareList;
    private final String portfolioName;

    public PortfolioBuilder(String portfolioName, int numberOfShare) {
      shareList = new HashMap<>(numberOfShare);
      this.portfolioName = portfolioName;
    }

    public PortfolioBuilder(String portfolioName) {
      shareList = new HashMap<>();
      this.portfolioName = portfolioName;
    }

    public void addShare(String shareName, int quantity) {
      // this.shareList.put(shareName, quantity);
      String tickerSymbol = validateStockName(shareName);

      if (tickerSymbol == null) {
        throw new IllegalArgumentException("Share name not found in nyse_stocks.csv");
      }
      StockImpl stock = new StockImpl(tickerSymbol);
      if (shareList.containsKey(stock)) {
        int existingQuantity = this.shareList.get(stock);
        this.shareList.put(stock, existingQuantity + quantity);
      } else {
        this.shareList.put(stock, quantity);
      }
    }

    private String validateStockName(String shareName) {
      try (BufferedReader reader = new BufferedReader(new FileReader("nyse_stocks.csv"))) {
        String line;
        while ((line = reader.readLine()) != null) {
          String[] parts = line.split(",");
          if (parts.length >= 2) {
            String tickerSymbol = parts[0].trim();
            String companyName = parts[1].trim().replaceAll("\\s", "");

            if (companyName.equalsIgnoreCase(shareName.trim().replaceAll("\\s", ""))) {
              return tickerSymbol;
            }
          }
        }
      } catch (IOException e) {
        System.err.println("Error reading file: " + e.getMessage());
      }
      return null;
    }

    public void load(String filePath) {
      try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
        String line;
        while ((line = reader.readLine()) != null) {
          String[] parts = line.split(","); // Assuming the delimiter is ","
          if (parts.length == 2) {
            String key = parts[0].trim();
            String value = parts[1].trim();
            //validation to check correct key entered that is stock name is remaining.
            String tickerSymbol = validateStockName(key);
            if (tickerSymbol == null) {
              throw new IllegalArgumentException("Share name not found in nyse_stocks.csv");
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
      } catch (IOException e) {
        System.err.println("Error reading file: " + e.getMessage());
      }
    }

    public Portfolio build() {
      return new PortfolioImpl(this.portfolioName, shareList);
    }
  }

}
