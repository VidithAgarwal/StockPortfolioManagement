package controller;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;


import static org.junit.Assert.assertEquals;

import model.InflexiblePortfolioImpl;
import view.GUIView;
import view.IViewGUI;


/**
 * This class represents a test suite for testing the functionality of StockControllerImplGUI class.
 * It contains test methods to verify the behavior of the GUI controller in different scenarios.
 */
public class NewControllerTest {

  IViewGUI view = new GUIView();
  /**
   * Represents an instance of StockControllerImplGUI used for testing purposes.
   */
  private StockControllerImplGUI controller;
  /**
   * Represents a mock model used for testing purposes.
   */
  private MockModel mockModel;

  /**
   * Sets up the test environment before each test method execution.
   * It initializes mock objects and sets up initial data for testing.
   */
  @Before
  public void setUp() {
    Map<String, Double> mockComposition = new HashMap<>();
    TreeMap<String, String> mockTreeMap = new TreeMap<>();
    mockTreeMap.put("date", "buy/sell");
    TreeMap<String, Integer> mockStringInt = new TreeMap<>();
    mockStringInt.put("date", 10);
    mockComposition.put("AAPL", 20d);
    mockComposition.put("Goog", 10d);
    String mockName = "Test Portfolio";
    double mockValue = 17.5;
    StringBuilder mockSaveInflexible = new StringBuilder();
    mockSaveInflexible.append("Stock, Quantity").append(System.lineSeparator()).append("AAPL,")
            .append("20");
    int intMockValue = 10;

    this.mockModel = new MockModel(mockComposition, mockName, mockValue, intMockValue,
            mockTreeMap, mockStringInt, mockSaveInflexible);

    InflexiblePortfolioImpl.PortfolioBuilder newBuilder = new InflexiblePortfolioImpl
            .PortfolioBuilder(mockName);
    newBuilder.addShare("AAPL", 20);
    mockModel.addPortfolio(newBuilder);
  }


  @Test
  public void testCreatePortfolio() {
    IViewGUI view = new GUIView();
    StockControllerImplGUI controller = new StockControllerImplGUI(view, mockModel);
    controller.createFlexiblePortfolio("Portfolio 1");
    StringBuilder inputLog = mockModel.getLogger();
    String[] logChecker = inputLog.toString().split("\n");
    assertEquals("Creating flexible portfolio with name: Portfolio 1",
            logChecker[logChecker.length - 1]);
  }

  @Test
  public void testCreatePortfolioAlreadyExists() {
    IViewGUI view = new GUIView();
    StockControllerImplGUI controller = new StockControllerImplGUI(view, mockModel);
    controller.createFlexiblePortfolio("Portfolio 1");
    controller.createFlexiblePortfolio("Portfolio 1");
    StringBuilder inputLog = mockModel.getLogger();
    String[] logChecker = inputLog.toString().split("\n");
    assertEquals("Creating flexible portfolio with name: Portfolio 1",
            logChecker[logChecker.length - 1]);
  }

  @Test
  public void testExportPortfolio() {
    IViewGUI view = new GUIView();
    StockControllerImplGUI controller = new StockControllerImplGUI(view, mockModel);
    controller.export(0, "/s.csv");
    StringBuilder inputLog = mockModel.getLogger();
    String[] logChecker = inputLog.toString().split("\n");
    assertEquals("Portfolio to be saved is at index: 0",
            logChecker[logChecker.length - 1]);
    //String error = controller.getErrorMessage();
    //assertEquals("Portfolio with this name already exists!",error);

  }

  @Test
  public void testExportPortfolioInputInvalid() {
    IViewGUI view = new GUIView();
    StockControllerImplGUI controller = new StockControllerImplGUI(view, mockModel);
    controller.export(2, "/s.csv");
    String error = controller.getErrorMessage();
    assertEquals("Invalid portfolio choice", error);

  }

  @Test
  public void testBuyStockInvalidDateFormat() {
    IViewGUI view = new GUIView();
    StockControllerImplGUI controller = new StockControllerImplGUI(view, mockModel);
    controller.buyStock("1", "1", "aapl", 0);
    //StringBuilder inputLog = mockModel.getLogger();
    //String[] logChecker = inputLog.toString().split("\n");
    String message = controller.getErrorMessage();
    assertEquals("Invalid date!", message);
    //assertEquals("Portfolio to be saved is at index: 0",logChecker[logChecker.length -1]);
  }

