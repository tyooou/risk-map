package nz.ac.auckland.se281;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class Graph {
  private Map<Country, List<Country>> adjCountries;

  public Graph() {
    this.adjCountries = new HashMap<>();
  }

  public void addCountry(Country country) {
    adjCountries.putIfAbsent(country, new ArrayList<>());
  }

  public void addPath(Country firstCountry, Country lastCountry) {
    addCountry(firstCountry);
    addCountry(lastCountry);
    adjCountries.get(firstCountry).add(lastCountry);
    adjCountries.get(lastCountry).add(firstCountry);
  }

  public Country getCountry(String name) throws CountryDoesNotExistException {
    // Searchs if there is a country with the name.
    for (Country country : adjCountries.keySet()) {
      if (country.getName().equals(name)) {
        return country;
      }
    }
    throw new CountryDoesNotExistException();
  }

  public List<Country> findShortestRoute(Country source, Country destination) {
    List<Country> visited = new ArrayList<>();
    List<Country> previous = new ArrayList<>();
    Queue<Country> queue = new LinkedList<>();
    List<Country> path = new ArrayList<>();

    queue.add(source);
    visited.add(source);
    previous.add(null);

    while (!queue.isEmpty()) {
      Country country = queue.poll();
      for (Country n : adjCountries.get(country)) {
        if (!visited.contains(n)) {
          visited.add(n);
          queue.add(n);
          previous.add(country);
        }
      }
    }

    for (Country country : visited) {
      System.out.println(country);
    }

    for (Country country : previous) {
      System.out.println(country);
    }

    boolean flag = false;
    Country country = destination;
    while (!flag) {
      if (country == source) {
        flag = true;
      }
      path.add(country);
      country = previous.get(visited.indexOf(country));
      System.out.println(country);
    }

    Collections.reverse(path);

    if (path.get(0) == source) {
      return path;
    }
    return List.of();
  }
}
