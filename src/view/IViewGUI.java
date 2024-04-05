package view;

import java.util.Map;

import controller.Features;

public interface IViewGUI {

  void addFeatures(Features features);

  String getPortfolioName();

  /**
   * this method displays an error message.
   * @param error The error message to be displayed.
   */
  void displayError(String error);

  /**
   * this method prints a message.
   * @param message The message to be printed.
   */
  void print(String message);

  String getPositiveInput();

  int[] inputDate();

  int selectPortfolio(Map<String, String> listOfPortfolios);

  String getPath();

  String getShareName();

}
