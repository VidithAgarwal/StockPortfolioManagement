package model;

import java.time.LocalDate;
import java.util.List;

import controller.IStockData;

public interface Strategy {
  List<Transaction> applyStrategy(LocalDate date, Schedule schedule, IStockData api);
}
