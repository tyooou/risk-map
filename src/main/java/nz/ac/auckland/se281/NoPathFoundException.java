package nz.ac.auckland.se281;

public class NoPathFoundException extends Exception {

  /** Exception for cases where no path is found between two countries on the map. */
  public NoPathFoundException() {
    super(
        "There is no accessible path between the two countries on the map Please review your input"
            + " or the .csv files.");
  }
}
