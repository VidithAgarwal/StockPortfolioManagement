package controller;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

import model.PortfolioDir;
import model.PortfolioImpl;
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
  private final PortfolioDir model;

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

  public StockControllerImpl(IView view, Readable in, PortfolioDir portfolioDir) {
    this.view = view;
    this.model = portfolioDir;
    this.scan = new Scanner(in);
  }

  /**
   * This method get the input for creating a new portfolio by prompting user for portfolio name.
   * the number of stocks in portfolio, stock names/ ticker symbol, and their quantities.
   * This method passes this data to the model through addShare method called by portfolio builder.
   * and delegates to model that creates portfolio through addPortfolio method.
   */
  private void createPortfolio() {
    String name = inputPortfolioName();
    PortfolioImpl.PortfolioBuilder newBuilder = new PortfolioImpl.PortfolioBuilder(name);
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
   * This method takes in the name of portfolio which should be given to the loaded portfolio file.
   * And takes in the path from the user to load portfolio data from.
   * throws exception if the file contains invalid data format or file cannot be loaded.
   */
  private void loadPortfolio() {
    String name = inputPortfolioName();
    PortfolioImpl.PortfolioBuilder newBuilder = new PortfolioImpl.PortfolioBuilder(name);
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
   * this method displays the composition of a portfolio selected by the user through the view.
   * The values in portfolio its composition is got from the model.
   */
  private void examineComposition() {
    int input = inputPortfolioChoice();
    try {
      view.showComposition(model.portfolioComposition(input));
    } catch (IllegalArgumentException e) {
      view.displayError(e.getMessage());
    }
  }

  /**
   * This method takes in path from user for saving portfolio.
   * If the path is incorrect an exception is thrown.
   * the valid user input for portfolio to be selected is taken and that file is saved using.
   * exportAsCSV method in the persistence class in controller.
   */
  private void save() {
    int input = inputPortfolioChoice();
    view.print("Enter the proper path with file name in which you would like to save portfolio.");
    String path = scan.nextLine();
    Persistence persistence = new Persistence();
    try {
      persistence.exportAsCSV(path, model.portfolioComposition(input));
      view.print("Portfolio exported to " + path + " successfully.");
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

    int[] date = inputDate();

    view.print("Wait until the total value is calculated");
    try {
      double totalValue = model.portfolioValue(choice, date[0], date[1], date[2]);
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
   * this method is used for validation that num of shares entered by user are not negative.
   *
   * @param numShares number of shares entered by the user to be added in portfolio.
   * @return boolean value true if num of shares is less than 0.
   */
  private boolean isNegative(int numShares) {
    return numShares < 0;
  }

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
   * Prompts the user to input a file path and validates it.
   * used for validation in loading the portfolio.
   * the file is loaded using persistence class in controller.
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

  /**
   * Validates user input to ensure it is a valid choice among available portfolios.
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
   * Validates user input to ensure it is a positive whole number.
   *
   * @param message The message to prompt the user for input.
   * @return The positive integer input by the user.
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
   * this method is used to show the list of portfolios to the user using the view.
   * and then asks the user to enter the valid portfolio choice from the list.
   *
   * @return the portfolio number chosen by the user.
   */
  private int inputPortfolioChoice() {
    ArrayList<String> listOfPortfolioNames = model.getListOfPortfoliosName();
    view.showListOfPortfolios(listOfPortfolioNames);

    return validateUserChoice();
  }

  /**
   * Prompts the user to input a date using view methods and validates the format of the date.
   *
   * @return The date in day, month , year array format for further date validation.
   */
  private int[] inputDate() {
    boolean validDate = false;
    String date;
    int day = 0;
    int month = 0;
    int year = 0;
    do {
      view.print("Enter the date for which you want to get the total price of the portfolio. ");
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
   * This method deletes all folders in a specified directory,
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
      if (!directory.delete()) {
        view.displayError("Failed to delete directory: " + directory.getName());
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
    int choice = 0;

    view.showSecondaryMenu();
    choice = inputPositiveInteger("Enter your choice: ");
    boolean exit = false;
    switch (choice) {
      case 1:
        createPortfolio();
        break;
      case 2:
        loadPortfolio();
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
          save();
        }
        break;
      case 6:
        exit = true;
        break;
      default:
        this.view.displayError("Enter a valid choice, this option doesn't exists.");
        break;
    }
    if (exit) {
      String currentDirectory = System.getProperty("user.dir") + "/Data";
      deleteCSVFilesFromStocklist(currentDirectory);
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
    int choice = 0;
    view.showPrimaryMenu();
    choice = inputPositiveInteger("Enter your choice: ");

    switch (choice) {
      case 1:
        createPortfolio();
        break;
      case 2:
        loadPortfolio();
        break;
      case 3:
        return true;
      //exit();
      default:
        this.view.displayError("Enter a valid choice, this option doesn't exists.");
        return false;
    }
    return false;
  }
}
