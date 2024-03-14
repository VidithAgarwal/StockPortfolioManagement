package controller;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Map;

import view.IView;

public class MockView implements IView {

  private final StringBuilder printedOutput = new StringBuilder();

  public StringBuilder getPrintedOutput() {
    return printedOutput;
  }

  @Override
  public void showPrimaryMenu() {
  }

  @Override
  public void showSecondaryMenu() {
  }

  @Override
  public void showComposition(Map<String, Integer> composition) {
    // this we can remove
    for (Map.Entry<String, Integer> entry : composition.entrySet()) {
      printedOutput.append(entry.getKey()).append(" ").append(entry.getValue()).append("\n");
    }
  }

  @Override
  public void showTotalValue(double value) {
    printedOutput.append("The total value of portfolio is: $").append(value).append("\n");
  }

  @Override
  public void showListOfPortfolios(ArrayList<String> listOfPortfolios) {
    // this we can remove
    for (int i = 0; i < listOfPortfolios.size(); i++) {
      printedOutput.append(i).append(listOfPortfolios.get(i)).append("\n");
    }
  }

  @Override
  public void displayError(String error) {
    printedOutput.append(error).append("\n");
  }

  @Override
  public void print(String message) {
    printedOutput.append(message).append("\n");
  }
}