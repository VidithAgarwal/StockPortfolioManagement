package model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import controller.StockData;
import java.util.Comparator;

/**
 * Performance class provides methods for analyzing the performance of stocks and portfolios.
 * Used for performance evaluation of stock trend and portfolio over time.
 */
public class Performance {


  /**
   * this method computes the performance of a stock within a specified time frame.
   * @param priceData  historical price data of the stock.
   * @param start start date of the performance analysis.
   * @param end end date of the performance analysis.
   * @return TreeMap containing the selected stock data, the timestamp and price on that day.
   * @throws IllegalArgumentException if the start date is before the last available date for stock.
   */
  public TreeMap<String, Double> stockPerformance (TreeMap<String, ArrayList <Double>> priceData,
                                                   LocalDate start, LocalDate end) {
    TreeMap<String, Double> selectedData = new TreeMap<>();
    LocalDate lastDay = returnLastEntry(priceData);
    if (start.isBefore(lastDay)) {
      throw new IllegalArgumentException("Start Date entered is before the stock listing date");
    }
    long totalDays = ChronoUnit.DAYS.between(start, end);
    long yearDiff = ChronoUnit.YEARS.between(start, end);
    long monthsDifference = ChronoUnit.MONTHS.between(start.withDayOfMonth(1), end.withDayOfMonth(1));
    int numParts;
    if ( totalDays <= 30 && totalDays > 0 ) {
      numParts = (int) totalDays;
      helperStockPerformanceYearDiff0(numParts,start,totalDays,priceData,selectedData);
    }
    else if(totalDays == 31) {
      numParts = 16;
      helperStockPerformanceYearDiff0(numParts,start,totalDays,priceData,selectedData);
    }
    else if (monthsDifference < 5 && monthsDifference > 0 && yearDiff == 0 ) {
      numParts = Math.min(Math.max((int) (totalDays / 5), 1), 29);
      helperStockPerformanceYearDiff0(numParts,start,totalDays,priceData,selectedData);
    }
    else if (monthsDifference >= 5 && monthsDifference < 30) {
      numParts = (int) monthsDifference;
      helperStockPerformanceMonthDiffBetween5And30(numParts,start,priceData,selectedData);
    }
    else if (yearDiff >= 1 && yearDiff < 5 && monthsDifference >= 30) {
      numParts = (int) Math.ceil((double) monthsDifference / 2);
      helperStockPerformanceYearDiffBetween1And5(numParts,start,priceData, selectedData);
    }
    else if (yearDiff >= 5 && yearDiff < 30) {
      numParts = (int) yearDiff;
      helperStockPerformanceYearDiffBetween5And30(numParts,start,priceData,selectedData);
    }
    else if (yearDiff >= 30 ) {
//      numParts = Math.min(Math.max((int) (totalDays / 5), 1), 29);
//      helperYearDiff0(numParts,start,totalDays,priceData,selectedData);
      numParts = (int) Math.ceil((double) yearDiff / 2);
      while(numParts > 30) {
        numParts = (int) Math.ceil((double)numParts/2);
      }
      helperStockPerformanceYearDiffMoreThan30(numParts,yearDiff,start,priceData,selectedData);
    }
    return selectedData;
  }

  private void helperStockPerformanceYearDiffMoreThan30(int numParts, long yearDiff, LocalDate start,Map<String,
          ArrayList<Double>> priceData, TreeMap<String, Double> selectedData )  {
    for (int i = 0; i <= numParts; i++) {
      long interval = Math.round((float) yearDiff / (numParts));
      LocalDate currentDate = start.plusYears(i*interval).withMonth(12).withDayOfMonth(31);
      String currentDateString = currentDate.toString();
      while (!priceData.containsKey((currentDateString))) {
        currentDate = currentDate.minusDays(1);
        currentDateString = currentDate.toString();
      }
      ArrayList<Double> values = priceData.get(currentDateString);
      Double closingValue = values.get(3);
      String formatDate = dateFormat(currentDateString);
      selectedData.put(formatDate, closingValue);
    }
  }

  /**
   * this is helper method to populate selectedData with stock closing prices.
   * for a specified time period and put timestamp along with their price data in selectData.
   * @param numParts  number of parts to divide the time period into.
   * @param start start date of the time period.
   * @param totalDays  total number of days in the time period.
   * @param priceData historical price data of the stock.
   * @param selectedData  TreeMap to store selected stock data.
   */
  private void helperStockPerformanceYearDiff0 (int numParts, LocalDate start, long totalDays, Map<String,
          ArrayList<Double>> priceData, TreeMap<String, Double> selectedData ) {
    long interval = Math.round((float) totalDays / (numParts));
    for (int i = 0; i < numParts; i++) {
      LocalDate currentDate = start.plusDays(interval * i);
      String currentDateString = currentDate.toString();
      while (!priceData.containsKey((currentDateString)) ) { //&& currentDate.isAfter(lastDay)
        currentDate = currentDate.minusDays(1);
        currentDateString = currentDate.toString();
      }
      ArrayList<Double> values = priceData.get(currentDateString);
      Double closingValue = values.get(3);
      selectedData.put(currentDateString, closingValue);
    }
  }

