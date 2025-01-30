package no.ntnu.idatg1002.grp8.inex.gui.controllers.util;

import static atlantafx.base.theme.Styles.STATE_DANGER;
import static atlantafx.base.theme.Styles.STATE_SUCCESS;

import java.time.Month;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.ToIntFunction;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import lombok.experimental.UtilityClass;
import no.ntnu.idatg1002.grp8.inex.addressapi.AddressValidator;
import no.ntnu.idatg1002.grp8.inex.addressapi.ZipLocation;
import no.ntnu.idatg1002.grp8.inex.financialdata.Expense;
import no.ntnu.idatg1002.grp8.inex.financialdata.Income;
import no.ntnu.idatg1002.grp8.inex.utilities.InternetAccessChecker;

/**
 * The ControllerUtility class contains utility methods for validating and retrieving information
 * related to zip codes and addresses in a GUI application.
 */
@UtilityClass
public final class ControllerUtility {

  /**
   * This function checks if a zip code is valid and retrieves the corresponding city, municipality,
   * and county information if it is valid.
   *
   * @param zipCodeField      A TextField where the user enters a zip code.
   * @param cityField         A TextField where the city name corresponding to the entered zip code
   *                          will be displayed.
   * @param municipalityField A TextField where the municipality corresponding to the entered
   *                          zip code will be displayed.
   * @param countyField       A TextField where the county name corresponding to the entered
   *                          zip code will be displayed.
   */
  public static void checkValidZipLookup(TextField zipCodeField, TextField cityField,
                                         TextField municipalityField, TextField countyField) {

    String zipCode = zipCodeField.getText();

    // Prevent the user from entering more than 4 numbers
    if (zipCode.length() > 4) {
      zipCodeField.setText(zipCode.substring(0, 4));
      zipCode = zipCodeField.getText();
    }

    if (zipCode.matches("\\d{4}") && InternetAccessChecker.checkInternetAccess()) {
      String[] zipLookupResult = ZipLocation.zipLookup(zipCode);
      cityField.setText(zipLookupResult[0]);
      municipalityField.setText(zipLookupResult[1]);
      countyField.setText(zipLookupResult[2]);
    } else {
      cityField.setText("");
      municipalityField.setText("");
      countyField.setText("");
    }
  }

  /**
   * Validates the fields of an address provided as a Map of TextFields.
   * The method checks if the
   * address is valid using the AddressValidator class,
   * and updates the state of each TextField in
   * the addressFields List accordingly.
   * If the address is valid, the pseudo class STATE_SUCCESS is
   * applied to each TextField; otherwise, the pseudo class STATE_DANGER is applied.
   *
   * @param address   a Map object containing the address fields as TextFields,
   *                  with the following keys:
   *                  "customerStreet", "customerHouseNumber", "customerHouseLetter",
   *                  "customerZipCode", "customerCity",
   *                  "customerMunicipality", and "customerCounty".
   * @param isInvoice a boolean flag indicating whether the address is for an invoice or not.
   */
  public static void validateAddress(Map<String, TextField> address, boolean isInvoice) {
    List<TextField> addressFields = Arrays.asList(
        address.get("customerStreet"), address.get("customerHouseNumber"),
        address.get("customerHouseLetter"),
        address.get("customerZipCode"), address.get("customerCity"),
        address.get("customerMunicipality"), address.get("customerCounty"));

    boolean isValidAddress = AddressValidator.validAddress(address, isInvoice);

    for (TextField field : addressFields) {
      field.pseudoClassStateChanged(STATE_DANGER, !isValidAddress);
      field.pseudoClassStateChanged(STATE_SUCCESS, isValidAddress);
    }
  }

  /**
   * This function sets the address fields in a controller using a map of address values.
   *
   * @param address    A Map object that contains the address information,
   *                   where the keys are the names of
   *                   the address fields and the values are the corresponding values for those
   *                   fields.
   * @param controller The parameter "controller" is an object of type "AddressField" which is a
   *                   controller class for an address form.
   *                   It contains methods to set and validate the address fields.
   */
  public static void setAddress(Map<String, String> address, AddressField controller) {
    controller.getCustomerStreet().setText(address.get("customerStreet"));
    controller.getCustomerHouseNumber().setText(address.get("customerHouseNumber"));
    controller.getCustomerHouseLetter().setText(address.get("customerHouseLetter"));
    controller.getCustomerZipCode().setText(address.get("customerZipCode"));
    controller.getCustomerCity().setText(address.get("customerCity"));
    controller.getCustomerCounty().setText(address.get("customerMunicipality"));
    controller.getCustomerMunicipality().setText(address.get("customerCounty"));

    controller.validateAddress();
  }

