package controller;

import java.io.File;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.regex.Pattern;

import model.InflexiblePortfolioImpl;
import model.InvestmentManager;
import view.IView;

/**
 * Implementation of the StockController interface providing methods to control
 * the flow of the application in the MVC architecture.
 * This class has methods to delegate the input from user and give the input to model.
 * and get the output from model and print it using the view methods.
 */
public class StockControllerImpl implements StockController {


  /**
   * view object that is required to call the view method in controller.
   */
  private final IView view;

  /**
   * model object of portfolioDir that is required to call the portfolioDir methods in controller.
   */
  private final InvestmentManager model;

  /**
   * The Scanner object used for reading input.
   */
  private final Scanner scan;


  /**
   * Constructs a StockControllerImpl object with the specified view, input stream.
   * and portfolio directory.
   *
   * @param view         The view component responsible for displaying information to the user.
   * @param in           The input stream used to get the user input.
   * @param portfolioDir The portfolio directory model component representing the model object.
   */

  public StockControllerImpl(IView view, Readable in, InvestmentManager portfolioDir) {
    this.view = view;
    this.model = portfolioDir;
    this.scan = new Scanner(in);
  }


  /**
   * Initiates the main functionality of the application, handling the flow of the user interface.
   * It prompts the user with the appropriate menu based on the state of the portfolio directory.
   * If the portfolio directory is empty, it displays the primary menu.
   * otherwise, it displays the secondary menu.
   * The method continues looping until the user chooses to exit the application.
   */
  public void execute() {
    boolean exit = false;
    while (!exit) {
      if (model.isEmpty()) {
        exit = startMenu();
      } else {
        exit = secondMenu();
      }
    }
  }

  /**
   * this method deletes all folders in a specified directory,
   * except for the folder named with the current date's name. This can be useful for
   * cleanup operations where only the most recent data should be preserved.
   *
   * @param directoryPath Path to the directory where folders will be deleted.
   */
  private void deleteCSVFilesFromStocklist(String directoryPath) {
    File directory = new File(directoryPath);

    LocalDate currentDate = LocalDate.now();

    File[] files = directory.listFiles();
    if (files != null) {
      for (File file : files) {
        // Check if it's a directory and not the current date folder
        if (file.isDirectory() && !file.getName().equals("" + currentDate)) {
          deleteCSVFiles(file);
        }
      }
    }
  }

  /**
   * Recursively deletes session CSV files from the directory.
   *
   * @param directory The directory from which the csv files are to be deleted.
   */
  private void deleteCSVFiles(File directory) {
    File[] allContents = directory.listFiles();
    if (allContents != null) {
      for (File file : allContents) {
        deleteCSVFiles(file);
      }
      try {
        if (!directory.delete()) {
          view.displayError("Deletion failed");
        }
      } catch (Exception e) {
        view.displayError("Exception occurred: " + e.getMessage());
      }
    }
  }

  /**
   * Displays the secondary menu and handles user choices accordingly.
   * Based on the user input the specific methods are called.
   *
   * @return true if the user chooses to exit the application, false for other choices.
   */
  private boolean secondMenu() {
    int choice;

    view.showSecondaryMenu();
    boolean exit = false;
    boolean showSubMenu = true;
    choice = inputPositiveInteger("Enter your choice: ");
    switch (choice) {
      case 1:
        while (showSubMenu) {
          showSubMenu = showCreateMenu();
        }
        break;
      case 2:
        boolean showLoadMenu = true;
        while (showLoadMenu) {
          showLoadMenu = showLoadMenu();
        }
        break;
      case 3:
        if (!model.isEmpty()) {
          examineComposition();
        } else {
          this.view.displayError("Enter a valid choice, this option doesn't exists.");
        }
        break;
      case 4:
        if (!model.isEmpty()) {
          view.print("Get total value of a portfolio for certain date");
          getTotalValue();
        } else {
          this.view.displayError("Enter a valid choice, this option doesn't exists.");
        }
        break;
      case 5:
        if (!model.isEmpty()) {
          export();
        }
        break;
      case 6:
        if (!model.isEmpty()) {
          buyStock();
        }
        break;
      case 7:
        if (!model.isEmpty()) {
          sellStock();
        }
        break;
      case 8:
        if (!model.isEmpty()) {
          getCostBasis();
        }
        break;
      case 9:
        boolean showStockMenu = true;
        while (showStockMenu) {
          showStockMenu = stockMenu();
        }
        break;
      case 10:
        if (!model.isEmpty()) {
          //view.print("Get total value of a portfolio for certain date");
          portfolioPerformance();
        }
        break;
      case 11:
        exit = true;
        break;
      default:
        this.view.displayError("Enter a valid choice, this option doesn't exists.");
        break;
    }
    if (exit) {
      String currentDirectory = System.getProperty("user.dir") + "/Data";
      //deleteCSVFilesFromStocklist(currentDirectory);
    }
    return exit;
  }


