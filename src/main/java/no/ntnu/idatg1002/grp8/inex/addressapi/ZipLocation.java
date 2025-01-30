package no.ntnu.idatg1002.grp8.inex.addressapi;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.util.HashMap;
import java.util.Map;
import lombok.experimental.UtilityClass;

/**
 * The ZipLocation class provides a method for retrieving the city, municipality, and county of a
 * location based on a provided zip code using the Mybring API.
 */
@UtilityClass
public class ZipLocation {
  /**
   * The function takes a zip code as input, sends an API request to retrieve information about the
   * corresponding location, and returns the city, municipality, and county of that location.
   *
   * @param zipCode The zip code parameter is a string representing the postal code to be looked up
   *               in the API.
   * @return The method returns a String array containing either the city, municipality, and county
   *         corresponding to the provided zip code,
   *         or an error message if the API request was unsuccessful.
   */
  public static String[] zipLookup(String zipCode) {
    // Set up the request parameters
    Map<String, String> params = new HashMap<>();
    params.put("postal_code", zipCode);

    // Replace <API_UID> and <API_KEY> with your actual Mybring login ID and API key
    String apiUid = "sir_alexiner@hotmail.com";
    String apiKey = "b1fbb72f-f91d-4cfa-9375-20dcae11e650";

    // Set up the request headers
    Map<String, String> headers = new HashMap<>();
    headers.put("X-MyBring-API-Uid", apiUid);
    headers.put("X-MyBring-API-Key", apiKey);

    // Set up the API endpoint
    String endpoint = "https://api.bring.com/address/api/no/postal-codes";

    // Send the API request
    String response = HttpConnection.sendGetRequest(endpoint, headers, params);
    JsonObject json = JsonParser.parseString(response).getAsJsonObject();

    // Check the response status code
    if (response.startsWith("{")) {
      JsonObject postalCodeObject = json.getAsJsonArray("postal_codes").get(0).getAsJsonObject();

      String city = postalCodeObject.get("city").getAsString();
      String municipality = postalCodeObject.get("municipality").getAsString();
      String county = postalCodeObject.get("county").getAsString();
      return new String[] {city, municipality, county};
    } else {
      return new String[] {"Error: " + response + "%n", "", ""};
    }
  }
}