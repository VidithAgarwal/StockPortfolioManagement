package controller;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.Reader;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import model.PortfolioImpl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * This class represents a test suite for testing the functionality of StockControllerImpl class.
 * It contains test methods to verify the behavior of the controller in different scenarios.
 */
public class ControllerTest {

  /**
   * Represents an instance of StockControllerImpl used for testing purposes.
   */
  private StockControllerImpl controller;

  /**
   * Represents a mock model used for testing purposes.
   */
  private MockModel mockModel;

  /**
   * Represents a mock view used for testing purposes.
   */
  private MockView mockView;

  /**
   * Represents a mock name used for testing purposes.
   */
  private String mockName;

  /**
   * Represents a mock value used for testing purposes.
   */
  private double mockValue;

  /**
   * Sets up the test environment before each test method execution.
   * It initializes mock objects and sets up initial data for testing.
   */
  @Before
  public void setUp() {
    Map<String, Integer> mockComposition = new HashMap<>();
    TreeMap<String,String> mockTreeMap = new TreeMap<>();
    mockTreeMap.put("date", "buy/sell");
    TreeMap<String,Integer> mockStringInt = new TreeMap<>();
    mockComposition.put("AAPL", 20);
    mockComposition.put("Goog", 10);
    mockName = "Test Portfolio";
    mockValue = 17.5;
    int intMockValue = 10;

    this.mockModel = new MockModel(mockComposition, mockName, mockValue, intMockValue,
            mockTreeMap, mockStringInt);
    PortfolioImpl.PortfolioBuilder newBuilder = new PortfolioImpl.PortfolioBuilder(mockName);
    newBuilder.addShare("AAPL", 20);
    mockModel.addPortfolio(newBuilder);
    this.mockView = new MockView();
  }


  @Test
  public void testGetComposition()  {
    String[] expectedOutputLog = {"Enter your choice: ", "Test Portfolio inflexible", "Enter the"
            + " Portfolio "
            + "number you want to select.","Enter the date you want to see the composition "
            + "for: " ,"The date should be in this format yyyy-mm-dd: " ,"Goog 10", "AAPL "
            + "20", "Enter your choice: "};
    Reader in = new StringReader("3\n0\n2024-03-03\n11\n");

    this.controller =  new StockControllerImpl(mockView, in, mockModel);
    controller.execute();
    StringBuilder inputLog = mockModel.getLogger();
    StringBuilder outputLogs = mockView.getPrintedOutput();
    String[] logChecker = inputLog.toString().split("\n");
    String[] outputLogChecker = outputLogs.toString().split("\n");
    assertEquals("Retrieving composition for portfolio at index: 0 on date: 2024-03-03",
            logChecker[logChecker.length - 1]);
    for (int i = 0; i < outputLogChecker.length; i++) {
      assertEquals(expectedOutputLog[i], outputLogChecker[i]);
    }
  }

  @Test
  public void testGetCompositionWithNegativeChoice()  {
    String[] expectedOutputLog = {"Enter your choice: ", "Test Portfolio inflexible", "Enter the "
            + "Portfolio " + "number you want to select.", "Error", "Enter the Portfolio "
            + "number you want to "
            + "select.","Enter the date you want to see the composition"
            + " for: ", "The date should be in this format yyyy-mm-dd: " ,"Goog 10", "AAPL "
            + "20", "Enter your choice: "};
    Reader in = new StringReader("3\n-1\n0\n2024-03-03\n11\n");


    this.controller =  new StockControllerImpl(mockView, in, mockModel);
    controller.execute();
    StringBuilder inputLog = mockModel.getLogger();
    StringBuilder outputLogs = mockView.getPrintedOutput();
    String[] logChecker = inputLog.toString().split("\n");
    String[] outputLogChecker = outputLogs.toString().split("\n");
    assertEquals("Retrieving composition for portfolio at index: 0 on date: 2024-03-03",
            logChecker[logChecker.length - 1]);
    for (int i = 0; i < outputLogChecker.length; i++) {
      assertEquals(expectedOutputLog[i], outputLogChecker[i]);
    }
  }

  @Test
  public void testGetCompositionWithInvalidChoice()  {
    String[] expectedOutputLog = {"Enter your choice: ", "Test Portfolio inflexible", "Enter the "
            + "Portfolio "
            + "number you want to select.", "Error",  "Enter the Portfolio number you want to "
            + "select.", "Enter the date you want to see the composition for: ","The date"
            + " should be in this format yyyy-mm-dd: ","Goog 10", "AAPL "
            + "20", "Enter your choice: "};
    Reader in = new StringReader("3\n3\n0\n2024-03-20\n11\n");

    this.controller =  new StockControllerImpl(mockView, in, mockModel);
    controller.execute();
    StringBuilder inputLog = mockModel.getLogger();
    StringBuilder outputLogs = mockView.getPrintedOutput();
    String[] logChecker = inputLog.toString().split("\n");
    String[] outputLogChecker = outputLogs.toString().split("\n");
    assertEquals("Retrieving composition for portfolio at index: 0 on date: 2024-03-20",
            logChecker[logChecker.length - 1]);
    for (int i = 0; i < outputLogChecker.length; i++) {
      assertEquals(expectedOutputLog[i], outputLogChecker[i]);
    }
  }

  @Test
  public void testGetCompositionWithChoiceAsString()  {
    String[] expectedOutputLog = {"Enter your choice: ", "Test Portfolio inflexible", "Enter the "
            + "Portfolio "
            + "number you want to select.", "Error",  "Enter the Portfolio number you want to "
            + "select.","Enter the date you want to see the composition for: " ,"The date should"
            + " be in this format yyyy-mm-dd: ","Goog 10", "AAPL 20", "Enter your choice: "};
    Reader in = new StringReader("3\nstring\n0\n2024-03-20\n11\n");

    this.controller =  new StockControllerImpl(mockView, in, mockModel);
    controller.execute();
    StringBuilder inputLog = mockModel.getLogger();
    StringBuilder outputLogs = mockView.getPrintedOutput();
    String[] logChecker = inputLog.toString().split("\n");
    String[] outputLogChecker = outputLogs.toString().split("\n");
    assertEquals("Retrieving composition for portfolio at index: 0 on date: 2024-03-20",
            logChecker[logChecker.length - 1]);
    for (int i = 0; i < outputLogChecker.length; i++) {
      assertEquals(expectedOutputLog[i], outputLogChecker[i]);
    }
  }

  @Test
  public void testGetCompositionWithChoiceAsDecimal()  {
    String[] expectedOutputLog = {"Enter your choice: ", "Test Portfolio inflexible", "Enter the "
            + "Portfolio "
            + "number you want to select.", "Error",  "Enter the Portfolio number you want to "
            + "select.","Enter the date you want to see the composition for: ","The date "
            + "should be in this format yyyy-mm-dd: ", "Goog 10", "AAPL 20", "Enter "
            + "your choice: "};
    Reader in = new StringReader("3\n3.5\n0\n2024-03-20\n11\n");

    this.controller =  new StockControllerImpl(mockView, in, mockModel);
    controller.execute();
    StringBuilder inputLog = mockModel.getLogger();
    StringBuilder outputLogs = mockView.getPrintedOutput();
    String[] logChecker = inputLog.toString().split("\n");
    String[] outputLogChecker = outputLogs.toString().split("\n");
    assertEquals("Retrieving composition for portfolio at index: 0 on date: 2024-03-20",
            logChecker[logChecker.length - 1]);
    for (int i = 0; i < outputLogChecker.length; i++) {
      assertEquals(expectedOutputLog[i], outputLogChecker[i]);
    }
  }

  @Test
  public void testCreatePortfolio()  {
    String[] expectedOutputLog = {"Enter your choice: ","Enter your choice: " ,"Enter the name of "
            + "the portfolio: ", "Enter the number of stocks you want to have in this "
            + "portfolio: ", "Enter the name of the share or ticker symbol: ", "Enter the quantity"
            + " of AAPL you "
            + "have: ", "Enter your choice: "};
    Reader in = new StringReader("1\n1\nTest\n1\nAAPL\n10\n11\n");

    MockModel newMockModel = new MockModel(mockName);
    this.controller =  new StockControllerImpl(mockView, in, newMockModel);
    controller.execute();
    StringBuilder inputLog = newMockModel.getLogger();
    StringBuilder outputLogs = mockView.getPrintedOutput();
    String[] logChecker = inputLog.toString().split("\n");
    String[] outputLogChecker = outputLogs.toString().split("\n");
    assertEquals("Test",
            logChecker[logChecker.length - 1]);
    for (int i = 0; i < outputLogChecker.length; i++) {
      assertEquals(expectedOutputLog[i], outputLogChecker[i]);
    }
  }

