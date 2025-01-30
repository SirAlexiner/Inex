package no.ntnu.idatg1002.grp8.inex.financialdata;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;

/**
 * The FinancialInformation class retrieves financial data from three different lists,
 * aggregates it by
 * date into a map, and returns a list of FinancialData objects.
 */
public class FinancialInformation {

  @Getter
  @Setter
  private List<Income> incomes;
  @Getter
  @Setter
  private List<Expense> expenses;
  @Getter
  @Setter
  private List<Revenue> revenues;

  /**
   * The function retrieves financial data from three different lists and aggregates it by date
   * into a map, which is then converted into a list and returned.
   *
   * @return The method is returning a List of FinancialData objects.
   */
  public List<FinancialData> getAllData() {
    Map<LocalDate, FinancialData> dataMap = new HashMap<>();

    for (Income income : incomes) {
      LocalDate date = income.getDate();
      FinancialData currentData = dataMap.getOrDefault(date, new FinancialData(date, 0, 0, 0));
      currentData.setIncome(currentData.getIncome() + income.getIncomeData());
      dataMap.put(date, currentData);
    }

    for (Expense expense : expenses) {
      LocalDate date = expense.getDate();
      FinancialData currentData = dataMap.getOrDefault(date, new FinancialData(date, 0, 0, 0));
      currentData.setExpense(currentData.getExpense() + expense.getExpenseData());
      dataMap.put(date, currentData);
    }

    for (Revenue revenue : revenues) {
      LocalDate date = revenue.getDate();
      FinancialData currentData = dataMap.getOrDefault(date, new FinancialData(date, 0, 0, 0));
      currentData.setRevenue(currentData.getRevenue() + revenue.getRevenueData());
      dataMap.put(date, currentData);
    }

    return new ArrayList<>(dataMap.values());
  }
}
