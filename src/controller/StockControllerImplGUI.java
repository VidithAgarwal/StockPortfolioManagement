package controller;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Pattern;

import model.InvestmentManager;

import view.IViewGUI;


/**
 * class implements Features interface and serves as controller.
 * for managing investment manager related operations in graphical user interface (GUI) environment.
 */
public class StockControllerImplGUI implements Features  {

  /**
   * view component responsible for displaying information to user.
   */
  private IViewGUI view;

  /**
   * investment manager representing the model object.
   */
  private final InvestmentManager model;

  /**
   * Constructs a StockControllerImpl object with the specified view, input stream.
   * and portfolio directory.
   * @param view         The view component responsible for displaying information to the user.
   * @param portfolioDir The portfolio directory model component representing the model object.
   */
  public StockControllerImplGUI(IViewGUI view, InvestmentManager portfolioDir) {
    this.model = portfolioDir;
    this.view = view;
  }

  /**
   * Sets the view component for the controller and adds features to the view.
   * @param v the view component to set.
   */
  public void setView(IViewGUI v) {
    view = v;
    //provide view with all the callbacks
    view.addFeatures(this);
  }

  /**
   * string error message that contains the error message at different executions.
   */
  private String errorMessage;

  /**
   * string success message that contains success message when used in different methods.
   */
  private String successMessage;

  @Override
  public String getSuccessMessage() {
    return successMessage;
  }

  @Override
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

  @Override
  public void export(int input,String path) {
    // here in view we will provide drop down for selecting portfolio
    // THE DATA WILL BE DISPLAYED IN THAT ORDER OF INDEX OF PORTFOLIOS
    errorMessage = null;
    successMessage = null;
    Persistence persistence = new Persistence();
    try {
      persistence.exportAsCSV(path, model.save(input));
      successMessage = "Portfolio exported to " + path + " successfully.";

    } catch (IllegalArgumentException e) {
      errorMessage = e.getMessage();
      //view.displayError(e.getMessage());
    }
  }

  @Override
  public void buyStock(String date, String quantity, String shareName, int choice) {
    errorMessage = null;
    successMessage = null;
//    int shareQuantity;
//    try {
//      shareQuantity = Integer.parseInt(quantity);
//      if (shareQuantity <= 0) {
//        errorMessage = "Share quantity must be a positive integer.";
//        return;
//      }
//    } catch (NumberFormatException e) {
//      errorMessage = "Share quantity must be a positive integer.";
//      return;
//    }
    int shareQuantity = validatePositiveNumber(quantity,"Quantity must be positive integer.");
    if (shareQuantity ==  -1) {
      return;
    }
//    boolean validDate = false;
//    int day = 0;
//    int month = 0;
//    int year = 0;
//    if (isValidDateFormat(date)) {
//      String[] dateParts = date.split("-");
//      year = Integer.parseInt(dateParts[0].trim());
//      month = Integer.parseInt(dateParts[1].trim());
//      day = Integer.parseInt(dateParts[2].trim());
//
//      if (validateDate(day, month, year)) {
//        validDate = true;
//      } else {
//        errorMessage = "Invalid date!";
//        return;
//      }
//    } else {
//      errorMessage = "Invalid date format.";
//      return;
//    }
//    LocalDate buyDate = LocalDate.parse(date);
    LocalDate buyDate = validateDateMessage(date, "Invalid date!");
    if (buyDate == null) {
      return;
    }

    try {
      model.buyStock(choice, shareName, shareQuantity, buyDate, new StockData());
      successMessage = quantity + " " + shareName + " bought successfully";
      //view.print(quantity + " " + shareName + " bought successfully");
    } catch (RuntimeException e) {
      errorMessage = e.getMessage();
      //view.displayError(e.getMessage());
    }

  }

