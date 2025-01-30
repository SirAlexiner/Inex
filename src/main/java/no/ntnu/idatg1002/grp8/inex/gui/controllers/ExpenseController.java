package no.ntnu.idatg1002.grp8.inex.gui.controllers;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import no.ntnu.idatg1002.grp8.inex.financialdata.Expense;
import no.ntnu.idatg1002.grp8.inex.financialdata.FinancialInformation;
import no.ntnu.idatg1002.grp8.inex.financialdata.Revenue;
import no.ntnu.idatg1002.grp8.inex.financialdata.dao.FinancialInformationDao;
import no.ntnu.idatg1002.grp8.inex.financialdata.util.RevenueUtility;
import no.ntnu.idatg1002.grp8.inex.gui.controllers.util.ControllerUtility;

/**
 * The ExpenseController class is responsible for initializing and managing the GUI components and
 * event handlers for adding, deleting, and filtering expenses
 * in a financial management application.
 */
public class ExpenseController {

  @FXML
  public ListView<Expense> expenseListView;
  @FXML
  public DatePicker expenseDate;
  @FXML
  public TextField expenseDescription;
  @FXML
  public TextField expenseCategory;
  @FXML
  public TextField expensePrice;
  @FXML
  public ComboBox<Integer> yearComboBox;
  @FXML
  public ComboBox<String> monthComboBox;
  @FXML
  public ComboBox<String> categoryComboBox;
  private FinancialInformation information;
  private List<Expense> expensesList;
  private FinancialInformation financialInformation;


  @FXML
  protected void initialize() {
    financialInformation = FinancialInformationDao.loadFinancialInformation();
    initializeComboBoxes();
    expenseDate.setValue(LocalDate.now());
    information = FinancialInformationDao.loadFinancialInformation();
    expensesList = information.getExpenses();
    AtomicReference<ObservableList<Expense>> observableList =
        new AtomicReference<>(FXCollections.observableArrayList(expensesList));
    expenseListView.setItems(observableList.get());

    ContextMenu contextMenu = new ContextMenu();
    MenuItem deleteItem = new MenuItem("Delete");
    contextMenu.getItems().add(deleteItem);

    expenseListView.setOnContextMenuRequested(event ->
        contextMenu.show(expenseListView, event.getScreenX(), event.getScreenY()));

    deleteItem.setOnAction(event -> {
      Expense selectedItem = expenseListView.getSelectionModel().getSelectedItem();
      if (selectedItem != null) {
        expensesList.remove(selectedItem);
        observableList.set(FXCollections.observableArrayList(expensesList));
        expenseListView.setItems(observableList.get());
        Revenue newRevenue =
            new Revenue(selectedItem.getDate().toString(), selectedItem.getExpenseData());
        RevenueUtility.updateRevenueExpenseRemoved(information.getRevenues(), newRevenue);
        FinancialInformationDao.saveFinancialInformationToFile();
        Platform.runLater(this::initialize);
      }
    });
  }

  /**
   * The function initializes three combo boxes with available years, categories, and months,
   * and sets event handlers to load expense data.
   */
  private void initializeComboBoxes() {
    // Initialize yearComboBox with available years from the data
    List<Integer> years = financialInformation.getExpenses().stream()
        .map(data -> data.getDate().getYear())
        .sorted()
        .distinct()
        .toList();
    ObservableList<Integer> yearItems = FXCollections.observableArrayList(years);
    yearItems.add(0, null);
    yearComboBox.setItems(yearItems);

    List<String> categories = financialInformation.getExpenses().stream()
        .map(Expense::getCategory)
        .sorted()
        .distinct()
        .toList();
    ControllerUtility.populateComboBoxes(categories, categoryComboBox, monthComboBox);

    // Set event handlers
    yearComboBox.setOnAction(event -> loadExpenseData());
    monthComboBox.setOnAction(event -> loadExpenseData());
    categoryComboBox.setOnAction(event -> loadExpenseData());
  }

  /**
   * This function filters and loads expense data based on selected year, month, and category.
   */
  private void loadExpenseData() {
    Integer selectedYear = yearComboBox.getValue();
    String selectedMonth = monthComboBox.getValue();
    String selectedCategory = categoryComboBox.getValue();

    List<Expense> expenseList = information.getExpenses();
    expenseList =
        ControllerUtility.sortExpenseList(selectedYear, selectedMonth, selectedCategory,
            expenseList);
    ObservableList<Expense> observableList =
        FXCollections.observableArrayList(expenseList);
    expenseListView.setItems(observableList);
  }

  /**
   * `Public void addExpense()` is a method in the `ExpenseController` class that is called when the
   * user clicks the "Add Expense" button in the GUI.
   * It creates a new `Expense` object with the data
   * entered by the user in the date picker, price text field, description text field,
   * and category text field.
   * It then adds the new `Expense` object to the list of expenses in the `expensesList`
   * variable, updates the revenue data based on the new expense,
   * saves the financial information to
   * the data file, and refreshes the GUI by calling the `initialize()` method.
   */
  @FXML
  public void addExpense() {
    expensesList.add(
        new Expense(expenseDate.getValue().toString(), Double.parseDouble(expensePrice.getText()),
            expenseDescription.getText(), expenseCategory.getText()));
    Revenue newRevenue =
        new Revenue(expenseDate.getValue().toString(), Double.parseDouble(expensePrice.getText()));
    RevenueUtility.updateRevenueExpenseAdded(information.getRevenues(), newRevenue);
    ObservableList<Expense> observableList =
        FXCollections.observableArrayList(expensesList);
    expenseListView.setItems(observableList);
    FinancialInformationDao.saveFinancialInformationToFile();
    Platform.runLater(this::initialize);
  }
}
