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

    // Add nodes.
    for (String country : countries) {
      String[] countryInfo = country.split(",");
      map.addCountry(new Country(countryInfo[0], countryInfo[1], countryInfo[2]));
    }

    // Connect nodes.
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
  }

  /** this method is invoked when the user run the command info-country. */
  public void showInfoCountry() {
    MessageCli.INSERT_COUNTRY.printMessage();
    Country target = getInput();
    MessageCli.COUNTRY_INFO.printMessage(target.getName(), target.getContinent(), target.getFee());
  }

  /** this method is invoked when the user run the command route. */
  public void showRoute() {

    MessageCli.INSERT_SOURCE.printMessage();
    Country source = getInput();
    MessageCli.INSERT_DESTINATION.printMessage();
    Country destination = getInput();

    if (source == destination) {
      MessageCli.NO_CROSSBORDER_TRAVEL.printMessage();
    }
  }

  public Country getInput() {
    boolean flag = false;
    Country target = null;

    do {
      String input = Utils.capitalizeFirstLetterOfEachWord(Utils.scanner.nextLine().trim());
      try {
        target = map.getCountry(input);
        flag = true;
      } catch (CountryDoesNotExistException e) {
        MessageCli.INVALID_COUNTRY.printMessage(input);
      }
    } while (!flag);
    return target;
  }
}
