package model;

import org.junit.Before;
import org.junit.Test;


import java.time.LocalDate;
import java.util.Iterator;
import java.util.List;
import java.io.File;
import java.util.Map;
import java.util.TreeMap;

import java.util.ArrayList;
import java.util.Set;

import controller.StockData;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * This class represents a test suite for testing the functionality of the model classes.
 */
public class ModelTest {

  /**
   * Represents an instance of PortfolioDirImpl used for testing purposes.
   */
  private PortfolioDirImpl portfolioDir;

  /**
   * Sets up the test environment before each test method execution.
   * It initializes a new instance of the PortfolioDirImpl class to be tested.
   */
  @Before
  public void setUp() {
    portfolioDir = new PortfolioDirImpl();

  }


  @Test
  public void testAddPortfolio() {
    PortfolioImpl.PortfolioBuilder newBuilder
            = new PortfolioImpl.PortfolioBuilder("Test " + "Portfolio");
    newBuilder.addShare("Apple Inc", 10);
    portfolioDir.addPortfolio(newBuilder);
    assertEquals(1, portfolioDir.getSize());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidStockName() {
    PortfolioImpl.PortfolioBuilder newBuilder
            = new PortfolioImpl.PortfolioBuilder("Test " + "Portfolio");
    newBuilder.addShare("India", 10);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidQuantity() {
    PortfolioImpl.PortfolioBuilder newBuilder
            = new PortfolioImpl.PortfolioBuilder("Test " + "Portfolio");
    newBuilder.addShare("AAPL", -10);
    portfolioDir.addPortfolio(newBuilder);

  }

  @Test
  public void testAddingStocksOfSameName() {
    PortfolioImpl.PortfolioBuilder newBuilder
            = new PortfolioImpl.PortfolioBuilder("Test " + "Portfolio");
    newBuilder.addShare("Apple Inc", 10);
    newBuilder.addShare("Apple Inc", 10);
    portfolioDir.addPortfolio(newBuilder);
    assertEquals(1, portfolioDir.getSize());
    LocalDate date = LocalDate.of(2020, 3, 20);
    Map<String, Integer> composition = portfolioDir.portfolioComposition(0, date);

    assertEquals(1, composition.size());
    assertEquals(20, (int) composition.get("AAPL"));
  }


  @Test
  public void testGetListOfPortfoliosName() {
    PortfolioImpl.PortfolioBuilder firstBuilder
            = new PortfolioImpl.PortfolioBuilder("Test " + "Portfolio1");
    firstBuilder.addShare("Apple Inc", 10);
    portfolioDir.addPortfolio(firstBuilder);
    PortfolioImpl.PortfolioBuilder secondBuilder
            = new PortfolioImpl.PortfolioBuilder("Test " + "Portfolio2");
    secondBuilder.addShare("GOOG", 10);
    portfolioDir.addPortfolio(secondBuilder);

    Map<String, String> listOfPortfolios = portfolioDir.getListOfPortfoliosName();

    List<String> portfolioNames = new ArrayList<>(listOfPortfolios.keySet());

    assertEquals("Test Portfolio1", portfolioNames.get(0));
    assertEquals("Test Portfolio2", portfolioNames.get(1));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testEmptyPortfolioCannotBeCreated() {
    PortfolioImpl.PortfolioBuilder firstBuilder
            = new PortfolioImpl.PortfolioBuilder("Test " + "Portfolio1");
    portfolioDir.addPortfolio(firstBuilder);
  }

  @Test
  public void testPortfolioCreated() {
    PortfolioImpl.PortfolioBuilder firstBuilder
            = new PortfolioImpl.PortfolioBuilder("Test " + "Portfolio1");
    firstBuilder.addShare("Apple Inc", 10);
    firstBuilder.addShare("GOOG", 10);
    portfolioDir.addPortfolio(firstBuilder);
    assertEquals(1, portfolioDir.getSize());
    Map<String, String> listOfPortfolios = portfolioDir.getListOfPortfoliosName();
    Set<String> keys = listOfPortfolios.keySet();
    Iterator<String> iterator = keys.iterator();
    String firstKey = iterator.next();
    assertEquals("Test Portfolio1", firstKey);
  }

  @Test
  public void testPortfolioValueForYearInFourDigitFutureDate() {
    PortfolioImpl.PortfolioBuilder firstBuilder
            = new PortfolioImpl.PortfolioBuilder("Test " + "Portfolio1");
    firstBuilder.addShare("Apple Inc", 10);
    StockData api = new StockData();
    portfolioDir.addPortfolio(firstBuilder);
    try {
      assertEquals(1000.749,
              portfolioDir.portfolioValue(0, 1, 12, 2024, api), 0.001);
    } catch (IllegalArgumentException e) {
      assertEquals("AAPL", e.getMessage());
    }
    LocalDate currentDate = LocalDate.now();
    String path = System.getProperty("user.dir") + "/Data/" + currentDate;

    File appleData = new File(path + "/AAPL.csv");
    assertTrue(appleData.exists());

  }

  @Test
  public void testPortfolioComposition() {

    PortfolioImpl.PortfolioBuilder firstBuilder
            = new PortfolioImpl.PortfolioBuilder("Test " + "Portfolio");
    firstBuilder.addShare("Apple Inc", 10);
    //share name given
    firstBuilder.addShare("Advanced Battery Technologies Inc", 20);
    portfolioDir.addPortfolio(firstBuilder);
    LocalDate date = LocalDate.of(2024, 3, 20);
    Map<String, Integer> composition = portfolioDir.portfolioComposition(0,date);

    assertEquals(2, composition.size());
    assertEquals(10, (int) composition.get("AAPL"));
    assertEquals(20, (int) composition.get("ABAT"));


    PortfolioImpl.PortfolioBuilder secondBuilder
            = new PortfolioImpl.PortfolioBuilder("Test " + "Portfolio2");
    // TICKER SYMBOL
    secondBuilder.addShare("AAON", 10);
    secondBuilder.addShare("ABEO", 20);
    secondBuilder.addShare("CAN", 30);
    portfolioDir.addPortfolio(secondBuilder);
    Map<String, Integer> composition2 = portfolioDir.portfolioComposition(1,date);

    assertEquals(2, portfolioDir.getSize());

    assertEquals(3, composition2.size());
    assertEquals(10, (int) composition2.get("AAON"));
    assertEquals(20, (int) composition2.get("ABEO"));
    assertEquals(30, (int) composition2.get("CAN"));

    Map<String, String> listOfPortfolios = portfolioDir.getListOfPortfoliosName();

    List<String> portfolioNames = new ArrayList<>(listOfPortfolios.keySet());

    assertEquals("Test Portfolio", portfolioNames.get(0));
    assertEquals("Test Portfolio2", portfolioNames.get(1));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testPortfolioOfSameNameCreated() {
    PortfolioImpl.PortfolioBuilder firstBuilder
            = new PortfolioImpl.PortfolioBuilder("Test " + "Portfolio1");
    firstBuilder.addShare("Apple Inc", 10);
    firstBuilder.addShare("GOOG", 10);
    portfolioDir.addPortfolio(firstBuilder);
    PortfolioImpl.PortfolioBuilder secondBuilder
            = new PortfolioImpl.PortfolioBuilder("Test " + "Portfolio1");
    firstBuilder.addShare("Apple Inc", 10);
    firstBuilder.addShare("CAN", 30);
    portfolioDir.addPortfolio(secondBuilder);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testPortfolioValueForPortfolioThatDoesnotExists() {
    PortfolioImpl.PortfolioBuilder firstBuilder =
            new PortfolioImpl.PortfolioBuilder("Test " + "Portfolio1");
    StockData api = new StockData();
    firstBuilder.addShare("Apple Inc", 10);
    firstBuilder.addShare("GOOG", 10);
    portfolioDir.addPortfolio(firstBuilder);
    portfolioDir.portfolioValue(1, 1, 1, 2022, api);

  }

  @Test(expected = IllegalArgumentException.class)
  public void testPortfolioValueForDayWrong() {
    PortfolioImpl.PortfolioBuilder firstBuilder
            = new PortfolioImpl.PortfolioBuilder("Test " + "Portfolio1");
    StockData api = new StockData();
    firstBuilder.addShare("Apple Inc", 10);
    firstBuilder.addShare("GOOG", 10);
    portfolioDir.addPortfolio(firstBuilder);
    portfolioDir.portfolioValue(0, 32, 1, 2022, api);
  }


  @Test(expected = IllegalArgumentException.class)
  public void testPortfolioValueForDayNegWrong() {
    PortfolioImpl.PortfolioBuilder firstBuilder
            = new PortfolioImpl.PortfolioBuilder("Test " + "Portfolio1");
    StockData api = new StockData();
    firstBuilder.addShare("Apple Inc", 10);
    firstBuilder.addShare("GOOG", 10);
    portfolioDir.addPortfolio(firstBuilder);
    portfolioDir.portfolioValue(0, -1, 1, 2022, api);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testPortfolioValueForDayZero() {
    PortfolioImpl.PortfolioBuilder firstBuilder
            = new PortfolioImpl.PortfolioBuilder("Test " + "Portfolio1");
    StockData api = new StockData();
    firstBuilder.addShare("Apple Inc", 10);
    firstBuilder.addShare("GOOG", 10);
    portfolioDir.addPortfolio(firstBuilder);
    portfolioDir.portfolioValue(0, 0, 1, 2022, api);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testPortfolioValueForDayBigNumber() {
    PortfolioImpl.PortfolioBuilder firstBuilder
            = new PortfolioImpl.PortfolioBuilder("Test " + "Portfolio1");
    StockData api = new StockData();
    firstBuilder.addShare("Apple Inc", 10);
    firstBuilder.addShare("GOOG", 10);
    portfolioDir.addPortfolio(firstBuilder);
    portfolioDir.portfolioValue(0, 1000, 1, 2022, api);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testPortfolioValueForMonthBigNumber() {
    PortfolioImpl.PortfolioBuilder firstBuilder
            = new PortfolioImpl.PortfolioBuilder("Test " + "Portfolio1");
    StockData api = new StockData();
    firstBuilder.addShare("Apple Inc", 10);
    firstBuilder.addShare("GOOG", 10);
    portfolioDir.addPortfolio(firstBuilder);
    portfolioDir.portfolioValue(0, 1, 1000, 2022, api);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testPortfolioValueForMonthZero() {
    PortfolioImpl.PortfolioBuilder firstBuilder
            = new PortfolioImpl.PortfolioBuilder("Test " + "Portfolio1");
    StockData api = new StockData();
    firstBuilder.addShare("Apple Inc", 10);
    firstBuilder.addShare("GOOG", 10);
    portfolioDir.addPortfolio(firstBuilder);
    portfolioDir.portfolioValue(0, 1, 0, 2022, api);
  }


  @Test(expected = IllegalArgumentException.class)
  public void testPortfolioValueForMonthNeg() {
    PortfolioImpl.PortfolioBuilder firstBuilder
            = new PortfolioImpl.PortfolioBuilder("Test " + "Portfolio1");
    StockData api = new StockData();
    firstBuilder.addShare("Apple Inc", 10);
    firstBuilder.addShare("GOOG", 10);
    portfolioDir.addPortfolio(firstBuilder);
    portfolioDir.portfolioValue(0, 1, -1, 2022, api);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testPortfolioValueForMonthWrong() {
    PortfolioImpl.PortfolioBuilder firstBuilder
            = new PortfolioImpl.PortfolioBuilder("Test " + "Portfolio1");
    StockData api = new StockData();
    firstBuilder.addShare("Apple Inc", 10);
    firstBuilder.addShare("GOOG", 10);
    portfolioDir.addPortfolio(firstBuilder);
    portfolioDir.portfolioValue(0, 1, 13, 2022, api);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testPortfolioValueForYearWrong() {
    PortfolioImpl.PortfolioBuilder firstBuilder
            = new PortfolioImpl.PortfolioBuilder("Test " + "Portfolio1");
    StockData api = new StockData();
    firstBuilder.addShare("Apple Inc", 10);
    firstBuilder.addShare("GOOG", 10);
    portfolioDir.addPortfolio(firstBuilder);
    portfolioDir.portfolioValue(0, 1, 12, 20220, api);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testPortfolioValueForYearNeg() {
    PortfolioImpl.PortfolioBuilder firstBuilder
            = new PortfolioImpl.PortfolioBuilder("Test " + "Portfolio1");
    StockData api = new StockData();
    firstBuilder.addShare("Apple Inc", 10);
    firstBuilder.addShare("GOOG", 10);
    portfolioDir.addPortfolio(firstBuilder);
    portfolioDir.portfolioValue(0, 1, 12, -2, api);
  }

  @Test
  public void testPortfolioValueForYearInSingleDigit() {
    PortfolioImpl.PortfolioBuilder firstBuilder
            = new PortfolioImpl.PortfolioBuilder("Test " + "Portfolio1");
    StockData api = new StockData();
    firstBuilder.addShare("Apple Inc", 10);
    portfolioDir.addPortfolio(firstBuilder);
    try {
      assertEquals(1000.749,
              portfolioDir.portfolioValue(0, 1, 12, 2, api), 0.001);
    } catch (IllegalArgumentException e) {
      assertEquals("AAPL", e.getMessage());
    }

    LocalDate currentDate = LocalDate.now();
    String path = System.getProperty("user.dir") + "/Data/" + currentDate;
    File appleData = new File(path + "/AAPL.csv");
    assertTrue(appleData.exists());
  }

  @Test
  public void testPortfolioValueForYearInThreeDigit() {
    PortfolioImpl.PortfolioBuilder firstBuilder
            = new PortfolioImpl.PortfolioBuilder("Test " + "Portfolio1");
    StockData api = new StockData();
    firstBuilder.addShare("Apple Inc", 10);
    portfolioDir.addPortfolio(firstBuilder);
    try {
      assertEquals(1000.749,
              portfolioDir.portfolioValue(0, 1, 12, 200, api), 0.001);
    } catch (IllegalArgumentException e) {
      assertEquals("AAPL", e.getMessage());
    }

    LocalDate currentDate = LocalDate.now();
    String path = System.getProperty("user.dir") + "/Data/" + currentDate;
    File appleData = new File(path + "/AAPL.csv");
    assertTrue(appleData.exists());

  }


  @Test
  public void testPortfolioValueForYearInFourDigitAvailableData() {
    PortfolioImpl.PortfolioBuilder firstBuilder
            = new PortfolioImpl.PortfolioBuilder("Test " + "Portfolio1");
    StockData api = new StockData();
    firstBuilder.addShare("Apple Inc", 10);
    firstBuilder.addShare("GOOG", 10);
    portfolioDir.addPortfolio(firstBuilder);
    assertEquals(3177.4,
            portfolioDir.portfolioValue(0, 1, 3, 2024, api), 0.001);


    LocalDate currentDate = LocalDate.now();
    String path = System.getProperty("user.dir") + "/Data/" + currentDate;

    File appleData = new File(path + "/AAPL.csv");
    File googData = new File(path + "/GOOG.csv");
    assertTrue(appleData.exists());
    assertTrue(googData.exists());

  }

  @Test
  public void testAvailableDataForDateMonthAsTwoDigit() {
    PortfolioImpl.PortfolioBuilder firstBuilder
            = new PortfolioImpl.PortfolioBuilder("Test " + "Portfolio1");
    StockData api = new StockData();
    firstBuilder.addShare("Apple Inc", 10);
    firstBuilder.addShare("GOOG", 10);
    portfolioDir.addPortfolio(firstBuilder);
    assertEquals(3177.4,
            portfolioDir.portfolioValue(0, 01, 03, 2024, api), 0.001);

    LocalDate currentDate = LocalDate.now();
    String path = System.getProperty("user.dir") + "/Data/" + currentDate;
    File appleData = new File(path + "/AAPL.csv");
    File googData = new File(path + "/GOOG.csv");
    assertTrue(appleData.exists());
    assertTrue(googData.exists());

  }

  @Test(expected = IllegalArgumentException.class)
  public void testPortfolioValueForWrongDate() {
    PortfolioImpl.PortfolioBuilder firstBuilder
            = new PortfolioImpl.PortfolioBuilder("Test " + "Portfolio1");
    StockData api = new StockData();
    firstBuilder.addShare("Apple Inc", 10);
    firstBuilder.addShare("GOOG", 10);
    portfolioDir.addPortfolio(firstBuilder);
    portfolioDir.portfolioValue(0, 30, 2, 2024, api);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testPortfolioValueForWrongDateDay() {
    PortfolioImpl.PortfolioBuilder firstBuilder
            = new PortfolioImpl.PortfolioBuilder("Test " + "Portfolio1");
    StockData api = new StockData();
    firstBuilder.addShare("Apple Inc", 10);
    firstBuilder.addShare("GOOG", 10);
    portfolioDir.addPortfolio(firstBuilder);
    portfolioDir.portfolioValue(0, 31, 4, 2023, api);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testPortfolioValueForWrongDateDayFeb() {
    PortfolioImpl.PortfolioBuilder firstBuilder
            = new PortfolioImpl.PortfolioBuilder("Test " + "Portfolio1");
    StockData api = new StockData();
    firstBuilder.addShare("Apple Inc", 10);
    firstBuilder.addShare("GOOG", 10);
    portfolioDir.addPortfolio(firstBuilder);
    portfolioDir.portfolioValue(0, 29, 2, 2023, api);
  }


  @Test(expected = IllegalArgumentException.class)
  public void testPortfolioCompositionInputPortfolioNeg() {
    PortfolioImpl.PortfolioBuilder firstBuilder
            = new PortfolioImpl.PortfolioBuilder("Test " + "Portfolio");
    firstBuilder.addShare("Apple Inc", 10);
    //share name given
    firstBuilder.addShare("Advanced Battery Technologies Inc", 20);
    portfolioDir.addPortfolio(firstBuilder);
    LocalDate date = LocalDate.of(2024, 3, 20);
    Map<String, Integer> composition = portfolioDir.portfolioComposition(-1,date);

  }


  @Test(expected = IllegalArgumentException.class)
  public void testPortfolioCompositionInputPortfolioGreaterThanListOfPortfolio() {
    PortfolioImpl.PortfolioBuilder firstBuilder
            = new PortfolioImpl.PortfolioBuilder("Test " + "Portfolio");
    firstBuilder.addShare("Apple Inc", 10);
    //share name given
    firstBuilder.addShare("Advanced Battery Technologies Inc", 20);
    portfolioDir.addPortfolio(firstBuilder);
    LocalDate date = LocalDate.of(2024, 3, 20);
    Map<String, Integer> composition = portfolioDir.portfolioComposition(2,date);
  }

  @Test
  public void testValueOfPortfolioSingleDate() {
    PortfolioImpl.PortfolioBuilder firstBuilder
            = new PortfolioImpl.PortfolioBuilder("Test " + "Portfolio1");
    StockData api = new StockData();
    firstBuilder.addShare("Apple Inc", 10);
    firstBuilder.addShare("Canaan Inc", 10);
    firstBuilder.addShare("Can B Corp", 10);
    portfolioDir.addPortfolio(firstBuilder);
    assertEquals(1, portfolioDir.getSize());

    assertEquals(1717.749,
            portfolioDir.portfolioValue(0, 5, 3, 2024, api), 0.001);

    LocalDate currentDate = LocalDate.now();
    String path = System.getProperty("user.dir") + "/Data/" + currentDate;

    File appleData = new File(path + "/AAPL.csv");
    File canaanData = new File(path + "/CAN.csv");
    File canbData = new File(path + "/CANB.csv");
    assertTrue(appleData.exists());
    assertTrue(canaanData.exists());
    assertTrue(canbData.exists());

  }


  @Test
  public void testValueOfPortfolioOnWeekend() {
    PortfolioImpl.PortfolioBuilder firstBuilder
            = new PortfolioImpl.PortfolioBuilder("Test " + "Portfolio");
    StockData api = new StockData();
    firstBuilder.addShare("Can B Corp", 10);
    portfolioDir.addPortfolio(firstBuilder);
    assertEquals(1, portfolioDir.getSize());

    try {
      assertEquals(1717.749,
              portfolioDir.portfolioValue(0, 3, 3, 2024,api), 0.001);
    } catch (IllegalArgumentException e) {
      assertEquals("CANB", e.getMessage());
    }
  }


  @Test
  public void testValueOfPortfolioBeforeListing() {
    PortfolioImpl.PortfolioBuilder firstBuilder
            = new PortfolioImpl.PortfolioBuilder("Test " + "Portfolio");
    StockData api = new StockData();
    firstBuilder.addShare("Apple Inc", 10);
    portfolioDir.addPortfolio(firstBuilder);
    assertEquals(1, portfolioDir.getSize());


    try {
      assertEquals(1717.749,
              portfolioDir.portfolioValue(0, 3, 3, 1890,api), 0.001);
    } catch (IllegalArgumentException e) {
      assertEquals("AAPL", e.getMessage());
    }
  }


  @Test
  public void testValueOfPortfolioManyDate() {
    PortfolioImpl.PortfolioBuilder firstBuilder
            = new PortfolioImpl.PortfolioBuilder("Test " + "Portfolio");
    firstBuilder.addShare("Apple Inc", 10);
    firstBuilder.addShare("Canaan Inc", 10);
    firstBuilder.addShare("Can B Corp", 10);
    StockData api = new StockData();
    portfolioDir.addPortfolio(firstBuilder);
    assertEquals(1, portfolioDir.getSize());

    assertEquals(1717.749,
            portfolioDir.portfolioValue(0, 5, 3, 2024,api), 0.001);
    assertEquals(1707.92,
            portfolioDir.portfolioValue(0, 6, 3, 2024,api), 0.001);
    assertEquals(1724.0249999999999,
            portfolioDir.portfolioValue(0, 8, 3, 2024,api), 0.001);

    LocalDate currentDate = LocalDate.now();
    String path = System.getProperty("user.dir") + "/Data/" + currentDate;

    File appleData = new File(path + "/AAPL.csv");
    File canaanData = new File(path + "/CAN.csv");
    File canbData = new File(path + "/CANB.csv");
    assertTrue(appleData.exists());
    assertTrue(canaanData.exists());
    assertTrue(canbData.exists());

  }


  @Test
  public void testValueOfTwoPortfoliosWithSameStocks() {
    PortfolioImpl.PortfolioBuilder firstBuilder
            = new PortfolioImpl.PortfolioBuilder("Test " + "Portfolio");
    StockData api = new StockData();
    firstBuilder.addShare("Apple Inc", 10);
    firstBuilder.addShare("Canaan Inc", 10);
    firstBuilder.addShare("Can B Corp", 10);
    portfolioDir.addPortfolio(firstBuilder);
    assertEquals(1, portfolioDir.getSize());

    assertEquals(1717.749,
            portfolioDir.portfolioValue(0, 5, 3, 2024,api), 0.001);
    assertEquals(1707.92,
            portfolioDir.portfolioValue(0, 6, 3, 2024,api), 0.001);
    assertEquals(1724.0249999999999,
            portfolioDir.portfolioValue(0, 8, 3, 2024,api), 0.001);


    PortfolioImpl.PortfolioBuilder secondBuilder
            = new PortfolioImpl.PortfolioBuilder("Test " + "Portfolio1");
    secondBuilder.addShare("Apple Inc", 20);
    secondBuilder.addShare("Canaan Inc", 20);
    secondBuilder.addShare("Can B Corp", 50);
    portfolioDir.addPortfolio(secondBuilder);
    assertEquals(2, portfolioDir.getSize());

    assertEquals(3438.045,
            portfolioDir.portfolioValue(1, 5, 3, 2024,api), 0.001);
    assertEquals(3418.6,
            portfolioDir.portfolioValue(1, 6, 3, 2024,api), 0.001);
    assertEquals(3451.4249999999997,
            portfolioDir.portfolioValue(1, 8, 3, 2024,api), 0.001);

    Map<String, String> listOfPortfolios = portfolioDir.getListOfPortfoliosName();

    List<String> portfolioNames = new ArrayList<>(listOfPortfolios.keySet());

    assertEquals("Test Portfolio1", portfolioNames.get(0));
    assertEquals("Test Portfolio", portfolioNames.get(1));


    LocalDate currentDate = LocalDate.now();
    String path = System.getProperty("user.dir") + "/Data/" + currentDate;
    File appleData = new File(path + "/AAPL.csv");
    File canaanData = new File(path + "/CAN.csv");
    File canbData = new File(path + "/CANB.csv");
    assertTrue(appleData.exists());
    assertTrue(canaanData.exists());
    assertTrue(canbData.exists());

  }


  @Test
  public void testValueOfManyPortfolioManyDateHavingSomeSameShare() {
    PortfolioImpl.PortfolioBuilder newBuilder
            = new PortfolioImpl.PortfolioBuilder("college fund");
    StockData api = new StockData();
    newBuilder.addShare("Apple Inc", 10);
    newBuilder.addShare("Canaan Inc", 10);
    newBuilder.addShare("Can B Corp", 10);
    portfolioDir.addPortfolio(newBuilder);
    assertEquals(1, portfolioDir.getSize());

    assertEquals(1717.749,
            portfolioDir.portfolioValue(0, 5, 3, 2024,api), 0.001);
    assertEquals(1724.0249999999999,
            portfolioDir.portfolioValue(0, 8, 3, 2024,api),
            0.001);

    PortfolioImpl.PortfolioBuilder second_builder
            = new PortfolioImpl.PortfolioBuilder("college " + "fund2");
    second_builder.addShare("Apple Inc", 10);
    second_builder.addShare("Canaan Inc", 10);
    second_builder.addShare("Capricor Therapeutics Inc", 10);
    portfolioDir.addPortfolio(second_builder);
    assertEquals(2, portfolioDir.getSize());


    assertEquals(1766.45,
            portfolioDir.portfolioValue(1, 5, 3, 2024,api), 0.001);
    assertEquals(1813.4,
            portfolioDir.portfolioValue(1, 4, 3, 2024,api), 0.001);

    LocalDate currentDate = LocalDate.now();
    String path = System.getProperty("user.dir") + "/Data/" + currentDate;
    File appleData = new File(path + "/AAPL.csv");
    File canaanData = new File(path + "/CAN.csv");
    File canbData = new File(path + "/CANB.csv");
    File capricorData = new File(path + "/CAPR.csv");
    assertTrue(appleData.exists());
    assertTrue(canaanData.exists());
    assertTrue(canbData.exists());
    assertTrue(capricorData.exists());

  }


  @Test
  public void testIsEmptyWhenEmpty() {
    assertTrue(portfolioDir.isEmpty());
  }

  @Test
  public void testIsEmptyAfterAddingElements() {
    PortfolioImpl.PortfolioBuilder firstBuilder
            = new PortfolioImpl.PortfolioBuilder("oldage fund");
    firstBuilder.addShare("Apple Inc", 10);
    PortfolioImpl.PortfolioBuilder secondBuilder =
            new PortfolioImpl.PortfolioBuilder("college fund");
    secondBuilder.addShare("Goog", 10);
    portfolioDir.addPortfolio(firstBuilder);
    portfolioDir.addPortfolio(secondBuilder);

    assertFalse(portfolioDir.isEmpty());
    assertEquals(2, portfolioDir.getSize());

    Map<String, String> listOfPortfolios = portfolioDir.getListOfPortfoliosName();

    List<String> portfolioNames = new ArrayList<>(listOfPortfolios.keySet());

    assertEquals("college fund", portfolioNames.get(0));
    assertEquals("oldage fund", portfolioNames.get(1));

  }

  @Test
  public void testExistsWhenPortfolioExists() {
    PortfolioImpl.PortfolioBuilder newBuilder
            = new PortfolioImpl.PortfolioBuilder("oldage fund");
    newBuilder.addShare("Apple Inc", 10);
    portfolioDir.addPortfolio(newBuilder);

    // Check if the portfolio exists
    assertTrue(portfolioDir.portfolioNameExists("oldage fund"));
    assertFalse(portfolioDir.portfolioNameExists("college fund"));

  }

  @Test
  public void testWrongAddShare() {
    PortfolioImpl.PortfolioBuilder newBuilder
            = new PortfolioImpl.PortfolioBuilder("Test " + "Portfolio1");
    newBuilder.addShare("Apple Inc", 10);
    try {
      newBuilder.addShare("Appple", 10);
      portfolioDir.addPortfolio(newBuilder);
    } catch (IllegalArgumentException e) {
      assertEquals("Share name not found in stocks.csv", e.getMessage());
    }
  }


  @Test
  public void testPortfolioValueFor23StocksDifferent() {
    PortfolioImpl.PortfolioBuilder firstBuilder
            = new PortfolioImpl.PortfolioBuilder("Test " + "Portfolio1");
    StockData api = new StockData();

    firstBuilder.addShare("Apple Inc", 10);
    firstBuilder.addShare("GOOG", 10);
    firstBuilder.addShare("Avis Budget Group Inc", 10);
    firstBuilder.addShare("Maplebear Inc", 10);
    firstBuilder.addShare("Carver Bancorp Inc", 10);
    firstBuilder.addShare("Casa Systems Inc", 10);
    firstBuilder.addShare("Pathward Financial Inc", 10);
    firstBuilder.addShare("CASI Pharmaceuticals Inc", 10);
    firstBuilder.addShare("Cambridge Bancorp", 10);
    firstBuilder.addShare("Collective Audience Inc", 10);
    firstBuilder.addShare("CBAK Energy Technology Inc", 10);
    firstBuilder.addShare("First Bancorp", 10);
    firstBuilder.addShare("Forte Biosciences Inc", 10);
    firstBuilder.addShare("First Community Corp", 10);
    firstBuilder.addShare("FormFactor Inc", 10);
    firstBuilder.addShare("Great Elm Group Inc", 10);
    firstBuilder.addShare("Geron Corp", 10);
    firstBuilder.addShare("Gevo Inc", 10);
    firstBuilder.addShare("Galapagos NV", 10);
    firstBuilder.addShare("Gamida Cell Ltd", 10);
    firstBuilder.addShare("casy", 10);
    firstBuilder.addShare("CCB", 10);
    firstBuilder.addShare("bof", 10);


    portfolioDir.addPortfolio(firstBuilder);
    LocalDate date = LocalDate.of(2024, 3, 20);
    Map<String, Integer> composition = portfolioDir.portfolioComposition(0,date);

    assertEquals(10663.179,
            portfolioDir.portfolioValue(0, 1, 3, 2024,api), 0.001);

    assertEquals(23, composition.size());
    assertEquals(10, (int) composition.get("AAPL"));
    assertEquals(10, (int) composition.get("GOOG"));
    assertEquals(10, (int) composition.get("CAR"));
    assertEquals(10, (int) composition.get("CART"));
    assertEquals(10, (int) composition.get("CARV"));
    assertEquals(10, (int) composition.get("CASA"));
    assertEquals(10, (int) composition.get("CASH"));
    assertEquals(10, (int) composition.get("CASI"));
    assertEquals(10, (int) composition.get("CATC"));
    assertEquals(10, (int) composition.get("CAUD"));
    assertEquals(10, (int) composition.get("CBAT"));
    assertEquals(10, (int) composition.get("FBNC"));
    assertEquals(10, (int) composition.get("FBRX"));
    assertEquals(10, (int) composition.get("FCCO"));
    assertEquals(10, (int) composition.get("FORM"));
    assertEquals(10, (int) composition.get("GEG"));
    assertEquals(10, (int) composition.get("GERN"));
    assertEquals(10, (int) composition.get("GEVO"));
    assertEquals(10, (int) composition.get("GLPG"));
    assertEquals(10, (int) composition.get("GMDA"));
    assertEquals(10, (int) composition.get("CASY"));
    assertEquals(10, (int) composition.get("CCB"));
    assertEquals(10, (int) composition.get("BOF"));

  }

  @Test
  public void testAddShare() {
    PortfolioImpl.PortfolioBuilder portfolioBuilder
            = new PortfolioImpl.PortfolioBuilder("Test Portfolio");

    // Adding a valid share
    portfolioBuilder.addShare("Apple Inc", 10);

    // Adding another share with existing share name
    portfolioBuilder.addShare("Goog", 5);

    // Adding share with invalid name
    try {
      portfolioBuilder.addShare("Invalid Share", 5);
    } catch (IllegalArgumentException e) {
      assertEquals("Share name not found in stocks.csv", e.getMessage());
    }

    // Adding share with negative quantity
    try {
      portfolioBuilder.addShare("BOF", -5);
    } catch (IllegalArgumentException e) {
      assertEquals("Quantity should be whole number.", e.getMessage());
    }

    // Adding share with zero quantity
    try {
      portfolioBuilder.addShare("BPTS", 0);
    } catch (IllegalArgumentException e) {
      assertEquals("Quantity should be whole number.", e.getMessage());
    }
    portfolioDir.addPortfolio(portfolioBuilder);
    LocalDate date = LocalDate.of(2024, 3, 20);
    Map<String, Integer> composition = portfolioDir.portfolioComposition(0,date);
    assertEquals(2, composition.size());
    assertNotNull(portfolioBuilder);
    assertEquals(1, portfolioDir.getSize());
  }

  @Test
  public void testBuild() {
    PortfolioImpl.PortfolioBuilder portfolioBuilder
            = new PortfolioImpl.PortfolioBuilder("Test Portfolio");

    portfolioBuilder.addShare("Apple Inc", 10);
    portfolioBuilder.addShare("GOOG", 5);


    portfolioDir.addPortfolio(portfolioBuilder);
    assertEquals(1, portfolioDir.getSize());
    LocalDate date = LocalDate.of(2024, 3, 20);
    Map<String, Integer> composition = portfolioDir.portfolioComposition(0,date);
    assertEquals(2, composition.size());
    assertEquals(10, (int) composition.get("AAPL"));
    assertEquals(5, (int) composition.get("GOOG"));
    assertNotNull(portfolioBuilder);

    PortfolioImpl.PortfolioBuilder emptyPortfolioBuilder
            = new PortfolioImpl.PortfolioBuilder("Empty Portfolio");

    try {
      emptyPortfolioBuilder.build();
    } catch (IllegalArgumentException e) {
      // catches illegal argument exception
    }
  }


  @Test
  public void testLoad() {
    PortfolioImpl.PortfolioBuilder portfolioBuilder
            = new PortfolioImpl.PortfolioBuilder("Test Portfolio");
    StockData api = new StockData();

    List<String[]> validLines = new ArrayList<>();
    validLines.add(new String[]{"AAPL", "10"});
    validLines.add(new String[]{"GOOG", "5"});

    portfolioBuilder.load(validLines);

    portfolioDir.addPortfolio(portfolioBuilder);
    LocalDate date = LocalDate.of(2024, 3, 20);
    Map<String, Integer> composition = portfolioDir.portfolioComposition(0,date);
    assertEquals(2, composition.size());
    assertEquals(10, (int) composition.get("AAPL"));
    assertEquals(5, (int) composition.get("GOOG"));

    assertEquals(2487.0,
            portfolioDir.portfolioValue(0, 1, 3, 2024,api), 0.001);
    assertEquals(1, portfolioDir.getSize());
    assertNotNull(portfolioBuilder);

  }


  @Test
  public void testLoadInvalid() {
    PortfolioImpl.PortfolioBuilder portfolioBuilder
            = new PortfolioImpl.PortfolioBuilder("Test Portfolio");

    List<String[]> invalidLines = new ArrayList<>();
    invalidLines.add(new String[]{"AAPL"});
    invalidLines.add(new String[]{"GOOG", "5", "Extra"});

    try {
      portfolioBuilder.load(invalidLines);
    } catch (IllegalArgumentException e) {
      // catches illegal argument exception
    }

    PortfolioImpl.PortfolioBuilder portfolioBuilder2
            = new PortfolioImpl.PortfolioBuilder("Test Portfolio2");

    List<String[]> linesWithNegativeQuantity = new ArrayList<>();
    linesWithNegativeQuantity.add(new String[]{"AAPL", "-10"});

    try {
      portfolioBuilder2.load(linesWithNegativeQuantity);
    } catch (IllegalArgumentException e) {
      // catches illegal argument exception
    }

    PortfolioImpl.PortfolioBuilder portfolioBuilder3
            = new PortfolioImpl.PortfolioBuilder("Test Portfolio3");

    List<String[]> linesWithNonIntegerQuantity = new ArrayList<>();
    linesWithNonIntegerQuantity.add(new String[]{"AAPL", "invalid"});

    try {
      portfolioBuilder3.load(linesWithNonIntegerQuantity);
    } catch (IllegalArgumentException e) {
      // catches illegal argument exception
    }

    PortfolioImpl.PortfolioBuilder portfolioBuilder4
            = new PortfolioImpl.PortfolioBuilder("Test Portfolio4");
    List<String[]> linesWithNonExistingStock = new ArrayList<>();
    linesWithNonExistingStock.add(new String[]{"INVALID", "10"});

    try {
      portfolioBuilder4.load(linesWithNonExistingStock);
    } catch (IllegalArgumentException e) {
      assertEquals("Share name not found in stocks.csv", e.getMessage());
    }
  }

  @Test
  public void testTypeOfPortfolio() {
    PortfolioImpl.PortfolioBuilder portfolioBuilder
            = new PortfolioImpl.PortfolioBuilder("Test Portfolio");

    portfolioBuilder.addShare("Apple Inc", 10);
    portfolioBuilder.addShare("GOOG", 5);

    portfolioDir.addPortfolio(portfolioBuilder);
    assertEquals(1, portfolioDir.getSize());

    portfolioDir.createFlexiblePortfolio("Test Portfolio2");
    assertEquals(2, portfolioDir.getSize());

    Map<String, String> listOfPortfolios = portfolioDir.getListOfPortfoliosName();

    String portfolioName = "Test Portfolio";
    String portfolioValue = listOfPortfolios.get(portfolioName);
    String portfolioName2 = "Test Portfolio2";
    String portfolioValue2 = listOfPortfolios.get(portfolioName2);


    assertEquals("Expected value for Test Portfolio", "Inflexible", portfolioValue);
    assertEquals("Expected value for Test Portfolio", "Flexible", portfolioValue2);
  }

  @Test
  public void testFlexiblePortfolioCreate() {
    portfolioDir.createFlexiblePortfolio("Test Portfolio1");
    assertEquals(1, portfolioDir.getSize());
    Map<String, String> listOfPortfolios = portfolioDir.getListOfPortfoliosName();
    String portfolioName = "Test Portfolio1";
    String portfolioValue = listOfPortfolios.get(portfolioName);
    assertEquals("Expected value for Test Portfolio", "Flexible", portfolioValue);
  }

  @Test
  public void testIsFlexible() {
    String portfolioName = "Flexible Portfolio";
    portfolioDir.createFlexiblePortfolio(portfolioName);
    assertTrue(portfolioDir.portfolioNameExists(portfolioName));
    //assertTrue(portfolioDir.isFlexible(portfolioName));
  }


  @Test(expected = IllegalArgumentException.class)
  public void testPortfolioOfSameNameCreatedFlexible() {
    portfolioDir.createFlexiblePortfolio("Test Portfolio1");
    assertEquals(1, portfolioDir.getSize());
    portfolioDir.createFlexiblePortfolio("Test Portfolio1");
  }

  @Test
  public void testCreatingFlexibleInflexibleList() {
    portfolioDir.createFlexiblePortfolio("Test Portfolio1");
    assertEquals(1, portfolioDir.getSize());
    PortfolioImpl.PortfolioBuilder newBuilder
            = new PortfolioImpl.PortfolioBuilder("college fund");
    newBuilder.addShare("Apple Inc", 10);
    newBuilder.addShare("Canaan Inc", 10);
    newBuilder.addShare("Can B Corp", 10);
    portfolioDir.addPortfolio(newBuilder);
    assertEquals(2, portfolioDir.getSize());

    Map<String, String> listOfPortfolios = portfolioDir.getListOfPortfoliosName();

    List<String> portfolioNames = new ArrayList<>(listOfPortfolios.keySet());

    assertEquals("Test Portfolio1", portfolioNames.get(0));
    assertEquals("college fund", portfolioNames.get(1));

    String portfolioName = "Test Portfolio1";
    String portfolioValue = listOfPortfolios.get(portfolioName);
    String portfolioName2 = "college fund";
    String portfolioValue2 = listOfPortfolios.get(portfolioName2);


    assertEquals("Expected value for Test Portfolio", "Flexible", portfolioValue);
    assertEquals("Expected value for Test Portfolio", "Inflexible", portfolioValue2);

  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetCompositionPortfolioFlexiNeg() {
    portfolioDir.createFlexiblePortfolio("Test Portfolio1");
    assertEquals(1, portfolioDir.getSize());
    LocalDate date = LocalDate.of(2024, 3, 20);
    portfolioDir.portfolioComposition(-1,date);
  }

  @Test
  public void testGetCompositionPortfolioFlexiEmpty() {
    portfolioDir.createFlexiblePortfolio("Test Portfolio1");
    assertEquals(1, portfolioDir.getSize());
    LocalDate date = LocalDate.of(2024, 3, 20);
    portfolioDir.portfolioComposition(0,date);
    //composition is empty
  }

//  @Test(expected = IllegalArgumentException.class)
//  public void testGetCompositionFlexiCallInflexiComposition() {
//    portfolioDir.createFlexiblePortfolio("Test Portfolio1");
//    assertEquals(1, portfolioDir.getSize());
//    LocalDate date = LocalDate.of(2024, 3, 20);
//    portfolioDir.portfolioComposition(0,date);
//  }

  @Test(expected = IllegalArgumentException.class)
  public void buyFlexiblePortolioWrongPortfolio() {
    portfolioDir.createFlexiblePortfolio("Test Portfolio1");
    assertEquals(1, portfolioDir.getSize());
    StockData api = new StockData();
    LocalDate date = LocalDate.of(2024, 3, 20);
    portfolioDir.buyStock(1, "aapl", 10, date, api);
  }

  @Test(expected = IllegalArgumentException.class)
  public void sellFlexiblePortolioWrongPortfolio() {
    portfolioDir.createFlexiblePortfolio("Test Portfolio1");
    assertEquals(1, portfolioDir.getSize());
    StockData api = new StockData();
    LocalDate date = LocalDate.of(2024, 3, 20);
    portfolioDir.sellStock(2, "aapl", 10, date, api);
  }

  @Test
  public void buySellFlexiblePortfolio() {
    portfolioDir.createFlexiblePortfolio("Test Portfolio1");
    assertEquals(1, portfolioDir.getSize());
    StockData api = new StockData();
    LocalDate date = LocalDate.of(2020, 3, 20);
    LocalDate date1 = LocalDate.of(2024, 3, 20);
    LocalDate date2 = LocalDate.of(2023, 3, 20);
    portfolioDir.buyStock(0, "aapl", 10, date, api);
    portfolioDir.buyStock(0, "aapl", 15, date2, api);
    portfolioDir.buyStock(0, "aapl", 20, date1, api);
    portfolioDir.buyStock(0, "goog", 20, date1, api);
    LocalDate date3 = LocalDate.of(2020, 3, 22);
    portfolioDir.portfolioComposition(0,date3);
    Map<String, Integer> composition = portfolioDir.portfolioComposition(0,date3);
    assertEquals(1, composition.size());
    assertEquals(10, (int) composition.get("AAPL"));

    LocalDate date4 = LocalDate.of(2023, 3, 22);
    Map<String, Integer> composition2 = portfolioDir.portfolioComposition(0,date4);
    assertEquals(1, composition2.size());
    assertEquals(25, (int) composition2.get("AAPL"));

    LocalDate date5 = LocalDate.of(2024, 3, 25);
    Map<String, Integer> composition3 = portfolioDir.portfolioComposition(0,date5);
    assertEquals(2, composition3.size());
    assertEquals(45, (int) composition3.get("AAPL"));
    assertEquals(20, (int) composition3.get("GOOG"));

    LocalDate date6 = LocalDate.of(2020, 4, 20);
    LocalDate date7 = LocalDate.of(2020, 4, 23);
    LocalDate date8 = LocalDate.of(2023, 3, 20);
    portfolioDir.sellStock(0, "aapl", 5, date6, api);
    portfolioDir.sellStock(0, "aapl", 5, date7, api);

    LocalDate date9 = LocalDate.of(2020, 4, 22);
    Map<String, Integer> composition4 = portfolioDir.portfolioComposition(0,date9);
    assertEquals(1, composition4.size());
    assertEquals(5, (int) composition4.get("AAPL"));

    LocalDate date10 = LocalDate.of(2020, 4, 24);
    Map<String, Integer> composition5 = portfolioDir.portfolioComposition(0,date10);
    assertEquals(0, composition5.size());
    //all sold out

    LocalDate date12 = LocalDate.of(2024, 3, 22);
    portfolioDir.sellStock(0, "aapl", 10, date12, api);
    portfolioDir.sellStock(0, "goog", 10, date12, api);
    LocalDate date13 = LocalDate.of(2024, 4, 24);
    Map<String, Integer> composition6 = portfolioDir.portfolioComposition(0,date13);
    assertEquals(2, composition6.size());
    assertEquals(25, (int) composition6.get("AAPL"));
    assertEquals(10, (int) composition6.get("GOOG"));

  }

  @Test
  public void testSellWhenAlreadySoldInFuture() {
    portfolioDir.createFlexiblePortfolio("Test Portfolio1");
    assertEquals(1, portfolioDir.getSize());
    StockData api = new StockData();
    LocalDate date = LocalDate.of(2015, 3, 20);
    LocalDate date1 = LocalDate.of(2016, 3, 21);
    LocalDate date2 = LocalDate.of(2017, 3, 20);
    portfolioDir.buyStock(0, "aapl", 10, date, api);
    portfolioDir.buyStock(0, "aapl", 5, date1, api);
    portfolioDir.buyStock(0, "aapl", 1, date2, api);
    //portfolioDir.buyStock(0, "goog", 20, date1, api);
    LocalDate date3 = LocalDate.of(2015, 3, 22);
    portfolioDir.portfolioComposition(0,date3);
    Map<String, Integer> composition = portfolioDir.portfolioComposition(0,date3);
    assertEquals(1, composition.size());
    assertEquals(10, (int) composition.get("AAPL"));

    LocalDate date4 = LocalDate.of(2016, 4, 22);
    Map<String, Integer> composition1 = portfolioDir.portfolioComposition(0,date4);
    assertEquals(1, composition1.size());
    assertEquals(15, (int) composition1.get("AAPL"));

    LocalDate date5 = LocalDate.of(2017, 4, 22);
    Map<String, Integer> composition2 = portfolioDir.portfolioComposition(0,date5);
    assertEquals(1, composition2.size());
    assertEquals(16, (int) composition2.get("AAPL"));

    LocalDate date6 = LocalDate.of(2017, 5, 22);
    LocalDate date7 = LocalDate.of(2018, 4, 23);
    LocalDate date8 = LocalDate.of(2019, 4, 22);
    LocalDate date9 = LocalDate.of(2020, 4, 22);
    portfolioDir.sellStock(0, "aapl", 1, date6, api);
    portfolioDir.sellStock(0, "aapl", 1, date7, api);
    portfolioDir.sellStock(0, "aapl", 1, date8, api);
    portfolioDir.sellStock(0, "aapl", 3, date9, api);

    LocalDate date10 = LocalDate.of(2021, 4, 22);
    Map<String, Integer> composition3 = portfolioDir.portfolioComposition(0,date10);
    assertEquals(1, composition3.size());
    assertEquals(10, (int) composition3.get("AAPL"));

    try {
      portfolioDir.sellStock(0, "aapl", 12, date4, api);
    } catch (IllegalArgumentException e) {
      assertEquals("Invalid sell!", e.getMessage());
    }

    LocalDate date11 = LocalDate.of(2024, 3, 20);
    portfolioDir.buyStock(0, "aapl", 10, date11, api);
    LocalDate date12 = LocalDate.of(2024, 4, 22);
    Map<String, Integer> composition4 = portfolioDir.portfolioComposition(0,date12);
    assertEquals(1, composition4.size());
    assertEquals(20, (int) composition4.get("AAPL"));

    try {
      portfolioDir.sellStock(0, "aapl", 12, date4, api);
    } catch (IllegalArgumentException e) {
      assertEquals("Invalid sell!", e.getMessage());
    }
  }


  @Test
  public void testSellStockWithInvalidTickerSymbol() {
    portfolioDir.createFlexiblePortfolio("Test Portfolio1");
    assertEquals(1, portfolioDir.getSize());
    StockData api = new StockData();
    LocalDate date = LocalDate.of(2015, 3, 20);
    try {
      portfolioDir.sellStock(0, "hello", 10, date, api);
    } catch (IllegalArgumentException e) {
      assertEquals("Ticker symbol doesn't exist", e.getMessage());
    }
  }

  @Test
  public void testSellStockWithWrongDateHoliday() {
    portfolioDir.createFlexiblePortfolio("Test Portfolio1");
    assertEquals(1, portfolioDir.getSize());
    StockData api = new StockData();
    LocalDate date = LocalDate.of(2024, 3, 16);
    try {
      portfolioDir.sellStock(0, "aapl", 10, date, api);
    } catch (IllegalArgumentException e) {
      assertEquals("Wrong date", e.getMessage());
    }
  }

  @Test
  public void testSellStockBeforeBuying() {
    portfolioDir.createFlexiblePortfolio("Test Portfolio1");
    assertEquals(1, portfolioDir.getSize());
    StockData api = new StockData();
    LocalDate date = LocalDate.of(2024, 3, 20);
    try {
      portfolioDir.sellStock(0, "aapl", 10, date, api);
    } catch (IllegalArgumentException e) {
      assertEquals("You can't sell before buying", e.getMessage());
    }
  }

  @Test
  public void testSellStockWithInsufficientQuantity() {
    portfolioDir.createFlexiblePortfolio("Test Portfolio1");
    assertEquals(1, portfolioDir.getSize());
    StockData api = new StockData();
    LocalDate date = LocalDate.of(2024, 3, 20);
    portfolioDir.buyStock(0, "aapl", 10, date, api);
    try {
      portfolioDir.sellStock(0, "aapl", 12, date, api);
    } catch (IllegalArgumentException e) {
      assertEquals("You don't have enough quantity to sell", e.getMessage());
    }
  }

  @Test
  public void testSellStockForNonExistingStock() {
    portfolioDir.createFlexiblePortfolio("Test Portfolio1");
    assertEquals(1, portfolioDir.getSize());
    StockData api = new StockData();
    LocalDate date = LocalDate.of(2024, 3, 20);
    portfolioDir.buyStock(0, "aapl", 10, date, api);
    try {
      portfolioDir.sellStock(0, "goog", 12, date, api);
    } catch (IllegalArgumentException e) {
      assertEquals("You don't have the stock you want to sell", e.getMessage());
    }
  }

  @Test
  public void testBuyStockWithInvalidTickerSymbol() {
    portfolioDir.createFlexiblePortfolio("Test Portfolio1");
    assertEquals(1, portfolioDir.getSize());
    StockData api = new StockData();
    LocalDate date = LocalDate.of(2015, 3, 20);
    try {
      portfolioDir.buyStock(0, "hello", 10, date, api);
    } catch (IllegalArgumentException e) {
      assertEquals("Ticker symbol doesn't exist", e.getMessage());
    }
  }

  @Test
  public void testBuyStockWithWrongDateHoliday() {
    portfolioDir.createFlexiblePortfolio("Test Portfolio1");
    assertEquals(1, portfolioDir.getSize());
    StockData api = new StockData();
    LocalDate date = LocalDate.of(2024, 3, 16);
    try {
      portfolioDir.buyStock(0, "aapl", 10, date, api);
    } catch (IllegalArgumentException e) {
      assertEquals("Wrong date", e.getMessage());
    }
  }

  @Test
  public void testBuyStockWithWrongDateFuture() {
    portfolioDir.createFlexiblePortfolio("Test Portfolio1");
    assertEquals(1, portfolioDir.getSize());
    StockData api = new StockData();
    LocalDate date = LocalDate.of(2024, 12, 1);
    try {
      portfolioDir.buyStock(0, "aapl", 10, date, api);
    } catch (IllegalArgumentException e) {
      assertEquals("Wrong date", e.getMessage());
    }
  }

  @Test
  public void testSellStockWithWrongDateFuture() {
    portfolioDir.createFlexiblePortfolio("Test Portfolio1");
    assertEquals(1, portfolioDir.getSize());
    StockData api = new StockData();
    LocalDate date = LocalDate.of(2024, 12, 1);
    LocalDate date1 = LocalDate.of(2024, 1, 11);
    portfolioDir.buyStock(0, "aapl", 10, date1, api);
    try {
      portfolioDir.sellStock(0, "aapl", 10, date, api);
    } catch (IllegalArgumentException e) {
      assertEquals("Wrong date", e.getMessage());
    }
  }

  @Test
  public void testSimpleGetCompositionFlexible() {
    portfolioDir.createFlexiblePortfolio("Test Portfolio1");
    assertEquals(1, portfolioDir.getSize());
    StockData api = new StockData();
    LocalDate date = LocalDate.of(2024, 3, 20);
    portfolioDir.buyStock(0, "aapl", 15, date, api);
    portfolioDir.buyStock(0, "goog", 10, date, api);
    LocalDate date1 = LocalDate.of(2024, 3, 23);
    Map<String, Integer> composition = portfolioDir.portfolioComposition(0,date1);
    assertEquals(2, composition.size());
    assertEquals(15, (int) composition.get("AAPL"));
    assertEquals(10, (int) composition.get("GOOG"));

    LocalDate date2 = LocalDate.of(2016, 3, 10);
    portfolioDir.buyStock(0, "aapl", 10, date2, api);
    LocalDate date3 = LocalDate.of(2016, 3, 23);
    Map<String, Integer> composition1= portfolioDir.portfolioComposition(0,date3);
    assertEquals(1, composition1.size());
    assertEquals(10, (int) composition1.get("AAPL"));

    LocalDate date4 = LocalDate.of(2024, 3, 23); //saturday
    Map<String, Integer> composition2 = portfolioDir.portfolioComposition(0,date4);
    assertEquals(2, composition2.size());
    assertEquals(25, (int) composition2.get("AAPL"));
    assertEquals(10, (int) composition2.get("GOOG"));

    LocalDate date5 = LocalDate.of(2024, 3, 21);
    portfolioDir.sellStock(0, "goog", 10, date5, api);
    portfolioDir.sellStock(0, "aapl", 11, date5, api);
    Map<String, Integer> composition3 = portfolioDir.portfolioComposition(0,date5);
    assertEquals(1, composition3.size());
    assertEquals(14, (int) composition3.get("AAPL"));
  }

  @Test
  public void testPortfolioValueFlexibleWhenPortfolioEmpty() {
    portfolioDir.createFlexiblePortfolio("Test Portfolio1");
    assertEquals(1, portfolioDir.getSize());
    StockData api = new StockData();
    LocalDate date = LocalDate.of(2024, 3, 20);
    portfolioDir.buyStock(0, "aapl", 15, date, api);
    portfolioDir.buyStock(0, "goog", 10, date, api);
    assertEquals(0.0,
            portfolioDir.portfolioValue(0, 5, 3, 2024,api), 0.001);
  }

  @Test
  public void testPortfolioValueFlexible() {
    portfolioDir.createFlexiblePortfolio("Test Portfolio1");
    assertEquals(1, portfolioDir.getSize());
    StockData api = new StockData();
    LocalDate date = LocalDate.of(2024, 3, 20);
    portfolioDir.buyStock(0, "aapl", 15, date, api);
    portfolioDir.buyStock(0, "goog", 10, date, api);
    assertEquals(4057.9500000000003,
            portfolioDir.portfolioValue(0, 21, 3, 2024,api), 0.001);
  }

//  @Test
//  public void testPortfolioValueFlexibleWhenOneStockNotListed() {
//    portfolioDir.createFlexiblePortfolio("Test Portfolio1");
//    assertEquals(1, portfolioDir.getSize());
//    StockData api = new StockData();
//    LocalDate date = LocalDate.of(2013, 3, 20);
//    portfolioDir.buyStock(0, "aapl", 15, date, api);
//    portfolioDir.buyStock(0, "goog", 10, date, api);
//    assertEquals(4057.9500000000003,
//            portfolioDir.portfolioValue(0, 21, 3, 2012,api), 0.001);
//  }

  @Test
  public void testValueOfPortfolioDiffDateFlexibleBuySell() {
    portfolioDir.createFlexiblePortfolio("Test Portfolio1");
    assertEquals(1, portfolioDir.getSize());
    StockData api = new StockData();
    LocalDate date = LocalDate.of(2020, 2, 3);
    portfolioDir.buyStock(0, "aapl", 15, date, api);
    portfolioDir.buyStock(0, "Apple Inc", 15, date, api);
    portfolioDir.buyStock(0, "Canaan Inc", 15, date, api);
    portfolioDir.buyStock(0, "Can B Corp", 15, date, api);

    assertEquals(5128.423500000001,
            portfolioDir.portfolioValue(0, 5, 3, 2024, api), 0.001);
    assertEquals(4659.825,
            portfolioDir.portfolioValue(0, 6, 3, 2023, api), 0.001);
    assertEquals(5229.6,
            portfolioDir.portfolioValue(0, 7, 2, 2022, api), 0.001);

    LocalDate date1 = LocalDate.of(2022, 2, 3);
    portfolioDir.sellStock(0, "aapl", 15, date1, api);
    portfolioDir.sellStock(0, "CAN", 15, date1, api);

    assertEquals(2553.0735,
            portfolioDir.portfolioValue(0, 5, 3, 2024, api), 0.001);
    assertEquals(2314.7250000000004,
            portfolioDir.portfolioValue(0, 6, 3, 2023, api), 0.001);
    assertEquals(2582.25,
            portfolioDir.portfolioValue(0, 7, 2, 2022, api), 0.001);

    LocalDate currentDate = LocalDate.now();
    String path = System.getProperty("user.dir") + "/Data/" + currentDate;

    File appleData = new File(path + "/AAPL.csv");
    File canaanData = new File(path + "/CAN.csv");
    File canbData = new File(path + "/CANB.csv");
    assertTrue(appleData.exists());
    assertTrue(canaanData.exists());
    assertTrue(canbData.exists());

  }

  @Test
  public void testValueOfPortfolioDateFlexibleWhenHoliday() {
    portfolioDir.createFlexiblePortfolio("Test Portfolio1");
    assertEquals(1, portfolioDir.getSize());
    StockData api = new StockData();
    LocalDate date = LocalDate.of(2020, 2, 3);
    portfolioDir.buyStock(0, "aapl", 15, date, api);
    portfolioDir.buyStock(0, "Apple Inc", 15, date, api);
    portfolioDir.buyStock(0, "Canaan Inc", 15, date, api);
    portfolioDir.buyStock(0, "Can B Corp", 15, date, api);

    try{
      portfolioDir.portfolioValue(0, 23, 3, 2024, api);
    } catch (IllegalArgumentException e) {
      assertEquals("CAN", e.getMessage());
    }
  }

  @Test(expected = IllegalArgumentException.class)
  public void testFlexiPortfolioValueForPortfolioThatDoesnotExists() {
    portfolioDir.createFlexiblePortfolio("Test Portfolio1");
    assertEquals(1, portfolioDir.getSize());
    StockData api = new StockData();
    LocalDate date = LocalDate.of(2020, 2, 3);
    portfolioDir.buyStock(0, "aapl", 15, date, api);
    portfolioDir.buyStock(0, "goog", 15, date, api);
    portfolioDir.portfolioValue(1, 1, 1, 2022, api);

  }

  @Test(expected = IllegalArgumentException.class)
  public void testFlexiPortfolioValueForDayWrong() {
    portfolioDir.createFlexiblePortfolio("Test Portfolio1");
    assertEquals(1, portfolioDir.getSize());
    StockData api = new StockData();
    LocalDate date = LocalDate.of(2020, 2, 3);
    portfolioDir.buyStock(0, "aapl", 15, date, api);
    portfolioDir.buyStock(0, "goog", 15, date, api);
    portfolioDir.portfolioValue(0, 32, 1, 2022, api);
  }


  @Test(expected = IllegalArgumentException.class)
  public void testFlexiPortfolioValueForDayNegWrong() {
    portfolioDir.createFlexiblePortfolio("Test Portfolio1");
    assertEquals(1, portfolioDir.getSize());
    StockData api = new StockData();
    LocalDate date = LocalDate.of(2020, 2, 3);
    portfolioDir.buyStock(0, "aapl", 15, date, api);
    portfolioDir.buyStock(0, "goog", 15, date, api);
    portfolioDir.portfolioValue(0, -1, 1, 2022, api);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testFlexiPortfolioValueForDayZero() {
    portfolioDir.createFlexiblePortfolio("Test Portfolio1");
    assertEquals(1, portfolioDir.getSize());
    StockData api = new StockData();
    LocalDate date = LocalDate.of(2020, 2, 3);
    portfolioDir.buyStock(0, "aapl", 15, date, api);
    portfolioDir.buyStock(0, "goog", 15, date, api);
    portfolioDir.portfolioValue(0, 0, 1, 2022, api);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testFlexiPortfolioValueForDayBigNumber() {
    portfolioDir.createFlexiblePortfolio("Test Portfolio1");
    assertEquals(1, portfolioDir.getSize());
    StockData api = new StockData();
    LocalDate date = LocalDate.of(2020, 2, 3);
    portfolioDir.buyStock(0, "aapl", 15, date, api);
    portfolioDir.buyStock(0, "goog", 15, date, api);
    portfolioDir.portfolioValue(0, 1000, 1, 2022, api);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testFlexiPortfolioValueForMonthBigNumber() {
    portfolioDir.createFlexiblePortfolio("Test Portfolio1");
    assertEquals(1, portfolioDir.getSize());
    StockData api = new StockData();
    LocalDate date = LocalDate.of(2020, 2, 3);
    portfolioDir.buyStock(0, "aapl", 15, date, api);
    portfolioDir.buyStock(0, "goog", 15, date, api);
    portfolioDir.portfolioValue(0, 1, 1000, 2022, api);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testFlexiPortfolioValueForMonthZero() {
    portfolioDir.createFlexiblePortfolio("Test Portfolio1");
    assertEquals(1, portfolioDir.getSize());
    StockData api = new StockData();
    LocalDate date = LocalDate.of(2020, 2, 3);
    portfolioDir.buyStock(0, "aapl", 15, date, api);
    portfolioDir.buyStock(0, "goog", 15, date, api);
    portfolioDir.portfolioValue(0, 1, 0, 2022, api);
  }


  @Test(expected = IllegalArgumentException.class)
  public void testFlexiPortfolioValueForMonthNeg() {
    portfolioDir.createFlexiblePortfolio("Test Portfolio1");
    assertEquals(1, portfolioDir.getSize());
    StockData api = new StockData();
    LocalDate date = LocalDate.of(2020, 2, 3);
    portfolioDir.buyStock(0, "aapl", 15, date, api);
    portfolioDir.buyStock(0, "goog", 15, date, api);
    portfolioDir.portfolioValue(0, 1, -1, 2022, api);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testFlexiPortfolioValueForMonthWrong() {
    portfolioDir.createFlexiblePortfolio("Test Portfolio1");
    assertEquals(1, portfolioDir.getSize());
    StockData api = new StockData();
    LocalDate date = LocalDate.of(2020, 2, 3);
    portfolioDir.buyStock(0, "aapl", 15, date, api);
    portfolioDir.buyStock(0, "goog", 15, date, api);
    portfolioDir.portfolioValue(0, 1, 13, 2022, api);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testFlexiPortfolioValueForYearWrong() {
    portfolioDir.createFlexiblePortfolio("Test Portfolio1");
    assertEquals(1, portfolioDir.getSize());
    StockData api = new StockData();
    LocalDate date = LocalDate.of(2020, 2, 3);
    portfolioDir.buyStock(0, "aapl", 15, date, api);
    portfolioDir.buyStock(0, "goog", 15, date, api);
    portfolioDir.portfolioValue(0, 1, 12, 20220, api);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testFlexiPortfolioValueForYearNeg() {
    portfolioDir.createFlexiblePortfolio("Test Portfolio1");
    assertEquals(1, portfolioDir.getSize());
    StockData api = new StockData();
    LocalDate date = LocalDate.of(2020, 2, 3);
    portfolioDir.buyStock(0, "aapl", 15, date, api);
    portfolioDir.buyStock(0, "goog", 15, date, api);
    portfolioDir.portfolioValue(0, 1, 12, -2, api);
  }

  @Test
  public void testFlexiPortfolioValueForYearInSingleDigit() {
    portfolioDir.createFlexiblePortfolio("Test Portfolio1");
    assertEquals(1, portfolioDir.getSize());
    StockData api = new StockData();
    LocalDate date = LocalDate.of(2020, 2, 3);
    portfolioDir.buyStock(0, "aapl", 15, date, api);

    assertEquals(0,
              portfolioDir.portfolioValue(0, 1, 12, 2, api), 0.001);


    LocalDate currentDate = LocalDate.now();
    String path = System.getProperty("user.dir") + "/Data/" + currentDate;
    File appleData = new File(path + "/AAPL.csv");
    assertTrue(appleData.exists());
  }

  @Test
  public void testFlexiPortfolioValueForYearInThreeDigit() {
    portfolioDir.createFlexiblePortfolio("Test Portfolio1");
    assertEquals(1, portfolioDir.getSize());
    StockData api = new StockData();
    LocalDate date = LocalDate.of(2020, 2, 3);
    portfolioDir.buyStock(0, "aapl", 15, date, api);

      assertEquals(0,
              portfolioDir.portfolioValue(0, 1, 12, 200, api), 0.001);


    LocalDate currentDate = LocalDate.now();
    String path = System.getProperty("user.dir") + "/Data/" + currentDate;
    File appleData = new File(path + "/AAPL.csv");
    assertTrue(appleData.exists());

  }


  @Test
  public void testFlexiPortfolioValueForYearInFourDigitAvailableData() {
    portfolioDir.createFlexiblePortfolio("Test Portfolio1");
    assertEquals(1, portfolioDir.getSize());
    StockData api = new StockData();
    LocalDate date = LocalDate.of(2020, 2, 3);
    portfolioDir.buyStock(0, "aapl", 15, date, api);
    portfolioDir.buyStock(0, "goog", 15, date, api);
    assertEquals(4766.1,
            portfolioDir.portfolioValue(0, 1, 3, 2024, api), 0.001);


    LocalDate currentDate = LocalDate.now();
    String path = System.getProperty("user.dir") + "/Data/" + currentDate;

    File appleData = new File(path + "/AAPL.csv");
    File googData = new File(path + "/GOOG.csv");
    assertTrue(appleData.exists());
    assertTrue(googData.exists());

  }

  @Test
  public void testFlexiAvailableDataForDateMonthAsTwoDigit() {
    portfolioDir.createFlexiblePortfolio("Test Portfolio1");
    assertEquals(1, portfolioDir.getSize());
    StockData api = new StockData();
    LocalDate date = LocalDate.of(2020, 2, 3);
    portfolioDir.buyStock(0, "aapl", 15, date, api);
    portfolioDir.buyStock(0, "goog", 15, date, api);
    assertEquals(4766.1,
            portfolioDir.portfolioValue(0, 01, 03, 2024, api), 0.001);

    LocalDate currentDate = LocalDate.now();
    String path = System.getProperty("user.dir") + "/Data/" + currentDate;
    File appleData = new File(path + "/AAPL.csv");
    File googData = new File(path + "/GOOG.csv");
    assertTrue(appleData.exists());
    assertTrue(googData.exists());

  }

  @Test(expected = IllegalArgumentException.class)
  public void testFlexiPortfolioValueForWrongDate() {
    portfolioDir.createFlexiblePortfolio("Test Portfolio1");
    assertEquals(1, portfolioDir.getSize());
    StockData api = new StockData();
    LocalDate date = LocalDate.of(2020, 2, 3);
    portfolioDir.buyStock(0, "aapl", 15, date, api);
    portfolioDir.buyStock(0, "goog", 15, date, api);
    portfolioDir.portfolioValue(0, 30, 2, 2024, api);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testFlexiPortfolioValueForWrongDateDay() {
    portfolioDir.createFlexiblePortfolio("Test Portfolio1");
    assertEquals(1, portfolioDir.getSize());
    StockData api = new StockData();
    LocalDate date = LocalDate.of(2020, 2, 3);
    portfolioDir.buyStock(0, "aapl", 15, date, api);
    portfolioDir.buyStock(0, "goog", 15, date, api);
    portfolioDir.portfolioValue(0, 31, 4, 2023, api);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testFlexiPortfolioValueForWrongDateDayFeb() {
    portfolioDir.createFlexiblePortfolio("Test Portfolio1");
    assertEquals(1, portfolioDir.getSize());
    StockData api = new StockData();
    LocalDate date = LocalDate.of(2020, 2, 3);
    portfolioDir.buyStock(0, "aapl", 15, date, api);
    portfolioDir.buyStock(0, "goog", 15, date, api);
    portfolioDir.portfolioValue(0, 29, 2, 2023, api);
  }

  @Test
  public void testValueOfPortfolioFlexibleBuySell() {
    portfolioDir.createFlexiblePortfolio("Test Portfolio1");
    assertEquals(1, portfolioDir.getSize());
    StockData api = new StockData();
    LocalDate date = LocalDate.of(2020, 2, 3);
    portfolioDir.buyStock(0, "aapl", 15, date, api);
    portfolioDir.buyStock(0, "Apple Inc", 15, date, api);
    portfolioDir.buyStock(0, "Canaan Inc", 15, date, api);
    portfolioDir.buyStock(0, "Can B Corp", 15, date, api);

    assertEquals(7711.650000000001,
            portfolioDir.portfolioValue(0, 30, 3, 2020, api), 0.001);
    assertEquals(4177.650000000001,
            portfolioDir.portfolioValue(0, 1, 3, 2021, api), 0.001);

    LocalDate date1 = LocalDate.of(2022, 3, 3);
    portfolioDir.sellStock(0, "aapl", 5, date1, api);
    portfolioDir.buyStock(0, "Can B Corp", 15, date1, api);
    assertEquals(4517.78,
            portfolioDir.portfolioValue(0, 1, 3, 2024, api), 0.001);


  }

  @Test
  public void testGain() {
    StockData api = new StockData();
    LocalDate date = LocalDate.of(2020, 2, 3);
    assertEquals("AAPL gained on 2020-02-03", portfolioDir.gainOrLose("aapl",date,api) );
  }

  @Test
  public void testLose() {
    StockData api = new StockData();
    LocalDate date = LocalDate.of(2021, 2, 3);
    assertEquals("AAPL lost on 2021-02-03", portfolioDir.gainOrLose("aapl",date,api) );
  }

  @Test
  public void testLoseGainWhenHoliday() {
    StockData api = new StockData();
    LocalDate date = LocalDate.of(2024, 3, 23);
    try {
      portfolioDir.gainOrLose("aapl",date,api);
    }
    catch (IllegalArgumentException e) {
      assertEquals("No price data available for 2024-03-23", e.getMessage());
    }
  }

  @Test
  public void testLoseGainWhenStockWrong() {
    StockData api = new StockData();
    LocalDate date = LocalDate.of(2024, 3, 22);
    try {
      portfolioDir.gainOrLose("hello",date,api);
    }
    catch (IllegalArgumentException e) {
      assertEquals("Invalid ticker symbol", e.getMessage());
    }
  }

  @Test
  public void xDayMovingAverage() {
    StockData api = new StockData();
    LocalDate date = LocalDate.of(2024, 3, 23);
    assertEquals(177.88899999999998, portfolioDir.xDayMovingAvg("aapl",date,30,api),0.0 );
  }

  @Test
  public void testXDayAvgWhenStockWrong() {
    StockData api = new StockData();
    LocalDate date = LocalDate.of(2024, 3, 22);
    try {
      portfolioDir.xDayMovingAvg("hello",date,30,api);
    }
    catch (IllegalArgumentException e) {
      assertEquals("Invalid ticker symbol", e.getMessage());
    }
  }

  @Test
  public void testXDayAvgWhenXNeg() {
    StockData api = new StockData();
    LocalDate date = LocalDate.of(2024, 3, 22);
    try {
      portfolioDir.xDayMovingAvg("aapl",date,-1,api);
    }
    catch (IllegalArgumentException e) {
      assertEquals("Enter positive number of days", e.getMessage());
    }
  }

  @Test
  public void testXDayAvgWhenXZero() {
    StockData api = new StockData();
    LocalDate date = LocalDate.of(2024, 3, 22);
    try {
      portfolioDir.xDayMovingAvg("aapl",date,0,api);
    }
    catch (IllegalArgumentException e) {
      assertEquals("Enter positive number of days", e.getMessage());
    }
  }

  @Test
  public void testXDayAvgWhenDataLess() {
    StockData api = new StockData();
    LocalDate date = LocalDate.of(2014, 4, 4);
    try {
      portfolioDir.xDayMovingAvg("goog",date,10,api);
    }
    catch (IllegalArgumentException e) {
      assertEquals("Insufficient data available for the specified period", e.getMessage());
    }
  }

  @Test
  public void testXDayAvgWhenStockNotListed() {
    StockData api = new StockData();
    LocalDate date = LocalDate.of(2010, 2, 22);
    try {
      portfolioDir.xDayMovingAvg("goog",date,10,api);
    }
    catch (IllegalArgumentException e) {
      assertEquals("Insufficient data available for the specified period", e.getMessage());
    }
  }


  @Test
  public void testCrossoverWrongStock() {
    StockData api = new StockData();
    LocalDate start = LocalDate.of(2023, 2, 22);
    LocalDate end = LocalDate.of(2023, 12, 22);
    try {
      portfolioDir.crossoverOverPeriod("hello",api,start,end);
    }
    catch (IllegalArgumentException e) {
      assertEquals("Invalid ticker symbol", e.getMessage());
    }
  }

  @Test
  public void testCrossoverOverPeriod() {
    StockData api = new StockData();
    LocalDate startDate = LocalDate.of(2023, 1, 1);
    LocalDate endDate = LocalDate.of(2023, 12, 31);

    TreeMap<String, String> actualCrossoverData
            = portfolioDir.crossoverOverPeriod("AAPL", api, startDate, endDate);
    TreeMap<String, String> expectedData = new TreeMap<>();
    expectedData.put("2023-01-17", "buy");
    expectedData.put("2023-02-27", "buy");
    expectedData.put("2023-03-03", "buy");
    expectedData.put("2023-03-10", "sell");
    expectedData.put("2023-03-13", "buy");
    expectedData.put("2023-03-14", "buy");
    expectedData.put("2023-08-03", "sell");
    expectedData.put("2023-08-30", "buy");
    expectedData.put("2023-09-06", "sell");
    expectedData.put("2023-10-09", "buy");
    expectedData.put("2023-10-18", "sell");
    expectedData.put("2023-11-02", "buy");
    expectedData.put("2023-12-29", "sell");
    assertEquals(expectedData, actualCrossoverData);
  }

  @Test
  public void testCrossoverOverPeriodSmall() {
    StockData api = new StockData();
    LocalDate startDate = LocalDate.of(2024, 2, 1);
    LocalDate endDate = LocalDate.of(2024, 3, 16);

    TreeMap<String, String> actualCrossoverData
            = portfolioDir.crossoverOverPeriod("AAPL", api, startDate, endDate);
    TreeMap<String, String> expectedData = new TreeMap<>();
    expectedData.put("2024-02-06", "buy");
    expectedData.put("2024-02-12", "sell");
    assertEquals(expectedData, actualCrossoverData);
  }

  @Test
  public void testCrossoverDateAfterCurrentDate() {
    StockData api = new StockData();
    LocalDate start = LocalDate.of(2023, 2, 22);
    LocalDate end = LocalDate.of(2024, 12, 22);
    try {
      portfolioDir.crossoverOverPeriod("aapl",api,start,end);
    }
    catch (IllegalArgumentException e) {
      assertEquals("End Date should be less than current date", e.getMessage());
    }
  }

  @Test
  public void testCrossoverDateBeforeListingDateBothDate() {
    StockData api = new StockData();
    LocalDate start = LocalDate.of(2012, 2, 22);
    LocalDate end = LocalDate.of(2014, 1, 22);
    try {
      portfolioDir.crossoverOverPeriod("goog",api,start,end);
    }
    catch (IllegalArgumentException e) {
      assertEquals("Insufficient data available for the specified period", e.getMessage());
    }
  }

  @Test
  public void testCrossoverDateBeforeListingDateStartDate() {
    StockData api = new StockData();
    LocalDate start = LocalDate.of(2012, 2, 22);
    LocalDate end = LocalDate.of(2015, 1, 22);
    try {
      portfolioDir.crossoverOverPeriod("goog",api,start,end);
    }
    catch (IllegalArgumentException e) {
      assertEquals("Insufficient data available for the specified period", e.getMessage());
    }
  }

  @Test
  public void testCrossoverOverPeriodDatesReverseOrder() {
    StockData api = new StockData();
    LocalDate startDate = LocalDate.of(2023, 12, 1);
    LocalDate endDate = LocalDate.of(2023, 1, 31);
    try {
      portfolioDir.crossoverOverPeriod("goog",api,startDate,endDate);
    }
    catch (IllegalArgumentException e) {
      assertEquals("Start Date should be less than End date", e.getMessage());
    }

  }

  @Test
  public void testCrossoverWhen30dayXAvgHasLessData() {
    StockData api = new StockData();
    LocalDate startDate = LocalDate.of(2014, 4, 4);
    LocalDate endDate = LocalDate.of(2015, 1, 31);
    try {
      portfolioDir.crossoverOverPeriod("goog",api,startDate,endDate);
    }
    catch (IllegalArgumentException e) {
      assertEquals("Insufficient data available for the specified period", e.getMessage());
    }
  }

  @Test
  public void testGainLoseOverPeriodDateReverse() {
    StockData api = new StockData();
    LocalDate startDate = LocalDate.of(2023, 12, 1);
    LocalDate endDate = LocalDate.of(2023, 1, 31);
    try {
      portfolioDir.gainOrLoseOverAPeriod("goog",startDate,endDate,api);
    }
    catch (IllegalArgumentException e) {
      assertEquals("Start Date should be less than End date", e.getMessage());
    }
  }

  @Test
  public void testGainLoseDateAfterCurrentDate() {
    StockData api = new StockData();
    LocalDate start = LocalDate.of(2023, 2, 22);
    LocalDate end = LocalDate.of(2024, 12, 22);
    try {
      portfolioDir.gainOrLoseOverAPeriod("aapl",start,end,api);
    }
    catch (IllegalArgumentException e) {
      assertEquals("End Date should be less than current date", e.getMessage());
    }
  }

  @Test
  public void testGainLoseOverPeriodStockWrong() {
    StockData api = new StockData();
    LocalDate start = LocalDate.of(2023, 2, 22);
    LocalDate end = LocalDate.of(2023, 12, 22);
    try {
      portfolioDir.gainOrLoseOverAPeriod("Applee",start,end,api);
    }
    catch (IllegalArgumentException e) {
      assertEquals("Invalid ticker symbol", e.getMessage());
    }
  }

  @Test
  public void testGainLoseOverPeriodBeforeListing() {
    StockData api = new StockData();
    LocalDate start = LocalDate.of(2012, 2, 22);
    LocalDate end = LocalDate.of(2013, 2, 22);
    try {
      portfolioDir.gainOrLoseOverAPeriod("goog",start,end,api);
    }
    catch (IllegalArgumentException e) {
      assertEquals("date is before the listing date.", e.getMessage());
    }
  }

  @Test
  public void testGainLoseOverPeriodBeforeListingStartDate() {
    StockData api = new StockData();
    LocalDate start = LocalDate.of(2012, 2, 22);
    LocalDate end = LocalDate.of(2014, 5, 22);
    assertEquals("GOOG lost over the period from 2012-02-22 to 2014-05-22",
            portfolioDir.gainOrLoseOverAPeriod("goog",start,end,api) );

  }

  @Test
  public void testGainLoseOverPeriodWhenHolidayStartDate() {
    StockData api = new StockData();
    LocalDate start = LocalDate.of(2024, 2, 3);
    LocalDate end = LocalDate.of(2024, 3, 15);
    assertEquals("GOOG lost over the period from 2024-02-03 to 2024-03-15",
            portfolioDir.gainOrLoseOverAPeriod("goog",start,end,api) );

  }

  @Test
  public void testGainLoseOverPeriodWhenHolidayEndDate() {
    StockData api = new StockData();
    LocalDate start = LocalDate.of(2024, 2, 2);
    LocalDate end = LocalDate.of(2024, 3, 16);
    assertEquals("GOOG lost over the period from 2024-02-02 to 2024-03-16",
            portfolioDir.gainOrLoseOverAPeriod("goog",start,end,api) );

  }

  @Test
  public void testGainLoseOverPeriodWhenHoliday() {
    StockData api = new StockData();
    LocalDate start = LocalDate.of(2024, 2, 3);
    LocalDate end = LocalDate.of(2024, 3, 16);
    assertEquals("GOOG lost over the period from 2024-02-03 to 2024-03-16",
            portfolioDir.gainOrLoseOverAPeriod("goog",start,end,api) );

  }

  @Test
  public void testCostBasisWhenEmptyPortfolio() {
    portfolioDir.createFlexiblePortfolio("Test Portfolio1");
    StockData api = new StockData();
    LocalDate date = LocalDate.of(2024, 2, 3);
    assertEquals(0, portfolioDir.costBasis(0,date,api),0.0);

  }

  @Test
  public void testCostBasis() {
    portfolioDir.createFlexiblePortfolio("Test Portfolio1");
    StockData api = new StockData();
    LocalDate date = LocalDate.of(2024, 3, 12);
    portfolioDir.buyStock(0, "aapl", 15, date, api);
    portfolioDir.buyStock(0, "Apple Inc", 15, date, api);
    portfolioDir.buyStock(0, "Canaan Inc", 15, date, api);
    LocalDate date1 = LocalDate.of(2024, 3, 22);
    assertEquals(5217.75, portfolioDir.costBasis(0,date1,api),0.0);

  }

  @Test
  public void testCostBasisOnHoliday() {
    portfolioDir.createFlexiblePortfolio("Test Portfolio1");
    StockData api = new StockData();
    LocalDate date = LocalDate.of(2024, 3, 12);
    portfolioDir.buyStock(0, "aapl", 15, date, api);
    portfolioDir.buyStock(0, "Apple Inc", 15, date, api);
    portfolioDir.buyStock(0, "Canaan Inc", 15, date, api);
    LocalDate date1 = LocalDate.of(2024, 3, 16);
    assertEquals(5217.75, portfolioDir.costBasis(0,date1,api),0.0);

  }

  @Test
  public void testCostBasisBuySell() {
    portfolioDir.createFlexiblePortfolio("Test Portfolio1");
    StockData api = new StockData();
    LocalDate date = LocalDate.of(2024, 3, 12);
    portfolioDir.buyStock(0, "aapl", 15, date, api);
    portfolioDir.buyStock(0, "Apple Inc", 15, date, api);
    portfolioDir.buyStock(0, "Canaan Inc", 15, date, api);
    portfolioDir.sellStock(0, "Canaan Inc", 10, date, api);
    portfolioDir.sellStock(0, "Apple Inc", 4, date, api);
    LocalDate date1 = LocalDate.of(2024, 3, 19);
    assertEquals(5217.75, portfolioDir.costBasis(0,date1,api),0.0);

    LocalDate date2 = LocalDate.of(2024, 3, 18);
    portfolioDir.buyStock(0, "Apple Inc", 15, date2, api);
    portfolioDir.buyStock(0, "Canaan Inc", 15, date2, api);
    LocalDate date3 = LocalDate.of(2024, 3, 22);
    assertEquals(7843.05, portfolioDir.costBasis(0,date3,api),0.0);
  }

  @Test
  public void testCostBasisWrongInput() {
    portfolioDir.createFlexiblePortfolio("Test Portfolio1");
    StockData api = new StockData();
    LocalDate date = LocalDate.of(2024, 3, 12);
    portfolioDir.buyStock(0, "aapl", 15, date, api);
    portfolioDir.buyStock(0, "Apple Inc", 15, date, api);
    portfolioDir.buyStock(0, "Canaan Inc", 15, date, api);
    LocalDate date1 = LocalDate.of(2024, 3, 16);
    try {
      portfolioDir.costBasis(1,date1,api);
    } catch (IllegalArgumentException e) {
      assertEquals("The choice of portfolio doesn't exists", e.getMessage());
    }
  }

  @Test
  public void testCostBasisWrongInputNeg() {
    portfolioDir.createFlexiblePortfolio("Test Portfolio1");
    StockData api = new StockData();
    LocalDate date = LocalDate.of(2024, 3, 12);
    portfolioDir.buyStock(0, "aapl", 15, date, api);
    portfolioDir.buyStock(0, "Apple Inc", 15, date, api);
    portfolioDir.buyStock(0, "Canaan Inc", 15, date, api);
    LocalDate date1 = LocalDate.of(2024, 3, 16);
    try {
      portfolioDir.costBasis(-1,date1,api);
    } catch (IllegalArgumentException e) {
      assertEquals("The choice of portfolio doesn't exists", e.getMessage());
    }
  }

  @Test
  public void testMovingCrossoverWrongStock() {
    StockData api = new StockData();
    LocalDate start = LocalDate.of(2023, 2, 22);
    LocalDate end = LocalDate.of(2023, 12, 22);
    try {
      portfolioDir.movingCrossOver("hello",api,start,end,30,100);
    }
    catch (IllegalArgumentException e) {
      assertEquals("Invalid ticker symbol", e.getMessage());
    }
  }

  @Test
  public void testMovingCrossoverWrongDateOrder() {
    StockData api = new StockData();
    LocalDate start = LocalDate.of(2023, 2, 22);
    LocalDate end = LocalDate.of(2023, 12, 22);
    try {
      portfolioDir.movingCrossOver("aapl",api,end,start,30,100);
    }
    catch (IllegalArgumentException e) {
      assertEquals("Start Date should be less than End date", e.getMessage());
    }
  }

  @Test
  public void testMovingCrossoverWrongEndDate() {
    StockData api = new StockData();
    LocalDate start = LocalDate.of(2023, 2, 22);
    LocalDate end = LocalDate.of(2024, 12, 22);
    try {
      portfolioDir.movingCrossOver("aapl",api,start,end,30,100);
    }
    catch (IllegalArgumentException e) {
      assertEquals("End Date should be less than current date", e.getMessage());
    }
  }

  @Test
  public void testMovingCrossoverWrongXvalue() {
    StockData api = new StockData();
    LocalDate start = LocalDate.of(2023, 2, 22);
    LocalDate end = LocalDate.of(2024, 3, 22);
    try {
      portfolioDir.movingCrossOver("aapl",api,start,end,-1,100);
    }
    catch (IllegalArgumentException e) {
      assertEquals("Enter positive number of days, & shorter moving avg days"
              + " should be less than larger moving avg days.", e.getMessage());
    }
  }

  @Test
  public void testMovingCrossoverWrongYvalue() {
    StockData api = new StockData();
    LocalDate start = LocalDate.of(2023, 2, 22);
    LocalDate end = LocalDate.of(2024, 3, 22);
    try {
      portfolioDir.movingCrossOver("aapl",api,start,end,30,-100);
    }
    catch (IllegalArgumentException e) {
      assertEquals("Enter positive number of days, & shorter moving avg days"
              + " should be less than larger moving avg days.", e.getMessage());
    }
  }

  @Test
  public void testMovingCrossoverWrongXMoreThanY() {
    StockData api = new StockData();
    LocalDate start = LocalDate.of(2023, 2, 22);
    LocalDate end = LocalDate.of(2024, 3, 22);
    try {
      portfolioDir.movingCrossOver("aapl",api,start,end,30,10);
    }
    catch (IllegalArgumentException e) {
      assertEquals("Enter positive number of days, & shorter moving avg days"
              + " should be less than larger moving avg days.", e.getMessage());
    }
  }

  @Test
  public void testMovingCrossover() {
    StockData api = new StockData();
    LocalDate start = LocalDate.of(2023, 2, 22);
    LocalDate end = LocalDate.of(2024, 3, 22);

    TreeMap<String, String> actualCrossoverData
            = portfolioDir.movingCrossOver("aapl",api,start,end,30,100);
    TreeMap<String, String> expectedData = new TreeMap<>();
    expectedData.put("2023-09-13", "sell");
    expectedData.put("2023-12-01", "buy");
    expectedData.put("2024-03-05", "sell");
    assertEquals(expectedData, actualCrossoverData);
  }


  @Test
  public void testMovingCrossoverDiff() {
    StockData api = new StockData();
    LocalDate start = LocalDate.of(2020, 2, 22);
    LocalDate end = LocalDate.of(2024, 3, 22);

    TreeMap<String, String> actualCrossoverData
            = portfolioDir.movingCrossOver("goog",api,start,end,30,100);
    TreeMap<String, String> crossoverInfo = new TreeMap<>();
    crossoverInfo.put("2020-03-19", "sell");
    crossoverInfo.put("2020-05-27", "buy");
    crossoverInfo.put("2020-10-16", "sell");
    crossoverInfo.put("2020-10-29", "buy");
    crossoverInfo.put("2022-01-20", "sell");
    crossoverInfo.put("2023-02-13", "buy");
    crossoverInfo.put("2023-12-06", "sell");
    crossoverInfo.put("2023-12-08", "buy");
    assertEquals(crossoverInfo, actualCrossoverData);
  }

  @Test
  public void testMovingCrossoverWhenDataLessForMovingAvg() {
    StockData api = new StockData();
    LocalDate start = LocalDate.of(2014, 3, 31);
    LocalDate end = LocalDate.of(2015, 3, 22);
    try {
      portfolioDir.movingCrossOver("goog", api, start, end, 30, 100);
    } catch (IllegalArgumentException e) {
      assertEquals("Insufficient data available for the specified period", e.getMessage());
    }
  }

    @Test
    public void testMovingCrossoverWhenDataLessForMovingAvgForLarger() {
      StockData api = new StockData();
      LocalDate start = LocalDate.of(2014, 5, 15);
      LocalDate end = LocalDate.of(2015, 3, 22);
      try {
        portfolioDir.movingCrossOver("goog",api,start,end,30,100);
      } catch (IllegalArgumentException e) {
        assertEquals("Insufficient data available for the specified period", e.getMessage());
      }
  }

  @Test
  public void testStockPerformanceInFuture() {
    StockData api = new StockData();
    LocalDate start = LocalDate.of(2014, 5, 15);
    LocalDate end = LocalDate.of(2025, 3, 22);
    try {
      portfolioDir.stockPerformance("aapl",api,start,end);
    } catch (IllegalArgumentException e) {
      assertEquals("End Date should be less than current date", e.getMessage());
    }
  }

  @Test
  public void testStockPerformanceStartDateLessThanEndDate() {
    StockData api = new StockData();
    LocalDate start = LocalDate.of(2015, 5, 15);
    LocalDate end = LocalDate.of(2014, 3, 22);
    try {
      portfolioDir.stockPerformance("aapl",api,start,end);
    } catch (IllegalArgumentException e) {
      assertEquals("Start Date should be less than End date", e.getMessage());
    }
  }

  @Test
  public void testStockPerformanceWrongStock() {
    StockData api = new StockData();
    LocalDate start = LocalDate.of(2015, 5, 15);
    LocalDate end = LocalDate.of(2016, 3, 22);
    try {
      portfolioDir.stockPerformance("hello",api,start,end);
    } catch (IllegalArgumentException e) {
      assertEquals("Invalid ticker symbol", e.getMessage());
    }
  }

  @Test
  public void testStockPerformanceDurationYears() {
    StockData api = new StockData();
    LocalDate start = LocalDate.of(2010, 5, 15);
    LocalDate end = LocalDate.of(2024, 3, 22);

    TreeMap<String,Integer > actualData
            = portfolioDir.stockPerformance("aapl",api,start,end);
    TreeMap<String, Integer> data = new TreeMap<>();
    data.put("Dec 2010", 27);
    data.put("Dec 2011", 34);
    data.put("Dec 2012", 45);
    data.put("Dec 2013", 47);
    data.put("Dec 2014", 9);
    data.put("Dec 2015", 9);
    data.put("Dec 2016", 10);
    data.put("Dec 2017", 14);
    data.put("Dec 2018", 13);
    data.put("Dec 2019", 24);
    data.put("Dec 2020", 11);
    data.put("Dec 2021", 15);
    data.put("Dec 2022", 11);
    data.put("Dec 2023", 16);
    assertEquals(data,actualData);
  }

  @Test
  public void testStockPerformanceDurationMonths() {
    StockData api = new StockData();
    LocalDate start = LocalDate.of(2022, 5, 15);
    LocalDate end = LocalDate.of(2024, 3, 22);

    TreeMap<String,Integer > actualData
            = portfolioDir.stockPerformance("aapl",api,start,end);
    TreeMap<String, Integer> data = new TreeMap<>();
    data.put("Apr 2023", 34);
    data.put("Aug 2022", 32);
    data.put("Aug 2023", 38);
    data.put("Dec 2022", 26);
    data.put("Dec 2023", 39);
    data.put("Feb 2023", 30);
    data.put("Feb 2024", 37);
    data.put("Jan 2023", 29);
    data.put("Jan 2024", 37);
    data.put("Jul 2022", 33);
    data.put("Jul 2023", 39);
    data.put("Jun 2022", 28);
    data.put("Jun 2023", 39);
    data.put("Mar 2023", 33);
    data.put("May 2022", 30);
    data.put("May 2023", 36);
    data.put("Nov 2022", 30);
    data.put("Nov 2023", 38);
    data.put("Oct 2022", 31);
    data.put("Oct 2023", 34);
    data.put("Sep 2022", 29);
    data.put("Sep 2023", 35);
    assertEquals(data,actualData);
  }

  @Test
  public void testStockPerformanceDurationDays() {
    StockData api = new StockData();
    LocalDate start = LocalDate.of(2024, 1, 15);
    LocalDate end = LocalDate.of(2024, 3, 22);

    TreeMap<String,Integer > actualData
            = portfolioDir.stockPerformance("aapl",api,start,end);
    TreeMap<String, Integer> data = new TreeMap<>();
    data.put("2024-01-12", 37);
    data.put("2024-01-19", 38);
    data.put("2024-01-25", 39);
    data.put("2024-01-30", 38);
    data.put("2024-02-02", 37);
    data.put("2024-02-09", 38);
    data.put("2024-02-14", 37);
    data.put("2024-02-16", 37);
    data.put("2024-02-23", 37);
    data.put("2024-02-29", 37);
    data.put("2024-03-05", 34);
    data.put("2024-03-08", 35);
    data.put("2024-03-15", 35);
    assertEquals(data,actualData);
  }

  @Test
  public void testStockPerformanceDurationDaysWhenDurationLessThan5Days() {
    StockData api = new StockData();
    LocalDate start = LocalDate.of(2024, 3, 18);
    LocalDate end = LocalDate.of(2024, 3, 22);

    TreeMap<String,Integer > actualData
            = portfolioDir.stockPerformance("aapl",api,start,end);
    TreeMap<String, Integer> data = new TreeMap<>();
    data.put("2024-03-18", 44);
    data.put("2024-03-19", 44);
    data.put("2024-03-20", 45);
    data.put("2024-03-21", 44);
    assertEquals(data,actualData);
  }

  @Test
  public void testStockPerformanceDurationDays31() {
    StockData api = new StockData();
    LocalDate start = LocalDate.of(2024, 2, 1);
    LocalDate end = LocalDate.of(2024, 3, 2);

    TreeMap<String,Integer > actualData
            = portfolioDir.stockPerformance("aapl",api,start,end);
    TreeMap<String, Integer> data = new TreeMap<>();
    data.put("2024-02-01", 47);
    data.put("2024-02-02", 47);
    data.put("2024-02-05", 47);
    data.put("2024-02-06", 47);
    data.put("2024-02-07", 48);
    data.put("2024-02-08", 47);
    data.put("2024-02-09", 47);
    data.put("2024-02-12", 47);
    data.put("2024-02-13", 47);
    data.put("2024-02-14", 46);
    data.put("2024-02-15", 46);
    data.put("2024-02-16", 46);
    data.put("2024-02-20", 46);
    data.put("2024-02-21", 46);
    data.put("2024-02-22", 46);
    data.put("2024-02-23", 46);
    data.put("2024-02-26", 46);
    data.put("2024-02-27", 46);
    data.put("2024-02-28", 46);
    data.put("2024-02-29", 46);
    data.put("2024-03-01", 45);
    assertEquals(data,actualData);
  }

  @Test
  public void testStockPerformanceDurationMonthLessThan30() {
    StockData api = new StockData();
    LocalDate start = LocalDate.of(2023, 10, 1);
    LocalDate end = LocalDate.of(2024, 3, 2);

    TreeMap<String,Integer > actualData
            = portfolioDir.stockPerformance("aapl",api,start,end);
    TreeMap<String, Integer> data = new TreeMap<>();
    data.put("Dec 2023", 49);
    data.put("Feb 2024", 46);
    data.put("Jan 2024", 47);
    data.put("Nov 2023", 48);
    data.put("Oct 2023", 43);
    assertEquals(data,actualData);
  }

  @Test
  public void testStockPerformanceDurationYearDiff4Years() {
    StockData api = new StockData();
    LocalDate start = LocalDate.of(2020, 10, 1);
    LocalDate end = LocalDate.of(2024, 3, 2);

    TreeMap<String,Integer > actualData
            = portfolioDir.stockPerformance("aapl",api,start,end);
    TreeMap<String, Integer> data = new TreeMap<>();
    data.put("Apr 2021", 33);
    data.put("Apr 2022", 42);
    data.put("Apr 2023", 42);
    data.put("Aug 2021", 38);
    data.put("Aug 2022", 40);
    data.put("Aug 2023", 47);
    data.put("Dec 2020", 34);
    data.put("Dec 2021", 45);
    data.put("Dec 2022", 32);
    data.put("Dec 2023", 49);
    data.put("Feb 2021", 31);
    data.put("Feb 2022", 41);
    data.put("Feb 2023", 37);
    data.put("Feb 2024", 46);
    data.put("Jun 2021", 34);
    data.put("Jun 2022", 35);
    data.put("Jun 2023", 49);
    data.put("Oct 2020", 28);
    data.put("Oct 2021", 37);
    data.put("Oct 2022", 39);
    data.put("Oct 2023", 43);
    assertEquals(data,actualData);
  }

  @Test
  public void testPortfolioPerformanceInFuture() {
    portfolioDir.createFlexiblePortfolio("Test Portfolio1");
    StockData api = new StockData();
    LocalDate date = LocalDate.of(2024, 3, 12);
    portfolioDir.buyStock(0, "aapl", 15, date, api);
    portfolioDir.buyStock(0, "Apple Inc", 15, date, api);
    LocalDate start = LocalDate.of(2014, 5, 15);
    LocalDate end = LocalDate.of(2025, 3, 22);
    try {
      portfolioDir.portfolioPerformance(0,start,end);
    } catch (IllegalArgumentException e) {
      assertEquals("End Date should be less than current date", e.getMessage());
    }
  }

  @Test
  public void testPortfolioPerformanceStartDateLessThanEndDate() {
    portfolioDir.createFlexiblePortfolio("Test Portfolio1");
    StockData api = new StockData();
    LocalDate date = LocalDate.of(2024, 3, 12);
    portfolioDir.buyStock(0, "aapl", 15, date, api);
    portfolioDir.buyStock(0, "Apple Inc", 15, date, api);
    LocalDate start = LocalDate.of(2015, 5, 15);
    LocalDate end = LocalDate.of(2014, 3, 22);
    try {
      portfolioDir.portfolioPerformance(0,start,end);
    } catch (IllegalArgumentException e) {
      assertEquals("Start Date should be less than End date", e.getMessage());
    }
  }

  @Test (expected = IllegalArgumentException.class)
  public void testPortfolioPerformanceByInflexiblePortfolio() {
    PortfolioImpl.PortfolioBuilder firstBuilder
            = new PortfolioImpl.PortfolioBuilder("Test " + "Portfolio1");
    firstBuilder.addShare("Apple Inc", 10);
    firstBuilder.addShare("GOOG", 10);
    firstBuilder.addShare("Avis Budget Group Inc", 10);
    portfolioDir.addPortfolio(firstBuilder);

    LocalDate start = LocalDate.of(2015, 5, 15);
    LocalDate end = LocalDate.of(2014, 3, 22);
    portfolioDir.portfolioPerformance(0,start,end);
  }

  @Test
  public void testPortfolioPerformanceDurationYears() {
    StockData api = new StockData();
    LocalDate start = LocalDate.of(2010, 5, 15);
    LocalDate end = LocalDate.of(2024, 3, 22);
    portfolioDir.createFlexiblePortfolio("Test Portfolio1");
    LocalDate date2 = LocalDate.of(2015, 3, 16);
    portfolioDir.buyStock(0, "goog", 15, date2, api);
    LocalDate date = LocalDate.of(2023, 3, 13);
    portfolioDir.buyStock(0, "aapl", 15, date, api);
    portfolioDir.buyStock(0, "Apple Inc", 15, date, api);
    LocalDate date1 = LocalDate.of(2023, 3, 15);
    portfolioDir.sellStock(0, "aapl", 15, date1, api);
    TreeMap<String,Integer > actualData
            = portfolioDir.portfolioPerformance(0,start,end);
    TreeMap<String, Integer> data = new TreeMap<>();
    data.put("Dec 2010", 0);
    data.put("Dec 2011", 0);
    data.put("Dec 2012", 0);
    data.put("Dec 2013", 0);
    data.put("Dec 2014", 0);
    data.put("Dec 2015", 13);
    data.put("Dec 2016", 13);
    data.put("Dec 2017", 18);
    data.put("Dec 2018", 18);
    data.put("Dec 2019", 22);
    data.put("Dec 2020", 29);
    data.put("Dec 2021", 49);
    data.put("Dec 2022", 1);
    data.put("Dec 2023",6);
    assertEquals(data,actualData);
  }

  @Test
  public void testPortfolioPerformanceDurationMonths() {
    StockData api = new StockData();
    LocalDate start = LocalDate.of(2022, 5, 15);
    LocalDate end = LocalDate.of(2024, 3, 22);

    portfolioDir.createFlexiblePortfolio("Test Portfolio1");
    LocalDate date2 = LocalDate.of(2015, 3, 16);
    portfolioDir.buyStock(0, "goog", 15, date2, api);
    LocalDate date = LocalDate.of(2023, 3, 13);
    portfolioDir.buyStock(0, "aapl", 15, date, api);
    portfolioDir.buyStock(0, "Apple Inc", 15, date, api);
    LocalDate date1 = LocalDate.of(2023, 3, 15);
    portfolioDir.sellStock(0, "aapl", 15, date1, api);
    TreeMap<String,Integer > actualData
            = portfolioDir.portfolioPerformance(0,start,end);
    TreeMap<String, Integer> data = new TreeMap<>();
    data.put("Apr 2023", 6);
    data.put("Aug 2022", 2);
    data.put("Aug 2023", 7);
    data.put("Dec 2022", 2);
    data.put("Dec 2023", 7);
    data.put("Feb 2023", 2);
    data.put("Feb 2024", 7);
    data.put("Jan 2023", 2);
    data.put("Jan 2024", 7);
    data.put("Jul 2022", 2);
    data.put("Jul 2023", 7);
    data.put("Jun 2022", 47);
    data.put("Jun 2023", 7);
    data.put("Mar 2023", 6);
    data.put("May 2022", 49);
    data.put("May 2023", 6);
    data.put("Nov 2022", 2);
    data.put("Nov 2023", 7);
    data.put("Oct 2022", 2);
    data.put("Oct 2023", 6);
    data.put("Sep 2022", 2);
    data.put("Sep 2023", 6);
    assertEquals(data,actualData);
  }


  @Test
  public void testPerformancePerformanceDurationDays() {
    StockData api = new StockData();
    LocalDate start = LocalDate.of(2024, 1, 15);
    LocalDate end = LocalDate.of(2024, 3, 22);

    portfolioDir.createFlexiblePortfolio("Test Portfolio1");
    LocalDate date = LocalDate.of(2023, 3, 13);
    portfolioDir.buyStock(0, "aapl", 15, date, api);
    portfolioDir.buyStock(0, "Apple Inc", 15, date, api);
    LocalDate date1 = LocalDate.of(2023, 3, 15);
    portfolioDir.sellStock(0, "aapl", 15, date1, api);
    LocalDate date2 = LocalDate.of(2024, 2, 1);
    portfolioDir.buyStock(0, "goog", 15, date2, api);
    TreeMap<String,Integer > actualData
            = portfolioDir.portfolioPerformance(0,start,end);
    TreeMap<String, Integer> data = new TreeMap<>();
    data.put("2024-01-12", 27);
    data.put("2024-01-19", 27);
    data.put("2024-01-25", 28);
    data.put("2024-01-30", 27);
    data.put("2024-02-02", 47);
    data.put("2024-02-09", 49);
    data.put("2024-02-14", 48);
    data.put("2024-02-16", 47);
    data.put("2024-02-23", 47);
    data.put("2024-02-29", 46);
    data.put("2024-03-05", 44);
    data.put("2024-03-08", 45);
    data.put("2024-03-15", 45);
    assertEquals(data,actualData);
  }


  @Test
  public void testPortfolioPerformanceDurationDaysWhenDurationLessThan5Days() {
    StockData api = new StockData();
    LocalDate start = LocalDate.of(2024, 3, 18);
    LocalDate end = LocalDate.of(2024, 3, 22);

    portfolioDir.createFlexiblePortfolio("Test Portfolio1");
    LocalDate date = LocalDate.of(2023, 3, 13);
    portfolioDir.buyStock(0, "aapl", 15, date, api);
    portfolioDir.buyStock(0, "Apple Inc", 15, date, api);
    LocalDate date1 = LocalDate.of(2023, 3, 15);
    portfolioDir.sellStock(0, "aapl", 15, date1, api);
    LocalDate date2 = LocalDate.of(2024, 3, 20);
    portfolioDir.buyStock(0, "goog", 15, date2, api);
    TreeMap<String,Integer > actualData
            = portfolioDir.portfolioPerformance(0,start,end);
    TreeMap<String, Integer> data = new TreeMap<>();
    data.put("2024-03-18", 26);
    data.put("2024-03-19", 26);
    data.put("2024-03-20", 49);
    data.put("2024-03-21", 49);
    assertEquals(data,actualData);
  }


  @Test
  public void testPerformancePerformanceDurationDays31() {
    StockData api = new StockData();
    LocalDate start = LocalDate.of(2024, 2, 1);
    LocalDate end = LocalDate.of(2024, 3, 3);

    portfolioDir.createFlexiblePortfolio("Test Portfolio1");
    LocalDate date = LocalDate.of(2023, 3, 13);
    portfolioDir.buyStock(0, "aapl", 15, date, api);
    portfolioDir.buyStock(0, "Apple Inc", 15, date, api);
    LocalDate date1 = LocalDate.of(2024, 2, 15);
    portfolioDir.sellStock(0, "aapl", 25, date1, api);
    LocalDate date2 = LocalDate.of(2024, 2, 23);
    portfolioDir.buyStock(0, "goog", 15, date2, api);
    TreeMap<String,Integer > actualData
            = portfolioDir.portfolioPerformance(0,start,end);
    TreeMap<String, Integer> data = new TreeMap<>();
    data.put("2024-02-01", 48);
    data.put("2024-02-02", 48);
    data.put("2024-02-05", 49);
    data.put("2024-02-07", 49);
    data.put("2024-02-09", 49);
    data.put("2024-02-13", 48);
    data.put("2024-02-15", 8);
    data.put("2024-02-16", 8);
    data.put("2024-02-21", 8);
    data.put("2024-02-23", 27);
    data.put("2024-02-27", 26);
    data.put("2024-02-29", 26);
    data.put("2024-03-01", 26);
    assertEquals(data,actualData);
  }

  @Test
  public void testPortfolioPerformanceDurationMonthLessThan30() {
    StockData api = new StockData();
    LocalDate start = LocalDate.of(2023, 10, 1);
    LocalDate end = LocalDate.of(2024, 3, 2);

    portfolioDir.createFlexiblePortfolio("Test Portfolio1");
    LocalDate date = LocalDate.of(2023, 11, 13);
    portfolioDir.buyStock(0, "aapl", 15, date, api);
    portfolioDir.buyStock(0, "Apple Inc", 15, date, api);
    LocalDate date1 = LocalDate.of(2024, 2, 15);
    portfolioDir.sellStock(0, "aapl", 25, date1, api);
    LocalDate date2 = LocalDate.of(2024, 2, 23);
    portfolioDir.buyStock(0, "goog", 15, date2, api);
    TreeMap<String,Integer > actualData
            = portfolioDir.portfolioPerformance(0,start,end);
    TreeMap<String, Integer> data = new TreeMap<>();
    data.put("Dec 2023", 49);
    data.put("Feb 2024", 25);
    data.put("Jan 2024", 47);
    data.put("Nov 2023", 48);
    data.put("Oct 2023", 0);
    assertEquals(data,actualData);
  }

  @Test
  public void testPortfolioPerformanceDurationYearDiff4Years() {
    StockData api = new StockData();
    LocalDate start = LocalDate.of(2020, 10, 1);
    LocalDate end = LocalDate.of(2024, 3, 2);

    portfolioDir.createFlexiblePortfolio("Test Portfolio1");
    LocalDate date3 = LocalDate.of(2019, 11, 13);
    portfolioDir.buyStock(0, "aapl", 20, date3, api);
    LocalDate date = LocalDate.of(2023, 11, 13);
    portfolioDir.buyStock(0, "aapl", 15, date, api);
    portfolioDir.buyStock(0, "Apple Inc", 15, date, api);
    LocalDate date1 = LocalDate.of(2024, 2, 15);
    portfolioDir.sellStock(0, "aapl", 25, date1, api);
    LocalDate date2 = LocalDate.of(2024, 2, 23);
    portfolioDir.buyStock(0, "goog", 15, date2, api);
    TreeMap<String,Integer > actualData
            = portfolioDir.portfolioPerformance(0,start,end);
    TreeMap<String, Integer> data = new TreeMap<>();
    data.put("Oct 2020", 11);
    data.put("Dec 2020", 14);
    data.put("Feb 2021", 13);
    data.put("Apr 2021", 13);
    data.put("Jun 2021", 14);
    data.put("Aug 2021", 15);
    data.put("Oct 2021", 15);
    data.put("Dec 2021", 18);
    data.put("Feb 2022", 17);
    data.put("Apr 2022", 17);
    data.put("Jun 2022", 14);
    data.put("Aug 2022", 16);
    data.put("Oct 2022", 16);
    data.put("Dec 2022", 13);
    data.put("Feb 2023", 15);
    data.put("Apr 2023", 17);
    data.put("Jun 2023", 20);
    data.put("Aug 2023", 19);
    data.put("Oct 2023", 17);
    data.put("Dec 2023", 49);
    data.put("Feb 2024", 33);
    assertEquals(data,actualData);
  }

}