  /**
   * Starts the main menu of the application.
   * this method is used when there is no portfolio created.
   * and calls the other methods based on choice entered.
   *
   * @return true if the user chooses to exit the application, false otherwise.
   */
  private boolean startMenu() {
    int choice;
    view.showPrimaryMenu();
    choice = inputPositiveInteger("Enter your choice: ");

    switch (choice) {
      case 1:
        boolean showSubMenu = true;
        while (showSubMenu) {
          showSubMenu = showCreateMenu();
        }
        break;
      case 2:
        boolean showLoadMenu = true;
        while (showLoadMenu) {
          showLoadMenu = showLoadMenu();
        }
        break;
      case 3:
        boolean showStockMenu = true;
        while (showStockMenu) {
          showStockMenu = stockMenu();
        }
        break;
      case 4:
        return true;
      //exit();
      default:
        this.view.displayError("Enter a valid choice, this option doesn't exists.");
        return false;
    }
    return false;
  }

  /**
   * this method displays menu for loading portfolios.
   * handles user input to initiate loading operations.
   *
   * @return true if menu should be displayed again, or false otherwise.
   */
  private boolean showLoadMenu() {
    int subChoice;
    view.choosePortfolioType();
    subChoice = inputPositiveInteger("Enter your choice: ");
    switch (subChoice) {
      case 1:
        loadInflexiblePortfolio();
        return false;
      case 2:
        loadFlexiblePortfolio();
        return false;
      case 3:
        //exit
        return false;
      default:
        this.view.displayError("Enter a valid choice, this option doesn't exists.");
        return true;
    }
  }

  /**
   * this method displays menu for creating portfolios.
   * & handles user input to initiate creation operations.
   *
   * @return true if menu should be displayed again or false otherwise.
   */
  private boolean showCreateMenu() {
    int subChoice;
    view.choosePortfolioType();
    subChoice = inputPositiveInteger("Enter your choice: ");
    switch (subChoice) {
      case 1:
        createPortfolio();
        return false;
      case 2:
        createFlexiblePortfolio();
        return false;
      case 3:
        //exit
        return false;
      default:
        this.view.displayError("Enter a valid choice, this option doesn't exists.");
        return true;
    }
  }

  /**
   * this method displays menu for stock analysis.
   * handles user input to initiate analysis operations.
   *
   * @return true if menu should be displayed again or false otherwise.
   */
  private boolean stockMenu() {
    int subChoice;
    view.showStockStat();
    subChoice = inputPositiveInteger("Enter your choice: ");
    switch (subChoice) {
      case 1:
        gainOrLose();
        break;
      case 2:
        gainOrLoseOverPeriod();
        break;
      case 3:
        xDayMovingAvg();
        break;
      case 4:
        crossoverOverPeriod();
        break;
      case 5:
        movingCrossoversOverPeriod();
        break;
      case 6:
        stockPerformance();
        break;
      case 7:
        //exit
        return false;
      default:
        this.view.displayError("Enter a valid choice, this option doesn't exists.");
        return true;
    }
    return true;
  }

  /**
   * This method get the input for creating a new portfolio by prompting user for portfolio name.
   * the number of stocks in portfolio, stock names/ ticker symbol, and their quantities.
   * This method passes this data to the model through addShare method called by portfolio builder.
   * and delegates to model that creates portfolio through addPortfolio method.
   */
  private void createPortfolio() {
    String name = inputPortfolioName();
    InflexiblePortfolioImpl.PortfolioBuilder newBuilder = new InflexiblePortfolioImpl
            .PortfolioBuilder(name);
    int numShares = inputPositiveInteger("Enter the number of stocks you want to have in this "
            + "portfolio: ");


    for (int i = 0; i < numShares; i++) {
      view.print("Enter the name of the share or ticker symbol: ");
      String shareName = scan.nextLine();
      int quantity = inputPositiveInteger("Enter the quantity of " + shareName + " you have: ");
      try {
        newBuilder.addShare(shareName, quantity);
      } catch (IllegalArgumentException e) {
        view.displayError("Error: " + e.getMessage() + "\nPlease enter a valid share name.\n");
        i--; //same share again asking
      }
    }
    try {
      this.model.addPortfolio(newBuilder);
    } catch (IllegalArgumentException e) {
      view.displayError("Cannot create portfolio with no stocks!");
    }
  }

