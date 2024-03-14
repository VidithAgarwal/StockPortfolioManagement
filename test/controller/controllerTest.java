package controller;

import org.junit.Before;
import org.junit.Test;

import java.io.Reader;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

import model.PortfolioImpl;

import static org.junit.Assert.assertEquals;

public class controllerTest {


  private StockControllerImpl controller;
  private MockModel mockModel;

  private MockView mockView;

  private Map<String, Integer> mockComposition;

  private String mockName;
  private double mockValue;

  @Before
  public void setUp() {
    mockComposition = new HashMap<>();
    mockComposition.put("AAPL", 20);
    mockComposition.put("Goog", 10);
    mockName = "Test Portfolio";
    mockValue = 17.5;
    this.mockModel = new MockModel(mockComposition, mockName, mockValue);
    PortfolioImpl.PortfolioBuilder newBuilder = new PortfolioImpl.PortfolioBuilder(mockName);
    newBuilder.addShare("AAPL", 20);
    mockModel.addPortfolio(newBuilder);
    this.mockView = new MockView();
  }


  @Test
  public void testGetComposition()  {
    String[] expectedOutputLog = {"Enter your choice: ", "Test Portfolio", "Enter the Portfolio " +
            "number you want to select.", "Goog 10", "AAPL 20", "Enter your choice: "};
    Reader in = new StringReader("3\n0\n6\n");

    this.controller =  new StockControllerImpl(mockView, in, mockModel);
    controller.go();
    StringBuilder inputLog = mockModel.getLogger();
    StringBuilder outputLogs = mockView.getPrintedOutput();
    String[] logChecker = inputLog.toString().split("\n");
    String[] outputLogChecker = outputLogs.toString().split("\n");
    assertEquals("Retrieving composition for portfolio at index: 0",
            logChecker[logChecker.length - 1]);
    for (int i = 0; i < outputLogChecker.length; i++){
      assertEquals(expectedOutputLog[i], outputLogChecker[i]);
    }
  }

  @Test
  public void testGetCompositionWithNegativeChoice()  {
    String[] expectedOutputLog = {"Enter your choice: ", "Test Portfolio", "Enter the " +
            "Portfolio " +
            "number you want to select.", "Error",  "Enter the Portfolio number you want to " +
            "select.", "Goog 10", "AAPL 20", "Enter your choice: "};
    Reader in = new StringReader("3\n-1\n0\n6\n");


    this.controller =  new StockControllerImpl(mockView, in, mockModel);
    controller.go();
    StringBuilder inputLog = mockModel.getLogger();
    StringBuilder outputLogs = mockView.getPrintedOutput();
    String[] logChecker = inputLog.toString().split("\n");
    String[] outputLogChecker = outputLogs.toString().split("\n");
    assertEquals("Retrieving composition for portfolio at index: 0",
            logChecker[logChecker.length - 1]);
    for (int i = 0; i < outputLogChecker.length; i++){
      assertEquals(expectedOutputLog[i], outputLogChecker[i]);
    }
  }

  @Test
  public void testGetCompositionWithInvalidChoice()  {
    String[] expectedOutputLog = {"Enter your choice: ", "Test Portfolio", "Enter the " +
            "Portfolio " +
            "number you want to select.", "Error",  "Enter the Portfolio number you want to " +
            "select.", "Goog 10", "AAPL 20", "Enter your choice: "};
    Reader in = new StringReader("3\n3\n0\n6\n");

    this.controller =  new StockControllerImpl(mockView, in, mockModel);
    controller.go();
    StringBuilder inputLog = mockModel.getLogger();
    StringBuilder outputLogs = mockView.getPrintedOutput();
    String[] logChecker = inputLog.toString().split("\n");
    String[] outputLogChecker = outputLogs.toString().split("\n");
    assertEquals("Retrieving composition for portfolio at index: 0",
            logChecker[logChecker.length - 1]);
    for (int i = 0; i < outputLogChecker.length; i++){
      assertEquals(expectedOutputLog[i], outputLogChecker[i]);
    }
  }

