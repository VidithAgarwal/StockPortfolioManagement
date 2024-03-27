package controller;

import java.io.File;
import java.time.LocalDate;
import java.util.Scanner;

import model.PortfolioDir;
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
    //System.out.println(directory);
    //System.out.println(Arrays.toString(allContents));
    if (allContents != null) {
      for (File file : allContents) {
        deleteCSVFiles(file);
      }
      try {
        if (!directory.delete()) {
          view.displayError("Deletion failed");
        }
      }  catch (Exception e) {
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
    int choice = 0;

    view.showSecondaryMenu();
    boolean exit = false;
    boolean showSubMenu = true;
    PortfolioAnalysisHelper portfolioAnalysisHelper = new PortfolioAnalysisHelper(view,
            model, scan);
    FlexiblePortfolioBuySellHelper flexiblePortfolioBuySellHelper
            = new FlexiblePortfolioBuySellHelper(view, model, scan);
    ExportHelper exportHelper = new ExportHelper(view, model, scan);
    choice = portfolioAnalysisHelper.inputPositiveInteger("Enter your choice: ");
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
          portfolioAnalysisHelper.examineComposition();
        } else {
          this.view.displayError("Enter a valid choice, this option doesn't exists.");
        }
        break;
      case 4:
        if (!model.isEmpty()) {
          view.print("Get total value of a portfolio for certain date");
          portfolioAnalysisHelper.getTotalValue();
        } else {
          this.view.displayError("Enter a valid choice, this option doesn't exists.");
        }
        break;
      case 5:
        if (!model.isEmpty()) {
          exportHelper.export();
        }
        break;
      case 6:
        if (!model.isEmpty()) {
          flexiblePortfolioBuySellHelper.buyStock();
        }
        break;
      case 7:
        if (!model.isEmpty()) {
          flexiblePortfolioBuySellHelper.sellStock();
        }
        break;
      case 8:
        if (!model.isEmpty()) {
          portfolioAnalysisHelper.getCostBasis();
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
          portfolioAnalysisHelper.portfolioPerformance();
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
    CreateHelper createHelper = new CreateHelper(view, model, scan);
    choice = createHelper.inputPositiveInteger("Enter your choice: ");

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
   * @return true if menu should be displayed again, or false otherwise.
   */
  private boolean showLoadMenu() {
    int subChoice = 0;
    view.choosePortfolioType();
    LoadHelper loadHelper = new LoadHelper(view, model, scan);
    subChoice = loadHelper.inputPositiveInteger("Enter your choice: ");
    switch (subChoice) {
      case 1:
        loadHelper.loadInflexiblePortfolio();
        return false;
      case 2:
        loadHelper.loadFlexiblePortfolio();
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
   * @return true if menu should be displayed again or false otherwise.
   */
  private boolean showCreateMenu() {
    int subChoice = 0;
    view.choosePortfolioType();
    CreateHelper createHelper = new CreateHelper(view, model, scan);
    subChoice = createHelper.inputPositiveInteger("Enter your choice: ");
    switch (subChoice) {
      case 1:
        createHelper.createPortfolio();
        return false;
      case 2:
        createHelper.createFlexiblePortfolio();
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
   * @return true if menu should be displayed again or false otherwise.
   */
  private boolean stockMenu() {
    int subChoice = 0;
    view.showStockStat();
    StockAnalysisHelper stockAnalysisHelper = new StockAnalysisHelper(view, model, scan);
    subChoice = stockAnalysisHelper.inputPositiveInteger("Enter your choice: ");
    switch (subChoice) {
      case 1:
        stockAnalysisHelper.gainOrLose();
        break;
      case 2:
        stockAnalysisHelper.gainOrLoseOverPeriod();
        break;
      case 3:
        stockAnalysisHelper.xDayMovingAvg();
        break;
      case 4:
        stockAnalysisHelper.crossoverOverPeriod();
        break;
      case 5:
        stockAnalysisHelper.movingCrossoversOverPeriod();
        break;
      case 6:
        stockAnalysisHelper.stockPerformance();
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

}
