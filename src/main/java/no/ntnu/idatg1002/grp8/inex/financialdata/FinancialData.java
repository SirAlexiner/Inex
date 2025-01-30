package no.ntnu.idatg1002.grp8.inex.financialdata;

import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

/**
 * The `FinancialData` class is a Java class that represents financial data with properties
 * for date, income, expense, and revenue, and a constructor to set these values.
 */
public class FinancialData {

  @Getter
  @Setter
  private LocalDate date;
  @Getter
  @Setter
  private double income;
  @Getter
  @Setter
  private double expense;
  @Getter
  @Setter
  private double revenue;

  /**
   * This is a constructor for the `FinancialData` class that takes in four parameters: `date`,
   * `income`, `expense`, and `revenue`. It sets the values of the corresponding instance variables
   * using the `this` keyword to refer to the current object. This allows an instance of the
   * `FinancialData` class to be created with the specified values for its properties.
   *
   * @param date    A 'LocalDate' date detailing the date of the financial data.
   * @param income  A double, The income amount for the financial data.
   * @param expense A double, The expense amount for the financial data.
   * @param revenue A double, The revenue amount for the financial data.
   */
  public FinancialData(LocalDate date, double income, double expense, double revenue) {
    this.date = date;
    this.income = income;
    this.expense = expense;
    this.revenue = revenue;
  }
}