  @Test
  public void testGetCompositionWithChoiceAsString()  {
    String[] expectedOutputLog = {"Enter your choice: ", "Test Portfolio", "Enter the " +
            "Portfolio " +
            "number you want to select.", "Error",  "Enter the Portfolio number you want to " +
            "select.", "Goog 10", "AAPL 20", "Enter your choice: "};
    Reader in = new StringReader("3\nstring\n0\n6\n");

    this.controller =  new StockControllerImpl(mockView, in, mockModel);
    controller.go();
    StringBuilder inputLog = mockModel.getLogger();
    StringBuilder outputLogs = mockView.getPrintedOutput();
    String[] logChecker = inputLog.toString().split("\n");
    String[] outputLogChecker = outputLogs.toString().split("\n");
    assertEquals("Retrieving composition for portfolio at index: 0",
            logChecker[logChecker.length - 1]);
    for (int i = 0; i < outputLogChecker.length; i++){
      assertEquals(expectedOutputLog[i], outputLogChecker[i]);
    }
  }

  @Test
  public void testGetCompositionWithChoiceAsDecimal()  {
    String[] expectedOutputLog = {"Enter your choice: ", "Test Portfolio", "Enter the " +
            "Portfolio " +
            "number you want to select.", "Error",  "Enter the Portfolio number you want to " +
            "select.", "Goog 10", "AAPL 20", "Enter your choice: "};
    Reader in = new StringReader("3\n3.5\n0\n6\n");

    this.controller =  new StockControllerImpl(mockView, in, mockModel);
    controller.go();
    StringBuilder inputLog = mockModel.getLogger();
    StringBuilder outputLogs = mockView.getPrintedOutput();
    String[] logChecker = inputLog.toString().split("\n");
    String[] outputLogChecker = outputLogs.toString().split("\n");
    assertEquals("Retrieving composition for portfolio at index: 0",
            logChecker[logChecker.length - 1]);
    for (int i = 0; i < outputLogChecker.length; i++){
      assertEquals(expectedOutputLog[i], outputLogChecker[i]);
    }
  }

  @Test
  public void testCreatePortfolio()  {
    String[] expectedOutputLog = {"Enter your choice: ", "Enter the name of the portfolio: ",
            "Enter the number of stocks you want to have in this portfolio: ",
            "Enter the name of the share or ticker symbol: ", "Enter the quantity of AAPL you "
            + "have: ", "Enter your choice: "};
    Reader in = new StringReader("1\nTest\n1\nAAPL\n10\n6\n");

    MockModel newMockModel = new MockModel(mockName);
    this.controller =  new StockControllerImpl(mockView, in, newMockModel);
    controller.go();
    StringBuilder inputLog = newMockModel.getLogger();
    StringBuilder outputLogs = mockView.getPrintedOutput();
    String[] logChecker = inputLog.toString().split("\n");
    String[] outputLogChecker = outputLogs.toString().split("\n");
    assertEquals("Test",
            logChecker[logChecker.length - 1]);
    for (int i = 0; i < outputLogChecker.length; i++){
      assertEquals(expectedOutputLog[i], outputLogChecker[i]);
    }
  }

  @Test
  public void testCreatePortfolioWithInvalidShareName()  {
    String[] expectedOutputLog = {"Enter your choice: ", "Enter the name of the portfolio: ",
            "Enter the number of stocks you want to have in this portfolio: ",
            "Enter the name of the share or ticker symbol: ", "Enter the quantity of AAPLL you "
            + "have: ", "Error", "Enter the name of the share or ticker symbol: ", "Enter the quantity of AAPL you "
            + "have: ", "Enter your " +
            "choice: "};
    Reader in = new StringReader("1\nTest\n1\nAAPLL\n10\nAAPL\n10\n6\n");

    MockModel newMockModel = new MockModel(mockName);
    this.controller =  new StockControllerImpl(mockView, in, newMockModel);
    controller.go();
    StringBuilder inputLog = newMockModel.getLogger();
    StringBuilder outputLogs = mockView.getPrintedOutput();
    String[] logChecker = inputLog.toString().split("\n");
    String[] outputLogChecker = outputLogs.toString().split("\n");
    assertEquals("Test",
            logChecker[logChecker.length - 1]);
    for (int i = 0; i < outputLogChecker.length; i++){
      assertEquals(expectedOutputLog[i], outputLogChecker[i]);
    }
  }

