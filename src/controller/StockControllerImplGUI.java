package controller;


import java.time.LocalDate;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;
import java.util.regex.Pattern;

import model.InvestmentManager;

import view.IViewGUI;


public class StockControllerImplGUI implements Features  {

  private IViewGUI view;

  private final InvestmentManager model;

  /**
   * Constructs a StockControllerImpl object with the specified view, input stream.
   * and portfolio directory.
   *
   * @param view         The view component responsible for displaying information to the user.
   * @param portfolioDir The portfolio directory model component representing the model object.
   */
  public StockControllerImplGUI(IViewGUI view, InvestmentManager portfolioDir) {
    this.model = portfolioDir;
    this.view = view;
  }

  public void setView(IViewGUI v) {
    view = v;
    //provide view with all the callbacks
    view.addFeatures(this);
  }
  private String errorMessage;
  private String successMessage;

  public String getSuccessMessage() {
    return successMessage;
  }

  public void createFlexiblePortfolio(String name) {
    successMessage = null;
    errorMessage = null;
    if (model.portfolioNameExists(name)) {
      errorMessage = "Portfolio with this name already exists!";
      return;
    }
    model.createFlexiblePortfolio(name);
    successMessage = "Portfolio created successfully.";

  }

  public void export(int input,String path) {
    // here in view we will provide drop down for selecting portfolio
    // THE DATA WILL BE DISPLAYED IN THAT ORDER OF INDEX OF PORTFOLIOS
    Persistence persistence = new Persistence();
    try {
      persistence.exportAsCSV(path, model.save(input));
      successMessage = "Portfolio exported to " + path + " successfully.";

    } catch (IllegalArgumentException e) {
      errorMessage = e.getMessage();
      //view.displayError(e.getMessage());
    }

  }

  public void buyStock(String date, String quantity, String shareName, int choice) {
    errorMessage = null;
    successMessage = null;
    int shareQuantity;
      try {
        shareQuantity = Integer.parseInt(quantity);
        if (shareQuantity <= 0) {
          //view.displayError("Share quantity must be a positive integer.");
          errorMessage = "Share quantity must be a positive integer.";
          return;
        }
      } catch (NumberFormatException e) {
        //view.displayError("Share quantity must be a positive integer.");
        errorMessage = "Share quantity must be a positive integer.";
         // Set to negative to continue the loop
        return;
      }
    boolean validDate = false;
    int day = 0;
    int month = 0;
    int year = 0;
    if (isValidDateFormat(date)) {
      String[] dateParts = date.split("-");
      year = Integer.parseInt(dateParts[0].trim());
      month = Integer.parseInt(dateParts[1].trim());
      day = Integer.parseInt(dateParts[2].trim());

      if (validateDate(day, month, year)) {
        validDate = true;
      } else {
        errorMessage = "Invalid date!";
        return;
      }
    } else {
      errorMessage = "Invalid date format.";
      return;
    }
    LocalDate buyDate = LocalDate.parse(date);

    try {
      model.buyStock(choice, shareName, shareQuantity, buyDate, new StockData());
      successMessage = quantity + " " + shareName + " bought successfully";
      //view.print(quantity + " " + shareName + " bought successfully");
    } catch (RuntimeException e) {
      errorMessage = e.getMessage();
      //view.displayError(e.getMessage());
    }

  }

  private boolean isValidDateFormat(String date) {
    String regex = "\\d{4}-\\d{2}-\\d{2}";
    return Pattern.matches(regex, date);
  }

