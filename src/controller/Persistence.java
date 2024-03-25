package controller;

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
 * The Persistence class provides methods for exporting data to CSV file and loading data
 * from CSV files.
 */
class Persistence {

  /**
   * Exports the given composition data to a CSV file at the specified path.
   * @param path The path to the CSV file where data will be exported.
   * @param data A map representing data, with stock names as keys, quantities as values.
   */
  void exportAsCSV(String path, StringBuilder data) {
    File file = new File(path);
    if (!file.getName().endsWith(".csv")) {
      throw new IllegalArgumentException("File provided must be CSV!");
    }

    File parentDir = file.getParentFile();

    if (parentDir == null || ".csv".equals(file.getName())) {
      throw new IllegalArgumentException("Invalid Path");
    }

    if (!parentDir.exists()) {
      parentDir.mkdirs(); // Create parent directories recursively
    }

    try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
      writer.write(data.toString());
      System.out.println("Data exported successfully to " + file);
    } catch (IOException e) {
      throw new IllegalArgumentException(e.getMessage());
    }
  }

  /**
   * this method saves historical data to a CSV file at the specified path.
   * @param path  path to the CSV file where data will be saved.
   * @param historicalData map representing historical data, with dates as keys.
   *                      and price lists as values.
   * @throws IllegalArgumentException if an error occurs while saving the data.
   */
  void save(String path, Map<String, ArrayList<Double>> historicalData) {
    File file = new File(path);
    File parentDir = file.getParentFile();

    if (!parentDir.exists()) {
      parentDir.mkdirs(); // Create parent directories recursively
    }

    try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
      for (Map.Entry<String, ArrayList<Double>> entry : historicalData.entrySet()) {
        StringBuilder rowBuilder = new StringBuilder();
        rowBuilder.append(entry.getKey()).append(",");
        for (Double value : entry.getValue()) {
          rowBuilder.append(value).append(",");
        }
        rowBuilder.deleteCharAt(rowBuilder.length() - 1); // Remove trailing comma
        writer.write(rowBuilder.toString());
        writer.newLine();
      }
    } catch (IOException e) {
      throw new IllegalArgumentException();
    }
  }


  /**
   * Loads data from a CSV file located at the specified file path.
   * @param filePath The path to the CSV file to be loaded.
   * @return A list of string arrays representing the lines of data read from the CSV file.
   */
  List<String[]> loadFromCSV(String filePath) {
    File file = new File(filePath);
    if (!file.exists()) {
      throw new IllegalArgumentException("File not found. Please enter a valid file path.");
    }
    if (!filePath.toLowerCase().endsWith(".csv")) {
      throw new IllegalArgumentException("File format is not CSV. Please enter a file with .csv "
              + "extension.");
    }
    List<String[]> lines = new ArrayList<>();
    try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
      String line;
      boolean firstLineSkipped = false;
      while ((line = reader.readLine()) != null) {
        if (!firstLineSkipped) {
          firstLineSkipped = true;
          continue;
        }
        String[] parts = line.split(",");

          lines.add(parts);

      }
    } catch (IOException e) {
      System.err.println("Error reading file: " + e.getMessage());
    }
    return lines;
  }
}
