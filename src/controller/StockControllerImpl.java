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
    System.out.print("Enter the name of the portfolio ");

    String name = in.nextLine();

    boolean found = this.portfolioExist(name);

    if (found) {
      this.view.displayError("Portfolio name already exists!");
      createPortfolio();
    }

    System.out.print("Enter the number of shares ");

    int numShares = in.nextInt();

    try {
      PortfolioImpl.PortfolioBuilder newBuilder = new PortfolioImpl.PortfolioBuilder(name,
              numShares);
      String shareName;
      int quantity;
      for (int i = 0; i < numShares; i++) {
        this.in = new Scanner(System.in);

        System.out.println("Enter the name of the share ");

        shareName = in.nextLine();

        System.out.println("Enter the quantity of the share ");

        quantity = in.nextInt();
        in.nextLine();

        newBuilder.addShare(shareName, quantity);
      }
      this.portfolioDirectory.add(newBuilder.build());
    } catch (IllegalArgumentException e) {
      System.out.println("The portfolio name already exists! Please given another name.");
    }

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
    System.out.println("Enter the proper path with file name in which you would like to save portfolio.");
    //shouldn't this be in view , just the sentence.
    in.nextLine();

    String path = in.nextLine();
    System.out.println(path);
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
    Scanner scanner = new Scanner(System.in);
    int choice = 0;

    while (choice != 3) {
      if (portfolioDirectory.isEmpty()) {
        view.showPrimaryMenu();
      } else {
        view.showSecondaryMenu();
      }
      choice = scanner.nextInt();

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
          if (! portfolioDirectory.isEmpty()) {
            examineComposition();
          }
          break;
        case 5:
          if (! portfolioDirectory.isEmpty()) {
            System.out.println("Get total value of a portfolio for certain date");
          }
          break;
        case 6:
          if (! portfolioDirectory.isEmpty()) {
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
