package no.ntnu.idatg1002.grp8.inex.gui.controllers;

import io.github.siralexiner.fxmanager.FxManager;
import java.io.IOException;
import java.util.logging.Level;
import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import no.ntnu.idatg1002.grp8.inex.ErrorLogger;
import no.ntnu.idatg1002.grp8.inex.gui.stages.CompanyPerformanceStage;
import no.ntnu.idatg1002.grp8.inex.gui.stages.CustomerRegisterStage;
import no.ntnu.idatg1002.grp8.inex.gui.stages.ExpenseStage;
import no.ntnu.idatg1002.grp8.inex.gui.stages.FileHandlerStage;
import no.ntnu.idatg1002.grp8.inex.gui.stages.IncomeStage;
import no.ntnu.idatg1002.grp8.inex.gui.stages.InvoiceStage;
import no.ntnu.idatg1002.grp8.inex.gui.stages.MainPageStage;

/**
 * The `MainPageController` class contains methods for opening different stages
 * and toggling the color mode of the application.
 */
public class MainPageController {

  @FXML
  public StackPane root;

  /**
   * The function initializes by opening the company performance stage.
   */
  @FXML
  public void initialize() {
    openCompanyPerformanceStage();
  }

  /**
   * This function clears the children of a root node and adds an invoice stage to it.
   */
  @FXML
  public void openInvoiceStage() {
    try {
      root.getChildren().clear();
      root.getChildren().add(InvoiceStage.getFxmlLoader());
    } catch (IOException e) {
      ErrorLogger.getLOGGER().log(Level.SEVERE, "Unable to open Invoice FXML");
    }
  }

  /**
   * This function clears the children of a root node and adds a file handler stage to it.
   */
  @FXML
  public void openFileHandlerStage() {
    try {
      root.getChildren().clear();
      root.getChildren().add(FileHandlerStage.getFxmlLoader().load());
    } catch (IOException e) {
      ErrorLogger.getLOGGER().log(Level.SEVERE, "Unable to open File Handler FXML");
    }
  }

  /**
   * This function clears the children of a root node and adds a new node from an IncomeStage FXML
   * loader.
   */
  @FXML
  public void openIncomeStage() {
    try {
      root.getChildren().clear();
      root.getChildren().add(IncomeStage.getFxmlLoader().load());
    } catch (IOException e) {
      ErrorLogger.getLOGGER().log(Level.SEVERE, "Unable to open Income FXML");
    }
  }

  /**
   * This function clears the children of a root node and adds a new node
   * loaded from an ExpenseStage FXML file.
   */
  @FXML
  public void openExpenseStage() {
    try {
      root.getChildren().clear();
      root.getChildren().add(ExpenseStage.getFxmlLoader().load());
    } catch (IOException e) {
      ErrorLogger.getLOGGER().log(Level.SEVERE, "Unable to open Expense FXML");
    }
  }

  /**
   * This function clears the root node and loads the CompanyPerformanceStage FXML file into it.
   */
  @FXML
  public void openCompanyPerformanceStage() {
    try {
      root.getChildren().clear();
      root.getChildren().add(CompanyPerformanceStage.getFxmlLoader().load());
    } catch (IOException e) {
      ErrorLogger.getLOGGER().log(Level.SEVERE, "Unable to open Company Performance FXML");
    }
  }

  /**
   * This function clears the root node and adds the CustomerRegisterStage FXML loader to it.
   */
  @FXML
  public void openCustomerRegisterStage() {
    try {
      root.getChildren().clear();
      root.getChildren().add(CustomerRegisterStage.getFxmlLoader());
    } catch (IOException e) {
      ErrorLogger.getLOGGER().log(Level.SEVERE, "Unable to open Customer Register FXML");
    }
  }

  /**
   * This function toggles between light and dark mode in a JavaFX application.
   */
  @FXML
  public void toggleMode() {
    Stage stage = MainPageStage.getPrimaryStage();
    if (FxManager.isDark()) {
      FxManager.enableLightMode(stage);
    } else {
      FxManager.enableDarkMode(stage);
    }
  }
}
