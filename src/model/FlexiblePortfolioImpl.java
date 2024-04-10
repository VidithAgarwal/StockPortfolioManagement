package model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import controller.IStockData;
import controller.StockData;

/**
 * Implementation of the Portfolio interface representing methods for a flexible portfolio.
 * It allows buying and selling stocks, computing portfolio composition, cost basis,
 * and portfolio value for a flexible portfolio.
 */
public class FlexiblePortfolioImpl extends AbstractPortfolio {

  /**
   * TreeMap to store composition of the portfolio on different dates.
   */
  private final TreeMap<LocalDate, Map<String, Double>> compositionOnDate;

  /**
   * ArrayList to store transaction history of the portfolio.
   */
  private final ArrayList<Transaction> transactions;

  /**
   * Constructor to initialize a flexible portfolio with a given name.
   *
   * @param portfolioName The name of the portfolio.
   */
  FlexiblePortfolioImpl(String portfolioName) {
    super(portfolioName);
    this.compositionOnDate = new TreeMap<>();
    this.transactions = new ArrayList<>();
  }

  /**
   * this method buys a specified quantity of a stock on a given date.
   *
   * @param tickerSymbol ticker symbol of the stock to buy.
   * @param quantity     quantity of the stock to buy.
   * @param buyDate      date of the purchase.
   * @param api          IStockData object used to fetch historical data.
   */
  @Override
  public void buyStock(String tickerSymbol, double quantity, LocalDate buyDate, IStockData api) {
    String ticker = validateStockName(tickerSymbol);
    if (ticker == null) {
      throw new IllegalArgumentException("Ticker symbol doesn't exist");
    }
    Map<String, ArrayList<Double>> priceData = api.fetchHistoricalData(ticker);
    if (!priceData.containsKey("" + buyDate)) {
      throw new IllegalArgumentException("Cannot buy on this date.");
    }
    Transaction buyTransaction = new Transaction("buy", ticker, quantity, buyDate);
    transactions.add(buyTransaction);
    Map.Entry<LocalDate, Map<String, Double>> closestEntry =
            compositionOnDate.floorEntry(buyDate);
    Map<String, Double> composition = new HashMap<>();

    if (closestEntry == null) {
      composition.put(ticker, quantity);
    } else {
      composition = deepCopy(closestEntry.getValue());
      if (composition.containsKey(ticker)) {
        double currentValue = composition.get(ticker);
        composition.put(ticker, currentValue + quantity);
      } else {
        composition.put(ticker, quantity);
      }
    }
    updateCompositionOnBuy(closestEntry, ticker, quantity);
    compositionOnDate.put(buyDate, composition);
  }

  /**
   * this method updates composition of the portfolio on a buy transaction.
   *
   * @param closestEntry closest entry in composition map to the buy date.
   * @param ticker       ticker symbol of the stock bought.
   * @param quantity     quantity of the stock bought.
   */
  private void updateCompositionOnBuy(Map.Entry<LocalDate, Map<String, Double>> closestEntry,
                                      String ticker, double quantity) {
    for (Map.Entry<LocalDate, Map<String, Double>> entry : (closestEntry != null
            ? this.compositionOnDate.tailMap(closestEntry.getKey(), false)
            : this.compositionOnDate).entrySet()) {
      Map<String, Double> futureComposition = deepCopy(entry.getValue());
      if (futureComposition.containsKey(ticker)) {
        double currentValue = futureComposition.get(ticker);
        futureComposition.put(ticker, currentValue + quantity);
      } else {
        futureComposition.put(ticker, quantity);
      }
      compositionOnDate.put(entry.getKey(), futureComposition);
    }
  }

