package nz.ac.auckland.se281;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
}
