package no.ntnu.idatg1002.grp8.inex.gui.stages;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.experimental.UtilityClass;
import no.ntnu.idatg1002.grp8.inex.gui.controllers.CustomerController;

/**
 * The CustomerRegisterStage class loads a CustomerView.fxml file using an FXMLLoader and returns a
 * Node object.
 */
@UtilityClass
public class CustomerRegisterStage {

  @Getter
  private static CustomerController customerController;

  /**
   * This function returns a Node object that loads a CustomerView.fxml file using an FXMLLoader.
   *
   * @return The method is returning a Node object, which is the root node of the FXML file
   *         "CustomerView.fxml".
   */
  public static Node getFxmlLoader() throws IOException {
    Stage stage = MainPageStage.getPrimaryStage();
    stage.setTitle("Inex - Browse/Add Customers");
    FXMLLoader loader = new FXMLLoader(InvoiceStage.class.getResource("/FXML/CustomerView.fxml"));
    Node node = loader.load();
    customerController = loader.getController();
    return node;
  }
}