  private boolean validateDate(int day, int month, int year) {
    if (month < 1 || month > 12) {
      return false;
    }

    if (day < 1 || day > 31) {
      return false;
    }

    if ((month == 4 || month == 6 || month == 9 || month == 11) && day > 30) {
      return false;
    }

    if (month == 2) {
      boolean isLeapYear = (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
      return (!isLeapYear || day <= 29) && (isLeapYear || day <= 28);
    }

    return year >= 0 && year <= 9999;
  }

  public void sellStock(String date, String quantity, String shareName, int choice) {
    errorMessage = null;
    successMessage = null;
    int shareQuantity;
    try {
      shareQuantity = Integer.parseInt(quantity);
      if (shareQuantity <= 0) {
        //view.displayError("Share quantity must be a positive integer.");
        errorMessage = "Share quantity must be a positive integer.";
        return;
      }
    } catch (NumberFormatException e) {
      //view.displayError("Share quantity must be a positive integer.");
      errorMessage = "Share quantity must be a positive integer.";
      // Set to negative to continue the loop
      return;
    }
    boolean validDate = false;
    int day = 0;
    int month = 0;
    int year = 0;
    if (isValidDateFormat(date)) {
      String[] dateParts = date.split("-");
      year = Integer.parseInt(dateParts[0].trim());
      month = Integer.parseInt(dateParts[1].trim());
      day = Integer.parseInt(dateParts[2].trim());

      if (validateDate(day, month, year)) {
        validDate = true;
      } else {
        errorMessage = "Invalid date!";
        return;
      }
    } else {
      errorMessage = "Invalid date format.";
      return;
    }
    LocalDate sellDate = LocalDate.parse(date);
    //sellStock(date,shareQuantity,name,choice);
    try {
      model.sellStock(choice, shareName, shareQuantity, sellDate, new StockData());
      successMessage = quantity + " " + shareName + " sold successfully";
      //view.print(quantity + " " + shareName + " sold successfully");
    } catch (RuntimeException e) {
      errorMessage = e.getMessage();
      //view.displayError(e.getMessage());
    }
  }

  public void examineComposition(int input, String date) {
    errorMessage = null;
    successMessage = null;
    boolean validDate = false;
    int day = 0;
    int month = 0;
    int year = 0;
    if (isValidDateFormat(date)) {
      String[] dateParts = date.split("-");
      year = Integer.parseInt(dateParts[0].trim());
      month = Integer.parseInt(dateParts[1].trim());
      day = Integer.parseInt(dateParts[2].trim());

      if (validateDate(day, month, year)) {
        validDate = true;
      } else {
        errorMessage = "Invalid date!";
        return;
        //view.displayError("Invalid date!");
      }
    } else {
      errorMessage = "Invalid date format.";
      return;
      //view.displayError("Invalid date format.");
    }
    LocalDate compositionDate = LocalDate.parse(date);

    try {
//      view.showComposition(model.portfolioComposition(input, compositionDate));
    } catch (IllegalArgumentException e) {
      //view.displayError(e.getMessage());
      errorMessage = e.getMessage();
    }
    //examineComposition(choice,date);
  }

  public void getTotalValue(int choice, String date) {
    errorMessage = null;
    successMessage = null;
    boolean validDate = false;
    int day = 0;
    int month = 0;
    int year = 0;
    if (isValidDateFormat(date)) {
      String[] dateParts = date.split("-");
      year = Integer.parseInt(dateParts[0].trim());
      month = Integer.parseInt(dateParts[1].trim());
      day = Integer.parseInt(dateParts[2].trim());

      if (validateDate(day, month, year)) {
        validDate = true;
      } else {
        errorMessage = "Invalid date!";
        return;
        //view.displayError("Invalid date!");
      }
    } else {
      errorMessage = "Invalid date format.";
      return;
      //view.displayError("Invalid date format.");
    }
    try {
      StockData api = new StockData();
      double totalValue = model.portfolioValue(choice, year, month, day, api);
//      view.showTotalValue(totalValue);
    } catch (IllegalArgumentException e) {
      if (e.getMessage() != null) {
        //view.print("No price data found for " + e.getMessage() + " on the " + "date: " + day + "-" + month + "-" + year);
        successMessage = "No price data found for " + e.getMessage() + " on the "
                + "date: " + day + "-" + month + "-" + year;
      } else {
        //view.print("Invalid date!");
        successMessage = "Invalid date!";
      }
    } catch (RuntimeException e) {
      //view.print("The data could not be fetched today, try again later!");
      successMessage = "The data could not be fetched today, try again later!";
    }
    //getTotalValue(choice, date);
  }

  public void getCostBasis(int choice, String date) {
    errorMessage = null;
    successMessage = null;
    boolean validDate = false;
    int day = 0;
    int month = 0;
    int year = 0;
    if (isValidDateFormat(date)) {
      String[] dateParts = date.split("-");
      year = Integer.parseInt(dateParts[0].trim());
      month = Integer.parseInt(dateParts[1].trim());
      day = Integer.parseInt(dateParts[2].trim());

      if (validateDate(day, month, year)) {
        validDate = true;
      } else {
        errorMessage = "Invalid date!";
        return;
        //view.displayError("Invalid date!");
      }
    } else {
      errorMessage = "Invalid date format.";
      return;
      //view.displayError("Invalid date format.");
    }
    LocalDate costBasisDate = LocalDate.parse(date);
    //LocalDate costBasisDate = LocalDate.of(date[2], date[1], date[0]);

    try {
      double costBasis = model.costBasis(choice, costBasisDate, new StockData());
      successMessage = "The cost basis is: $" + costBasis;
      //view.print("The cost basis is: $" + costBasis);
    } catch (IllegalArgumentException e) {
      errorMessage = e.getMessage();
      //view.displayError(e.getMessage());
    }
  }

  public void gainOrLose(String date, String ticker) {
    StockData api = new StockData();
    errorMessage = null;
    successMessage = null;
    boolean validDate = false;
    int day = 0;
    int month = 0;
    int year = 0;
    if (isValidDateFormat(date)) {
      String[] dateParts = date.split("-");
      year = Integer.parseInt(dateParts[0].trim());
      month = Integer.parseInt(dateParts[1].trim());
      day = Integer.parseInt(dateParts[2].trim());

      if (validateDate(day, month, year)) {
        validDate = true;
      } else {
        errorMessage = "Invalid date!";
        return;
        //view.displayError("Invalid date!");
      }
    } else {
      errorMessage = "Invalid date format.";
      return;
      //view.displayError("Invalid date format.");
    }
    LocalDate date1 = LocalDate.parse(date);

    //LocalDate date = LocalDate.of(dateArray[2], dateArray[1], dateArray[0]);

    try {
      String result = model.gainOrLose(ticker, date1, api);
      //view.print(result);
      successMessage = result;
    } catch (IllegalArgumentException e) {
      //view.displayError(e.getMessage());
      errorMessage = e.getMessage();
    }
  }

  public void gainOrLoseOverPeriod(String startDateArray, String endDateArray, String ticker) {
    StockData api = new StockData();
    boolean validDate = false;
    int day = 0;
    int month = 0;
    int year = 0;
    if (isValidDateFormat(startDateArray)) {
      String[] dateParts = startDateArray.split("-");
      year = Integer.parseInt(dateParts[0].trim());
      month = Integer.parseInt(dateParts[1].trim());
      day = Integer.parseInt(dateParts[2].trim());

      if (validateDate(day, month, year)) {
        validDate = true;
      } else {
        errorMessage = "Invalid start date!";
        return;
        //view.displayError("Invalid date!");
      }
    } else {
      errorMessage = "Invalid start date format.";
      return;
      //view.displayError("Invalid date format.");
    }
    LocalDate startDate = LocalDate.parse(startDateArray);

    boolean validDate1 = false;
    int day1 = 0;
    int month1 = 0;
    int year1 = 0;
    if (isValidDateFormat(endDateArray)) {
      String[] dateParts1 = startDateArray.split("-");
      year1 = Integer.parseInt(dateParts1[0].trim());
      month1 = Integer.parseInt(dateParts1[1].trim());
      day1 = Integer.parseInt(dateParts1[2].trim());

      if (validateDate(day1, month1, year1)) {
        validDate1 = true;
      } else {
        errorMessage = "Invalid end date!";
        return;
        //view.displayError("Invalid date!");
      }
    } else {
      errorMessage = "Invalid end date format.";
      return;
      //view.displayError("Invalid date format.");
    }
    LocalDate endDate = LocalDate.parse(endDateArray);

    try {
      String result = model.gainOrLoseOverAPeriod(ticker, startDate, endDate, api);
      //view.print(result);
      successMessage = result;
    } catch (IllegalArgumentException e) {
      //view.displayError(e.getMessage());
      errorMessage = e.getMessage();
    }
  }

  public void xDayMovingAvg(String ticker, String x, String startDateArray) {
    errorMessage = null;
    successMessage = null;
    int value;
    try {
      value = Integer.parseInt(x);
      if (value <= 0) {
        //view.displayError("Share quantity must be a positive integer.");
        errorMessage = "X must be a positive integer.";
        return;
      }
    } catch (NumberFormatException e) {
      //view.displayError("Share quantity must be a positive integer.");
      errorMessage = "X must be a positive integer.";
      // Set to negative to continue the loop
      return;
    }

    StockData api = new StockData();
    boolean validDate = false;
    int day = 0;
    int month = 0;
    int year = 0;
    if (isValidDateFormat(startDateArray)) {
      String[] dateParts = startDateArray.split("-");
      year = Integer.parseInt(dateParts[0].trim());
      month = Integer.parseInt(dateParts[1].trim());
      day = Integer.parseInt(dateParts[2].trim());

      if (validateDate(day, month, year)) {
        validDate = true;
      } else {
        errorMessage = "Invalid date!";
        return;
        //view.displayError("Invalid date!");
      }
    } else {
      errorMessage = "Invalid date format.";
      return;
      //view.displayError("Invalid date format.");
    }
    LocalDate startDate = LocalDate.parse(startDateArray);
    //LocalDate startDate = LocalDate.of(startDateArray[2], startDateArray[1], startDateArray[0]);
    try {
      double result = model.xDayMovingAvg(ticker, startDate, value, api);
//      view.showXDayMovingAvg(result);
    } catch (IllegalArgumentException e) {
      errorMessage = e.getMessage();
      //view.displayError(e.getMessage());
    }
  }

  public void crossoverOverPeriod(String startDateArray, String endDateArray, String ticker) {
    StockData api = new StockData();
    boolean validDate = false;
    int day = 0;
    int month = 0;
    int year = 0;
    if (isValidDateFormat(startDateArray)) {
      String[] dateParts = startDateArray.split("-");
      year = Integer.parseInt(dateParts[0].trim());
      month = Integer.parseInt(dateParts[1].trim());
      day = Integer.parseInt(dateParts[2].trim());

      if (validateDate(day, month, year)) {
        validDate = true;
      } else {
        errorMessage = "Invalid start date!";
        return;
        //view.displayError("Invalid date!");
      }
    } else {
      errorMessage = "Invalid start date format.";
      return;
      //view.displayError("Invalid date format.");
    }
    LocalDate startDate = LocalDate.parse(startDateArray);

    boolean validDate1 = false;
    int day1 = 0;
    int month1 = 0;
    int year1 = 0;
    if (isValidDateFormat(endDateArray)) {
      String[] dateParts1 = startDateArray.split("-");
      year1 = Integer.parseInt(dateParts1[0].trim());
      month1 = Integer.parseInt(dateParts1[1].trim());
      day1 = Integer.parseInt(dateParts1[2].trim());

      if (validateDate(day1, month1, year1)) {
        validDate1 = true;
      } else {
        errorMessage = "Invalid end date!";
        return;
        //view.displayError("Invalid date!");
      }
    } else {
      errorMessage = "Invalid end date format.";
      return;
      //view.displayError("Invalid date format.");
    }
    LocalDate endDate = LocalDate.parse(endDateArray);

    try {
      TreeMap<String, String> result = model.crossoverOverPeriod(ticker, api, startDate, endDate);
//      view.printTreeMapEntries(result);
    } catch (IllegalArgumentException e) {
      //view.displayError(e.getMessage());
      errorMessage = e.getMessage();
    }
  }

  public void stockPerformance(String startDateArray, String endDateArray, String ticker) {
    StockData api = new StockData();
    boolean validDate = false;
    int day = 0;
    int month = 0;
    int year = 0;
    if (isValidDateFormat(startDateArray)) {
      String[] dateParts = startDateArray.split("-");
      year = Integer.parseInt(dateParts[0].trim());
      month = Integer.parseInt(dateParts[1].trim());
      day = Integer.parseInt(dateParts[2].trim());

      if (validateDate(day, month, year)) {
        validDate = true;
      } else {
        errorMessage = "Invalid start date!";
        return;
        //view.displayError("Invalid date!");
      }
    } else {
      errorMessage = "Invalid start date format.";
      return;
      //view.displayError("Invalid date format.");
    }
    LocalDate startDate = LocalDate.parse(startDateArray);

    boolean validDate1 = false;
    int day1 = 0;
    int month1 = 0;
    int year1 = 0;
    if (isValidDateFormat(endDateArray)) {
      String[] dateParts1 = startDateArray.split("-");
      year1 = Integer.parseInt(dateParts1[0].trim());
      month1 = Integer.parseInt(dateParts1[1].trim());
      day1 = Integer.parseInt(dateParts1[2].trim());

      if (validateDate(day1, month1, year1)) {
        validDate1 = true;
      } else {
        errorMessage = "Invalid end date!";
        return;
        //view.displayError("Invalid date!");
      }
    } else {
      errorMessage = "Invalid end date format.";
      return;
      //view.displayError("Invalid date format.");
    }
    LocalDate endDate = LocalDate.parse(endDateArray);
    TreeMap<String, Integer> result;
    try {
      result = model.stockPerformance(ticker, api, startDate, endDate);
      int scale = model.scaleForStockPerformance(ticker, api, startDate, endDate);
//      view.barGraph(scale, result, ticker, startDate + "", endDate + "");
    } catch (IllegalArgumentException e) {
      //view.displayError(e.getMessage());
      errorMessage = e.getMessage();
    }
  }

  @Override
  public void portfolioPerformance(String startDateArray, String endDateArray, int choice) {
    String portfolioName = "";
    Map<String, String> portfolioList = model.getListOfPortfoliosName();
    int index = 0;
    for (Map.Entry<String, String> entry : portfolioList.entrySet()) {
      if (index == choice) {
        portfolioName = entry.getKey();
        break;
      }
      index++;
    }
    if (Objects.equals(portfolioName, "")) {
      errorMessage = "No such portfolio found";
      return;
    }
    boolean validDate = false;
    int day = 0;
    int month = 0;
    int year = 0;
    if (isValidDateFormat(startDateArray)) {
      String[] dateParts = startDateArray.split("-");
      year = Integer.parseInt(dateParts[0].trim());
      month = Integer.parseInt(dateParts[1].trim());
      day = Integer.parseInt(dateParts[2].trim());

      if (validateDate(day, month, year)) {
        validDate = true;
      } else {
        errorMessage = "Invalid start date!";
        return;
        //view.displayError("Invalid date!");
      }
    } else {
      errorMessage = "Invalid start date format.";
      return;
      //view.displayError("Invalid date format.");
    }
    LocalDate startDate = LocalDate.parse(startDateArray);

    boolean validDate1 = false;
    int day1 = 0;
    int month1 = 0;
    int year1 = 0;
    if (isValidDateFormat(endDateArray)) {
      String[] dateParts1 = startDateArray.split("-");
      year1 = Integer.parseInt(dateParts1[0].trim());
      month1 = Integer.parseInt(dateParts1[1].trim());
      day1 = Integer.parseInt(dateParts1[2].trim());

      if (validateDate(day1, month1, year1)) {
        validDate1 = true;
      } else {
        errorMessage = "Invalid end date!";
        return;
        //view.displayError("Invalid date!");
      }
    } else {
      errorMessage = "Invalid end date format.";
      return;
      //view.displayError("Invalid date format.");
    }
    LocalDate endDate = LocalDate.parse(endDateArray);

    //portfolioPerformance(startDate,endDate,choice,portfolioName);
    TreeMap<String, Integer> result;
    try {
      result = model.portfolioPerformance(choice, startDate, endDate);
      int scale = model.scaleForPortfolioPerformance(choice, startDate, endDate);
//      view.barGraph(scale, result, portfolioName, startDate + "", endDate + "");
    } catch (RuntimeException e) {
      //view.displayError(e.getMessage());
      errorMessage = e.getMessage();
    }
  }


  @Override
  public void movingCrossoversOverPeriod(String startDateArray, String endDateArray,
                                         String x, String y, String ticker) {
    boolean validDate = false;
    int day = 0;
    int month = 0;
    int year = 0;
    if (isValidDateFormat(startDateArray)) {
      String[] dateParts = startDateArray.split("-");
      year = Integer.parseInt(dateParts[0].trim());
      month = Integer.parseInt(dateParts[1].trim());
      day = Integer.parseInt(dateParts[2].trim());

      if (validateDate(day, month, year)) {
        validDate = true;
      } else {
        errorMessage = "Invalid start date!";
        return;
        //view.displayError("Invalid date!");
      }
    } else {
      errorMessage = "Invalid start date format.";
      return;
      //view.displayError("Invalid date format.");
    }
    LocalDate startDate = LocalDate.parse(startDateArray);

    boolean validDate1 = false;
    int day1 = 0;
    int month1 = 0;
    int year1 = 0;
    if (isValidDateFormat(endDateArray)) {
      String[] dateParts1 = startDateArray.split("-");
      year1 = Integer.parseInt(dateParts1[0].trim());
      month1 = Integer.parseInt(dateParts1[1].trim());
      day1 = Integer.parseInt(dateParts1[2].trim());

      if (validateDate(day1, month1, year1)) {
        validDate1 = true;
      } else {
        errorMessage = "Invalid end date!";
        return;
        //view.displayError("Invalid date!");
      }
    } else {
      errorMessage = "Invalid end date format.";
      return;
      //view.displayError("Invalid date format.");
    }
    LocalDate endDate = LocalDate.parse(endDateArray);

    int value;
    try {
      value = Integer.parseInt(x);
      if (value <= 0) {
        //view.displayError("Share quantity must be a positive integer.");
        errorMessage = "X must be a positive integer.";
        return;
      }
    } catch (NumberFormatException e) {
      //view.displayError("Share quantity must be a positive integer.");
      errorMessage = "X must be a positive integer.";
      // Set to negative to continue the loop
      return;
    }

    int value1;
    try {
      value1 = Integer.parseInt(y);
      if (value1 <= 0) {
        //view.displayError("Share quantity must be a positive integer.");
        errorMessage = "Y must be a positive integer.";
        return;
      }
    } catch (NumberFormatException e) {
      //view.displayError("Share quantity must be a positive integer.");
      errorMessage = "Y must be a positive integer.";
      // Set to negative to continue the loop
      return;
    }
    StockData api = new StockData();
    TreeMap<String, String> result;
    try {
      result = model.movingCrossOver(ticker, api, startDate, endDate, value, value1);
//      view.printTreeMapEntries(result);
    } catch (IllegalArgumentException e) {
      //view.displayError(e.getMessage());
      errorMessage = e.getMessage();
    }



  }

  @Override
  public String getErrorMessage() {
    return this.errorMessage;
  }

}