package nz.ac.auckland.se281;

public class CountryDoesNotExistException extends Exception {

  public CountryDoesNotExistException() {
    super("The name of a country you have typed is mispelled or does not exist in this map.");
  }
}
