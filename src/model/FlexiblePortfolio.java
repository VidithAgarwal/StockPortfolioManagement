package model;

import java.util.Map;

public interface FlexiblePortfolio extends Portfolio{
  void buyStock(String ticker, int quantity, String date);

  void sellStock(String ticker, int quantity, String date);

  Map<String, Integer> portfolioComposition(String date);


}
