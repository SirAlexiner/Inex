package no.ntnu.idatg1002.grp8.inex.addressapi.json;

import lombok.Getter;
import lombok.Setter;

/**
 * The class "Suggestion" has getter and setter methods for street name, house number, letter,
 * postal code, city, county, and municipality.
 */
public class Suggestion {
  @SuppressWarnings("checkstyle:MemberName")
  @Getter @Setter
  private String street_name;
  @SuppressWarnings("checkstyle:MemberName")
  @Getter @Setter
  private String house_number;
  @Getter @Setter
  private String letter;
  @SuppressWarnings("checkstyle:MemberName")
  @Getter @Setter
  private String postal_code;
  @Getter @Setter
  private String city;
  @Getter @Setter
  private String county;
  @Getter @Setter
  private String municipality;
}
