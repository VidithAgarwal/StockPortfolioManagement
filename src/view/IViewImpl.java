package view;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Map;

public class IViewImpl implements IView{

  private PrintStream out;

  public IViewImpl(PrintStream out) {
    this.out = out;
  }

  @Override
  public void showPrimaryMenu() {

  }

  @Override
  public void showSecondaryMenu() {

  }

  @Override
  public void showComposition(Map<String, Integer> composition) {
    for (Map.Entry<String, Integer> entry : composition.entrySet()) {
      out.println(entry.getKey() + " : " + entry.getValue());
    }
  }

  @Override
  public void showTotalValue(float value) {

  }

  @Override
  public void showListOfPortfolios(ArrayList<String> listOfPortfolios) {
    for (int i = 0; i < listOfPortfolios.size(); i++) {

      out.println(i + " : " + listOfPortfolios.get(i));
    }
  }

  @Override
  public void displayError(String error) {

  }
}
