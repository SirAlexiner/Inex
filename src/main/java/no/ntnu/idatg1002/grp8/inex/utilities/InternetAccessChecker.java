package no.ntnu.idatg1002.grp8.inex.utilities;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import lombok.experimental.UtilityClass;

/**
 * The InternetAccessChecker class checks if there is internet access by attempting to connect to
 * www.google.com on port 80.
 */
@UtilityClass
public class InternetAccessChecker {

  /**
   * The function checks if there is internet access:
   * by attempting to connect to www.google.com on port 80.
   *
   * @return The method `checkInternetAccess()` returns a boolean value. It returns `true` if the
   *         device has internet access and can connect to the Google website on port 80
   *         within 3 seconds, and false,
   *         if it cannot establish a connection within the given time or encounters an IOException.
   */
  public static boolean checkInternetAccess() {
    SocketAddress socketAddress = new InetSocketAddress("www.google.com", 80);
    try (Socket socket = new Socket()) {
      socket.connect(socketAddress, 3000);
      return true;
    } catch (IOException e) {
      return false;
    }
  }
}