  @Test
  public void testBuyStockInvalidQuantity() {
    IViewGUI view = new GUIView();
    StockControllerImplGUI controller = new StockControllerImplGUI(view, mockModel);
    controller.buyStock("2024-03-04", "-1", "aapl", 0);
    //StringBuilder inputLog = mockModel.getLogger();
    //String[] logChecker = inputLog.toString().split("\n");
    String message = controller.getErrorMessage();
    assertEquals("Quantity must be positive integer.", message);
    //assertEquals("Portfolio to be saved is at index: 0",logChecker[logChecker.length -1]);
  }

  @Test
  public void testBuyStockInvalidDate() {
    IViewGUI view = new GUIView();
    StockControllerImplGUI controller = new StockControllerImplGUI(view, mockModel);
    controller.buyStock("2024-13-20", "1", "aapl", 0);
    String message = controller.getErrorMessage();
    assertEquals("Invalid date!", message);
  }

  @Test
  public void testBuyStockInvalidInput() {
    IViewGUI view = new GUIView();
    StockControllerImplGUI controller = new StockControllerImplGUI(view, mockModel);
    controller.buyStock("2024-12-20", "1", "aapl", 2);
    String message = controller.getErrorMessage();
    assertEquals("Invalid portfolio choice", message);
  }

  @Test
  public void testBuyStock() {
    IViewGUI view = new GUIView();
    StockControllerImplGUI controller = new StockControllerImplGUI(view, mockModel);
    controller.buyStock("2024-03-04", "10", "aapl", 0);
    StringBuilder inputLog = mockModel.getLogger();
    String[] logChecker = inputLog.toString().split("\n");
    String message = controller.getSuccessMessage();
    assertEquals("10 aapl bought successfully", message);
    assertEquals("Buying 10 shares of aapl for portfolio at index: 0 on 2024-03-04"
            , logChecker[logChecker.length - 1]);
  }

  @Test
  public void testSellStock() {
    IViewGUI view = new GUIView();
    StockControllerImplGUI controller = new StockControllerImplGUI(view, mockModel);
    controller.sellStock("2024-03-04", "10", "aapl", 0);
    StringBuilder inputLog = mockModel.getLogger();
    String[] logChecker = inputLog.toString().split("\n");
    String message = controller.getSuccessMessage();
    assertEquals("10 aapl sold successfully", message);
    assertEquals("Selling 10 shares of aapl for portfolio at index: 0 on 2024-03-04"
            , logChecker[logChecker.length - 1]);
  }

  @Test
  public void testSellStockInvalidDateFormat() {
    IViewGUI view = new GUIView();
    StockControllerImplGUI controller = new StockControllerImplGUI(view, mockModel);
    controller.sellStock("1", "1", "aapl", 0);
    String message = controller.getErrorMessage();
    assertEquals("Invalid date!", message);
  }

  @Test
  public void testSellStockInvalidDate() {
    IViewGUI view = new GUIView();
    StockControllerImplGUI controller = new StockControllerImplGUI(view, mockModel);
    controller.sellStock("2024-13-20", "1", "aapl", 0);
    String message = controller.getErrorMessage();
    assertEquals("Invalid date!", message);
  }

  @Test
  public void testSellStockInvalidQuantity() {
    IViewGUI view = new GUIView();
    StockControllerImplGUI controller = new StockControllerImplGUI(view, mockModel);
    controller.sellStock("2024-03-04", "-1", "aapl", 0);
    String message = controller.getErrorMessage();
    assertEquals("Quantity must be positive integer.", message);
  }

  @Test
  public void testSellStockInvalidInput() {
    IViewGUI view = new GUIView();
    StockControllerImplGUI controller = new StockControllerImplGUI(view, mockModel);
    controller.sellStock("2024-03-04", "1", "aapl", 2);
    String message = controller.getErrorMessage();
    assertEquals("Invalid portfolio choice", message);
  }

  @Test
  public void testTotalValueInvalidDateFormat() {
    IViewGUI view = new GUIView();
    StockControllerImplGUI controller = new StockControllerImplGUI(view, mockModel);
    controller.getTotalValue(0, "1");
    String message = controller.getErrorMessage();
    assertEquals("Invalid date format.", message);
  }

  @Test
  public void testTotalValueInvalidInput() {
    IViewGUI view = new GUIView();
    StockControllerImplGUI controller = new StockControllerImplGUI(view, mockModel);
    controller.getTotalValue(2, "2024-03-03");
    String message = controller.getErrorMessage();
    assertEquals("Invalid portfolio choice", message);
  }

