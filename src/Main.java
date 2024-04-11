
import java.io.InputStreamReader;

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
   * It initializes gui controller as default and text based controller on entering -text in args.
   * @param args The command line arguments.
   */
  public static void main(String[] args) {
    // Default to GUI controller
    boolean useGUI = true;

    if (args.length > 0) {
      if (args[0].equals("-text")) {
        useGUI = false;
      } else if (args[0].equals("-gui")) {
        useGUI = true;
      } else {
        System.out.println("Usage: java Main [-gui | -text], \n type -text to use test based "
                + "view or -gui for gui based view");
        //System.exit(1);
      }
    }

    // Create model
    InvestmentManager model = new InvestmentManagerImpl();

    if (useGUI) {
      // GUI controller
      IViewGUI view = new GUIView();
      StockControllerImplGUI controller = new StockControllerImplGUI(view, model);
      controller.setView(view);
    } else {
      // Text-based controller
      IView view = new IViewImpl(System.out, System.err);
      StockControllerImpl controller
              = new StockControllerImpl(view, new InputStreamReader(System.in), model);
      controller.execute();
    }
  }
}
