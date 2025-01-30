package no.ntnu.idatg1002.grp8.inex.financialdata.dao;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.logging.Level;
import lombok.experimental.UtilityClass;
import no.ntnu.idatg1002.grp8.inex.ErrorLogger;
import no.ntnu.idatg1002.grp8.inex.financialdata.FinancialInformation;

/**
 * The FinancialInformationDao class uses the Gson library in Java to load and save financial
 * information to a file.
 */
@UtilityClass
public class FinancialInformationDao {

  private static final String FILE_PATH = "cfg/json/financial_data.json";

  private static FinancialInformation information;

  /**
   * This function loads financial information from a file using the Gson library in Java.
   *
   * @return The method is returning an object of type FinancialInformation.
   */
  public static FinancialInformation loadFinancialInformation() {
    // Save the updated customer list to the file
    Gson gson = new Gson();
    JsonObject json = null;
    try (Reader reader = new FileReader(FILE_PATH)) {
      json = gson.fromJson(reader, JsonObject.class);
    } catch (IOException e) {
      ErrorLogger.getLOGGER().log(Level.SEVERE, "Unable to load Financial Information from JSON");
    }
    information = gson.fromJson(json, FinancialInformation.class);
    return information;
  }

  /**
   * This Java function saves financial information to a file using the Gson library.
   */
  public static void saveFinancialInformationToFile() {
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    JsonObject jsonObject = new JsonObject();
    jsonObject.add("incomes", gson.toJsonTree(information.getIncomes()));
    jsonObject.add("expenses", gson.toJsonTree(information.getExpenses()));
    jsonObject.add("revenues", gson.toJsonTree(information.getRevenues()));

    try (Writer writer = new FileWriter(FILE_PATH)) {
      gson.toJson(jsonObject, writer);
    } catch (IOException e) {
      ErrorLogger.getLOGGER().log(Level.SEVERE, "Unable to save Financial Information from JSON");
    }
  }
}
