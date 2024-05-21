package nz.ac.auckland.se281;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

/** This class is the main entry point. */
public class MapEngine {
  private ArrayList<Country> countryList = new ArrayList<>();
  private Graph worldMap;

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
    // creates the world map
    this.worldMap = new Graph();
    for (int i = 0; i < countries.size(); i++) {
      countryInfo = countries.get(i).split(",");
      // stores info for each country
      country = new Country(countryInfo[0], countryInfo[1], Integer.parseInt(countryInfo[2]));
      worldMap.addCountry(country);
      countryList.add(country);
    }
    for (int i = 0; i < countryList.size(); i++) {
      adjacentCountriesInfo = adjacencies.get(i).split(",");
      for (int j = 1; j < adjacentCountriesInfo.length; j++) {
        for (int k = 0; k < countryList.size(); k++) {
          if (countryList.get(k).getName().equals(adjacentCountriesInfo[j])) {
            // adds every given connection between all the countries
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
    boolean countryFound = false;
    MessageCli.INSERT_COUNTRY.printMessage();
    userInput = Utils.scanner.nextLine();
    userInput = Utils.capitalizeFirstLetterOfEachWord(userInput);
    while (countryFound == false) {
      try {
        countryExistCheck(userInput);
        countryFound = true;
      } catch (InvalidCountryException e) {
        MessageCli.INVALID_COUNTRY.printMessage(userInput);
        MessageCli.INSERT_COUNTRY.printMessage();
        userInput = Utils.scanner.nextLine();
        userInput = Utils.capitalizeFirstLetterOfEachWord(userInput);
      }
    }
  }

  /**
   * Checks if the country that the user input is a valid country.
   *
   * @param userInput the country that the user inputs
   * @throws InvalidCountryException an error that occurs when the country is invalid.
   */
  public void countryExistCheck(String userInput) throws InvalidCountryException {
    Country country;
    for (int i = 0; i < countryList.size(); i++) {
      country = countryList.get(i);
      // finds the country that the user inputs and then displays the info of
      // the country.
      if (userInput.equals(country.getName())) {
        MessageCli.COUNTRY_INFO.printMessage(
            country.getName(), country.getContinent(), String.valueOf(country.getTaxes()));
        return;
      }
    }
    throw new InvalidCountryException(userInput);
  }

  /** this method is invoked when the user run the command route. */
  public void showRoute() {
    String startCountryString, endCountryString;
    boolean countryFound = false;
    int count;
    Country startCountry = null;
    Country endCountry = null;
    StringBuilder sb1 = new StringBuilder();
    StringBuilder sb2 = new StringBuilder();
    Set<String> continents = new LinkedHashSet<>();
    // asks for starting country;
    MessageCli.INSERT_SOURCE.printMessage();
    startCountryString = Utils.scanner.nextLine();
    startCountryString = Utils.capitalizeFirstLetterOfEachWord(startCountryString);
    while (countryFound == false) {
      try {
        countryExistCheck(startCountryString);
        countryFound = true;
      } catch (InvalidCountryException e) {
        MessageCli.INVALID_COUNTRY.printMessage(startCountryString);
        MessageCli.INSERT_COUNTRY.printMessage();
        startCountryString = Utils.scanner.nextLine();
        startCountryString = Utils.capitalizeFirstLetterOfEachWord(startCountryString);
      }
    }
    countryFound = false;
    // asks for end country;
    MessageCli.INSERT_DESTINATION.printMessage();
    endCountryString = Utils.scanner.nextLine();
    endCountryString = Utils.capitalizeFirstLetterOfEachWord(endCountryString);
    while (countryFound == false) {
      try {
        countryExistCheck(endCountryString);
        countryFound = true;
      } catch (InvalidCountryException e) {
        MessageCli.INVALID_COUNTRY.printMessage(endCountryString);
        MessageCli.INSERT_COUNTRY.printMessage();
        endCountryString = Utils.scanner.nextLine();
        endCountryString = Utils.capitalizeFirstLetterOfEachWord(endCountryString);
      }
    }
    // finds the countries corresponding to the user input;
    for (int i = 0; i < countryList.size(); i++) {
      if (countryList.get(i).getName().equals(startCountryString)) {
        startCountry = countryList.get(i);
      } else if (countryList.get(i).getName().equals(endCountryString)) {
        endCountry = countryList.get(i);
      }
    }
    // calls the findRouteTravelled function to find the smallest route.
    List<Country> route = findRouteTravelled(startCountry, endCountry);
    // appends the route of countries into a string and reverses the order
    // of the countries
    sb1.append("[");
    for (int l = (route.size() - 1); l > -1; l--) {
      sb1.append(route.get(l));
      continents.add(route.get(l).getContinent());
      if (l == 0) {
        sb1.append("]");
      } else {
        sb1.append(", ");
      }
    }
    // prints the route of the countries
    MessageCli.ROUTE_INFO.printMessage(sb1.toString());
    count = 0;
    sb2.append("[");
    for (String continent : continents) {
      sb2.append(continent);
      count++;
      if (count == continents.size()) {
        sb2.append("]");
      } else {
        sb2.append(", ");
      }
    }
    MessageCli.CONTINENT_INFO.printMessage(sb2.toString());
  }

  /**
   * Finds the shortest route that can be done given the start and end countries.
   *
   * @param startCountry the country the user wishes to start at
   * @param endCountry the country the user wishes to end at
   * @return the shortest route in List<Country> form.
   */
  public List<Country> findRouteTravelled(Country startCountry, Country endCountry) {
    List<Country> travelledCountry = new ArrayList<>();
    Queue<Country> queue = new LinkedList<>();
    List<Country> shortestPath = new LinkedList<>();
    Country currentTravelledCountry;
    int counter = 0;
    queue.add(startCountry);
    travelledCountry.add(startCountry);
    // this is the BFS algorithm which finds all countries that is connected to a country
    // that is connected to the start country.
    while (!queue.isEmpty()) {
      Country currentCountry = queue.poll();
      for (Country adjCountry : worldMap.getAdjacencies().get(currentCountry)) {
        if (!travelledCountry.contains(adjCountry)) {
          travelledCountry.add(adjCountry);
          queue.add(adjCountry);
        }
      }
    }
    currentTravelledCountry = endCountry;
    // this part of the algorithm finds the smallest version of the route
    while (counter < travelledCountry.size()) {
      for (int m = 0; m < worldMap.getAdjacencies(travelledCountry.get(counter)).size(); m++) {
        if (worldMap
            .getAdjacencies(travelledCountry.get(counter))
            .get(m)
            .equals(currentTravelledCountry)) {
          shortestPath.add(currentTravelledCountry);
          currentTravelledCountry = travelledCountry.get(counter);
          // if the current country is the start country we can deduce that the
          // path is finalised so we add the start country and return the path.
          if (currentTravelledCountry.equals(startCountry)) {
            shortestPath.add(startCountry);
            return shortestPath;
          }
          counter = -1;
          break;
        }
      }
      counter++;
    }
    return null;
  }
}
