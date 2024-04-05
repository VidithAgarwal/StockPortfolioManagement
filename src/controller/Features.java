package controller;

import java.util.List;

import model.InflexiblePortfolioImpl;

public interface Features {

  void createFlexiblePortfolio(String name);

  void export(int input, String path);

  void buyStock(String date, String quantity, String shareName, int choice);

  void sellStock(String date, String quantity, String shareName, int choice);

  void examineComposition(int input, String date);

  void getTotalValue(int choice, String date);

  void getCostBasis(int choice, String date);

  void portfolioPerformance(String startDateArray, String endDateArray, int choice);

  void stockPerformance(String startDateArray, String endDateArray, String ticker );

  void gainOrLose(String date, String ticker);

  void gainOrLoseOverPeriod(String startDateArray, String endDateArray,String ticker );

  void xDayMovingAvg(String ticker, String x, String startDateArray);

  void crossoverOverPeriod(String startDateArray, String endDateArray, String ticker);

  void movingCrossoversOverPeriod(String startDateArray, String endDateArray, String x,String y,
                                  String ticker );


  String getErrorMessage();
}
