import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import controller.StockControllerImpl;
import controller.StockData;
import model.FlexiblePortfolio;
import model.FlexiblePortfolioImpl;
import model.PortfolioDir;
import model.PortfolioDirImpl;
import model.StockStatistic;
import model.StockStatisticsImpl;
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

//    controller.execute();

    StockStatistic stats = new StockStatisticsImpl();
    view.print("" + stats.xDayMovingAvg("AAPL", "2024-03-18",4,
            new StockData().fetchHistoricalData(
            "AAPL")));

//    FlexiblePortfolio portfolio = new FlexiblePortfolioImpl("Vidith");
//
//    portfolio.buyStock("AAPL", 10, "2015-10-12");
//    view.showComposition(portfolio.portfolioComposition("2015-10-18"));
//    view.showTotalValue(portfolio.portfolioValue("2015-10-14", new StockData()));
//    portfolio.sellStock("AAPL", 1, "2015-10-12");
//    view.showTotalValue(portfolio.portfolioValue("2020-01-15", new StockData()));
//    view.showComposition(portfolio.portfolioComposition("2017-10-18"));
//    portfolio.sellStock("AAPL", 1, "2018-10-14");
//    view.showComposition(portfolio.portfolioComposition("2018-10-18"));
//    portfolio.sellStock("AAPL", 1, "2019-10-14");
//    view.showComposition(portfolio.portfolioComposition("2019-10-18"));
//    portfolio.sellStock("AAPL", 1, "2020-10-14");
//    view.showComposition(portfolio.portfolioComposition("2020-10-18"));
//    portfolio.sellStock("AAPL", 1, "2020-10-14");
//    view.showComposition(portfolio.portfolioComposition("2020-10-18"));
//    portfolio.sellStock("AAPL", 1, "2021-10-14");
//    view.showComposition(portfolio.portfolioComposition("2021-10-18"));
//    portfolio.buyStock("AAPL", 1, "2023-10-18");
//    view.showComposition(portfolio.portfolioComposition("2023-10-18"));
//    portfolio.buyStock("AAPL", 1, "2024-10-18");
//    view.showComposition(portfolio.portfolioComposition("2024-10-18"));
//
//    portfolio.buyStock("AAPL", 10, "2025-10-18");
//    view.showComposition(portfolio.portfolioComposition("2025-10-18"));
//    portfolio.sellStock("AAPL", 4, "2016-10-18");
//    view.showComposition(portfolio.portfolioComposition("2025-10-18"));
////    portfolio.buyStock("AAPL", 10, "2016-10-12");
  }

}
