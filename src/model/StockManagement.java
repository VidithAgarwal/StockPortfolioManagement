package model;

import java.util.Map;

public interface StockManagement {
  public Map<String, Integer> portfolioComposition(String portfolioName);

  public float portfolioValue (String portfolioName, String date);

  public void persistPortfolio (String portfolioName);






}
