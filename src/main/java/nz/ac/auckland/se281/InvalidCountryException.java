package nz.ac.auckland.se281;

public class InvalidCountryException extends Exception {

  public InvalidCountryException(String userInput) {
    super("Invalid country input: " + userInput);
  }
}
