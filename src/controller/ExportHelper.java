package controller;

import java.util.Scanner;

import model.PortfolioDir;
import view.IView;

/**
 * exportHelper class provides methods for exporting portfolio data to a CSV file.
 * it also extends AbsHelperController class, it uses view and model methods.
 */
class ExportHelper extends AbsHelperController {

  /**
   * this method constructs exportHelper object with specified view, model, & scanner.
   * @param view view component for user interaction.
   * @param model model component for managing portfolios.
   * @param scan scanner object for user input.
   */
  ExportHelper(IView view, PortfolioDir model, Scanner scan) {
    super(view, model, scan);
  }

  /**
   * This method takes in path from user for saving portfolio.
   * If the path is incorrect an exception is thrown.
   * the valid user input for portfolio to be selected is taken and that file is saved using.
   * exportAsCSV method in the persistence class in controller.
   */
  void export() {
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
}
