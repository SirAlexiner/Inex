package no.ntnu.idatg1002.grp8.inex.gui.stages;

import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import lombok.experimental.UtilityClass;

/**
 * The FileHandlerStage class provides a function to return a new FXMLLoader object for browsing
 * invoices in the Inex application.
 */
@UtilityClass
public class FileHandlerStage {

  /**
   * This function returns a new FXMLLoader object with a specified FXML file resource for browsing
   * invoices in the Inex application.
   *
   * @return A `FXMLLoader` object is being returned.
   */
  public static FXMLLoader getFxmlLoader() {
    Stage stage = MainPageStage.getPrimaryStage();
    stage.setTitle("Inex - Browse Invoices");
    return new FXMLLoader(InvoiceStage.class.getResource("/FXML/InvoiceFileHandlerView.fxml"));
  }
}
