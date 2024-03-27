package controller;

import java.util.Scanner;

import model.PortfolioDir;
import model.PortfolioImpl;
import view.IView;

class CreateHelper extends AbsHelperController {


  CreateHelper(IView view, PortfolioDir model, Scanner scan) {
    super(view, model, scan);
  }

  /**
   * This method get the input for creating a new portfolio by prompting user for portfolio name.
   * the number of stocks in portfolio, stock names/ ticker symbol, and their quantities.
   * This method passes this data to the model through addShare method called by portfolio builder.
   * and delegates to model that creates portfolio through addPortfolio method.
   */
  void createPortfolio() {
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

  void createFlexiblePortfolio() {
    String name = inputPortfolioName();

    model.createFlexiblePortfolio(name);
  }
}
