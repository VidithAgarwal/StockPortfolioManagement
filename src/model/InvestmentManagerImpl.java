package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import controller.IStockData;

/**
 * Implementation of the InvestmentManager interface.
 * It provides methods for managing portfolios, such adding portfolios, get list of portfolio.
 * as well as retrieving portfolio composition and total value of portfolio in portfolio directory.
 * It has methods for showing the stock trends, and buy, sell, cost basis option.
 *for flexible portfolio additionally with above methods, for both inflexible & flexible portfolio.
 */
public class InvestmentManagerImpl implements InvestmentManager {

  /**
   * Array list to store the portfolio objects.
   */
  private final ArrayList<Portfolio> portfolioDirectory;

  /**
   * Instance of StockStatistic for performing statistical calculations, stock trend.
   */
  private final StockStatistic stats = new StockStatisticsImpl();

  /**
   * Constructor to initialize the portfolio directory with the array list.
   */
  public InvestmentManagerImpl() {
    portfolioDirectory = new ArrayList<>();
  }

  @Override
  public void addPortfolio(InflexiblePortfolioImpl.PortfolioBuilder newBuilder) {
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
    LinkedHashMap<String, String> listOfPortfolio = new LinkedHashMap<>();
    for (Portfolio obj : portfolioDirectory) {
      if (obj.isFlexible()) {
        listOfPortfolio.put(obj.getName(), "Flexible");
      } else {
        listOfPortfolio.put(obj.getName(), "Inflexible");
      }
    }
    return listOfPortfolio;
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
  public double portfolioValue(int input, int day, int month, int year, IStockData api) {
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
  public void buyStock(int input, String stock, int quantity, LocalDate buyDate, IStockData api) {
    if (input >= portfolioDirectory.size() || input < 0) {
      throw new IllegalArgumentException("The choice of portfolio doesn't exists");
    }

    if (!portfolioDirectory.get(input).isFlexible()) {
      throw new IllegalArgumentException("Cannot buy in inflexible portfolio!");
    }
    portfolioDirectory.get(input).buyStock(stock, quantity, buyDate, api);
  }

  @Override
  public void sellStock(int input, String stock, int quantity, LocalDate sellDate, IStockData api) {
    if (input >= portfolioDirectory.size() || input < 0) {
      throw new IllegalArgumentException("The choice of portfolio doesn't exists");
    }
    if (!portfolioDirectory.get(input).isFlexible()) {
      throw new IllegalArgumentException("Cannot sell in inflexible portfolio!");
    }
    portfolioDirectory.get(input).sellStock(stock, quantity, sellDate, api);
  }

  @Override
  public double costBasis(int input, LocalDate date, IStockData api) {
    if (input >= portfolioDirectory.size() || input < 0) {
      throw new IllegalArgumentException("The choice of portfolio doesn't exists");
    }
    if (!portfolioDirectory.get(input).isFlexible()) {
      throw new IllegalArgumentException("Cannot get the cost basis of a inflexible portfolio!");
    }
    return portfolioDirectory.get(input).costBasis(date, api);
  }

  @Override
  public String gainOrLose(String stock, LocalDate date, IStockData api) {
    String tickerSymbol = AbstractPortfolio.validateStockName(stock);
    if (tickerSymbol == null) {
      throw new IllegalArgumentException("Invalid ticker symbol");
    }
    return stats.gainOrLoseOnDate(tickerSymbol, date
            + "", api.fetchHistoricalData(tickerSymbol));
  }

  @Override
  public String gainOrLoseOverAPeriod(String stock, LocalDate date1, LocalDate date2,
                                      IStockData api) {
    String tickerSymbol = AbstractPortfolio.validateStockName(stock);
    if (tickerSymbol == null) {
      throw new IllegalArgumentException("Invalid ticker symbol");
    }
    LocalDate currentDate = LocalDate.now();
    if (date2.isAfter(currentDate)) {
      throw new IllegalArgumentException("End Date should be less than current date");
    }
    if (date1.isAfter(date2)) {
      throw new IllegalArgumentException("Start Date should be less than End date");
    }
    return stats.gainOrLoseOverPeriod(tickerSymbol, date1 + "", date2 + "",
            api.fetchHistoricalData(tickerSymbol));
  }

  @Override
  public double xDayMovingAvg(String stock, LocalDate date, int x, IStockData api) {
    String tickerSymbol = AbstractPortfolio.validateStockName(stock);
    if (tickerSymbol == null) {
      throw new IllegalArgumentException("Invalid ticker symbol");
    }
    if (x < 0) {
      throw new IllegalArgumentException("Enter positive number of days");
    }

    return stats.xDayMovingAvg(tickerSymbol, date
            + "", x, api.fetchHistoricalData(tickerSymbol));
  }

  @Override
  public TreeMap<String, String> crossoverOverPeriod(String stock, IStockData api,
                                                     LocalDate startDate,
                                                     LocalDate endDate) {
    String tickerSymbol = AbstractPortfolio.validateStockName(stock);
    if (tickerSymbol == null) {
      throw new IllegalArgumentException("Invalid ticker symbol");
    }
    LocalDate currentDate = LocalDate.now();
    if (endDate.isAfter(currentDate)) {
      throw new IllegalArgumentException("End Date should be less than current date");
    }
    if (startDate.isAfter(endDate)) {
      throw new IllegalArgumentException("Start Date should be less than End date");
    }
    return stats.crossoverOverPeriod(tickerSymbol, api.fetchHistoricalData(tickerSymbol),
            startDate + "",
            endDate + "");
  }

  @Override
  public TreeMap<String, String> movingCrossOver(String stock, IStockData api, LocalDate startDate,
                                                 LocalDate endDate, int x, int y) {
    String tickerSymbol = AbstractPortfolio.validateStockName(stock);
    if (tickerSymbol == null) {
      throw new IllegalArgumentException("Invalid ticker symbol");
    }
    LocalDate currentDate = LocalDate.now();
    if (endDate.isAfter(currentDate)) {
      throw new IllegalArgumentException("End Date should be less than current date");
    }
    if (startDate.isAfter(endDate)) {
      throw new IllegalArgumentException("Start Date should be less than End date");
    }
    if (x < 0 || y < 0 || x > y) {
      throw new IllegalArgumentException("Enter positive number of days, & shorter "
              + "moving avg days should be less than larger moving avg days.");
    }
    return stats.movingCrossoversOverPeriod(tickerSymbol, api.fetchHistoricalData(tickerSymbol),
            startDate + "", endDate + "", x, y);
  }

  @Override
  public TreeMap<String, Integer> stockPerformance(String stock, IStockData api,
                                                    LocalDate start, LocalDate end) {
    Performance p = new Performance();
    TreeMap<String, Double> selectedData
            = getStockPerformance(end, start, stock, api, p);
    int scale = p.determineScale(selectedData);
    TreeMap<String, Integer> stockPerfom = p.determineValueBasedOnScale(selectedData,scale);
    return p.sortTreeMapByMonthAndYear(stockPerfom);
  }

  @Override
  public int scaleForStockPerformance(String stock, IStockData api,
                                      LocalDate start, LocalDate end) {
    IPerformance p = new Performance();

    TreeMap<String, Double> selectedData
            = getStockPerformance(end, start, stock, api, p);
    return p.determineScale(selectedData);
  }

  @Override
  public int scaleForPortfolioPerformance(int input, LocalDate start,  LocalDate end) {
    IPerformance p = new Performance();
    TreeMap<String, Double> selectedData
            = getPortfolioPerformance(end, start, input, p);
    return p.determineScale(selectedData);
  }

  @Override
  public StringBuilder save(int input) {
    if (input >= portfolioDirectory.size() || input < 0) {
      throw new IllegalArgumentException("The choice of portfolio doesn't exists");
    }
    return portfolioDirectory.get(input).save();
  }

  @Override
  public void loadPortfolio(String portfolioName, List<String[]> lines, IStockData api) {
    if (portfolioNameExists(portfolioName)) {
      throw new IllegalArgumentException();
    }
    createFlexiblePortfolio(portfolioName);
    portfolioDirectory.get(getSize() - 1).load(lines, api);
  }

  @Override
  public TreeMap<String, Integer> portfolioPerformance(int input,
                                                        LocalDate start,  LocalDate end) {
    IPerformance performance = new Performance();
    TreeMap<String, Double> selectedData
            = getPortfolioPerformance(end, start, input, performance);

    int scale = performance.determineScale(selectedData);
    TreeMap<String, Integer> portfolioPerfom = performance
            .determineValueBasedOnScale(selectedData,scale);
    return performance.sortTreeMapByMonthAndYear(portfolioPerfom);
  }

  private TreeMap<String, Double> getPortfolioPerformance(LocalDate end, LocalDate start, int input,
                                                 IPerformance performance) {
    LocalDate currentDate = LocalDate.now();
    if (end.isAfter(currentDate)) {
      throw new IllegalArgumentException("End Date should be less than current date");
    }
    if (start.isAfter(end)) {
      throw new IllegalArgumentException("Start Date should be less than End date");
    }
    return performance.portfolioPerformance(portfolioDirectory.get(input),start,end);
  }

  private TreeMap<String, Double> getStockPerformance(LocalDate end, LocalDate start,
                                                      String stock, IStockData api,
                                                      IPerformance p) {
    String tickerSymbol = AbstractPortfolio.validateStockName(stock);
    if (tickerSymbol == null) {
      throw new IllegalArgumentException("Invalid ticker symbol");
    }
    LocalDate currentDate = LocalDate.now();
    if (end.isAfter(currentDate)) {
      throw new IllegalArgumentException("End Date should be less than current date");
    }
    if (start.isAfter(end)) {
      throw new IllegalArgumentException("Start Date should be less than End date");
    }

    return p.stockPerformance(api.fetchHistoricalData(tickerSymbol), start, end);
  }


}