  @Test
  public void testTotalValueInvalidDate() {
    IViewGUI view = new GUIView();
    StockControllerImplGUI controller = new StockControllerImplGUI(view, mockModel);
    controller.getTotalValue(0, "2023-13-32");
    String message = controller.getErrorMessage();
    assertEquals("Invalid date!", message);
  }

  @Test
  public void testTotalValue() {
    IViewGUI view = new GUIView();
    StockControllerImplGUI controller = new StockControllerImplGUI(view, mockModel);
    controller.getTotalValue(0, "2024-03-04");
    StringBuilder inputLog = mockModel.getLogger();
    String[] logChecker = inputLog.toString().split("\n");
    String message = controller.getSuccessMessage();
    assertEquals("The total value of the portfolio on 2024-03-04 is 17.5", message);
    assertEquals("Retrieving composition for portfolio at index: 0 For the day: 4 "
                    + "month: 3 year: 2024"
            , logChecker[logChecker.length - 1]);
  }

  @Test
  public void testCostBasis() {
    IViewGUI view = new GUIView();
    StockControllerImplGUI controller = new StockControllerImplGUI(view, mockModel);
    controller.getCostBasis(0, "2024-03-04");
    StringBuilder inputLog = mockModel.getLogger();
    String[] logChecker = inputLog.toString().split("\n");
    String message = controller.getSuccessMessage();
    assertEquals("The cost basis is: $17.5", message);
    assertEquals("Calculating cost basis for portfolio at index: 0on date: 2024-03-04"
            , logChecker[logChecker.length - 1]);
  }

  @Test
  public void testCostBasisInvalidDateFormat() {
    IViewGUI view = new GUIView();
    StockControllerImplGUI controller = new StockControllerImplGUI(view, mockModel);
    controller.getCostBasis(0, "1");
    String message = controller.getErrorMessage();
    assertEquals("Invalid date!", message);
  }

  @Test
  public void testCostBasisInvalidDate() {
    IViewGUI view = new GUIView();
    StockControllerImplGUI controller = new StockControllerImplGUI(view, mockModel);
    controller.getCostBasis(0, "2024-03-34");
    String message = controller.getErrorMessage();
    assertEquals("Invalid date!", message);
  }

  @Test
  public void testCostBasisInvalidInput() {
    IViewGUI view = new GUIView();
    StockControllerImplGUI controller = new StockControllerImplGUI(view, mockModel);
    controller.getCostBasis(2, "2024-03-28");
    String message = controller.getErrorMessage();
    assertEquals("Invalid portfolio choice", message);
  }

  @Test
  public void testGainOrLose() {
    IViewGUI view = new GUIView();
    StockControllerImplGUI controller = new StockControllerImplGUI(view, mockModel);
    controller.gainOrLose("2024-03-04", "aapl");
    StringBuilder inputLog = mockModel.getLogger();
    String[] logChecker = inputLog.toString().split("\n");
    String message = controller.getSuccessMessage();
    assertEquals("Test Portfolio", message);
    assertEquals("Calculating gain or loss for aapl on 2024-03-04"
            , logChecker[logChecker.length - 1]);
  }

  @Test
  public void testGainOrLoseInvalidDate() {
    IViewGUI view = new GUIView();
    StockControllerImplGUI controller = new StockControllerImplGUI(view, mockModel);
    controller.gainOrLose("2024-13-20", "aapl");
    String message = controller.getErrorMessage();
    assertEquals("Invalid date!", message);
  }

  @Test
  public void testGainOrLoseInvalidDateFormat() {
    IViewGUI view = new GUIView();
    StockControllerImplGUI controller = new StockControllerImplGUI(view, mockModel);
    controller.gainOrLose("2024", "aapl");
    String message = controller.getErrorMessage();
    assertEquals("Invalid date!", message);
  }

  @Test
  public void testGainOrLoseInvalidDateStartFormat() {
    IViewGUI view = new GUIView();
    StockControllerImplGUI controller = new StockControllerImplGUI(view, mockModel);
    controller.gainOrLoseOverPeriod("2024", "2024-03-03", "aapl");
    String message = controller.getErrorMessage();
    assertEquals("Invalid start date!", message);
  }

