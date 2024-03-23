package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import controller.StockData;

class FlexiblePortfolioImpl extends AbstractPortfolio {

  private final TreeMap<LocalDate, Map<String, Integer>> compositionOnDate;
  private final ArrayList<Transaction> transactions;

  FlexiblePortfolioImpl(String portfolioName) {
    super(portfolioName);
    this.compositionOnDate = new TreeMap<>();
    this.transactions = new ArrayList<>();
  }



  @Override
  public void buyStock(String tickerSymbol, int quantity, LocalDate buyDate, StockData api) {
    String ticker = validateStockName(tickerSymbol);
    if (ticker == null) {
      throw new IllegalArgumentException("Ticker symbol doesn't exist");
    }
    Map<String, ArrayList<Double>> priceData = api.fetchHistoricalData(ticker);
    if (!priceData.containsKey("" + buyDate)) {
      throw new IllegalArgumentException("Wrong date");
    }
    Transaction buyTransaction = new Transaction("buy", ticker, quantity, buyDate);
    transactions.add(buyTransaction);


    Map.Entry<LocalDate, Map<String, Integer>> closestEntry =
            compositionOnDate.floorEntry(buyDate);
    Map<String, Integer> composition = new HashMap<>();

    if (closestEntry == null) {
      composition.put(ticker, quantity);
    } else {
      composition = deepCopy(closestEntry.getValue());
      if (composition.containsKey(ticker)) {
        int currentValue = composition.get(ticker);
        composition.put(ticker, currentValue + quantity);
      } else {
        composition.put(ticker, quantity);
      }
    }

    updateCompositionOnBuy(closestEntry, ticker, quantity);


    compositionOnDate.put(buyDate, composition);

  }

  private void updateCompositionOnBuy(Map.Entry<LocalDate, Map<String, Integer>> closestEntry,
                                  String ticker, int quantity) {
    for (Map.Entry<LocalDate, Map<String, Integer>> entry : (closestEntry != null ?
            this.compositionOnDate.tailMap(closestEntry.getKey(), false) : this.compositionOnDate).entrySet()) {
      Map<String, Integer> futureComposition = deepCopy(entry.getValue());
      if (futureComposition.containsKey(ticker)) {
        int currentValue = futureComposition.get(ticker);
        futureComposition.put(ticker, currentValue + quantity);
      } else {
        futureComposition.put(ticker, quantity);
      }
      compositionOnDate.put(entry.getKey(), futureComposition);
    }
  }

  @Override
  public void sellStock(String tickerSymbol, int quantity, LocalDate sellDate, StockData api) {
    String ticker = validateStockName(tickerSymbol);
    if (ticker == null) {
      throw new IllegalArgumentException("Ticker symbol doesn't exist");
    }

    Map<String, ArrayList<Double>> priceData = api.fetchHistoricalData(ticker);
    if (!priceData.containsKey("" + sellDate)) {
      throw new IllegalArgumentException("Wrong date");
    }
    Transaction sellTransaction = new Transaction("sell", ticker, quantity, sellDate);
    transactions.add(sellTransaction);
    Map.Entry<LocalDate, Map<String, Integer>> closestEntry =
            compositionOnDate.floorEntry(sellDate);
    Map<String, Integer> composition;
    if (closestEntry == null) {
      throw new IllegalArgumentException("You can't sell before buying");
    } else {
      composition = deepCopy(closestEntry.getValue());
      if (composition.containsKey(ticker)) {
        int currentValue = composition.get(ticker);
        if (quantity > currentValue) {
          throw new IllegalArgumentException("You don't have enough quantity to sell");
        }
        int newValue = currentValue - quantity;
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

  private void updateCompositionOnSell(Map.Entry<LocalDate, Map<String, Integer>> closestEntry,
                                      String ticker, int quantity) {
    boolean isQuantityValid = true;

    for (Map.Entry<LocalDate, Map<String, Integer>> entry : this.compositionOnDate.tailMap(closestEntry.getKey(), false).entrySet()) {
      Map<String, Integer> futureComposition = deepCopy(entry.getValue());
      if (futureComposition.containsKey(ticker)) {
        int currentValue = futureComposition.get(ticker);
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
    for (Map.Entry<LocalDate, Map<String, Integer>> entry : this.compositionOnDate.tailMap(closestEntry.getKey(), false).entrySet()) {
      Map<String, Integer> futureComposition = deepCopy(entry.getValue());
      if (futureComposition.containsKey(ticker)) {
        int currentValue = futureComposition.get(ticker);
        int newValue = currentValue - quantity;
        if (newValue == 0) {
          futureComposition.remove(ticker);
        } else {
          futureComposition.put(ticker, newValue);
        }
        compositionOnDate.put(entry.getKey(), futureComposition);
      }
    }
  }

  @Override
  public Map<String, Integer> portfolioComposition() {
    throw new IllegalArgumentException();
  }

  public Map<String, Integer> portfolioComposition(LocalDate date) {
    Map.Entry<LocalDate, Map<String, Integer>> closestEntry =
            compositionOnDate.floorEntry(date);

    if (closestEntry == null) {
      return new HashMap<>();
    }

    return closestEntry.getValue();
  }

  @Override
  public double costBasis(LocalDate date, StockData api) {
    double total = 0;
    for (var transaction : transactions) {
      if (!transaction.getDate().isAfter(date)) {
        LocalDate buyDate = transaction.getDate();
        String ticker = transaction.getStock();
        int quantity = transaction.getQuantity();
        double closingPrice = getClosingPriceOnDate(ticker, api, buyDate + "");
        if (transaction.getType().equalsIgnoreCase("buy")) {
          total += closingPrice * quantity;
        }
      }
    }
    return total;
  }


  @Override
  public double portfolioValue(String date, StockData api) {
    LocalDate valueDate = LocalDate.parse(date);
    Map.Entry<LocalDate, Map<String, Integer>> closestEntry =
            compositionOnDate.floorEntry(valueDate);

    if (closestEntry == null) {
      return 0;
    }

    Map<String, Integer> composition = closestEntry.getValue();

    return computeValue(date, composition, api);
  }

  public boolean isFlexible() {
    return true;
  }


}
