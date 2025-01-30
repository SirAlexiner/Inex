package no.ntnu.idatg1002.grp8.inex.gui.stages;

import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import lombok.experimental.UtilityClass;

/**
 * The ExpenseStage class provides a method to get an FXMLLoader object for loading the
 * ExpenseView.fxml file with a customized title for the stage.
 */
@UtilityClass
public class ExpenseStage {

  /**
   * This function returns an FXMLLoader object for the ExpenseView.fxml file
   * with a customized title for the stage.
   *
   * @return The method `getFxmlLoader()` returns an instance of `FXMLLoader` class,
   *         which is used to load the FXML file for the ExpenseView.fxml.
   */
  public static FXMLLoader getFxmlLoader() {
    Stage stage = MainPageStage.getPrimaryStage();
    stage.setTitle("Inex - Browse/Add Expenses");
    return new FXMLLoader(InvoiceStage.class.getResource("/FXML/ExpenseView.fxml"));
  }
}
