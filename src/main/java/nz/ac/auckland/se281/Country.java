package nz.ac.auckland.se281;

public class Country {
  private String name;
  private int taxes;
  private String continent;

  public Country(String name, String continent, int taxes) {
    this.name = name;
    this.continent = continent;
    this.taxes = taxes;
  }

  /**
   * Gets the name of the country.
   *
   * @return the name of the country
   */
  public String getName() {
    return name;
  }

  /**
   * Gets the continent that the country is in.
   *
   * @return the continent
   */
  public String getContinent() {
    return continent;
  }

  /**
   * Gets the taxes imposed in that country.
   *
   * @return the amount of taxes to pay
   */
  public int getTaxes() {
    return taxes;
  }
  
  @Override
  public String toString() {
    return name;
  }

  @Override
  public boolean equals(Object obj) {
    Country otherCountry = (Country) obj;
    if (this.getName() == otherCountry.getName()) {
      return true;
    }
    return false;
  }
}
