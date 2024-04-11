package model;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import controller.IStockData;


/**
 * Implementation of the Portfolio interface representing methods for a single inflexible portfolio.
 * The interface has various methods such as portfolio composition, portfolio value, getting name.
 * for a single portfolio, inflexible type.
 * The static portfolio builder class in also present in the InflexiblePortfolioImpl class.
 * It also extends abstract portfolio class that has abstract methods.
 */
public class InflexiblePortfolioImpl extends AbstractPortfolio {

  /**
   * Map to store stock impl object that has ticker symbol and price data.
   * along with their quantities.
   */
  private final Map<String, Double> sharesList;

  /**
   * Constructs a InflexiblePortfolioImpl object with the given portfolio name and share list.
   * @param portfolioName The name of the portfolio.
   * @param list The map containing ticker symbols and quantities of shares.
   */
  private InflexiblePortfolioImpl(String portfolioName, Map<String , Double> list) {
    super(portfolioName);
    this.sharesList = deepCopy(list);
  }

  /**
   * Returns a deep copy of the portfolio composition.
   * @return A map containing ticker symbols and quantities of shares.
   */
  private Map<String, Double> portfolioComposition() {
    return deepCopy(this.sharesList);
  }

  /**
   * Returns the composition of the portfolio.
   * @param date The date for which the composition is required (not used in this implementation).
   *             As for inflexible portfolio, composition is same on any date.
   * @return map containing ticker symbols and quantities of shares.
   */
  @Override
  public Map<String, Double> portfolioComposition(LocalDate date) {
    return portfolioComposition();
  }


  /**
   * this method generates a StringBuilder containing symbol and quantity data for each share.
   * in the portfolio. StringBuilder is returned for further processing or storage.
   * @return StringBuilder containing symbol and quantity data for each share in the portfolio.
   */
  @Override
  public StringBuilder save() {
    StringBuilder sb = new StringBuilder();
    sb.append("Symbol,Quantity").append(System.lineSeparator());
    for (Map.Entry<String, Double> entry : sharesList.entrySet()) {
      sb.append(entry.getKey()).append(",").append(entry.getValue());
      sb.append(System.lineSeparator());
    }
    return sb;
  }

  /**
   * this method calculates the cost basis of the portfolio, for flexible portfolio.
   * @param date date for which the cost basis is calculated.
   * @param api StockData object used for fetching stock data.
   * @return cost basis of the portfolio.
   * @throws IllegalArgumentException if the method is called by inflexible portfolio.
   */
  @Override
  public double costBasis(LocalDate date, IStockData api) {
    throw new IllegalArgumentException();
  }

  /**
   * this method calculates value of the portfolio on a given date.
   * @param date for which the portfolio value is calculated.
   * @param api StockData object used for fetching stock data.
   * @return value of the inflexible portfolio.
   */
  @Override
  public double portfolioValue(String date, IStockData api) {
    Map<String , Double> composition = portfolioComposition();
    return computeValue(date, composition, api);
  }

  /**
   * this method buys stock for portfolio (not implemented here), used in flexible portfolio.
   * @param ticker ticker symbol of the stock to be bought.
   * @param quantity  quantity of stock to be bought.
   * @param date date on which the stock is bought.
   * @param api StockData object used for fetching stock data.
   * @throws IllegalArgumentException if the method is called by inflexible portfolio.
   */
  @Override
  public void buyStock(String ticker, double quantity, LocalDate date, IStockData api) {
    throw new IllegalArgumentException();
  }

  /**
   * this method sells stock from the portfolio is implemented in flexible portfolio.
   * @param ticker ticker symbol of the stock to be sold.
   * @param quantity quantity of stock to be sold.
   * @param date date on which the stock is sold.
   * @param api StockData object used for fetching stock data.
   * @throws IllegalArgumentException if the method is called by inflexible portfolio.
   */
  @Override
  public void sellStock(String ticker, double quantity, LocalDate date, IStockData api) {
    throw new IllegalArgumentException();
  }

