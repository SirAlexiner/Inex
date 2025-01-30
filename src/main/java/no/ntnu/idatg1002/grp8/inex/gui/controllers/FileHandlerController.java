package no.ntnu.idatg1002.grp8.inex.gui.controllers;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Comparator;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

/**
 * The FileHandlerController class is a JavaFX controller that loads and displays PDF files from a
 * specified folder allows the user to open and delete files, and displays error messages if
 * necessary.
 */
public class FileHandlerController implements Initializable {

  @FXML
  public Button deleteButton;
  @FXML
  private ListView<File> fileListView;

  private static final String PDF_FOLDER = "cfg/pdf/generated";

  /**
   * This function initializes the file list view and sets a double click event to open a selected
   * file.
   *
   * @param location  The location parameter is a URL object that represents the location
   *                  of the FXML
   *                  file that contains the root node of the scene graph.
   *                  It is typically used to load additional
   *                  resources or data that are required by the controller.
   * @param resources The `resources` parameter in the `initialize` method is a `ResourceBundle`
   *                  object
   *                  that contains any resources (such as strings, images, or other files)
   *                  that may be needed by the
   *                  controller. It is typically used to load localized resources for
   *                  internationalization purposes.
   */
  @FXML
  public void initialize(URL location, ResourceBundle resources) {
    loadPdfFiles();

    fileListView.setOnMouseClicked(event -> {
      if (event.getClickCount() == 2) {
        handleOpenFile();
      }
    });
  }

  /**
   * The function loads all PDF files from a specified folder, sorts them by a number extracted from
   * their names, and adds them to a file list view.
   */
  private void loadPdfFiles() {
    File pdfFolder = new File(PDF_FOLDER);
    File[] files = pdfFolder.listFiles((dir, name) -> name.endsWith(".pdf"));

    if (files != null) {
      Arrays.sort(files, Comparator.comparingInt(FileHandlerController::extractNumberFromFileName));
      fileListView.getItems().clear();
      fileListView.getItems().addAll(files);
    }
  }

  /**
   * This Java function extracts a number from a file name using regular expressions.
   *
   * @param file The "file" parameter is an instance of the File class, which represents a file or
   *             directory path in the file system. The method "extractNumberFromFileName"
   *             takes a File object as
   *             input and extracts a number from its name using regular expressions.
   * @return The method `extractNumberFromFileName` returns an integer value.
   *         If a number is found in the file name,
   *         it returns that number as an integer. If no number is found, it returns -1.
   */
  private static int extractNumberFromFileName(File file) {
    Pattern pattern = Pattern.compile("\\d+");
    Matcher matcher = pattern.matcher(file.getName());
    if (matcher.find()) {
      return Integer.parseInt(matcher.group());
    }
    return -1;
  }

  @FXML
  private void handleOpenFile() {
    File selectedFile = fileListView.getSelectionModel().getSelectedItem();
    if (selectedFile != null) {
      if (Desktop.isDesktopSupported()) {
        try {
          Desktop.getDesktop().open(selectedFile);
        } catch (IOException e) {
          showAlert("Error opening file", "Unable to open the file: " + e.getMessage());
        }
      } else {
        showAlert("Error", "Desktop is not supported on this platform.");
      }
    }
  }

  @FXML
  private void handleDeleteFile() {
    File selectedFile = fileListView.getSelectionModel().getSelectedItem();
    if (selectedFile != null) {
      try {
        Files.delete(selectedFile.toPath());
        loadPdfFiles();
      } catch (IOException e) {
        showAlert("Error deleting file", "Unable to delete the file: " + e.getMessage());
      }
    }
  }

  /**
   * This function displays an error alert with a given title and message.
   *
   * @param title   The title of the error alert that will be displayed to the user.
   *                It should briefly
   *                describe the nature of the error.
   * @param message The message parameter is a string that contains the text to be displayed in the
   *                content area of the alert dialog.
   *                It typically contains information about an error or warning that
   *                has occurred in the application.
   */
  private void showAlert(String title, String message) {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.showAndWait();
  }
}