  @Test
  public void testCreatePortfolioWithNegativeTotalNumberOfStocks()  {
    String[] expectedOutputLog = {"Enter your choice: ", "Enter the name of the portfolio: ",
            "Enter the number of stocks you want to have in this portfolio: ", "Error", "Enter " +
            "the number of stocks you want to have in this portfolio: ",
            "Enter the name of the share or ticker symbol: ", "Enter the quantity of AAPLL you "
            + "have: ", "Error", "Enter the name of the share or ticker symbol: ", "Enter the quantity of AAPL you "
            + "have: ", "Enter your " +
            "choice: "};
    Reader in = new StringReader("1\nTest\n-1\n1\nAAPLL\n10\nAAPL\n10\n6\n");

    MockModel newMockModel = new MockModel(mockName);
    this.controller =  new StockControllerImpl(mockView, in, newMockModel);
    controller.go();
    StringBuilder inputLog = newMockModel.getLogger();
    StringBuilder outputLogs = mockView.getPrintedOutput();
    String[] logChecker = inputLog.toString().split("\n");
    String[] outputLogChecker = outputLogs.toString().split("\n");
    assertEquals("Test",
            logChecker[logChecker.length - 1]);
    for (int i = 0; i < outputLogChecker.length; i++){
      assertEquals(expectedOutputLog[i], outputLogChecker[i]);
    }
  }

  @Test
  public void testCreatePortfolioWithDecimalTotalNumberOfStocks()  {
    String[] expectedOutputLog = {"Enter your choice: ", "Enter the name of the portfolio: ",
            "Enter the number of stocks you want to have in this portfolio: ", "Error", "Enter " +
            "the number of stocks you want to have in this portfolio: ",
            "Enter the name of the share or ticker symbol: ", "Enter the quantity of AAPLL you "
            + "have: ", "Error", "Enter the name of the share or ticker symbol: ", "Enter the quantity of AAPL you "
            + "have: ", "Enter your " +
            "choice: "};
    Reader in = new StringReader("1\nTest\n1.5\n1\nAAPLL\n10\nAAPL\n10\n6\n");

    MockModel newMockModel = new MockModel(mockName);
    this.controller =  new StockControllerImpl(mockView, in, newMockModel);
    controller.go();
    StringBuilder inputLog = newMockModel.getLogger();
    StringBuilder outputLogs = mockView.getPrintedOutput();
    String[] logChecker = inputLog.toString().split("\n");
    String[] outputLogChecker = outputLogs.toString().split("\n");
    assertEquals("Test",
            logChecker[logChecker.length - 1]);
    for (int i = 0; i < outputLogChecker.length; i++){
      assertEquals(expectedOutputLog[i], outputLogChecker[i]);
    }
  }

  @Test
  public void testCreatePortfolioWithStringTotalNumberOfStocks()  {
    String[] expectedOutputLog = {"Enter your choice: ", "Enter the name of the portfolio: ",
            "Enter the number of stocks you want to have in this portfolio: ", "Error", "Enter " +
            "the number of stocks you want to have in this portfolio: ",
            "Enter the name of the share or ticker symbol: ", "Enter the quantity of AAPLL you "
            + "have: ", "Error", "Enter the name of the share or ticker symbol: ", "Enter the quantity of AAPL you "
            + "have: ", "Enter your " +
            "choice: "};
    Reader in = new StringReader("1\nTest\nhell\n1\nAAPLL\n10\nAAPL\n10\n6\n");

    MockModel newMockModel = new MockModel(mockName);
    this.controller =  new StockControllerImpl(mockView, in, newMockModel);
    controller.go();
    StringBuilder inputLog = newMockModel.getLogger();
    StringBuilder outputLogs = mockView.getPrintedOutput();
    String[] logChecker = inputLog.toString().split("\n");
    String[] outputLogChecker = outputLogs.toString().split("\n");
    assertEquals("Test",
            logChecker[logChecker.length - 1]);
    for (int i = 0; i < outputLogChecker.length; i++){
      assertEquals(expectedOutputLog[i], outputLogChecker[i]);
    }
  }

  @Test
  public void testCreatePortfolioWithNegativeQuantityOfShares()  {
    String[] expectedOutputLog = {"Enter your choice: ", "Enter the name of the portfolio: ",
            "Enter the number of stocks you want to have in this portfolio: ",
            "Enter the name of the share or ticker symbol: ", "Enter the quantity of AAPL you "
            + "have: ", "Error", "Enter the quantity of AAPL you "
            + "have: ",  "Enter your choice: "};
    Reader in = new StringReader("1\nTest\n1\nAAPL\n-10\n10\n6\n");

    MockModel newMockModel = new MockModel(mockName);
    this.controller =  new StockControllerImpl(mockView, in, newMockModel);
    controller.go();
    StringBuilder inputLog = newMockModel.getLogger();
    StringBuilder outputLogs = mockView.getPrintedOutput();
    String[] logChecker = inputLog.toString().split("\n");
    String[] outputLogChecker = outputLogs.toString().split("\n");
    assertEquals("Test",
            logChecker[logChecker.length - 1]);
    for (int i = 0; i < outputLogChecker.length; i++){
      assertEquals(expectedOutputLog[i], outputLogChecker[i]);
    }
  }