  /**
   * this method sells a specified quantity of a stock on a given date.
   *
   * @param tickerSymbol ticker symbol of stock to sell.
   * @param quantity     quantity of the stock to sell.
   * @param sellDate     date of the sale.
   * @param api          IStockData object used to fetch historical data.
   */
  @Override
  public void sellStock(String tickerSymbol, double quantity, LocalDate sellDate, IStockData api) {
    String ticker = validateStockName(tickerSymbol);
    if (ticker == null) {
      throw new IllegalArgumentException("Ticker symbol doesn't exist");
    }
    Map<String, ArrayList<Double>> priceData = api.fetchHistoricalData(ticker);
    if (!priceData.containsKey("" + sellDate)) {
      throw new IllegalArgumentException("Cannot sell on this date.");
    }
    Transaction sellTransaction = new Transaction("sell", ticker, quantity, sellDate);
    transactions.add(sellTransaction);
    Map.Entry<LocalDate, Map<String, Double>> closestEntry =
            compositionOnDate.floorEntry(sellDate);
    Map<String, Double> composition;
    if (closestEntry == null) {
      throw new IllegalArgumentException("You can't sell before buying");
    } else {
      composition = deepCopy(closestEntry.getValue());
      if (composition.containsKey(ticker)) {
        double currentValue = composition.get(ticker);
        if (quantity > currentValue) {
          throw new IllegalArgumentException("You don't have enough quantity to sell");
        }
        double newValue = currentValue - quantity;
        if (newValue == 0) {
          composition.remove(ticker);
        } else {
          composition.put(ticker, newValue);
        }
      } else {
        throw new IllegalArgumentException("You don't have the stock you want to sell");
      }
    }
    updateCompositionOnSell(closestEntry, ticker, quantity);
    compositionOnDate.put(sellDate, composition);
  }

  /**
   * this method updates composition of the portfolio on a sell transaction.
   *
   * @param closestEntry closest entry in the composition map to the sell date.
   * @param ticker       ticker symbol of the stock sold.
   * @param quantity     of the stock sold.
   */
  private void updateCompositionOnSell(Map.Entry<LocalDate, Map<String, Double>> closestEntry,
                                       String ticker, double quantity) {
    boolean isQuantityValid = true;
    for (Map.Entry<LocalDate, Map<String, Double>> entry
            : this.compositionOnDate.tailMap(closestEntry.getKey(), false).entrySet()) {
      Map<String, Double> futureComposition = deepCopy(entry.getValue());
      if (futureComposition.containsKey(ticker)) {
        double currentValue = futureComposition.get(ticker);
        if (quantity > currentValue) {
          isQuantityValid = false;
          break;
        }
      } else {
        isQuantityValid = false;
        break;
      }
    }
    if (!isQuantityValid) {
      throw new IllegalArgumentException("Invalid sell!");
    }
    // Proceed with updating the futureComposition
    for (Map.Entry<LocalDate, Map<String, Double>> entry
            : this.compositionOnDate.tailMap(closestEntry.getKey(), false).entrySet()) {
      Map<String, Double> futureComposition = deepCopy(entry.getValue());
      if (futureComposition.containsKey(ticker)) {
        double currentValue = futureComposition.get(ticker);
        double newValue = currentValue - quantity;
        if (newValue == 0) {
          futureComposition.remove(ticker);
        } else {
          futureComposition.put(ticker, newValue);
        }
        compositionOnDate.put(entry.getKey(), futureComposition);
      }
    }
  }

  /**
   * this method retrieves composition of the portfolio on a specified date.
   *
   * @param date date for which the composition is to be retrieved.
   * @return map containing composition of the portfolio, stock ticker symbols and quantities.
   */
  @Override
  public Map<String, Double> portfolioComposition(LocalDate date) {
    Map.Entry<LocalDate, Map<String, Double>> closestEntry =
            compositionOnDate.floorEntry(date);
    if (closestEntry == null) {
      throw new IllegalArgumentException("Portfolio is empty");
    }
    return closestEntry.getValue();
  }

  /**
   * this method calculates cost basis of portfolio up to a specified date.
   *
   * @param date date up to which the cost basis is to be calculated.
   * @param api  IStockData object used to fetch historical data.
   * @return total cost basis of the portfolio.
   */
  @Override
  public double costBasis(LocalDate date, IStockData api) {
    double total = 0;
    for (var transaction : transactions) {
      if (!transaction.getDate().isAfter(date)) {
        LocalDate buyDate = transaction.getDate();
        String ticker = transaction.getStock();
        double quantity = transaction.getQuantity();
        double closingPrice = getClosingPriceOnDate(ticker, api, buyDate + "");
        if (transaction.getType().equalsIgnoreCase("buy")) {
          total += closingPrice * quantity;
        }
      }
    }
    return total;
  }