  /**
   * method to take in the date and pass the message to user based on date validation as used.
   * differently in different methods.
   * @param date the date that is passed to check for validity.
   * @param message the message based on which method is using this date validation.
   * @return the date if date entered is true or null otherwise.
   */
  private LocalDate validateDateMessage(String date, String message) {
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
        errorMessage = message;
        return null;
      }
    } else {
      errorMessage = message;
      return null;
    }
    LocalDate buyDate = LocalDate.parse(date);
    return buyDate;
  }

  /**
   * method to validate the date format.
   * @param date the date entered by the user which is checked if is in correct format.
   * @return true is pattern matches or false otherwise.
   */
  private boolean isValidDateFormat(String date) {
    String regex = "\\d{4}-\\d{2}-\\d{2}";
    return Pattern.matches(regex, date);
  }

  /**
   * method to validate the date entered by the user.
   * @param day the day of the year entered by the user.
   * @param month the month of the year selected by the user.
   * @param year the year selected by the user.
   * @return true if the date is valid or false otherwise.
   */
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

  @Override
  public void sellStock(String date, String quantity, String shareName, int choice) {
    errorMessage = null;
    successMessage = null;
//    int shareQuantity;
//    try {
//      shareQuantity = Integer.parseInt(quantity);
//      if (shareQuantity <= 0) {
//        //view.displayError("Share quantity must be a positive integer.");
//        errorMessage = "Share quantity must be a positive integer.";
//        return;
//      }
//    } catch (NumberFormatException e) {
//      //view.displayError("Share quantity must be a positive integer.");
//      errorMessage = "Share quantity must be a positive integer.";
//      // Set to negative to continue the loop
//      return;
//    }
    int shareQuantity = validatePositiveNumber(quantity,"Quantity must be positive integer.");
    if (shareQuantity ==  -1) {
      return;
    }
//    boolean validDate = false;
//    int day = 0;
//    int month = 0;
//    int year = 0;
//    if (isValidDateFormat(date)) {
//      String[] dateParts = date.split("-");
//      year = Integer.parseInt(dateParts[0].trim());
//      month = Integer.parseInt(dateParts[1].trim());
//      day = Integer.parseInt(dateParts[2].trim());
//
//      if (validateDate(day, month, year)) {
//        validDate = true;
//      } else {
//        errorMessage = "Invalid date!";
//        return;
//      }
//    } else {
//      errorMessage = "Invalid date format.";
//      return;
//    }
//    LocalDate sellDate = LocalDate.parse(date);
    LocalDate sellDate = validateDateMessage(date, "Invalid date!");
    if (sellDate == null) {
      return;
    }
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

  @Override
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
    //validateDateMessage(date);
    try {
      StockData api = new StockData();
      double totalValue = model.portfolioValue(choice, day, month, year, api);
      successMessage = "The total value of the portfolio on " + date + " is " + totalValue;
    } catch (IllegalArgumentException e) {
      if (e.getMessage() != null) {
        errorMessage = "No price data found for " + e.getMessage() + " on the "
                + "date: " + day + "-" + month + "-" + year;
      } else {
        //view.print("Invalid date!");
        errorMessage = "Invalid date!";
      }
    } catch (RuntimeException e) {
      //view.print("The data could not be fetched today, try again later!");
      errorMessage = "The data could not be fetched today, try again later!";
    }
    //getTotalValue(choice, date);
  }

  @Override
  public void getCostBasis(int choice, String date) {
    errorMessage = null;
    successMessage = null;
//    boolean validDate = false;
//    int day = 0;
//    int month = 0;
//    int year = 0;
//    if (isValidDateFormat(date)) {
//      String[] dateParts = date.split("-");
//      year = Integer.parseInt(dateParts[0].trim());
//      month = Integer.parseInt(dateParts[1].trim());
//      day = Integer.parseInt(dateParts[2].trim());
//
//      if (validateDate(day, month, year)) {
//        validDate = true;
//      } else {
//        errorMessage = "Invalid date!";
//        return;
//        //view.displayError("Invalid date!");
//      }
//    } else {
//      errorMessage = "Invalid date format.";
//      return;
//      //view.displayError("Invalid date format.");
//    }
//    LocalDate costBasisDate = LocalDate.parse(date);
    LocalDate costBasisDate = validateDateMessage(date, "Invalid date!");
    if (costBasisDate == null) {
      return;
    }
    //LocalDate costBasisDate = LocalDate.of(date[2], date[1], date[0]);

    try {
      double costBasis = model.costBasis(choice, costBasisDate, new StockData());
      successMessage = "The cost basis is: $" + costBasis;
    } catch (IllegalArgumentException e) {
      errorMessage = e.getMessage();
    }
  }


  @Override
  public String getErrorMessage() {
    return this.errorMessage;
  }

  @Override
  public void gainOrLose(String date, String ticker) {
    StockData api = new StockData();
    errorMessage = null;
    successMessage = null;
//    boolean validDate = false;
//    int day = 0;
//    int month = 0;
//    int year = 0;
//    if (isValidDateFormat(date)) {
//      String[] dateParts = date.split("-");
//      year = Integer.parseInt(dateParts[0].trim());
//      month = Integer.parseInt(dateParts[1].trim());
//      day = Integer.parseInt(dateParts[2].trim());
//
//      if (validateDate(day, month, year)) {
//        validDate = true;
//      } else {
//        errorMessage = "Invalid date!";
//        return;
//        //view.displayError("Invalid date!");
//      }
//    } else {
//      errorMessage = "Invalid date format.";
//      return;
//      //view.displayError("Invalid date format.");
//    }
//    LocalDate date1 = LocalDate.parse(date);
    LocalDate date1 = validateDateMessage(date, "Invalid date!");
    if (date1 == null) {
      return;
    }
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

  @Override
  public void gainOrLoseOverPeriod(String startDateArray, String endDateArray, String ticker) {
    StockData api = new StockData();
//    boolean validDate = false;
    errorMessage = null;
    successMessage = null;
//    int day = 0;
//    int month = 0;
//    int year = 0;
//    if (isValidDateFormat(startDateArray)) {
//      String[] dateParts = startDateArray.split("-");
//      year = Integer.parseInt(dateParts[0].trim());
//      month = Integer.parseInt(dateParts[1].trim());
//      day = Integer.parseInt(dateParts[2].trim());
//
//      if (validateDate(day, month, year)) {
//        validDate = true;
//      } else {
//        errorMessage = "Invalid start date!";
//        return;
//        //view.displayError("Invalid date!");
//      }
//    } else {
//      errorMessage = "Invalid start date format.";
//      return;
//      //view.displayError("Invalid date format.");
//    }
//    LocalDate startDate = LocalDate.parse(startDateArray);
    LocalDate startDate = validateDateMessage(startDateArray, "Invalid start date!");
    if (startDate == null) {
      return;
    }

//    boolean validDate1 = false;
//    int day1 = 0;
//    int month1 = 0;
//    int year1 = 0;
//    if (isValidDateFormat(endDateArray)) {
//      String[] dateParts1 = startDateArray.split("-");
//      year1 = Integer.parseInt(dateParts1[0].trim());
//      month1 = Integer.parseInt(dateParts1[1].trim());
//      day1 = Integer.parseInt(dateParts1[2].trim());
//
//      if (validateDate(day1, month1, year1)) {
//        validDate1 = true;
//      } else {
//        errorMessage = "Invalid end date!";
//        return;
//        //view.displayError("Invalid date!");
//      }
//    } else {
//      errorMessage = "Invalid end date format.";
//      return;
//      //view.displayError("Invalid date format.");
//    }
//    LocalDate endDate = LocalDate.parse(endDateArray);
    LocalDate endDate = validateDateMessage(endDateArray, "Invalid end date!");
    if (endDate == null) {
      return;
    }

    try {
      String result = model.gainOrLoseOverAPeriod(ticker, startDate, endDate, api);
      //view.print(result);
      successMessage = result;
    } catch (IllegalArgumentException e) {
      //view.displayError(e.getMessage());
      errorMessage = e.getMessage();
    }
  }

  /**
   * method to check if the user has entered valid positive number. 
   * @param quantity the parameter to be checked if it is positive integer or not.
   * @param message the string message that is passed based on the method using this validation.
   * @return the number if positive int or else returns -1.
   */
  private int validatePositiveNumber(String quantity, String message) {
    int value;
    try {
      value = Integer.parseInt(quantity);
      if (value <= 0) {
        errorMessage = message;
        return -1;
      }
    } catch (NumberFormatException e) {
      errorMessage = message;
      return -1;
    }
    return value;
  }


  @Override
  public void xDayMovingAvg(String ticker, String x, String startDateArray) {
    errorMessage = null;
    successMessage = null;
    int value = validatePositiveNumber(x,"X must be a positive integer." );
//    try {
//      value = Integer.parseInt(x);
//      if (value <= 0) {
//        //view.displayError("Share quantity must be a positive integer.");
//        errorMessage = "X must be a positive integer.";
//        return;
//      }
//    } catch (NumberFormatException e) {
//      //view.displayError("Share quantity must be a positive integer.");
//      errorMessage = "X must be a positive integer.";
//      // Set to negative to continue the loop
//      return;
//    }
    if (value == -1) {
      return;
    }
    StockData api = new StockData();
//    boolean validDate = false;
//    int day = 0;
//    int month = 0;
//    int year = 0;
//    if (isValidDateFormat(startDateArray)) {
//      String[] dateParts = startDateArray.split("-");
//      year = Integer.parseInt(dateParts[0].trim());
//      month = Integer.parseInt(dateParts[1].trim());
//      day = Integer.parseInt(dateParts[2].trim());
//
//      if (validateDate(day, month, year)) {
//        validDate = true;
//      } else {
//        errorMessage = "Invalid date!";
//        return;
//        //view.displayError("Invalid date!");
//      }
//    } else {
//      errorMessage = "Invalid date format.";
//      return;
//      //view.displayError("Invalid date format.");
//    }
//    LocalDate startDate = LocalDate.parse(startDateArray);
    LocalDate startDate = validateDateMessage(startDateArray, "Invalid date!");
    if (startDate == null) {
      return;
    }
    //LocalDate startDate = LocalDate.of(startDateArray[2], startDateArray[1], startDateArray[0]);
    try {
      double result = model.xDayMovingAvg(ticker, startDate, value, api);
      successMessage = "The X-day moving average is: $" + result;
//      view.showXDayMovingAvg(result);
    } catch (IllegalArgumentException e) {
      errorMessage = e.getMessage();
      //view.displayError(e.getMessage());
    }
  }

  @Override
  public TreeMap<String, String> crossoverOverPeriod(String startDateArray, String endDateArray, String ticker) {
    errorMessage = null;
    successMessage = null;
    StockData api = new StockData();
//    boolean validDate = false;
//    int day = 0;
//    int month = 0;
//    int year = 0;
//    if (isValidDateFormat(startDateArray)) {
//      String[] dateParts = startDateArray.split("-");
//      year = Integer.parseInt(dateParts[0].trim());
//      month = Integer.parseInt(dateParts[1].trim());
//      day = Integer.parseInt(dateParts[2].trim());
//
//      if (validateDate(day, month, year)) {
//        validDate = true;
//      } else {
//        errorMessage = "Invalid start date!";
//        return null;
//        //view.displayError("Invalid date!");
//      }
//    } else {
//      errorMessage = "Invalid start date format.";
//      return null;
//      //view.displayError("Invalid date format.");
//    }
//    LocalDate startDate = LocalDate.parse(startDateArray);
    LocalDate startDate = validateDateMessage(startDateArray, "Invalid start date!");
    if (startDate == null) {
      return null;
    }

//    boolean validDate1 = false;
//    int day1 = 0;
//    int month1 = 0;
//    int year1 = 0;
//    if (isValidDateFormat(endDateArray)) {
//      String[] dateParts1 = startDateArray.split("-");
//      year1 = Integer.parseInt(dateParts1[0].trim());
//      month1 = Integer.parseInt(dateParts1[1].trim());
//      day1 = Integer.parseInt(dateParts1[2].trim());
//
//      if (validateDate(day1, month1, year1)) {
//        validDate1 = true;
//      } else {
//        errorMessage = "Invalid end date!";
//        return null;
//        //view.displayError("Invalid date!");
//      }
//    } else {
//      errorMessage = "Invalid end date format.";
//      return null;
//      //view.displayError("Invalid date format.");
//    }
//    LocalDate endDate = LocalDate.parse(endDateArray);
    LocalDate endDate = validateDateMessage(endDateArray, "Invalid end date!");
    if (endDate == null) {
      return null;
    }

    try {
      //view.printTreeMapEntries(result);
      return model.crossoverOverPeriod(ticker, api, startDate, endDate);

    } catch (IllegalArgumentException e) {
      //view.displayError(e.getMessage());
      errorMessage = e.getMessage();
      return null;
    }
  }
  @Override
  public TreeMap<String, String> movingCrossoversOverPeriod(String startDateArray, String endDateArray,
                                                            String x, String y, String ticker) {
    errorMessage = null;
    successMessage = null;
//    boolean validDate = false;
//    int day = 0;
//    int month = 0;
//    int year = 0;
//    if (isValidDateFormat(startDateArray)) {
//      String[] dateParts = startDateArray.split("-");
//      year = Integer.parseInt(dateParts[0].trim());
//      month = Integer.parseInt(dateParts[1].trim());
//      day = Integer.parseInt(dateParts[2].trim());
//
//      if (validateDate(day, month, year)) {
//        validDate = true;
//      } else {
//        errorMessage = "Invalid start date!";
//        return null;
//        //view.displayError("Invalid date!");
//      }
//    } else {
//      errorMessage = "Invalid start date format.";
//      return null;
//      //view.displayError("Invalid date format.");
//    }
//    LocalDate startDate = LocalDate.parse(startDateArray);
    LocalDate startDate = validateDateMessage(startDateArray, "Invalid start date!");
    if (startDate == null) {
      return null;
    }


//    boolean validDate1 = false;
//    int day1 = 0;
//    int month1 = 0;
//    int year1 = 0;
//    if (isValidDateFormat(endDateArray)) {
//      String[] dateParts1 = startDateArray.split("-");
//      year1 = Integer.parseInt(dateParts1[0].trim());
//      month1 = Integer.parseInt(dateParts1[1].trim());
//      day1 = Integer.parseInt(dateParts1[2].trim());
//
//      if (validateDate(day1, month1, year1)) {
//        validDate1 = true;
//      } else {
//        errorMessage = "Invalid end date!";
//        return null;
//        //view.displayError("Invalid date!");
//      }
//    } else {
//      errorMessage = "Invalid end date format.";
//      return null;
//      //view.displayError("Invalid date format.");
//    }
//    LocalDate endDate = LocalDate.parse(endDateArray);
    LocalDate endDate = validateDateMessage(endDateArray, "Invalid end date!");
    if (endDate == null) {
      return null;
    }

//    int value;
//    try {
//      value = Integer.parseInt(x);
//      if (value <= 0) {
//        errorMessage = "X must be a positive integer.";
//        return null;
//      }
//    } catch (NumberFormatException e) {
//      errorMessage = "X must be a positive integer.";
//      // Set to negative to continue the loop
//      return null;
//    }
    int value = validatePositiveNumber(x,"X must be a positive integer." );
    if (value ==  -1) {
      return null;
    }

//    int value1;
//    try {
//      value1 = Integer.parseInt(y);
//      if (value1 <= 0) {
//        errorMessage = "Y must be a positive integer.";
//        return null;
//      }
//    } catch (NumberFormatException e) {
//      errorMessage = "Y must be a positive integer.";
//      // Set to negative to continue the loop
//      return null;
//    }
    int value1 = validatePositiveNumber(y,"Y must be a positive integer.");
    if (value1 ==  -1) {
      return null;
    }

      StockData api = new StockData();
    TreeMap<String, String> result;
    try {
      return model.movingCrossOver(ticker, api, startDate, endDate, value, value1);

    } catch (IllegalArgumentException e) {
      errorMessage = e.getMessage();
      return null;
    }
  }


  @Override
  public ArrayList<String> getPortfolioNames() {
    Map<String, String > mapOfPortfolios = model.getListOfPortfoliosName();
    ArrayList<String> list = new ArrayList<>();
    for (Map.Entry<String, String> entry: mapOfPortfolios.entrySet()) {
      if (entry.getValue().equalsIgnoreCase("Flexible")) {
        list.add(entry.getKey());
      }
    }
    return list;
  }

  @Override
  public void loadPortfolio(String name, String portfolioPath) {
    successMessage = null;
    errorMessage = null;
    List<String[]> output = inputPath(portfolioPath);
    if (errorMessage != null && output.isEmpty() ) {
      return;
    }
    try {
      model.loadPortfolio(name, output, new StockData());
    } catch (IllegalArgumentException e) {
      //view.displayError("The values provided in the file is invalid");
      errorMessage = "The values provided in the file is invalid";
      return;
    }
    successMessage = "File loaded successfully";
    //view.print("File loaded successfully");
  }


  /**
   * method to check that path entered by user is valid and take path as input.
   * @param path the path entered by the user.
   * @return list of strings of the contents in the file. 
   */
  private List<String[]> inputPath(String path) {
    errorMessage = null;
    Persistence persistence = new Persistence();
    try {
      return persistence.loadFromCSV(path);
    } catch (IllegalArgumentException e) {
      //view.displayError(e.getMessage());
      errorMessage = e.getMessage();
      return new ArrayList<>();
    }
  }

  @Override
  public Map<String, Double> examineComposition(int input, String date) {
    errorMessage = null;
    successMessage = null;
//    boolean validDate = false;
//    int day = 0;
//    int month = 0;
//    int year = 0;
//    if (isValidDateFormat(date)) {
//      String[] dateParts = date.split("-");
//      year = Integer.parseInt(dateParts[0].trim());
//      month = Integer.parseInt(dateParts[1].trim());
//      day = Integer.parseInt(dateParts[2].trim());
//
//      if (validateDate(day, month, year)) {
//        validDate = true;
//      } else {
//        errorMessage = "Invalid date!";
//        return null;
//        //view.displayError("Invalid date!");
//      }
//    } else {
//      errorMessage = "Invalid date format.";
//      return null;
//      //view.displayError("Invalid date format.");
//    }
//    LocalDate compositionDate = LocalDate.parse(date);
    LocalDate compositionDate = validateDateMessage(date, "Invalid date!");
    if (compositionDate == null) {
      return null;
    }

    try {
      return model.portfolioComposition(input, compositionDate);
    } catch (IllegalArgumentException e) {
      //view.displayError(e.getMessage());
      errorMessage = e.getMessage();
      return null;
    }
    //examineComposition(choice,date);
  }


  @Override
  public void createPortfolioWithStrategy(String portfolioName, String startDate, String endDate,
                                          int frequency,
                                          Double amount, Map<String, Double> shareDetails) {
    this.errorMessage = null;
    this.successMessage = null;

//    int day = 0;
//    int month = 0;
//    int year = 0;
//    if (isValidDateFormat(startDate)) {
//      String[] dateParts = startDate.split("-");
//      year = Integer.parseInt(dateParts[0].trim());
//      month = Integer.parseInt(dateParts[1].trim());
//      day = Integer.parseInt(dateParts[2].trim());
//
//      if (!validateDate(day, month, year)) {
//        errorMessage = "Invalid start date!";
//        return;
//      }
//    } else {
//      errorMessage = "Invalid start date format.";
//      return;
//    }
//    LocalDate strategyStartDate = LocalDate.parse(startDate);
    LocalDate strategyStartDate = validateDateMessage(startDate, "Invalid start date!");
    if (strategyStartDate == null) {
      return;
    }
//    if (isValidDateFormat(endDate)) {
//      String[] dateParts = endDate.split("-");
//      year = Integer.parseInt(dateParts[0].trim());
//      month = Integer.parseInt(dateParts[1].trim());
//      day = Integer.parseInt(dateParts[2].trim());
//
//      if (!validateDate(day, month, year)) {
//        errorMessage = "Invalid end date!";
//        return;
//      }
//    } else {
//      errorMessage = "Invalid end date format.";
//      return;
//    }
//    LocalDate strategyEndDate = LocalDate.parse(endDate);
    LocalDate strategyEndDate = validateDateMessage(endDate, "Invalid end date!");
    if (strategyEndDate == null) {
      return;
    }

    int sum = 0;

    for (Map.Entry<String, Double> entry: shareDetails.entrySet()) {
      if (entry.getValue() < 0) {
        errorMessage = "The weight cannot be negative";
        return;
      }

      if (entry.getValue() > 100) {
        errorMessage = "The weight cannot be greater than 100%";
        return;
      }
      sum += entry.getValue();
    }

    if (sum != 100) {
      errorMessage = "The combined sum of weights must be 100%";
    }

    if (frequency <= 0) {
      errorMessage = "Frequency of investment must be a positive integer.";
      return;
    }


    if (amount <= 0) {
      errorMessage = "Amount must be a positive integer.";
      return;
    }

    try {
      model.createDollarCostAverageStrategy(model.getSize() - 1, shareDetails, strategyStartDate,
              strategyEndDate, frequency, amount, new StockData());
      this.successMessage = "Portfolio with the provided strategy created successfully";
    } catch (RuntimeException e) {
      this.errorMessage = e.getMessage();
    }
  }

  @Override
  public void investWithDCAStrategy(int input, String date,
                                    Double amount, Map<String, Double> shareDetails) {
    this.errorMessage = null;
    this.successMessage = null;

//    boolean validDate = false;
//    int day = 0;
//    int month = 0;
//    int year = 0;
//    if (isValidDateFormat(date)) {
//      String[] dateParts = date.split("-");
//      year = Integer.parseInt(dateParts[0].trim());
//      month = Integer.parseInt(dateParts[1].trim());
//      day = Integer.parseInt(dateParts[2].trim());
//
//      if (!validateDate(day, month, year)) {
//        errorMessage = "Invalid date!";
//        return;
//      }
//    } else {
//      errorMessage = "Invalid date format.";
//      return;
//    }
//    LocalDate investmentDate = LocalDate.parse(date);

    LocalDate investmentDate = validateDateMessage(date, "Invalid date!");
    if (investmentDate == null) {
      return;
    }

//    if (input < 0 || input >= model.getSize()) {
//      errorMessage = "Invalid portfolio choice";
//      return;
//    }


    if (amount <= 0) {
      errorMessage = "Amount must be a positive integer.";
      return;
    }

    int sum = 0;

    for (Map.Entry<String, Double> entry: shareDetails.entrySet()) {
      if (entry.getValue() < 0) {
        errorMessage = "The weight cannot be negative";
        return;
      }

      if (entry.getValue() > 100) {
        errorMessage = "The weight cannot be greater than 100%";
        return;
      }
      sum += entry.getValue();
    }

    if (sum != 100) {
      errorMessage = "The combined sum of weights must be 100%";
    }

    try {
      model.investWithDCAStrategy(model.getSize() - 1, shareDetails, investmentDate,
              amount, new StockData());
      this.successMessage = amount + " invested in portfolio successfully";
    } catch (RuntimeException e) {
      this.errorMessage = e.getMessage();
    }
  }

}