  @Test
  public void testCreatePortfolioWithFractionalQuantityOfShares()  {
    String[] expectedOutputLog = {"Enter your choice: ", "Enter the name of the portfolio: ",
            "Enter the number of stocks you want to have in this portfolio: ",
            "Enter the name of the share or ticker symbol: ", "Enter the quantity of AAPL you "
            + "have: ", "Error", "Enter the quantity of AAPL you "
            + "have: ",  "Enter your choice: "};
    Reader in = new StringReader("1\nTest\n1\nAAPL\n10.5\n10\n6\n");

    MockModel newMockModel = new MockModel(mockName);
    this.controller =  new StockControllerImpl(mockView, in, newMockModel);
    controller.go();
    StringBuilder inputLog = newMockModel.getLogger();
    StringBuilder outputLogs = mockView.getPrintedOutput();
    String[] logChecker = inputLog.toString().split("\n");
    String[] outputLogChecker = outputLogs.toString().split("\n");
    assertEquals("Test",
            logChecker[logChecker.length - 1]);
    for (int i = 0; i < outputLogChecker.length; i++){
      assertEquals(expectedOutputLog[i], outputLogChecker[i]);
    }
  }

  @Test
  public void testCreatePortfolioWithStringQuantityOfShares()  {
    String[] expectedOutputLog = {"Enter your choice: ", "Enter the name of the portfolio: ",
            "Enter the number of stocks you want to have in this portfolio: ",
            "Enter the name of the share or ticker symbol: ", "Enter the quantity of AAPL you "
            + "have: ", "Error", "Enter the quantity of AAPL you "
            + "have: ",  "Enter your choice: "};
    Reader in = new StringReader("1\nTest\n1\nAAPL\nten\n10\n6\n");

    MockModel newMockModel = new MockModel(mockName);
    this.controller =  new StockControllerImpl(mockView, in, newMockModel);
    controller.go();
    StringBuilder inputLog = newMockModel.getLogger();
    StringBuilder outputLogs = mockView.getPrintedOutput();
    String[] logChecker = inputLog.toString().split("\n");
    String[] outputLogChecker = outputLogs.toString().split("\n");
    assertEquals("Test",
            logChecker[logChecker.length - 1]);
    for (int i = 0; i < outputLogChecker.length; i++){
      assertEquals(expectedOutputLog[i], outputLogChecker[i]);
    }
  }

  @Test
  public void testCreatePortfolioWithTwoStocks()  {
    String[] expectedOutputLog = {"Enter your choice: ", "Enter the name of the portfolio: ",
            "Enter the number of stocks you want to have in this portfolio: ",
            "Enter the name of the share or ticker symbol: ", "Enter the quantity of AAPL you "
            + "have: ", "Enter the name of the share or ticker symbol: ", "Enter the " +
            "quantity of GOOG you have: ",  "Enter your choice: "};
    Reader in = new StringReader("1\nTest\n2\nAAPL\n10\nGOOG\n20\n6\n");

    MockModel newMockModel = new MockModel(mockName);
    this.controller =  new StockControllerImpl(mockView, in, newMockModel);
    controller.go();
    StringBuilder inputLog = newMockModel.getLogger();
    StringBuilder outputLogs = mockView.getPrintedOutput();
    String[] logChecker = inputLog.toString().split("\n");
    String[] outputLogChecker = outputLogs.toString().split("\n");
    assertEquals("Test",
            logChecker[logChecker.length - 1]);
    for (int i = 0; i < outputLogChecker.length; i++){
      assertEquals(expectedOutputLog[i], outputLogChecker[i]);
    }
  }

  @Test
  public void testTotalValue()  {
    String[] expectedOutputLog = {"Enter your choice: ", "Get total value of a portfolio for certain date",
            "Test Portfolio", "Enter the Portfolio number you want to select.",
            "Enter the date for which you want to get the total price of the portfolio. ", "The "
            + "date should be in this format yyyy-mm-dd: ", "Wait until the total value is " +
            "calculated", mockValue + "", "Enter your choice: "};
    Reader in = new StringReader("4\n0\n2024-03-05\n6\n");

    this.controller =  new StockControllerImpl(mockView, in, mockModel);
    controller.go();
    StringBuilder inputLog = mockModel.getLogger();
    StringBuilder outputLogs = mockView.getPrintedOutput();
    String[] logChecker = inputLog.toString().split("\n");
    String[] outputLogChecker = outputLogs.toString().split("\n");
    assertEquals("Retrieving composition for portfolio at index: 0 For the day: 5 month: 3 year: " +
                    "2024",
            logChecker[logChecker.length - 1]);
    for (int i = 0; i < outputLogChecker.length; i++){
      assertEquals(expectedOutputLog[i], outputLogChecker[i]);
    }
  }

