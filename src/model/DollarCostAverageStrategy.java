package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import controller.IStockData;
import controller.StockData;

import static model.AbstractPortfolio.validateStockName;

/**
 * this class represents a Dollar Cost Averaging investment strategy.
 * this strategy involves investing a fixed amount of money at regular intervals.
 */
class DollarCostAverageStrategy implements Strategy {

  /**
   * this method applies Dollar Cost Averaging strategy to generate transactions
   * based on the given weights of stocks and the start and the end date.
   * @param today   current date when the scheduling for strategy should end.
   * @param schedule schedule for executing the strategy.
   * @param api       interface for accessing stock data.
   * @return list of transactions generated by applying strategy.
   * @throws IllegalArgumentException if an invalid stock name is encountered in buying list.
   */
  @Override
  public List<Transaction> applyStrategy(LocalDate today, Schedule schedule, IStockData api) {
    LocalDate current = schedule.getLastRunDate() == null ? schedule.getStartDate()
            .minusDays(schedule.getFrequencyDays()) : schedule.getLastRunDate();

    today = schedule.getEndDate() != null && today.isAfter(schedule.getEndDate())
            ? schedule.getEndDate() : today;

    List<Transaction> transactions = new ArrayList<>();
    while (today.isAfter(current)) {
      LocalDate newBuy = current.plusDays(schedule.getFrequencyDays());
      current = current.plusDays(schedule.getFrequencyDays());
      for (Map.Entry<String, Double> entry : schedule.getBuyingList().entrySet()) {
        String ticker = validateStockName(entry.getKey());
        if (ticker == null) {
          throw new IllegalArgumentException("Share name " + entry.getKey() + " doesn't exists");
        }
        int tryNextDay = 0;
        Map<String, ArrayList<Double>> prices = api.fetchHistoricalData(ticker);
        Double buyPrice = null;
        while (tryNextDay < 7 && tryNextDay < schedule.getFrequencyDays()) {
          try {
            buyPrice = prices.get(newBuy.plusDays(tryNextDay) + "").get(3);
            break;
          } catch (Exception ignored) {
            tryNextDay += 1;
          }
        }
        if (buyPrice == null) {
          continue;
        }
        double quantity =
                ((entry.getValue() / 100) * schedule.getAmount()) / buyPrice;
        quantity = Math.round(quantity * 100.0) / 100.0;
        System.out.println(quantity);

        Transaction transaction = new Transaction("Buy", ticker, quantity,
                newBuy.plusDays(tryNextDay));
        transactions.add(transaction);
      }
    }
    schedule.setLastRunDate(LocalDate.now());
    return transactions;
  }
}
