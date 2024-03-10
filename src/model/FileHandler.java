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

public class FileHandler {
  void export(String path, String portfolioName, Map<String, Integer> composition) {
    File file = new File(path);
    File parentDir = file.getParentFile();

    if (parentDir != null && !parentDir.exists()) {
      parentDir.mkdirs(); // Create parent directories recursively
    }

    try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
      writer.write("Portfolio Name: " + portfolioName + "\n\n");
      writer.write("Stock,Quantity" + "\n");
      for (Map.Entry<String, Integer> entry : composition.entrySet()) {
        writer.write(entry.getKey() + "," + entry.getValue());
        writer.newLine();
      }
    } catch (IOException e) {
      throw new IllegalArgumentException();
    }
  }

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
