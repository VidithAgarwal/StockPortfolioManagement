package model;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class PortfolioDirImpl implements PortfolioDir{

  private final ArrayList<Portfolio> portfolioDirectory;
  private PortfolioImpl.PortfolioBuilder builder;

  public PortfolioDirImpl() {
    portfolioDirectory = new ArrayList<>();
  }

  @Override
  public void addPortfolio() {
    portfolioDirectory.add(builder.build());
  }

  @Override
  public void createBuilder(String portfolioName) {
    if(portfolioNameExists(portfolioName)) {
      throw new IllegalArgumentException();
    }
    builder = new PortfolioImpl.PortfolioBuilder(portfolioName);
  }

  @Override
  public void addShare(String shareName, int quantity) {
    builder.addShare(shareName, quantity);
  }

  @Override
  public void loadPortfolioData(String pathName) {
    builder.load(pathName);
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
    if (input >= portfolioDirectory.size()) {
      throw new IllegalArgumentException();
    }
    return portfolioDirectory.get(input).portfolioComposition();
  }

  @Override
  public int getSize() {
    return portfolioDirectory.size();
  }

  @Override
  public void savePortfolio(int input, String path) {
    if (input >= portfolioDirectory.size()) {
      throw new IllegalArgumentException();
    }
    portfolioDirectory.get(input).savePortfolio(path);
  }

  @Override
  public double portfolioValue(int input, String date) {
    if (input >= portfolioDirectory.size()) {
      throw new IllegalArgumentException();
    }
    return portfolioDirectory.get(input).portfolioValue(date);
  }

  @Override
  public boolean isEmpty() {
    return portfolioDirectory.isEmpty();
  }

  @Override
  public void deleteSessionCSVFilesFromStocklist(String directoryPath) throws IOException {
    File stocklistDirectory = new File(directoryPath);
    deleteSessionCSVFiles(stocklistDirectory);
  }

  boolean portfolioNameExists(String portfolioName) {

    for (Portfolio obj : portfolioDirectory) {
      if(obj.getName().equalsIgnoreCase(portfolioName)) {
        return true;
      }
    }

    return false;
  }


  private void deleteSessionCSVFiles(File directory) throws IOException {
    File[] files = directory.listFiles();
    if (files != null) {
      for (File file : files) {
        if (file.isDirectory()) {
          if (!file.getName().equalsIgnoreCase("testFiles")) {
            deleteSessionCSVFiles(file);
          }
        } else {
          String fileName = file.getName();
          if (fileName.endsWith(".csv") && !fileName.equals("stocks.csv")) {
            file.delete();
          }
        }
      }
    }
  }
}
