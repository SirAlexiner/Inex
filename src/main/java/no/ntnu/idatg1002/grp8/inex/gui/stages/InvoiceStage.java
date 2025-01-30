package no.ntnu.idatg1002.grp8.inex.gui.stages;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.experimental.UtilityClass;
import no.ntnu.idatg1002.grp8.inex.gui.controllers.InvoiceController;

/**
 * The InvoiceStage class provides a method
 * to load a Node object from an FXML file for generating an
 * invoice.
 */
@UtilityClass
public class InvoiceStage {

  @Getter
  private static InvoiceController invoiceController;

  /**
   * This function returns a Node object loaded from an FXML file for generating an invoice.
   *
   * @return The method `getFxmlLoader()` returns a `Node` object which is loaded from the FXML file
   *         "GenerateInvoiceView.fxml".
   *         The FXML file is loaded using the `FXMLLoader` class,
   *         and the controller associated with the FXML file is obtained
   *         using the `getController()` method of the `FXMLLoader`class.
   */
  public static Node getFxmlLoader() throws IOException {
    Stage stage = MainPageStage.getPrimaryStage();
    stage.setTitle("Inex - Generate Invoice");
    FXMLLoader loader =
        new FXMLLoader(InvoiceStage.class.getResource("/FXML/GenerateInvoiceView.fxml"));
    Node node = loader.load();
    invoiceController = loader.getController();
    return node;
  }
}