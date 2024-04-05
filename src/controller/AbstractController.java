//package controller;
//
//import java.time.LocalDate;
//import java.util.List;
//import java.util.Map;
//import java.util.Objects;
//import java.util.Scanner;
//import java.util.TreeMap;
//import java.util.regex.Pattern;
//
//import model.InflexiblePortfolioImpl;
//import model.InvestmentManager;
//import view.IView;
//public class AbstractController implements Features.java{
//
//  private final IView view;
//
//  /**
//   * model object of portfolioDir that is required to call the portfolioDir methods in controller.
//   */
//  private final InvestmentManager model;
//
//  /**
//   * The Scanner object used for reading input.
//   */
//  //private final Scanner scan;
//
//
//  /**
//   * Constructs a StockControllerImpl object with the specified view, input stream.
//   * and portfolio directory.
//   *
//   * @param view         The view component responsible for displaying information to the user.
//   * @param portfolioDir The portfolio directory model component representing the model object.
//   */
//
//  public AbstractController(IView view, InvestmentManager portfolioDir) {
//    this.view = view;
//    this.model = portfolioDir;
//    //this.scan = new Scanner(in);
//  }
//
//  private String errorMessage;
//  private String successMessage;
//  public String getErrorMessage() {
//    return errorMessage;
//  }
//
//  public String getSuccessMessage() {
//    return successMessage;
//  }
//
//  @Override
//  public void createFlexiblePortfolio(String name) {
//    if (model.portfolioNameExists(name)) {
//      errorMessage = "Portfolio with this name already exists!";
//    }
//    model.createFlexiblePortfolio(name);
//    successMessage = "Portfolio created successfully.";
//  }
//
//
//  @Override
//  public void export(int input, String path) {
//    Persistence persistence = new Persistence();
//    try {
//      persistence.exportAsCSV(path, model.save(input));
//      successMessage = "Portfolio exported to " + path + " successfully.";
//
//    } catch (IllegalArgumentException e) {
//      errorMessage = e.getMessage();
//      //view.displayError(e.getMessage());
//    }
//  }
//
//  @Override
//  public void buyStock(String date, int quantity, String shareName, int choice) {
//    //if(quan)
//    errorMessage = null;
//    successMessage = null;
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
//    LocalDate buyDate = LocalDate.parse(date);
//
//    try {
//      model.buyStock(choice, shareName, quantity, buyDate, new StockData());
//      successMessage = quantity + " " + shareName + " bought successfully";
//      //view.print(quantity + " " + shareName + " bought successfully");
//    } catch (RuntimeException e) {
//      errorMessage = e.getMessage();
//      //view.displayError(e.getMessage());
//    }
//  }
//
//  private boolean isValidDateFormat(String date) {
//    String regex = "\\d{4}-\\d{2}-\\d{2}";
//    return Pattern.matches(regex, date);
//  }
//
//  private boolean validateDate(int day, int month, int year) {
//    if (month < 1 || month > 12) {
//      return false;
//    }
//
//    if (day < 1 || day > 31) {
//      return false;
//    }
//
//    if ((month == 4 || month == 6 || month == 9 || month == 11) && day > 30) {
//      return false;
//    }
//
//    if (month == 2) {
//      boolean isLeapYear = (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
//      return (!isLeapYear || day <= 29) && (isLeapYear || day <= 28);
//    }
//
//    return year >= 0 && year <= 9999;
//  }
//
//  @Override
//  public void sellStock(String date, int quantity, String shareName, int choice) {
//    errorMessage = null;
//    successMessage = null;
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
//    LocalDate sellDate = LocalDate.parse(date);
//
//    try {
//      model.sellStock(choice, shareName, quantity, sellDate, new StockData());
//      successMessage = quantity + " " + shareName + " sold successfully";
//      //view.print(quantity + " " + shareName + " sold successfully");
//    } catch (RuntimeException e) {
//      errorMessage = e.getMessage();
//      //view.displayError(e.getMessage());
//    }
//  }
//
////  @Override
////  public void loadInflexiblePortfolio(InflexiblePortfolioImpl.PortfolioBuilder newBuilder) {
////    try {
////      this.model.addPortfolio(newBuilder);
////    } catch (IllegalArgumentException e) {
////      view.displayError("Cannot create portfolio with no stocks!");
////    }
////    view.print("File loaded successfully");
////  }
//
//  public void loadFlexiblePortfolio(String name, List<String[]> lines) {
//    errorMessage = null;
//    successMessage = null;
//    try {
//      model.loadPortfolio(name, lines, new StockData());
//    } catch (IllegalArgumentException e) {
//      //view.displayError("The values provided in the file is invalid");
//      errorMessage = "The values provided in the file is invalid";
//      return;
//    }
//    successMessage = "File loaded successfully";
//    //view.print("File loaded successfully");
//  }
//
//  @Override
//  public void examineComposition(int input, String date) {
//    errorMessage = null;
//    successMessage = null;
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
//    LocalDate compositionDate = LocalDate.parse(date);
//
//    try {
//      view.showComposition(model.portfolioComposition(input, compositionDate));
//    } catch (IllegalArgumentException e) {
//      //view.displayError(e.getMessage());
//      errorMessage = e.getMessage();
//    }
//  }
//
//  @Override
//  public void getTotalValue(int choice, int[] date) {
//    try {
//      StockData api = new StockData();
//      double totalValue = model.portfolioValue(choice, date[0], date[1], date[2], api);
//      view.showTotalValue(totalValue);
//    } catch (IllegalArgumentException e) {
//      if (e.getMessage() != null) {
//        view.print("No price data found for " + e.getMessage() + " on the "
//                + "date: " + date[2] + "-" + date[1] + "-" + date[0]);
//      } else {
//        view.print("Invalid date!");
//      }
//    } catch (RuntimeException e) {
//      view.print("The data could not be fetched today, try again later!");
//    }
//  }
////    errorMessage = null;
////    successMessage = null;
////    boolean validDate = false;
////    int day = 0;
////    int month = 0;
////    int year = 0;
////    if (isValidDateFormat(date)) {
////      String[] dateParts = date.split("-");
////      year = Integer.parseInt(dateParts[0].trim());
////      month = Integer.parseInt(dateParts[1].trim());
////      day = Integer.parseInt(dateParts[2].trim());
////
////      if (validateDate(day, month, year)) {
////        validDate = true;
////      } else {
////        errorMessage = "Invalid date!";
////        return;
////        //view.displayError("Invalid date!");
////      }
////    } else {
////      errorMessage = "Invalid date format.";
////      return;
////      //view.displayError("Invalid date format.");
////    }
////    try {
////      StockData api = new StockData();
////      double totalValue = model.portfolioValue(choice, year, month, day, api);
////      view.showTotalValue(totalValue);
////    } catch (IllegalArgumentException e) {
////      if (e.getMessage() != null) {
////        //view.print("No price data found for " + e.getMessage() + " on the " + "date: " + day + "-" + month + "-" + year);
////        successMessage = "No price data found for " + e.getMessage() + " on the "
////                + "date: " + day + "-" + month + "-" + year;
////      } else {
////        //view.print("Invalid date!");
////        successMessage = "Invalid date!";
////      }
////    } catch (RuntimeException e) {
////      //view.print("The data could not be fetched today, try again later!");
////      successMessage = "The data could not be fetched today, try again later!";
////    }
//
//
//  @Override
//  public void getCostBasis(int choice, String date) {
//    errorMessage = null;
//    successMessage = null;
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
//    //LocalDate costBasisDate = LocalDate.of(date[2], date[1], date[0]);
//
//    try {
//      double costBasis = model.costBasis(choice, costBasisDate, new StockData());
//      successMessage = "The cost basis is: $" + costBasis;
//      //view.print("The cost basis is: $" + costBasis);
//    } catch (IllegalArgumentException e) {
//      errorMessage = e.getMessage();
//      //view.displayError(e.getMessage());
//    }
//  }
//
//  @Override
//  public void portfolioPerformance(int[] startDateArray, int[] endDateArray, int choice,
//                                   String portfolioName) {
//    LocalDate startDate = LocalDate.of(startDateArray[2], startDateArray[1], startDateArray[0]);
//    LocalDate endDate = LocalDate.of(endDateArray[2], endDateArray[1], endDateArray[0]);
//    String name = "";
//    Map<String, String> portfolioList = model.getListOfPortfoliosName();
//    int index = 0;
//    for (Map.Entry<String, String> entry : portfolioList.entrySet()) {
//      if (index == choice) {
//        name = entry.getKey();
//        break;
//      }
//      index++;
//    }
//    if (!Objects.equals(portfolioName, name)) {
//      errorMessage = "No such portfolio found";
//      //view.displayError("No such portfolio found");
//      return;
//    }
//
//    TreeMap<String, Integer> result;
//    try {
//      result = model.portfolioPerformance(choice, startDate, endDate);
//      int scale = model.scaleForPortfolioPerformance(choice, startDate, endDate);
//      view.barGraph(scale, result, portfolioName, startDate + "", endDate + "");
//    } catch (RuntimeException e) {
//      //view.displayError(e.getMessage());
//      errorMessage = e.getMessage();
//    }
//  }
//
//  @Override
//  public void stockPerformance(int[] startDateArray, int[] endDateArray, String ticker ) {
//    StockData api = new StockData();
//    LocalDate startDate = LocalDate.of(startDateArray[2], startDateArray[1], startDateArray[0]);
//    LocalDate endDate = LocalDate.of(endDateArray[2], endDateArray[1], endDateArray[0]);
//    TreeMap<String, Integer> result;
//    try {
//      result = model.stockPerformance(ticker, api, startDate, endDate);
//      int scale = model.scaleForStockPerformance(ticker, api, startDate, endDate);
//      view.barGraph(scale, result, ticker, startDate + "", endDate + "");
//    } catch (IllegalArgumentException e) {
//      view.displayError(e.getMessage());
//    }
//  }
//
//  @Override
//  public void gainOrLose(String date, String ticker) {
//    StockData api = new StockData();
//    errorMessage = null;
//    successMessage = null;
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
//
//    //LocalDate date = LocalDate.of(dateArray[2], dateArray[1], dateArray[0]);
//
//    try {
//      String result = model.gainOrLose(ticker, date1, api);
//      //view.print(result);
//      successMessage = result;
//    } catch (IllegalArgumentException e) {
//      //view.displayError(e.getMessage());
//      errorMessage = e.getMessage();
//    }
//  }
//
//  @Override
//  public void gainOrLoseOverPeriod(int[] startDateArray, int[] endDateArray,String ticker ) {
//    StockData api = new StockData();
//
//    LocalDate startDate = LocalDate.of(startDateArray[2], startDateArray[1], startDateArray[0]);
//    LocalDate endDate = LocalDate.of(endDateArray[2], endDateArray[1], endDateArray[0]);
//
//    try {
//      String result = model.gainOrLoseOverAPeriod(ticker, startDate, endDate, api);
//      view.print(result);
//    } catch (IllegalArgumentException e) {
//      view.displayError(e.getMessage());
//    }
//  }
//
//  @Override
//  public void xDayMovingAvg(String ticker, int x, String startDateArray) {
//    StockData api = new StockData();
//    errorMessage = null;
//    successMessage = null;
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
//    //LocalDate startDate = LocalDate.of(startDateArray[2], startDateArray[1], startDateArray[0]);
//    try {
//      double result = model.xDayMovingAvg(ticker, startDate, x, api);
//      view.showXDayMovingAvg(result);
//    } catch (IllegalArgumentException e) {
//      errorMessage = e.getMessage();
//      //view.displayError(e.getMessage());
//    }
//  }
//
//  @Override
//  public void crossoverOverPeriod(int[] startDateArray, int[] endDateArray, String ticker) {
//    StockData api = new StockData();
//    LocalDate startDate = LocalDate.of(startDateArray[2], startDateArray[1], startDateArray[0]);
//    LocalDate endDate = LocalDate.of(endDateArray[2], endDateArray[1], endDateArray[0]);
//    try {
//      TreeMap<String, String> result = model.crossoverOverPeriod(ticker, api, startDate, endDate);
//      view.printTreeMapEntries(result);
//    } catch (IllegalArgumentException e) {
//      view.displayError(e.getMessage());
//    }
//  }
//
//  @Override
//  public void movingCrossoversOverPeriod(int[] startDateArray, int[] endDateArray, int x,int y,
//                                         String ticker ) {
//    StockData api = new StockData();
//    LocalDate startDate = LocalDate.of(startDateArray[2], startDateArray[1], startDateArray[0]);
//    LocalDate endDate = LocalDate.of(endDateArray[2], endDateArray[1], endDateArray[0]);
//    TreeMap<String, String> result;
//    try {
//      result = model.movingCrossOver(ticker, api, startDate, endDate, x, y);
//      view.printTreeMapEntries(result);
//    } catch (IllegalArgumentException e) {
//      view.displayError(e.getMessage());
//    }
//  }
//
//
//
//
//
//}