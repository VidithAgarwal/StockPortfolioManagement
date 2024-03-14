package controller;

import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

import model.PortfolioImpl;
import view.IView;

import static org.junit.Assert.assertEquals;

public class controllerTest {


  private StockControllerImpl controller;
  private MockModel mockModel;

  private MockView mockView;

//  @Before
//  public void setUp() {
//    controller = new StockControllerImpl(mockView, System.in, mockModel);
//  }


  @Test
  public void testGetComposition()  {
    Map<String, Integer> mockComposition = new HashMap<>();
    mockComposition.put("AAPL", 20);
    mockComposition.put("Goog", 10);

    Reader in = new StringReader("3\n0\n6\n");
    this.mockModel = new MockModel(mockComposition);
    PortfolioImpl.PortfolioBuilder newBuilder = new PortfolioImpl.PortfolioBuilder("Test " +
            "Portfolio");
    newBuilder.addShare("AAPL", 20);
    newBuilder.addShare("GOOG", 10);
    mockModel.addPortfolio(newBuilder);
    this.mockView = new MockView();
    this.controller =  new StockControllerImpl(mockView, in, mockModel);
    controller.go();
    StringBuilder inputLog = mockModel.getLogger();
    StringBuilder outputLog = mockView.getPrintedOutput();
    String[] logChecker = inputLog.toString().split("\n");
    String[] outputLogChecker = outputLog.toString().split("\n");
    assertEquals("Retrieving composition for portfolio at index: 0",
            logChecker[logChecker.length - 1]);
    assertEquals("",outputLog.toString());

  }
}
