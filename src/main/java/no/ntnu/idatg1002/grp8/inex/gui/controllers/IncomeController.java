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
import no.ntnu.idatg1002.grp8.inex.financialdata.FinancialInformation;
import no.ntnu.idatg1002.grp8.inex.financialdata.Income;
import no.ntnu.idatg1002.grp8.inex.financialdata.Revenue;
import no.ntnu.idatg1002.grp8.inex.financialdata.dao.FinancialInformationDao;
import no.ntnu.idatg1002.grp8.inex.financialdata.util.RevenueUtility;
import no.ntnu.idatg1002.grp8.inex.gui.controllers.util.ControllerUtility;

/**
 * The `IncomeController` class is responsible for managing the GUI components
 * and actions related to
 * adding, deleting, and filtering income data in a financial management application.
 */
public class IncomeController {

  @FXML
  public ListView<Income> incomeListView;
  @FXML
  public DatePicker incomeDate;
  @FXML
  public TextField incomeDescription;
  @FXML
  public TextField incomeCategory;
  @FXML
  public TextField incomePrice;
  @FXML
  public ComboBox<Integer> yearComboBox;
  @FXML
  public ComboBox<String> monthComboBox;
  @FXML
  public ComboBox<String> categoryComboBox;
  private FinancialInformation information;
  private List<Income> incomeList;
  private FinancialInformation financialInformation;


  @FXML
  protected void initialize() {
    financialInformation = FinancialInformationDao.loadFinancialInformation();
    initializeComboBoxes();
    incomeDate.setValue(LocalDate.now());
    information = FinancialInformationDao.loadFinancialInformation();
    incomeList = information.getIncomes();
    AtomicReference<ObservableList<Income>> observableList =
        new AtomicReference<>(FXCollections.observableArrayList(incomeList));
    incomeListView.setItems(observableList.get());

    ContextMenu contextMenu = new ContextMenu();
    MenuItem deleteItem = new MenuItem("Delete");
    contextMenu.getItems().add(deleteItem);

    incomeListView.setOnContextMenuRequested(event ->
        contextMenu.show(incomeListView, event.getScreenX(), event.getScreenY()));

    deleteItem.setOnAction(event -> {
      Income selectedItem = incomeListView.getSelectionModel().getSelectedItem();
      if (selectedItem != null) {
        incomeList.remove(selectedItem);
        observableList.set(FXCollections.observableArrayList(incomeList));
        incomeListView.setItems(observableList.get());
        Revenue newRevenue =
            new Revenue(selectedItem.getDate().toString(), selectedItem.getIncomeData());
        RevenueUtility.updateRevenueIncomeRemoved(information.getRevenues(), newRevenue);
        FinancialInformationDao.saveFinancialInformationToFile();
        Platform.runLater(this::initialize);
      }
    });
  }

  /**
   * The function initializes and populates three combo boxes for year, category, and month,
   * and sets actions to load income data based on the selected values.
   */
  private void initializeComboBoxes() {
    List<Integer> years = financialInformation.getIncomes().stream()
        .map(data -> data.getDate().getYear())
        .sorted()
        .distinct()
        .toList();
    ObservableList<Integer> yearItems = FXCollections.observableArrayList(years);
    yearItems.add(0, null);
    yearComboBox.setItems(yearItems);

    List<String> categories = financialInformation.getIncomes().stream()
        .map(Income::getCategory)
        .sorted()
        .distinct()
        .toList();
    ControllerUtility.populateComboBoxes(categories, categoryComboBox, monthComboBox);

    yearComboBox.setOnAction(event -> loadIncomeData());
    monthComboBox.setOnAction(event -> loadIncomeData());
    categoryComboBox.setOnAction(event -> loadIncomeData());
  }


  /**
   * The function loads income data based on selected year, month, and category filters
   * and displays it in a list view.
   */
  private void loadIncomeData() {
    Integer selectedYear = yearComboBox.getValue();
    String selectedMonth = monthComboBox.getValue();
    String selectedCategory = categoryComboBox.getValue();

    List<Income> informationIncomes = information.getIncomes();
    informationIncomes =
        ControllerUtility.sortIncomeList(selectedYear, selectedMonth, selectedCategory,
            informationIncomes);
    ObservableList<Income> observableList =
        FXCollections.observableArrayList(informationIncomes);
    incomeListView.setItems(observableList);
  }

  /**
   * This function adds a new income to a list and updates the revenue information accordingly.
   */
  @FXML
  public void addIncome() {
    incomeList.add(
        new Income(incomeDate.getValue().toString(), Double.parseDouble(incomePrice.getText()),
            incomeDescription.getText(), incomeCategory.getText()));
    Revenue newRevenue =
        new Revenue(incomeDate.getValue().toString(), Double.parseDouble(incomePrice.getText()));
    RevenueUtility.updateRevenueIncomeAdded(information.getRevenues(), newRevenue);
    ObservableList<Income> observableList =
        FXCollections.observableArrayList(incomeList);
    incomeListView.setItems(observableList);
    FinancialInformationDao.saveFinancialInformationToFile();
    Platform.runLater(this::initialize);
  }
}
