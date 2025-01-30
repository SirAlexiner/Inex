package no.ntnu.idatg1002.grp8.inex.addressapi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import lombok.experimental.UtilityClass;

/**
 * The HttpConnection class provides methods for sending GET requests with specified headers and
 * parameters and returning the response as a string or an error message.
 */
@UtilityClass
public class HttpConnection {

  /**
   * This function sends a GET request to a specified endpoint with given headers and parameters.
   *
   * @param endpoint The URL endpoint to which the GET request will be sent.
   * @param headers  A map of HTTP headers to be included in the GET request. These headers provide
   *                 additional information about the request or the client making the request.
   * @param params   The `params` parameter is a `Map` object that contains key-value pairs of query
   *                 parameters to be included in the GET request URL.
   * @return The method is returning a String, which is either the response string obtained from the
   *         HTTP GET request or an error message if an exception occurs.
   */
  public String sendGetRequest(String endpoint, Map<String, String> headers,
                               Map<String, String> params) {
    String queryString = String.join("&",
        params.entrySet().stream().map(e -> encode(e.getKey()) + "=" + encode(e.getValue()))
            .toArray(String[]::new));

    try {
      URI uri = new URI(endpoint + "?" + queryString);
      URL url = uri.toURL();
      return HttpConnection.getHttpResponseString(headers, url);
    } catch (Exception e) {
      return e.getMessage();
    }
  }

  /**
   * The function encodes a given string using UTF-8 encoding.
   *
   * @param s The input string that needs to be encoded using URL encoding.
   * @return The method `encode` returns a string that is the URL-encoded version
   *         of the input string
   *         `s`. If an exception occurs during the encoding process,
   *         the original string is returned.
   */
  private String encode(String s) {
    try {
      return URLEncoder.encode(s, StandardCharsets.UTF_8);
    } catch (Exception e) {
      return s;
    }
  }

  /**
   * This function sends a GET request to a URL with specified headers and returns the response as a
   * string or an error message if the response code is not 200.
   *
   * @param headers A map containing HTTP headers to be included in the request.
   * @param url     The URL of the HTTP request that needs to be made.
   * @return The method returns a String. If the HTTP response code is 200,
   *         it returns the content of
   *         the response as a String. If the response code is not 200,
   *         it returns a String with an error message that includes the HTTP response
   *         code and message.
   */
  private String getHttpResponseString(Map<String, String> headers, URL url) throws
      IOException {
    HttpURLConnection con = (HttpURLConnection) url.openConnection();
    con.setRequestMethod("GET");

    for (Map.Entry<String, String> entry : headers.entrySet()) {
      con.setRequestProperty(entry.getKey(), entry.getValue());
    }

    int status = con.getResponseCode();

    if (status == 200) {
      BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
      String inputLine;
      StringBuilder content = new StringBuilder();
      while ((inputLine = in.readLine()) != null) {
        content.append(inputLine);
      }
      in.close();
      con.disconnect();
      return content.toString();
    } else {
      con.disconnect();
      return String.format("HTTP Error: %d - %s", status, con.getResponseMessage());
    }
  }
}
