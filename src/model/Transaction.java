package model;

import java.time.LocalDate;

public class Transaction {

  private final String txType;
  private final String ticker;
  private final int quantity;
  private final LocalDate date;


  /**
   * This is a constructor to construct a portfolio entry, which contains the type of transaction,
   * symbol, amount, transaction date, commission fee. The amount means shares.
   *
   * @param txType        the type of transaction
   * @param symbol        the symbol of a stock
   * @param quantity        the shares of a stock
   * @param date          the date to do the transaction
   */
  public Transaction(String txType,
                     String symbol,
                     int quantity,
                     LocalDate date) {
    this.txType = txType;
    this.ticker = symbol;
    this.quantity = quantity;
    this.date = date;
  }

  /**
   * This is the method that return the transaction type.
   *
   * @return the type of transaction.
   */
  public String getType() {
    return txType;
  }

  /**
   * This is the method that return the symbol of a stock in a string format.
   *
   * @return the symbol of stock
   */
  public String getStock() {
    return ticker;
  }

  /**
   * This is the method that return the shares of a stock.
   *
   * @return the shares of a stock
   */
  public int getQuantity() {
    return quantity;
  }

  /**
   * This is the method that return the date to do the transaction.
   *
   * @return the date of the transaction
   */
  public LocalDate getDate() {
    return date;
  }
}