  @Test
  public void testGainOrLoseInvalidDateStart() {
    IViewGUI view = new GUIView();
    StockControllerImplGUI controller = new StockControllerImplGUI(view, mockModel);
    controller.gainOrLoseOverPeriod("2023-13-03", "2024-03-03",
            "aapl");
    String message = controller.getErrorMessage();
    assertEquals("Invalid start date!", message);
  }

  @Test
  public void testGainOrLoseInvalidDateEndFormat() {
    IViewGUI view = new GUIView();
    StockControllerImplGUI controller = new StockControllerImplGUI(view, mockModel);
    controller.gainOrLoseOverPeriod("2024-03-03", "2024-30",
            "aapl");
    String message = controller.getErrorMessage();
    assertEquals("Invalid end date!", message);
  }

  @Test
  public void testGainOrLoseOverPeriod() {
    IViewGUI view = new GUIView();
    StockControllerImplGUI controller = new StockControllerImplGUI(view, mockModel);
    controller.gainOrLoseOverPeriod("2024-03-03", "2024-03-13",
            "aapl");
    StringBuilder inputLog = mockModel.getLogger();
    String[] logChecker = inputLog.toString().split("\n");
    assertEquals("Calculating gain or loss for aapl from 2024-03-03 to 2024-03-13"
            , logChecker[logChecker.length - 1]);
  }

  @Test
  public void testGainOrLoseInvalidDateEnd() {
    IViewGUI view = new GUIView();
    StockControllerImplGUI controller = new StockControllerImplGUI(view, mockModel);
    controller.gainOrLoseOverPeriod("2024-03-03", "2024-30-03",
            "aapl");
    String message = controller.getErrorMessage();
    assertEquals("Invalid end date!", message);
  }

  @Test
  public void testXMovingAvgInvalidDateEnd() {
    IViewGUI view = new GUIView();
    StockControllerImplGUI controller = new StockControllerImplGUI(view, mockModel);
    controller.xDayMovingAvg("aapl", "20", "2024-30-03");
    String message = controller.getErrorMessage();
    assertEquals("Invalid date!", message);
  }

  @Test
  public void testXMovingAvgInvalidDateEndFormat() {
    IViewGUI view = new GUIView();
    StockControllerImplGUI controller = new StockControllerImplGUI(view, mockModel);
    controller.xDayMovingAvg("aapl", "20", "2024-30");
    String message = controller.getErrorMessage();
    assertEquals("Invalid date!", message);
  }

  @Test
  public void testXMovingAvgInvalidXValue() {
    IViewGUI view = new GUIView();
    StockControllerImplGUI controller = new StockControllerImplGUI(view, mockModel);
    controller.xDayMovingAvg("aapl", "-1"
            , "2024-03-03");
    String message = controller.getErrorMessage();
    assertEquals("X must be a positive integer.", message);
  }

  @Test
  public void testXMovingAvg() {
    IViewGUI view = new GUIView();
    StockControllerImplGUI controller = new StockControllerImplGUI(view, mockModel);
    controller.xDayMovingAvg("aapl", "20", "2024-03-03");
    StringBuilder inputLog = mockModel.getLogger();
    String[] logChecker = inputLog.toString().split("\n");
    assertEquals("Calculating 20-day moving average for aapl on 2024-03-03"
            , logChecker[logChecker.length - 1]);
  }

  @Test
  public void testCrossoverInvalidDateEnd() {
    IViewGUI view = new GUIView();
    StockControllerImplGUI controller = new StockControllerImplGUI(view, mockModel);
    controller.crossoverOverPeriod("2024-03-03", "2024-30-03",
            "aapl");
    String message = controller.getErrorMessage();
    assertEquals("Invalid end date!", message);
  }

  @Test
  public void testCrossoverInvalidDateEndFormat() {
    IViewGUI view = new GUIView();
    StockControllerImplGUI controller = new StockControllerImplGUI(view, mockModel);
    controller.crossoverOverPeriod("2024-03-03", "2024-30",
            "aapl");
    String message = controller.getErrorMessage();
    assertEquals("Invalid end date!", message);
  }

  @Test
  public void testCrossoverInvalidDateStart() {
    IViewGUI view = new GUIView();
    StockControllerImplGUI controller = new StockControllerImplGUI(view, mockModel);
    controller.crossoverOverPeriod("2023-13-03", "2024-03-03",
            "aapl");
    String message = controller.getErrorMessage();
    assertEquals("Invalid start date!", message);
  }

