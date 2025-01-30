package no.ntnu.idatg1002.grp8.inex.invoice;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import javafx.scene.control.Alert;
import lombok.experimental.UtilityClass;

/**
 * The OpenPdf class provides a function to open a PDF file using the default desktop application
 * and displays an error message if it is not supported.
 */
@UtilityClass
public class OpenPdf {
  /**
   * This Java function opens a PDF file using the default desktop application if it is supported,
   * and displays an error message if it is not.
   *
   * @param pdf The pdf parameter is a File object representing the PDF file
   *            that needs to be opened.
   */
  public static void openInvoice(File pdf) {
    if (pdf != null) {
      if (Desktop.isDesktopSupported()) {
        try {
          Desktop.getDesktop().open(pdf);
        } catch (IOException e) {
          showAlert("Error opening file", "Unable to open the file: " + e.getMessage());
        }
      } else {
        showAlert("Error", "Desktop is not supported on this platform.");
      }
    }
  }

  /**
   * This function displays an error alert with a given title and message.
   *
   * @param title   The title of the error alert that will be displayed to the user.
   * @param message The message parameter is a string that contains the text message to be displayed
   *                in the alert dialog. It is the main content of the alert
   *                and provides information to the user about
   *                the error or issue that has occurred.
   */
  private static void showAlert(String title, String message) {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.showAndWait();
  }
}