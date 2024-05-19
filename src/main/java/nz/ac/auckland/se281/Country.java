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
  public String getName() {
    return name;
  }
  
  public String getContinent() {
    return continent;
  }

  public int getTaxes() {
    return taxes;
  }

  // @Override
  // public boolean equals(Object obj) {
  //   Country otherCountry = (Country) obj;
  //   if (this.getName() == otherCountry.getName()) {
  //     return true;
  //   }
  //   return false;
  // }
}
