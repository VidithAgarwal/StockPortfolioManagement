package model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import controller.StockData;

public class Performance {

  public void performance(String stockOrPortfolio, String startDate, String endDate) {

  }

//  public void getPerformance (int portfolioInput, String currentDate, StockData data) {
//    PortfolioDir.get(portfolioInput).portfolioValue(currentDate, data);
//  }

  private void helperYearDiff0 (int numParts, LocalDate start, long totalDays, Map<String, ArrayList<Double>> priceData, TreeMap<String, Double> selectedData, LocalDate lastDay ) {
    long interval = Math.round((float) totalDays / (numParts));
    for (int i = 0; i < numParts; i++) {
      LocalDate currentDate = start.plusDays(interval * i);
      String currentDateString = currentDate.toString();
      while (!priceData.containsKey((currentDateString)) ) { //&& currentDate.isAfter(lastDay)
        currentDate = currentDate.minusDays(1);
        currentDateString = currentDate.toString();
      }
      ArrayList<Double> values = priceData.get(currentDateString);
      Double closingValue = values.get(1);
      selectedData.put(currentDateString, closingValue);
    }
  }

  private LocalDate returnLastEntry (TreeMap<String, ArrayList<Double>> priceData) {
    String lastDate = null;
    Map.Entry<String, ArrayList<Double>> lastEntry = priceData.lastEntry();
    if (lastEntry != null) {
      lastDate = lastEntry.getKey();
    } else {
      throw new IllegalArgumentException();
    }
    return LocalDate.parse(lastDate);
  }

  public TreeMap<String, Double> stockPerformance (TreeMap<String, ArrayList <Double>> priceData, LocalDate start, LocalDate end) {
    TreeMap<String, Double> selectedData = new TreeMap<>();
    LocalDate lastDay = returnLastEntry(priceData);
    if (start.isBefore(lastDay)) {
      throw new IllegalArgumentException();
    }
    long totalDays = ChronoUnit.DAYS.between(start, end);
    long yearDiff = ChronoUnit.YEARS.between(start, end);
    long monthsDifference = ChronoUnit.MONTHS.between(start.withDayOfMonth(1), end.withDayOfMonth(1));
    int numParts;

    if ( totalDays <= 30 && totalDays > 0 ) {
      numParts = (int) totalDays;
      helperYearDiff0(numParts,start,totalDays,priceData,selectedData, lastDay);
    }
    else if(totalDays ==31) {
      numParts = 16;
      helperYearDiff0(numParts,start,totalDays,priceData,selectedData, lastDay);
    }
    else if (monthsDifference < 5 && monthsDifference > 0 && yearDiff == 0 ) {
      numParts = Math.min(Math.max((int) (totalDays / 5), 1), 29);
      helperYearDiff0(numParts,start,totalDays,priceData,selectedData, lastDay);
    }

    else if (monthsDifference >= 5 && monthsDifference < 30) {
      numParts = (int) monthsDifference;
      for (int i = 0; i < numParts; i++) {
        LocalDate currentDate = start.plusMonths(i).withDayOfMonth(start.plusMonths(i).lengthOfMonth());
        String currentDateString = currentDate.toString();
        while (!priceData.containsKey((currentDateString))) {
          currentDate = currentDate.minusDays(1);
          currentDateString = currentDate.toString();
        }
        ArrayList<Double> values = priceData.get(currentDateString);
        Double closingValue = values.get(1);
        String formatDate = dateFormat(currentDateString);
        selectedData.put(formatDate, closingValue);
      }
    }

    else if (yearDiff >= 1 && yearDiff < 5 && monthsDifference >= 30) {
      numParts = (int) Math.ceil((double) monthsDifference / 2);
      long interval = Math.round((float) totalDays / (numParts));
      for (int i = 0; i < numParts; i++) {
        LocalDate currentDate = start.plusMonths(i*2).withDayOfMonth(start.plusMonths(i*2).lengthOfMonth());
        //LocalDate currentDate = start.plusDays(interval * i);
        String currentDateString = currentDate.toString();

        while (!priceData.containsKey((currentDateString))) {
          currentDate = currentDate.minusDays(1);
          currentDateString = currentDate.toString();
        }
        ArrayList<Double> values = priceData.get(currentDateString);
        Double closingValue = values.get(1);
        String formatDate = dateFormat(currentDateString);
        selectedData.put(formatDate, closingValue);
      }
    }

    else if (yearDiff >= 5 && yearDiff < 30) {
      numParts = (int) yearDiff;
      for (int i = 0; i < numParts; i++) {
        LocalDate currentDate = start.plusYears(i).withMonth(12).withDayOfMonth(31);
        String currentDateString = currentDate.toString();
        while (!priceData.containsKey((currentDateString))) {
          currentDate = currentDate.minusDays(1);
          currentDateString = currentDate.toString();
        }
        ArrayList<Double> values = priceData.get(currentDateString);
        Double closingValue = values.get(1);
        String formatDate = dateFormat(currentDateString);
        selectedData.put(formatDate, closingValue);
      }
    }
    else if (yearDiff >= 30 ) {
      numParts = Math.min(Math.max((int) (totalDays / 5), 1), 29);
      helperYearDiff0(numParts,start,totalDays,priceData,selectedData, lastDay);
    }
    return selectedData;

  }


