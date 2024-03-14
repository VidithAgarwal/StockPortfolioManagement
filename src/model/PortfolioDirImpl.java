package model;


import java.util.ArrayList;
import java.util.Map;

public class PortfolioDirImpl implements PortfolioDir{

  private final ArrayList<Portfolio> portfolioDirectory;

  public PortfolioDirImpl() {
    portfolioDirectory = new ArrayList<>();
  }

  @Override
  public void addPortfolio(PortfolioImpl.PortfolioBuilder newBuilder) {
    portfolioDirectory.add(newBuilder.build());
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
    if (input >= portfolioDirectory.size() || input < 0) {
      throw new IllegalArgumentException("The choice of portfolio doesn't exists");
    }
    return portfolioDirectory.get(input).portfolioComposition();
  }

  @Override
  public int getSize() {
    return portfolioDirectory.size();
  }

  @Override
  public double portfolioValue(int input, int day, int month, int year) {
    if (input >= portfolioDirectory.size() || input < 0) {
      throw new IllegalArgumentException("The choice of portfolio doesn't exists");
    }

    if (month < 1 || month > 12) {
      throw new IllegalArgumentException();
    }

    if (day < 1 || day > 31) {
      throw new IllegalArgumentException();
    }

    if ((month == 4 || month == 6 || month == 9 || month == 11) && day > 30) {
      throw new IllegalArgumentException();
    }

    if (month == 2) {
      boolean isLeapYear = (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
      if ((isLeapYear && day > 29) || (!isLeapYear && day > 28)) {
        throw new IllegalArgumentException();
      }
    }

    if (year > 9999 || year < 0) {
      throw new IllegalArgumentException();
    }
    String date = String.format("%04d-%02d-%02d", year, month, day);
    return portfolioDirectory.get(input).portfolioValue(date);
  }



  @Override
  public boolean isEmpty() {
    return portfolioDirectory.isEmpty();
  }

  @Override
  public boolean portfolioNameExists(String portfolioName) {

    for (Portfolio obj : portfolioDirectory) {
      if(obj.getName().equalsIgnoreCase(portfolioName)) {
        return true;
      }
    }

    return false;
  }
}
