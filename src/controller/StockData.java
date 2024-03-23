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

public class StockData {
  private final String apiKey = "W0M1JOKC82EZEQA8";
  private String tickerSymbol;
  private final TreeMap<String, ArrayList<Double>> priceData;

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
   * It also searches for the price on the particular date while storing the data for all dates.
   * in the csv file.
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

  private void populateMap(String[] line) {
    if (line.length >= 2) {
      String date = line[0].trim();
      String openingPriceStr = line[1].trim();
      String closingPriceStr = line[2].trim();
      String lowPriceStr = line[3].trim();
      String highPriceStr = line[4].trim();
      Double openingPrice = Double.parseDouble(openingPriceStr);
      Double closingPrice = Double.parseDouble(closingPriceStr);
      Double lowPrice = Double.parseDouble(lowPriceStr);
      Double highPrice = Double.parseDouble(highPriceStr);
      this.priceData.put(date, new ArrayList<>(Arrays.asList(openingPrice, closingPrice, highPrice, lowPrice)));

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
   * returnPrice method gets the price for the tickerSymbol on a particular date.
   * @param tickerSymbol is the date for which user wants to get the stock price.
   * @return the price for the tickerSymbol on a particular date.
   */
  public TreeMap<String, ArrayList<Double>> fetchHistoricalData(String tickerSymbol) {
    this.tickerSymbol = tickerSymbol;

    try {
      if (!isCSVFileExists()) {
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
