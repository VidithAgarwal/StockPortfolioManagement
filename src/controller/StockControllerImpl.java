package controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.regex.Pattern;

import model.Portfolio;
import model.PortfolioImpl;
import view.IView;


public class StockControllerImpl implements StockController {

  private final ArrayList<Portfolio> portfolioDirectory;
  private final IView view;

  private final Scanner in;

  public StockControllerImpl(IView view, InputStream in) {
    this.portfolioDirectory = new ArrayList<>();
    this.view = view;
    this.in = new Scanner(in);
  }

  public void createPortfolio() {
    view.print("Enter the name of the portfolio: ");

    String name = in.nextLine();

    boolean found = this.portfolioExist(name);

    if (found) {
      this.view.displayError("Portfolio with this name already exists!");
      createPortfolio();
      return;
    }

    int numShares = 0;
    boolean validInput = false;
    while (!validInput) {
      try {
        view.print("Enter the number of stocks you want to have in this portfolio: ");
        numShares = in.nextInt();
        validInput = true;
      } catch (InputMismatchException e) {
        view.displayError("Please enter a whole number");
        in.nextLine();
      }
    }

    PortfolioImpl.PortfolioBuilder newBuilder = new PortfolioImpl.PortfolioBuilder(name,
            numShares);
//    Map<String, Integer> shareQuantities = new HashMap<>();
    String shareName;
    int quantity = 0;
    for (int i = 0; i < numShares; i++) {
      in.nextLine();
      view.print("Enter the name of the share: ");
      shareName = in.nextLine();

      validInput = false;
      while (!validInput) {
        try {
          view.print("Enter the quantity of " + shareName + " you have: ");
          quantity = in.nextInt();
          if (quantity <= 0) {
            throw new InputMismatchException();
          }
          validInput = true;
        } catch (InputMismatchException e) {
          view.print("Please enter a positive whole number.");
          in.nextLine();
        }
      }

      try {
        newBuilder.addShare(shareName, quantity);
      } catch (IllegalArgumentException e) {
        view.print("Error: " + e.getMessage() + "\nPlease enter a valid share name.\n");
        i--; //same share again asking
      }
    }
    //newBuilder.addShare(shareName, quantity);
    this.portfolioDirectory.add(newBuilder.build());

  }

  public void loadPortfolio() {
    view.print("Enter the name of the portfolio: ");
    String name = in.nextLine();
    boolean found = this.portfolioExist(name);
    if (found) {
      this.view.displayError("Portfolio with this name already exists!");
      this.loadPortfolio();
      return;
    }
    view.print("Enter the full path of the file you want to load data from: ");
    String pathName = in.nextLine();
    //validation of correct path and csv format file.
    boolean validPath = false;

    while (!validPath) {
      try {
        File file = new File(pathName);
        if (!file.exists()) {
          throw new FileNotFoundException("File not found. Please enter a valid file path.");
        }
        if (!pathName.toLowerCase().endsWith(".csv")) {
          throw new IllegalArgumentException("File format is not CSV. Please enter a file with .csv extension.");
        }
        PortfolioImpl.PortfolioBuilder newBuilder = new PortfolioImpl.PortfolioBuilder(name);

        try {
          newBuilder.load(pathName);
          this.portfolioDirectory.add(newBuilder.build());

        } catch (IllegalArgumentException e) {
          view.displayError("The values provided in the path is invalid");
        }
        validPath = true;
      } catch (FileNotFoundException | IllegalArgumentException e) {
        view.displayError("Error reading file: " + e.getMessage());
        view.print("Enter the full path of the file you want to load data from: ");
        pathName = in.nextLine();
      }
    }
  }

  private boolean portfolioExist(String name) {
    for (Portfolio obj : portfolioDirectory) {
      if (obj.getName().equals(name)) {
        return true;
      }
    }
    return false;
  }


  public void examineComposition() {
    ArrayList<String> listOfPortfolios = getListOfPortfoliosName();
    view.showListOfPortfolios(listOfPortfolios);

    int input = in.nextInt();
    if (!validateUserChoice(input)) {
      examineComposition();
      return;
    }
    view.showComposition(portfolioDirectory.get(input).portfolioComposition());
  }

  public void save() {
    ArrayList<String> listOfPortfolioNames = getListOfPortfoliosName();
    view.showListOfPortfolios(listOfPortfolioNames);

    int input = in.nextInt();
    if (!validateUserChoice(input)) {
      save();
      return;
    }
    view.print("Enter the proper path with file name in which you would like to save portfolio.");
    //shouldn't this be in view , just the sentence.
    in.nextLine();

    String path = in.nextLine();
    portfolioDirectory.get(input).savePortfolio(path);
  }

  private boolean validateUserChoice(int input) {
    if (input >= portfolioDirectory.size() || input < 0) {
      this.view.displayError("Enter a valid choice, this option doesn't exists.");
      return false;
    }
    return true;
  }

  private ArrayList<String> getListOfPortfoliosName() {
    ArrayList<String> listOfPortfolios = new ArrayList<>();
    for (Portfolio obj : portfolioDirectory) {
      listOfPortfolios.add(obj.getName());
    }

    return listOfPortfolios;
  }

  public void getTotalValue() {
    ArrayList<String> listOfPortfolioNames = getListOfPortfoliosName();
    view.showListOfPortfolios(listOfPortfolioNames);

    int input = in.nextInt();
    if (!validateUserChoice(input)) {
      getTotalValue();
      return;
    }
    in.nextLine();

    boolean validDate = false;
    String date;
    do {
      view.print("Enter the date for which you want to get the total price of the portfolio. ");
      view.print("The date should be in this format yyyy-mm-dd: ");
      date = in.nextLine();
      if (isValidDateFormat(date)) {
        validDate = true;
      } else {
        view.print("Invalid date format.\n");
      }
    } while (!validDate);
    view.print("Wait until the total value is calculated");
    try {
      double totalValue = portfolioDirectory.get(input).portfolioValue(date);
      view.showTotalValue(totalValue);
    } catch (IllegalArgumentException e) {
      view.print("Error: No price data found for " + e.getMessage() + " on the date: " + date);
    }
  }

  private boolean isValidDateFormat(String date) {
    String regex = "\\d{4}-\\d{2}-\\d{2}";
    return Pattern.matches(regex, date);
  }
  public void go() {
    int choice = 0;

    while (choice != 3) {
      if (portfolioDirectory.isEmpty()) {
        view.showPrimaryMenu();
      } else {
        view.showSecondaryMenu();
      }
      choice = in.nextInt();
      in.nextLine();

      switch (choice) {
        case 1:
          createPortfolio();
          break;
        case 2:
          loadPortfolio();
          break;
        case 3:
          //exit();
          break;
        case 4:
          if (!portfolioDirectory.isEmpty()) {
            examineComposition();
          }
          break;
        case 5:
          if (!portfolioDirectory.isEmpty()) {
            view.print("Get total value of a portfolio for certain date");
            getTotalValue();
          }
          break;
        case 6:
          if (!portfolioDirectory.isEmpty()) {
            save();
          }
          break;
        default:
          this.view.displayError("Enter a valid choice, this option doesn't exists.");
          break;
      }
    }
  }


}
  
    


  
    
