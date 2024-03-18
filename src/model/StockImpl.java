package model;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * StockImpl class implements StockInterface class.
 * It has methods to fetch the data from api, store the data in csv files to decrease the number.
 * of api calls and also store the data in price data object for faster retrieval of
 * stock price on a particular date.
 */
class StockImpl implements StockInterface {

  /**
   * it has the ticker symbol for which the price data is to be found and api is called.
   */
  private final String tickerSymbol;

  /**
   * priceData object stores the date and respective price of the stock data.
   */
  private final Map<String, Double> priceData;

  /**
   * StockImpl constructor has tickerSymbol as argument for which the price data is found.
   * @param tickerSymbol has the tickerSymbol for which the user asks to get the price data.
   *                     on a particular date.
   */
  public StockImpl(String tickerSymbol) {
    this.tickerSymbol = tickerSymbol;
    priceData = new HashMap<>();
  }

  /**
   * fetchData method is used to call the api for the stockSymbol using api Key.
   * It also calls the storeFetchedData method to store the fetched data in a csv file.
   * @param date is the data for which the user is asking to get the price for the stock.
   * @return the price of the particular stock on that date.
   */
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


  /**
   * storeFetchedData is used to store the data got from the api call in the csv file.
   * it does this with the help of file handler, save method.
   * It also searches for the price on the particular date while storing the data for all dates.
   * in the csv file.
   * @param output is the price for the stock on dates fetched when the api is called.
   * @param requestedDate is the date for which the price is requested by the user for that stock.
   * @return the price for the stock on a particular date.
   */
  private double storeFetchedData(StringBuilder output, String requestedDate) {
    LocalDate currentDate = LocalDate.now();
    String fileName =
            System.getProperty("user.dir") + "/Data/" + currentDate + "/" + tickerSymbol + ".csv";
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


  /**
   * loadDatFromCSV is used to load the csv file of a particular ticker symbol present in the
   * current date's folder using the file handler load method and puts the data in the price data
   * object that contains the date and closing price data for the tickerSymbol.
   */
  private void loadDataFromCSV() {
    LocalDate currentDate = LocalDate.now();
    String fileName =
            System.getProperty("user.dir") + "/Data/" + currentDate + "/" + tickerSymbol + ".csv";
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


  /**
   * returnPrice method gets the price for the tickerSymbol on a particular date.
   * @param date is the date for which user wants to get the stock price.
   * @return the price for the tickerSymbol on a particular date.
   */
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

  /**
   * this method is used to get ticker symbol that is being used.
   * @return the tickerSymbol string.
   */
  String getTicker() {
    return this.tickerSymbol;
  }
}
