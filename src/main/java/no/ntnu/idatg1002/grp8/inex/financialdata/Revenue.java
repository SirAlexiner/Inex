package no.ntnu.idatg1002.grp8.inex.financialdata;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import lombok.Getter;
import lombok.Setter;

/**
 * The `Revenue` class represents revenue data for a specific date
 * and includes a method to parse the date string into a `LocalDate` object.
 */
public class Revenue {
  @Setter
  private String date;
  @Getter
  @Setter
  private double revenueData;

  public Revenue(String date, double revenue) {
    this.date = date;
    this.revenueData = revenue;
  }

  /**
   * This function parses a string date in the format "yyyy-MM-dd" and returns a LocalDate object.
   *
   * @return A `LocalDate` object is being returned. The `getDate()` method
   *         uses a `DateTimeFormatter` to parse a date string in the format "yyyy-MM-dd"
   *         and returns a `LocalDate` object representing that date.
   */
  public LocalDate getDate() {
    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    return LocalDate.parse(date, dateFormatter);
  }
}

