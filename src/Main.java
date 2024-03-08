import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;

import controller.StockControllerImpl;
import model.PortfolioDir;
import model.PortfolioDirImpl;
import view.IView;
import view.IViewImpl;

public class Main {
  public static void main(String[] args) {

    PortfolioDir model = new PortfolioDirImpl();
    IView view = new IViewImpl(System.out);
    StockControllerImpl a = new StockControllerImpl(view, System.in, model);
    a.go();
//    fetchAndWriteStockData("NASDAQ");
//    fetchAndWriteStockData("NYSE");
  }

//  public static void fetchAndWriteStockData(String exchange) {
//    String API_KEY = "W0M1JOKC82EZEQA8";
//    String stockSymbol = "FUL"; //ticker symbol for Google
//    URL url;
//
//    try {
//      /*
//      create the URL. This is the query to the web service. The query string
//      includes the type of query (DAILY stock prices), stock symbol to be
//      looked up, the API key and the format of the returned
//      data (comma-separated values:csv). This service also supports JSON
//      which you are welcome to use.
//       */
//      url = new URL("https://www.alphavantage.co/query?function=LISTING_STATUS&apikey=" + API_KEY);
//    }
//    catch (MalformedURLException e) {
//      throw new RuntimeException("the alphavantage API has either changed or "
//              + "no longer works");
//    }
//
//    InputStream in = null;
//    StringBuilder output = new StringBuilder();
//
//    try {
//      /*
//      Execute this query. This returns an InputStream object.
//      In the csv format, it returns several lines, each line being separated
//      by commas. Each line contains the date, price at opening time, highest
//      price for that date, lowest price for that date, price at closing time
//      and the volume of trade (no. of shares bought/sold) on that date.
//
//      This is printed below.
//       */
//
//      in = url.openStream();
//      int b;
//
//      while ((b=in.read())!=-1) {
//        output.append((char)b);
//      }
//
//    }
//    catch (IOException e) {
//      throw new IllegalArgumentException("No price data found for "+stockSymbol);
//    }
//
//
//    String filename = "stocks.csv";
//
//    try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename, true))) {
//      String[] lines = output.toString().split("\n");
//      for(String line : lines){
//        String[] parts = line.split(",");
//        if (parts[2].equals(exchange)) {
//          if (parts[3].equals("Stock")) {
//            writer.write(parts[0] + "," + parts[1] + "\n");
//          }
//        }
//      }
//    } catch (IOException e) {
//      throw new IllegalArgumentException(e.getMessage());
//    }
//  }
}
