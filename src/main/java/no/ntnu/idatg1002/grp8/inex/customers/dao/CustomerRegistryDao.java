package no.ntnu.idatg1002.grp8.inex.customers.dao;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.List;
import java.util.logging.Level;
import lombok.experimental.UtilityClass;
import no.ntnu.idatg1002.grp8.inex.ErrorLogger;
import no.ntnu.idatg1002.grp8.inex.customers.Customer;
import no.ntnu.idatg1002.grp8.inex.customers.CustomerRegistry;

/**
 * The CustomerRegistryDao class uses the Gson library in Java to load and save a list of customers
 * to a JSON file.
 */
@UtilityClass
public class CustomerRegistryDao {

  private static CustomerRegistry registry;

  private static final String FILE_PATH = "cfg/json/customers.json";

  /**
   * This function loads a list of customers from a file using the Gson library in Java.
   *
   * @return The method is returning a list of customers loaded from a file.
   */
  public static List<Customer> loadCustomerRegister() {
    // Save the updated customer list to the file
    Gson gson = new Gson();
    JsonObject json = null;
    try (Reader reader = new FileReader(FILE_PATH)) {
      json = gson.fromJson(reader, JsonObject.class);
    } catch (IOException e) {
      ErrorLogger.getLOGGER().log(Level.SEVERE, "Unable to load Customers from JSON");
    }
    registry = gson.fromJson(json, CustomerRegistry.class);
    return registry.getCustomers();
  }

  /**
   * This function saves a list of customers to a JSON file using the Gson library in Java.
   */
  public static void saveCustomersToFile() {
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    JsonObject jsonObject = new JsonObject();
    jsonObject.add("customers", gson.toJsonTree(registry.getCustomers()));

    try (Writer writer = new FileWriter(FILE_PATH)) {
      gson.toJson(jsonObject, writer);
    } catch (IOException e) {
      ErrorLogger.getLOGGER().log(Level.SEVERE, "Unable to save Customers to JSON");
    }
  }
}
