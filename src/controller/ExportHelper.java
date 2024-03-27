package controller;

import java.util.Scanner;

import model.PortfolioDir;
import view.IView;

class ExportHelper extends AbsHelperController {

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
