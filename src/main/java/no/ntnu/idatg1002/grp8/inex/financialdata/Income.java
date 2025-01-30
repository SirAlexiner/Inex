package no.ntnu.idatg1002.grp8.inex.financialdata;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import lombok.Getter;
import lombok.Setter;

/**
 * The `Income` class represents a source of income and includes private instance variables
 * for date,
 * income data, description, and category, as well as methods for getting and
 * setting these variables
 * and parsing the date.
 */
public class Income {
  @Setter
  private String date;
  @Getter
  @Setter
  private double incomeData;
  @Getter
  @Setter
  private String description;
  @Getter
  @Setter
  private String category;

  /**
   * This is a constructor for the `Income` class that takes in four parameters: `date`, `income`,
   * `description`, and `category`. It sets the values of the private instance variables `date`,
   * `incomeData`, `description`, and `category` to the values of the corresponding parameters
   * passed in.
   * This allows an `Income` object to be created with specific values for these variables.
   *
   * @param date A 'String' representing the date of the income
   * @param income A 'double' representing the amount of the income
   * @param description A 'String' describing the income
   * @param category A 'String' detailing the category of income
   */
  public Income(String date, double income, String description, String category) {
    this.date = date;
    this.incomeData = income;
    this.description = description;
    this.category = category;
  }

  /**
   * This function parses a string date in the format "yyyy-MM-dd" and returns a LocalDate object.
   *
   * @return A `LocalDate` object is being returned. The `getDate()` method uses a
   *         `DateTimeFormatter`
   *         to parse a date string in the format "yyyy-MM-dd" and returns a `LocalDate` object
   *         representing that date.
   */
  public LocalDate getDate() {
    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    return LocalDate.parse(date, dateFormatter);
  }

  @Override
  public String toString() {
    // Customize the output format as needed
    return date + " - " + incomeData + " - " + category + " : " + description;
  }
}
