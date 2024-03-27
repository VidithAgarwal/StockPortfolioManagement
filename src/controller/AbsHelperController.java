package controller;

import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

import model.PortfolioDir;
import view.IView;

/**
 * AbsHelperController class is an abstract class providing helper methods.
 * for controllers dealing with user input and validation.
 * It has all common functionality shared among controller helper classes.
 */
abstract class AbsHelperController {

  /**
   * view component for user interaction. */
  protected final IView view;

  /**
   * model component for managing portfolios. */
  protected final PortfolioDir model;

  /** scanner object for user input. */
  protected final Scanner scan;

  /**
   * constructs AbsHelperController object with specified view, model, and scanner.
   * @param view view component for user interaction.
   * @param model model component for managing portfolios.
   * @param scan scanner object for user input.
   */
  AbsHelperController(IView view, PortfolioDir model, Scanner scan) {
    this.view = view;
    this.model = model;
    this.scan = scan;
  }

  /**
   * this prompts user to enter name for a new portfolio and ensures it is unique.
   * @return validated portfolio name entered by user.
   */
  protected String inputPortfolioName() {
    view.print("Enter the name of the portfolio: ");
    String portfolioName = scan.nextLine();
    if (model.portfolioNameExists(portfolioName)) {
      //System.out.println(portfolioName);
      view.displayError("Portfolio with this name already exists!");
      return inputPortfolioName();
    }
    return portfolioName;
  }

  /**
   * this method validates user input to ensure it is a positive whole number.
   * @param message message to prompt the user for input.
   * @return positive integer input by the user.
   */
  protected int inputPositiveInteger(String message) {
    view.print(message);

    while (!scan.hasNextInt()) {
      view.displayError("Please enter a whole number");
      scan.next();
      view.print(message);
    }
    int input = scan.nextInt();
    scan.nextLine();

    if (isNegative(input)) {
      view.displayError("Enter a positive whole number");
      return inputPositiveInteger(message);
    }
    return input;
  }

  /**
   * this method checks the format of the date.
   * @param date is the date that is passed to the method.
   * @return boolean value true if the format is correct for date entered else false.
   */
  protected boolean isValidDateFormat(String date) {
    String regex = "\\d{4}-\\d{2}-\\d{2}";
    return Pattern.matches(regex, date);
  }

  /**
   * this method prompts user to input a date using view methods and validates format of the date.
   * @return The date in day, month , year array format for further date validation.
   */
  protected int[] inputDate(String message) {
    boolean validDate = false;
    String date;
    int day = 0;
    int month = 0;
    int year = 0;
    do {
      view.print(message);
      view.print("The date should be in this format yyyy-mm-dd: ");
      date = scan.nextLine();

      if (isValidDateFormat(date)) {
        String[] dateParts = date.split("-");
        year = Integer.parseInt(dateParts[0].trim());
        month = Integer.parseInt(dateParts[1].trim());
        day = Integer.parseInt(dateParts[2].trim());

        if (validateDate(day, month, year)) {
          validDate = true;
        } else {
          view.displayError("Invalid date!");
        }
      } else {
        view.displayError("Invalid date format.");
      }
    }
    while (!validDate);
    return new int[]{day, month, year};
  }

  /**
   * this method validates user input to ensure it is a valid choice among available portfolios.
   * @return validated portfolio choice input by user, if wrong choice then displays error message.
   */
  protected int validateUserChoice() {
    int choice = inputPositiveInteger("Enter the Portfolio number you want to select.");
    if (choice >= model.getSize() || choice < 0) {
      this.view.displayError("Enter a valid choice, this option doesn't exists.");
      return validateUserChoice();
    }
    return choice;
  }

  /**
   * this method is used for validation that num of shares entered by user are not negative.
   * @param numShares number of shares entered by the user to be added in portfolio.
   * @return boolean value true if num of shares is less than 0.
   */
  protected boolean isNegative(int numShares) {
    return numShares < 0;
  }

  /**
   * this method is used to validate date, if it is a valid date or not.
   * @param day   is the day of the date entered.
   * @param month is the month of the date entered.
   * @param year  is the year of the date entered.
   * @return true if date is valid or else returns false.
   */
  protected boolean validateDate(int day, int month, int year) {
    if (month < 1 || month > 12) {
      return false;
    }

    if (day < 1 || day > 31) {
      return false;
    }

    if ((month == 4 || month == 6 || month == 9 || month == 11) && day > 30) {
      return false;
    }

    if (month == 2) {
      boolean isLeapYear = (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
      return (!isLeapYear || day <= 29) && (isLeapYear || day <= 28);
    }

    return year >= 0 && year <= 9999;
  }

  /**
   * this method is used to show the list of portfolios to the user using the view.
   * and then asks the user to enter the valid portfolio choice from the list.
   * @return the portfolio number chosen by the user.
   */
  protected int inputPortfolioChoice() {
    view.showListOfPortfolios(model.getListOfPortfoliosName());

    return validateUserChoice();
  }

  /**
   * this method prompts user to input a file path and validates it.
   * used for validation in loading the portfolio.
   * the file is loaded using persistence class in controller.
   * @return The validated file path input by the user.
   */
  protected List<String[]> inputPath() {
    view.print("Enter the full path of the file you want to load data from: ");
    String pathName = scan.nextLine();

    Persistence persistence = new Persistence();
    try {
      return persistence.loadFromCSV(pathName);
    } catch (IllegalArgumentException e) {
      view.displayError(e.getMessage());
      return inputPath();
    }
  }
}
