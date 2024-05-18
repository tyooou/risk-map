public class Country {
  private String name;
  private String continent;
  private int fee;

  public Country(String name, String continent, int fee) {
    this.name = name;
    this.continent = continent;
    this.fee = fee;
  }

  @Override
  public String toString() {
    return name + " => continent: " + continent + ", tax fees: " + fee;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    return prime + fee;
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
