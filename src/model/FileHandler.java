package model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * This class FileHandler as methods for loading the data from files, that are created when.
 * api is called for a stock data. It also saves the data to a file, which is fetched from api.
 */
class FileHandler {

  /**
   * Loads data from a file located at the specified file path.
   * @param filePath The path to the file to be loaded.
   * @return A list of string arrays representing the lines of data read from the file.
   */
  List<String[]> load(String filePath) {
    List<String[]> lines = new ArrayList<>();
    try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
      String line;
      while ((line = reader.readLine()) != null) {
        String[] parts = line.split(",");
        lines.add(parts);
      }
    } catch (IOException e) {
      System.err.println("Error reading file: " + e.getMessage());
    }
    return lines;
  }
}