  private void helperStockPerformanceYearDiffBetween5And30 (int numParts, LocalDate start, Map<String,
          ArrayList<Double>> priceData, TreeMap<String, Double> selectedData ) {
    for (int i = 0; i <= numParts; i++) {
      LocalDate currentDate = start.plusYears(i).withMonth(12).withDayOfMonth(31);
      String currentDateString = currentDate.toString();
      while (!priceData.containsKey((currentDateString))) {
        currentDate = currentDate.minusDays(1);
        currentDateString = currentDate.toString();
      }
      ArrayList<Double> values = priceData.get(currentDateString);
      Double closingValue = values.get(3);
      String formatDate = dateFormat(currentDateString);
      selectedData.put(formatDate, closingValue);
    }
  }

  private void helperStockPerformanceYearDiffBetween1And5 (int numParts, LocalDate start,Map<String,
          ArrayList<Double>> priceData, TreeMap<String, Double> selectedData  ) {
    for (int i = 0; i < numParts; i++) {
      LocalDate currentDate = start.plusMonths(i*2)
              .withDayOfMonth(start.plusMonths(i*2).lengthOfMonth());
      //LocalDate currentDate = start.plusDays(interval * i);
      String currentDateString = currentDate.toString();

      while (!priceData.containsKey((currentDateString))) {
        currentDate = currentDate.minusDays(1);
        currentDateString = currentDate.toString();
      }
      ArrayList<Double> values = priceData.get(currentDateString);
      Double closingValue = values.get(3);
      String formatDate = dateFormat(currentDateString);
      selectedData.put(formatDate, closingValue);
    }
  }

  private void helperStockPerformanceMonthDiffBetween5And30 (int numParts,LocalDate start, Map<String,
          ArrayList<Double>> priceData, TreeMap<String, Double> selectedData) {
    for (int i = 0; i < numParts; i++) {
      LocalDate currentDate = start.plusMonths(i).withDayOfMonth(start.plusMonths(i).lengthOfMonth());
      String currentDateString = currentDate.toString();
      while (!priceData.containsKey((currentDateString))) {
        currentDate = currentDate.minusDays(1);
        currentDateString = currentDate.toString();
      }
      ArrayList<Double> values = priceData.get(currentDateString);
      Double closingValue = values.get(3);
      String formatDate = dateFormat(currentDateString);
      selectedData.put(formatDate, closingValue);
    }
  }

  /**
   * this method returns the last available date in the provided price data.
   * @param priceData historical price data of the stock.
   * @return last available date for that stock, that is when it was listed.
   * @throws IllegalArgumentException if the price data is empty.
   */
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


  /**
   * this is a helper method to populate selectedData with portfolio values.
   * When the timestamps are taken as days in the specified time period.
   * @param numParts number of parts to divide the time period into.
   * @param start start date of the time period.
   * @param value portfolio value at the specified date.
   * @param totalDays  total number of days in the time period.
   * @param portfolioName portfolio object, for which total value is calculated to find performance.
   * @param selectedData TreeMap to store selected portfolio data, timestamp & portfolio value.
   */
  private void helperPortfolioPerformanceYearDiff0 (int numParts, LocalDate start, double value, long totalDays, Portfolio portfolioName, TreeMap <String, Double> selectedData ) {
    long interval = Math.round((float) totalDays / (numParts));
    for (int i = 0; i < numParts; i++) {
      LocalDate currentDate = start.plusDays(interval * i);
      String currentDateString = currentDate.toString();
      while (true) {
        try {
          value = portfolioName.portfolioValue(currentDateString, new StockData());
          break;
        }
        catch (IllegalArgumentException e) {
          if (e.getMessage().equalsIgnoreCase("The share was not listed")) {
            throw new IllegalArgumentException("Cannot find the performance as one of the share was not listed!");
          }
          currentDate = currentDate.minusDays(1);
          currentDateString = currentDate.toString();
        }
      }
      selectedData.put(currentDateString, value);
    }
  }