  @Test
  public void testTotalValueForInvalidPortfolioChoice()  {
    String[] expectedOutputLog = {"Enter your choice: ", "Get total value of a portfolio for certain date",
            "Test Portfolio", "Enter the Portfolio number you want to select.", "Error",
            "Enter the Portfolio number you want to select.",
            "Enter the date for which you want to get the total price of the portfolio. ", "The "
            + "date should be in this format yyyy-mm-dd: ", "Wait until the total value is " +
            "calculated", mockValue + "", "Enter your choice: "};
    Reader in = new StringReader("4\n3\n0\n2024-03-05\n6\n");

    this.controller =  new StockControllerImpl(mockView, in, mockModel);
    controller.go();
    StringBuilder inputLog = mockModel.getLogger();
    StringBuilder outputLogs = mockView.getPrintedOutput();
    String[] logChecker = inputLog.toString().split("\n");
    String[] outputLogChecker = outputLogs.toString().split("\n");
    assertEquals("Retrieving composition for portfolio at index: 0 For the day: 5 month: 3 year: " +
                    "2024",
            logChecker[logChecker.length - 1]);
    for (int i = 0; i < outputLogChecker.length; i++){
      assertEquals(expectedOutputLog[i], outputLogChecker[i]);
    }
  }

  @Test
  public void testTotalValueForNegativePortfolioChoice()  {
    String[] expectedOutputLog = {"Enter your choice: ", "Get total value of a portfolio for certain date",
            "Test Portfolio", "Enter the Portfolio number you want to select.", "Error",
            "Enter the Portfolio number you want to select.",
            "Enter the date for which you want to get the total price of the portfolio. ", "The "
            + "date should be in this format yyyy-mm-dd: ", "Wait until the total value is " +
            "calculated", mockValue + "", "Enter your choice: "};
    Reader in = new StringReader("4\n-3\n0\n2024-03-05\n6\n");

    this.controller =  new StockControllerImpl(mockView, in, mockModel);
    controller.go();
    StringBuilder inputLog = mockModel.getLogger();
    StringBuilder outputLogs = mockView.getPrintedOutput();
    String[] logChecker = inputLog.toString().split("\n");
    String[] outputLogChecker = outputLogs.toString().split("\n");
    assertEquals("Retrieving composition for portfolio at index: 0 For the day: 5 month: 3 year: " +
                    "2024",
            logChecker[logChecker.length - 1]);
    for (int i = 0; i < outputLogChecker.length; i++){
      assertEquals(expectedOutputLog[i], outputLogChecker[i]);
    }
  }

  @Test
  public void testTotalValueForStringPortfolioChoice()  {
    String[] expectedOutputLog = {"Enter your choice: ", "Get total value of a portfolio for certain date",
            "Test Portfolio", "Enter the Portfolio number you want to select.", "Error",
            "Enter the Portfolio number you want to select.",
            "Enter the date for which you want to get the total price of the portfolio. ", "The "
            + "date should be in this format yyyy-mm-dd: ", "Wait until the total value is " +
            "calculated", mockValue + "", "Enter your choice: "};
    Reader in = new StringReader("4\nzero\n0\n2024-03-05\n6\n");

    this.controller =  new StockControllerImpl(mockView, in, mockModel);
    controller.go();
    StringBuilder inputLog = mockModel.getLogger();
    StringBuilder outputLogs = mockView.getPrintedOutput();
    String[] logChecker = inputLog.toString().split("\n");
    String[] outputLogChecker = outputLogs.toString().split("\n");
    assertEquals("Retrieving composition for portfolio at index: 0 For the day: 5 month: 3 year: " +
                    "2024",
            logChecker[logChecker.length - 1]);
    for (int i = 0; i < outputLogChecker.length; i++){
      assertEquals(expectedOutputLog[i], outputLogChecker[i]);
    }
  }

