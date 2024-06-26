package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

/**
 * The StockStatisticsImpl class implements the StockStatistic interface providing methods
 * to calculate various statistics related to stocks, for stock trend.
 */
public class StockStatisticsImpl implements StockStatistic {

  @Override
  public String gainOrLoseOnDate(String tickerSymbol, String dateString,
                                 TreeMap<String, ArrayList<Double>> priceData ) {
    try {
      ArrayList<Double> prices = priceData.get(dateString);
      double openingPrice = prices.get(0);
      double closingPrice = prices.get(3);

      if (closingPrice > openingPrice) {
        return tickerSymbol  + " gained on " + dateString;
      } else if (closingPrice < openingPrice) {
        return tickerSymbol + " lost on " + dateString;
      } else {
        return tickerSymbol + " remained unchanged on " + dateString;
      }
    } catch (NullPointerException e) {
      throw new IllegalArgumentException("No price data available for " + dateString);
    }
  }

  @Override
  public String gainOrLoseOverPeriod(String tickerSymbol, String date1,
                                     String date2, TreeMap<String, ArrayList<Double>> priceData) {
    ArrayList<Double> prices1 = priceData.get(date1);
    ArrayList<Double> prices2 = priceData.get(date2);
    String currentDate1 = date1;
    String currentDate2 = date2;
    if (prices1 == null && prices2 == null)  {
      currentDate1 = getNextDate(date1,priceData,date2);
      currentDate2 = getPreviousDate(date2,priceData);
    } else if (prices2 == null) {
      currentDate2 = getPreviousDate(date2,priceData);
    } else if (prices1 == null) {
      currentDate1 = getNextDate(date1,priceData,date2);
    } else {
      currentDate1 = date1;
      currentDate2 = date2;
    }
    prices1 = priceData.get(currentDate1);
    prices2 = priceData.get(currentDate2);
    if (prices1 == null || prices2 == null) {
      throw new IllegalArgumentException("No price data available for one or "
              + "both of the specified dates");
    }
    double closingPrice1 = prices1.get(3);
    double closingPrice2 = prices2.get(3);
    double priceDifference = closingPrice2 - closingPrice1;
    if (priceDifference > 0) {
      return tickerSymbol + " gained over the period from " + date1 + " to " + date2;
    } else if (priceDifference < 0) {
      return tickerSymbol + " lost over the period from " + date1 + " to " + date2;
    } else {
      return tickerSymbol + " remained unchanged over the period from " + date1 + " to " + date2;
    }
  }

  @Override
  public double xDayMovingAvg(String tickerSymbol, String date, int x,
                              TreeMap<String, ArrayList<Double>> priceData) {
    LocalDate currentDate = LocalDate.parse(date);
    ArrayList<Double> closingPrices = new ArrayList<>();
    LocalDate lastDay = returnLastEntry(priceData);

    for (int i = 0; i < x && currentDate.isAfter(lastDay); i++) {
      ArrayList<Double> prices = priceData.get(currentDate.toString());
      if (prices != null && prices.size() >= 2) {
        closingPrices.add(prices.get(3));
      } else {
        boolean foundValidDate = false;
        while (!foundValidDate && (currentDate.isAfter(lastDay))) {
          currentDate = currentDate.minusDays(1);
          ArrayList<Double> prevDayPrices = priceData.get(currentDate.toString());
          if (prevDayPrices != null && prevDayPrices.size() >= 2) {
            closingPrices.add(prevDayPrices.get(3));
            foundValidDate = true;
          }
        }
      }
      currentDate = currentDate.minusDays(1);
    }
    if (closingPrices.size() < x) {
      throw new IllegalArgumentException("Insufficient data available for the specified period");
    }
    double sum = 0;
    for (double price : closingPrices) {
      sum += price;
    }
    return sum / x;
  }


  /**
   * this method finds last date entry in price data TreeMap.
   * @param priceData Price data of the stock.
   * @return last date entry as a LocalDate.
   * @throws IllegalArgumentException if TreeMap is empty.
   */
  private LocalDate returnLastEntry(TreeMap<String, ArrayList<Double>> priceData) {
    String lastDate;
    Map.Entry<String, ArrayList<Double>> lastEntry = priceData.lastEntry();
    if (lastEntry != null) {
      lastDate = lastEntry.getKey();
    } else {
      throw new IllegalArgumentException();
    }
    return LocalDate.parse(lastDate);
  }


