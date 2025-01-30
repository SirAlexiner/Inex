package no.ntnu.idatg1002.grp8.inex.invoice;

import lombok.Getter;

/**
 * The `Item` class has private fields for description, quantity, price, and sum, and a constructor
 * method that initializes these fields and calculates the sum.
 */
public class Item {
  @Getter
  private final String description;
  @Getter
  private final double quantity;
  @Getter
  private final double price;
  @Getter
  private final double sum;

  /**
   * This is the constructor method for the `Item` class.
   * It takes in three parameters: `description`,quantity`, and `price`.
   * It initializes the private fields `description`, `quantity`, and `price`
   * with the values passed in as parameters.
   * It also calculates the `sum` by multiplying the `quantity` and `price` fields.
   * Finally, it sets the `sum` field to the calculated value.
   *
   * @param description A string containing the item's description.
   * @param quantity    A double containing the quantity of this item
   * @param price       A double containing the price of the item
   */
  public Item(String description, double quantity, double price) {
    this.description = description;
    this.quantity = quantity;
    this.price = price;
    this.sum = price * quantity;
  }
}
