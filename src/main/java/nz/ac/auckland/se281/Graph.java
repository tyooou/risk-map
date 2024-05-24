package nz.ac.auckland.se281;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

/** A graph that allows for countries and paths to be initialised. Represents a geographical map. */
public class Graph {
  // Initialise the map.
  private Map<Country, List<Country>> adjCountries;

  /** Constructor class. Initialise the map on creation. */
  public Graph() {
    this.adjCountries = new HashMap<>();
  }

  /**
   * Add a country to the map.
   *
   * @param country country
   */
  public void addCountry(Country country) {
    // Add the country to the map.
    adjCountries.putIfAbsent(country, new ArrayList<>());
  }

  /**
   * Add a one-way path from one country to another on the map (as the correctly formatted data
   * specifies both paths).
   *
   * @param source source country
   * @param destination destination country
   */
  public void addPath(Country source, Country destination) {
    // Add the source and the destination to the map if they do not exist.
    addCountry(source);
    addCountry(destination);

    // Add a one-way path from the source to the destination.
    adjCountries.get(source).add(destination);
  }

  /**
   * Fetches the country object by its name if it exist on the map.
   *
   * @param name the name of the country
   * @return the country
   * @throws CountryDoesNotExistException country does not exist on the map
   */
  public Country getCountry(String name) throws CountryDoesNotExistException {
    // Searchs if there is a country with the name.
    for (Country country : adjCountries.keySet()) {
      if (country.getName().equals(name)) {
        // Return country if found on the map.
        return country;
      }
    }
    // Throw exception if the country is not found on the map.
    throw new CountryDoesNotExistException();
  }

  /**
   * Finds the shortest path between a source country and destination country using a breadth-first
   * search algorithm.
   *
   * @param source source country
   * @param destination destination country
   * @return array of countries representing path (source --> .... --> destination)
   * @throws NoPathFoundException no path exists between the source and destination on the map
   */
  public List<Country> findShortestRoute(Country source, Country destination)
      throws NoPathFoundException {

    // Initialise necessary data structures.
    Queue<Country> queue = new LinkedList<>();
    List<Country> visited = new ArrayList<>();
    List<Country> previous = new ArrayList<>();
    List<Country> path = new LinkedList<>();

    queue.add(source);
    visited.add(source);
    previous.add(null);

    // Do a breadth-first search.
    while (!queue.isEmpty()) {
      Country country = queue.poll();
      for (Country n : adjCountries.get(country)) {
        if (!visited.contains(n)) {
          visited.add(n);
          queue.add(n);

          // Keep track of previous nodes.
          previous.add(country);
        }
      }
    }

    // Check if accessible path exists.
    if (!visited.contains(destination)) {
      throw new NoPathFoundException();
    } else {
      boolean flag = false;
      Country country = destination;

      while (!flag) {
        // Backtrack from the destination to the source.
        path.add(country);
        country = previous.get(visited.indexOf(country));

        // End loop after the source is reached.
        if (country == null) {
          flag = true;
        }
      }

      // Reverse the path for formatting.
      Collections.reverse(path);
      return path;
    }
  }
}
