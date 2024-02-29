package model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class PortfolioBuilder {
  private final Map<String, Integer> shareList;
  private final String portfolioName;

  public PortfolioBuilder(String portfolioName, int numberOfShare) {
    boolean found = false;
    shareList = new HashMap<>();
    String filePath = "portfolioNames.txt";
    File file = new File(filePath);
    if (!file.exists()) {
      try {
        file.createNewFile();
        System.out.println("File created successfully.");
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

    // Open the file and read its contents line by line
    try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
      String line;
      while ((line = br.readLine()) != null) {
        // Compare each line with the given string
        if (line.equals(portfolioName)) {
          found = true;
          break;
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }

    if (!found) {
      this.portfolioName = portfolioName;
    } else {
      throw new IllegalArgumentException();
    }

  }

  public void addShare(String shareName, int quantity) {
    this.shareList.put(shareName, quantity);
  }

  public Portfolio createPortfolio() {
    String filePath = "portfolioNames.txt";
    File file = new File(filePath);

    try (FileWriter fw = new FileWriter(filePath, true);
         BufferedWriter bw = new BufferedWriter(fw);
         PrintWriter out = new PrintWriter(bw)) {
      out.println(this.portfolioName);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return new Portfolio(this.portfolioName, shareList);
  }
}
