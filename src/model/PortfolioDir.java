package model;

import java.util.ArrayList;
import java.util.Map;

public interface PortfolioDir {
  void addPortfolio(Portfolio portfolio);

  boolean exists(String name);

  ArrayList<String> getListOfPortfoliosName();

  Map<String, Integer> portfolioComposition(int input);

  int getSize();

  void savePortfolio(int input, String path);

  double portfolioValue(int input, String date);

  boolean isEmpty();
}
