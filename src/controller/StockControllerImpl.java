package controller;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

import model.Portfolio;
import model.PortfolioImpl;
import view.IView;


public class StockControllerImpl implements StockController {

  private ArrayList<Portfolio> portfolioDirectory;
  private IView view;

  private Scanner in;

  public StockControllerImpl(IView view, InputStream in) {
    this.portfolioDirectory = new ArrayList<>();
    this.view = view;
    this.in = new Scanner(in);
  }

  public void createPortfolio() {
    view.print("Enter the name of the portfolio: ");

    String name = in.nextLine();

    boolean found = this.portfolioExist(name);

    if (found) {
      this.view.displayError("Portfolio with this name already exists!");
      createPortfolio();
      return;
    }

    view.print("Enter the number of stocks you want to have in this portfolio: ");

    int numShares = in.nextInt();



    PortfolioImpl.PortfolioBuilder newBuilder = new PortfolioImpl.PortfolioBuilder(name,
            numShares);
    String shareName;
    int quantity;
    for (int i = 0; i < numShares; i++) {
      in.nextLine();
      view.print("Enter the name of the share: ");

      shareName = in.nextLine();

      view.print("Enter the quantity of " + shareName + " you have: ");

      quantity = in.nextInt();

      newBuilder.addShare(shareName, quantity);
    }
    this.portfolioDirectory.add(newBuilder.build());

  }

  private boolean portfolioExist(String name) {
    for (Portfolio obj : portfolioDirectory) {
      if (obj.getName().equals(name)) {
        return true;
      }
    }
    return false;
  }


  public void examineComposition() {
    ArrayList<String> listOfPortfolios = getListOfPortfoliosName();
    view.showListOfPortfolios(listOfPortfolios);

    int input = in.nextInt();
    view.showComposition(portfolioDirectory.get(input).portfolioComposition());
  }

  public void save() {
    ArrayList<String> listOfPortfolioNames = getListOfPortfoliosName();
    view.showListOfPortfolios(listOfPortfolioNames);

    int input = in.nextInt();
    view.print("Enter the proper path with file name in which you would like to save portfolio.");
    //shouldn't this be in view , just the sentence.
    in.nextLine();

    String path = in.nextLine();
//    System.out.println(path);
    portfolioDirectory.get(input).savePortfolio(path);
  }

//  public void load() {
//    System.out.println("Enter the file path you want to load");
//    in.nextLine();
//
//    String path = in.nextLine();
//    System.out.println(path);
//    portfolioDirectory.get(input).savePortfolio(path);
//  }

  private ArrayList<String> getListOfPortfoliosName() {
    ArrayList<String> listOfPortfolios = new ArrayList<>();
    for (Portfolio obj : portfolioDirectory) {
      listOfPortfolios.add(obj.getName());
    }

    return listOfPortfolios;
  }

  public void go() {
    int choice = 0;

    while (choice != 3) {
      if (portfolioDirectory.isEmpty()) {
        view.showPrimaryMenu();
      } else {
        view.showSecondaryMenu();
      }
      choice = in.nextInt();
      in.nextLine();

      switch (choice) {
        case 1:
          createPortfolio();
          break;
        case 2:
          //load();
          break;
        case 3:
          //exit();
          break;
        case 4:
          if (!portfolioDirectory.isEmpty()) {
            examineComposition();
          }
          break;
        case 5:
          if (!portfolioDirectory.isEmpty()) {
            view.print("Get total value of a portfolio for certain date");
          }
          break;
        case 6:
          if (!portfolioDirectory.isEmpty()) {
            save();
          }
          break;
        default:
          this.view.displayError("Enter a valid choice, this option doesn't exists.");
          break;
      }
    }
  }


}