  @Test
  public void testCrossoverInvalidDateStartFormat() {
    IViewGUI view = new GUIView();
    StockControllerImplGUI controller = new StockControllerImplGUI(view, mockModel);
    controller.crossoverOverPeriod("2023-13", "2024-03-03",
            "aapl");
    String message = controller.getErrorMessage();
    assertEquals("Invalid start date!", message);
  }

  @Test
  public void testCrossOver() {
    IViewGUI view = new GUIView();
    StockControllerImplGUI controller = new StockControllerImplGUI(view, mockModel);
    controller.crossoverOverPeriod("2024-03-03", "2024-03-13",
            "aapl");
    StringBuilder inputLog = mockModel.getLogger();
    String[] logChecker = inputLog.toString().split("\n");
    assertEquals("Calculating crossover over period for aapl from 2024-03-03 to 2024-03-13"
            , logChecker[logChecker.length - 1]);
  }

  @Test
  public void testMovingCrossOver() {
    IViewGUI view = new GUIView();
    StockControllerImplGUI controller = new StockControllerImplGUI(view, mockModel);
    controller.movingCrossoversOverPeriod("2024-03-03", "2024-03-13",
            "10", "100", "aapl");
    StringBuilder inputLog = mockModel.getLogger();
    String[] logChecker = inputLog.toString().split("\n");
    assertEquals("Calculating moving crossover for aapl from 2024-03-03 to "
                    + "2024-03-13 for x = 10 and y = 100"
            , logChecker[logChecker.length - 1]);
  }

  @Test
  public void testMovingCrossoverInvalidDateEnd() {
    IViewGUI view = new GUIView();
    StockControllerImplGUI controller = new StockControllerImplGUI(view, mockModel);
    controller.movingCrossoversOverPeriod("2024-03-03", "2024-30-13",
            "10", "100", "aapl");
    String message = controller.getErrorMessage();
    assertEquals("Invalid end date!", message);
  }

  @Test
  public void testMovingCrossoverInvalidDateEndFormat() {
    IViewGUI view = new GUIView();
    StockControllerImplGUI controller = new StockControllerImplGUI(view, mockModel);
    controller.movingCrossoversOverPeriod("2024-03-03", "2024-11",
            "10", "100", "aapl");
    String message = controller.getErrorMessage();
    assertEquals("Invalid end date!", message);
  }

  @Test
  public void testMovingCrossoverInvalidDateStart() {
    IViewGUI view = new GUIView();
    StockControllerImplGUI controller = new StockControllerImplGUI(view, mockModel);
    controller.movingCrossoversOverPeriod("2023-13-03", "2024-01-11",
            "10", "100", "aapl");
    String message = controller.getErrorMessage();
    assertEquals("Invalid start date!", message);
  }

  @Test
  public void testMovingCrossoverInvalidDateStartFormat() {
    IViewGUI view = new GUIView();
    StockControllerImplGUI controller = new StockControllerImplGUI(view, mockModel);
    controller.movingCrossoversOverPeriod("2024-03", "2024-04-03",
            "10", "100", "aapl");
    String message = controller.getErrorMessage();
    assertEquals("Invalid start date!", message);
  }

  @Test
  public void testMovingCrossoverInvalidXValue() {
    IViewGUI view = new GUIView();
    StockControllerImplGUI controller = new StockControllerImplGUI(view, mockModel);
    controller.movingCrossoversOverPeriod("2024-03-03", "2024-04-03",
            "-10", "100", "aapl");
    String message = controller.getErrorMessage();
    assertEquals("X must be a positive integer.", message);
  }

  @Test
  public void testMovingCrossoverInvalidYValue() {
    IViewGUI view = new GUIView();
    StockControllerImplGUI controller = new StockControllerImplGUI(view, mockModel);
    controller.movingCrossoversOverPeriod("2024-03-03", "2024-04-03",
            "10", "-100", "aapl");
    String message = controller.getErrorMessage();
    assertEquals("Y must be a positive integer.", message);
  }

  @Test
  public void testLoad() {
    IViewGUI view = new GUIView();
    StockControllerImplGUI controller = new StockControllerImplGUI(view, mockModel);
    String path = System.getProperty("user.dir") + "/test/testFiles/loadTest.csv";
    controller.loadPortfolio("sachi", path);
    StringBuilder inputLog = mockModel.getLogger();
    String[] logChecker = inputLog.toString().split("\n");
    String message = controller.getSuccessMessage();
    assertEquals("File loaded successfully", message);
    assertEquals("Name of portfolio to be saved: sachi and lines to be appended are: lines"
            , logChecker[logChecker.length - 1]);

  }