  @Test
  public void testTotalValueForInvalidDateFormat()  {
    String[] expectedOutputLog = {"Enter your choice: ", "Get total value of a portfolio for certain date",
            "Test Portfolio", "Enter the Portfolio number you want to select.",
            "Enter the date for which you want to get the total price of the portfolio. ", "The "
            + "date should be in this format yyyy-mm-dd: ", "Error",  "Enter the date for which you want to get the total price of the portfolio. ", "The "
            + "date should be in this format yyyy-mm-dd: ", "Wait until the total value" +
            " is " +
            "calculated", mockValue + "", "Enter your choice: "};
    Reader in = new StringReader("4\n0\n05-03-2024\n2024-03-05\n6\n");

    this.controller =  new StockControllerImpl(mockView, in, mockModel);
    controller.go();
    StringBuilder inputLog = mockModel.getLogger();
    StringBuilder outputLogs = mockView.getPrintedOutput();
    String[] logChecker = inputLog.toString().split("\n");
    String[] outputLogChecker = outputLogs.toString().split("\n");
    assertEquals("Retrieving composition for portfolio at index: 0 For the day: 5 month: 3 year: " +
                    "2024",
            logChecker[logChecker.length - 1]);
    for (int i = 0; i < outputLogChecker.length; i++){
      assertEquals(expectedOutputLog[i], outputLogChecker[i]);
    }
  }

  @Test
  public void testTotalValueForInvalidDay()  {
    String[] expectedOutputLog = {"Enter your choice: ", "Get total value of a portfolio for certain date",
            "Test Portfolio", "Enter the Portfolio number you want to select.",
            "Enter the date for which you want to get the total price of the portfolio. ", "The "
            + "date should be in this format yyyy-mm-dd: ", "Error",  "Enter the date for which you want to get the total price of the portfolio. ", "The "
            + "date should be in this format yyyy-mm-dd: ", "Wait until the total value" +
            " is " +
            "calculated", mockValue + "", "Enter your choice: "};
    Reader in = new StringReader("4\n0\n2024-03-35\n2024-02-25\n6\n");

    this.controller =  new StockControllerImpl(mockView, in, mockModel);
    controller.go();
    StringBuilder inputLog = mockModel.getLogger();
    StringBuilder outputLogs = mockView.getPrintedOutput();
    String[] logChecker = inputLog.toString().split("\n");
    String[] outputLogChecker = outputLogs.toString().split("\n");
    assertEquals("Retrieving composition for portfolio at index: 0 For the day: 25 month: 2 year:" +
                    " " +
                    "2024",
            logChecker[logChecker.length - 1]);
    for (int i = 0; i < outputLogChecker.length; i++){
      assertEquals(expectedOutputLog[i], outputLogChecker[i]);
    }
  }

  @Test
  public void testTotalValueForInvalidMonth()  {
    String[] expectedOutputLog = {"Enter your choice: ", "Get total value of a portfolio for certain date",
            "Test Portfolio", "Enter the Portfolio number you want to select.",
            "Enter the date for which you want to get the total price of the portfolio. ", "The "
            + "date should be in this format yyyy-mm-dd: ", "Error",  "Enter the date for which you want to get the total price of the portfolio. ", "The "
            + "date should be in this format yyyy-mm-dd: ", "Wait until the total value" +
            " is " +
            "calculated", mockValue + "", "Enter your choice: "};
    Reader in = new StringReader("4\n0\n2024-13-35\n2024-02-25\n6\n");

    this.controller =  new StockControllerImpl(mockView, in, mockModel);
    controller.go();
    StringBuilder inputLog = mockModel.getLogger();
    StringBuilder outputLogs = mockView.getPrintedOutput();
    String[] logChecker = inputLog.toString().split("\n");
    String[] outputLogChecker = outputLogs.toString().split("\n");
    assertEquals("Retrieving composition for portfolio at index: 0 For the day: 25 month: 2 year:" +
                    " " +
                    "2024",
            logChecker[logChecker.length - 1]);
    for (int i = 0; i < outputLogChecker.length; i++){
      assertEquals(expectedOutputLog[i], outputLogChecker[i]);
    }
  }