  /**
   * Checks if the portfolio is flexible.
   * @return always returns false, as this is an inflexible portfolio.
   */
  @Override
  public boolean isFlexible() {
    return false;
  }


  /**
   * the load method to load portfolio data from a list of string arrays representing lines of data.
   * and the StockData object used for fetching stock data.
   * @param lines A list of string arrays representing lines of portfolio data to be loaded.
   * @param api The StockData object used for fetching stock data.
   * @throws IllegalArgumentException throws an IllegalArgumentException as it is not implemented.
   */
  @Override
  public void load(List<String[]> lines, IStockData api) {
    throw new IllegalArgumentException();
  }

  @Override
  public void strategicalInvestment(Schedule schedule, Strategy strategy, IStockData api) {
    throw new IllegalArgumentException("Cannot add strategy to Inflexible portfolio");
  }

  /**
   * Static inner class to build Portfolio objects.
   */
  public static class PortfolioBuilder {

    /**
     * name of the portfolio that is being built.
     */
    private final String portfolioName;

    /**
     * Map to store shares data and their quantities.
     */
    private Map<String , Double> shareList;

    /**
     * Constructor for PortfolioBuilder, that takes in the portfolio name.
     * and initialises the portfolio name and creates the shareList map.
     * @param portfolioName The name of the portfolio to be built.
     */
    public PortfolioBuilder(String portfolioName) {
      shareList = new HashMap<>();
      this.portfolioName = portfolioName;
    }

    /**
     * Adds a share to the portfolio being built, with the share name and its quantity.
     * @param shareName The name of the share to be added.
     * @param quantity  The quantity of the share to be added.
     * @throws IllegalArgumentException If the share name is not found in the stocks.csv file.
     */
    public void addShare(String shareName, double quantity) {
      String tickerSymbol = validateStockName(shareName);

      if (tickerSymbol == null) {
        throw new IllegalArgumentException("Share name not found in stocks.csv");
      }

      if (quantity <= 0 ) {
        throw new IllegalArgumentException("Quantity should be whole number.");
      }

      boolean flag = false;
      for (Map.Entry<String , Double> entry : this.shareList.entrySet()) {
        if (entry.getKey().equals(tickerSymbol)) {
          double existingQuantity = this.shareList.get(entry.getKey());
          this.shareList.put(entry.getKey(), existingQuantity + quantity);
          flag = true;
        }
      }

      if (!flag) {
        this.shareList.put(tickerSymbol, quantity);
      }
    }

    /**
     * the method gets the lines for the file that is read in controller, and stores the stock name.
     * or ticker symbol along with its quantity in the shareList, which has stock data.
     * @param lines is the lines of the file that is read in the controller.
     */
    public void load(List<String[]> lines) {

      for (String[] line : lines) {
        if (line.length == 2) {
          String key = line[0].trim();
          String value = line[1].trim();
          //validation to check correct key entered that is stock name is remaining.
          String tickerSymbol = validateStockName(key);
          if (tickerSymbol == null) {
            throw new IllegalArgumentException("Share name not found in stocks.csv");
          }
          // Validate value is a positive whole number
          try {
            int intValue = Integer.parseInt(value);
            if (intValue <= 0) {
              throw new IllegalArgumentException();
            }
          } catch (NumberFormatException e) {
            throw new IllegalArgumentException();
          }
          this.shareList.put(tickerSymbol, Double.parseDouble(value));
        } else {
          // Handle invalid line
          throw new IllegalArgumentException();
        }
      }
    }

    /**
     * Builds a new InflexiblePortfolioImpl object.
     * that has portfolio name and shareList map, shareList contains the stockImpl object.
     * that has the share data with price and the quantity of that share.
     * if shareList is empty it throws exception, as empty portfolio cannot be built.
     * @return A Portfolio object, that has portfolio name and shareList map.
     */
    public Portfolio build() {
      if (this.shareList.isEmpty()) {
        throw new IllegalArgumentException();
      }
      return new InflexiblePortfolioImpl(this.portfolioName, shareList);
    }
  }

}
