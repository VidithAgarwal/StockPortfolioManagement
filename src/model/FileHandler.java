package model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
  public List<String[]> load(String filePath) {
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

  /**
   * Saves data to a file located at the specified path.
   * @param path The path to the file where data will be saved.
   * @param historicalData A map representing the historical data to be saved, with keys as
   *                       stock names and values as historical prices.
   */
  void save(String path, Map<String, Double> historicalData) {
    File file = new File(path);
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
      for (Map.Entry<String, Double> entry : historicalData.entrySet()) {
        writer.write(entry.getKey() + "," + entry.getValue());
        writer.newLine();
      }
    } catch (IOException e) {
      throw new IllegalArgumentException();
    }
  }
}
