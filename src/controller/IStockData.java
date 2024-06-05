package controller;

import java.util.ArrayList;
import java.util.TreeMap;

/**
 * This Interface provides method to get the historical price data for a given stock.
 * It retrieves this data either from the file cache or the API.
 */
public interface IStockData {

  /**
   * this method gets the price for the tickerSymbol on a particular date.
   * @param tickerSymbol The ticker symbol of the stock for which price is to be fetched.
   * @return TreeMap containing historical stock data, with dates & price information.
   * @throws IllegalArgumentException if no price data is found for the provided ticker symbol.
   */
  TreeMap<String, ArrayList<Double>> fetchHistoricalData(String tickerSymbol);
}
