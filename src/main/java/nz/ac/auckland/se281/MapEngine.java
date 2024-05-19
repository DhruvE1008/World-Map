package nz.ac.auckland.se281;

import java.util.ArrayList;
import java.util.List;

/** This class is the main entry point. */
public class MapEngine {
  private ArrayList<Country> countryList = new ArrayList<>();

  public MapEngine() {
    // add other code here if you want
    loadMap(); // keep this mehtod invocation
  }

  /** invoked one time only when constracting the MapEngine class. */
  private void loadMap() {
    Country country;
    String[] countryInfo;
    String[] adjacentCountriesInfo;
    List<String> countries = Utils.readCountries();
    List<String> adjacencies = Utils.readAdjacencies();
    // add code here to create your data structures
    Graph worldMap = new Graph();
    for (int i = 0; i < countries.size(); i++) {
      countryInfo = countries.get(i).split(",");
      country = new Country(countryInfo[0], countryInfo[1], Integer.parseInt(countryInfo[2]));
      worldMap.addCountry(country);
      countryList.add(country);
    }
    for (int i = 0; i < countryList.size(); i++) {
      adjacentCountriesInfo = adjacencies.get(i).split(",");
      for (int j = 1; j < adjacentCountriesInfo.length; j++) {
        for (int k = 0; k < countryList.size(); k++) {
          if (countryList.get(k).getName() == adjacentCountriesInfo[j]) {
            worldMap.addConnection(countryList.get(i), countryList.get(k));
          }
        }
      }
    }
  }

  /** this method is invoked when the user run the command info-country. */
  public void showInfoCountry() {
    // add code here
    String userInput;
    Country country;
    MessageCli.INSERT_COUNTRY.printMessage();
    userInput = Utils.scanner.nextLine();
    for (int i = 0; i < countryList.size(); i++) {
      country = countryList.get(i);
      if (userInput.equals(country.getName())) {
        MessageCli.COUNTRY_INFO.printMessage(
            country.getName(), country.getContinent(), String.valueOf(country.getTaxes()));
      }
    }
  }

  /** this method is invoked when the user run the command route. */
  public void showRoute() {}
}
