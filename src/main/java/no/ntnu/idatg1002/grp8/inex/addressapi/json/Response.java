package no.ntnu.idatg1002.grp8.inex.addressapi.json;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 * The Response class has a boolean variable and a list of suggestions, with getter
 * and setter methods for both.
 */
public class Response {

  @Getter @Setter
  private boolean valid;
  @Getter @Setter
  private List<Suggestion> suggestions;
}

