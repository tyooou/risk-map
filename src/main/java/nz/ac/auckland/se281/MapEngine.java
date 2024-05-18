package nz.ac.auckland.se281;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/** This class is the main entry point. */
public class MapEngine {

  private Graph map = new Graph();

  public MapEngine() {
    // add other code here if you want
    loadMap(); // keep this mehtod invocation
  }

  /** invoked one time only when constracting the MapEngine class. */
  private void loadMap() {
    List<String> countries = Utils.readCountries();
    List<String> adjacencies = Utils.readAdjacencies();

    for (String country : countries) {
      String[] countryInfo = country.split(",");
      map.addCountry(new Country(countryInfo[0], countryInfo[1], countryInfo[2]));
    }

    for (String adjacency : adjacencies) {
      String[] adjacenctCountries = adjacency.split(",");
      Queue<Country> adjacenctCountriesQueue = new LinkedList<>();

      for (String country : adjacenctCountries) {
        adjacenctCountriesQueue.add(map.getCountry(country));
      }

      Country start = adjacenctCountriesQueue.remove();
      while (!adjacenctCountriesQueue.isEmpty()) {
        map.addPath(start, adjacenctCountriesQueue.remove());
      }
    }

    // add code here to create your data structures
  }

  /** this method is invoked when the user run the command info-country. */
  public void showInfoCountry() {}

  /** this method is invoked when the user run the command route. */
  public void showRoute() {}
}
