package model;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Portfolio implements StockManagement {


  private final Map<String, Integer> sharesList;
  private final String portfolioName;

  public Portfolio(String portfolioName, Map<String, Integer> list) {
    sharesList = new HashMap<>();
    this.portfolioName = portfolioName;
    for (Map.Entry<String, Integer> entry : list.entrySet()) {
      this.sharesList.put(entry.getKey(), entry.getValue());
    }



    String fileName = this.portfolioName + ".txt";

    try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {

      for (Map.Entry<String, Integer> entry : sharesList.entrySet()) {
        writer.write(entry.getKey() + ": " + entry.getValue());
        writer.newLine();
      }
      System.out.println("Values have been written to the file: " + fileName);
    } catch (IOException e) {
      System.err.println("Error writing to the file: " + e.getMessage());
    }
  }




@Override
  public Map<String, Integer> portfolioComposition(String portfolioName) {
    Map<String, Integer> composition = new HashMap<>();

    String filePath = portfolioName + ".txt";
    try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
      String line;
      while ((line = br.readLine()) != null) {
        String[] parts = line.split(":", 2);
        if (parts.length == 2) {
          String key = parts[0].trim();
          int value = Integer.parseInt(parts[1].trim());
          composition.put(key, value);
        }
      }
    } catch (FileNotFoundException e) {
      throw new RuntimeException("File not found: " + filePath, e);
    } catch (IOException e) {
      throw new RuntimeException("Error reading file: " + filePath, e);
      //required ?
    }
    return composition;

  }

  @Override
  public float portfolioValue(String portfolioName, String date) {
    return 0;
  }

  @Override
  public void persistPortfolio(String portfolioName) {

  }
}
