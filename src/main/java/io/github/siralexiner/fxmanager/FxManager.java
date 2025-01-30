package io.github.siralexiner.fxmanager;

import atlantafx.base.theme.PrimerDark;
import atlantafx.base.theme.PrimerLight;
import com.jthemedetecor.OsThemeDetector;
import io.github.mimoguz.customwindow.StageOps;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.experimental.UtilityClass;

/**
 * The FxManager class provides methods for enabling light or dark mode for a JavaFX application and
 * customize the window handle's caption and border colors.
 */
@UtilityClass
public class FxManager {

  @Getter
  private static boolean isDark;

  private static final OsThemeDetector detector = OsThemeDetector.getDetector();

  /**
   * This function enables light mode for a Java application by setting the user agent stylesheet
   * and adjusting the window handle's caption and border colors.
   *
   * @param stage The stage parameter is an instance of the JavaFX Stage class, which represents the
   *              top-level container for a JavaFX application.
   *              It is used to manage the application window and its
   *              contents. In this code,
   *              the stage parameter is used to set the user agent stylesheet to a
   *              light theme and to change the window handle's caption and border colors.
   */
  public static void enableLightMode(Stage stage) {
    Application.setUserAgentStylesheet(new PrimerLight().getUserAgentStylesheet());
    isDark = false;
    Platform.runLater(() -> {
      final var handle = StageOps.findWindowHandle(stage);
      StageOps.setCaptionColor(handle, Color.rgb(255, 255, 255));
      StageOps.setBorderColor(handle, Color.rgb(225, 225, 225));
    });
  }

  /**
   * This function enables dark mode for a Java application by setting the user agent stylesheet and
   * changing the window handle's caption and border color.
   *
   * @param stage The stage parameter is an instance of the JavaFX Stage class, which represents the
   *              top-level container for a JavaFX application.
   *              It is used to display the user interface of the
   *              application and manage its lifecycle. In this code,
   *              the stage parameter is used to set the
   *              user agent stylesheet and customize the window handle's caption and border colors.
   */
  public static void enableDarkMode(Stage stage) {
    Application.setUserAgentStylesheet(new PrimerDark().getUserAgentStylesheet());
    isDark = true;
    Platform.runLater(() -> {
      final var handle = StageOps.findWindowHandle(stage);
      StageOps.setCaptionColor(handle, Color.rgb(13, 17, 23));
      StageOps.setBorderColor(handle, Color.rgb(13, 17, 23));
    });
  }

  /**
   * The function sets up the stage to either enable dark mode or light mode
   * based on the result of the detector's isDark method.
   *
   * @param stage The "stage" parameter is an instance of the JavaFX Stage class,
   *              which represents the
   *              main window of a JavaFX application.
   *              It is used to set up the initial appearance of the
   *              application, including the layout, scene, and style. In this code,
   *              the "setup" method takes a stage.
   */
  public static void setup(Stage stage) {
    isDark = detector.isDark();
    if (isDark) {
      enableDarkMode(stage);
    } else {
      enableLightMode(stage);
    }
  }

  /**
   * This function sets the color of the caption bar and border of a JavaFX stage based on a boolean
   * value.
   *
   * @param stage The stage is a top-level container that represents a window
   *              in a JavaFX application.
   *              It contains all the UI elements that are displayed in the window.
   *               In this method, the stage
   *              parameter is used to set the color of the caption bar and border
   *              of the window based on the value of the isDark boolean.
   */
  public static void setupCaptionBar(Stage stage) {
    if (isDark) {
      Platform.runLater(() -> {
        final var handle = StageOps.findWindowHandle(stage);
        StageOps.setCaptionColor(handle, Color.rgb(13, 17, 23));
        StageOps.setBorderColor(handle, Color.rgb(13, 17, 23));
      });
    } else {
      Platform.runLater(() -> {
        final var handle = StageOps.findWindowHandle(stage);
        StageOps.setCaptionColor(handle, Color.rgb(255, 255, 255));
        StageOps.setBorderColor(handle, Color.rgb(225, 225, 225));
      });
    }
  }
}