  @Test
  public void testCreatePortfolioWithInvalidShareName()  {
    String[] expectedOutputLog = {"Enter your choice: ","Enter your choice: " ,"Enter "
            + "the name of the "
            + "portfolio: ", "Enter the number of stocks you want to have in this "
            + "portfolio: ", "Enter the name "
            + "of the share or ticker symbol: ", "Enter the quantity of AAPLL you "
            + "have: ", "Error", "Enter the name of the share or ticker symbol: ", "Enter the "
            + "quantity of AAPL you "
            + "have: ", "Enter your "
            + "choice: "};
    Reader in = new StringReader("1\n1\nTest\n1\nAAPLL\n10\nAAPL\n10\n11\n");

    MockModel newMockModel = new MockModel(mockName);
    this.controller =  new StockControllerImpl(mockView, in, newMockModel);
    controller.execute();
    StringBuilder inputLog = newMockModel.getLogger();
    StringBuilder outputLogs = mockView.getPrintedOutput();
    String[] logChecker = inputLog.toString().split("\n");
    String[] outputLogChecker = outputLogs.toString().split("\n");
    assertEquals("Test",
            logChecker[logChecker.length - 1]);
    for (int i = 0; i < outputLogChecker.length; i++) {
      assertEquals(expectedOutputLog[i], outputLogChecker[i]);
    }
  }

  @Test
  public void testCreatePortfolioWithNegativeTotalNumberOfStocks()  {
    String[] expectedOutputLog = {"Enter your choice: ", "Enter your choice: ","Enter "
            + "the name of the "
            + "portfolio: ", "Enter the number of stocks you want to have in this "
            + "portfolio: ", "Error", "Enter "
            + "the number of stocks you want to have in this portfolio: ", "Enter the name of "
            + "the share or ticker symbol: ", "Enter the quantity of AAPLL you "
            + "have: ", "Error", "Enter the name of the share or ticker symbol: ", "Enter the "
            + "quantity of AAPL you "
            + "have: ", "Enter your "
            + "choice: "};
    Reader in = new StringReader("1\n1\nTest\n-1\n1\nAAPLL\n10\nAAPL\n10\n11\n");

    MockModel newMockModel = new MockModel(mockName);
    this.controller =  new StockControllerImpl(mockView, in, newMockModel);
    controller.execute();
    StringBuilder inputLog = newMockModel.getLogger();
    StringBuilder outputLogs = mockView.getPrintedOutput();
    String[] logChecker = inputLog.toString().split("\n");
    String[] outputLogChecker = outputLogs.toString().split("\n");
    assertEquals("Test",
            logChecker[logChecker.length - 1]);
    for (int i = 0; i < outputLogChecker.length; i++) {
      assertEquals(expectedOutputLog[i], outputLogChecker[i]);
    }
  }

  @Test
  public void testCreatePortfolioWithDecimalTotalNumberOfStocks()  {
    String[] expectedOutputLog = {"Enter your choice: ", "Enter your choice: ","Enter "
            + "the name of the "
            + "portfolio: ", "Enter the number of stocks you want to have in this "
            + "portfolio: ", "Error", "Enter "
            + "the number of stocks you want to have in this portfolio: ", "Enter the name of "
            + "the share or ticker symbol: ", "Enter the quantity of AAPLL you "
            + "have: ", "Error", "Enter the name of the share or ticker symbol: ", "Enter "
            + "the quantity of AAPL you "
            + "have: ", "Enter your "
            + "choice: "};
    Reader in = new StringReader("1\n1\nTest\n1.5\n1\nAAPLL\n10\nAAPL\n10\n11\n");

    MockModel newMockModel = new MockModel(mockName);
    this.controller =  new StockControllerImpl(mockView, in, newMockModel);
    controller.execute();
    StringBuilder inputLog = newMockModel.getLogger();
    StringBuilder outputLogs = mockView.getPrintedOutput();
    String[] logChecker = inputLog.toString().split("\n");
    String[] outputLogChecker = outputLogs.toString().split("\n");
    assertEquals("Test",
            logChecker[logChecker.length - 1]);
    for (int i = 0; i < outputLogChecker.length; i++) {
      assertEquals(expectedOutputLog[i], outputLogChecker[i]);
    }
  }

  @Test
  public void testCreatePortfolioWithStringTotalNumberOfStocks()  {
    String[] expectedOutputLog = {"Enter your choice: ","Enter your choice: " ,"Enter the name of "
            + "the portfolio: ", "Enter the number of stocks you want to have in this "
            + "portfolio: ", "Error", "Enter "
            + "the number of stocks you want to have in this portfolio: ", "Enter the name of the "
            + "share or ticker symbol: ", "Enter the quantity of AAPLL you "
            + "have: ", "Error", "Enter the name of the share or ticker symbol: ", "Enter the "
            + "quantity of AAPL you "
            + "have: ", "Enter your "
            + "choice: "};
    Reader in = new StringReader("1\n1\nTest\nhell\n1\nAAPLL\n10\nAAPL\n10\n11\n");

    MockModel newMockModel = new MockModel(mockName);
    this.controller =  new StockControllerImpl(mockView, in, newMockModel);
    controller.execute();
    StringBuilder inputLog = newMockModel.getLogger();
    StringBuilder outputLogs = mockView.getPrintedOutput();
    String[] logChecker = inputLog.toString().split("\n");
    String[] outputLogChecker = outputLogs.toString().split("\n");
    assertEquals("Test",
            logChecker[logChecker.length - 1]);
    for (int i = 0; i < outputLogChecker.length; i++) {
      assertEquals(expectedOutputLog[i], outputLogChecker[i]);
    }
  }

  @Test
  public void testCreatePortfolioWithNegativeQuantityOfShares()  {
    String[] expectedOutputLog = {"Enter your choice: ","Enter your choice: " ,"Enter the "
            + "name of the "
            + "portfolio: ", "Enter "
            + "the number of stocks you want to have in this portfolio: ", "Enter the"
            + " name of the share or ticker symbol: ", "Enter the"
            + " quantity of AAPL you "
            + "have: ", "Error", "Enter the quantity of AAPL you "
            + "have: ",  "Enter your choice: "};
    Reader in = new StringReader("1\n1\nTest\n1\nAAPL\n-10\n10\n11\n");

    MockModel newMockModel = new MockModel(mockName);
    this.controller =  new StockControllerImpl(mockView, in, newMockModel);
    controller.execute();
    StringBuilder inputLog = newMockModel.getLogger();
    StringBuilder outputLogs = mockView.getPrintedOutput();
    String[] logChecker = inputLog.toString().split("\n");
    String[] outputLogChecker = outputLogs.toString().split("\n");
    assertEquals("Test",
            logChecker[logChecker.length - 1]);
    for (int i = 0; i < outputLogChecker.length; i++) {
      assertEquals(expectedOutputLog[i], outputLogChecker[i]);
    }
  }

  @Test
  public void testCreatePortfolioWithFractionalQuantityOfShares()  {
    String[] expectedOutputLog = {"Enter your choice: ","Enter your choice: " ,"Enter "
            + "the name of the "
            + "portfolio: ", "Enter the number of stocks you want to have in this "
            + "portfolio: ", "Enter the name of the share or ticker symbol: ", "Enter "
            + "the quantity of AAPL you "
            + "have: ", "Error", "Enter the quantity of AAPL you "
            + "have: ",  "Enter your choice: "};
    Reader in = new StringReader("1\n1\nTest\n1\nAAPL\n10.5\n10\n11\n");

    MockModel newMockModel = new MockModel(mockName);
    this.controller =  new StockControllerImpl(mockView, in, newMockModel);
    controller.execute();
    StringBuilder inputLog = newMockModel.getLogger();
    StringBuilder outputLogs = mockView.getPrintedOutput();
    String[] logChecker = inputLog.toString().split("\n");
    String[] outputLogChecker = outputLogs.toString().split("\n");
    assertEquals("Test",
            logChecker[logChecker.length - 1]);
    for (int i = 0; i < outputLogChecker.length; i++) {
      assertEquals(expectedOutputLog[i], outputLogChecker[i]);
    }
  }

