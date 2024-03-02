import controller.StockControllerImpl;
import view.IView;
import view.IViewImpl;

public class Main {
  public static void main(String[] args) {

    IView view = new IViewImpl(System.out);
    StockControllerImpl a = new StockControllerImpl(view, System.in);
    a.go();
//    for (int i = 0; i <1 1; i++) {
//
//      a.createPortfolio();
//    }
//    a.examineComposition();
//    a.save();
  }
}