  @Test
  public void testLoadPathEmpty() {
    IViewGUI view = new GUIView();
    StockControllerImplGUI controller = new StockControllerImplGUI(view, mockModel);
    controller.loadPortfolio("sachi", "");
    StringBuilder inputLog = mockModel.getLogger();
    String[] logChecker = inputLog.toString().split("\n");
    String message = controller.getErrorMessage();
    assertEquals("File not found. Please enter a valid file path.", message);

  }

  @Test
  public void testExamineComposition() {
    IViewGUI view = new GUIView();
    StockControllerImplGUI controller = new StockControllerImplGUI(view, mockModel);
    controller.examineComposition(0, "2024-03-03");
    StringBuilder inputLog = mockModel.getLogger();
    String[] logChecker = inputLog.toString().split("\n");
    assertEquals("Retrieving composition for portfolio at index: 0 on date: 2024-03-03"
            , logChecker[logChecker.length - 1]);
  }

  @Test
  public void testExamineCompositionInvalidDate() {
    IViewGUI view = new GUIView();
    StockControllerImplGUI controller = new StockControllerImplGUI(view, mockModel);
    controller.examineComposition(0, "2024-13-03");
    String message = controller.getErrorMessage();
    assertEquals("Invalid date!", message);
  }

  @Test
  public void testExamineCompositionInvalidDateFormat() {
    IViewGUI view = new GUIView();
    StockControllerImplGUI controller = new StockControllerImplGUI(view, mockModel);
    controller.examineComposition(0, "2024-0303");
    String message = controller.getErrorMessage();
    assertEquals("Invalid date!", message);
  }

  @Test
  public void testExamineCompositionInvalidInput() {
    IViewGUI view = new GUIView();
    StockControllerImplGUI controller = new StockControllerImplGUI(view, mockModel);
    controller.examineComposition(2, "2024-0303");
    String message = controller.getErrorMessage();
    assertEquals("Invalid portfolio choice", message);
  }

  @Test
  public void testCreatePortfolioWithStrategy() {
    IViewGUI view = new GUIView();
    StockControllerImplGUI controller = new StockControllerImplGUI(view, mockModel);
    Map<String, Double> shareDetails = new HashMap<>();
    shareDetails.put("aapl", 50.0);
    shareDetails.put("goog", 50.0);
    controller.createPortfolioWithStrategy("0", "2024-03-03",
            "2024-04-03", 10, 100.0, shareDetails);
    StringBuilder inputLog = mockModel.getLogger();
    String[] logChecker = inputLog.toString().split("\n");
    assertEquals("Index of portfolio to be saved: 0 and buyingList to be appended are: "
                    + "[{goog: 50.0}, {aapl: 50.0}, ], startDate: 2024-03-03, endDate: 2024-04-03, "
                    + "frequencyDays: 10, amount: 100.0"
            , logChecker[logChecker.length - 1]);
  }

  @Test
  public void testCreatePortfolioWithStrategyInvalidStartDate() {
    IViewGUI view = new GUIView();
    StockControllerImplGUI controller = new StockControllerImplGUI(view, mockModel);
    Map<String, Double> shareDetails = new HashMap<>();
    shareDetails.put("aapl", 50.0);
    shareDetails.put("goog", 50.0);
    controller.createPortfolioWithStrategy("0", "2024-13-03",
            "2024-04-03", 10, 100.0, shareDetails);
    String message = controller.getErrorMessage();
    assertEquals("Invalid start date!", message);
  }

  @Test
  public void testCreatePortfolioWithStrategyInvalidStartDateFormat() {
    IViewGUI view = new GUIView();
    StockControllerImplGUI controller = new StockControllerImplGUI(view, mockModel);
    Map<String, Double> shareDetails = new HashMap<>();
    shareDetails.put("aapl", 50.0);
    shareDetails.put("goog", 50.0);
    controller.createPortfolioWithStrategy("0", "2024-03",
            "2024-04-03", 10, 100.0, shareDetails);
    String message = controller.getErrorMessage();
    assertEquals("Invalid start date!", message);
  }