  @Override
  public TreeMap<String, String> crossoverOverPeriod(String tickerSymbol,
                                                     TreeMap<String, ArrayList<Double>> priceData,
                                                     String startDate, String endDate) {
    TreeMap<String, String> crossoverInfo = new TreeMap<>();
    String currentDate;
    ArrayList<Double> checkStartDate = priceData.get(startDate);
    if (checkStartDate == null )  {
      currentDate = getNextDate(startDate,priceData,endDate);
    }
    else {
      currentDate = startDate;
    }

    for (; currentDate.compareTo(endDate) <= 0; currentDate
            = getNextDate(currentDate, priceData, endDate)) {
      double movingAverage = xDayMovingAvg(tickerSymbol, currentDate, 30, priceData);
      ArrayList<Double> currentPrices = priceData.get(currentDate);
      ArrayList<Double> prevDayPrices = priceData.get(getPreviousDate(currentDate, priceData));

      if (currentPrices != null && prevDayPrices != null
              && currentPrices.size() >= 2 && prevDayPrices.size() >= 2) {
        double currentClosingPrice = currentPrices.get(3);
        double prevDayClosingPrice = prevDayPrices.get(3);
        if (prevDayClosingPrice < movingAverage && currentClosingPrice > movingAverage) {
          crossoverInfo.put(currentDate, "buy");
        } else if (prevDayClosingPrice > movingAverage && currentClosingPrice < movingAverage) {
          crossoverInfo.put(currentDate, "sell");
        }
      }
      else if (currentPrices == null && currentDate.equals(endDate) ) {
        // do nothing
      }
      else {
        throw new IllegalArgumentException("Price data not available for one or"
                + " both of the dates: " + currentDate + ", "
                + getPreviousDate(currentDate, priceData));
      }
    }
    return crossoverInfo;
  }


  /**
   * this method gets previous date based on given date.
   * @param currentDate current date whose previous date is to be found.
   * @param priceData   price data of stock over time.
   * @return previous date as a string.
   * @throws IllegalArgumentException if date is before listing date of that stock.
   */
  private String getPreviousDate(String currentDate, TreeMap<String, ArrayList<Double>> priceData) {

    LocalDate lastDay = returnLastEntry(priceData);
    LocalDate date = LocalDate.parse(currentDate).minusDays(1);
    while (!priceData.containsKey(date.toString()) && date.isAfter(lastDay)) {
      date = date.minusDays(1);
    }
    if (date.isAfter(lastDay) ) {
      return date.toString();
    }
    else {
      throw new IllegalArgumentException("date is before the listing date.");
    }
  }

  /**
   * this method gets next date based on given date.
   * @param currentDate current date whose next date is to be found.
   * @param priceData   price data of stock.
   * @param endDate    end date entered by user, next day should be less than end date.
   * @return next date as string.
   */
  private String getNextDate(String currentDate, TreeMap<String,
          ArrayList<Double>> priceData, String endDate) {
    LocalDate nextDate = LocalDate.parse(currentDate).plusDays(1);
    while (!priceData.containsKey(nextDate.toString())
            && nextDate.isBefore(LocalDate.parse(endDate))) {
      nextDate = nextDate.plusDays(1);
    }
    return nextDate.toString();
  }


  @Override
  public TreeMap<String, String> movingCrossoversOverPeriod(String tickerSymbol, TreeMap<String,
          ArrayList<Double>> priceData, String startDate, String endDate, int x, int y) {
    if (x < y) {
      String currentDate;
      ArrayList<Double> checkStartDate = priceData.get(startDate);
      if (checkStartDate == null )  {
        currentDate = getNextDate(startDate,priceData,endDate);
      }
      else {
        currentDate = startDate;
      }
      if (currentDate.equals(endDate) ) {
        throw new IllegalArgumentException("No data found between this period for finding "
                + "moving crossover.");
      }
      TreeMap<String, String> crossoverInfo = new TreeMap<>();
      for (; !currentDate.equals(endDate); currentDate
              = getNextDate(currentDate, priceData, endDate)) {
        double xMovingAverage = xDayMovingAvg(tickerSymbol, currentDate, x, priceData);
        double yMovingAverage = xDayMovingAvg(tickerSymbol, currentDate, y, priceData);
        String prevDay = getPreviousDate(currentDate,priceData);
        double xMovingAveragePrev = xDayMovingAvg(tickerSymbol, prevDay, x, priceData);
        double yMovingAveragePrev = xDayMovingAvg(tickerSymbol, prevDay, y, priceData);
        if (xMovingAverage > yMovingAverage && xMovingAveragePrev < yMovingAveragePrev) {
          crossoverInfo.put(currentDate, "buy");
        } else if (xMovingAverage < yMovingAverage && xMovingAveragePrev > yMovingAveragePrev) {
          crossoverInfo.put(currentDate, "sell");
        }
      }
      return crossoverInfo;
    }
    else {
      throw new IllegalArgumentException("X days that is shorter moving average days should be "
              + "less than Y days");
    }

  }

}