  @Test
  public void testCreatePortfolioWithStringQuantityOfShares()  {
    String[] expectedOutputLog = {"Enter your choice: ","Enter your choice: " ,"Enter the "
            + "name of the "
            + "portfolio: ", "Enter the number of stocks you want to have in this "
            + "portfolio: ", "Enter the name of the share or ticker symbol: ", "Enter "
            + "the quantity of AAPL you "
            + "have: ", "Error", "Enter the quantity of AAPL you "
            + "have: ",  "Enter your choice: "};
    Reader in = new StringReader("1\n1\nTest\n1\nAAPL\nten\n10\n11\n");

    MockModel newMockModel = new MockModel(mockName);
    this.controller =  new StockControllerImpl(mockView, in, newMockModel);
    controller.execute();
    StringBuilder inputLog = newMockModel.getLogger();
    StringBuilder outputLogs = mockView.getPrintedOutput();
    String[] logChecker = inputLog.toString().split("\n");
    String[] outputLogChecker = outputLogs.toString().split("\n");
    assertEquals("Test",
            logChecker[logChecker.length - 1]);
    for (int i = 0; i < outputLogChecker.length; i++) {
      assertEquals(expectedOutputLog[i], outputLogChecker[i]);
    }
  }

  @Test
  public void testCreatePortfolioWithTwoStocks()  {
    String[] expectedOutputLog = {"Enter your choice: ","Enter your choice: " ,"Enter "
            + "the name of the"
            + " portfolio: ", "Enter the number of stocks you want to have in this "
            + "portfolio: ", "Enter the name of the share or ticker symbol: ", "Enter "
            + "the quantity of AAPL you "
            + "have: ", "Enter the name of the share or ticker symbol: ", "Enter the "
            + "quantity of GOOG you have: ",  "Enter your choice: "};
    Reader in = new StringReader("1\n1\nTest\n2\nAAPL\n10\nGOOG\n20\n11\n");

    MockModel newMockModel = new MockModel(mockName);
    this.controller =  new StockControllerImpl(mockView, in, newMockModel);
    controller.execute();
    StringBuilder inputLog = newMockModel.getLogger();
    StringBuilder outputLogs = mockView.getPrintedOutput();
    String[] logChecker = inputLog.toString().split("\n");
    String[] outputLogChecker = outputLogs.toString().split("\n");
    assertEquals("Test",
            logChecker[logChecker.length - 1]);
    for (int i = 0; i < outputLogChecker.length; i++) {
      assertEquals(expectedOutputLog[i], outputLogChecker[i]);
    }
  }

  @Test
  public void testTotalValue()  {
    String[] expectedOutputLog = {"Enter your choice: ", "Get total value of a "
            + "portfolio for certain date", "Test Portfolio inflexible", "Enter the Portfolio "
            + "number you want to select.", "Enter the date for which you want to "
            + "get the total price " + "of the portfolio. ", "The "
            + "date should be in this format yyyy-mm-dd: ", "Wait until the total value is "
            + "calculated", mockValue + "", "Enter your choice: "};
    Reader in = new StringReader("4\n0\n2024-03-05\n11\n");

    this.controller =  new StockControllerImpl(mockView, in, mockModel);
    controller.execute();
    StringBuilder inputLog = mockModel.getLogger();
    StringBuilder outputLogs = mockView.getPrintedOutput();
    String[] logChecker = inputLog.toString().split("\n");
    String[] outputLogChecker = outputLogs.toString().split("\n");
    assertEquals("Retrieving composition for portfolio at index: 0 For the day: "
                    + "5 month: 3 year: " + "2024",
            logChecker[logChecker.length - 1]);
    for (int i = 0; i < outputLogChecker.length; i++) {
      assertEquals(expectedOutputLog[i], outputLogChecker[i]);
    }
  }

  @Test
  public void testTotalValueForInvalidPortfolioChoice()  {
    String[] expectedOutputLog = {"Enter your choice: ", "Get total value of a portfolio"
            + " for certain date", "Test Portfolio inflexible", "Enter "
            + "the Portfolio number you want "
            + "to select.", "Error", "Enter the Portfolio number you want to select.", "Enter"
            + " the date for which you want to get the total price of the portfolio. ", "The "
            + "date should be in this format yyyy-mm-dd: ", "Wait until the total value is "
            + "calculated", mockValue + "", "Enter your choice: "};
    Reader in = new StringReader("4\n3\n0\n2024-03-05\n11\n");

    this.controller =  new StockControllerImpl(mockView, in, mockModel);
    controller.execute();
    StringBuilder inputLog = mockModel.getLogger();
    StringBuilder outputLogs = mockView.getPrintedOutput();
    String[] logChecker = inputLog.toString().split("\n");
    String[] outputLogChecker = outputLogs.toString().split("\n");
    assertEquals("Retrieving composition for portfolio at index: 0 For the day: "
                    + "5 month: 3 year: " + "2024",
            logChecker[logChecker.length - 1]);
    for (int i = 0; i < outputLogChecker.length; i++) {
      assertEquals(expectedOutputLog[i], outputLogChecker[i]);
    }
  }

  @Test
  public void testTotalValueForNegativePortfolioChoice()  {
    String[] expectedOutputLog = {"Enter your choice: ", "Get total value of a portfolio for "
            + "certain date", "Test Portfolio inflexible", "Enter the "
            + "Portfolio number you want to "
            + "select.", "Error", "Enter the Portfolio number you want to select.", "Enter the "
            + "date for which you want to get the total price of the portfolio. ", "The "
            + "date should be in this format yyyy-mm-dd: ", "Wait until the total value is "
            + "calculated", mockValue + "", "Enter your choice: "};
    Reader in = new StringReader("4\n-3\n0\n2024-03-05\n11\n");

    this.controller =  new StockControllerImpl(mockView, in, mockModel);
    controller.execute();
    StringBuilder inputLog = mockModel.getLogger();
    StringBuilder outputLogs = mockView.getPrintedOutput();
    String[] logChecker = inputLog.toString().split("\n");
    String[] outputLogChecker = outputLogs.toString().split("\n");
    assertEquals("Retrieving composition for portfolio at index: 0 For the day: 5 "
                    + "month: 3 year: " + "2024",
            logChecker[logChecker.length - 1]);
    for (int i = 0; i < outputLogChecker.length; i++) {
      assertEquals(expectedOutputLog[i], outputLogChecker[i]);
    }
  }

  @Test
  public void testTotalValueForStringPortfolioChoice()  {
    String[] expectedOutputLog = {"Enter your choice: ", "Get total value of a portfolio for "
            + "certain date", "Test Portfolio inflexible", "Enter the Portfolio "
            + "number you want to "
            + "select.", "Error", "Enter the Portfolio number you want to select.", "Enter the"
            + " date for which you want to get the total price of the portfolio. ", "The "
            + "date should be in this format yyyy-mm-dd: ", "Wait until the total value is "
            + "calculated", mockValue + "", "Enter your choice: "};
    Reader in = new StringReader("4\nzero\n0\n2024-03-05\n11\n");

    this.controller =  new StockControllerImpl(mockView, in, mockModel);
    controller.execute();
    StringBuilder inputLog = mockModel.getLogger();
    StringBuilder outputLogs = mockView.getPrintedOutput();
    String[] logChecker = inputLog.toString().split("\n");
    String[] outputLogChecker = outputLogs.toString().split("\n");
    assertEquals("Retrieving composition for portfolio at index: 0 For the day: 5 "
                    + "month: 3 year: " + "2024",
            logChecker[logChecker.length - 1]);
    for (int i = 0; i < outputLogChecker.length; i++) {
      assertEquals(expectedOutputLog[i], outputLogChecker[i]);
    }
  }

