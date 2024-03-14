
import java.io.ByteArrayInputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import controller.StockControllerImpl;
import model.PortfolioDir;
import model.PortfolioDirImpl;
import view.IView;
import view.IViewImpl;

public class Main {
    public static void main(String[] args) {

        PortfolioDir model = new PortfolioDirImpl();
        IView view = new IViewImpl(System.out, System.err);
        StockControllerImpl a = new StockControllerImpl(view, new InputStreamReader(System.in),
                model);
        a.go();
    }
}