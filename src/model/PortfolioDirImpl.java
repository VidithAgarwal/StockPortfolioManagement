package model;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import controller.StockData;

/**
 * Implementation of the PortfolioDir interface.
 * It provides methods for managing portfolios, such adding portfolios, get list of portfolio.
 * as well as retrieving portfolio composition and total value of portfolio in portfolio directory.
 */
public class PortfolioDirImpl implements PortfolioDir {

  /**
   * Array list to store the portfolio objects.
   */
  private final ArrayList<Portfolio> portfolioDirectory;
  private final StockStatistic stats = new StockStatisticsImpl();

  /**
   * Constructor to initialize the portfolio directory with the array list.
   */
  public PortfolioDirImpl() {
    portfolioDirectory = new ArrayList<>();
  }

  @Override
  public void addPortfolio(PortfolioImpl.PortfolioBuilder newBuilder) {
    portfolioDirectory.add(newBuilder.build());
  }

  @Override
  public void createFlexiblePortfolio(String portfolioName) {
    if (portfolioNameExists(portfolioName)) {
      throw new IllegalArgumentException();
    }
    portfolioDirectory.add(new FlexiblePortfolioImpl(portfolioName));
  }

  @Override
  public Map<String, String> getListOfPortfoliosName() {
    Map<String, String> listOfPortfolio = new HashMap<>();
    for (Portfolio obj : portfolioDirectory) {
      if(obj.isFlexible()) {
        listOfPortfolio.put(obj.getName(), "Flexible");
      } else {
        listOfPortfolio.put(obj.getName(), "Inflexible");
      }
    }
    return listOfPortfolio;
  }

  @Override
  public Map<String, Integer> portfolioComposition(int input) {
    if (input >= portfolioDirectory.size() || input < 0) {
      throw new IllegalArgumentException("The choice of portfolio doesn't exists");
    }
    return portfolioDirectory.get(input).portfolioComposition();
  }

  @Override
  public Map<String, Integer> portfolioComposition(int input, LocalDate date) {
    if (input >= portfolioDirectory.size() || input < 0) {
      throw new IllegalArgumentException("The choice of portfolio doesn't exists");
    }
    return portfolioDirectory.get(input).portfolioComposition(date);
  }

  @Override
  public int getSize() {
    return portfolioDirectory.size();
  }

  @Override
  public double portfolioValue(int input, int day, int month, int year, StockData api) {
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
    return portfolioDirectory.get(input).portfolioValue(date, api);
  }



  @Override
  public boolean isEmpty() {
    return portfolioDirectory.isEmpty();
  }

  @Override
  public boolean portfolioNameExists(String portfolioName) {

    for (Portfolio obj : portfolioDirectory) {
      if (obj.getName().equalsIgnoreCase(portfolioName)) {
        return true;
      }
    }
    return false;
  }

  @Override
  public void buyStock(int input, String stock, int quantity, LocalDate buyDate, StockData api) {
    if (input >= portfolioDirectory.size() || input < 0) {
      throw new IllegalArgumentException("The choice of portfolio doesn't exists");
    }
    portfolioDirectory.get(input).buyStock(stock, quantity, buyDate, api);
  }

  @Override
  public void sellStock(int input, String stock, int quantity, LocalDate sellDate, StockData api) {
    if (input >= portfolioDirectory.size() || input < 0) {
      throw new IllegalArgumentException("The choice of portfolio doesn't exists");
    }
    portfolioDirectory.get(input).sellStock(stock, quantity, sellDate, api);
  }

  @Override
  public double costBasis(int input, LocalDate date, StockData api) {
    if (input >= portfolioDirectory.size() || input < 0) {
      throw new IllegalArgumentException("The choice of portfolio doesn't exists");
    }
    return portfolioDirectory.get(input).costBasis(date, api);
  }

  @Override
  public String gainOrLose(String stock, LocalDate date, StockData api) {
    String tickerSymbol = AbstractPortfolio.validateStockName(stock);
    if (tickerSymbol == null) {
      throw new IllegalArgumentException("Invalid ticker symbol");
    }
    return stats.gainOrLoseOnDate(tickerSymbol, date + "", api.fetchHistoricalData(tickerSymbol));
  }

  @Override
  public String gainOrLoseOverAPeriod(String stock, LocalDate date1, LocalDate date2,
                                      StockData api) {
    String tickerSymbol = AbstractPortfolio.validateStockName(stock);
    if (tickerSymbol == null) {
      throw new IllegalArgumentException("Invalid ticker symbol");
    }
    return stats.gainOrLoseOverPeriod(tickerSymbol, date1 + "", date2 + "",
            api.fetchHistoricalData(tickerSymbol));
  }

  @Override
  public double xDayMovingAvg(String stock, LocalDate date, int x, StockData api) {
    String tickerSymbol = AbstractPortfolio.validateStockName(stock);
    if (tickerSymbol == null) {
      throw new IllegalArgumentException("Invalid ticker symbol");
    }
    return stats.xDayMovingAvg(tickerSymbol, date + "", x, api.fetchHistoricalData(tickerSymbol));
  }

  @Override
  public TreeMap<String, String> crossoverOverPeriod(String stock, StockData api,
                                                  LocalDate startDate,
                                                 LocalDate endDate) {
    String tickerSymbol = AbstractPortfolio.validateStockName(stock);
    if (tickerSymbol == null) {
      throw new IllegalArgumentException("Invalid ticker symbol");
    }
    return stats.crossoverOverPeriod(tickerSymbol, api.fetchHistoricalData(tickerSymbol),
            startDate + "",
            endDate + "");
  }

  @Override
  public TreeMap<String, String> movingCrossOver(String stock, StockData api, LocalDate startDate,
                                LocalDate endDate, int x, int y) {
    String tickerSymbol = AbstractPortfolio.validateStockName(stock);
    if (tickerSymbol == null) {
      throw new IllegalArgumentException("Invalid ticker symbol");
    }
    return stats.movingCrossoversOverPeriod(tickerSymbol, api.fetchHistoricalData(tickerSymbol),
            startDate + "", endDate + "", x,
     y);
  }


}