  @Test
  public void testTotalValueForInvalidDateFormat()  {
    String[] expectedOutputLog = {"Enter your choice: ", "Get total value of a portfolio for "
            + "certain date", "Test Portfolio inflexible", "Enter the Portfolio number "
            + "you want to "
            + "select.", "Enter the date for which you want to get the total price of the "
            + "portfolio. ", "The "
            + "date should be in this format yyyy-mm-dd: ", "Error",  "Enter the date "
            + "for which you want to get the total price of the portfolio. ", "The "
            + "date should be in this format yyyy-mm-dd: ", "Wait until the total value"
            + " is " + "calculated", mockValue + "", "Enter your choice: "};
    Reader in = new StringReader("4\n0\n05-03-2024\n2024-03-05\n11\n");

    this.controller =  new StockControllerImpl(mockView, in, mockModel);
    controller.execute();
    StringBuilder inputLog = mockModel.getLogger();
    StringBuilder outputLogs = mockView.getPrintedOutput();
    String[] logChecker = inputLog.toString().split("\n");
    String[] outputLogChecker = outputLogs.toString().split("\n");
    assertEquals("Retrieving composition for portfolio at index: 0 For the day: 5 "
                    + "month: 3 year: " + "2024",
            logChecker[logChecker.length - 1]);
    for (int i = 0; i < outputLogChecker.length; i++) {
      assertEquals(expectedOutputLog[i], outputLogChecker[i]);
    }
  }

  @Test
  public void testTotalValueForInvalidDay()  {
    String[] expectedOutputLog = {"Enter your choice: ", "Get total value of a portfolio for "
            + "certain date", "Test Portfolio inflexible", "Enter the Portfolio number "
            + "you want to "
            + "select.", "Enter the date for which you want to get the total price of the "
            + "portfolio. ", "The "
            + "date should be in this format yyyy-mm-dd: ", "Error",  "Enter the date for which "
            + "you want to get the total price of the portfolio. ", "The "
            + "date should be in this format yyyy-mm-dd: ", "Wait until the total value"
            + " is " + "calculated", mockValue + "", "Enter your choice: "};
    Reader in = new StringReader("4\n0\n2024-03-35\n2024-02-25\n11\n");

    this.controller =  new StockControllerImpl(mockView, in, mockModel);
    controller.execute();
    StringBuilder inputLog = mockModel.getLogger();
    StringBuilder outputLogs = mockView.getPrintedOutput();
    String[] logChecker = inputLog.toString().split("\n");
    String[] outputLogChecker = outputLogs.toString().split("\n");
    assertEquals("Retrieving composition for portfolio at index: 0 For the day: "
                    + "25 month: 2 year:" + " " + "2024",
            logChecker[logChecker.length - 1]);
    for (int i = 0; i < outputLogChecker.length; i++) {
      assertEquals(expectedOutputLog[i], outputLogChecker[i]);
    }
  }

  @Test
  public void testTotalValueForInvalidMonth()  {
    String[] expectedOutputLog = {"Enter your choice: ", "Get total value of a portfolio for "
            + "certain date", "Test Portfolio inflexible", "Enter the Portfolio number "
            + "you want to "
            + "select.", "Enter the date for which you want to get the total price of the "
            + "portfolio. ", "The "
            + "date should be in this format yyyy-mm-dd: ", "Error",  "Enter the date for "
            + "which you want to get the total price of the portfolio. ", "The "
            + "date should be in this format yyyy-mm-dd: ", "Wait until the total value"
            + " is "
            + "calculated", mockValue + "", "Enter your choice: "};
    Reader in = new StringReader("4\n0\n2024-13-35\n2024-02-25\n11\n");

    this.controller =  new StockControllerImpl(mockView, in, mockModel);
    controller.execute();
    StringBuilder inputLog = mockModel.getLogger();
    StringBuilder outputLogs = mockView.getPrintedOutput();
    String[] logChecker = inputLog.toString().split("\n");
    String[] outputLogChecker = outputLogs.toString().split("\n");
    assertEquals("Retrieving composition for portfolio at index: 0 For the day: "
                    + "25 month: 2 year:" + " " + "2024",
            logChecker[logChecker.length - 1]);
    for (int i = 0; i < outputLogChecker.length; i++) {
      assertEquals(expectedOutputLog[i], outputLogChecker[i]);
    }
  }

  @Test
  public void testTotalValueForInvalidMonthDayCombination()  {
    String[] expectedOutputLog = {"Enter your choice: ", "Get total value of a portfolio for "
            + "certain date", "Test Portfolio inflexible", "Enter the Portfolio number you "
            + "want to select.", "Enter the date for which you want to get the total price of "
            + "the portfolio. ", "The "
            + "date should be in this format yyyy-mm-dd: ", "Error",  "Enter the date for "
            + "which you want to get the total price of the portfolio. ", "The "
            + "date should be in this format yyyy-mm-dd: ", "Wait until the total value"
            + " is "
            + "calculated", mockValue + "", "Enter your choice: "};
    Reader in = new StringReader("4\n0\n2024-11-31\n2024-02-25\n11\n");

    this.controller =  new StockControllerImpl(mockView, in, mockModel);
    controller.execute();
    StringBuilder inputLog = mockModel.getLogger();
    StringBuilder outputLogs = mockView.getPrintedOutput();
    String[] logChecker = inputLog.toString().split("\n");
    String[] outputLogChecker = outputLogs.toString().split("\n");
    assertEquals("Retrieving composition for portfolio at index: 0 For the day: 25 month: "
                    + "2 year:" + " " + "2024",
            logChecker[logChecker.length - 1]);
    for (int i = 0; i < outputLogChecker.length; i++) {
      assertEquals(expectedOutputLog[i], outputLogChecker[i]);
    }
  }

  @Test
  public void testTotalValueForZeroDay()  {
    String[] expectedOutputLog = {"Enter your choice: ", "Get total value of a portfolio for "
            + "certain date", "Test Portfolio inflexible", "Enter the Portfolio number"
            + " you want to "
            + "select.", "Enter the date for which you want to get the total price of the "
            + "portfolio. ", "The "
            + "date should be in this format yyyy-mm-dd: ", "Error",  "Enter the date for which "
            + "you want to get the total price of the portfolio. ", "The "
            + "date should be in this format yyyy-mm-dd: ", "Wait until the total value"
            + " is "
            + "calculated", mockValue + "", "Enter your choice: "};
    Reader in = new StringReader("4\n0\n2024-11-0\n2024-02-25\n11\n");

    this.controller =  new StockControllerImpl(mockView, in, mockModel);
    controller.execute();
    StringBuilder inputLog = mockModel.getLogger();
    StringBuilder outputLogs = mockView.getPrintedOutput();
    String[] logChecker = inputLog.toString().split("\n");
    String[] outputLogChecker = outputLogs.toString().split("\n");
    assertEquals("Retrieving composition for portfolio at index: 0 For the day: 25 "
                    + "month: 2 year:" + " " + "2024",
            logChecker[logChecker.length - 1]);
    for (int i = 0; i < outputLogChecker.length; i++) {
      assertEquals(expectedOutputLog[i], outputLogChecker[i]);
    }
  }

  @Test
  public void testTotalValueForZeroMonth()  {
    String[] expectedOutputLog = {"Enter your choice: ", "Get total value of a portfolio "
            + "for certain date", "Test Portfolio inflexible", "Enter the Portfolio number "
            + "you want to "
            + "select.", "Enter the date for which you want to get the total price of the "
            + "portfolio. ", "The "
            + "date should be in this format yyyy-mm-dd: ", "Error",  "Enter the date for "
            + "which you want to get the total price of the portfolio. ", "The "
            + "date should be in this format yyyy-mm-dd: ", "Wait until the total value"
            + " is "
            + "calculated", mockValue + "", "Enter your choice: "};
    Reader in = new StringReader("4\n0\n2024-0-11\n2024-02-25\n11\n");

    this.controller =  new StockControllerImpl(mockView, in, mockModel);
    controller.execute();
    StringBuilder inputLog = mockModel.getLogger();
    StringBuilder outputLogs = mockView.getPrintedOutput();
    String[] logChecker = inputLog.toString().split("\n");
    String[] outputLogChecker = outputLogs.toString().split("\n");
    assertEquals("Retrieving composition for portfolio at index: 0 For the day: 25 "
                    + "month: 2 year:"
                    + " "
                    + "2024",
            logChecker[logChecker.length - 1]);
    for (int i = 0; i < outputLogChecker.length; i++) {
      assertEquals(expectedOutputLog[i], outputLogChecker[i]);
    }
  }

