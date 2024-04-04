package model;

import java.time.LocalDate;
import java.util.Map;

public interface BuyingStrategy {

  double getAmount();

  int getFrequencyDays();

  LocalDate getStartDate();

  LocalDate getEndDate();

  double getTransactionFee();

  LocalDate getLastRunDate();

  Map<String, Double> getBuyingList();
}
