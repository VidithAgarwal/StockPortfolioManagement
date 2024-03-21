package model;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class StockStatisticsImpl implements StockStatistic{
  @Override
  public String gainOrLoseOnDate(String tickerSymbol, String date, TreeMap<String, ArrayList<Double>> priceData ) {
    // here this function does not require ticker symbol that can be asked the user to enter at controller
    // to get the price data for that stock name / ticker symbol.
    try {
      ArrayList<Double> prices = priceData.get(date);

      double openingPrice = prices.get(0);
      double closingPrice = prices.get(1);

      if (closingPrice > openingPrice) {
        return "Stock gained on " + date;
      } else if (closingPrice < openingPrice) {
        return "Stock lost on " + date;
      } else {
        return "Stock remained unchanged on " + date;
      }
    } catch (NullPointerException e) {
      throw new IllegalArgumentException("No price data available for " + date);
    }
  }

  @Override
  public String gainOrLoseOverPeriod(String tickerSymbol, String date1, String date2, TreeMap<String, ArrayList<Double>> priceData) {
    ArrayList<Double> prices1 = priceData.get(date1);
    ArrayList<Double> prices2 = priceData.get(date2);

    if (prices1 == null || prices2 == null) {
      throw new IllegalArgumentException("No price data available for one or both of the specified dates");
    }

    double closingPrice1 = prices1.get(1);
    double closingPrice2 = prices2.get(1);

    double priceDifference = closingPrice2 - closingPrice1;

    if (priceDifference > 0) {
      return "Stock gained over the period from " + date1 + " to " + date2;
    } else if (priceDifference < 0) {
      return "Stock lost over the period from " + date1 + " to " + date2;
    } else {
      return "Stock remained unchanged over the period from " + date1 + " to " + date2;
    }
  }

  @Override
  public double xDayMovingAvg(String tickerSymbol, String date, int x, TreeMap<String, ArrayList<Double>> priceData) {
    LocalDate currentDate = LocalDate.parse(date);

    ArrayList<Double> closingPrices = new ArrayList<>();

    String lastDate = null;
//    Map.Entry<String, ArrayList<Double>> lastEntry = null;
    Map.Entry<String, ArrayList<Double>> lastEntry = priceData.lastEntry();

    // Check if there's at least one entry in the map
    if (lastEntry != null) {
      lastDate = lastEntry.getKey();
      //ArrayList<Double> lastPrices = lastEntry.getValue();
      // Process the last entry as needed
    } else {
      throw new IllegalArgumentException();
    }


    LocalDate lastDay = LocalDate.parse(lastDate);
    for (int i = 0; i < x && currentDate.isAfter(lastDay); i++) {
      ArrayList<Double> prices = priceData.get(currentDate.toString());
      if (prices != null && prices.size() >= 2) {
        closingPrices.add(prices.get(1));
      } else {
        boolean foundValidDate = false;
        while (!foundValidDate && (currentDate.isAfter(lastDay))) {
          currentDate = currentDate.minusDays(1);
          if (currentDate.getDayOfWeek() == DayOfWeek.SATURDAY || currentDate.getDayOfWeek() == DayOfWeek.SUNDAY) {
            continue;
          }

          ArrayList<Double> prevDayPrices = priceData.get(currentDate.toString());
          if (prevDayPrices != null && prevDayPrices.size() >= 2) {
            closingPrices.add(prevDayPrices.get(1));
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


  @Override
  public Map<String, String> crossoverOverPeriod(String tickerSymbol, TreeMap<String, ArrayList<Double>> priceData, String endDate, String startDate) {
    //ArrayList<String> crossoverDates = new ArrayList<>();
    Map<String, String> crossoverInfo = new HashMap<>();

    for (String currentDate = startDate; !currentDate.equals(endDate); currentDate = getNextDate(currentDate, priceData, endDate)) {

      double movingAverage = xDayMovingAvg(tickerSymbol, currentDate, 30, priceData);

      ArrayList<Double> currentPrices = priceData.get(currentDate);
      ArrayList<Double> prevDayPrices = priceData.get(getPreviousDate(currentDate, priceData));


      if (currentPrices != null && prevDayPrices != null && currentPrices.size() >= 2 && prevDayPrices.size() >= 2) {
        double currentClosingPrice = currentPrices.get(1);
        double prevDayClosingPrice = prevDayPrices.get(1);


        if (prevDayClosingPrice < movingAverage && currentClosingPrice > movingAverage) {
          //crossoverDates.add(currentDate);
          crossoverInfo.put(currentDate, "buy");
        } else if (prevDayClosingPrice > movingAverage && currentClosingPrice < movingAverage) {
          //crossoverDates.add(currentDate);
          crossoverInfo.put(currentDate, "sell");
        }
      }
      else {
        throw new IllegalArgumentException("Price data not available for one or both of the dates: " + currentDate + ", " + getPreviousDate(currentDate, priceData));
      }
    }

    return crossoverInfo;
  }


  private String getPreviousDate(String currentDate, TreeMap<String, ArrayList<Double>> priceData) {
    String lastDate = null;
    Map.Entry<String, ArrayList<Double>> lastEntry = null;
    for (Map.Entry<String, ArrayList<Double>> entry : priceData.entrySet()) {
      lastEntry = entry;
    }

    // Check if there's at least one entry in the map
    if (lastEntry != null) {
      lastDate = lastEntry.getKey();
      //ArrayList<Double> lastPrices = lastEntry.getValue();
      // Process the last entry as needed
    } else {
      throw new IllegalArgumentException();
    }
    LocalDate lastDay = LocalDate.parse(lastDate);


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
    //return date.toString();

  }

  private String getNextDate(String currentDate, TreeMap<String, ArrayList<Double>> priceData, String endDate) {
    LocalDate nextDate = LocalDate.parse(currentDate).plusDays(1);
    while (!priceData.containsKey(nextDate.toString()) && nextDate.isBefore(LocalDate.parse(endDate))) {
      nextDate = nextDate.plusDays(1);
    }
    //if
    return nextDate.toString();
  }

  @Override
  public String movingCrossOver(String tickerSymbol, TreeMap<String, ArrayList<Double>> priceData, double xMovingAvg, double yMovingAvg) {
    return null;
  }


  public Map<String, String> movingCrossoversOverPeriod(String tickerSymbol, TreeMap<String, ArrayList<Double>> priceData, String endDate, String startDate, int x, int y) {
    Map<String, String> crossoverInfo = new HashMap<>();

    for (String currentDate = startDate; !currentDate.equals(endDate); currentDate = getNextDate(currentDate, priceData, endDate)) {

      double shortMovingAverage = xDayMovingAvg(tickerSymbol, currentDate, x, priceData);
      double longMovingAverage = xDayMovingAvg(tickerSymbol, currentDate, y, priceData);

      ArrayList<Double> currentPrices = priceData.get(currentDate);
      ArrayList<Double> prevDayPrices = priceData.get(getPreviousDate(currentDate, priceData));

      if (currentPrices != null && prevDayPrices != null && currentPrices.size() >= 2 && prevDayPrices.size() >= 2) {
        double currentClosingPrice = currentPrices.get(1);
        double prevDayClosingPrice = prevDayPrices.get(1);

        //is this correct for buy moving crossover ?
        if ( currentClosingPrice > shortMovingAverage && currentClosingPrice > prevDayClosingPrice && shortMovingAverage > longMovingAverage) { //prevDayClosingPrice < shortMovingAverage
          crossoverInfo.put(currentDate, "buy");
        } else if (prevDayClosingPrice > shortMovingAverage && prevDayClosingPrice > longMovingAverage && currentClosingPrice < longMovingAverage &&
                shortMovingAverage > longMovingAverage) {
          crossoverInfo.put(currentDate, "sell");
        }
      } else {
        throw new IllegalArgumentException("Price data not available for one or both of the dates: " + currentDate + ", " + getPreviousDate(currentDate, priceData));
      }
    }

    return crossoverInfo;
  }
}