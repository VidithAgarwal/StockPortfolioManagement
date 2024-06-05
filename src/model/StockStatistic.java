package model;

import java.util.ArrayList;
import java.util.TreeMap;

/**
 * The StockStatistic interface provides methods to calculate various statistics related to stocks.
 */
public interface StockStatistic {

  /**
   * this method calculates gain or loss of a stock on a specific date.
   * @param tickerSymbol The symbol of the stock.
   * @param date         The date for which the gain or loss is calculated.
   * @param priceData    Price data of that stock over time.
   * @return string indicating the gain or loss on the specified date.
   */
  String gainOrLoseOnDate(String tickerSymbol, String date, TreeMap<String,
          ArrayList<Double>> priceData);

  /**
   * this method calculates gain or loss of a stock over a period of time.
   *
   * @param tickerSymbol symbol of the stock.
   * @param date1         start date of the period.
   * @param date2         end date of the period.
   * @param priceData    Price data of that stock over time.
   * @return string indicating gain or loss over specified period.
   */
  String gainOrLoseOverPeriod(String tickerSymbol, String date1, String date2,
                              TreeMap<String, ArrayList<Double>> priceData);

  /**
   * this method calculates X-day moving average of a stock on a specific date.
   *
   * @param tickerSymbol symbol of the stock.
   * @param date         date for which the moving average is calculated.
   * @param x            number of days for the moving average.
   * @param priceData    Price data of that stock over time.
   * @return  X-day moving average of stock on specified date.
   */
  double xDayMovingAvg(String tickerSymbol, String date, int x,
                       TreeMap<String, ArrayList<Double>> priceData);

  /**
   * this method identifies crossover points over a specified period for a stock.
   *
   * @param tickerSymbol symbol of the stock.
   * @param priceData    price data of the stock over time.
   * @param startDate    start date of the period.
   * @param endDate      end date of the period.
   * @return treeMap containing crossover points with details whether it was buy/sell opportunity.
   */
  TreeMap<String, String> crossoverOverPeriod(String tickerSymbol,TreeMap<String,
          ArrayList<Double>> priceData, String startDate, String endDate);

  /**
   * this method identifies moving crossovers over a specified period for a stock.
   *
   * @param tickerSymbol symbol of the stock.
   * @param priceData    price data of the stock over time.
   * @param startDate    start date of the period.
   * @param endDate      end date of the period.
   * @param x            number of days for the first moving average, shorter moving day average.
   * @param y            number of days for the longer moving average.
   * @return treeMap containing moving crossovers with details whether it was buy/sell opportunity.
   */
  TreeMap<String, String> movingCrossoversOverPeriod(String tickerSymbol,
                                                     TreeMap<String, ArrayList<Double>> priceData,
                                                     String startDate, String endDate,
                                                     int x, int y);

}
