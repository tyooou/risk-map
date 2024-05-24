package nz.ac.auckland.se281;

/** Exception for cases where the country is asked for but does not exist in the map. */
public class CountryDoesNotExistException extends Exception {

  /** Constructor for exception. Calls to super to print message. */
  public CountryDoesNotExistException() {
    super(
        "The country you are looking for does not exist in this map. Please review your input or"
            + " the .csv files.");
  }
}
