package controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * This Persistence class provides methods for exporting data to a file and loading data
 * from CSV files.
 */
class Persistence implements IPersistence {

  public void exportAsCSV(String path, StringBuilder data) {
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
    } catch (IOException e) {
      throw new IllegalArgumentException(e.getMessage());
    }
  }


  /**
   * this method loads data from a CSV file located at the specified file path.
   * @param filePath  path to the CSV file to be loaded.
   * @return list of string arrays representing the lines of data read from the CSV file.
   */
  public List<String[]> loadFromCSV(String filePath) {
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
