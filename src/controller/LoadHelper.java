package controller;

import java.util.Scanner;

import model.PortfolioDir;
import model.PortfolioImpl;
import view.IView;

class LoadHelper extends AbsHelperController {

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
