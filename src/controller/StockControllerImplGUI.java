package controller;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
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

  private void validateChoice(int input) {
    if (input < 0 || input >= model.getSize()) {
      errorMessage = "Invalid portfolio choice";
    }
  }


  @Override
  public void export(int input,String path) {
    errorMessage = null;
    successMessage = null;
    validateChoice(input);
    if (Objects.equals(errorMessage, "Invalid portfolio choice")) {
      return;
    }
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
    validateChoice(choice);
    if (Objects.equals(errorMessage, "Invalid portfolio choice")) {
      return;
    }

    int shareQuantity = validatePositiveNumber(quantity,"Quantity must be positive integer.");
    if (shareQuantity ==  -1) {
      return;
    }

    LocalDate buyDate = validateDateMessage(date, "Invalid date!");
    if (buyDate == null) {
      return;
    }

    try {
      model.buyStock(choice, shareName, shareQuantity, buyDate, new StockData());
      successMessage = quantity + " " + shareName + " bought successfully";
    } catch (RuntimeException e) {
      errorMessage = e.getMessage();
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

    int day = 0;
    int month = 0;
    int year = 0;
    if (isValidDateFormat(date)) {
      String[] dateParts = date.split("-");
      year = Integer.parseInt(dateParts[0].trim());
      month = Integer.parseInt(dateParts[1].trim());
      day = Integer.parseInt(dateParts[2].trim());

      if (validateDate(day, month, year)) {
        errorMessage = null;
      } else {
        errorMessage = message;
        return null;
      }
    } else {
      errorMessage = message;
      return null;
    }
    LocalDate date1 = LocalDate.parse(date);
    return date1;
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
    validateChoice(choice);
    if (Objects.equals(errorMessage, "Invalid portfolio choice")) {
      return;
    }

    int shareQuantity = validatePositiveNumber(quantity,"Quantity must be positive integer.");
    if (shareQuantity ==  -1) {
      return;
    }
    LocalDate sellDate = validateDateMessage(date, "Invalid date!");
    if (sellDate == null) {
      return;
    }

    try {
      model.sellStock(choice, shareName, shareQuantity, sellDate, new StockData());
      successMessage = quantity + " " + shareName + " sold successfully";
    } catch (RuntimeException e) {
      errorMessage = e.getMessage();
    }
  }

  @Override
  public void getTotalValue(int choice, String date) {
    errorMessage = null;
    successMessage = null;
    validateChoice(choice);
    if (Objects.equals(errorMessage, "Invalid portfolio choice")) {
      return;
    }

    int day = 0;
    int month = 0;
    int year = 0;
    if (isValidDateFormat(date)) {
      String[] dateParts = date.split("-");
      year = Integer.parseInt(dateParts[0].trim());
      month = Integer.parseInt(dateParts[1].trim());
      day = Integer.parseInt(dateParts[2].trim());

      if (validateDate(day, month, year)) {
        errorMessage = null;
      } else {
        errorMessage = "Invalid date!";
        return;
        //view.displayError("Invalid date!");
      }
    } else {
      errorMessage = "Invalid date format.";
      return;
    }
    try {
      StockData api = new StockData();
      double totalValue = model.portfolioValue(choice, day, month, year, api);
      successMessage = "The total value of the portfolio on " + date + " is " + totalValue;
    } catch (IllegalArgumentException e) {
      if (e.getMessage() != null) {
        errorMessage = "No price data found for " + e.getMessage() + " on the "
                + "date: " + day + "-" + month + "-" + year;
      } else {
        errorMessage = "Invalid date!";
      }
    } catch (RuntimeException e) {
      errorMessage = "The data could not be fetched today, try again later!";
    }
  }

  @Override
  public void getCostBasis(int choice, String date) {
    errorMessage = null;
    successMessage = null;

    validateChoice(choice);
    if (Objects.equals(errorMessage, "Invalid portfolio choice")) {
      return;
    }

    LocalDate costBasisDate = validateDateMessage(date, "Invalid date!");
    if (costBasisDate == null) {
      return;
    }

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

    LocalDate date1 = validateDateMessage(date, "Invalid date!");
    if (date1 == null) {
      return;
    }

    try {
      String result = model.gainOrLose(ticker, date1, api);
      successMessage = result;
    } catch (IllegalArgumentException e) {
      errorMessage = e.getMessage();
    }
  }

  @Override
  public void gainOrLoseOverPeriod(String startDateArray, String endDateArray, String ticker) {
    StockData api = new StockData();
    errorMessage = null;
    successMessage = null;

    LocalDate startDate = validateDateMessage(startDateArray, "Invalid start date!");
    if (startDate == null) {
      return;
    }


    LocalDate endDate = validateDateMessage(endDateArray, "Invalid end date!");
    if (endDate == null) {
      return;
    }

    try {
      String result = model.gainOrLoseOverAPeriod(ticker, startDate, endDate, api);
      successMessage = result;
    } catch (IllegalArgumentException e) {
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

    if (value == -1) {
      return;
    }
    StockData api = new StockData();

    LocalDate startDate = validateDateMessage(startDateArray, "Invalid date!");
    if (startDate == null) {
      return;
    }
    try {
      double result = model.xDayMovingAvg(ticker, startDate, value, api);
      successMessage = "The X-day moving average is: $" + result;
    } catch (IllegalArgumentException e) {
      errorMessage = e.getMessage();
    }
  }

  @Override
  public TreeMap<String, String> crossoverOverPeriod(String startDateArray, String endDateArray,
                                                     String ticker) {
    errorMessage = null;
    successMessage = null;
    StockData api = new StockData();

    LocalDate startDate = validateDateMessage(startDateArray, "Invalid start date!");
    if (startDate == null) {
      return null;
    }


    LocalDate endDate = validateDateMessage(endDateArray, "Invalid end date!");
    if (endDate == null) {
      return null;
    }

    try {
      return model.crossoverOverPeriod(ticker, api, startDate, endDate);

    } catch (IllegalArgumentException e) {
      errorMessage = e.getMessage();
      return null;
    }
  }


  @Override
  public TreeMap<String, String> movingCrossoversOverPeriod(String startDateArray,
                                                            String endDateArray,
                                                            String x, String y, String ticker) {
    errorMessage = null;
    successMessage = null;

    LocalDate startDate = validateDateMessage(startDateArray, "Invalid start date!");
    if (startDate == null) {
      return null;
    }



    LocalDate endDate = validateDateMessage(endDateArray, "Invalid end date!");
    if (endDate == null) {
      return null;
    }

    int value = validatePositiveNumber(x,"X must be a positive integer." );
    if (value ==  -1) {
      return null;
    }

    int value1 = validatePositiveNumber(y,"Y must be a positive integer.");
    if (value1 ==  -1) {
      return null;
    }

    StockData api = new StockData();
    try {
      return model.movingCrossOver(ticker, api, startDate, endDate, value, value1);

    } catch (IllegalArgumentException e) {
      errorMessage = e.getMessage();
      return null;
    }
  }


  @Override
  public ArrayList<String> getPortfolioNames() {
    Map<String, String> mapOfPortfolios = model.getListOfPortfoliosName();
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
      errorMessage = "The values provided in the file is invalid";
      return;
    }
    successMessage = "File loaded successfully";
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
      errorMessage = e.getMessage();
      return new ArrayList<>();
    }
  }

  @Override
  public Map<String, Double> examineComposition(int input, String date) {
    errorMessage = null;
    successMessage = null;
    validateChoice(input);
    if (Objects.equals(errorMessage, "Invalid portfolio choice")) {
      return null;
    }

    LocalDate compositionDate = validateDateMessage(date, "Invalid date!");
    if (compositionDate == null) {
      return null;
    }

    try {
      return model.portfolioComposition(input, compositionDate);
    } catch (IllegalArgumentException e) {
      errorMessage = e.getMessage();
      return null;
    }
  }


  @Override
  public void createPortfolioWithStrategy(String portfolioName, String startDate, String endDate,
                                          int frequency,
                                          Double amount, Map<String, Double> shareDetails) {
    this.errorMessage = null;
    this.successMessage = null;

    LocalDate strategyStartDate = validateDateMessage(startDate, "Invalid start date!");
    if (strategyStartDate == null) {
      return;
    }
    LocalDate strategyEndDate = null;
    if (endDate != null) {
      strategyEndDate = validateDateMessage(endDate, "Invalid end date!");
      if (strategyEndDate == null) {
        return;
      }
    }

    if (validateBuyingList(shareDetails)) return;

    if (frequency <= 0) {
      errorMessage = "Frequency of investment must be a positive integer.";
      return;
    }


    if (amount <= 0) {
      errorMessage = "Amount must be a positive integer.";
      return;
    }

    try {
      model.createDollarCostAverageStrategy(model.getSize() - 1, shareDetails,
              strategyStartDate,
              strategyEndDate, frequency, amount, new StockData());
      this.successMessage = "Portfolio with the provided strategy created successfully";
    } catch (RuntimeException e) {
      this.errorMessage = e.getMessage();
    }
  }

  private boolean validateBuyingList(Map<String, Double> shareDetails) {
    int sum = 0;

    for (Map.Entry<String, Double> entry: shareDetails.entrySet()) {
      if (entry.getValue() < 0) {
        errorMessage = "The weight cannot be negative";
        return true;
      }

      if (entry.getValue() > 100) {
        errorMessage = "The weight cannot be greater than 100%";
        return true;
      }
      sum += entry.getValue();
    }

    if (sum != 100) {
      errorMessage = "The combined sum of weights must be 100%";
    }
    return false;
  }

  @Override
  public void investWithDCAStrategy(int input, String date,
                                    Double amount, Map<String, Double> shareDetails) {
    this.errorMessage = null;
    this.successMessage = null;
    validateChoice(input);
    if (Objects.equals(errorMessage, "Invalid portfolio choice")) {
      return;
    }

    LocalDate investmentDate = validateDateMessage(date, "Invalid date!");
    if (investmentDate == null) {
      return;
    }

    if (input < 0 || input >= model.getSize()) {
      errorMessage = "Invalid portfolio choice";
      return;
    }


    if (amount <= 0) {
      errorMessage = "Amount must be a positive integer.";
      return;
    }

    if (validateBuyingList(shareDetails)) return;

    try {
      model.investWithDCAStrategy(model.getSize() - 1, shareDetails, investmentDate,
              amount, new StockData());
      this.successMessage = amount + " invested in portfolio successfully";
    } catch (RuntimeException e) {
      this.errorMessage = e.getMessage();
    }
  }

}
