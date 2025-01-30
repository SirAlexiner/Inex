package no.ntnu.idatg1002.grp8.inex.addressapi;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.github.siralexiner.fxmanager.FxManager;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lombok.experimental.UtilityClass;
import no.ntnu.idatg1002.grp8.inex.addressapi.json.Response;
import no.ntnu.idatg1002.grp8.inex.addressapi.json.Suggestion;
import no.ntnu.idatg1002.grp8.inex.gui.controllers.CustomerController;
import no.ntnu.idatg1002.grp8.inex.gui.controllers.InvoiceController;
import no.ntnu.idatg1002.grp8.inex.gui.controllers.util.ControllerUtility;
import no.ntnu.idatg1002.grp8.inex.gui.stages.CustomerRegisterStage;
import no.ntnu.idatg1002.grp8.inex.gui.stages.InvoiceStage;
import no.ntnu.idatg1002.grp8.inex.gui.stages.MainPageStage;
import no.ntnu.idatg1002.grp8.inex.utilities.AddressUtility;

/**
 * The AddressValidator class validates a postal address using the MyBring API
 * and displays suggestions for invalid addresses in a pop-up window.
 */
@UtilityClass
public class AddressValidator {

  private static Response jsonResponse;

  private static boolean generateInvoice;

  /**
   * This function validates a customer address using the Bring API and returns a boolean value
   * indicating whether the address is valid or not.
   *
   * @param address   The address parameter is a Map object that contains TextFields for various
   *                  address
   *                  fields such as customerStreet, customerHouseNumber, customerZipCode, etc.
   * @param isInvoice A boolean value indicating whether the address is for an invoice or not.
   * @return The method is returning a boolean value.
   *         It returns true if the address provided in the
   *         Map parameter is valid, according to the Bring API, and false otherwise.
   */
  public static boolean validAddress(Map<String, TextField> address, boolean isInvoice) {
    generateInvoice = isInvoice;
    Map<String, String> params = new HashMap<>();
    params.put("street_or_place", address.get("customerStreet").getText());
    if (address.get("customerHouseLetter").getText().isEmpty()) {
      params.put("street_number", address.get("customerHouseNumber").getText());
    } else if (address.get("customerHouseNumber").getText().isEmpty()) {
      params.put("letter", address.get("customerHouseLetter").getText());
    } else {
      params.put("street_number", address.get("customerHouseNumber").getText());
      params.put("letter", address.get("customerHouseLetter").getText());
    }
    params.put("address_type", "street");
    params.put("postal_code", address.get("customerZipCode").getText());
    params.put("city", address.get("customerCity").getText());
    params.put("municipality", address.get("customerMunicipality").getText());
    params.put("county", address.get("customerCounty").getText());

    String apiUid = "sir_alexiner@hotmail.com";
    String apiKey = "b1fbb72f-f91d-4cfa-9375-20dcae11e650";

    Map<String, String> headers = new HashMap<>();
    headers.put("X-MyBring-API-Uid", apiUid);
    headers.put("X-MyBring-API-Key", apiKey);

    String endpoint = "https://api.bring.com/address/api/no/validation";

    String response = HttpConnection.sendGetRequest(endpoint, headers, params);

    if (response.startsWith("{")) {
      JsonObject json = JsonParser.parseString(response).getAsJsonObject();
      Gson gson = new Gson();
      jsonResponse = gson.fromJson(json, Response.class);
      if (jsonResponse.isValid()) {
        return true;
      } else {
        open();
        return false;
      }
    } else {
      return false;
    }
  }

  /**
   * This function creates a pop-up window that displays a table of address suggestions
   * and allows the user to select an address to populate a form.
   */
  private static void open() {
    Stage stage = setupStage();
    TableView<Suggestion> suggestionsTable = setupSuggestionsTable();
    BorderPane borderPane = setupBorderPane(suggestionsTable);

    suggestionsTable.setOnMouseClicked(event -> {
      if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
        handleTableClick(suggestionsTable, stage);
      }
    });

    Scene scene = new Scene(borderPane);
    stage.setTitle("Inex - Address Suggestions");
    Image logo = new Image(String.valueOf(InvoiceStage.class.getResource("/images/logo.png")));
    stage.getIcons().addAll(logo);
    stage.setScene(scene);
    stage.show();
  }

  private static Stage setupStage() {
    Stage stage = new Stage();
    FxManager.setupCaptionBar(stage);
    stage.initModality(Modality.APPLICATION_MODAL);
    stage.initOwner(MainPageStage.getPrimaryStage());
    return stage;
  }

  private static TableView<Suggestion> setupSuggestionsTable() {
    List<TableColumn<Suggestion, String>> columns = Arrays.asList(
        createColumn("Street Name", "street_name"),
        createColumn("House Number", "house_number"),
        createColumn("Letter", "letter"),
        createColumn("Postal Code", "postal_code"),
        createColumn("City", "city"),
        createColumn("County", "county"),
        createColumn("Municipality", "municipality")
    );

    TableView<Suggestion> suggestionsTable = new TableView<>();
    suggestionsTable.getColumns().setAll(columns);
    ObservableList<Suggestion> suggestions =
        FXCollections.observableArrayList(jsonResponse.getSuggestions());
    suggestionsTable.setItems(suggestions);
    return suggestionsTable;
  }

  private static TableColumn<Suggestion, String> createColumn(String columnName,
                                                              String propertyName) {
    TableColumn<Suggestion, String> column = new TableColumn<>(columnName);
    column.setCellValueFactory(new PropertyValueFactory<>(propertyName));
    return column;
  }

  private static BorderPane setupBorderPane(TableView<Suggestion> suggestionsTable) {
    BorderPane borderPane = new BorderPane();
    VBox suggestionsvbox = new VBox(suggestionsTable);
    borderPane.setCenter(suggestionsvbox);
    return borderPane;
  }

  private static void handleTableClick(TableView<Suggestion> suggestionsTable, Stage stage) {
    Suggestion suggestion = suggestionsTable.getSelectionModel().getSelectedItem();
    if (suggestion != null) {
      setAddressFields(suggestion);
      stage.close();
    }
  }

  private static void setAddressFields(Suggestion suggestion) {
    Map<String, String> address = AddressUtility.getAddress(suggestion);

    if (generateInvoice) {
      InvoiceController controller = InvoiceStage.getInvoiceController();
      ControllerUtility.setAddress(address, controller);
    } else {
      CustomerController controller = CustomerRegisterStage.getCustomerController();
      ControllerUtility.setAddress(address, controller);
    }
  }
}