package model;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


/**
 * BuySchedule implements BuyingStrategy. It stores the information of scheduled stocks to buy,
 * date to buy, investment amount, and buying frequency days.
 */
public class BuySchedule implements BuyingStrategy {

  private final int frequencyDays;
  private final LocalDate startDate;
  private final LocalDate endDate;
  private final double amount;
  private final double transactionFee;
  private final LocalDate lastRunDate;
  private final Map<String, Double> buyingList;

  /**
   * this construct BuySchedule object.
   * @param amount         investment amount.
   * @param frequencyDays  number of days.
   * @param startDate      start date.
   * @param endDate        end date.
   * @param transactionFee commission fee.
   * @param lastRunDate    last schedule run date.
   * @param buyingList     buying stock list.
   * @throws IllegalArgumentException if input is not correct.
   */
  public BuySchedule(double amount, int frequencyDays,
                     LocalDate startDate,
                     LocalDate endDate, double transactionFee, LocalDate lastRunDate,
                     Map<String, Double> buyingList) throws IllegalArgumentException {
    if (endDate != null && endDate.isBefore(startDate)) {
      throw new IllegalArgumentException("endDate cannot be before startDate");
    }
    if (transactionFee < 0) {
      throw new IllegalArgumentException("Commission fee cannot be negative.");
    }
    if (frequencyDays <= 0) {
      throw new IllegalArgumentException("Frequency day cannot be less than zero.");
    }
    Map<String, Double> scaledBuyingList = getStringDoubleMap(amount, buyingList);
    this.amount = amount;
    this.frequencyDays = frequencyDays;
    this.startDate = startDate;
    this.endDate = endDate;
    this.transactionFee = transactionFee;
    this.lastRunDate = lastRunDate;
    this.buyingList = scaledBuyingList;
  }

  /**
   * Scales buying percentages in provided buying list to ensure they sum up to 100%.
   * Throws IllegalArgumentException if buying list is empty or if the total investment.
   * amount is less than or equal to zero,
   * or if any individual share percentage is less than or equal to zero.
   * @param amount     total investment amount.
   * @param buyingList  map of stocks to buy, along with their respective percentages.
   * @return a new map with buying percentages scaled to sum up to 100%.
   * @throws IllegalArgumentException if buying list is empty, or if total investment amount is less
   *                                  than or equal to zero, or if any individual share percentage
   *                                  is less than or equal to zero.
   */
  private static Map<String, Double> getStringDoubleMap(double amount, Map<String,
          Double> buyingList) {
    if (buyingList == null || buyingList.isEmpty()) {
      throw new IllegalArgumentException("Stock buying list is empty");
    }
    if (amount <= 0) {
      throw new IllegalArgumentException("Amount cannot be less than zero");
    }
    double total = 0;
    for (Map.Entry<String, Double> entry : buyingList.entrySet()) {
      if (entry.getValue() <= 0) {
        throw new IllegalArgumentException("Share percentage cannot be less than zero");
      }
      total += entry.getValue();
    }
    Map<String, Double> scaledBuyingList = new HashMap<>();
    for (Map.Entry<String, Double> entry : buyingList.entrySet()) {
      scaledBuyingList.put(entry.getKey(), entry.getValue() * 100/total);
    }
    return scaledBuyingList;
  }

  @Override
  public double getAmount() {
    return amount;
  }

  @Override
  public int getFrequencyDays() {
    return frequencyDays;
  }

  @Override
  public LocalDate getStartDate() {
    return startDate;
  }

  @Override
  public LocalDate getEndDate() {
    return endDate;
  }

  @Override
  public double getTransactionFee() {
    return transactionFee;
  }

  @Override
  public LocalDate getLastRunDate() {
    return lastRunDate;
  }

  @Override
  public Map<String, Double> getBuyingList() {
    return Collections.unmodifiableMap(buyingList);
  }
}
