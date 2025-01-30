package no.ntnu.idatg1002.grp8.inex.gui.stages;

import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import lombok.experimental.UtilityClass;

/**
 * The IncomeStage class provides a method to get an FXMLLoader object for the IncomeView.fxml file
 * with a customized title for the stage.
 */
@UtilityClass
public class IncomeStage {

  /**
   * This function returns an FXMLLoader object for the IncomeView.fxml file with a customized title
   * for the stage.
   *
   * @return The method `getFxmlLoader()` returns an instance of `FXMLLoader` class which is used to
   *         load the FXML file "IncomeView.fxml" for the "Inex - Browse/Add Income" stage.
   */
  public static FXMLLoader getFxmlLoader() {
    Stage stage = MainPageStage.getPrimaryStage();
    stage.setTitle("Inex - Browse/Add Income");
    return new FXMLLoader(InvoiceStage.class.getResource("/FXML/IncomeView.fxml"));
  }
}
