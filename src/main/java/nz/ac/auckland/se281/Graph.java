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

  public Map<Country, List<Country>> getAdjacencies() {
    return adjCountries;
  }

  public List<Country> getAdjacencies(Country country) {
    return adjCountries.get(country);
  }

  /**
   * Adds the country to the world map.
   *
   * @param country the country that will be added to the world map.
   */
  public void addCountry(Country country) {
    adjCountries.putIfAbsent(country, new ArrayList<>());
  }

  /**
   * Adds the connection between two countries.
   *
   * @param country1 the first country that the connection begins at
   * @param country2 the second country that the connection ends at
   */
  public void addConnection(Country country1, Country country2) {
    adjCountries.get(country1).add(country2);
  }
}
