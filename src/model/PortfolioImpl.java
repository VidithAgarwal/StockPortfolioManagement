package model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class PortfolioImpl implements Portfolio {


  private final Map<String, Integer> sharesList;
  private final String portfolioName;

  private PortfolioImpl(String portfolioName, Map<String, Integer> list) {
    sharesList = new HashMap<>();
    this.portfolioName = portfolioName;
    for (Map.Entry<String, Integer> entry : list.entrySet()) {
      this.sharesList.put(entry.getKey(), entry.getValue());
    }
  }




  @Override
  public Map<String, Integer> portfolioComposition() {
    return this.sharesList;
  }

  @Override
  public float portfolioValue(String portfolioName, String date) {
    return 0;
  }

  @Override
  public void savePortfolio(String filePath) {
    File file = new File(filePath);
    File parentDir = file.getParentFile();

    if (parentDir != null && !parentDir.exists()) {
      parentDir.mkdirs(); // Create parent directories recursively
    }

    try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
      for (Map.Entry<String, Integer> entry : this.sharesList.entrySet()) {
        writer.write(entry.getKey() + ": " + entry.getValue());
        writer.newLine();
      }
      System.out.println("Portfolio exported to " + filePath + " successfully.");
    } catch (IOException e) {
      System.err.println("Error exporting portfolio to file: " + e.getMessage());
    }
  }

//  @Override
//  public Map<String, Integer> loadPortfolio(String filePath) {
//    Map<String, Integer> portfolioMap = new HashMap<>();
//    try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
//      String line;
//      while ((line = reader.readLine()) != null) {
//        String[] parts = line.split(":"); // Assuming the delimiter is ":"
//        if (parts.length == 2) {
//          String key = parts[0].trim();
//          int value = Integer.parseInt(parts[1].trim());
//          portfolioMap.put(key, value);
//        } else {
//          // Handle invalid lines if needed
//          System.err.println("Invalid line: " + line);
//        }
//      }
//    } catch (IOException e) {
//      System.err.println("Error reading file: " + e.getMessage());
//    } catch (NumberFormatException e) {
//      System.err.println("Invalid number format in file: " + e.getMessage());
//    }
//    return portfolioMap;
//  }

  @Override
  public String getName() {
    return this.portfolioName;
  }

  public static class PortfolioBuilder {
    private Map<String, Integer> shareList;
    private final String portfolioName;

    public PortfolioBuilder(String portfolioName, int numberOfShare) {
      shareList = new HashMap<>(numberOfShare);
      this.portfolioName = portfolioName;
    }

    public PortfolioBuilder(String portfolioName, String path) {
      shareList = loadPortfolio(path);
      this.portfolioName = portfolioName;
    }

//    public void loadedShareList (Map<String, Integer> portfolioMap) {
//      this.shareList = portfolioMap;
//    }
    public void addShare(String shareName, int quantity) {
      this.shareList.put(shareName, quantity);
    }

    public Portfolio build() {
      return new PortfolioImpl(this.portfolioName, shareList);
    }


    public Map<String, Integer> loadPortfolio(String filePath) {
      Map<String, Integer> portfolioMap = new HashMap<>();
      try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
        String line;
        while ((line = reader.readLine()) != null) {
          String[] parts = line.split(":"); // Assuming the delimiter is ":"
          if (parts.length == 2) {
            String key = parts[0].trim();
            int value = Integer.parseInt(parts[1].trim());
            portfolioMap.put(key, value);
          } else {
            // Handle invalid lines if needed
            System.err.println("Invalid line: " + line);
          }
        }
      } catch (IOException e) {
        System.err.println("Error reading file: " + e.getMessage());
      } catch (NumberFormatException e) {
        System.err.println("Invalid number format in file: " + e.getMessage());
      }
      return portfolioMap;
    }
  }

}
