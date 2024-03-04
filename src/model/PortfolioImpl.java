package model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.json.JSONArray;
import org.json.JSONObject;

public class PortfolioImpl implements Portfolio {


  private final Map<String, Integer> sharesList;
  private final String portfolioName;

  private PortfolioImpl(String portfolioName, Map<String, Integer> list) {
    sharesList = new HashMap<>();
    this.portfolioName = portfolioName;
    for (Map.Entry<String, Integer> entry : list.entrySet()) {
      this.sharesList.put(entry.getKey(), entry.getValue());
    }
  }




@Override
  public Map<String, Integer> portfolioComposition() {
    return this.sharesList;
  }

  @Override
  public float portfolioValue(String portfolioName, String date) {
    return 0;
  }

  //replace it with portfoliovalue of interface
//  private double portfolioValues(String portfolioName, String date) {
//    PortfolioImpl portfolio = getPortfolio(portfolioName);
//    Map<String, Integer> sharesList = portfolio.getSharesList();
//
//    double totalValue = 0;
//    double price;
//
//    for (Map.Entry<String, Integer> entry : sharesList.entrySet()) {
//      String stockName = entry.getKey();
//      int quantity = entry.getValue();
//
//      // Check if the data for this stock exists in the map
//      if (!sharesList.containsKey(stockName)) {
//        // If the data does not exist, call the API to get the data
//         price = getPrice(stockName, date);
//        sharesList.put(stockName, price);
//      } else {
//         price = sharesList.get(stockName);
//      }
//
//      totalValue += price * quantity;
//    }
//
//    return totalValue;
//  }

//  private PortfolioImpl getPortfolio(String portfolioName) {
//   // return PortfolioImpl.getPortfolioByName(portfolioName);
//  }

  private static double getPrice(String stockName, String date) {
    String API_KEY = "GSxm0cOzHGUXHmBTb_wteC5_Ag1eBCSt";
    String BASE_URL = "https://api.polygon.io/v3/reference/tickers";
    try {
      HttpClient client = HttpClient.newHttpClient();
      HttpRequest request = HttpRequest.newBuilder()
              .uri(URI.create(BASE_URL + stockName + "?apiKey=" + API_KEY + "&date=" + date))
              .build();

      HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
      JSONObject data = new JSONObject(response.body());

      // Get the closing price from the API response
      double price = data.getDouble("close");

      return price;
    } catch (Exception e) {
      e.printStackTrace();
    }

    return 0;
  }

  // to be replaced with portfoliovalue method
  public static double getTotalValue(Map<String, Integer> portfolio, String date) {
    double totalValue = 0;
    double price;
    for (Map.Entry<String, Integer> entry : portfolio.entrySet()) {
      String stockName = entry.getKey();
      int quantity = entry.getValue();

      if (!portfolio.containsKey(stockName)) {

        price = getPrice(stockName, date);
       // portfolio.put(stockName, price);
      } else {
        price = portfolio.get(stockName);
      }

      totalValue += price * quantity;
    }

    return totalValue;
  }

  

  private List<String> fetchStockNames() {
     String API_KEY = "GSxm0cOzHGUXHmBTb_wteC5_Ag1eBCSt";
     String BASE_URL = "https://api.polygon.io/v3/reference/tickers";

    List<String> stockNames = new ArrayList<>();

    try {
      HttpClient client = HttpClient.newHttpClient();
      HttpRequest request = HttpRequest.newBuilder()
              .uri(URI.create(BASE_URL + "?apiKey=" + API_KEY))
              .build();

      HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
      JSONArray tickers = new JSONObject(response.body()).getJSONArray("results");

      for (int i = 0; i < tickers.length(); i++) {
        JSONObject ticker = tickers.getJSONObject(i);
        String name = ticker.getString("name");
        stockNames.add(name);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }

    return stockNames;
  }

  private boolean isValidStockName(String stockName) {
    List<String> stockNames = fetchStockNames();
    return stockNames.contains(stockName);
  }

  @Override
  public void savePortfolio(String filePath) {
    File file = new File(filePath);
    File parentDir = file.getParentFile();

    if (parentDir != null && !parentDir.exists()) {
      parentDir.mkdirs(); // Create parent directories recursively
    }


    try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
      for (Map.Entry<String, Integer> entry : this.sharesList.entrySet()) {
        writer.write(entry.getKey() + ": " + entry.getValue());
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
    private Map<String, Integer> shareList;
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
      this.shareList.put(shareName, quantity);
    }

    public void load(String filePath) {
      try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
        String line;
        while ((line = reader.readLine()) != null) {
          String[] parts = line.split(":"); // Assuming the delimiter is ":"
          if (parts.length == 2) {
            String key = parts[0].trim();
            int value = Integer.parseInt(parts[1].trim());
            this.shareList.put(key, value);
          } else {
            // Handle invalid lines if needed
            System.err.println("Invalid line: " + line);
          }
        }
      } catch (IOException e) {
        System.err.println("Error reading file: " + e.getMessage());
      } catch (NumberFormatException e) {
        System.err.println("Invalid number format in file: " + e.getMessage());
      }
    }

    public Portfolio build() {
      return new PortfolioImpl(this.portfolioName, shareList);
    }
  }

}
