package model;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public interface StockStatistics {

  public String gainOrLoseOnDate (String tickerSymbol, String date, TreeMap<String, ArrayList<Double>> priceData);

  public String gainOrLoseOverPeriod (String tickerSymbol, String date1, String date2, TreeMap<String, ArrayList<Double>> priceData);

  public double xDayMovingAvg (String tickerSymbol, String date, int x, TreeMap<String, ArrayList<Double>> priceData);

  public Map<String, String> crossoverOverPeriod (String tickerSymbol,TreeMap<String, ArrayList<Double>> priceData, String startDate, String endDate);

  public String movingCrossOver (String tickerSymbol,TreeMap<String, ArrayList<Double>> priceData, double xMovingAvg, double yMovingAvg);


}
