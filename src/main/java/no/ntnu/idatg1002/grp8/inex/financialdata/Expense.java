package no.ntnu.idatg1002.grp8.inex.financialdata;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import lombok.Getter;
import lombok.Setter;

/**
 * The `Expense` class represents an expense with a date, expense data, description, and category,
 * and includes methods for getting the date and converting the object to a string.
 */
public class Expense {
  @Setter
  private String date;
  @Getter
  @Setter
  private double expenseData;
  @Getter
  @Setter
  private String description;
  @Getter
  @Setter
  private String category;

  /**
   * This is a constructor for the `Expense` class that takes in four parameters: `date`, `expense`,
   * `description`, and `category`. It sets the values of the private instance variables `date`,
   * `incomeData`, `description`, and `category` to the values of the corresponding parameters
   * passed in.
   * This allows an `Expense` object to be created with specific values for these variables.
   *
   * @param date A 'String' representing the date of the expense
   * @param expense A 'double' representing the amount of the expense
   * @param description A 'String' describing the expense
   * @param category A 'String' detailing the category of expense
   */
  public Expense(String date, double expense, String description, String category) {
    this.date = date;
    this.expenseData = expense;
    this.description = description;
    this.category = category;
  }

  /**
   * This function parses a string date in the format "yyyy-MM-dd" and returns a LocalDate object.
   *
   * @return A `LocalDate` object is being returned. The `getDate()` method uses a
   *         `DateTimeFormatter`
   *         to parse a date string in the format "yyyy-MM-dd" and returns a `LocalDate`
   *         object representing that date.
   */
  public LocalDate getDate() {
    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    return LocalDate.parse(date, dateFormatter);
  }

  @Override
  public String toString() {
    // Customize the output format as needed
    return date + " - " + expenseData + " - " + category + " : " + description;
  }
}
