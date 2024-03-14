package controller;

/**
 * This class represents a controller with a single action method. It's designed to handle specific
 * requests within an application. The go() method is the primary entry point for the
 * action this controller performs.
 */
public interface StockController {

  /**
   * Initiates the main functionality of the application, handling the flow of the user interface.
   * It prompts the user with the appropriate menu based on the state of the portfolio directory.
   * If the portfolio directory is empty, it displays the primary menu.
   * otherwise, it displays the secondary menu.
   * The method continues looping until the user chooses to exit the application.
   */
  void go();
}
