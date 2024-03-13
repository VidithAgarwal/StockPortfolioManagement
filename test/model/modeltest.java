//package model;
//
//import org.junit.Before;
//import org.junit.Test;
//
//import java.io.File;
//import java.io.IOException;
//import java.util.Map;
//
//
//
//import java.util.ArrayList;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertFalse;
//import static org.junit.Assert.assertTrue;
//
//public class modeltest {
//
//  private PortfolioDirImpl portfolioDir;
//
//  @Before
//  public void setUp() {
//    portfolioDir = new PortfolioDirImpl();
//  }



//  @Test
//  public void testAddPortfolio() {
//    portfolioDir.createBuilder("Test Portfolio");
//    portfolioDir.addShare("Apple Inc", 10);
//    portfolioDir.addPortfolio(newBuilder);
//    assertEquals(1, portfolioDir.getSize());
//  }
//
//  @Test(expected = IllegalArgumentException.class)
//  public void testInvalidStockName() {
//    portfolioDir.createBuilder("Test Portfolio1");
//    portfolioDir.addShare("India", 10);
//  }
//
//  @Test
//  public void testAddingStocksOfSameName() {
//    portfolioDir.createBuilder("Test Portfolio1");
//    portfolioDir.addShare("AAPL", 10);
//    portfolioDir.addShare("AAPL", 10);
//    portfolioDir.addPortfolio(newBuilder);
//    assertEquals(1, portfolioDir.getSize());
//    Map<String, Integer> composition = portfolioDir.portfolioComposition(0);
//
//    assertEquals(1, composition.size());
//    assertEquals(20,  (int)composition.get("AAPL"));
//
//    File appleData = new File("AAPL.csv");
//    assertFalse(appleData.exists());
//
//    try {
//      portfolioDir.deleteSessionCSVFilesFromStocklist(System.getProperty("user.dir"));
//      assertFalse(appleData.exists());
//    } catch (IOException ignored) {
//
//    }
//
//  }
//
//  @Test
//  public void testGetListOfPortfoliosName() {
//    portfolioDir.createBuilder("Test Portfolio1");
//    portfolioDir.addPortfolio(newBuilder);
//    portfolioDir.createBuilder("Test Portfolio2");
//    portfolioDir.addPortfolio(newBuilder);
//
//    ArrayList<String> listOfPortfolios = portfolioDir.getListOfPortfoliosName();
//
//    assertEquals("Test Portfolio1", listOfPortfolios.get(0));
//    assertEquals("Test Portfolio2", listOfPortfolios.get(1));
//  }
//
//  @Test (expected = IllegalArgumentException.class)
//  public void testTwoPortfoliosWithSameName() {
//    portfolioDir.createBuilder("Test Portfolio");
//    portfolioDir.addPortfolio(newBuilder);
//    portfolioDir.createBuilder("Test Portfolio");
//  }
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
//  @Test(expected = IllegalArgumentException.class)
//  public void testPortfolioCompositionForPortfolioThatDoesnotExists() {
//    portfolioDir.createBuilder("Test Portfolio");
//    portfolioDir.addShare("Apple Inc", 10);
//    portfolioDir.addShare("Captivision Inc", 20);
//    portfolioDir.addPortfolio(newBuilder);
//    portfolioDir.portfolioComposition(1);
//  }
//
//  @Test(expected = IllegalArgumentException.class)
//  public void testPortfolioValueForPortfolioThatDoesnotExists() {
//    portfolioDir.createBuilder("Test Portfolio");
//    portfolioDir.addShare("Apple Inc", 10);
//    portfolioDir.addShare("Captivision Inc", 20);
//    portfolioDir.addPortfolio(newBuilder);
//    portfolioDir.portfolioValue(1, "2024-03-05");
//  }
//
//
//  @Test
//  public void testPortfolioComposition() {
//    portfolioDir.createBuilder("Test Portfolio");
//    //SHARENAME
//    portfolioDir.addShare("Apple Inc", 10);
//    portfolioDir.addShare("Advanced Battery Technologies Inc", 20);
//    portfolioDir.addPortfolio(newBuilder);
//
//
//    Map<String, Integer> composition = portfolioDir.portfolioComposition(0);
//
//    assertEquals(2, composition.size());
//    assertEquals(10, (int) composition.get("AAPL"));
//    assertEquals(20, (int) composition.get("ABAT"));
//
//
//    portfolioDir.createBuilder("Test Portfolio2");
//    // TICKER SYMBOL
//    portfolioDir.addShare("AAON", 10);
//    portfolioDir.addShare("ABEO", 20);
//    portfolioDir.addShare("CAN", 30);
//    portfolioDir.addPortfolio(newBuilder);
//
//
//    Map<String, Integer> composition2 = portfolioDir.portfolioComposition(1);
//
//    assertEquals(2, portfolioDir.getSize());
//
//    assertEquals(3, composition2.size());
//    assertEquals(10, (int) composition2.get("AAON"));
//    assertEquals(20, (int) composition2.get("ABEO"));
//    assertEquals(30, (int) composition2.get("CAN"));
//
//    ArrayList<String> listOfPortfolios = portfolioDir.getListOfPortfoliosName();
//
//    assertEquals("Test Portfolio", listOfPortfolios.get(0));
//    assertEquals("Test Portfolio2", listOfPortfolios.get(1));
//  }
//
//  @Test
//  public void testValueOfPortfolioSingleDate() {
//    portfolioDir.createBuilder("Test Portfolio");
//    portfolioDir.addShare("Apple Inc", 10);
//    portfolioDir.addShare("Canaan Inc", 10);
//    portfolioDir.addShare("Can B Corp", 10);
//    portfolioDir.addPortfolio(newBuilder);
//    assertEquals(1, portfolioDir.getSize());
//
//    assertEquals(1717.749, portfolioDir.portfolioValue(0,"2024-03-05"), 0.001);
//
//    File appleData = new File("AAPL.csv");
//    File CanaanData = new File("CAN.csv");
//    File CanbData = new File("CANB.csv");
//    assertTrue(appleData.exists());
//    assertTrue(CanaanData.exists());
//    assertTrue(CanbData.exists());
//
//    try {
//      portfolioDir.deleteSessionCSVFilesFromStocklist(System.getProperty("user.dir"));
//      assertFalse(appleData.exists());
//      assertFalse(CanbData.exists());
//      assertFalse(CanaanData.exists());
//    } catch (IOException e) {
//      System.out.println(e.getMessage());
//    }
//  }
//
//  @Test
//  public void testValueOfPortfolioOnWeekend() {
//    portfolioDir.createBuilder("Test Portfolio");
//    portfolioDir.addShare("Can B Corp", 10);
//    portfolioDir.addPortfolio(newBuilder);
//    assertEquals(1, portfolioDir.getSize());
//
//    try{
//      assertEquals(1717.749, portfolioDir.portfolioValue(0,"2024-03-03"), 0.001);
//    } catch (IllegalArgumentException e) {
//      assertEquals("CANB", e.getMessage());
//      try {
//        portfolioDir.deleteSessionCSVFilesFromStocklist(System.getProperty("user.dir"));
//      } catch (IOException ignored) {
//      }
//    }
//  }
//
//  @Test
//  public void testValueOfPortfolioBeforeListing() {
//    portfolioDir.createBuilder("Test Portfolio");
//    portfolioDir.addShare("Apple Inc", 10);
//
//    portfolioDir.addPortfolio(newBuilder);
//    assertEquals(1, portfolioDir.getSize());
//
//    try {
//      assertEquals(1717.749, portfolioDir.portfolioValue(0,"1890-03-03"), 0.001);
//    } catch (IllegalArgumentException e) {
//      assertEquals("AAPL", e.getMessage());
//      try {
//        portfolioDir.deleteSessionCSVFilesFromStocklist(System.getProperty("user.dir"));
//      } catch (IOException ignored) {
//      }
//    }
//
//  }
//
//  @Test
//  public void testValueOfPortfolioManyDate() {
//    portfolioDir.createBuilder("Test Portfolio");
//    portfolioDir.addShare("Apple Inc", 10);
//    portfolioDir.addShare("Canaan Inc", 10);
//    portfolioDir.addShare("Can B Corp", 10);
//    portfolioDir.addPortfolio(newBuilder);
//    assertEquals(1, portfolioDir.getSize());
//
//    assertEquals(1717.749, portfolioDir.portfolioValue(0,"2024-03-05"), 0.001);
//    assertEquals(1707.92, portfolioDir.portfolioValue(0,"2024-03-06"), 0.001);
//    assertEquals(1724.0249999999999, portfolioDir.portfolioValue(0,"2024-03-08"), 0.001);
//
//    File appleData = new File("AAPL.csv");
//    File CanaanData = new File("CAN.csv");
//    File CanbData = new File("CANB.csv");
//    assertTrue(appleData.exists());
//    assertTrue(CanaanData.exists());
//    assertTrue(CanbData.exists());
//
//    try {
//      portfolioDir.deleteSessionCSVFilesFromStocklist(System.getProperty("user.dir"));
//      assertFalse(appleData.exists());
//      assertFalse(CanbData.exists());
//      assertFalse(CanaanData.exists());
//    } catch (IOException e) {
//      System.out.println(e.getMessage());
//    }
//  }
//
//  @Test
//  public void testValueOfTwoPortfoliosWithSameStocks() {
//    portfolioDir.createBuilder("Test Portfolio");
//    portfolioDir.addShare("Apple Inc", 10);
//    portfolioDir.addShare("Canaan Inc", 10);
//    portfolioDir.addShare("Can B Corp", 10);
//    portfolioDir.addPortfolio(newBuilder);
//    assertEquals(1, portfolioDir.getSize());
//
//    assertEquals(1717.749, portfolioDir.portfolioValue(0,"2024-03-05"), 0.001);
//    assertEquals(1707.92, portfolioDir.portfolioValue(0,"2024-03-06"), 0.001);
//    assertEquals(1724.0249999999999, portfolioDir.portfolioValue(0,"2024-03-08"), 0.001);
//
//    portfolioDir.createBuilder("Test Portfolio1");
//    portfolioDir.addShare("Apple Inc", 20);
//    portfolioDir.addShare("Canaan Inc", 20);
//    portfolioDir.addShare("Can B Corp", 50);
//    portfolioDir.addPortfolio(newBuilder);
//    assertEquals(2, portfolioDir.getSize());
//
//    assertEquals(3438.045, portfolioDir.portfolioValue(1,"2024-03-05"), 0.001);
//    assertEquals(3418.6, portfolioDir.portfolioValue(1,"2024-03-06"), 0.001);
//    assertEquals(3451.4249999999997, portfolioDir.portfolioValue(1,"2024-03-08"), 0.001);
//
//    File appleData = new File("AAPL.csv");
//    File CanaanData = new File("CAN.csv");
//    File CanbData = new File("CANB.csv");
//    assertTrue(appleData.exists());
//    assertTrue(CanaanData.exists());
//    assertTrue(CanbData.exists());
//
//    try {
//      portfolioDir.deleteSessionCSVFilesFromStocklist(System.getProperty("user.dir"));
//      assertFalse(appleData.exists());
//      assertFalse(CanbData.exists());
//      assertFalse(CanaanData.exists());
//    } catch (IOException e) {
//      System.out.println(e.getMessage());
//    }
//  }
//
//
//  @Test
//  public void testValueOfManyPortfolioManyDateHavingSomeSameShare() {
//    portfolioDir.createBuilder("college fund");
//    portfolioDir.addShare("Apple Inc", 10);
//    portfolioDir.addShare("Canaan Inc", 10);
//    portfolioDir.addShare("Can B Corp", 10);
//    portfolioDir.addPortfolio(newBuilder);
//    assertEquals(1, portfolioDir.getSize());
//
//    assertEquals(1717.749, portfolioDir.portfolioValue(0,"2024-03-05"), 0.001);
//    assertEquals(1724.0249999999999, portfolioDir.portfolioValue(0,"2024-03-08"), 0.001);
//
//    portfolioDir.createBuilder("oldage fund");
//    portfolioDir.addShare("Apple Inc", 10);
//    portfolioDir.addShare("Calamp Corp", 10);
//    portfolioDir.addShare("Capricor Therapeutics Inc", 10);
//    portfolioDir.addPortfolio(newBuilder);
//    assertEquals(2, portfolioDir.getSize());
//
//    assertEquals(1774.55, portfolioDir.portfolioValue(1,"2024-03-05"), 0.001);
//    assertEquals(1824.8, portfolioDir.portfolioValue(1,"2024-03-04"), 0.001);
//
//    File appleData = new File("AAPL.csv");
//    File calampData = new File("CAMP.csv");
//    File CanaanData = new File("CAN.csv");
//    File CanbData = new File("CANB.csv");
//    File capricorData = new File("CAPR.csv");
//    assertTrue(appleData.exists());
//    assertTrue(calampData.exists());
//    assertTrue(CanaanData.exists());
//    assertTrue(CanbData.exists());
//    assertTrue(capricorData.exists());
//
//    try {
//      portfolioDir.deleteSessionCSVFilesFromStocklist(System.getProperty("user.dir"));
//      assertFalse(appleData.exists());
//      assertFalse(capricorData.exists());
//      assertFalse(CanbData.exists());
//      assertFalse(CanaanData.exists());
//      assertFalse(calampData.exists());
//    } catch (IOException e) {
//      System.out.println(e.getMessage());
//    }
//  }
//
//
//  @Test
//  public void testIsEmptyWhenEmpty() {
//    assertEquals(true,portfolioDir.isEmpty());
//  }
//
//  @Test
//  public void testIsEmptyAfterAddingElements() {
//    portfolioDir.createBuilder("oldage fund");
//    portfolioDir.addPortfolio(newBuilder);
//    portfolioDir.createBuilder("college fund");
//    portfolioDir.addPortfolio(newBuilder);
//
//    assertEquals(false, portfolioDir.isEmpty());
//
//    ArrayList<String> listOfPortfolios = portfolioDir.getListOfPortfoliosName();
//
//    assertEquals("oldage fund", listOfPortfolios.get(0));
//    assertEquals("college fund", listOfPortfolios.get(1));
//  }
//
//  @Test
//  public void testExistsWhenPortfolioExists() {
//    portfolioDir.createBuilder("oldage fund");
//    portfolioDir.addPortfolio(newBuilder);
//
//    // Check if the portfolio exists
//    assertEquals(true,portfolioDir.exists("oldage fund"));
//    assertEquals(false,portfolioDir.exists("college fund"));
//
//  }
//
//  @Test
//  public void testWrongAddShare() {
//    portfolioDir.createBuilder("Test Portfolio");
//
//    try {
//      portfolioDir.addShare("Appple", 10);
//      portfolioDir.addPortfolio(newBuilder);
//    } catch (IllegalArgumentException e) {
//      assertEquals("Share name not found in stocks.csv", e.getMessage());
//    }
//  }
//
//  @Test (expected = IllegalArgumentException.class )
//  public void testSaveToWrongFilePath() {
//    //Portfolio mockPortfolio = Mockito.mock(Portfolio.class);
//    portfolioDir.createBuilder("Test Portfolio");
//    portfolioDir.addShare("Apple Inc", 10);
//    portfolioDir.addPortfolio(newBuilder);
//    String testPath = "";
//    //will have to change this path
//      portfolioDir.savePortfolio(0, testPath);
//
//  }
//
//
//
//
//}
