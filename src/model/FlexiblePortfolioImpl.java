package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import controller.StockData;

public class FlexiblePortfolioImpl implements FlexiblePortfolio {

  private final TreeMap<LocalDate, Map<String, Integer>> compositionOnDate;
//  private final ArrayList<Transaction> Transactions;

  private final String portfolioName;

  public FlexiblePortfolioImpl(String portfolioName) {
    this.compositionOnDate = new TreeMap<>();
    this.portfolioName = portfolioName;
  }

  Map<String, Integer> deepCopy(Map<String, Integer> map1) {
    Map<String, Integer> map2 = new HashMap<>();
    for (Map.Entry<String, Integer> entry : map1.entrySet()) {
      map2.put(entry.getKey(), entry.getValue());
    }
    return map2;
  }

  @Override
  public void buyStock(String ticker, int quantity, String date) {
    LocalDate buyDate = LocalDate.parse(date);
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
  public void sellStock(String ticker, int quantity, String date) {
    LocalDate sellDate = LocalDate.parse(date);
    Map.Entry<LocalDate, Map<String, Integer>> closestEntry =
            compositionOnDate.floorEntry(sellDate);
    Map<String, Integer> composition;
    if (closestEntry == null) {
      throw new IllegalArgumentException();
    } else {
      composition = deepCopy(closestEntry.getValue());
      if (composition.containsKey(ticker)) {
        int currentValue = composition.get(ticker);
        if (quantity > currentValue) {
          throw new IllegalArgumentException();
        }
        int newValue = currentValue - quantity;
        if (newValue == 0) {
          composition.remove(ticker);
        } else {
          composition.put(ticker, newValue);
        }
      } else {
        throw new IllegalArgumentException();
      }
    }

    updateCompositionOnSell(closestEntry, ticker, quantity);

    compositionOnDate.put(sellDate, composition);
  }

  private void updateCompositionOnSell(Map.Entry<LocalDate, Map<String, Integer>> closestEntry,
                                      String ticker, int quantity) {
//    for (Map.Entry<LocalDate, Map<String, Integer>> entry : (
//            this.compositionOnDate.tailMap(closestEntry.getKey(), false)).entrySet()) {
//      Map<String, Integer> futureComposition = entry.getValue();
//      if (futureComposition.containsKey(ticker)) {
//        int currentValue = futureComposition.get(ticker);
//        if (quantity > currentValue) {
//          throw new IllegalArgumentException();
//        }
//        int newValue = currentValue - quantity;
//        if (newValue == 0) {
//          futureComposition.remove(ticker);
//        } else {
//          futureComposition.put(ticker, newValue);
//        }
//      } else {
//        throw new IllegalArgumentException();
//      }
//    }
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
      throw new IllegalArgumentException();
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
    return null;
  }

  public Map<String, Integer> portfolioComposition(String date) {
    LocalDate compositionDate = LocalDate.parse(date);
    Map.Entry<LocalDate, Map<String, Integer>> closestEntry =
            compositionOnDate.floorEntry(compositionDate);
    System.out.println(closestEntry);

    if (closestEntry == null) {
      return new HashMap<>();
    }

    return closestEntry.getValue();
  }

  private double getClosingPriceOnDate(String ticker, StockData api, String date) {
    Map<String, ArrayList<Double>> priceData = api.fetchHistoricalData(ticker);
    return priceData.get(date).get(1);
  }

  @Override
  public double portfolioValue(String date, StockData api) {
    LocalDate valueDate = LocalDate.parse(date);
    Map.Entry<LocalDate, Map<String, Integer>> closestEntry =
            compositionOnDate.floorEntry(valueDate);

    Map<String, Integer> composition = closestEntry.getValue();

    double totalValue = 0;
    for (Map.Entry<String, Integer> entry : composition.entrySet()) {
      String ticker = entry.getKey();
      Integer quantity = entry.getValue();
      try {
        Double closingPrice = getClosingPriceOnDate(ticker, api, date);
        totalValue += closingPrice * quantity;
      } catch (RuntimeException e) {
        throw new IllegalArgumentException(ticker);
      }
    }
    return totalValue;
  }

  @Override
  public String getName() {
    return portfolioName;
  }
}
