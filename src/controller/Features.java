package controller;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import model.InflexiblePortfolioImpl;

public interface Features {

  void createFlexiblePortfolio(String name);

  void export(int input, String path);

  void buyStock(String date, String quantity, String shareName, int choice);

  void sellStock(String date, String quantity, String shareName, int choice);

  void examineComposition(int input, String date);

  void getTotalValue(int choice, String date);

  void getCostBasis(int choice, String date);


  void gainOrLose(String date, String ticker);

  void gainOrLoseOverPeriod(String startDateArray, String endDateArray,String ticker );

  void xDayMovingAvg(String ticker, String x, String startDateArray);

  TreeMap<String, String> crossoverOverPeriod(String startDateArray, String endDateArray, String ticker);

  TreeMap<String, String> movingCrossoversOverPeriod(String startDateArray, String endDateArray, String x,String y,
                                  String ticker );


  String getErrorMessage();


  ArrayList<String> getPortfolioNames();

  String  getSuccessMessage();

  void loadPortfolio(String name, String portfolioPath);
}
