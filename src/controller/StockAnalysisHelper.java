package controller;

import java.time.LocalDate;
import java.util.Scanner;
import java.util.TreeMap;

import model.PortfolioDir;
import view.IView;

class StockAnalysisHelper extends AbsHelperController {

  StockAnalysisHelper(IView view, PortfolioDir model, Scanner scan) {
    super(view, model, scan);
  }

  void stockPerformance() {
    StockData api = new StockData();
    view.print("Enter the name of the share or ticker symbol: ");
    String ticker = scan.nextLine();
    int[] startDateArray = inputDate("Enter the start date");
    int[] endDateArray = inputDate("Enter the end date");
    LocalDate startDate = LocalDate.of(startDateArray[2], startDateArray[1],startDateArray[0]);
    LocalDate endDate = LocalDate.of(endDateArray[2], endDateArray[1],endDateArray[0]);
    TreeMap<String, Integer> result;
    try {
      result = model.stockPerformance(ticker, api,startDate,endDate);
      int scale = model.scaleForStockPerformance(ticker, api,startDate,endDate);
      view.barGraph(scale,result, ticker, startDate+"", endDate+"");
    } catch (IllegalArgumentException e) {
      view.displayError(e.getMessage());
    }
  }

  void gainOrLose() {
    StockData api = new StockData();
    view.print("Enter the name of the share or ticker symbol: ");
    String ticker = scan.nextLine();
    int[] dateArray = inputDate("Enter the date to know if the above stock gained or lost on that" +
            " " +
            "date: ");

    LocalDate date = LocalDate.of(dateArray[2], dateArray[1],dateArray[0]);

    try {
      String result = model.gainOrLose(ticker, date, api);
      view.print(result);
    } catch (IllegalArgumentException e) {
      view.displayError(e.getMessage());
    }

  }

  void gainOrLoseOverPeriod() {
    StockData api = new StockData();
    view.print("Enter the name of the share or ticker symbol: ");
    String ticker = scan.nextLine();
    int[] startDateArray = inputDate("Enter the start date");
    int[] endDateArray = inputDate("Enter the end date");
    LocalDate startDate = LocalDate.of(startDateArray[2], startDateArray[1],startDateArray[0]);
    LocalDate endDate = LocalDate.of(endDateArray[2], endDateArray[1],endDateArray[0]);

    try {
      String result = model.gainOrLoseOverAPeriod(ticker, startDate, endDate, api);
      view.print(result);
    } catch (IllegalArgumentException e) {
      view.displayError(e.getMessage());
    }

  }

  void xDayMovingAvg() {
    StockData api = new StockData();
    view.print("Enter the name of the share or ticker symbol: ");
    String ticker = scan.nextLine();
    int[] startDateArray = inputDate("Enter the start date");
    LocalDate startDate = LocalDate.of(startDateArray[2], startDateArray[1],startDateArray[0]);
    int x = inputPositiveInteger("Enter X days before the given date you want to find the moving " +
            "average for: ");
    try {
      double result = model.xDayMovingAvg(ticker, startDate, x, api);
      view.print(result + "");
    } catch (IllegalArgumentException e) {
      view.displayError(e.getMessage());
    }
  }

  void crossoverOverPeriod() {
    StockData api = new StockData();
    view.print("Enter the name of the share or ticker symbol: ");
    String ticker = scan.nextLine();
    int[] startDateArray = inputDate("Enter the start date");
    int[] endDateArray = inputDate("Enter the end date");
    LocalDate startDate = LocalDate.of(startDateArray[2], startDateArray[1],startDateArray[0]);
    LocalDate endDate = LocalDate.of(endDateArray[2], endDateArray[1],endDateArray[0]);
    try {
      TreeMap<String, String> result = model.crossoverOverPeriod(ticker, api, startDate, endDate);
      view.printTreeMapEntries(result);
    } catch (IllegalArgumentException e) {
      view.displayError(e.getMessage());
    }

  }

  void movingCrossoversOverPeriod() {
    StockData api = new StockData();
    view.print("Enter the name of the share or ticker symbol: ");
    String ticker = scan.nextLine();
    int[] startDateArray = inputDate("Enter the start date");
    int[] endDateArray = inputDate("Enter the end date");
    LocalDate startDate = LocalDate.of(startDateArray[2], startDateArray[1],startDateArray[0]);
    LocalDate endDate = LocalDate.of(endDateArray[2], endDateArray[1],endDateArray[0]);
    TreeMap<String, String> result = new TreeMap<>();

    int x = inputPositiveInteger("Enter the value of x (shorter moving average period): ");
    int y = inputPositiveInteger("Enter the value of y (longer moving average period, greater " +
            "than x): ");

    try {

      result = model.movingCrossOver(ticker, api, startDate, endDate, x
              , y);
      view.printTreeMapEntries(result);
    } catch (IllegalArgumentException e) {
      view.displayError(e.getMessage());
    }
  }
}
