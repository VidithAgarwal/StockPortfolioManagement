package controller;

import java.util.Scanner;

import model.PortfolioDir;
import view.IView;

/**
 * The InputController class is responsible for managing user input and coordinating
 * interactions between the view and model components of the application.
 */
class InputController {
  private final IView view;

  private final Scanner in;
  private final PortfolioDir model;

  /**
   * Constructs a new InputController with the specified view, Scanner, and model.
   * @param view The view component to be associated with this controller.
   * @param in The Scanner object to be used for reading user input.
   * @param model The model component to be associated with this controller.
   */
  public InputController(IView view, Scanner in, PortfolioDir model) {
    this.view = view;
    this.in = in;
    this.model = model;
  }
}
