package nz.ac.auckland.se281;

/** Exception for cases where no path is found between two countries on the map. */
public class NoPathFoundException extends Exception {

  /** Constructor for exception. Calls to super to print message. */
  public NoPathFoundException() {
    super(
        "There is no accessible path between the two countries on the map Please review your input"
            + " or the .csv files.");
  }
}