  /**
   * This method get the input for creating a new portfolio by prompting user for portfolio name.
   * and delegates to model that creates flexible portfolio of entered name.
   */
  private void createFlexiblePortfolio() {
    String name = inputPortfolioName();

    model.createFlexiblePortfolio(name);
  }

  /**
   * This method takes in path from user for saving portfolio.
   * If the path is incorrect an exception is thrown.
   * the valid user input for portfolio to be selected is taken and that file is saved using.
   * exportAsCSV method in the persistence class in controller.
   */
  private void export() {
    int input = inputPortfolioChoice();
    view.print("Enter the proper path with file name in which you would like to save portfolio.");
    String path = scan.nextLine();
    Persistence persistence = new Persistence();
    try {
      persistence.exportAsCSV(path, model.save(input));
      view.print("Portfolio exported to " + path + " successfully.");
    } catch (IllegalArgumentException e) {
      view.displayError(e.getMessage());
    }
  }

  /**
   * this method allows user to buy stocks for a flexible portfolio.
   * Prompts user for portfolio choice, share name, quantity, and purchase date.
   * Calls model's buyStock method to handle purchase, and view to displays the messages.
   */
  private void buyStock() {
    int choice = inputPortfolioChoice();

    view.print("Enter the name of the share or ticker symbol: ");
    String shareName = scan.nextLine();
    int quantity = inputPositiveInteger("Enter the quantity of " + shareName + " you want to buy:");
    int[] date = inputDate("Enter the date of your purchase");
    LocalDate buyDate = LocalDate.of(date[2], date[1], date[0]);

    try {
      model.buyStock(choice, shareName, quantity, buyDate, new StockData());
      view.print(quantity + " " + shareName + " bought successfully");
    } catch (RuntimeException e) {
      view.displayError(e.getMessage());
    }
  }

  /**
   * this method allows user to sell stocks from a flexible portfolio.
   * Prompts user for portfolio choice, share name, quantity, and sale date.
   * Calls model's sellStock method to handle the sale, and view to displays the messages.
   */
  private void sellStock() {
    int choice = inputPortfolioChoice();

    view.print("Enter the name of the share or ticker symbol: ");
    String shareName = scan.nextLine();
    int quantity = inputPositiveInteger("Enter the quantity of " + shareName + " you want to "
            + "sell:");
    int[] date = inputDate("Enter the date of your sale");
    LocalDate sellDate = LocalDate.of(date[2], date[1], date[0]);

    try {
      model.sellStock(choice, shareName, quantity, sellDate, new StockData());
      view.print(quantity + " " + shareName + " sold successfully");
    } catch (RuntimeException e) {
      view.displayError(e.getMessage());
    }
  }

  /**
   * This method takes in the name of portfolio which should be given to the loaded portfolio file.
   * And takes in the path from the user to load portfolio data from.
   * throws exception if the file contains invalid data format or file cannot be loaded.
   */
  private void loadInflexiblePortfolio() {
    String name = inputPortfolioName();
    InflexiblePortfolioImpl.PortfolioBuilder newBuilder = new InflexiblePortfolioImpl
            .PortfolioBuilder(name);
    try {
      newBuilder.load(inputPath());
    } catch (IllegalArgumentException e) {
      view.displayError("The values provided in the file is invalid");
      return;
    }

    try {
      this.model.addPortfolio(newBuilder);
    } catch (IllegalArgumentException e) {
      view.displayError("Cannot create portfolio with no stocks!");
    }
    view.print("File loaded successfully");
  }

  /**
   * this method loads data from a file to create a flexible portfolio.
   * Prompts user for a portfolio name and file path.
   * If the file contains invalid data format or cannot be loaded, it throws an exception.
   * it uses view and model method to carry out this operation.
   */
  private void loadFlexiblePortfolio() {
    String name = inputPortfolioName();
    try {
      model.loadPortfolio(name, inputPath(), new StockData());
    } catch (IllegalArgumentException e) {
      view.displayError("The values provided in the file is invalid");
      return;
    }
    view.print("File loaded successfully");
  }

