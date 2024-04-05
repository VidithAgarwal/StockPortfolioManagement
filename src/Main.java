import java.io.InputStreamReader;

import controller.Features;
import controller.StockControllerImpl;

import controller.StockControllerImplGUI;
import model.InvestmentManager;
import model.InvestmentManagerImpl;
import view.GUIView;
import view.IView;
import view.IViewGUI;
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

    InvestmentManager model = new InvestmentManagerImpl();

    IViewGUI view = new GUIView();

    StockControllerImplGUI controller = new StockControllerImplGUI(view,
            model);

    controller.setView(view);


  }

}