  /**
   * This function populates two JavaFX ComboBoxes with a list of categories and a list of months.
   *
   * @param categories       A list of strings representing categories.
   * @param categoryComboBox A JavaFX ComboBox that will display a list of categories.
   * @param monthComboBox    A JavaFX ComboBox that will display a list of months to select from.
   */
  public static void populateComboBoxes(List<String> categories, ComboBox<String> categoryComboBox,
                                        ComboBox<String> monthComboBox) {
    ObservableList<String> categoryItems = FXCollections.observableArrayList(categories);
    categoryItems.add(0, null);
    categoryComboBox.setItems(categoryItems);

    ObservableList<String> monthItems =
        FXCollections.observableArrayList(null, "January", "February", "March", "April", "May",
            "June", "July", "August", "September", "October", "November", "December");
    monthComboBox.setItems(monthItems);
  }

  private static <T> List<T> sortList(Integer selectedYear, String selectedMonth,
                                      String selectedCategory, List<T> itemList,
                                      ToIntFunction<T> getYear,
                                      Function<T, Month> getMonth,
                                      Function<T, String> getCategory) {
    return itemList.stream()
        .filter(data -> (selectedYear == null || getYear.applyAsInt(data) == (selectedYear)))
        .filter(data -> (selectedMonth == null
            || getMonth.apply(data).equals(Month.valueOf(selectedMonth.toUpperCase()))))
        .filter(data -> (selectedCategory == null
            || Objects.equals(getCategory.apply(data), selectedCategory)))
        .toList();
  }

  /**
   * This function sorts a list of expenses based on the selected year, month, and category.
   *
   * @param selectedYear     The year selected by the user to filter the expense list.
   * @param selectedMonth    The selectedMonth parameter is a String variable that represents
   *                         the month
   *                         selected by the user. It is used as a filter to sort the expenseList
   *                         by month.
   * @param selectedCategory The selected category is a String variable that represents
   *                         the category of
   *                         expenses that the user has selected to filter the expense list.
   *                         For example, if the user selects
   *                         "Food" as the category, the method will return a sorted
   *                         list of expenses that belong to the "Food"
   *                         category.
   * @param expenseList      The expenseList parameter is a List of Expense objects.
   *                         It contains all the
   *                         expenses that need to be sorted based on the selectedYear,
   *                         selectedMonth, and selectedCategory.
   * @return The method `sortExpenseList` returns a sorted list of expenses based on the
   *         selected year,
   *         month, and category.
   *         The sorting is done using the `sortList` method, which takes
   *         in the selected
   *         year, month, and category, as well as the original expense list, and three lambda
   *         expressions that
   *         extract the year, month, and category from each expense object.
   */
  public static List<Expense> sortExpenseList(Integer selectedYear, String selectedMonth,
                                              String selectedCategory, List<Expense> expenseList) {
    return sortList(selectedYear, selectedMonth, selectedCategory, expenseList,
        expense -> expense.getDate().getYear(),
        expense -> expense.getDate().getMonth(),
        Expense::getCategory);
  }

  /**
   * This function sorts a list of income objects based on selected year, month, and category.
   *
   * @param selectedYear     The year selected by the user to filter the income list.
   * @param selectedMonth    The selectedMonth parameter is a String variable that represents the
   *                         month
   *                         selected by the user. It is used as a filter to sort the incomeList
   *                         by the selected month.
   * @param selectedCategory The selected category is a String variable that represents the category
   *                         of
   *                         income that the user has selected for sorting the income list.
   * @param incomeList       The list of Income objects that needs to be sorted.
   * @return The method `sortIncomeList` returns a sorted list of `Income` objects based on the
   *         selected year, month, and category.
   *         The sorting is done using the `sortList` method,
   *         which takes
   *         in the selected year, month, and category as well as the original `incomeList`
   *         and three lambda
   *         expressions that extract the year, month, and category from each `Income` object.
   */
  public static List<Income> sortIncomeList(Integer selectedYear, String selectedMonth,
                                            String selectedCategory, List<Income> incomeList) {
    return sortList(selectedYear, selectedMonth, selectedCategory, incomeList,
        income -> income.getDate().getYear(),
        income -> income.getDate().getMonth(),
        Income::getCategory);
  }
}