  @Test
  public void testTotalValueForInvalidMonthDayCombination()  {
    String[] expectedOutputLog = {"Enter your choice: ", "Get total value of a portfolio for certain date",
            "Test Portfolio", "Enter the Portfolio number you want to select.",
            "Enter the date for which you want to get the total price of the portfolio. ", "The "
            + "date should be in this format yyyy-mm-dd: ", "Error",  "Enter the date for which you want to get the total price of the portfolio. ", "The "
            + "date should be in this format yyyy-mm-dd: ", "Wait until the total value" +
            " is " +
            "calculated", mockValue + "", "Enter your choice: "};
    Reader in = new StringReader("4\n0\n2024-11-31\n2024-02-25\n6\n");

    this.controller =  new StockControllerImpl(mockView, in, mockModel);
    controller.go();
    StringBuilder inputLog = mockModel.getLogger();
    StringBuilder outputLogs = mockView.getPrintedOutput();
    String[] logChecker = inputLog.toString().split("\n");
    String[] outputLogChecker = outputLogs.toString().split("\n");
    assertEquals("Retrieving composition for portfolio at index: 0 For the day: 25 month: 2 year:" +
                    " " +
                    "2024",
            logChecker[logChecker.length - 1]);
    for (int i = 0; i < outputLogChecker.length; i++){
      assertEquals(expectedOutputLog[i], outputLogChecker[i]);
    }
  }

  @Test
  public void testTotalValueForZeroDay()  {
    String[] expectedOutputLog = {"Enter your choice: ", "Get total value of a portfolio for certain date",
            "Test Portfolio", "Enter the Portfolio number you want to select.",
            "Enter the date for which you want to get the total price of the portfolio. ", "The "
            + "date should be in this format yyyy-mm-dd: ", "Error",  "Enter the date for which you want to get the total price of the portfolio. ", "The "
            + "date should be in this format yyyy-mm-dd: ", "Wait until the total value" +
            " is " +
            "calculated", mockValue + "", "Enter your choice: "};
    Reader in = new StringReader("4\n0\n2024-11-0\n2024-02-25\n6\n");

    this.controller =  new StockControllerImpl(mockView, in, mockModel);
    controller.go();
    StringBuilder inputLog = mockModel.getLogger();
    StringBuilder outputLogs = mockView.getPrintedOutput();
    String[] logChecker = inputLog.toString().split("\n");
    String[] outputLogChecker = outputLogs.toString().split("\n");
    assertEquals("Retrieving composition for portfolio at index: 0 For the day: 25 month: 2 year:" +
                    " " +
                    "2024",
            logChecker[logChecker.length - 1]);
    for (int i = 0; i < outputLogChecker.length; i++){
      assertEquals(expectedOutputLog[i], outputLogChecker[i]);
    }
  }

  @Test
  public void testTotalValueForZeroMonth()  {
    String[] expectedOutputLog = {"Enter your choice: ", "Get total value of a portfolio for certain date",
            "Test Portfolio", "Enter the Portfolio number you want to select.",
            "Enter the date for which you want to get the total price of the portfolio. ", "The "
            + "date should be in this format yyyy-mm-dd: ", "Error",  "Enter the date for which you want to get the total price of the portfolio. ", "The "
            + "date should be in this format yyyy-mm-dd: ", "Wait until the total value" +
            " is " +
            "calculated", mockValue + "", "Enter your choice: "};
    Reader in = new StringReader("4\n0\n2024-0-11\n2024-02-25\n6\n");

    this.controller =  new StockControllerImpl(mockView, in, mockModel);
    controller.go();
    StringBuilder inputLog = mockModel.getLogger();
    StringBuilder outputLogs = mockView.getPrintedOutput();
    String[] logChecker = inputLog.toString().split("\n");
    String[] outputLogChecker = outputLogs.toString().split("\n");
    assertEquals("Retrieving composition for portfolio at index: 0 For the day: 25 month: 2 year:" +
                    " " +
                    "2024",
            logChecker[logChecker.length - 1]);
    for (int i = 0; i < outputLogChecker.length; i++){
      assertEquals(expectedOutputLog[i], outputLogChecker[i]);
    }
  }

  @Test
  public void testTotalValueForNegativeValues()  {
    String[] expectedOutputLog = {"Enter your choice: ", "Get total value of a portfolio for certain date",
            "Test Portfolio", "Enter the Portfolio number you want to select.",
            "Enter the date for which you want to get the total price of the portfolio. ", "The "
            + "date should be in this format yyyy-mm-dd: ", "Error",  "Enter the date for which you want to get the total price of the portfolio. ", "The "
            + "date should be in this format yyyy-mm-dd: ", "Wait until the total value" +
            " is " +
            "calculated", mockValue + "", "Enter your choice: "};
    Reader in = new StringReader("4\n0\n-2024-11-11\n2024-02-25\n6\n");

    this.controller =  new StockControllerImpl(mockView, in, mockModel);
    controller.go();
    StringBuilder inputLog = mockModel.getLogger();
    StringBuilder outputLogs = mockView.getPrintedOutput();
    String[] logChecker = inputLog.toString().split("\n");
    String[] outputLogChecker = outputLogs.toString().split("\n");
    assertEquals("Retrieving composition for portfolio at index: 0 For the day: 25 month: 2 year:" +
                    " " +
                    "2024",
            logChecker[logChecker.length - 1]);
    for (int i = 0; i < outputLogChecker.length; i++){
      assertEquals(expectedOutputLog[i], outputLogChecker[i]);
    }
  }