  @Test
  public void testTotalValueForNegativeValues()  {
    String[] expectedOutputLog = {"Enter your choice: ", "Get total value of a portfolio for"
            + " certain date", "Test Portfolio inflexible", "Enter the Portfolio"
            + " number you want to "
            + "select.", "Enter the date for which you want to get the total price of the "
            + "portfolio. ", "The "
            + "date should be in this format yyyy-mm-dd: ", "Error",  "Enter the date for which "
            + "you want to get the total price of the portfolio. ", "The "
            + "date should be in this format yyyy-mm-dd: ", "Wait until the total value"
            + " is "
            + "calculated", mockValue + "", "Enter your choice: "};
    Reader in = new StringReader("4\n0\n-2024-11-11\n2024-02-25\n11\n");

    this.controller =  new StockControllerImpl(mockView, in, mockModel);
    controller.execute();
    StringBuilder inputLog = mockModel.getLogger();
    StringBuilder outputLogs = mockView.getPrintedOutput();
    String[] logChecker = inputLog.toString().split("\n");
    String[] outputLogChecker = outputLogs.toString().split("\n");
    assertEquals("Retrieving composition for portfolio at index: 0 For the day: 25 month: "
                    + "2 year:" + " " + "2024",
            logChecker[logChecker.length - 1]);
    for (int i = 0; i < outputLogChecker.length; i++) {
      assertEquals(expectedOutputLog[i], outputLogChecker[i]);
    }
  }

  @Test
  public void testSavePortfolio()  {
    String path = System.getProperty("user.dir") + "/testFiles/saveAndLoad.csv";
    String[] expectedOutputLog = {"Enter your choice: ", "Test Portfolio inflexible", "Enter the "
            + "Portfolio number you want to select.", "Enter the proper path with file name in "
            + "which you would like to save portfolio.", "Portfolio exported to "
            + path + " successfully.", "Enter your choice: "};
    Reader in = new StringReader("5\n1\n" + path + "\n11\n");
    PortfolioImpl.PortfolioBuilder newBuilder
            = new PortfolioImpl.PortfolioBuilder("Vidith");
    newBuilder.addShare("AAPL", 20);
    mockModel.addPortfolio(newBuilder);

    this.controller =  new StockControllerImpl(mockView, in, mockModel);
    controller.execute();
    StringBuilder outputLogs = mockView.getPrintedOutput();
    String[] outputLogChecker = outputLogs.toString().split("\n");
    for (int i = 0; i < outputLogChecker.length; i++) {
      assertEquals(expectedOutputLog[i], outputLogChecker[i]);
    }
    assertTrue(new File(path).exists());
  }

  @Test
  public void testSaveAndThenLoadPortfolio()  {
    String path = System.getProperty("user.dir") + "/testFiles/saveAndLoad.csv";
    String[] expectedOutputLog = {"Enter your choice: ", "Test Portfolio inflexible", "Enter "
            + "the Portfolio "
            + "number you want to select.", "Enter the proper path with file name in which you "
            + "would like to save portfolio.", "Portfolio exported to "
            + path + " successfully.", "Enter your choice: ", "Enter the name of the "
            + "portfolio: ", "Enter the full path of "
            + "the file you want to load data from: ", "File loaded successfully", "Enter your "
            + "choice: "};
    Reader in = new StringReader("5\n1\n" + path + "\n2\nTest2\n" + path + "\n11\n");
    PortfolioImpl.PortfolioBuilder newBuilder
            = new PortfolioImpl.PortfolioBuilder("Vidith");
    newBuilder.addShare("AAPL", 20);
    mockModel.addPortfolio(newBuilder);

    this.controller =  new StockControllerImpl(mockView, in, mockModel);
    controller.execute();
    StringBuilder outputLogs = mockView.getPrintedOutput();
    String[] outputLogChecker = outputLogs.toString().split("\n");
    for (int i = 0; i < outputLogChecker.length; i++) {
      assertEquals(expectedOutputLog[i], outputLogChecker[i]);
    }
    assertTrue(new File(path).exists());
  }

  @Test
  public void testSavePortfolioForInvalidPortfolioChoice()  {
    String path = System.getProperty("user.dir") + "/testFiles/saveAndLoad.csv";
    String[] expectedOutputLog = {"Enter your choice: ", "Test Portfolio inflexible", "Enter the "
            + "Portfolio number you want to select.", "Error", "Enter "
            + "the Portfolio number you want to select.", "Enter the proper path with file "
            + "name in which you would like to save portfolio.", "Portfolio exported to "
            + path + " successfully.", "Enter your choice: "};

    Reader in = new StringReader("5\n3\n0\n" + path + "\n11\n");

    this.controller =  new StockControllerImpl(mockView, in, mockModel);
    controller.execute();
    StringBuilder outputLogs = mockView.getPrintedOutput();
    String[] outputLogChecker = outputLogs.toString().split("\n");
    for (int i = 0; i < outputLogChecker.length; i++) {
      assertEquals(expectedOutputLog[i], outputLogChecker[i]);
    }
    assertTrue(new File(path).exists());
  }

  @Test
  public void testSavePortfolioForNegativePortfolioChoice()  {
    String path = System.getProperty("user.dir") + "/testFiles/saveAndLoad.csv";
    String[] expectedOutputLog = {"Enter your choice: ", "Test Portfolio inflexible", "Enter "
            + "the Portfolio "
            + "number you want to select.", "Error", "Enter "
            + "the Portfolio number you want to select.", "Enter the proper path with file name in "
            + "which you would like to save portfolio.", "Portfolio exported to "
            + path + " successfully.", "Enter your choice: "};
    Reader in = new StringReader("5\n-3\n0\n" + path + "\n11\n");

    this.controller =  new StockControllerImpl(mockView, in, mockModel);
    controller.execute();
    StringBuilder outputLogs = mockView.getPrintedOutput();
    String[] outputLogChecker = outputLogs.toString().split("\n");
    for (int i = 0; i < outputLogChecker.length; i++) {
      assertEquals(expectedOutputLog[i], outputLogChecker[i]);
    }
    assertTrue(new File(path).exists());
  }

  @Test
  public void testSavePortfolioForInvalidFileType()  {
    String path = System.getProperty("user.dir") + "/testFiles/saveAndLoad.txt";
    String[] expectedOutputLog = {"Enter your choice: ", "Test Portfolio inflexible", "Enter "
            + "the Portfolio "
            + "number you want to select.", "Error", "Enter "
            + "the Portfolio number you want to select.", "Enter the proper path with "
            + "file name in which you would like to save portfolio.", "Error", "Enter your"
            + " choice: "};
    Reader in = new StringReader("5\n-3\n0\n" + path + "\n11\n");

    this.controller =  new StockControllerImpl(mockView, in, mockModel);
    controller.execute();
    StringBuilder outputLogs = mockView.getPrintedOutput();
    String[] outputLogChecker = outputLogs.toString().split("\n");
    for (int i = 0; i < outputLogChecker.length; i++) {
      assertEquals(expectedOutputLog[i], outputLogChecker[i]);
    }
    assertFalse(new File(path).exists());
  }

  @Test
  public void testSavePortfolioForEmptyFileName()  {
    String path = System.getProperty("user.dir") + "/testFiles/.csv";
    String[] expectedOutputLog = {"Enter your choice: ", "Test Portfolio inflexible", "Enter"
            + " the Portfolio "
            + "number you want to select.", "Error", "Enter "
            + "the Portfolio number you want to select.", "Enter the proper path with "
            + "file name in which you would like to save portfolio.", "Error", "Enter "
            + "your choice: "};
    Reader in = new StringReader("5\n-3\n0\n" + path + "\n11\n");

    this.controller =  new StockControllerImpl(mockView, in, mockModel);
    controller.execute();
    StringBuilder outputLogs = mockView.getPrintedOutput();
    String[] outputLogChecker = outputLogs.toString().split("\n");
    for (int i = 0; i < outputLogChecker.length; i++) {
      assertEquals(expectedOutputLog[i], outputLogChecker[i]);
    }
    assertFalse(new File(path).exists());
  }