  /**
   * this method calculates total value of the portfolio on a specified date.
   *
   * @param date date for which the portfolio value is to be calculated.
   * @param api  IStockData object used to fetch historical data.
   * @return total value of the portfolio.
   */
  @Override
  public double portfolioValue(String date, IStockData api) {
    LocalDate valueDate = LocalDate.parse(date);
    Map.Entry<LocalDate, Map<String, Double>> closestEntry =
            compositionOnDate.floorEntry(valueDate);
    if (closestEntry == null) {
      return 0;
    }
    Map<String, Double> composition = closestEntry.getValue();
    return computeValue(date, composition, api);
  }

  /**
   * this method saves transaction data associated with the portfolio to a StringBuilder object.
   * The saved data includes the transaction type, symbol, quantity, and date.
   *
   * @return StringBuilder object containing the saved transaction data in CSV format.
   */
  @Override
  public StringBuilder save() {
    StringBuilder sb = new StringBuilder();
    sb.append("Transaction Type,Symbol,Quantity,Date").append(System.lineSeparator());
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    for (Transaction transaction : transactions) {
      sb.append(transaction.getType()).append(",");
      sb.append(transaction.getStock()).append(",");
      sb.append(transaction.getQuantity()).append(",");
      sb.append(transaction.getDate().format(formatter)).append(System.lineSeparator());
    }
    return sb;
  }

  /**
   * this method indicates whether portfolio is flexible or not.
   *
   * @return true, indicating the portfolio is flexible.
   */
  @Override
  public boolean isFlexible() {
    return true;
  }

  /**
   * this method loads portfolio data from a list of string arrays representing lines of data,
   * using the provided StockData object for fetching stock data.
   *
   * @param line list of string arrays representing lines of portfolio data to be loaded.
   * @param api  IStockData object used for fetching stock data.
   * @throws IllegalArgumentException if format of the date in file is incorrect
   *                                  or if data in file is invalid.
   */
  @Override
  public void load(List<String[]> line, IStockData api) {
    for (String[] parts : line) {
      if (validateLine(parts)) {
        try {
          if (parts[0].equalsIgnoreCase("buy")) {
            buyStock(parts[1], Integer.parseInt(parts[2]), LocalDate.parse(parts[3]), api);
          } else {
            sellStock(parts[1], Integer.parseInt(parts[2]), LocalDate.parse(parts[3]), api);
          }
        } catch (DateTimeParseException e) {
          throw new IllegalArgumentException("Format of date in the file is incorrect!");
        }

      } else {
        throw new IllegalArgumentException("Invalid data in given file!");
      }
    }
  }

  /**
   * this method validates a line of data from file.
   *
   * @param parts array representing a line of data from the file.
   * @return true if lines are valid or else false if any entry is invalid.
   */
  private boolean validateLine(String[] parts) {
    return parts.length == 4 && isInteger(parts[2].trim())
            && (parts[0].equalsIgnoreCase("buy")
            || parts[0].equalsIgnoreCase("sell"));
  }

  /**
   * this checks if a string represents an integer.
   *
   * @param str string to be checked.
   * @return true if string represents an integer, otherwise false.
   */
  private boolean isInteger(String str) {
    try {
      Integer.parseInt(str);
      return true;
    } catch (NumberFormatException e) {
      return false;
    }
  }


  /**
   * this method executes dollar-cost averaging investment strategy.
   * based on the provided schedule and current date,
   * using the given stock data API to fetch historical prices.
   * @param today    current date.
   * @param schedule  buying schedule defining investment strategy.
   * @param api      stock data API to fetch historical prices.
   */
  @Override
  public void strategicalInvestment(Schedule schedule, Strategy strategy, IStockData api) {
    List<Transaction> buyTransactions = strategy.applyStrategy(LocalDate.now(), schedule, api);
    for (Transaction transaction: buyTransactions) {
      buyStock(transaction.getStock(), transaction.getQuantity(), transaction.getDate(), api);
    }
  }
}