  @Test
  public void testCreatePortfolioWithStrategyInvalidEndDate() {
    IViewGUI view = new GUIView();
    StockControllerImplGUI controller = new StockControllerImplGUI(view, mockModel);
    Map<String, Double> shareDetails = new HashMap<>();
    shareDetails.put("aapl", 50.0);
    shareDetails.put("goog", 50.0);
    controller.createPortfolioWithStrategy("0", "2024-12-03",
            "2024-13-03", 10, 100.0, shareDetails);
    String message = controller.getErrorMessage();
    assertEquals("Invalid end date!", message);
  }

  @Test
  public void testCreatePortfolioWithStrategyInvalidEndDateFormat() {
    IViewGUI view = new GUIView();
    StockControllerImplGUI controller = new StockControllerImplGUI(view, mockModel);
    Map<String, Double> shareDetails = new HashMap<>();
    shareDetails.put("aapl", 50.0);
    shareDetails.put("goog", 50.0);
    controller.createPortfolioWithStrategy("0", "2024-03-04",
            "2024-043", 10, 100.0, shareDetails);
    String message = controller.getErrorMessage();
    assertEquals("Invalid end date!", message);
  }

  @Test
  public void testCreatePortfolioWithStrategyInvalidWeightNeg() {
    IViewGUI view = new GUIView();
    StockControllerImplGUI controller = new StockControllerImplGUI(view, mockModel);
    Map<String, Double> shareDetails = new HashMap<>();
    shareDetails.put("aapl", -50.0);
    shareDetails.put("goog", 50.0);
    controller.createPortfolioWithStrategy("0", "2024-03-04",
            "2024-04-03", 10, 100.0, shareDetails);
    String message = controller.getErrorMessage();
    assertEquals("The weight cannot be negative", message);
  }

  @Test
  public void testCreatePortfolioWithStrategyInvalidWeightGreater() {
    IViewGUI view = new GUIView();
    StockControllerImplGUI controller = new StockControllerImplGUI(view, mockModel);
    Map<String, Double> shareDetails = new HashMap<>();
    shareDetails.put("aapl", 101.0);
    shareDetails.put("goog", 50.0);
    controller.createPortfolioWithStrategy("0", "2024-03-04",
            "2024-04-03", 10, 100.0, shareDetails);
    String message = controller.getErrorMessage();
    assertEquals("The weight cannot be greater than 100%", message);
  }

  @Test
  public void testCreatePortfolioWithStrategyInvalidWeightNot100() {
    IViewGUI view = new GUIView();
    StockControllerImplGUI controller = new StockControllerImplGUI(view, mockModel);
    Map<String, Double> shareDetails = new HashMap<>();
    shareDetails.put("aapl", 48.0);
    shareDetails.put("goog", 50.0);
    controller.createPortfolioWithStrategy("0", "2024-03-04",
            "2024-04-03", 10, 100.0, shareDetails);
    String message = controller.getErrorMessage();
    assertEquals("The combined sum of weights must be 100%", message);
  }

  @Test
  public void testCreatePortfolioWithStrategyInvalidFrequency() {
    IViewGUI view = new GUIView();
    StockControllerImplGUI controller = new StockControllerImplGUI(view, mockModel);
    Map<String, Double> shareDetails = new HashMap<>();
    shareDetails.put("aapl", 50.0);
    shareDetails.put("goog", 50.0);
    controller.createPortfolioWithStrategy("0", "2024-03-04",
            "2024-04-03", -10, 100.0, shareDetails);
    String message = controller.getErrorMessage();
    assertEquals("Frequency of investment must be a positive integer.", message);
  }

  @Test
  public void testCreatePortfolioWithStrategyInvalidAmount() {
    IViewGUI view = new GUIView();
    StockControllerImplGUI controller = new StockControllerImplGUI(view, mockModel);
    Map<String, Double> shareDetails = new HashMap<>();
    shareDetails.put("aapl", 50.0);
    shareDetails.put("goog", 50.0);
    controller.createPortfolioWithStrategy("0", "2024-03-04",
            "2024-04-03", 10, -100.0, shareDetails);
    String message = controller.getErrorMessage();
    assertEquals("Amount must be a positive integer.", message);
  }


