package model;

import java.util.ArrayList;
import java.util.TreeMap;

public interface StockStatistic {

  public String gainOrLoseOnDate (String tickerSymbol, String date, TreeMap<String, ArrayList<Double>> priceData);

  public String gainOrLoseOverPeriod (String tickerSymbol, String date1, String date2, TreeMap<String, ArrayList<Double>> priceData);

  public double xDayMovingAvg (String tickerSymbol, String date, int x, TreeMap<String, ArrayList<Double>> priceData);

  public TreeMap<String, String> crossoverOverPeriod (String tickerSymbol,TreeMap<String, ArrayList<Double>> priceData, String startDate, String endDate);

  public TreeMap<String, String> movingCrossoversOverPeriod(String tickerSymbol, TreeMap<String,
          ArrayList<Double>> priceData, String startDate, String endDate, int x, int y);

}