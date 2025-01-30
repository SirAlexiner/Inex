package no.ntnu.idatg1002.grp8.inex.utilities;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.scene.control.TextField;
import lombok.experimental.UtilityClass;
import no.ntnu.idatg1002.grp8.inex.addressapi.json.Suggestion;
import no.ntnu.idatg1002.grp8.inex.customers.Customer;

/**
 * The AddressUtility class provides methods to extract and populate address information
 * from different types of objects.
 */
@UtilityClass
public class AddressUtility {

  /**
   * The function takes a suggestion object and returns a map of address components.
   *
   * @param suggestion The parameter "suggestion" is an object of the class "Suggestion".
   *                  It contains information about a suggested address, such as the street name,
   *                  house number, letter, postal code, city, county, and municipality.
   * @return A `Map` object containing the address information extracted from the `Suggestion`
   *         object passed as a parameter. The keys of the map are strings representing
   *         the different parts of the address (street name, house number, letter, postal code,
   *         city, county, and municipality), and the
   *         values are the corresponding values extracted from the `Suggestion` object.
   */
  public static Map<String, String> getAddress(Suggestion suggestion) {
    String streetName = suggestion.getStreet_name() != null ? suggestion.getStreet_name() : "";
    String houseNumber = suggestion.getHouse_number() != null ? suggestion.getHouse_number() : "";
    String letter = suggestion.getLetter() != null ? suggestion.getLetter() : "";
    String postalCode = suggestion.getPostal_code() != null ? suggestion.getPostal_code() : "";
    return getStringStringMap(streetName, houseNumber, letter, postalCode, suggestion.getCity(),
        suggestion.getCounty(), suggestion.getMunicipality());
  }

  /**
   * This Java function returns a map of address information for a given customer,
   * with default values for any missing fields.
   *
   * @param customer The parameter "customer" is an object of the class "Customer" which contains
   *                 information about a customer, such as their name, address, and contact details.
   *                 The method "getAddress" takes this customer object as input and returns a map
   *                 containing the customer's address information.
   * @return A 'Map' is being returned.
   */
  public static Map<String, String> getAddress(Customer customer) {
    String streetName = customer.getStreet() != null ? customer.getStreet() : "";
    String houseNumber = customer.getHouseNumber() != null ? customer.getHouseNumber() : "";
    String letter = customer.getHouseLetter() != null ? customer.getHouseLetter() : "";
    String postalCode = customer.getZipCode() != null ? customer.getZipCode() : "";
    return getStringStringMap(streetName, houseNumber, letter, postalCode, customer.getCity(),
        customer.getCounty(), customer.getMunicipality());
  }

  /**
   * The function returns a map of text fields representing a customer's address.
   *
   * @param customerStreet       A TextField object representing the street address of a customer.
   * @param customerHouseNumber  A TextField object that represents the house number
   *                             of the customer's address.
   * @param customerHouseLetter  This parameter is a TextField that represents the letter associated
   *                             with the house number in the customer's address. For example,
   *                             if the customer's address is "123A
   *                             Main Street", the value of this parameter would be a TextField
   *                             containing the letter "A". If there
   *                             is no letter associated with the house number, it should be empty.
   * @param customerZipCode      A TextField object representing the zip code of the customer's
   *                             address.
   * @param customerCity         A TextField object representing the city of the customer's address.
   * @param customerMunicipality A TextField object representing the municipality of the customer's
   *                             address.
   * @param customerCounty       A TextField object representing the county of the customer's
   *                             address.
   * @return The method is returning a Map object that contains the address information of a
   *         customer. The keys of the map are strings representing the different parts
   *         of the address (e.g."customerStreet", "customerZipCode"),
   *         and the values are TextField objects that contain
   *         the corresponding information entered by the user.
   */
  public Map<String, TextField> getAddress(TextField customerStreet, TextField customerHouseNumber,
                                           TextField customerHouseLetter, TextField customerZipCode,
                                           TextField customerCity, TextField customerMunicipality,
                                           TextField customerCounty) {
    Map<String, TextField> address = new HashMap<>();
    populateAddress(address,
        List.of("customerStreet", "customerHouseNumber", "customerHouseLetter",
            "customerZipCode", "customerCity", "customerMunicipality", "customerCounty"),
        List.of(customerStreet, customerHouseNumber, customerHouseLetter,
            customerZipCode, customerCity, customerCounty, customerMunicipality));
    return address;
  }

  private static Map<String, String> getStringStringMap(String streetName, String houseNumber,
                                                        String letter, String postalCode,
                                                        String city2, String county2,
                                                        String municipality2) {
    String city = city2 != null ? city2 : "";
    String county = county2 != null ? county2 : "";
    String municipality = municipality2 != null ? municipality2 : "";

    Map<String, String> address = new HashMap<>();
    populateAddress(address,
        List.of("customerStreet", "customerHouseNumber", "customerHouseLetter",
            "customerZipCode", "customerCity", "customerMunicipality", "customerCounty"),
        List.of(streetName, houseNumber, letter,
            postalCode, city, county, municipality));
    return address;
  }

  private static <T> void populateAddress(Map<String, T> address, List<String> keys,
                                          List<T> values) {
    for (int i = 0; i < keys.size(); i++) {
      address.put(keys.get(i), values.get(i));
    }
  }
}