  @Test
  public void testLoadPortfolio()  {
    String path = System.getProperty("user.dir") + "/test/testFiles/loadTest.csv";
    String[] expectedOutputLog = {"Enter your choice: ","Enter your choice: " ,"Enter the name of the "
            + "portfolio: ", "Enter the full path of the file you want to load data "
            + "from: ", "File loaded "
            + "successfully", "Enter your choice: "};
    Reader in = new StringReader("2\n1\nTest\n" + path + "\n11\n");

    this.controller =  new StockControllerImpl(mockView, in, mockModel);
    controller.execute();
    StringBuilder outputLogs = mockView.getPrintedOutput();
    String[] outputLogChecker = outputLogs.toString().split("\n");
    for (int i = 0; i < outputLogChecker.length; i++) {
      assertEquals(expectedOutputLog[i], outputLogChecker[i]);
    }
  }

  @Test
  public void testLoadPortfolioWithNonExistingFile()  {
    String incorrectPath = System.getProperty("user.dir") + "/test/testFiles/load.csv";
    String correctPath = System.getProperty("user.dir") + "/test/testFiles/loadTest.csv";
    String[] expectedOutputLog = {"Enter your choice: ", "Enter the name of the "
            + "portfolio: ", "Enter the full path of the file you want to load data "
            + "from: ", "Error", "Enter the "
            + "full path of the file you want to load data from: ", "File "
            + "loaded"
            + " successfully", "Enter your choice: "};
    Reader in = new StringReader("2\nTest\n" + incorrectPath + "\n" + correctPath + "\n11\n");

    this.controller =  new StockControllerImpl(mockView, in, mockModel);
    controller.execute();
    StringBuilder outputLogs = mockView.getPrintedOutput();
    String[] outputLogChecker = outputLogs.toString().split("\n");
    for (int i = 0; i < outputLogChecker.length; i++) {
      assertEquals(expectedOutputLog[i], outputLogChecker[i]);
    }
  }

  @Test
  public void testLoadPortfolioWithInvalidFileType()  {
    String incorrectPath = System.getProperty("user.dir") + "/test/testFiles/load.txt";
    String correctPath = System.getProperty("user.dir") + "/test/testFiles/loadTest.csv";
    String[] expectedOutputLog = {"Enter your choice: ", "Enter the name of the "
            + "portfolio: ", "Enter the full path of the file you want to load data "
            + "from: ", "Error", "Enter the "
            + "full path of the file you want to load data from: ", "File "
            + "loaded" + " successfully", "Enter your choice: "};
    Reader in = new StringReader("2\n1\nTest\n" + incorrectPath + "\n" + correctPath + "\n6\n");

    this.controller =  new StockControllerImpl(mockView, in, mockModel);
    controller.execute();
    StringBuilder outputLogs = mockView.getPrintedOutput();
    String[] outputLogChecker = outputLogs.toString().split("\n");
    for (int i = 0; i < outputLogChecker.length; i++) {
      assertEquals(expectedOutputLog[i], outputLogChecker[i]);
    }
  }

  @Test
  public void testingGoWithInvalidChoice() {
    String[] expectedOutputLog = {"Enter your choice: ", "Error", "Enter your choice: "};
    Reader in = new StringReader("12\n11\n");

    this.controller =  new StockControllerImpl(mockView, in, mockModel);
    controller.execute();
    StringBuilder inputLog = mockModel.getLogger();
    StringBuilder outputLogs = mockView.getPrintedOutput();
    String[] logChecker = inputLog.toString().split("\n");
    String[] outputLogChecker = outputLogs.toString().split("\n");
    for (int i = 0; i < outputLogChecker.length; i++) {
      assertEquals(expectedOutputLog[i], outputLogChecker[i]);
    }
  }

  @Test
  public void testingGoWithNegativeChoice() {
    String[] expectedOutputLog = {"Enter your choice: ", "Error", "Enter your choice: "};
    Reader in = new StringReader("-7\n11\n");

    this.controller =  new StockControllerImpl(mockView, in, mockModel);
    controller.execute();
    StringBuilder inputLog = mockModel.getLogger();
    StringBuilder outputLogs = mockView.getPrintedOutput();
    String[] logChecker = inputLog.toString().split("\n");
    String[] outputLogChecker = outputLogs.toString().split("\n");
    for (int i = 0; i < outputLogChecker.length; i++) {
      assertEquals(expectedOutputLog[i], outputLogChecker[i]);
    }
  }

  @Test
  public void testingGoWithNegativeStringType() {
    String[] expectedOutputLog = {"Enter your choice: ", "Error", "Enter your choice: "};
    Reader in = new StringReader("seven\n11\n");

    this.controller =  new StockControllerImpl(mockView, in, mockModel);
    controller.execute();
    StringBuilder inputLog = mockModel.getLogger();
    StringBuilder outputLogs = mockView.getPrintedOutput();
    String[] logChecker = inputLog.toString().split("\n");
    String[] outputLogChecker = outputLogs.toString().split("\n");
    for (int i = 0; i < outputLogChecker.length; i++) {
      assertEquals(expectedOutputLog[i], outputLogChecker[i]);
    }
  }

  @Test
  public void testingGoWithForPrimaryMenu() {
    String[] expectedOutputLog = {"Enter your choice: ", "Error", "Enter your choice: "};
    Reader in = new StringReader("4\n");

    MockModel newMockModel = new MockModel(mockName);
    this.controller =  new StockControllerImpl(mockView, in, newMockModel);
    controller.execute();
    StringBuilder outputLogs = mockView.getPrintedOutput();
    String[] outputLogChecker = outputLogs.toString().split("\n");
    for (int i = 0; i < outputLogChecker.length; i++) {
      assertEquals(expectedOutputLog[i], outputLogChecker[i]);
    }
  }

  @Test
  public void testingGoWithForPrimaryMenuForInvalidChoice() {
    String[] expectedOutputLog = {"Enter your choice: ", "Error", "Enter your choice: "};
    Reader in = new StringReader("12\n4\n");

    MockModel newMockModel = new MockModel(mockName);
    this.controller =  new StockControllerImpl(mockView, in, newMockModel);
    controller.execute();
    StringBuilder outputLogs = mockView.getPrintedOutput();
    String[] outputLogChecker = outputLogs.toString().split("\n");
    for (int i = 0; i < outputLogChecker.length; i++) {
      assertEquals(expectedOutputLog[i], outputLogChecker[i]);
    }
  }

  @Test
  public void testStockGainLoss() {
    String[] expectedOutputLog = {"Enter your choice: ","Enter your choice: ","Enter the name"
            + " of the share or ticker symbol: ","Enter the date to know if the above stock gained "
            + "or lost on that date: ","The date should be in this format yyyy-mm-dd: ","Test"
            + " Portfolio","Enter your choice: ","Enter your choice: " ,"Error", "Enter"
            + " your choice: "};
    Reader in = new StringReader("3\n1\naapl\n2024-03-20\n7\n4\n");
    MockModel newMockModel = new MockModel(mockName);
    this.controller =  new StockControllerImpl(mockView, in, newMockModel);
    controller.execute();
    StringBuilder inputLog = newMockModel.getLogger();
    StringBuilder outputLogs = mockView.getPrintedOutput();
    System.out.println(inputLog);
    String[] logChecker = inputLog.toString().split("\n");
    String[] outputLogChecker = outputLogs.toString().split("\n");
    assertEquals("Calculating gain or loss for aapl on 2024-03-20",
            logChecker[logChecker.length - 1]);
    for (int i = 0; i < outputLogChecker.length; i++) {
      assertEquals(expectedOutputLog[i], outputLogChecker[i]);
    }
  }

  @Test
  public void testStockGainLossStockWrong() {
    String[] expectedOutputLog = {"Enter your choice: ","Enter your choice: ","Enter the name"
            + " of the share or ticker symbol: ","Enter the date to know if the above stock gained "
            + "or lost on that date: ","The date should be in this format yyyy-mm-dd: ","Test"
            + " Portfolio","Enter your choice: ","Enter your choice: " ,"Error", "Enter"
            + " your choice: "};
    Reader in = new StringReader("3\n1\nhello\n2024-03-20\n7\n4\n");
    MockModel newMockModel = new MockModel(mockName);
    this.controller =  new StockControllerImpl(mockView, in, newMockModel);
    controller.execute();
    StringBuilder inputLog = newMockModel.getLogger();
    StringBuilder outputLogs = mockView.getPrintedOutput();
    String[] logChecker = inputLog.toString().split("\n");
    String[] outputLogChecker = outputLogs.toString().split("\n");
    assertEquals("Calculating gain or loss for hello on 2024-03-20",
            logChecker[logChecker.length - 1]);
    for (int i = 0; i < outputLogChecker.length; i++) {
      assertEquals(expectedOutputLog[i], outputLogChecker[i]);
    }
  }

