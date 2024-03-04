package model;

import java.util.Map;

public interface Portfolio {
  Map<String, Integer> portfolioComposition();


  float portfolioValue (String portfolioName, String date);

  void savePortfolio (String portfolioName);

  String getName();
}
