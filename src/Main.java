import controller.StockControllerImpl;
import view.IView;
import view.IViewImpl;

public class Main {
  public static void main(String[] args) {

    IView view = new IViewImpl(System.out);
    StockControllerImpl a = new StockControllerImpl(view, System.in);
    a.go();
  }
}
