package controller;

/**
 * The FetchFromAPI interface provides a method for fetching data from an API.
 */
interface FetchFromAPI {

  /**
   * FetchData method is used to call the api for the stockSymbol using api Key.
   * It gets the historical data for that stock and returns the data as a stringBuilder object.
   *
   * @param ticker The ticker symbol we need the historical data for.
   * @return A stringBuilder object containing the data fetched from the API.
   */
  StringBuilder fetchData(String ticker);
}
