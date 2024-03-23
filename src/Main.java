import java.io.InputStreamReader;
import java.time.LocalDate;

import controller.StockControllerImpl;
import controller.StockData;
import model.PortfolioDir;
import model.PortfolioDirImpl;
import model.PortfolioImpl;
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

//      model.createFlexiblePortfolio("Vidith");
//    PortfolioImpl.PortfolioBuilder builder = new PortfolioImpl.PortfolioBuilder("Sachi");
//    builder.addShare("AAPL", 10);
//      model.addPortfolio(builder);
//      view.showListOfPortfolios(model.getListOfPortfoliosName());
//      model.buyStock(0, "AAPL", 20, LocalDate.parse("2023-01-10"), new StockData());
//      model.buyStock(0, "AAPL", 20, LocalDate.parse("2014-01-10"), new StockData());
//    model.sellStock(0, "AAPL", 20, LocalDate.parse("2015-01-14"), new StockData());
//    model.sellStock(0, "AAPL", 20, LocalDate.parse("2024-01-16"), new StockData());
//    view.print("" + model.costBasis(0,LocalDate.parse("2015-10-01"), new StockData()));
//      view.print("" + model.costBasis(0,LocalDate.parse("2024-03-01"), new StockData()));
//      view.showComposition(model.portfolioComposition(0, LocalDate.parse("2015-03-10")));
//      view.showComposition(model.portfolioComposition(1));
//      view.showTotalValue(model.portfolioValue(0, 13, 3, 2023, new StockData()));
//      view.showTotalValue(model.portfolioValue(1, 13, 3, 2023, new StockData()));
    controller.execute();

//    StockStatistic stats = new StockStatisticsImpl();
//    view.print("" + stats.xDayMovingAvg("AAPL", "2024-03-18",4,
//            new StockData().fetchHistoricalData(
//            "AAPL")));

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
