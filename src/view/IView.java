package view;

import java.util.ArrayList;
import java.util.Map;

public interface IView {
  void showPrimaryMenu();

  void showSecondaryMenu();

  void showComposition(Map<String, Integer> composition);

  void showTotalValue(float value);

  void showListOfPortfolios(ArrayList<String> listOfPortfolios);

  void displayError(String error);
}