  /**
   * this method displays the composition of a portfolio selected by the user through the view.
   * The date is also given to the model method and view is used to prompt to user to enter date.
   * The values in portfolio its composition is got from the model.
   */
  private void examineComposition() {
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
  private void getTotalValue() {
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
  private void getCostBasis() {
    int choice = inputPortfolioChoice();

    int[] date = inputDate("Enter the date till which you want the cost basis of the portfolio");
    LocalDate costBasisDate = LocalDate.of(date[2], date[1], date[0]);

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
  private void portfolioPerformance() {
    int choice = inputPortfolioChoice();
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
      view.displayError("No such portfolio found");
      return;
    }
    int[] startDateArray = inputDate("Enter the start date");
    int[] endDateArray = inputDate("Enter the end date");
    LocalDate startDate = LocalDate.of(startDateArray[2], startDateArray[1], startDateArray[0]);
    LocalDate endDate = LocalDate.of(endDateArray[2], endDateArray[1], endDateArray[0]);
    TreeMap<String, Integer> result;
    try {
      result = model.portfolioPerformance(choice, startDate, endDate);
      int scale = model.scaleForPortfolioPerformance(choice, startDate, endDate);
      view.barGraph(scale, result, portfolioName, startDate + "", endDate + "");
    } catch (RuntimeException e) {
      view.displayError(e.getMessage());
    }
  }

  /**
   * this method calculates & displays performance of a specific stock over a given period,
   * using view and model methods.
   */
  private void stockPerformance() {
    StockData api = new StockData();
    view.print("Enter the name of the share or ticker symbol: ");
    String ticker = scan.nextLine();
    int[] startDateArray = inputDate("Enter the start date");
    int[] endDateArray = inputDate("Enter the end date");
    LocalDate startDate = LocalDate.of(startDateArray[2], startDateArray[1], startDateArray[0]);
    LocalDate endDate = LocalDate.of(endDateArray[2], endDateArray[1], endDateArray[0]);
    TreeMap<String, Integer> result;
    try {
      result = model.stockPerformance(ticker, api, startDate, endDate);
      int scale = model.scaleForStockPerformance(ticker, api, startDate, endDate);
      view.barGraph(scale, result, ticker, startDate + "", endDate + "");
    } catch (IllegalArgumentException e) {
      view.displayError(e.getMessage());
    }
  }

  /**
   * this method calculates & displays whether a specific stock gained or lost on a particular date.
   * using view and model methods.
   */
  private void gainOrLose() {
    StockData api = new StockData();
    view.print("Enter the name of the share or ticker symbol: ");
    String ticker = scan.nextLine();
    int[] dateArray = inputDate("Enter the date to know if the above stock gained or lost on that"
            + " "
            + "date: ");

    LocalDate date = LocalDate.of(dateArray[2], dateArray[1], dateArray[0]);

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
  private void gainOrLoseOverPeriod() {
    StockData api = new StockData();
    view.print("Enter the name of the share or ticker symbol: ");
    String ticker = scan.nextLine();
    int[] startDateArray = inputDate("Enter the start date");
    int[] endDateArray = inputDate("Enter the end date");
    LocalDate startDate = LocalDate.of(startDateArray[2], startDateArray[1], startDateArray[0]);
    LocalDate endDate = LocalDate.of(endDateArray[2], endDateArray[1], endDateArray[0]);

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
  private void xDayMovingAvg() {
    StockData api = new StockData();
    view.print("Enter the name of the share or ticker symbol: ");
    String ticker = scan.nextLine();
    int[] startDateArray = inputDate("Enter the start date");
    LocalDate startDate = LocalDate.of(startDateArray[2], startDateArray[1], startDateArray[0]);
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
  private void crossoverOverPeriod() {
    StockData api = new StockData();
    view.print("Enter the name of the share or ticker symbol: ");
    String ticker = scan.nextLine();
    int[] startDateArray = inputDate("Enter the start date");
    int[] endDateArray = inputDate("Enter the end date");
    LocalDate startDate = LocalDate.of(startDateArray[2], startDateArray[1], startDateArray[0]);
    LocalDate endDate = LocalDate.of(endDateArray[2], endDateArray[1], endDateArray[0]);
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
  private void movingCrossoversOverPeriod() {
    StockData api = new StockData();
    view.print("Enter the name of the share or ticker symbol: ");
    String ticker = scan.nextLine();
    int[] startDateArray = inputDate("Enter the start date");
    int[] endDateArray = inputDate("Enter the end date");
    LocalDate startDate = LocalDate.of(startDateArray[2], startDateArray[1], startDateArray[0]);
    LocalDate endDate = LocalDate.of(endDateArray[2], endDateArray[1], endDateArray[0]);
    TreeMap<String, String> result;

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

  /**
   * this prompts user to enter name for a new portfolio and ensures it is unique.
   *
   * @return validated portfolio name entered by user.
   */
  private String inputPortfolioName() {
    view.print("Enter the name of the portfolio: ");
    String portfolioName = scan.nextLine();
    if (model.portfolioNameExists(portfolioName)) {
      view.displayError("Portfolio with this name already exists!");
      return inputPortfolioName();
    }
    return portfolioName;
  }

  /**
   * this method validates user input to ensure it is a positive whole number.
   *
   * @param message message to prompt the user for input.
   * @return positive integer input by the user.
   */
  private int inputPositiveInteger(String message) {
    view.print(message);

    while (!scan.hasNextInt()) {
      view.displayError("Please enter a whole number");
      scan.next();
      view.print(message);
    }
    int input = scan.nextInt();
    scan.nextLine();

    if (isNegative(input)) {
      view.displayError("Enter a positive whole number");
      return inputPositiveInteger(message);
    }
    return input;
  }

  /**
   * this method checks the format of the date.
   *
   * @param date is the date that is passed to the method.
   * @return boolean value true if the format is correct for date entered else false.
   */
  private boolean isValidDateFormat(String date) {
    String regex = "\\d{4}-\\d{2}-\\d{2}";
    return Pattern.matches(regex, date);
  }

  /**
   * this method prompts user to input a date using view methods and validates format of the date.
   *
   * @return The date in day, month , year array format for further date validation.
   */
  private int[] inputDate(String message) {
    boolean validDate = false;
    String date;
    int day = 0;
    int month = 0;
    int year = 0;
    do {
      view.print(message);
      view.print("The date should be in this format yyyy-mm-dd: ");
      date = scan.nextLine();

      if (isValidDateFormat(date)) {
        String[] dateParts = date.split("-");
        year = Integer.parseInt(dateParts[0].trim());
        month = Integer.parseInt(dateParts[1].trim());
        day = Integer.parseInt(dateParts[2].trim());

        if (validateDate(day, month, year)) {
          validDate = true;
        } else {
          view.displayError("Invalid date!");
        }
      } else {
        view.displayError("Invalid date format.");
      }
    }
    while (!validDate);
    return new int[]{day, month, year};
  }

  /**
   * this method validates user input to ensure it is a valid choice among available portfolios.
   *
   * @return validated portfolio choice input by user, if wrong choice then displays error message.
   */
  private int validateUserChoice() {
    int choice = inputPositiveInteger("Enter the Portfolio number you want to select.");
    if (choice >= model.getSize() || choice < 0) {
      this.view.displayError("Enter a valid choice, this option doesn't exists.");
      return validateUserChoice();
    }
    return choice;
  }

  /**
   * this method is used for validation that num of shares entered by user are not negative.
   *
   * @param numShares number of shares entered by the user to be added in portfolio.
   * @return boolean value true if num of shares is less than 0.
   */
  private boolean isNegative(int numShares) {
    return numShares < 0;
  }

  /**
   * this method is used to validate date, if it is a valid date or not.
   *
   * @param day   is the day of the date entered.
   * @param month is the month of the date entered.
   * @param year  is the year of the date entered.
   * @return true if date is valid or else returns false.
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

  /**
   * this method is used to show the list of portfolios to the user using the view.
   * and then asks the user to enter the valid portfolio choice from the list.
   *
   * @return the portfolio number chosen by the user.
   */
  private int inputPortfolioChoice() {
    view.showListOfPortfolios(model.getListOfPortfoliosName());

    return validateUserChoice();
  }

  /**
   * This method prompts user to input a file path and validates it.
   * Used for validation in loading the portfolio.
   * The file is loaded using persistence class in controller.
   *
   * @return The validated file path input by the user.
   */
  private List<String[]> inputPath() {
    view.print("Enter the full path of the file you want to load data from: ");
    String pathName = scan.nextLine();

    Persistence persistence = new Persistence();
    try {
      return persistence.loadFromCSV(pathName);
    } catch (IllegalArgumentException e) {
      view.displayError(e.getMessage());
      return inputPath();
    }
  }

}
