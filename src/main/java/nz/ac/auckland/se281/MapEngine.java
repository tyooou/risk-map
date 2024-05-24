package nz.ac.auckland.se281;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * The map engine that allows a geographic map to be loaded from .csv files. The information about
 * the countries can be accessed and the shortest route between countries can be found by the user.
 */
public class MapEngine {

  // Initialise map.
  private Graph map = new Graph();

  /** Load map on initialisation. */
  public MapEngine() {
    loadMap();
  }

  /**
   * Loads the map from two .csv files containing the name, continent and taxes of countries, and
   * the accessible paths between countries.
   */
  private void loadMap() {
    // Read the data from the .csv files.
    List<String> countriesData = Utils.readCountries();
    List<String> adjacenciesData = Utils.readAdjacencies();

    // Add countries to the map.
    for (String line : countriesData) {
      String[] country = line.split(",");
      map.addCountry(new Country(country[0], country[1], Integer.parseInt(country[2])));
    }

    // Connect adjacent countries.
    for (String line : adjacenciesData) {
      String[] adjacencies = line.split(",");
      // Ensure the country exists on the map.
      try {
        Country base = map.getCountry(adjacencies[0]);
        // Ensure the adjacent country exist on the map. Nested within the try loop so that
        // adjacencies after an invalid adjacencies are still checked.
        try {
          for (int i = 1; i < adjacencies.length; i++) {
            map.addPath(base, map.getCountry(adjacencies[i]));
          }
        } catch (CountryDoesNotExistException e) {
          System.out.println("Error: The country you are looking for does not exist in this map.");
        }
      } catch (CountryDoesNotExistException e) {
        System.out.println("Error: The country you are looking for does not exist in this map.");
      }
    }
  }

  /** Asks the user for a country to display relevant information. */
  public void showInfoCountry() {
    // Ask user to define the country.
    MessageCli.INSERT_COUNTRY.printMessage();
    Country country = processInput();

    // Print the name of the country, its continent and its tax for travel.
    MessageCli.COUNTRY_INFO.printMessage(
        country.getName(), country.getContinent(), Integer.toString(country.getFee()));
  }

  /**
   * Finds the shortest route between two countries, in addition to the continents needed to visit
   * and the total taxes. Will only print the shortest route if a route exists. Multiple routes may
   * be the shortest, however, only the one found first is printed.
   */
  public void showRoute() {
    // Ask for user to define the source and destination countries.
    MessageCli.INSERT_SOURCE.printMessage();
    Country source = processInput();
    MessageCli.INSERT_DESTINATION.printMessage();
    Country destination = processInput();

    // Handle cases where the source and destination are the same country.
    if (source == destination) {
      MessageCli.NO_CROSSBORDER_TRAVEL.printMessage();
    } else {
      // Ensure there is a path between the source and destination.
      try {
        // Find and print the shortest path.
        List<Country> path = map.findShortestRoute(source, destination);
        MessageCli.ROUTE_INFO.printMessage(Arrays.toString(path.toArray()));

        // Create a linked hash set to store the visited continents and reject duplicates.
        Set<String> continents = new LinkedHashSet<>();
        for (Country country : path) {
          continents.add(country.getContinent());
        }
        MessageCli.CONTINENT_INFO.printMessage(Arrays.toString(continents.toArray()));

        // Find the total of cross-border taxes, ignoring the tax from the source country.
        int taxes = 0;
        for (int i = 1; i < path.size(); i++) {
          taxes += path.get(i).getFee();
        }

        // Print the fastest route, continents visited and the total taxes.
        MessageCli.TAX_INFO.printMessage(Integer.toString(taxes));
      } catch (NoPathFoundException e) {
        // Prompt user that there is no accessible path between the source and destination.
        System.out.println("Error: There is no accessible path between the two countries.");
      }
    }
  }

  /**
   * Fetches a country that exists on the map by its name.
   *
   * @return country with specified name
   */
  public Country processInput() {
    boolean flag;
    Country target = null;
    do {
      // Ask user for the name of the country. Lower case is handled, however, capitilisation
      // elsewhere other than the front is rejected.
      String input = Utils.capitalizeFirstLetterOfEachWord(Utils.scanner.nextLine().trim());
      try {
        // Fetch the country from the map.
        target = map.getCountry(input);
        flag = true;
      } catch (CountryDoesNotExistException e) {
        // Prompt user that there is no country of such name in the map.
        MessageCli.INVALID_COUNTRY.printMessage(input);
        flag = false;
      }
    } while (!flag);
    return target;
  }
}
