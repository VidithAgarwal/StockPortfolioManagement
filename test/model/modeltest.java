package model;

import org.junit.Before;
import org.junit.Test;
import java.util.List;
import java.io.File;
import java.util.Map;



import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * This class represents a test suite for testing the functionality of the model classes.
 */
public class modelTest {

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
    portfolioDir.addPortfolio(newBuilder);
  }

  @Test (expected = IllegalArgumentException.class)
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
    Map<String, Integer> composition = portfolioDir.portfolioComposition(0);

    assertEquals(1, composition.size());
    assertEquals(20,  (int)composition.get("AAPL"));
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

    ArrayList<String> listOfPortfolios = portfolioDir.getListOfPortfoliosName();

    assertEquals("Test Portfolio1", listOfPortfolios.get(0));
    assertEquals("Test Portfolio2", listOfPortfolios.get(1));
  }

  @Test (expected = IllegalArgumentException.class)
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
    ArrayList<String> listOfPortfolios = portfolioDir.getListOfPortfoliosName();
    assertEquals("Test Portfolio1",listOfPortfolios.get(0) );

  }

  @Test(expected = IllegalArgumentException.class)
  public void testPortfolioValueForPortfolioThatDoesnotExists() {
    PortfolioImpl.PortfolioBuilder firstBuilder =
            new PortfolioImpl.PortfolioBuilder("Test " + "Portfolio1");
    firstBuilder.addShare("Apple Inc", 10);
    firstBuilder.addShare("GOOG", 10);
    portfolioDir.addPortfolio(firstBuilder);
    portfolioDir.portfolioValue(1, 1,1,2022);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testPortfolioValueForDayWrong() {
    PortfolioImpl.PortfolioBuilder firstBuilder
            = new PortfolioImpl.PortfolioBuilder("Test " + "Portfolio1");
    firstBuilder.addShare("Apple Inc", 10);
    firstBuilder.addShare("GOOG", 10);
    portfolioDir.addPortfolio(firstBuilder);
    portfolioDir.portfolioValue(0, 32,1,2022);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testPortfolioValueForDayNegWrong() {
    PortfolioImpl.PortfolioBuilder firstBuilder
            = new PortfolioImpl.PortfolioBuilder("Test " + "Portfolio1");
    firstBuilder.addShare("Apple Inc", 10);
    firstBuilder.addShare("GOOG", 10);
    portfolioDir.addPortfolio(firstBuilder);
    portfolioDir.portfolioValue(0, -1,1,2022);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testPortfolioValueForDayZero() {
    PortfolioImpl.PortfolioBuilder firstBuilder
            = new PortfolioImpl.PortfolioBuilder("Test " + "Portfolio1");
    firstBuilder.addShare("Apple Inc", 10);
    firstBuilder.addShare("GOOG", 10);
    portfolioDir.addPortfolio(firstBuilder);
    portfolioDir.portfolioValue(0, 0,1,2022);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testPortfolioValueForDayBigNumber() {
    PortfolioImpl.PortfolioBuilder firstBuilder
            = new PortfolioImpl.PortfolioBuilder("Test " + "Portfolio1");
    firstBuilder.addShare("Apple Inc", 10);
    firstBuilder.addShare("GOOG", 10);
    portfolioDir.addPortfolio(firstBuilder);
    portfolioDir.portfolioValue(0, 1000,1,2022);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testPortfolioValueForMonthBigNumber() {
    PortfolioImpl.PortfolioBuilder firstBuilder
            = new PortfolioImpl.PortfolioBuilder("Test " + "Portfolio1");
    firstBuilder.addShare("Apple Inc", 10);
    firstBuilder.addShare("GOOG", 10);
    portfolioDir.addPortfolio(firstBuilder);
    portfolioDir.portfolioValue(0, 1,1000,2022);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testPortfolioValueForMonthZero() {
    PortfolioImpl.PortfolioBuilder firstBuilder
            = new PortfolioImpl.PortfolioBuilder("Test " + "Portfolio1");
    firstBuilder.addShare("Apple Inc", 10);
    firstBuilder.addShare("GOOG", 10);
    portfolioDir.addPortfolio(firstBuilder);
    portfolioDir.portfolioValue(0, 1,0,2022);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testPortfolioValueForMonthNeg() {
    PortfolioImpl.PortfolioBuilder firstBuilder
            = new PortfolioImpl.PortfolioBuilder("Test " + "Portfolio1");
    firstBuilder.addShare("Apple Inc", 10);
    firstBuilder.addShare("GOOG", 10);
    portfolioDir.addPortfolio(firstBuilder);
    portfolioDir.portfolioValue(0, 1,-1,2022);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testPortfolioValueForMonthWrong() {
    PortfolioImpl.PortfolioBuilder firstBuilder
            = new PortfolioImpl.PortfolioBuilder("Test " + "Portfolio1");
    firstBuilder.addShare("Apple Inc", 10);
    firstBuilder.addShare("GOOG", 10);
    portfolioDir.addPortfolio(firstBuilder);
    portfolioDir.portfolioValue(0, 1,13,2022);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testPortfolioValueForYearWrong() {
    PortfolioImpl.PortfolioBuilder firstBuilder
            = new PortfolioImpl.PortfolioBuilder("Test " + "Portfolio1");
    firstBuilder.addShare("Apple Inc", 10);
    firstBuilder.addShare("GOOG", 10);
    portfolioDir.addPortfolio(firstBuilder);
    portfolioDir.portfolioValue(0, 1,12,20220);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testPortfolioValueForYearNeg() {
    PortfolioImpl.PortfolioBuilder firstBuilder
            = new PortfolioImpl.PortfolioBuilder("Test " + "Portfolio1");
    firstBuilder.addShare("Apple Inc", 10);
    firstBuilder.addShare("GOOG", 10);
    portfolioDir.addPortfolio(firstBuilder);
    portfolioDir.portfolioValue(0, 1,12,-2);
  }

  @Test
  public void testPortfolioValueForYearInSingleDigit() {
    PortfolioImpl.PortfolioBuilder firstBuilder
            = new PortfolioImpl.PortfolioBuilder("Test " + "Portfolio1");
    firstBuilder.addShare("Apple Inc", 10);
    firstBuilder.addShare("GOOG", 10);
    portfolioDir.addPortfolio(firstBuilder);
    try {
      assertEquals(1000.749,
              portfolioDir.portfolioValue(0, 1,12,2), 0.001);
    } catch (IllegalArgumentException e) {
      assertEquals("AAPL", e.getMessage());
    }
  }

  @Test
  public void testPortfolioValueForYearInThreeDigit() {
    PortfolioImpl.PortfolioBuilder firstBuilder
            = new PortfolioImpl.PortfolioBuilder("Test " + "Portfolio1");
    firstBuilder.addShare("Apple Inc", 10);
    firstBuilder.addShare("GOOG", 10);
    portfolioDir.addPortfolio(firstBuilder);
    try {
      assertEquals(1000.749,
              portfolioDir.portfolioValue(0, 1,12,200), 0.001);
    } catch (IllegalArgumentException e) {
      assertEquals("AAPL", e.getMessage());
    }
  }

  @Test
  public void testPortfolioValueForYearInFourDigitFutureDate() {
    PortfolioImpl.PortfolioBuilder firstBuilder
            = new PortfolioImpl.PortfolioBuilder("Test " + "Portfolio1");
    firstBuilder.addShare("Apple Inc", 10);
    firstBuilder.addShare("GOOG", 10);
    portfolioDir.addPortfolio(firstBuilder);
    try {
      assertEquals(1000.749,
              portfolioDir.portfolioValue(0, 1,12,2024), 0.001);
    } catch (IllegalArgumentException e) {
      assertEquals("AAPL", e.getMessage());
    }
  }

  @Test
  public void testPortfolioValueForYearInFourDigitAvailableData() {
    PortfolioImpl.PortfolioBuilder firstBuilder
            = new PortfolioImpl.PortfolioBuilder("Test " + "Portfolio1");
    firstBuilder.addShare("Apple Inc", 10);
    firstBuilder.addShare("GOOG", 10);
    portfolioDir.addPortfolio(firstBuilder);
      assertEquals(3177.4,
              portfolioDir.portfolioValue(0, 1,3,2024), 0.001);
  }

  @Test
  public void testAvailableDataForDateMonthAsTwoDigit() {
    PortfolioImpl.PortfolioBuilder firstBuilder
            = new PortfolioImpl.PortfolioBuilder("Test " + "Portfolio1");
    firstBuilder.addShare("Apple Inc", 10);
    firstBuilder.addShare("GOOG", 10);
    portfolioDir.addPortfolio(firstBuilder);
    assertEquals(3177.4,
            portfolioDir.portfolioValue(0, 01,03,2024), 0.001);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testPortfolioValueForWrongDate() {
    PortfolioImpl.PortfolioBuilder firstBuilder
            = new PortfolioImpl.PortfolioBuilder("Test " + "Portfolio1");
    firstBuilder.addShare("Apple Inc", 10);
    firstBuilder.addShare("GOOG", 10);
    portfolioDir.addPortfolio(firstBuilder);
    portfolioDir.portfolioValue(0, 30,2,2024);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testPortfolioValueForWrongDateDay() {
    PortfolioImpl.PortfolioBuilder firstBuilder
            = new PortfolioImpl.PortfolioBuilder("Test " + "Portfolio1");
    firstBuilder.addShare("Apple Inc", 10);
    firstBuilder.addShare("GOOG", 10);
    portfolioDir.addPortfolio(firstBuilder);
    portfolioDir.portfolioValue(0, 31,4,2023);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testPortfolioValueForWrongDateDayFeb() {
    PortfolioImpl.PortfolioBuilder firstBuilder
            = new PortfolioImpl.PortfolioBuilder("Test " + "Portfolio1");
    firstBuilder.addShare("Apple Inc", 10);
    firstBuilder.addShare("GOOG", 10);
    portfolioDir.addPortfolio(firstBuilder);
    portfolioDir.portfolioValue(0, 29,2,2023);
  }


  @Test(expected = IllegalArgumentException.class)
  public void testPortfolioCompositionInputPortfolioNeg() {
    PortfolioImpl.PortfolioBuilder firstBuilder
            = new PortfolioImpl.PortfolioBuilder("Test " + "Portfolio");
    firstBuilder.addShare("Apple Inc", 10);
    //share name given
    firstBuilder.addShare("Advanced Battery Technologies Inc", 20);
    portfolioDir.addPortfolio(firstBuilder);

    Map<String, Integer> composition = portfolioDir.portfolioComposition(-1);

  }

  @Test (expected = IllegalArgumentException.class)
  public void testPortfolioCompositionInputPortfolioGreaterThanListOfPortfolio() {
    PortfolioImpl.PortfolioBuilder firstBuilder
            = new PortfolioImpl.PortfolioBuilder("Test " + "Portfolio");
    firstBuilder.addShare("Apple Inc", 10);
    //share name given
    firstBuilder.addShare("Advanced Battery Technologies Inc", 20);
    portfolioDir.addPortfolio(firstBuilder);

    Map<String, Integer> composition = portfolioDir.portfolioComposition(2);
  }


  @Test
  public void testPortfolioComposition() {

    PortfolioImpl.PortfolioBuilder firstBuilder
            = new PortfolioImpl.PortfolioBuilder("Test " + "Portfolio");
    firstBuilder.addShare("Apple Inc", 10);
    //share name given
    firstBuilder.addShare("Advanced Battery Technologies Inc", 20);
    portfolioDir.addPortfolio(firstBuilder);

    Map<String, Integer> composition = portfolioDir.portfolioComposition(0);

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

    Map<String, Integer> composition2 = portfolioDir.portfolioComposition(1);

    assertEquals(2, portfolioDir.getSize());

    assertEquals(3, composition2.size());
    assertEquals(10, (int) composition2.get("AAON"));
    assertEquals(20, (int) composition2.get("ABEO"));
    assertEquals(30, (int) composition2.get("CAN"));

    ArrayList<String> listOfPortfolios = portfolioDir.getListOfPortfoliosName();

    assertEquals("Test Portfolio", listOfPortfolios.get(0));
    assertEquals("Test Portfolio2", listOfPortfolios.get(1));
  }

  @Test (expected = IllegalArgumentException.class)
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


  @Test
  public void testValueOfPortfolioSingleDate() {
    PortfolioImpl.PortfolioBuilder firstBuilder
            = new PortfolioImpl.PortfolioBuilder("Test " + "Portfolio1");
    firstBuilder.addShare("Apple Inc", 10);
    firstBuilder.addShare("Canaan Inc", 10);
    firstBuilder.addShare("Can B Corp", 10);
    portfolioDir.addPortfolio(firstBuilder);
    assertEquals(1, portfolioDir.getSize());

    assertEquals(1717.749,
            portfolioDir.portfolioValue(0,5,3,2024), 0.001);

    File appleData = new File("AAPL.csv");
    File CanaanData = new File("CAN.csv");
    File CanbData = new File("CANB.csv");
    assertTrue(appleData.exists());
    assertTrue(CanaanData.exists());
    assertTrue(CanbData.exists());

  }

  @Test
  public void testValueOfPortfolioOnWeekend() {
    PortfolioImpl.PortfolioBuilder firstBuilder
            = new PortfolioImpl.PortfolioBuilder("Test " + "Portfolio");
    firstBuilder.addShare("Can B Corp", 10);
    portfolioDir.addPortfolio(firstBuilder);
    assertEquals(1, portfolioDir.getSize());

    try{
      assertEquals(1717.749,
              portfolioDir.portfolioValue(0,3,3,2024), 0.001);
    } catch (IllegalArgumentException e) {
      assertEquals("CANB", e.getMessage());
    }
  }

  @Test
  public void testValueOfPortfolioBeforeListing() {
    PortfolioImpl.PortfolioBuilder firstBuilder
            = new PortfolioImpl.PortfolioBuilder("Test " + "Portfolio");
    firstBuilder.addShare("Apple Inc", 10);
    portfolioDir.addPortfolio(firstBuilder);
    assertEquals(1, portfolioDir.getSize());


    try {
      assertEquals(1717.749,
              portfolioDir.portfolioValue(0,3,3,1890), 0.001);
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
    portfolioDir.addPortfolio(firstBuilder);
    assertEquals(1, portfolioDir.getSize());

    assertEquals(1717.749,
            portfolioDir.portfolioValue(0,5,3,2024), 0.001);
    assertEquals(1707.92,
            portfolioDir.portfolioValue(0,6,3,2024), 0.001);
    assertEquals(1724.0249999999999,
            portfolioDir.portfolioValue(0,8,3,2024), 0.001);

    File appleData = new File("AAPL.csv");
    File CanaanData = new File("CAN.csv");
    File CanbData = new File("CANB.csv");
    assertTrue(appleData.exists());
    assertTrue(CanaanData.exists());
    assertTrue(CanbData.exists());

  }


  @Test
  public void testValueOfTwoPortfoliosWithSameStocks() {
    PortfolioImpl.PortfolioBuilder firstBuilder
            = new PortfolioImpl.PortfolioBuilder("Test " + "Portfolio");
    firstBuilder.addShare("Apple Inc", 10);
    firstBuilder.addShare("Canaan Inc", 10);
    firstBuilder.addShare("Can B Corp", 10);
    portfolioDir.addPortfolio(firstBuilder);
    assertEquals(1, portfolioDir.getSize());

    assertEquals(1717.749,
            portfolioDir.portfolioValue(0,5,3,2024), 0.001);
    assertEquals(1707.92,
            portfolioDir.portfolioValue(0,6,3,2024), 0.001);
    assertEquals(1724.0249999999999,
            portfolioDir.portfolioValue(0,8,3,2024), 0.001);


    PortfolioImpl.PortfolioBuilder secondBuilder
            = new PortfolioImpl.PortfolioBuilder("Test " + "Portfolio1");
    secondBuilder.addShare("Apple Inc", 20);
    secondBuilder.addShare("Canaan Inc", 20);
    secondBuilder.addShare("Can B Corp", 50);
    portfolioDir.addPortfolio(secondBuilder);
    assertEquals(2, portfolioDir.getSize());

    assertEquals(3438.045,
            portfolioDir.portfolioValue(1,5,3,2024), 0.001);
    assertEquals(3418.6,
            portfolioDir.portfolioValue(1,6,3,2024), 0.001);
    assertEquals(3451.4249999999997,
            portfolioDir.portfolioValue(1,8,3,2024), 0.001);

    ArrayList<String> listOfPortfolios
            = portfolioDir.getListOfPortfoliosName();

    assertEquals("Test Portfolio", listOfPortfolios.get(0));
    assertEquals("Test Portfolio1", listOfPortfolios.get(1));

    File appleData = new File("AAPL.csv");
    File CanaanData = new File("CAN.csv");
    File CanbData = new File("CANB.csv");
    assertTrue(appleData.exists());
    assertTrue(CanaanData.exists());
    assertTrue(CanbData.exists());

  }


  @Test
  public void testValueOfManyPortfolioManyDateHavingSomeSameShare() {
    PortfolioImpl.PortfolioBuilder newBuilder
            = new PortfolioImpl.PortfolioBuilder("college fund");
    newBuilder.addShare("Apple Inc", 10);
    newBuilder.addShare("Canaan Inc", 10);
    newBuilder.addShare("Can B Corp", 10);
    portfolioDir.addPortfolio(newBuilder);
    assertEquals(1, portfolioDir.getSize());

    assertEquals(1717.749,
            portfolioDir.portfolioValue(0, 5, 3, 2024), 0.001);
    assertEquals(1724.0249999999999,
            portfolioDir.portfolioValue(0, 8, 3, 2024),
            0.001);

    PortfolioImpl.PortfolioBuilder second_builder
            = new PortfolioImpl.PortfolioBuilder("college " + "fund2");
    second_builder.addShare("Apple Inc", 10);
    second_builder.addShare("Canaan Inc", 10);
    second_builder.addShare("Capricor Therapeutics Inc", 10);
    portfolioDir.addPortfolio(second_builder);
    assertEquals(2, portfolioDir.getSize());


    assertEquals(1766.45,
            portfolioDir.portfolioValue(1,5,3,2024), 0.001);
    assertEquals(1813.4,
            portfolioDir.portfolioValue(1,4,3,2024), 0.001);

    File appleData = new File("AAPL.csv");
    File calampData = new File("CAMP.csv");
    File CanaanData = new File("CAN.csv");
    File CanbData = new File("CANB.csv");
    File capricorData = new File("CAPR.csv");
    assertTrue(appleData.exists());
    assertTrue(calampData.exists());
    assertTrue(CanaanData.exists());
    assertTrue(CanbData.exists());
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

    ArrayList<String> listOfPortfolios = portfolioDir.getListOfPortfoliosName();

    assertEquals("oldage fund", listOfPortfolios.get(0));
    assertEquals("college fund", listOfPortfolios.get(1));
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
      assertEquals("Share name not found in stocks.csv.", e.getMessage());
    }
  }


  @Test
  public void testPortfolioValueFor26StocksDifferent() {
    PortfolioImpl.PortfolioBuilder firstBuilder
            = new PortfolioImpl.PortfolioBuilder("Test " + "Portfolio1");

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
    firstBuilder.addShare("Groupon Inc", 10);
    firstBuilder.addShare("Hasbro Inc", 10);
    firstBuilder.addShare("Hologic Inc", 10);
    firstBuilder.addShare("casy", 10);
    firstBuilder.addShare("CCB", 10);
    firstBuilder.addShare("bof", 10);


    portfolioDir.addPortfolio(firstBuilder);
    Map<String, Integer> composition = portfolioDir.portfolioComposition(0);

    assertEquals(12091.379,
            portfolioDir.portfolioValue(0, 1,3,2024), 0.001);

    assertEquals(26, composition.size());
    assertEquals(10, (int) composition.get("AAPL"));
    assertEquals(10, (int) composition.get("G00G"));
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
    assertEquals(10, (int) composition.get("GRPN"));
    assertEquals(10, (int) composition.get("HAS"));
    assertEquals(10, (int) composition.get("HOLX"));
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
      assertEquals("Share name not found in stocks.csv.", e.getMessage());
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
  }

  @Test
  public void testBuild() {
    PortfolioImpl.PortfolioBuilder portfolioBuilder
            = new PortfolioImpl.PortfolioBuilder("Test Portfolio");

    portfolioBuilder.addShare("Apple Inc", 10);
    portfolioBuilder.addShare("GOOG", 5);

    Portfolio portfolio = portfolioBuilder.build();

    assertEquals("Test Portfolio", portfolio.getName());

    try{
      Map<String, Integer> composition = portfolioDir.portfolioComposition(0);
    }
    catch (IllegalArgumentException e) {
      assertEquals("The choice of portfolio doesn't exists", e.getMessage());
    }

    PortfolioImpl.PortfolioBuilder emptyPortfolioBuilder
            = new PortfolioImpl.PortfolioBuilder("Empty Portfolio");

    try {
      emptyPortfolioBuilder.build();
    } catch (IllegalArgumentException e) {
    }
  }


  @Test
  public void testLoad() {
    PortfolioImpl.PortfolioBuilder portfolioBuilder
            = new PortfolioImpl.PortfolioBuilder("Test Portfolio");

    List<String[]> validLines = new ArrayList<>();
    validLines.add(new String[]{"AAPL", "10"});
    validLines.add(new String[]{"MSFT", "5"});

    portfolioBuilder.load(validLines);

    List<String[]> invalidLines = new ArrayList<>();
    invalidLines.add(new String[]{"AAPL"});
    invalidLines.add(new String[]{"MSFT", "5", "Extra"});

    try {
      portfolioBuilder.load(invalidLines);
    } catch (IllegalArgumentException e) {
    }

    List<String[]> linesWithNegativeQuantity = new ArrayList<>();
    linesWithNegativeQuantity.add(new String[]{"AAPL", "-10"});

    try {
      portfolioBuilder.load(linesWithNegativeQuantity);
    } catch (IllegalArgumentException e) {
    }

    List<String[]> linesWithNonIntegerQuantity = new ArrayList<>();
    linesWithNonIntegerQuantity.add(new String[]{"AAPL", "invalid"});

    try {
      portfolioBuilder.load(linesWithNonIntegerQuantity);
    } catch (IllegalArgumentException e) {
    }

    List<String[]> linesWithNonExistingStock = new ArrayList<>();
    linesWithNonExistingStock.add(new String[]{"INVALID", "10"});

    try {
      portfolioBuilder.load(linesWithNonExistingStock);
    } catch (IllegalArgumentException e) {
      assertEquals("Share name not found in stocks.csv", e.getMessage());
    }
  }

  //
//  //here , in load we also require portfolio name from user but its taken in controller.
//  @Test
//  public void testLoadPortfolioData() {
//    portfolioDir.createBuilder("Test Portfolio");
//    String testPath = System.getProperty("user.dir") + "/testFiles/loadTest.csv";
//    portfolioDir.loadPortfolioData(testPath);
//    portfolioDir.addPortfolio(newBuilder);
//
//    Map<String, Integer> composition = portfolioDir.portfolioComposition(0);
//
//    assertEquals(2, composition.size());
//    assertEquals(10, (int) composition.get("AAPL"));
//    assertEquals(20, (int) composition.get("CAPT"));
//  }
//
//  @Test(expected = IllegalArgumentException.class)
//  public void testLoadPortfolioForInvalidStockName() {
//    portfolioDir.createBuilder("Test Portfolio");
//    String testPath = System.getProperty("user.dir") + "/testFiles/invalidLoadTest.csv";
//    portfolioDir.loadPortfolioData(testPath);
//    portfolioDir.addPortfolio(newBuilder);
//  }
//
//  @Test(expected = IllegalArgumentException.class)
//  public void testLoadPortfolioForFractionQuantity() {
//    portfolioDir.createBuilder("Test Portfolio");
//    String testPath = System.getProperty("user.dir") + "/testFiles/invalidLoadTest1.csv";
//    portfolioDir.loadPortfolioData(testPath);
//    portfolioDir.addPortfolio(newBuilder);
//  }
//
//  @Test(expected = IllegalArgumentException.class)
//  public void testLoadPortfolioForNegativeQuantity() {
//    portfolioDir.createBuilder("Test Portfolio");
//    String testPath = System.getProperty("user.dir") + "/testFiles/invalidLoadTest2.csv";
//    portfolioDir.loadPortfolioData(testPath);
//    portfolioDir.addPortfolio(newBuilder);
//  }
//
//  @Test(expected = IllegalArgumentException.class)
//  public void testLoadPortfolioForNonIntegerQuantity() {
//    portfolioDir.createBuilder("Test Portfolio");
//    String testPath = System.getProperty("user.dir") + "/testFiles/invalidLoadTest3.csv";
//    portfolioDir.loadPortfolioData(testPath);
//    portfolioDir.addPortfolio(newBuilder);
//  }
//
//  @Test(expected = IllegalArgumentException.class)
//  public void testLoadPortfolioForInvalidFormat() {
//    portfolioDir.createBuilder("Test Portfolio");
//    String testPath = System.getProperty("user.dir") + "/testFiles/invalidLoadTest4.csv";
//    portfolioDir.loadPortfolioData(testPath);
//    portfolioDir.addPortfolio(newBuilder);
//  }
//
//

//
//  @Test
//  public void testSavePortfolio() {
//    portfolioDir.createBuilder("Test Portfolio");
//    portfolioDir.addShare("Apple Inc", 10);
//    portfolioDir.addShare("Captivision Inc", 20);
//    portfolioDir.addPortfolio(newBuilder);
//    //will have to change this path
//    String testPath = System.getProperty("user.dir") + "/testFiles/test.ass4.csv";
//    portfolioDir.savePortfolio(0, testPath);
//    File savedFile = new File(testPath);
//    assertTrue(savedFile.exists());
//
//  }
//
//  @Test(expected = IllegalArgumentException.class)
//  public void testSavePortfolioForPortfolioThatDoesnotExists() {
//    portfolioDir.createBuilder("Test Portfolio");
//    portfolioDir.addShare("Apple Inc", 10);
//    portfolioDir.addShare("Captivision Inc", 20);
//    portfolioDir.addPortfolio(newBuilder);
//    //will have to change this path
//    String testPath = System.getProperty("user.dir") + "/testFiles/test.ass4.csv";
//    portfolioDir.savePortfolio(1, testPath);
//  }
//

}