  @Test
  public void testStockGainLossDateWrong() {
    String[] expectedOutputLog = {"Enter your choice: ","Enter your choice: ","Enter the name"
            + " of the share or ticker symbol: ","Enter the date to know if the above stock gained "
            + "or lost on that date: ","The date should be in this format"
            + " yyyy-mm-dd: ","Error","Enter the date to know if the above stock gained"
            + " or lost on that date: ","The date should be in this "
            + "format yyyy-mm-dd: ","Error","Enter the date to know if the above stock gained or "
            + "lost on that date: ","The date should be in this format yyyy-mm-dd: ","Test"
            + " Portfolio","Enter your choice: ","Enter your choice: " ,"Error", "Enter"
            + " your choice: "};
    Reader in = new StringReader("3\n1\naapl\n20245-03-20\n2024-13-12\n2024-03-20\n7\n4\n");
    MockModel newMockModel = new MockModel(mockName);
    this.controller =  new StockControllerImpl(mockView, in, newMockModel);
    controller.execute();
    StringBuilder inputLog = newMockModel.getLogger();
    StringBuilder outputLogs = mockView.getPrintedOutput();
    String[] logChecker = inputLog.toString().split("\n");
    String[] outputLogChecker = outputLogs.toString().split("\n");
    assertEquals("Calculating gain or loss for aapl on 2024-03-20",
            logChecker[logChecker.length - 1]);
    for (int i = 0; i < outputLogChecker.length; i++) {
      assertEquals(expectedOutputLog[i], outputLogChecker[i]);
    }
  }

  @Test
  public void testStockGainLossOverPeriod() {
    String[] expectedOutputLog = {"Enter your choice: ","Enter your choice: ","Enter the name"
            + " of the share or ticker symbol: ","Enter the start date","The date should be in " +
            "this format yyyy-mm-dd: ", "Enter the end date","The date should be in this format " +
            "yyyy-mm-dd: ", "Test"
            + " Portfolio","Enter your choice: ","Enter your choice: "};
    Reader in = new StringReader("3\n2\naapl\n2024-03-01\n2024-03-21\n7\n4\n");
    MockModel newMockModel = new MockModel(mockName);
    this.controller =  new StockControllerImpl(mockView, in, newMockModel);
    controller.execute();
    StringBuilder inputLog = newMockModel.getLogger();
    StringBuilder outputLogs = mockView.getPrintedOutput();
    String[] logChecker = inputLog.toString().split("\n");
    String[] outputLogChecker = outputLogs.toString().split("\n");
    assertEquals("Calculating gain or loss for aapl from 2024-03-01 to 2024-03-21",
            logChecker[logChecker.length - 1]);
    for (int i = 0; i < outputLogChecker.length; i++) {
      assertEquals(expectedOutputLog[i], outputLogChecker[i]);
    }
  }

  @Test
  public void testStockGainLossOverPeriodWrongStockName() {
    String[] expectedOutputLog = {"Enter your choice: ","Enter your choice: ","Enter the name"
            + " of the share or ticker symbol: ","Enter the start date","The date should be in " +
            "this format yyyy-mm-dd: ", "Enter the end date","The date should be in this format " +
            "yyyy-mm-dd: ", "Test"
            + " Portfolio", "Enter your choice: ","Enter your choice: "};
    Reader in = new StringReader("3\n2\nhello\n2024-03-01\n2024-03-21\n7\n4\n");
    MockModel newMockModel = new MockModel(mockName);
    this.controller =  new StockControllerImpl(mockView, in, newMockModel);
    controller.execute();
    StringBuilder inputLog = newMockModel.getLogger();
    StringBuilder outputLogs = mockView.getPrintedOutput();
    String[] logChecker = inputLog.toString().split("\n");
    String[] outputLogChecker = outputLogs.toString().split("\n");
    assertEquals("Calculating gain or loss for hello from 2024-03-01 to 2024-03-21",
            logChecker[logChecker.length - 1]);
    for (int i = 0; i < outputLogChecker.length; i++) {
      assertEquals(expectedOutputLog[i], outputLogChecker[i]);
    }
  }

  @Test
  public void testStockGainLossOverPeriodWrongEndDate() {
    String[] expectedOutputLog = {"Enter your choice: ","Enter your choice: ","Enter the name"
            + " of the share or ticker symbol: ","Enter the start date","The date should be in " +
            "this format yyyy-mm-dd: ", "Enter the end date","The date should be in " +
            "this format yyyy-mm-dd: ", "Error", "Enter the end date","The date should be in this" +
            " format " +
            "yyyy-mm-dd: ", "Test"
            + " Portfolio", "Enter your choice: ","Enter your choice: "};
    Reader in = new StringReader("3\n2\nAAPL\n2024-03-01\n2024-13-01\n2024-03-21\n7\n4\n");
    MockModel newMockModel = new MockModel(mockName);
    this.controller =  new StockControllerImpl(mockView, in, newMockModel);
    controller.execute();
    StringBuilder inputLog = newMockModel.getLogger();
    StringBuilder outputLogs = mockView.getPrintedOutput();
    String[] logChecker = inputLog.toString().split("\n");
    String[] outputLogChecker = outputLogs.toString().split("\n");
    assertEquals("Calculating gain or loss for AAPL from 2024-03-01 to 2024-03-21",
            logChecker[logChecker.length - 1]);
    for (int i = 0; i < outputLogChecker.length; i++) {
      assertEquals(expectedOutputLog[i], outputLogChecker[i]);
    }
  }

  @Test
  public void testStockGainLossOverPeriodWrongDate() {
    String[] expectedOutputLog = {"Enter your choice: ","Enter your choice: ","Enter the name"
            + " of the share or ticker symbol: ","Enter the start date","The date should be in " +
            "this format yyyy-mm-dd: ", "Error", "Enter the start date","The date should be in " +
            "this format yyyy-mm-dd: ", "Enter the end date","The date should be in this" +
            " format " +
            "yyyy-mm-dd: ", "Test"
            + " Portfolio", "Enter your choice: ","Enter your choice: "};
    Reader in = new StringReader("3\n2\nAAPL\n2024-13-01\n2024-03-01\n2024-03-21\n7\n4\n");
    MockModel newMockModel = new MockModel(mockName);
    this.controller =  new StockControllerImpl(mockView, in, newMockModel);
    controller.execute();
    StringBuilder inputLog = newMockModel.getLogger();
    StringBuilder outputLogs = mockView.getPrintedOutput();
    String[] logChecker = inputLog.toString().split("\n");
    String[] outputLogChecker = outputLogs.toString().split("\n");
    assertEquals("Calculating gain or loss for AAPL from 2024-03-01 to 2024-03-21",
            logChecker[logChecker.length - 1]);
    for (int i = 0; i < outputLogChecker.length; i++) {
      assertEquals(expectedOutputLog[i], outputLogChecker[i]);
    }
  }

  @Test
  public void testStockXDayMovingAvg() {
    String[] expectedOutputLog = {"Enter your choice: ","Enter your choice: ","Enter the name"
            + " of the share or ticker symbol: ","Enter the start date","The date should be in " +
            "this format yyyy-mm-dd: ", "Enter X days before the given date you want to find the " +
            "moving average for: ", "0.0","Enter your choice: ","Enter your choice: "};
    Reader in = new StringReader("3\n3\naapl\n2024-03-01\n10\n7\n4\n");
    MockModel newMockModel = new MockModel(mockName);
    this.controller =  new StockControllerImpl(mockView, in, newMockModel);
    controller.execute();
    StringBuilder inputLog = newMockModel.getLogger();
    StringBuilder outputLogs = mockView.getPrintedOutput();
    String[] logChecker = inputLog.toString().split("\n");
    String[] outputLogChecker = outputLogs.toString().split("\n");
    assertEquals("Calculating 10-day moving average for aapl on 2024-03-01",
            logChecker[logChecker.length - 1]);
    for (int i = 0; i < outputLogChecker.length; i++) {
      assertEquals(expectedOutputLog[i], outputLogChecker[i]);
    }
  }

