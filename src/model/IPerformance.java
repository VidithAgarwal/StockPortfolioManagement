package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.TreeMap;


/**
 * Performance Interface provides methods for analyzing the performance of stocks and portfolios.
 * Used for performance evaluation of stock trend and portfolio over time.
 */
interface IPerformance {

  /**
   * this method computes the performance of a stock within a specified time frame.
   *
   * @param priceData historical price data of the stock.
   * @param start     start date of the performance analysis.
   * @param end       end date of the performance analysis.
   * @return TreeMap containing the selected stock data, the timestamp and price on that day.
   * @throws IllegalArgumentException if the start date is before the last available date for stock.
   */
  TreeMap<String, Double> stockPerformance(TreeMap<String, ArrayList<Double>> priceData,
                                           LocalDate start, LocalDate end);

  /**
   * this method computes performance of a portfolio within a specified time frame/ period.
   *
   * @param portfolioName portfolio object, whose performance is to be determined.
   * @param start         start date of the performance analysis.
   * @param end           end date of the performance analysis.
   * @return A TreeMap containing the selected portfolio data, timestamp and price on that day .
   * @throws IllegalArgumentException if the portfolio is not flexible.
   */
  TreeMap<String, Double> portfolioPerformance(Portfolio portfolioName, LocalDate start,
                                               LocalDate end);

  /**
   * this method determines scale for visualizing performance data overtime.
   *
   * @param selectedData it is price data for which scale is calculated for better representation.
   * @return the scale value for that timestamps and price data.
   */
  int determineScale(TreeMap<String, Double> selectedData);

  /**
   * this method scales the prices for the timestamps, based on the calculated scale.
   * this is used to determine length of bar chart in representation.
   *
   * @param prices the prices to be scaled for the stock or portfolio performance representation.
   * @param scale  the scale factor.
   * @return TreeMap containing scaled prices with timestamp.
   */
  TreeMap<String, Integer> determineValueBasedOnScale(TreeMap<String, Double> prices,
                                                      int scale);

  /**
   * sorts a TreeMap by month and year in ascending order.
   * if keys of the input TreeMap do not match format "MMM yyyy".
   * original TreeMap is returned without sorting.
   *
   * @param data input TreeMap to be sorted.
   * @return sorted TreeMap if keys are in "MMM yyyy" format, otherwise returns original TreeMap.
   */
  TreeMap<String, Integer> sortTreeMapByMonthAndYear(TreeMap<String, Integer> data);

}
