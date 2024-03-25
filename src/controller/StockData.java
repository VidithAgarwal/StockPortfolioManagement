package controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * StockData class provides methods for fetching historical stock data and storing it in a CSV file.
 */
public class StockData {

  /**
   * The API key used for accessing the stock data service.
   */
  private final String apiKey = "W0M1JOKC82EZEQA8";

  /**
   * The ticker symbol representing the stock for which data is fetched.
   */
  private String tickerSymbol;

  /**
   * TreeMap storing historical price data for the ticker symbol.
   * keys are dates, and the values are ArrayLists containing opening price,
   * closing price, high price, and low price.
   */
  private final TreeMap<String, ArrayList<Double>> priceData;

  /**
   * Constructs a new StockData object, price data.
   * Initializes the priceData TreeMap to store historical price data.
   */
  public StockData() {
    priceData = new TreeMap<>(Collections.reverseOrder());
  }

  /**
   * fetchData method is used to call the api for the stockSymbol using api Key.
   * It also calls the storeFetchedData method to store the fetched data in a csv file.
   */
  private void fetchData() {
    String stockSymbol = tickerSymbol;
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

    storeFetchedData(output);
  }

  /**
   * storeFetchedData is used to store the data got from the api call in the csv file.
   * it does this with the help of file handler, save method.
   * it also stores this data in a price data object.
   * @param output is the price for the stock on dates fetched when the api is called.
   */
  private void storeFetchedData(StringBuilder output) {
    LocalDate currentDate = LocalDate.now();
    String fileName =
            System.getProperty("user.dir") + "/Data/" + currentDate + "/" + tickerSymbol + ".csv";
    String[] lines = output.toString().split("\n");
    for (int i = 1; i < lines.length; i++) {
      String line = lines[i];
      String[] parts = line.split(",");
      populateMap(parts);
    }

    Persistence fileHandler = new Persistence();
    fileHandler.save(fileName, this.priceData);
  }

  /**
   * this method populates priceData TreeMap with historical stock data extracted from given line.
   * line should contain comma-separated values representing date, opening price, high price,
   * closing price, and low price.
   * @param line An array of strings representing a line of historical stock data.
   *
   */
  private void populateMap(String[] line) {  // here should be >=5 ?
    if (line.length >= 2) {
      String date = line[0].trim();
      String openingPriceStr = line[1].trim();
      String closingPriceStr = line[4].trim();
      String lowPriceStr = line[3].trim();
      String highPriceStr = line[2].trim();
      Double openingPrice = Double.parseDouble(openingPriceStr);
      Double closingPrice = Double.parseDouble(closingPriceStr);
      Double lowPrice = Double.parseDouble(lowPriceStr);
      Double highPrice = Double.parseDouble(highPriceStr);
      this.priceData.put(date, new ArrayList<>(Arrays.asList(openingPrice, highPrice, lowPrice,
              closingPrice)));

    }
  }

  /**
   * loadDatFromCSV is used to load the csv file of a particular ticker symbol present in the
   * current date's folder using the file handler load method and puts the data in the price data
   * object that contains the date and closing price data for the tickerSymbol.
   */
  private void loadDataFromFile() {
    LocalDate currentDate = LocalDate.now();
    String fileName =
            System.getProperty("user.dir") + "/Data/" + currentDate + "/" + tickerSymbol + ".csv";
    Persistence fileHandler = new Persistence();
    List<String[]> lines = fileHandler.loadFromCSV(fileName);
    for (String[] line : lines) {
      populateMap(line);
    }
  }


  /**
   * this method gets the price for the tickerSymbol on a particular date.
   * @param tickerSymbol The ticker symbol of the stock for which price is to be fetched.
   * @return TreeMap containing historical stock data, with dates as keys.
   * and price information as values.
   * @throws IllegalArgumentException if no price data is found for the provided ticker symbol.
   */
  public TreeMap<String, ArrayList<Double>> fetchHistoricalData(String tickerSymbol) {
    this.tickerSymbol = tickerSymbol;

    try {
      if (!isCSVFileExists()) {
        //System.out.println("Hello");
        fetchData();
      } else if (isCSVFileExists()) {
        loadDataFromFile();
      }
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException(this.tickerSymbol);
    }

    return priceData;

  }

  /**
   * this method is used to check is a CSV file exists or not for a particular tickerSymbol.
   * in the data folder.
   * @return true if the csv file exists for else returns false.
   */
  private boolean isCSVFileExists() {
    LocalDate currentDate = LocalDate.now();
    String fileName =
            System.getProperty("user.dir") + "/Data/" + currentDate + "/" + tickerSymbol + ".csv";
    File csvFile = new File(fileName);
    return csvFile.exists();
  }


}
