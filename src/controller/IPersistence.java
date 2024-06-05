package controller;

import java.util.List;

/**
 *This interface provides method to write to and read from a CSV file provided to it.
 */
interface IPersistence {

  /**
   * This method exports the given composition data to a CSV file at the specified path.
   * @param path path to the CSV file where data will be exported.
   * @param data String builder object that contains the data to be stored.
   */
  void exportAsCSV(String path, StringBuilder data);

  /**
   * this method loads data from a CSV file located at the specified file path.
   * @param filePath  path to the CSV file to be loaded.
   * @return list of string arrays representing the lines of data read from the CSV file.
   */
  List<String[]> loadFromCSV(String filePath);
}
