//import org.junit.Before;
//import org.junit.Test;
//
//import java.io.Reader;
//import java.io.StringReader;
//
//import controller.StockControllerImpl;
////import model.MockModel;
//import view.IView;
//import model.PortfolioDir;
//
//public class controllerTest {
//
//
//  private StockControllerImpl controller;
////  private MockModel mockModel;
//
//  private IView view;
//
//  @Before
//  public void setUp() {
//    controller = new StockControllerImpl(view, System.in, mockModel);
//  }
//
//
//  @Test
//  public void testCreateAddShare()  {
//    StringBuffer out = new StringBuffer();
//    Reader in = new StringReader("college fund");
//    StockControllerImpl controller =  new StockControllerImpl(view, System.in, mockModel);
//    //CalcController controller6 = new Controller6(in, out);
//    StringBuilder log = new StringBuilder(); //log for mock model
//    controller.go();
//    //assertEquals("Input: 3 4\nInput: 8 9\n", log.toString()); //inputs reached the model correctly
//    //assertEquals("1234321\n1234321\n",out.toString()); //output of model transmitted correctly
//  }
//
//
//}