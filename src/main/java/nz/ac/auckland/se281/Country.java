package nz.ac.auckland.se281;

import java.nio.charset.StandardCharsets;

/**
 * Graph node object that represents a country. All countries must have a name, an appropriate
 * continent and a cross-border tax.
 */
public class Country {
  private String name;
  private String continent;
  private int fee;

  /**
   * Constructor class for a country.
   *
   * @param name name of country
   * @param continent continent the country is in
   * @param fee tax for cross-border travel
   */
  public Country(String name, String continent, int fee) {
    this.name = name;
    this.continent = continent;
    this.fee = fee;
  }

  /**
   * Get name of the country. This is also used to identify the country by the map engine.
   *
   * @return name of country
   */
  public String getName() {
    return name;
  }

  /**
   * Get continent the country is in.
   *
   * @return name of continent the country is in.
   */
  public String getContinent() {
    return continent;
  }

  /**
   * Get the tax for cross-border travel of the country.
   *
   * @return the tax for cross-border travel of the country.
   */
  public int getFee() {
    return fee;
  }

  /**
   * Find the sum of the ASCII values of a string.
   *
   * @param string string to find sum of ASCII values
   * @return sum of ASCII values
   */
  public int sumBytes(String string) {
    int result = 0;
    for (int num : string.getBytes(StandardCharsets.US_ASCII)) {
      result += num;
    }
    return result;
  }

  /**
   * Generate unique hashCode for the countries based on their name, continent and fee.
   *
   * @return unique hashCode
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + sumBytes(this.name);
    result = prime * result + sumBytes(this.continent);
    result = prime * result + this.fee;
    return result;
  }

  /**
   * Determine if countries are equivalent or not. It checks if the country is not null, a country,
   * has a name, equals its name and continent, to determine if they are equal.
   *
   * @return if the country is equal to another country
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    Country other = (Country) obj;
    if (name == null) {
      if (other.name != null) {
        return false;
      }
    } else if (!name.equals(other.name)) {
      return false;
    } else if (!continent.equals(other.continent)) {
      return false;
    }
    return true;
  }

  /**
   * Returns the countries name when converted to a String.
   *
   * @return the name of the country.
   */
  @Override
  public String toString() {
    return name;
  }
}