  private void helperPerformanceYearDiff0 (int numParts, LocalDate start, double value, long totalDays, FlexiblePortfolio portfolioName, TreeMap <String, Double> selectedData ) {
    long interval = Math.round((float) totalDays / (numParts));
    for (int i = 0; i < numParts; i++) {
      LocalDate currentDate = start.plusDays(interval * i);
      String currentDateString = currentDate.toString();
      while (true) {
        try {
          value = portfolioName.portfolioValue(currentDateString, new StockData());
          break;
        }
        catch (Exception e) {
          currentDate = currentDate.minusDays(1);
          currentDateString = currentDate.toString();
        }
      }
      selectedData.put(currentDateString, value);
    }
  }

  public TreeMap<String, Double> portfolioPerformance ( FlexiblePortfolio portfolioName, LocalDate start,  LocalDate end) {
    double value = 0;
    TreeMap<String, Double> selectedData = new TreeMap<>();
    long totalDays = ChronoUnit.DAYS.between(start, end);
    long monthsDifference = ChronoUnit.MONTHS.between(start.withDayOfMonth(1), end.withDayOfMonth(1));
    long yearDiff = ChronoUnit.YEARS.between(start, end);
    int numParts;

    if ( totalDays <=30  && totalDays > 0 ) {
      numParts = (int) totalDays;
      helperPerformanceYearDiff0(numParts, start, value, totalDays, portfolioName, selectedData);
    }
    else if (totalDays == 31 ) {
      numParts = 16;
      helperPerformanceYearDiff0(numParts, start, value, totalDays, portfolioName, selectedData);
    }
    else if (monthsDifference < 5 && monthsDifference > 0 && yearDiff == 0 ) {
      numParts = Math.min(Math.max((int) (totalDays / 5), 1), 29);
      helperPerformanceYearDiff0(numParts, start, value, totalDays, portfolioName, selectedData);
    }
    else if (monthsDifference >= 5 && monthsDifference < 30 ) {
      numParts = (int) monthsDifference;
      for (int i = 0; i < numParts; i++) {
        LocalDate currentDate = start.plusMonths(i).withDayOfMonth(start.plusMonths(i).lengthOfMonth());
        String currentDateString = currentDate.toString();
        while (true) {
          try {
            value = portfolioName.portfolioValue(currentDateString, new StockData());
            break;
          }
          catch (Exception e) {
            currentDate = currentDate.minusDays(1);
            currentDateString = currentDate.toString();
          }
        }
        //selectedData.put(currentDateString, value);
        String formatDate = dateFormat(currentDateString);
        selectedData.put(formatDate, value);
      }
    }

    else if (yearDiff >= 1 && yearDiff < 5 && monthsDifference >= 30) {
      //numParts = (int) (monthsDifference/2);
      numParts = (int) Math.ceil((double) monthsDifference / 2);
      //long interval = Math.round((float) totalDays / (numParts));
      for (int i = 0; i < numParts; i++) {
        LocalDate currentDate = start.plusMonths(i*2).withDayOfMonth(start.plusMonths(i*2).lengthOfMonth());
        //LocalDate currentDate = start.plusDays(interval * i);
        String currentDateString = currentDate.toString();
        while (true) {
          try {
            value = portfolioName.portfolioValue(currentDateString, new StockData());
            break;
          }
          catch (Exception e) {
            currentDate = currentDate.minusDays(1);
            currentDateString = currentDate.toString();
          }
        }
        //selectedData.put(currentDateString, value);
        String formatDate = dateFormat(currentDateString);
        selectedData.put(formatDate, value);
      }
    }

    else if (yearDiff >= 5 && yearDiff < 30) {
      numParts = (int) yearDiff;
      for (int i = 0; i < numParts; i++) {
        LocalDate currentDate = start.plusYears(i).withMonth(12).withDayOfMonth(31);
        String currentDateString = currentDate.toString();
        while (true) {
          try {
            value = portfolioName.portfolioValue(currentDateString,new StockData() );
            break;
          }
          catch (Exception e) {
            currentDate = currentDate.minusDays(1);
            currentDateString = currentDate.toString();
          }
        }
        String formatDate = dateFormat(currentDateString);
        selectedData.put(formatDate, value);
      }
    }
    else if (yearDiff >= 30 ) {
      numParts = Math.min(Math.max((int) (totalDays / 5), 1), 29);
      helperPerformanceYearDiff0(numParts, start, value, totalDays, portfolioName, selectedData);
    }
    return selectedData;
  }


  // to convert to required date format.
  public String dateFormat(String dateString) {
    LocalDate date = LocalDate.parse(dateString);
    String formattedDate = date.format(DateTimeFormatter.ofPattern("MMM yyyy"));
    return formattedDate;
  }

  //finding scale
  public int determineScale (Map<String, Double> selectedData) {
    double maxValue = Double.MIN_VALUE;
    for (double value : selectedData.values()) {
      if (value > maxValue) {
        maxValue = value;
      }
    }
    int scale = (int) Math.round(maxValue/ 49); // or 49
    return scale;
  }

  //for printing performance in view
  public Map<String, Integer> determineValueBasedOnScale (Map<String, Double> prices, int scale) {
    Map<String, Integer> scaledPrices = new HashMap<>();
    for (Map.Entry<String, Double> entry : prices.entrySet()) {
      String date = entry.getKey();
      double price = entry.getValue();
      int scaledPrice = (int) Math.round(price / scale);
      scaledPrices.put(date, scaledPrice);
    }
    return scaledPrices;
  }

}
