package no.ntnu.idatg1002.grp8.inex;

import no.ntnu.idatg1002.grp8.inex.gui.stages.MainPageStage;

/**
 * The InexApplication class starts the MainPageStage application in Java.
 */
public class InexApplication {
  /**
   * This is the main function that starts the application.
   */
  public static void main(String[] args) {
    ErrorLogger errorLogger = new ErrorLogger();
    errorLogger.configureLogger("ErrorLog.log", "./cfg/logs");
    MainPageStage.startApplication(args);
  }
}
