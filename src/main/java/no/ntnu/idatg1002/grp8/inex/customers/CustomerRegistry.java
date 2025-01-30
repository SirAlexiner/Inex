package no.ntnu.idatg1002.grp8.inex.customers;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 * The CustomerRegistry class contains a list of customers and has getter and setter methods for
 * accessing and modifying the list.
 */
public class CustomerRegistry {
  @Getter @Setter
  private List<Customer> customers;
}
