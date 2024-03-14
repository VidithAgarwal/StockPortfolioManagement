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

class Persistence {
  void exportAsCSV(String path, Map<String, Integer> composition) {
    File file = new File(path);
    if (!file.getName().endsWith(".csv")) {
      throw new IllegalArgumentException("File provided must be CSV!");
    }
    File parentDir = file.getParentFile();

    if (parentDir != null && !parentDir.exists()) {
      parentDir.mkdirs(); // Create parent directories recursively
    }

    try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
      writer.write("Stock,Quantity" + "\n");
      for (Map.Entry<String, Integer> entry : composition.entrySet()) {
        writer.write(entry.getKey() + "," + entry.getValue());
        writer.newLine();
      }
    } catch (IOException e) {
      throw new IllegalArgumentException(e.getMessage());
    }
  }

  List<String[]> loadFromCSV(String filePath) {
    File file = new File(filePath);
    if (!file.exists()) {
      throw new IllegalArgumentException("File not found. Please enter a valid file path.");
    }
    if (!filePath.toLowerCase().endsWith(".csv")) {
      throw new IllegalArgumentException("File format is not CSV. Please enter a file with .csv " +
              "extension.");
    }
    List<String[]> lines = new ArrayList<>();
    try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
      String line;
      while ((line = reader.readLine()) != null) {
        String[] parts = line.split(",");
        if (!parts[0].equalsIgnoreCase("Stock")) {
          System.out.println(parts[0]);
          lines.add(parts);
        }
      }
    } catch (IOException e) {
      System.err.println("Error reading file: " + e.getMessage());
    }
    return lines;
  }
}
