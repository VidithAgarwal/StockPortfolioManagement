package model;

import java.util.ArrayList;
import java.util.Map;

public class PortfolioDirImpl implements PortfolioDir{

  private final ArrayList<Portfolio> portfolioDirectory;

  public PortfolioDirImpl() {
    portfolioDirectory = new ArrayList<>();
  }

  @Override
  public void addPortfolio(Portfolio portfolio) {
    portfolioDirectory.add(portfolio);
  }

  @Override
  public boolean exists(String name) {
    for (Portfolio obj : portfolioDirectory) {
      if (obj.getName().equals(name)) {
        return true;
      }
    }
    return false;
  }

  @Override
  public ArrayList<String> getListOfPortfoliosName() {
    ArrayList<String> listOfPortfolios = new ArrayList<>();
    for (Portfolio obj : portfolioDirectory) {
      listOfPortfolios.add(obj.getName());
    }

    return listOfPortfolios;
  }

  @Override
  public Map<String, Integer> portfolioComposition(int input) {
    return portfolioDirectory.get(input).portfolioComposition();
  }

  @Override
  public int getSize() {
    return portfolioDirectory.size();
  }

  @Override
  public void savePortfolio(int input, String path) {
    portfolioDirectory.get(input).savePortfolio(path);
  }

  @Override
  public double portfolioValue(int input, String date) {
    return portfolioDirectory.get(input).portfolioValue(date);
  }

  @Override
  public boolean isEmpty() {
    return portfolioDirectory.isEmpty();
  }


}
