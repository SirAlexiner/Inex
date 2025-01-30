package no.ntnu.idatg1002.grp8.inex.gui.stages;

import io.github.siralexiner.fxmanager.FxManager;
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;

/**
 * The `MainPageStage` class sets up the primary stage of a JavaFX application, loads the FXML file
 * for the main page, and displays the stage.
 */
public class MainPageStage extends Application {

  @Getter
  @Setter
  private static Stage primaryStage;


  /**
   * The start method is a method that is called when the JavaFX
   * application is launched. It sets up the primary stage, loads the FXML file for the main page,
   * sets the scene, and displays the stage.
   * The `Stage` parameter represents the primary stage of the application.
   *
   * @param stage the primary stage for this application, onto which
   *              the application scene can be set.
   *              Applications may create other stages, if needed, but they will not be
   *              primary stages.
   * @throws IOException an `IOException` may be thrown if there is an error loading the FXML file.
   */
  @Override
  public void start(Stage stage) throws IOException {
    setPrimaryStage(stage);
    FxManager.setup(stage);
    stage.setOnCloseRequest(event -> System.exit(0));
    Image logo =
        new Image(String.valueOf(InvoiceStage.class.getResource("/images/logo.png")));
    stage.getIcons().addAll(logo);
    stage.setResizable(false);
    FXMLLoader fxmlLoader =
        new FXMLLoader(InvoiceStage.class.getResource("/FXML/MainPageView.fxml"));
    Scene scene = new Scene(fxmlLoader.load());
    scene.setOnMouseClicked(mouseEvent -> scene.getRoot().requestFocus());
    stage.setScene(scene);
    stage.show();
  }

  /**
   * This function starts the JavaFX application by launching it with the given arguments.
   */
  public static void startApplication(String[] args) {
    launch(args);
  }
}