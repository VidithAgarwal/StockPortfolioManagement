package model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public interface PortfolioDir {
  void addPortfolio();

  boolean exists(String name);

  ArrayList<String> getListOfPortfoliosName();

  Map<String, Integer> portfolioComposition(int input);

  int getSize();

  void savePortfolio(int input, String path);

  double portfolioValue(int input, String date);

  boolean isEmpty();

  void deleteSessionCSVFilesFromStocklist(String dir) throws IOException;
  void createBuilder(String portfolioName);
  void addShare(String shareName, int quantity);

  void loadPortfolioData(String pathName);
}
