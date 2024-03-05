package model;

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

  private void fetchData() {
    String apiKey = "W0M1JOKC82EZEQA8";
    // our key B2R39JDS3MPERHL7
    // sir's key String apiKey = "W0M1JOKC82EZEQA8";
    String stockSymbol = tickerSymbol; //ticker symbol for Google
    URL url = null;

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

    InputStream in = null;
    StringBuilder output = new StringBuilder();

    try {
      for (int i = 0; i < 26; i++) {
        in = url.openStream();
        int b;

        while ((b=in.read())!=-1) {
          output.append((char)b);
        }
      }
    }
    catch (IOException e) {
      throw new IllegalArgumentException("No price data found for "+stockSymbol);
    }
    storeFetchedData(output);
  }

  @Override
  public double returnPrice(String date) {
    if (this.priceData.isEmpty()) {
      fetchData();
    }

    Double price = this.priceData.get(date);
    if (price == null) {
      throw new NullPointerException();
    }

    return price;
  }

  private void storeFetchedData(StringBuilder output) {
    String[] lines = output.toString().split("\n");
    for (int i = 1; i < lines.length/2; i++) {
      String line = lines[i];
      String[] parts = line.split(",");
      if (parts.length >= 5) {
        String date = parts[0].trim();
        String closingPriceStr = parts[4].trim();
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