  @Test
  public void testSavePortfolio()  {
    String path = System.getProperty("user.dir") + "/testFiles/output1.csv";
    String[] expectedOutputLog = {"Enter your choice: ",
            "Test Portfolio", "Enter the Portfolio number you want to select.",
            "Enter the proper path with file name in which you would like to save portfolio.",
            "Portfolio exported to " + path + " successfully.",
            "Enter your choice: "};
    Reader in = new StringReader("5\n1\n" + path + "\n6\n");
    PortfolioImpl.PortfolioBuilder newBuilder = new PortfolioImpl.PortfolioBuilder("Vidith");
    newBuilder.addShare("AAPL", 20);
    mockModel.addPortfolio(newBuilder);

    this.controller =  new StockControllerImpl(mockView, in, mockModel);
    controller.go();
    StringBuilder outputLogs = mockView.getPrintedOutput();
    String[] outputLogChecker = outputLogs.toString().split("\n");
    for (int i = 0; i < outputLogChecker.length; i++){
      assertEquals(expectedOutputLog[i], outputLogChecker[i]);
    }
  }

  @Test
  public void testSavePortfolioForInvalidPortfolioChoice()  {
    String path = System.getProperty("user.dir") + "/testFiles/output1.csv";
    String[] expectedOutputLog = {"Enter your choice: ",
            "Test Portfolio", "Enter the Portfolio number you want to select.", "Error", "Enter " +
            "the Portfolio number you want to select.",
            "Enter the proper path with file name in which you would like to save portfolio.",
            "Portfolio exported to " + path + " successfully.",
            "Enter your choice: "};

    Reader in = new StringReader("5\n3\n0\n" + path + "\n6\n");

    this.controller =  new StockControllerImpl(mockView, in, mockModel);
    controller.go();
    StringBuilder outputLogs = mockView.getPrintedOutput();
    String[] outputLogChecker = outputLogs.toString().split("\n");
    for (int i = 0; i < outputLogChecker.length; i++){
      assertEquals(expectedOutputLog[i], outputLogChecker[i]);
    }
  }

  @Test
  public void testSavePortfolioForNegativePortfolioChoice()  {
    String path = System.getProperty("user.dir") + "/testFiles/output1.csv";
    String[] expectedOutputLog = {"Enter your choice: ",
            "Test Portfolio", "Enter the Portfolio number you want to select.", "Error", "Enter " +
            "the Portfolio number you want to select.",
            "Enter the proper path with file name in which you would like to save portfolio.",
            "Portfolio exported to " + path + " successfully.",
            "Enter your choice: "};
    Reader in = new StringReader("5\n-3\n0\n" + path + "\n6\n");

    this.controller =  new StockControllerImpl(mockView, in, mockModel);
    controller.go();
    StringBuilder outputLogs = mockView.getPrintedOutput();
    String[] outputLogChecker = outputLogs.toString().split("\n");
    for (int i = 0; i < outputLogChecker.length; i++){
      assertEquals(expectedOutputLog[i], outputLogChecker[i]);
    }
  }

  @Test
  public void testSavePortfolioForInvalidFileType()  {
    String path = System.getProperty("user.dir") + "/testFiles/output1.txt";
    String[] expectedOutputLog = {"Enter your choice: ",
            "Test Portfolio", "Enter the Portfolio number you want to select.", "Error", "Enter " +
            "the Portfolio number you want to select.",
            "Enter the proper path with file name in which you would like to save portfolio.",
            "Error",
            "Enter your choice: "};
    Reader in = new StringReader("5\n-3\n0\n" + path + "\n6\n");

    this.controller =  new StockControllerImpl(mockView, in, mockModel);
    controller.go();
    StringBuilder outputLogs = mockView.getPrintedOutput();
    String[] outputLogChecker = outputLogs.toString().split("\n");
    for (int i = 0; i < outputLogChecker.length; i++){
      assertEquals(expectedOutputLog[i], outputLogChecker[i]);
    }
  }

}


