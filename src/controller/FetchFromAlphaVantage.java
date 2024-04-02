package controller;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;


/**
 * The FetchFromAlphaVantage class implements the FetchFromAPI interface and provides methods for
 * fetching data from the Alpha Vantage API.
 */
class FetchFromAlphaVantage implements FetchFromAPI {

  /**
   * The API key used for accessing the stock data service.
   */
  private final String apiKey;

  /**
   * Constructs a FetchFromAlphaVantage object with a default API key.
   */
  FetchFromAlphaVantage() {
    apiKey = "I6RVKAXBROB97OE4";
  }

  /**
   * Constructs a FetchFromAlphaVantage object with a custom API key.
   *
   * @param apiKey The API key for accessing the stock data service.
   */
  FetchFromAlphaVantage(String apiKey) {
    this.apiKey = apiKey;
  }

  @Override
  public StringBuilder fetchData(String ticker) {
    URL url;

    try {
      url = new URL("https://www.alphavantage"
              + ".co/query?function=TIME_SERIES_DAILY"
              + "&outputsize=full"
              + "&symbol"
              + "=" + ticker + "&apikey=" + apiKey + "&datatype=csv");
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
      throw new RuntimeException("No price data found for " + ticker);
    }
    if (output.toString().charAt(0) == '{') {
      throw new RuntimeException();
    }
    return output;
  }

}