  @Test
  public void testInvestWithDCAStrategy() {
    IViewGUI view = new GUIView();
    StockControllerImplGUI controller = new StockControllerImplGUI(view, mockModel);
    Map<String, Double> shareDetails = new HashMap<>();
    shareDetails.put("aapl", 50.0);
    shareDetails.put("goog", 50.0);
    controller.investWithDCAStrategy(0, "2024-03-03",
            100.0, shareDetails);
    StringBuilder inputLog = mockModel.getLogger();
    String[] logChecker = inputLog.toString().split("\n");
    assertEquals("Index of portfolio to be invested: 0 and investingList to be appended "
                    + "are: [{goog: 50.0}, {aapl: 50.0}, ], date: 2024-03-03, amount: 100.0"
            , logChecker[logChecker.length - 1]);
  }

  @Test
  public void testInvestWithDCAStrategyInvalidAmount() {
    IViewGUI view = new GUIView();
    StockControllerImplGUI controller = new StockControllerImplGUI(view, mockModel);
    Map<String, Double> shareDetails = new HashMap<>();
    shareDetails.put("aapl", 50.0);
    shareDetails.put("goog", 50.0);
    controller.investWithDCAStrategy(0, "2024-03-03",
            -100.0, shareDetails);
    String message = controller.getErrorMessage();
    assertEquals("Amount must be a positive integer.", message);
  }

  @Test
  public void testInvestWithDCAStrategyInvalidWeightNeg() {
    IViewGUI view = new GUIView();
    StockControllerImplGUI controller = new StockControllerImplGUI(view, mockModel);
    Map<String, Double> shareDetails = new HashMap<>();
    shareDetails.put("aapl", -50.0);
    shareDetails.put("goog", 50.0);
    controller.investWithDCAStrategy(0, "2024-03-03",
            100.0, shareDetails);
    String message = controller.getErrorMessage();
    assertEquals("The weight cannot be negative", message);
  }

  @Test
  public void testInvestWithDCAStrategyInvalidWeightGreater() {
    IViewGUI view = new GUIView();
    StockControllerImplGUI controller = new StockControllerImplGUI(view, mockModel);
    Map<String, Double> shareDetails = new HashMap<>();
    shareDetails.put("aapl", 101.0);
    shareDetails.put("goog", 50.0);
    controller.investWithDCAStrategy(0, "2024-03-03",
            100.0, shareDetails);
    String message = controller.getErrorMessage();
    assertEquals("The weight cannot be greater than 100%", message);
  }

  @Test
  public void testInvestWithDCAStrategyInvalidWeightNot100() {
    IViewGUI view = new GUIView();
    StockControllerImplGUI controller = new StockControllerImplGUI(view, mockModel);
    Map<String, Double> shareDetails = new HashMap<>();
    shareDetails.put("aapl", 48.0);
    shareDetails.put("goog", 50.0);
    controller.investWithDCAStrategy(0, "2024-03-03",
            100.0, shareDetails);
    String message = controller.getErrorMessage();
    assertEquals("The combined sum of weights must be 100%", message);
  }

  @Test
  public void testInvestWithDCAStrategyInvalidInput() {
    IViewGUI view = new GUIView();
    StockControllerImplGUI controller = new StockControllerImplGUI(view, mockModel);
    Map<String, Double> shareDetails = new HashMap<>();
    shareDetails.put("aapl", 48.0);
    shareDetails.put("goog", 50.0);
    controller.investWithDCAStrategy(2, "2024-03-03",
            100.0, shareDetails);
    String message = controller.getErrorMessage();
    assertEquals("Invalid portfolio choice", message);
  }

  @Test
  public void testInvestWithDCAStrategyInvalidDate() {
    IViewGUI view = new GUIView();
    StockControllerImplGUI controller = new StockControllerImplGUI(view, mockModel);
    Map<String, Double> shareDetails = new HashMap<>();
    shareDetails.put("aapl", 50.0);
    shareDetails.put("goog", 50.0);
    controller.investWithDCAStrategy(0, "2024-13-03",
            100.0, shareDetails);
    String message = controller.getErrorMessage();
    assertEquals("Invalid date!", message);
  }

  @Test
  public void testInvestWithDCAStrategyInvalidDateFormat() {
    IViewGUI view = new GUIView();
    StockControllerImplGUI controller = new StockControllerImplGUI(view, mockModel);
    Map<String, Double> shareDetails = new HashMap<>();
    shareDetails.put("aapl", 50.0);
    shareDetails.put("goog", 50.0);
    controller.investWithDCAStrategy(0, "2024-03",
            100.0, shareDetails);
    String message = controller.getErrorMessage();
    assertEquals("Invalid date!", message);
  }


}
