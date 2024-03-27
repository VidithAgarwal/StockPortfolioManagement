package controller;

import java.util.Scanner;

import model.PortfolioDir;
import model.PortfolioImpl;
import view.IView;

/**
 * LoadHelper class provides helper methods for loading portfolios from files.
 * It extends the AbsHelperController class, uses model and view methods.
 */
class LoadHelper extends AbsHelperController {

  /**
   * constructs a LoadHelper object with specified view, model, and scanner.
   * @param view view component for user interaction.
   * @param model model component for managing portfolios.
   * @param scan scanner object for user input.
   */
  LoadHelper(IView view, PortfolioDir model, Scanner scan) {
    super(view, model, scan);
  }

  /**
   * This method takes in the name of portfolio which should be given to the loaded portfolio file.
   * And takes in the path from the user to load portfolio data from.
   * throws exception if the file contains invalid data format or file cannot be loaded.
   */
  void loadInflexiblePortfolio() {
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
   * this method loads data from a file to create a flexible portfolio.
   * Prompts user for a portfolio name and file path.
   * If the file contains invalid data format or cannot be loaded, it throws an exception.
   * it uses view and model method to carry out this operation.
   */
  void loadFlexiblePortfolio() {
    String name = inputPortfolioName();
    try {
      model.loadPortfolio(name, inputPath(), new StockData());
    } catch (IllegalArgumentException e) {
      view.displayError("The values provided in the file is invalid");
      return;
    }
    view.print("File loaded successfully");
  }
}
