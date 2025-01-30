package no.ntnu.idatg1002.grp8.inex.customers;

import java.util.Map;
import lombok.Getter;
import lombok.Setter;

/**
 * The `Customer` class is a Java class that represents a customer and contains fields for their
 * personal information and a constructor method to initialize those fields.
 */
public class Customer {

  @Getter
  @Setter
  private String customerId;
  @Getter
  @Setter
  private String fullName;
  @Getter
  @Setter
  private String email;
  @Getter
  @Setter
  private String zipCode;
  @Getter
  @Setter
  private String city;
  @Getter
  @Setter
  private String street;
  @Getter
  @Setter
  private String houseNumber;
  @Getter
  @Setter
  private String houseLetter;
  @Getter
  @Setter
  private String county;
  @Getter
  @Setter
  private String municipality;

  /**
   * Constructs a new Customer object using the provided customer information in a map.
   *
   * @param customer a Map object containing the customer's information with the following keys:
   *                 "customerId", "customerName", "customerEmail", "customerZipCode",
   *                 "customerCity", "customerStreet", "customerHouseNumber",
   *                 "customerHouseLetter", "customerCounty", and "customerMunicipality".
   *                 The values associated with these keys should correspond to the customer's
   *                 ID, full name, email, zip code, city, street, house number,
   *                 house letter (if applicable),county, and municipality, respectively.
   */
  public Customer(Map<String, String> customer) {
    this.customerId = customer.get("customerId");
    this.fullName = customer.get("customerName");
    this.email = customer.get("customerEmail");
    this.zipCode = customer.get("customerZipCode");
    this.city = customer.get("customerCity");
    this.street = customer.get("customerStreet");
    this.houseNumber = customer.get("customerHouseNumber");
    this.houseLetter = customer.get("customerHouseLetter");
    this.county = customer.get("customerCounty");
    this.municipality = customer.get("customerMunicipality");
  }

  @Override
  public String toString() {
    // Customize the output format as needed
    return customerId + " - " + fullName + " - " + email;
  }
}