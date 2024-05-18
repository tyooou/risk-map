package nz.ac.auckland.se281;

import java.util.Objects;

public class Country {
  private String name;
  private String continent;
  private String fee;

  public Country(String name, String continent, String fee) {
    this.name = name;
    this.continent = continent;
    this.fee = fee;
  }

  public String getName() {
    return name;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append(name);
    sb.append(" => continent: ");
    sb.append(continent);
    sb.append(", tax fees: ");
    sb.append(fee);
    return sb.toString();
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.name, this.continent, this.fee);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    Country other = (Country) obj;
    if (name == null) {
      if (other.name != null) return false;
    } else if (!name.equals(other.name)) return false;
    return true;
  }
}
