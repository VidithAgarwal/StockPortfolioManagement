package controller;

import java.time.LocalDate;
import java.util.Scanner;
import java.util.TreeMap;

import model.PortfolioDir;
import view.IView;

/**
 * StockAnalysisHelper class provides methods for performing diff stock analysis operations.
 * such as calculating stock performance, gain/loss over a period, x-day moving average.
 * crossover events over a period, & moving crossovers over a period using view and model methods.
 */
class StockAnalysisHelper extends AbsHelperController {

  /**
   * this constructs a new StockAnalysisHelper object with view, model, and scanner.
   * @param view  view component for displaying information to the user.
   * @param model model component for accessing portfolio data.
   * @param scan scanner object for user input.
   */
  StockAnalysisHelper(IView view, PortfolioDir model, Scanner scan) {
    super(view, model, scan);
  }

  /**
   * this method calculates & displays performance of a specific stock over a given period,
   * using view and model methods.
   */
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
      view.barGraph(scale,result, ticker, startDate + "", endDate + "");
    } catch (IllegalArgumentException e) {
      view.displayError(e.getMessage());
    }
  }

  /**
   * this method calculates & displays whether a specific stock gained or lost on a particular date.
   * using view and model methods.
   */
  void gainOrLose() {
    StockData api = new StockData();
    view.print("Enter the name of the share or ticker symbol: ");
    String ticker = scan.nextLine();
    int[] dateArray = inputDate("Enter the date to know if the above stock gained or lost on that"
            + " "
            + "date: ");

    LocalDate date = LocalDate.of(dateArray[2], dateArray[1],dateArray[0]);

    try {
      String result = model.gainOrLose(ticker, date, api);
      view.print(result);
    } catch (IllegalArgumentException e) {
      view.displayError(e.getMessage());
    }

  }

  /**
   * this method calculates & displays whether specific stock gained or lost over specified period.
   * using view and model methods.
   */
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

  /**
   * this method calculates & displays X-day moving average for specific stock on particular date.
   * using view and model methods.
   */
  void xDayMovingAvg() {
    StockData api = new StockData();
    view.print("Enter the name of the share or ticker symbol: ");
    String ticker = scan.nextLine();
    int[] startDateArray = inputDate("Enter the start date");
    LocalDate startDate = LocalDate.of(startDateArray[2], startDateArray[1],startDateArray[0]);
    int x = inputPositiveInteger("Enter X days before the given date you want to find the moving "
            + "average for: ");
    try {
      double result = model.xDayMovingAvg(ticker, startDate, x, api);
      view.showXDayMovingAvg(result);
    } catch (IllegalArgumentException e) {
      view.displayError(e.getMessage());
    }
  }


  /**
   * this method calculates & displays crossover events over specified period for given stock.
   * using view and model methods.
   */
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

  /**
   * this method calculates & displays moving crossovers over specified period for given stock.
   * using view and model methods.
   */
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
    int y = inputPositiveInteger("Enter the value of y (longer moving average period, greater "
            + "than x): ");
    try {
      result = model.movingCrossOver(ticker, api, startDate, endDate, x, y);
      view.printTreeMapEntries(result);
    } catch (IllegalArgumentException e) {
      view.displayError(e.getMessage());
    }
  }
}
