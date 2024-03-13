package model;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class StockImpl implements StockInterface {

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
              + "=" + stockSymbol + "&apikey=" + apiKey + "&datatype=csv");
    } catch (MalformedURLException e) {
      throw new RuntimeException("the alphavantage API has either changed or "
              + "no longer works");
    }

    InputStream in;
    StringBuilder output = new StringBuilder();

    try {
      in = url.openStream();
      int b;

      while ((b = in.read()) != -1) {
        output.append((char) b);
      }
    } catch (IOException e) {
      throw new RuntimeException("No price data found for " + stockSymbol);
    }
    if (output.toString().charAt(0) == '{') {
      throw new RuntimeException();
    }
    return storeFetchedData(output, date);

  }

  private double storeFetchedData(StringBuilder output, String requestedDate) {
    String fileName = tickerSymbol + ".csv";
    double price = -1;
    String[] lines = output.toString().split("\n");
    for (int i = 1; i < lines.length; i++) {
      String line = lines[i];
      String[] parts = line.split(",");
      if (parts.length >= 5) {
        String date = parts[0].trim();
        String closingPriceStr = parts[4].trim();
        double closingPrice = Double.parseDouble(closingPriceStr);
        if (date.equals(requestedDate)) {
          price = closingPrice;
        }
        this.priceData.put(date, closingPrice);
      }
    }


    FileHandler fileHandler = new FileHandler();
    fileHandler.save(fileName, this.priceData);


    return price;
  }

  private void loadDataFromCSV() {
    String fileName = tickerSymbol + ".csv";
    FileHandler fileHandler = new FileHandler();
    List<String[]> lines = fileHandler.load(fileName);
    for (String[] line : lines) {
      if (line.length >= 2) {
        String date = line[0].trim();
        String closingPriceStr = line[1].trim();
        Double closingPriceFloat = Double.parseDouble(closingPriceStr);
        this.priceData.put(date, closingPriceFloat);

      }
    }
  }

  @Override
  public double returnPrice(String date) {
    double price = 0;
    try {
      if (!this.priceData.isEmpty()) {
        price = this.priceData.getOrDefault(date, -1.0);
      } else if (!isCSVFileExists()) {
        price = fetchData(date);
      } else if (isCSVFileExists()) {
        loadDataFromCSV();
        price = this.priceData.getOrDefault(date, -1.0);
      }
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException(this.tickerSymbol);
    }


    if (price < 0) {
      throw new IllegalArgumentException(this.tickerSymbol);
    }

    return price;
  }

  private boolean isCSVFileExists() {
    String fileName = tickerSymbol + ".csv";
    File csvFile = new File(fileName);
    return csvFile.exists();
  }

  String getTicker() {
    return this.tickerSymbol;
  }
}
