package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import model.Portfolio;
import model.PortfolioDir;
import model.PortfolioImpl;


public class MockModel implements PortfolioDir {

  private String mockName;
  private ArrayList<Portfolio> portfolioDirectory = new ArrayList<>();
  private Map<String, Integer> mockComposition = new HashMap<>();
  private double mockValue;
  private final StringBuilder logger = new StringBuilder();

  public MockModel(Map<String, Integer> map) {
    mockComposition = map;
  }

  public MockModel(double mockValue) {
    this.mockValue = mockValue;
  }

  public MockModel(String mockName) {
    this.mockName = mockName;
  }


  public StringBuilder getLogger() {
    return logger;
  }

  @Override
  public void addPortfolio(PortfolioImpl.PortfolioBuilder newBuilder) {
    portfolioDirectory.add(newBuilder.build());
    logger.append(portfolioDirectory.get(0).getName()).append('\n');
  }

  @Override
  public ArrayList<String> getListOfPortfoliosName() {
    ArrayList<String> listOfPortfolios = new ArrayList<>();
    listOfPortfolios.add(mockName);
    return listOfPortfolios;
  }

  @Override
  public Map<String, Integer> portfolioComposition(int input) {
    logger.append("Retrieving composition for portfolio at index: ").append(input).append('\n');
    return mockComposition;
  }

  @Override
  public int getSize() {
    return portfolioDirectory.size();
  }

  @Override
  public double portfolioValue(int input, int day, int month, int year) {
    logger.append("Retrieving composition for portfolio at index: ").append(input).
            append("For " + "the day: ").
            append(day).append(" month: ").
            append(month).append(" year: ").
            append(year);
    return mockValue;
  }


  @Override
  public boolean isEmpty() {
    return portfolioDirectory.isEmpty();
  }

  @Override
  public boolean portfolioNameExists(String portfolioName) {
    return false;
  }
}
