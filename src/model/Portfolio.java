package model;

import java.util.Map;

public interface Portfolio {
  public Map<String, Integer> portfolioComposition();


  public float portfolioValue (String portfolioName, String date);

  public void savePortfolio (String portfolioName);
  //public Map<String, Integer> loadPortfolio (String pathName);

  public String getName();
}
