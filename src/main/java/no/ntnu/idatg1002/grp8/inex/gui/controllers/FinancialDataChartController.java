package no.ntnu.idatg1002.grp8.inex.gui.controllers;

import java.time.LocalDate;
import java.time.Month;
import java.util.Comparator;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.chart.XYChart;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tooltip;
import no.ntnu.idatg1002.grp8.inex.financialdata.FinancialData;
import no.ntnu.idatg1002.grp8.inex.financialdata.FinancialInformation;
import no.ntnu.idatg1002.grp8.inex.financialdata.dao.FinancialInformationDao;

/**
 * The FinancialDataChartController class is responsible for initializing and updating a JavaFX line
 * chart displaying financial data based on user-selected filters.
 */
public class FinancialDataChartController {

  @FXML
  private XYChart<String, Number> lineChart;

  @FXML
  private ComboBox<Integer> yearComboBox;

  @FXML
  private ComboBox<String> monthComboBox;

  @FXML
  private CheckBox incomeCheckBox;

  @FXML
  private CheckBox expenseCheckBox;

  @FXML
  private CheckBox revenueCheckBox;

  private FinancialInformation financialInformation;

  /**
   * The function initializes financial information, loads data and plots a chart, and initializes
   * controls.
   */
  @FXML
  public void initialize() {
    financialInformation = FinancialInformationDao.loadFinancialInformation();
    loadDataAndPlotChart();
    initializeControls();
  }

  /**
   * This function updates the tooltips for a given XYChart series with the chart name, x-value, and
   * y-value for each data point.
   *
   * @param chart The parameter "chart" is an object of type XYChart.Series.
   *              It represents a series of data to be displayed on an XY chart,
   *               where the x-axis values are of type
   *              String and the y-axis values are of type Number.
   */
  private void updateTooltips(XYChart.Series<String, Number> chart) {
    for (XYChart.Data<String, Number> data : chart.getData()) {
      String chartName = chart.getName(); // Get the chart name
      Tooltip tooltip = new Tooltip(chartName + " - " + data.getXValue() + ": " + data.getYValue());
      Tooltip.install(data.getNode(), tooltip);
    }
  }

  /**
   * The function adds a vertical line to a JavaFX line chart to indicate the current date.
   *
   * @param today The parameter "today" is a LocalDate object representing the current date.
   */
  private void addTodayVerticalLine(LocalDate today) {
    XYChart.Series<String, Number> todaySeries = new XYChart.Series<>();
    todaySeries.setName("Today");

    double minY = lineChart.getData().stream()
        .flatMap(series -> series.getData().stream())
        .mapToDouble(data -> data.getYValue().doubleValue())
        .min()
        .orElse(0);

    double maxY = lineChart.getData().stream()
        .flatMap(series -> series.getData().stream())
        .mapToDouble(data -> data.getYValue().doubleValue())
        .max()
        .orElse(0);

    todaySeries.getData().add(new XYChart.Data<>(String.valueOf(today), minY));
    todaySeries.getData().add(new XYChart.Data<>(String.valueOf(today), maxY));

    lineChart.getData().add(todaySeries);

    for (XYChart.Data<String, Number> data : todaySeries.getData()) {
      Node node = data.getNode();
      node.setStyle(
          "-fx-background-color: #000; -fx-background-radius: 0; -fx-padding: 0 0.5 0 0.5;");
      node.toFront();
    }
  }

  /**
   * The function initializes combo boxes and check boxes.
   */
  private void initializeControls() {
    initializeComboBoxes();
    initializeCheckBoxes();
  }

  /**
   * The function initializes three checkboxes and sets their actions to load data and plot a chart
   * when clicked.
   */
  private void initializeCheckBoxes() {
    incomeCheckBox.setOnAction(event -> loadDataAndPlotChart());
    expenseCheckBox.setOnAction(event -> loadDataAndPlotChart());
    revenueCheckBox.setOnAction(event -> loadDataAndPlotChart());
  }

  /**
   * The function initializes two combo boxes for selecting a year and month, and sets their action
   * events to load data and plot a chart.
   */
  private void initializeComboBoxes() {
    List<Integer> years = financialInformation.getExpenses().stream()
        .map(data -> data.getDate().getYear())
        .sorted()
        .distinct()
        .toList();
    ObservableList<Integer> yearItems = FXCollections.observableArrayList(years);
    yearItems.add(0, null);
    yearComboBox.setItems(yearItems);

    ObservableList<String> monthItems =
        FXCollections.observableArrayList(null, "January", "February", "March", "April", "May",
            "June", "July", "August", "September", "October", "November", "December");
    monthComboBox.setItems(monthItems);

    yearComboBox.setOnAction(event -> loadDataAndPlotChart());
    monthComboBox.setOnAction(event -> loadDataAndPlotChart());
  }


  /**
   * This function loads financial data, filters it based on user-selected year and month, creates
   * series for income, expenses, and revenue, adds data to the series,
   * and updates the line chart with the selected series and tooltips.
   */
  private void loadDataAndPlotChart() {
    FinancialInformation information = FinancialInformationDao.loadFinancialInformation();

    Integer selectedYear = yearComboBox.getValue();
    String selectedMonth = monthComboBox.getValue();

    List<FinancialData> dataList = information.getAllData();
    dataList = dataList.stream()
        .filter(data -> (selectedYear == null || data.getDate().getYear() == selectedYear))
        .filter(data -> (selectedMonth == null
            || data.getDate().getMonth() == Month.valueOf(selectedMonth.toUpperCase())))
        .toList();

    XYChart.Series<String, Number> incomeSeries = new XYChart.Series<>();
    incomeSeries.setName("Income");

    XYChart.Series<String, Number> expensesSeries = new XYChart.Series<>();
    expensesSeries.setName("Expense");

    XYChart.Series<String, Number> revenueSeries = new XYChart.Series<>();
    revenueSeries.setName("Revenue");

    XYChart.Series<String, Number> todaySeries = new XYChart.Series<>();
    todaySeries.setName("Today");

    dataList = dataList.stream()
        .sorted(Comparator.comparing(FinancialData::getDate))
        .toList();

    for (FinancialData data : dataList) {
      LocalDate date = data.getDate();
      double income = data.getIncome();
      double expense = data.getExpense();
      double revenue = data.getRevenue();

      incomeSeries.getData().add(new XYChart.Data<>(String.valueOf(date), income));
      expensesSeries.getData().add(new XYChart.Data<>(String.valueOf(date), expense));
      revenueSeries.getData().add(new XYChart.Data<>(String.valueOf(date), revenue));
    }

    lineChart.getData().clear();

    if (incomeCheckBox.isSelected()) {
      lineChart.getData().add(incomeSeries);
      updateTooltips(incomeSeries);
    }

    if (expenseCheckBox.isSelected()) {
      lineChart.getData().add(expensesSeries);
      updateTooltips(expensesSeries);
    }

    if (revenueCheckBox.isSelected()) {
      lineChart.getData().add(revenueSeries);
      updateTooltips(revenueSeries);
    }

    addTodayVerticalLine(LocalDate.now());
  }
}
