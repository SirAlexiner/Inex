package no.ntnu.idatg1002.grp8.inex.gui.controllers.util;

import javafx.scene.control.TextField;

/**
 * This is a Java interface named `AddressField` that defines a set of methods for accessing and
 * validating customer address information. The interface includes getter methods for retrieving the
 * street, house number, house letter, zip code, city, county,
 * and municipality fields of a customer's
 * address, as well as a `validateAddress()` method for validating the address.
 * This interface can be
 * implemented by classes that need to work with customer address information,
 * allowing them to access
 * and validate the address fields in a standardized way.
 */
public interface AddressField {
  TextField getCustomerStreet();

  TextField getCustomerHouseNumber();

  TextField getCustomerHouseLetter();

  TextField getCustomerZipCode();

  TextField getCustomerCity();

  TextField getCustomerCounty();

  TextField getCustomerMunicipality();

  void validateAddress();
}
