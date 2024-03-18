import java.io.InputStreamReader;

import controller.StockControllerImpl;
import model.PortfolioDir;
import model.PortfolioDirImpl;
import view.IView;
import view.IViewImpl;

/**
 * The Main class serves as the entry point for the application.
 * It initializes the model, view, and controller components, and starts the program.
 */
public class Main {

  /**
   * The main method initializes the components and starts the application.
   * @param args The command line arguments .
   */
  public static void main(String[] args) {

    PortfolioDir model = new PortfolioDirImpl();

    IView view = new IViewImpl(System.out, System.err);

    StockControllerImpl controller = new StockControllerImpl(view, new InputStreamReader(System.in),
            model);

    controller.execute();
  }
}
