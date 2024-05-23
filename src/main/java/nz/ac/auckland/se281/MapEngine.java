package nz.ac.auckland.se281;

import java.util.ArrayList;
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

    // Add countries to graph.
    for (String country : countries) {
      String[] countryInfo = country.split(",");
      map.addCountry(new Country(countryInfo[0], countryInfo[1], Integer.parseInt(countryInfo[2])));
    }

    // Connect adjacent countries.
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

  public void showInfoCountry() {

    // Ask user for the target country;
    MessageCli.INSERT_COUNTRY.printMessage();

    // Fetch the country if it exist, if not, keep asking for a valid country.
    Country target = getInput();

    // Prompt user with country information.
    MessageCli.COUNTRY_INFO.printMessage(
        target.getName(), target.getContinent(), Integer.toString(target.getFee()));
  }

  /** this method is invoked when the user run the command route. */
  public void showRoute() {

    MessageCli.INSERT_SOURCE.printMessage();
    Country source = getInput();
    MessageCli.INSERT_DESTINATION.printMessage();
    Country destination = getInput();

    if (source == destination) {
      MessageCli.NO_CROSSBORDER_TRAVEL.printMessage();
    } else {
      List<Country> path = map.findShortestRoute(source, destination);
      StringBuilder pathCountries = new StringBuilder();

      pathCountries.append("[");
      for (int i = 0; i < path.size() - 1; i++) {
        pathCountries.append(path.get(i).toString());
        pathCountries.append(", ");
      }
      pathCountries.append(path.get(path.size() - 1));
      pathCountries.append("]");

      MessageCli.ROUTE_INFO.printMessage(pathCountries.toString());

      List<String> continents = new ArrayList<>();
      StringBuilder pathContinents = new StringBuilder();
      for (Country country : path) {
        if (!continents.contains(country.getContinent())) {
          continents.add(country.getContinent());
        }
      }

      pathContinents.append("[");
      for (int i = 0; i < continents.size() - 1; i++) {
        pathContinents.append(continents.get(i).toString());
        pathContinents.append(", ");
      }
      pathContinents.append(continents.get(continents.size() - 1));
      pathContinents.append("]");

      MessageCli.CONTINENT_INFO.printMessage(pathContinents.toString());

      int taxes = 0;
      for (int i = 1; i < path.size(); i++) {
        taxes += path.get(i).getFee();
      }
      MessageCli.TAX_INFO.printMessage(Integer.toString(taxes));
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
