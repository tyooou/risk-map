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
      String[] adjacentCountries = adjacency.split(",");
      Queue<Country> adjacentCountriesQueue = new LinkedList<>();

      for (String country : adjacentCountries) {
        try {
          adjacentCountriesQueue.add(map.getCountry(country));
        } catch (CountryDoesNotExistException e) {
          MessageCli.INVALID_COUNTRY.printMessage(country);
        }
      }

      Country start = adjacentCountriesQueue.remove();
      while (!adjacentCountriesQueue.isEmpty()) {
        map.addPath(start, adjacentCountriesQueue.remove());
      }
    }

    // add code here to create your data structures
  }

  /** this method is invoked when the user run the command info-country. */
  public void showInfoCountry() {
    boolean flag = false;
    do {
      MessageCli.INSERT_COUNTRY.printMessage();
      String input = Utils.capitalizeFirstLetterOfEachWord(Utils.scanner.nextLine().trim());
      try {
        Country targetCountry = map.getCountry(input);
        MessageCli.COUNTRY_INFO.printMessage(
            targetCountry.getName(), targetCountry.getContinent(), targetCountry.getFee());
        flag = true;
      } catch (CountryDoesNotExistException e) {
        MessageCli.INVALID_COUNTRY.printMessage(input);
      }
    } while (!flag);
  }

  /** this method is invoked when the user run the command route. */
  public void showRoute() {}
}
