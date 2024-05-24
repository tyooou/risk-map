package nz.ac.auckland.se281;

public class CountryDoesNotExistException extends Exception {

  /** Exception for cases where the country is asked for but does not exist in the map. */
  public CountryDoesNotExistException() {
    super(
        "The country you are looking for does not exist in this map. Please review your input or"
            + " the .csv files.");
  }
}
