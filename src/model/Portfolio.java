package model;

import java.util.Map;

public interface Portfolio {
  Map<String, Integer> portfolioComposition();


  double portfolioValue (String date);

  void savePortfolio (String path);

  String getName();
}
