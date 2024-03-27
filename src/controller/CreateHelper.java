package controller;

import java.util.Scanner;

import model.PortfolioDir;
import model.PortfolioImpl;
import view.IView;

/**
 * CreateHelper class provides methods for creating portfolios,
 * flexible and inflexible using view for display and model.
 * It also extends AbsHelperController class.
 */
class CreateHelper extends AbsHelperController {


  /**
   * constructs CreateHelper object with specified view, model, & scanner.
   * @param view view component for user interaction.
   * @param model model component for managing portfolios.
   * @param scan scanner object for user input.
   */
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

  /**
   * This method get the input for creating a new portfolio by prompting user for portfolio name.
   * and delegates to model that creates flexible portfolio of entered name.
   */
  void createFlexiblePortfolio() {
    String name = inputPortfolioName();

    model.createFlexiblePortfolio(name);
  }
}
