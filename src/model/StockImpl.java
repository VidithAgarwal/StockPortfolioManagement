package model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class StockImpl implements StockInterface{

  private final String tickerSymbol;
  private final Map<String, Double> priceData;

  public StockImpl(String tickerSymbol) {
    this.tickerSymbol = tickerSymbol;
    priceData = new HashMap<>();
  }

  private double fetchData(String date) {
    String apiKey = "W0M1JOKC82EZEQA8";
    // our key B2R39JDS3MPERHL7
    // sir's key String apiKey = "W0M1JOKC82EZEQA8";
    String stockSymbol = tickerSymbol; //ticker symbol for Google
    URL url;

    try {

      url = new URL("https://www.alphavantage"
              + ".co/query?function=TIME_SERIES_DAILY"
              + "&outputsize=full"
              + "&symbol"
              + "=" + stockSymbol + "&apikey="+apiKey+"&datatype=csv");
    }
    catch (MalformedURLException e) {
      throw new RuntimeException("the alphavantage API has either changed or "
              + "no longer works");
    }

    InputStream in;
    StringBuilder output = new StringBuilder();

    try {
      in = url.openStream();
      int b;

      while ((b=in.read())!=-1) {
        output.append((char)b);
      }
    }
    catch (IOException e) {
      System.out.println("Hello");
      throw new RuntimeException("No price data found for "+stockSymbol);
    }
    return storeFetchedDataInCSV(output, date);

  }

  private double storeFetchedDataInCSV(StringBuilder output, String requestedDate) {
    String fileName = tickerSymbol + ".csv";
    double price = -1;
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
      String[] lines = output.toString().split("\n");
      for (int i = 1; i < lines.length / 2; i++) {
        String line = lines[i];
        String[] parts = line.split(",");
        if (parts.length >= 5) {
          String date = parts[0].trim();
          String closingPriceStr = parts[4].trim();
          if (date.equals(requestedDate)) {
            price = Double.parseDouble(closingPriceStr);
          }
          writer.write(date + "," + closingPriceStr + "\n");
        }
      }
      return price;
    } catch (IOException e) {
      throw new IllegalArgumentException(e.getMessage());
    }
    //return price;
  }

  private void loadDataFromCSV() {
    String fileName = tickerSymbol + ".csv";
    File csvFile = new File(fileName);
    if (csvFile.exists()) {
      try (BufferedReader reader = new BufferedReader(new FileReader(csvFile))) {
        String line;
        StringBuilder csvData = new StringBuilder();
        while ((line = reader.readLine()) != null) {
          csvData.append(line).append("\n");
        }
        storeFetchedData(csvData);
      } catch (IOException e) {
        throw new IllegalArgumentException(e.getMessage());
      }
    }
  }

  @Override
  public double returnPrice(String date) {
    double price = 0;
      if (!this.priceData.isEmpty()) {
        price = this.priceData.getOrDefault(date, -1.0);
      } else if (!isCSVFileExists() && this.priceData.isEmpty()) {
        try {
          price = fetchData(date);
        } catch (RuntimeException e) {
          throw e;
        }

      } else if (isCSVFileExists()) {
          loadDataFromCSV();
          price = this.priceData.getOrDefault(date, -1.0);
      }

      if (price < 0) {
        throw new IllegalArgumentException();
      }

      return price;
  }

  private boolean isCSVFileExists() {
    String fileName = tickerSymbol + ".csv";
    File csvFile = new File(fileName);
    return csvFile.exists();
  }



  private void storeFetchedData(StringBuilder output) {
    String[] lines = output.toString().split("\n");
    for (int i = 0; i < lines.length; i++) {
      String line = lines[i];
      String[] parts = line.split(",");
      if (parts.length >= 2) {
        String date = parts[0].trim();
        String closingPriceStr = parts[1].trim();
        try {
          Double closingPriceFloat = Double.parseDouble(closingPriceStr);
          this.priceData.put(date, closingPriceFloat);
        } catch (NumberFormatException ignored) {
        }
      }
    }
  }

  String getTicker() {
    return this.tickerSymbol;
  }
}