  /**
   * this method computes performance of a portfolio within a specified time frame/ period.
   * @param portfolioName portfolio object, whose performance is to be determined.
   * @param start start date of the performance analysis.
   * @param end end date of the performance analysis.
   * @return A TreeMap containing the selected portfolio data, timestamp and price on that day .
   * @throws IllegalArgumentException if the portfolio is not flexible.
   */
  public TreeMap<String, Double> portfolioPerformance ( Portfolio portfolioName, LocalDate start,  LocalDate end) {
    double value = 0;
    TreeMap<String, Double> selectedData = new TreeMap<>();
    long totalDays = ChronoUnit.DAYS.between(start, end);
    long monthsDifference = ChronoUnit.MONTHS.between(start.withDayOfMonth(1), end.withDayOfMonth(1));
    long yearDiff = ChronoUnit.YEARS.between(start, end);
    int numParts;
    if ( totalDays <= 30  && totalDays > 0 ) {
      numParts = (int) totalDays;
      helperPortfolioPerformanceYearDiff0(numParts, start, value, totalDays, portfolioName, selectedData);
    }
    else if (totalDays == 31 ) {
      numParts = 16;
      helperPortfolioPerformanceYearDiff0(numParts, start, value, totalDays, portfolioName, selectedData);
    }
    else if (monthsDifference < 5 && monthsDifference > 0 && yearDiff == 0 ) {
      numParts = Math.min(Math.max((int) (totalDays / 5), 1), 29);
      helperPortfolioPerformanceYearDiff0(numParts, start, value, totalDays, portfolioName, selectedData);
    }
    else if (monthsDifference >= 5 && monthsDifference < 30 ) {
      numParts = (int) monthsDifference;
      helperPortfolioPerformanceMonthDiffBetween5And30(numParts,start,value,portfolioName,selectedData);
    }
    else if (yearDiff >= 1 && yearDiff < 5 && monthsDifference >= 30) {
      numParts = (int) Math.ceil((double) monthsDifference / 2);
      helperPortfolioPerformanceMonthDiffBetween1And5(numParts,start,value,portfolioName,selectedData);
    }
    else if (yearDiff >= 5 && yearDiff < 30) {
      numParts = (int) yearDiff;
      helperPortfolioPerformanceYearDiffBetween1And5(numParts,start,value,portfolioName,selectedData);
    }
    else if (yearDiff >= 30 ) {
      numParts = (int) Math.ceil((double) yearDiff / 2);
      while(numParts > 30) {
        numParts = (int) Math.ceil((double)numParts/2);
      }
      //numParts = Math.min(Math.max((int) (totalDays / 5), 1), 29);
      //helperPerformanceYearDiff0(numParts, start, value, totalDays, portfolioName, selectedData);
      helperPortfolioPerformanceYearDiffMoreThan30(numParts,start,value,portfolioName,selectedData,yearDiff);
    }
    return selectedData;
  }



  /**
   * this method formats a date string to the required format for performance , MMM yyyy.
   * @param dateString The date string to be formatted.
   * @return The formatted date string, in above format.
   */
  private String dateFormat(String dateString) {
    LocalDate date = LocalDate.parse(dateString);
    String formattedDate = date.format(DateTimeFormatter.ofPattern("MMM yyyy"));
    return formattedDate;
  }

  /**
   * this method determines scale for visualizing performance data overtime.
   * @param selectedData it is price data for which scale is calculated for better representation.
   * @return the scale value for that timestamps and price data.
   */
  public int determineScale (TreeMap<String, Double> selectedData) {
    double maxValue = Double.MIN_VALUE;
    for (double value : selectedData.values()) {
      if (value > maxValue) {
        maxValue = value;
      }
    }
    int scale = (int) Math.ceil(maxValue/ 49);
    return scale;
  }

  /**
   * this method scales the prices for the timestamps, based on the calculated scale.
   * this is used to determine length of bar chart in representation.
   * @param prices the prices to be scaled for the stock or portfolio performance representation.
   * @param scale the scale factor.
   * @return TreeMap containing scaled prices with timestamp.
   */
  public TreeMap<String, Integer> determineValueBasedOnScale (TreeMap<String, Double> prices, int scale) {
    TreeMap<String, Integer> scaledPrices = new TreeMap<>();
    for (Map.Entry<String, Double> entry : prices.entrySet()) {
      String date = entry.getKey();
      double price = entry.getValue();
      int scaledPrice = (int) Math.round(price / scale);
      scaledPrices.put(date, scaledPrice);
    }
    return scaledPrices;
  }


