package controller;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

import model.PortfolioDir;
import model.PortfolioImpl;
import view.IView;


public class StockControllerImpl implements StockController {

  private final IView view;

  private final Scanner in;
  private final PortfolioDir model;


  public StockControllerImpl(IView view, InputStream in, PortfolioDir portfolioDir) {
    this.view = view;
    this.in = new Scanner(in);
    this.model = portfolioDir;
  }

  public void createPortfolio() {
    String name = inputPortfolioName();
    PortfolioImpl.PortfolioBuilder newBuilder = new PortfolioImpl.PortfolioBuilder(name);
    int numShares = inputPositiveInteger("Enter the number of stocks you want to have in this " +
            "portfolio: ");


    for (int i = 0; i < numShares; i++) {
      in.nextLine();
      view.print("Enter the name of the share or ticker symbol: ");
      String shareName = in.nextLine();
      int quantity = inputPositiveInteger("Enter the quantity of " + shareName + " you have: ");
      try {
        newBuilder.addShare(shareName, quantity);
      } catch (IllegalArgumentException e) {
        view.displayError("Error: " + e.getMessage() + "\nPlease enter a valid share name.\n");
        i--; //same share again asking
      }
    }
    this.model.addPortfolio(newBuilder);
  }

  public void loadPortfolio() {
    String name = inputPortfolioName();
    PortfolioImpl.PortfolioBuilder newBuilder = new PortfolioImpl.PortfolioBuilder(name);
    try {
      newBuilder.load(inputPath());
    } catch (IllegalArgumentException e) {
      view.displayError("The values provided in the file is invalid");
    }

    model.addPortfolio(newBuilder);
  }

  public void examineComposition() {
    int input = inputPortfolioChoice();
    try {
      view.showComposition(model.portfolioComposition(input));
    } catch (IllegalArgumentException e) {
      view.displayError(e.getMessage());
    }

  }

  public void save() {
    int input = inputPortfolioChoice();
    in.nextLine();
    view.print("Enter the proper path with file name in which you would like to save portfolio.");
    String path = in.nextLine();
    Persistence persistence = new Persistence();
    try {
      persistence.exportAsCSV(path, model.portfolioComposition(input));
      view.print("Portfolio exported to " + path + " successfully.");
    } catch (IllegalArgumentException e) {
      view.displayError(e.getMessage());
    }
  }

  public void getTotalValue() {
    int choice = inputPortfolioChoice();
    in.nextLine();

    int[] date = inputDate();

    view.print("Wait until the total value is calculated");
    try {
      double totalValue = model.portfolioValue(choice, date[0], date[1], date[2]);
      view.showTotalValue(totalValue);
    } catch (IllegalArgumentException e) {
      if (e.getMessage() != null) {
        view.print("No price data found for " + e.getMessage() + " on the " +
        "date: " + date);
      } else {
        view.print("Invalid date!");
      }
    } catch (RuntimeException e) {
      view.print("The data could not be fetched today, try again later!");
    }
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

  private boolean isNegative(int numShares) {
    return numShares < 0;
  }

  private String inputPortfolioName() {
    view.print("Enter the name of the portfolio: ");
    String portfolioName = in.nextLine();
    if (model.portfolioNameExists(portfolioName)) {
      view.displayError("Portfolio with this name already exists!");
      return inputPortfolioName();
    }
    return portfolioName;
  }


  private List<String[]> inputPath() {
    view.print("Enter the full path of the file you want to load data from: ");
    String pathName = in.nextLine();

    Persistence persistence = new Persistence();
    try {
      return persistence.loadFromCSV(pathName);
    } catch (IllegalArgumentException e) {
      view.displayError(e.getMessage());
      return inputPath();
    }
  }

  private int validateUserChoice() {
    int choice = inputPositiveInteger("Enter the Portfolio number you want to select.");
    if (choice >= model.getSize() || choice < 0) {
      this.view.displayError("Enter a valid choice, this option doesn't exists.");
      return validateUserChoice();
    }
    return choice;
  }

  private int inputPositiveInteger(String message) {
    view.print(message);
    while (!in.hasNextInt()) {
      view.displayError("Please enter a whole number");
      in.next();
      view.print(message);
    }

    int input = in.nextInt();
    if(isNegative(input)) {
      view.displayError("Enter a positive whole number");
      return inputPositiveInteger(message);
    }
    return input;
  }

  private int inputPortfolioChoice() {
    ArrayList<String> listOfPortfolioNames = model.getListOfPortfoliosName();
    view.showListOfPortfolios(listOfPortfolioNames);

    return validateUserChoice();
  }

  private int[] inputDate() {
    boolean validDate = false;
    String date;
    int day = 0, month = 0, year = 0;
    do {
      view.print("Enter the date for which you want to get the total price of the portfolio. ");
      view.print("The date should be in this format yyyy-mm-dd: ");
      date = in.nextLine();

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
    } while (!validDate);
    return new int[]{day, month, year};
  }

  private boolean isValidDateFormat(String date) {
    String regex = "\\d{4}-\\d{2}-\\d{2}";
    return Pattern.matches(regex, date);
  }

  public void go() {
    boolean exit = false;
    while (!exit) {
      if (model.isEmpty()) {
        exit = startMenu();
      } else {
        exit = secondMenu();
      }
    }
  }

  public void deleteSessionCSVFilesFromStocklist(String directoryPath) throws IOException {
    File stocklistDirectory = new File(directoryPath);
    deleteSessionCSVFiles(stocklistDirectory);
  }

  private void deleteSessionCSVFiles(File directory) throws IOException {
    File[] files = directory.listFiles();
    if (files != null) {
      for (File file : files) {
        if (file.isDirectory()) {
          if (!file.getName().equalsIgnoreCase("testFiles")) {
            deleteSessionCSVFiles(file);
          }
        } else {
          String fileName = file.getName();
          if (fileName.endsWith(".csv") && !fileName.equals("stocks.csv")) {
            file.delete();
          }
        }
      }
    }
  }

  private boolean secondMenu() {

    int choice = 0;

    view.showSecondaryMenu();
    choice = inputPositiveInteger("Enter your choice: ");
    in.nextLine();
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
        //exit();
        break;
      default:
        this.view.displayError("Enter a valid choice, this option doesn't exists.");
        break;
    }
    if (exit) {
      try {
        String currentDirectory = System.getProperty("user.dir");
        deleteSessionCSVFilesFromStocklist(currentDirectory);
      } catch (IOException e) {
        throw new RuntimeException("Failed to delete one or more files. ");
      }
    }
    return exit;
  }

  private boolean startMenu() {

    int choice = 0;
    view.showPrimaryMenu();
    choice = inputPositiveInteger("Enter your choice: ");
    in.nextLine();

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
    }
    return false;
  }
}