  @Test
  public void testStockXDayMovingAvgWongDate() {
    String[] expectedOutputLog = {"Enter your choice: ","Enter your choice: ","Enter the name"
            + " of the share or ticker symbol: ","Enter the start date","The date should be in " +
            "this format yyyy-mm-dd: ", "Error", "Enter the start date","The date should be in " +
            "this format yyyy-mm-dd: ", "Enter X days before the given date you want to" +
            " find the" +
            " " +
            "moving average for: ", "0.0","Enter your choice: ","Enter your choice: "};
    Reader in = new StringReader("3\n3\naapl\n2024-03-32\n2024-03-01\n10\n7\n4\n");
    MockModel newMockModel = new MockModel(mockName);
    this.controller =  new StockControllerImpl(mockView, in, newMockModel);
    controller.execute();
    StringBuilder inputLog = newMockModel.getLogger();
    StringBuilder outputLogs = mockView.getPrintedOutput();
    String[] logChecker = inputLog.toString().split("\n");
    String[] outputLogChecker = outputLogs.toString().split("\n");
    assertEquals("Calculating 10-day moving average for aapl on 2024-03-01",
            logChecker[logChecker.length - 1]);
    for (int i = 0; i < outputLogChecker.length; i++) {
      assertEquals(expectedOutputLog[i], outputLogChecker[i]);
    }
  }

  @Test
  public void testStockXDayMovingAvgNegativeX() {
    String[] expectedOutputLog = {"Enter your choice: ","Enter your choice: ","Enter the name"
            + " of the share or ticker symbol: ","Enter the start date","The date should be in " +
            "this format yyyy-mm-dd: ", "Enter X days before the given date you want to" +
            " find the moving average for: ", "Error", "Enter X days before the given date you " +
            "want to find the moving average for: ", "0.0","Enter your choice: ","Enter your " +
            "choice:" +
            " "};
    Reader in = new StringReader("3\n3\naapl\n2024-03-12\n-10\n10\n7\n4\n");
    MockModel newMockModel = new MockModel(mockName);
    this.controller =  new StockControllerImpl(mockView, in, newMockModel);
    controller.execute();
    StringBuilder inputLog = newMockModel.getLogger();
    StringBuilder outputLogs = mockView.getPrintedOutput();
    String[] logChecker = inputLog.toString().split("\n");
    String[] outputLogChecker = outputLogs.toString().split("\n");
    assertEquals("Calculating 10-day moving average for aapl on 2024-03-12",
            logChecker[logChecker.length - 1]);
    for (int i = 0; i < outputLogChecker.length; i++) {
      assertEquals(expectedOutputLog[i], outputLogChecker[i]);
    }
  }

  @Test
  public void testCrossoverOverPeriod() {
    String[] expectedOutputLog = {"Enter your choice: ","Enter your choice: ","Enter the name"
            + " of the share or ticker symbol: ","Enter the start date","The date should be in " +
            "this format yyyy-mm-dd: ", "Enter the end date","The date should be in this" +
            " format " +
            "yyyy-mm-dd: ", "date buy/sell", "Enter your choice: ","Enter your choice: "};
    Reader in = new StringReader("9\n4\nAAPL\n2024-03-01\n2024-03-21\n7\n11\n");
    MockModel newMockModel = new MockModel(mockName);
    this.controller =  new StockControllerImpl(mockView, in, mockModel);
    controller.execute();
    StringBuilder inputLog = mockModel.getLogger();
    StringBuilder outputLogs = mockView.getPrintedOutput();
    String[] logChecker = inputLog.toString().split("\n");
    String[] outputLogChecker = outputLogs.toString().split("\n");
    assertEquals("Calculating crossover over period for AAPL from 2024-03-01 to 2024-03-21",
            logChecker[logChecker.length - 1]);
    for (int i = 0; i < outputLogChecker.length; i++) {
      assertEquals(expectedOutputLog[i], outputLogChecker[i]);
    }
  }

  @Test
  public void testCrossoverOverPeriodForWrongStartDate() {
    String[] expectedOutputLog = {"Enter your choice: ","Enter your choice: ","Enter the name"
            + " of the share or ticker symbol: ","Enter the start date","The date should be in " +
            "this format yyyy-mm-dd: ", "Error", "Enter the start date","The date should be in " +
            "this format yyyy-mm-dd: ", "Enter the end date","The date should be in this" +
            " format " +
            "yyyy-mm-dd: ", "date buy/sell", "Enter your choice: ","Enter your choice: "};
    Reader in = new StringReader("9\n4\nAAPL\n2024-02-30\n2024-03-11\n2024-03-21\n7\n11\n");
    MockModel newMockModel = new MockModel(mockName);
    this.controller =  new StockControllerImpl(mockView, in, mockModel);
    controller.execute();
    StringBuilder inputLog = mockModel.getLogger();
    StringBuilder outputLogs = mockView.getPrintedOutput();
    String[] logChecker = inputLog.toString().split("\n");
    String[] outputLogChecker = outputLogs.toString().split("\n");
    assertEquals("Calculating crossover over period for AAPL from 2024-03-11 to 2024-03-21",
            logChecker[logChecker.length - 1]);
    for (int i = 0; i < outputLogChecker.length; i++) {
      assertEquals(expectedOutputLog[i], outputLogChecker[i]);
    }
  }

  @Test
  public void testCrossoverOverPeriodForWrongEndDate() {
    String[] expectedOutputLog = {"Enter your choice: ","Enter your choice: ","Enter the name"
            + " of the share or ticker symbol: ","Enter the start date","The date should be in " +
            "this format yyyy-mm-dd: ", "Enter the end date","The date should be in this" +
            " format " +
            "yyyy-mm-dd: ", "Error", "Enter the end date","The date should be in " +
            "this format yyyy-mm-dd: ","date buy/sell", "Enter your choice: ","Enter your choice: "};
    Reader in = new StringReader("9\n4\nAAPL\n2024-03-11\n2024-15-11\n2024-03-21\n7\n11\n");
    MockModel newMockModel = new MockModel(mockName);
    this.controller =  new StockControllerImpl(mockView, in, mockModel);
    controller.execute();
    StringBuilder inputLog = mockModel.getLogger();
    StringBuilder outputLogs = mockView.getPrintedOutput();
    String[] logChecker = inputLog.toString().split("\n");
    String[] outputLogChecker = outputLogs.toString().split("\n");
    assertEquals("Calculating crossover over period for AAPL from 2024-03-11 to 2024-03-21",
            logChecker[logChecker.length - 1]);
    for (int i = 0; i < outputLogChecker.length; i++) {
      assertEquals(expectedOutputLog[i], outputLogChecker[i]);
    }
  }

  @Test
  public void testMovingCrossover() {
    String[] expectedOutputLog = {"Enter your choice: ","Enter your choice: ","Enter the name"
            + " of the share or ticker symbol: ","Enter the start date","The date should be in " +
            "this format yyyy-mm-dd: ", "Enter the end date","The date should be in this" +
            " format " +
            "yyyy-mm-dd: ", "Enter the end date","The date should be in " +
            "this format yyyy-mm-dd: ","date buy/sell", "Enter your choice: ","Enter your choice: "};
    Reader in = new StringReader("9\n5\nAAPL\n2024-03-11\n2024-03-21\n10\n100\n7\n11\n");
    this.controller =  new StockControllerImpl(mockView, in, mockModel);
    controller.execute();
    StringBuilder inputLog = mockModel.getLogger();
    StringBuilder outputLogs = mockView.getPrintedOutput();
    String[] logChecker = inputLog.toString().split("\n");
    String[] outputLogChecker = outputLogs.toString().split("\n");
    assertEquals("Calculating crossover over period for AAPL from 2024-03-11 to 2024-03-21",
            logChecker[logChecker.length - 1]);
    for (int i = 0; i < outputLogChecker.length; i++) {
      assertEquals(expectedOutputLog[i], outputLogChecker[i]);
    }
  }
}
