package controller;

import java.util.Scanner;

import model.PortfolioDir;
import view.IView;

class InputController {
  private final IView view;

  private final Scanner in;
  private final PortfolioDir model;

  public InputController(IView view, Scanner in, PortfolioDir model) {
    this.view = view;
    this.in = in;
    this.model = model;
  }
}
