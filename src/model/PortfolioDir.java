package model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public interface PortfolioDir {
  void addPortfolio(PortfolioImpl.PortfolioBuilder newBuilder);

  ArrayList<String> getListOfPortfoliosName();

  Map<String, Integer> portfolioComposition(int input);

  int getSize();

  double portfolioValue(int input, int day, int month, int year);

  boolean isEmpty();
  boolean portfolioNameExists(String portfolioName);
}
