package model;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Map;
//import org.mockito.Mockito;



import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
public class modelTest {

  private PortfolioDirImpl portfolioDir;

  @Before
  public void setUp() {
    portfolioDir = new PortfolioDirImpl();
  }


  private PortfolioImpl portfolio;


  @Test
  public void testAddPortfolio() {
    portfolioDir.createBuilder("Test Portfolio");
    portfolioDir.addShare("Apple Inc", 10);
    portfolioDir.addPortfolio();
    assertEquals(1, portfolioDir.getSize());
  }

  @Test
  public void testGetListOfPortfoliosName() {
    portfolioDir.createBuilder("Test Portfolio1");
    portfolioDir.addPortfolio();
    portfolioDir.createBuilder("Test Portfolio2");
    portfolioDir.addPortfolio();

    ArrayList<String> listOfPortfolios = portfolioDir.getListOfPortfoliosName();

    assertEquals("Test Portfolio1", listOfPortfolios.get(0));
    assertEquals("Test Portfolio2", listOfPortfolios.get(1));
  }

  //here , in load we also require portfolio name from user but its taken in controller.
  @Test
  public void testLoadPortfolioData() {
    portfolioDir.createBuilder("Test Portfolio");
    String testPath = "/Users/sachi/Desktop/ass4.test.csv";
    portfolioDir.loadPortfolioData(testPath);

  }

  @Test
  public void testSavePortfolio() {
    //Portfolio mockPortfolio = Mockito.mock(Portfolio.class);
    portfolioDir.createBuilder("Test Portfolio");
    portfolioDir.addShare("Apple Inc", 10);
    portfolioDir.addShare("Captivision Inc", 20);
    portfolioDir.addPortfolio();
    //will have to change this path
    String testPath = "/Users/sachi/Desktop/ass4.test.csv";
    portfolioDir.savePortfolio(0, testPath);

    //Mockito.verify(mockPortfolio).savePortfolio(testPath);
  }


  @Test
  public void testPortfolioComposition() {
    portfolioDir.createBuilder("Test Portfolio");
    //SHARENAME
    portfolioDir.addShare("Apple Inc", 10);
    portfolioDir.addShare("Advanced Battery Technologies Inc", 20);
    portfolioDir.addPortfolio();


    Map<String, Integer> composition = portfolioDir.portfolioComposition(0);

    assertEquals(2, composition.size());
    assertEquals(10, (int) composition.get("AAPL"));
    assertEquals(20, (int) composition.get("ABAT"));


    portfolioDir.createBuilder("Test Portfolio2");
    // TICKER SYMBOL
    portfolioDir.addShare("AAON", 10);
    portfolioDir.addShare("ABEO", 20);
    portfolioDir.addShare("CAN", 30);
    portfolioDir.addPortfolio();


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

  @Test
  public void testValueOfPortfolioSingleDate() {
    portfolioDir.createBuilder("Test Portfolio");
    portfolioDir.addShare("Apple Inc", 10);
    portfolioDir.addShare("Canaan Inc", 10);
    portfolioDir.addShare("Can B Corp", 10);
    portfolioDir.addPortfolio();
    assertEquals(1, portfolioDir.getSize());

    assertEquals(1717.749, portfolioDir.portfolioValue(0,"2024-03-05"), 0.001);
  }

  @Test
  public void testValueOfPortfolioManyDate() {
    portfolioDir.createBuilder("Test Portfolio");
    portfolioDir.addShare("Apple Inc", 10);
    portfolioDir.addShare("Canaan Inc", 10);
    portfolioDir.addShare("Can B Corp", 10);
    portfolioDir.addPortfolio();
    assertEquals(1, portfolioDir.getSize());

    assertEquals(1717.749, portfolioDir.portfolioValue(0,"2024-03-05"), 0.001);
    assertEquals(1707.92, portfolioDir.portfolioValue(0,"2024-03-06"), 0.001);
    assertEquals(1724.0249999999999, portfolioDir.portfolioValue(0,"2024-03-08"), 0.001);
  }


  @Test
  public void testValueOfManyPortfolioManyDateHavingSomeSameShare() {
    portfolioDir.createBuilder("college fund");
    portfolioDir.addShare("Apple Inc", 10);
    portfolioDir.addShare("Canaan Inc", 10);
    portfolioDir.addShare("Can B Corp", 10);
    portfolioDir.addPortfolio();
    assertEquals(1, portfolioDir.getSize());

    assertEquals(1717.749, portfolioDir.portfolioValue(0,"2024-03-05"), 0.001);
    assertEquals(1724.0249999999999, portfolioDir.portfolioValue(0,"2024-03-08"), 0.001);

    portfolioDir.createBuilder("oldage fund");
    portfolioDir.addShare("Apple Inc", 10);
    portfolioDir.addShare("Calamp Corp", 10);
    portfolioDir.addShare("Camtek Ltd", 10);
    portfolioDir.addPortfolio();
    assertEquals(2, portfolioDir.getSize());

    assertEquals(2520.5, portfolioDir.portfolioValue(1,"2024-03-05"), 0.001);
    assertEquals(2620.1, portfolioDir.portfolioValue(1,"2024-03-04"), 0.001);
  }


  @Test
  public void testIsEmptyWhenEmpty() {
    assertEquals(true,portfolioDir.isEmpty());
  }

  @Test
  public void testIsEmptyAfterAddingElements() {
    portfolioDir.createBuilder("oldage fund");
    portfolioDir.addPortfolio();
    portfolioDir.createBuilder("college fund");
    portfolioDir.addPortfolio();

    assertEquals(false, portfolioDir.isEmpty());

    ArrayList<String> listOfPortfolios = portfolioDir.getListOfPortfoliosName();

    assertEquals("oldage fund", listOfPortfolios.get(0));
    assertEquals("college fund", listOfPortfolios.get(1));
  }

  @Test
  public void testExistsWhenPortfolioExists() {
    portfolioDir.createBuilder("oldage fund");
    portfolioDir.addPortfolio();

    // Check if the portfolio exists
    assertEquals(true,portfolioDir.exists("oldage fund"));
    assertEquals(false,portfolioDir.exists("college fund"));

  }

  @Test
  public void testWrongAddShare() {
    portfolioDir.createBuilder("Test Portfolio");

    try {
      portfolioDir.addShare("Appple", 10);
      portfolioDir.addPortfolio();
    } catch (IllegalArgumentException e) {
      assertEquals("Share name not found in stocks.csv", e.getMessage());
    }
  }

  @Test
  public void testSaveToWrongFilePath() {
    //Portfolio mockPortfolio = Mockito.mock(Portfolio.class);
    portfolioDir.createBuilder("Test Portfolio");
    portfolioDir.addShare("Apple Inc", 10);
    portfolioDir.addPortfolio();
    String testPath = "/Users/sachi/Desktop/ass4";
    //will have to change this path
    try {
      portfolioDir.savePortfolio(0, testPath);
    } catch (IllegalArgumentException e) {
      assertEquals("Error exporting portfolio to file: " + e.getMessage(), e.getMessage());
    }
  }




}
