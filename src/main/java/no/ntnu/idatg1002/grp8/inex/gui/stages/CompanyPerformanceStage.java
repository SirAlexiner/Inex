package no.ntnu.idatg1002.grp8.inex.gui.stages;

import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import lombok.experimental.UtilityClass;

/**
 * The CompanyPerformanceStage class provides a function to get an FXMLLoader object for the
 * FinancialDataChartView.fxml file with a specified title for the stage.
 */
@UtilityClass
public class CompanyPerformanceStage {

  /**
   * This function returns an FXMLLoader object for the FinancialDataChartView.fxml
   * file with a specified title for the stage.
   *
   * @return A new instance of FXMLLoader class with the specified FXML file path
   *         "/FXML/FinancialDataChartView.fxml" being returned.
   */
  public static FXMLLoader getFxmlLoader() {
    Stage stage = MainPageStage.getPrimaryStage();
    stage.setTitle("Inex - Company Performance");
    return new FXMLLoader(InvoiceStage.class.getResource("/FXML/FinancialDataChartView.fxml"));
  }
}
