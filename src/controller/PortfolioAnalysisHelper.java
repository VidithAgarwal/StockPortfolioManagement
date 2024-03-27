package controller;

import java.time.LocalDate;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;
import java.util.TreeMap;

import model.PortfolioDir;
import view.IView;

/**
 * PortfolioAnalysisHelper class provides helper methods for analyzing portfolios.
 * It extends AbsHelperController class, and uses view and model methods.
 */
class PortfolioAnalysisHelper extends AbsHelperController {

  /**
   * constructs a PortfolioAnalysisHelper object with specified view, model, & scanner.
   * @param view view component for user interaction.
   * @param model model component for managing portfolios.
   * @param scan scanner object for user input.
   */
  PortfolioAnalysisHelper(IView view, PortfolioDir model, Scanner scan) {
    super(view, model, scan);
  }

  /**
   * this method displays the composition of a portfolio selected by the user through the view.
   * The date is also given to the model method and view is used to prompt to user to enter date.
   * The values in portfolio its composition is got from the model.
   */
  void examineComposition() {
    int input = inputPortfolioChoice();
    int[] date = inputDate("Enter the date you want to see the composition for: ");
    LocalDate compositionDate = LocalDate.of(date[2], date[1], date[0]);
    try {
      view.showComposition(model.portfolioComposition(input, compositionDate));
    } catch (IllegalArgumentException e) {
      view.displayError(e.getMessage());
    }
  }

  /**
   * this method gets the total value for the portfolio selected by the user.
   * total value is calculated in portfolioValue method in model to which the input date is passed.
   * the view methods are also called to pass error messages if data is not found for the date.
   */
  void getTotalValue() {
    int choice = inputPortfolioChoice();

    int[] date = inputDate("Enter the date for which you want to get the total"
            + " price of the portfolio. ");

    view.print("Wait until the total value is calculated");

    try {
      StockData api = new StockData();
      double totalValue = model.portfolioValue(choice, date[0], date[1], date[2], api);
      view.showTotalValue(totalValue);
    } catch (IllegalArgumentException e) {
      if (e.getMessage() != null) {
        view.print("No price data found for " + e.getMessage() + " on the "
                + "date: " + date[2] + "-" + date[1] + "-" + date[0]);
      } else {
        view.print("Invalid date!");
      }
    } catch (RuntimeException e) {
      view.print("The data could not be fetched today, try again later!");
    }
  }

  /**
   * this method calculates and displays the cost basis of a selected portfolio till a given date.
   * using the view and model methods.
   */
  void getCostBasis() {
    int choice = inputPortfolioChoice();

    int[] date = inputDate("Enter the date till which you want the cost basis of the portfolio");
    LocalDate costBasisDate = LocalDate.of(date[2], date[1],date[0]);

    try {
      double costBasis = model.costBasis(choice, costBasisDate, new StockData());
      view.print("The cost basis is: $" + costBasis);
    } catch (IllegalArgumentException e) {
      view.displayError(e.getMessage());
    }
  }

  /**
   * this method generates and displays performance of a selected portfolio over a given period.
   * using the view, to prompt the user to enter data and model methods.
   */
  void portfolioPerformance() {
    int choice = inputPortfolioChoice();
    String portfolioName = "";
    Map<String,String> portfolioList = model.getListOfPortfoliosName();
    int index = 0;
    for (Map.Entry<String, String> entry : portfolioList.entrySet()) {
      if (index == choice) {
        portfolioName = entry.getKey();
        break;
      }
      index++;
    }
    if (Objects.equals(portfolioName, "")) {
      view.displayError("No such portfolio found");
      return;
    }
    int[] startDateArray = inputDate("Enter the start date");
    int[] endDateArray = inputDate("Enter the end date");
    LocalDate startDate = LocalDate.of(startDateArray[2], startDateArray[1],startDateArray[0]);
    LocalDate endDate = LocalDate.of(endDateArray[2], endDateArray[1],endDateArray[0]);
    TreeMap<String, Integer> result;
    try {
      result = model.portfolioPerformance(choice,startDate,endDate);
      int scale = model.scaleForPortfolioPerformance(choice,startDate,endDate);
      view.barGraph(scale,result, portfolioName, startDate + "", endDate + "");
    } catch (RuntimeException e) {
      view.displayError(e.getMessage());
    }
  }
}
