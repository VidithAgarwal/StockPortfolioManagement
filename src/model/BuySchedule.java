package model;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Map;

import static model.AbstractPortfolio.validateStockName;


/**
 * BuySchedule implements Schedule. It stores the information of scheduled stocks to buy,
 * date to buy, investment amount, and buying frequency days.
 */
class BuySchedule implements Schedule {

  private final int frequencyDays;

  private final String name;
  private final LocalDate startDate;
  private final LocalDate endDate;
  private final double amount;

  @Override
  public String getName() {
    return name;
  }

  private LocalDate lastRunDate;
  private final Map<String, Double> buyingList;

  /**
   * this construct BuySchedule object.
   * @param amount         investment amount.
   * @param frequencyDays  number of days.
   * @param startDate      start date.
   * @param endDate        end date.
   * @param lastRunDate    last schedule run date.
   * @param buyingList     buying stock list.
   * @throws IllegalArgumentException if input is not correct.
   */
  public BuySchedule(String name, double amount, int frequencyDays,
                     LocalDate startDate,
                     LocalDate endDate, LocalDate lastRunDate,
                     Map<String, Double> buyingList) throws IllegalArgumentException {
    if (endDate != null && endDate.isBefore(startDate)) {
      throw new IllegalArgumentException("endDate cannot be before startDate");
    }
    if (frequencyDays <= 0) {
      throw new IllegalArgumentException("Frequency day cannot be less than zero.");
    }
    Map<String, Double> correctBuyingList = getStringDoubleMap(amount, buyingList);
    this.name = name;
    this.amount = amount;
    this.frequencyDays = frequencyDays;
    this.startDate = startDate;
    this.endDate = endDate;
    this.lastRunDate = lastRunDate;
    this.buyingList = correctBuyingList;
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
      String ticker = validateStockName(entry.getKey());
      if (ticker == null) {
        throw new IllegalArgumentException(entry.getKey() + " share name doesn't exists");
      }

      total += entry.getValue();
    }

    if (total > 100) {
      throw new IllegalArgumentException("Total percentage cannot be more than 100%");
    }
    return buyingList;
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
  public LocalDate getLastRunDate() {
    return lastRunDate;
  }

  @Override
  public Map<String, Double> getBuyingList() {
    return Collections.unmodifiableMap(buyingList);
  }

  @Override
  public void setLastRunDate(LocalDate date) {
    this.lastRunDate = date;
  }
}
