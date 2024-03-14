package controller;

/**
 * This interface represents a stock controller.
 * Controllers implementing this interface are responsible for delegating input from user to.
 * model and view.
 */
public interface StockController {

  /**
   * Initiates the main functionality of the application, handling the flow of the user interface.
   * It prompts the user with the appropriate menu based on the state of the portfolio directory.
   * If the portfolio directory is empty, it displays the primary menu.
   * otherwise, it displays the secondary menu.
   * The method continues looping until the user chooses to exit the application.
   */
  void execute();
}
