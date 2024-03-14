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
    printedOutput.append(value).append("\n");
  }

  @Override
  public void showListOfPortfolios(ArrayList<String> listOfPortfolios) {
      printedOutput.append(listOfPortfolios.get(0)).append("\n");
  }

  @Override
  public void displayError(String error) {
    printedOutput.append("Error").append("\n");
  }

  @Override
  public void print(String message) {
    printedOutput.append(message).append("\n");
  }
}