  public TreeMap<String, Integer> sortTreeMapByMonthAndYear(TreeMap<String, Integer> data) {
    boolean isMonthYearFormat = true;
    for (String key : data.keySet()) {
      if (!key.matches("^[A-Za-z]{3} \\d{4}$")) {
        isMonthYearFormat = false;
        break;
      }
    }
    if (!isMonthYearFormat) {
      return data;
    }
    Comparator<String> customComparator = (s1, s2) -> {
      String[] parts1 = s1.split(" ");
      String[] parts2 = s2.split(" ");

      int yearComparison = parts1[1].compareTo(parts2[1]);
      if (yearComparison != 0) {
        return yearComparison;
      }
      TreeMap<String, Integer> months = new TreeMap<>();
      months.put("Jan", 1);
      months.put("Feb", 2);
      months.put("Mar", 3);
      months.put("Apr", 4);
      months.put("May", 5);
      months.put("Jun", 6);
      months.put("Jul", 7);
      months.put("Aug", 8);
      months.put("Sep", 9);
      months.put("Oct", 10);
      months.put("Nov", 11);
      months.put("Dec", 12);
      return months.get(parts1[0]).compareTo(months.get(parts2[0]));
    };
    TreeMap<String, Integer> orderedData = new TreeMap<>(customComparator);
    orderedData.putAll(data);
    return orderedData;
  }

  private void helperPortfolioPerformanceMonthDiffBetween5And30 (int numParts, LocalDate start,
                                                                double value, Portfolio portfolioName,
                                                                TreeMap <String, Double> selectedData) {
    for (int i = 0; i < numParts; i++) {
      LocalDate currentDate = start.plusMonths(i).withDayOfMonth(start.plusMonths(i).lengthOfMonth());
      String currentDateString = currentDate.toString();
      while (true) {
        try {
          value = portfolioName.portfolioValue(currentDateString, new StockData());
          break;
        }
        catch (IllegalArgumentException e) {
          if (e.getMessage().equalsIgnoreCase("The share was not listed")) {
            throw new IllegalArgumentException("Cannot find the performance as one of the share was not listed!");
          }
          currentDate = currentDate.minusDays(1);
          currentDateString = currentDate.toString();
        }
      }
      String formatDate = dateFormat(currentDateString);
      selectedData.put(formatDate, value);
    }
  }

  private void helperPortfolioPerformanceMonthDiffBetween1And5 (int numParts, LocalDate start,
                                          double value, Portfolio portfolioName,
                                          TreeMap <String, Double> selectedData) {
    for (int i = 0; i < numParts; i++) {
      LocalDate currentDate = start.plusMonths(i*2).withDayOfMonth(start.plusMonths(i*2).lengthOfMonth());
      String currentDateString = currentDate.toString();
      while (true) {
        try {
          value = portfolioName.portfolioValue(currentDateString, new StockData());
          break;
        }
        catch (IllegalArgumentException e) {
          if (e.getMessage().equalsIgnoreCase("The share was not listed")) {
            throw new IllegalArgumentException("Cannot find the performance as one of the share was not listed!");
          }
          currentDate = currentDate.minusDays(1);
          currentDateString = currentDate.toString();
        }
      }
      //selectedData.put(currentDateString, value);
      String formatDate = dateFormat(currentDateString);
      selectedData.put(formatDate, value);
    }
  }

  private void helperPortfolioPerformanceYearDiffBetween1And5 (int numParts, LocalDate start,
                                                              double value, Portfolio portfolioName,
                                                              TreeMap <String, Double> selectedData) {
    for (int i = 0; i <= numParts; i++) {
      LocalDate currentDate = start.plusYears(i).withMonth(12).withDayOfMonth(31);
      String currentDateString = currentDate.toString();
      while (true) {
        try {
          value = portfolioName.portfolioValue(currentDateString,new StockData() );
          break;
        }
        catch (IllegalArgumentException e) {
          if (e.getMessage().equalsIgnoreCase("The share was not listed")) {
            throw new IllegalArgumentException("Cannot find the performance as one of the share was not listed!");
          }
          currentDate = currentDate.minusDays(1);
          currentDateString = currentDate.toString();
        }
      }
      String formatDate = dateFormat(currentDateString);
      selectedData.put(formatDate, value);
    }
  }

  private void helperPortfolioPerformanceYearDiffMoreThan30 (int numParts, LocalDate start,
                                                             double value, Portfolio portfolioName,
                                                             TreeMap <String, Double> selectedData,
                                                             long yearDiff) {
    for (int i = 0; i <= numParts; i++) {
      long interval = Math.round((float) yearDiff / (numParts));
      LocalDate currentDate = start.plusYears(i*interval).withMonth(12).withDayOfMonth(31);
      String currentDateString = currentDate.toString();
      while (true) {
        try {
          value = portfolioName.portfolioValue(currentDateString,new StockData() );
          break;
        }
        catch (IllegalArgumentException e) {
          if (e.getMessage().equalsIgnoreCase("The share was not listed")) {
            throw new IllegalArgumentException("Cannot find the performance as one of the share was not listed!");
          }
          currentDate = currentDate.minusDays(1);
          currentDateString = currentDate.toString();
        }
      }
      String formatDate = dateFormat(currentDateString);
      selectedData.put(formatDate, value);
    }
  }



}
