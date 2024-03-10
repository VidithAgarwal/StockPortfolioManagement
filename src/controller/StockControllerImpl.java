package controller;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;
import model.PortfolioDir;
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
    String name = inputPortfolioNameFromUser();
    int numShares = inputPositiveInteger("Enter the number of stocks you want to have in this " +
            "portfolio: ");
    model.createBuilder(name);
    for (int i = 0; i < numShares; i++) {
      in.nextLine();
      view.print("Enter the name of the share or ticker symbol: ");
      String shareName = in.nextLine();
      int quantity = inputPositiveInteger("Enter the quantity of " + shareName + " you have: ");
      try {
        model.addShare(shareName, quantity);
      } catch (IllegalArgumentException e) {
        view.displayError("Error: " + e.getMessage() + "\nPlease enter a valid share name.\n");
        i--; //same share again asking
      }
    }
    this.model.addPortfolio();
  }

  public void loadPortfolio() {
    String name = inputPortfolioNameFromUser();
    String pathName = inputPath();
    //validation of correct path and csv format file.
    model.createBuilder(name);
    try {
      model.loadPortfolioData(pathName);
      this.model.addPortfolio();
    } catch (IllegalArgumentException e) {
      view.displayError("The values provided in the path is invalid");
    }
  }

  public void examineComposition() {
    int input = inputPortfolioChoice();
    view.showComposition(model.portfolioComposition(input));
  }

  public void save() {
    int input = inputPortfolioChoice();
    in.nextLine();
    view.print("Enter the proper path with file name in which you would like to save portfolio.");
    String path = in.nextLine();
    model.savePortfolio(input, path);
    view.print("Portfolio exported to " + path + " successfully.");
  }

  public void getTotalValue() {
    int choice = inputPortfolioChoice();
    in.nextLine();

    String date = inputDate();
    view.print("Wait until the total value is calculated");
    try {
      double totalValue = model.portfolioValue(choice, date);
      view.showTotalValue(totalValue);
    } catch (IllegalArgumentException e) {
      view.print("Error: No price data found for " + e.getMessage() + " on the date: " + date);
    } catch (RuntimeException e) {
      view.print("Data not found!");
    }
  }

  private boolean isNegative(int numShares) {
    return numShares < 0;
  }

  private String  inputPortfolioNameFromUser() {
    view.print("Enter the name of the portfolio: ");

    String name = in.nextLine();

    boolean found = this.portfolioExist(name);

    if (found) {
      this.view.displayError("Portfolio with this name already exists!");
      return inputPortfolioNameFromUser();
    }
    return name;
  }

  private String inputPath() {
    view.print("Enter the full path of the file you want to load data from: ");
    String pathName = in.nextLine();
    File file = new File(pathName);
    if (!file.exists()) {
      view.displayError("File not found. Please enter a valid file path.");
      return inputPath();
    }
    if (!pathName.toLowerCase().endsWith(".csv")) {
      view.displayError("File format is not CSV. Please enter a file with .csv extension.");
      return inputPath();
    }
    return pathName;
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

  private boolean portfolioExist(String name) {
    return model.exists(name);
  }

  private String inputDate() {
    boolean validDate = false;
    String date;
    do {
      view.print("Enter the date for which you want to get the total price of the portfolio. ");
      view.print("The date should be in this format yyyy-mm-dd: ");
      date = in.nextLine();
      if (isValidDateFormat(date)) {
        validDate = true;
      } else {
        view.displayError("Invalid date format.");
      }
    } while (!validDate);
    return date;
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
        model.deleteSessionCSVFilesFromStocklist(currentDirectory);
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





