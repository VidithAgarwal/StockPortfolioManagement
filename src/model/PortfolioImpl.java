package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import controller.StockData;

/**
 * Implementation of the Portfolio interface representing methods for a single portfolio.
 * The interface has various methods such as portfolio composition, portfolio value, getting name.
 * for a single portfolio.
 * The static portfolio builder class in also present in the PortfolioImpl class.
 */
public class PortfolioImpl extends AbstractPortfolio {

  /**
   * Map to store stock impl object that has ticker symbol and price data.
   * along with their quantities.
   */
  private final Map<String, Integer> sharesList;


  /**
   * Private constructor for PortfolioImpl, the portfolio name is initialised here.
   * the shareList map is also created that contains the stock data and its quantity.
   * @param portfolioName The name of the portfolio.
   * @param list Map containing stock data and their quantities.
   */
  private PortfolioImpl(String portfolioName, Map<String , Integer> list) {
    super(portfolioName);
    this.sharesList = deepCopy(list);
  }


  @Override
  public Map<String, Integer> portfolioComposition() {
    return deepCopy(this.sharesList);
  }

  @Override
  public Map<String, Integer> portfolioComposition(LocalDate date) {
    return null;
  }

  @Override
  public double costBasis(LocalDate date, StockData api) {
    throw new IllegalArgumentException();
  }


  @Override
  public double portfolioValue(String date, StockData api) {
    Map<String , Integer> composition = portfolioComposition();
    return computeValue(date, composition, api);
  }

  @Override
  public void buyStock(String ticker, int quantity, LocalDate date, StockData api) {
    throw new IllegalArgumentException();
  }

  @Override
  public void sellStock(String ticker, int quantity, LocalDate date, StockData api) {
    throw new IllegalArgumentException();
  }

  public boolean isFlexible() {
    return false;
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
    private Map<String , Integer> shareList;

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
    public void addShare(String shareName, int quantity) {
      String tickerSymbol = validateStockName(shareName);

      if (tickerSymbol == null) {
        throw new IllegalArgumentException("Share name not found in stocks.csv");
      }

      if (quantity <= 0 ) {
        throw new IllegalArgumentException("Quantity should be whole number.");
      }

      boolean flag = false;
      for (Map.Entry<String , Integer> entry : this.shareList.entrySet()) {
        if (entry.getKey().equals(tickerSymbol)) {
          int existingQuantity = this.shareList.get(entry.getKey());
          this.shareList.put(entry.getKey(), existingQuantity + quantity);
          flag = true;
        }
      }

      if (!flag) {
        this.shareList.put(tickerSymbol, quantity);
      }
    }

    /**
     * Validates if the share name exists in the stocks.csv file.
     * @param shareName The name of the share to be validated.
     * @return The ticker symbol of the share if found, otherwise null.
     */


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
          this.shareList.put(tickerSymbol, Integer.parseInt(value));
        } else {
          // Handle invalid line
          throw new IllegalArgumentException();
        }
      }
    }

    /**
     * Builds a new PortfolioImpl object.
     * that has portfolio name and shareList map, shareList contains the stockImpl object.
     * that has the share data with price and the quantity of that share.
     * if shareList is empty it throws exception, as empty portfolio cannot be built.
     * @return A Portfolio object, that has portfolio name and shareList map.
     */
    public Portfolio build() {
      if (this.shareList.isEmpty()) {
        throw new IllegalArgumentException();
      }
      return new PortfolioImpl(this.portfolioName, shareList);
    }
  }

}
