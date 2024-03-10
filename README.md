# Assignment4

The assignment follows MVC architecture for portfolio creation, management and shares handling for a portfolio.

The main program files created in the assignment are:

Model :

1. PortfolioDir - interface which determines the list of portfolio's created and
is connected to the controller to connect the single portfolio object (model)
in the portfolio list. It has methods which delegate the input from controller
to the main model object that is a single portfolio, where the adding shares in a portfolio,
portfolio creation, getting composition of a portfolio present in portfolio list, getting total value, saving and loading of portfolio takes place.

2. PortfolioImpl - it has methods which are implemented to assign the inputs for creation of a single
portfolio, further adding shares to a portfolio and saving or loading a portfolio. Also. getting the total
value of a specific portfolio in the array list of portfolios stored in this class.

3. Portfolio - interface which acts as the main model and represents a single portfolio object.
The interface has methods that help in creating a single portfolio object, adding shares to portfolio,
seeing the composition of portfolio, loading a portfolio, saving a portfolio, and getting total value of a
portfolio.

4. PortfolioImpl - has the implementation of portfolio interface. It also contains the portfolio builder class
which helps in adding number of shares to the portfolio, i.e., different stocks with different quantity.

5. StockInterface - interface has the methods that return the price of a particular stock on a particular date.

6. StockImpl- it fetches the data for a stock using the api call, stores the data of a stock until the current date
in a CSV file, and also stores the data in a price data object that has date and closing price for the stock.
The data is stored in map object price data for faster fetching of price whenever required for an already called stock data.

7. MockModel- it is the mock class that helps in testing the controller, and implements the PortfolioDir.

Controller :

1. StockController - interface has the ?.

2. StockControllerImpl - has the model, view and scanner object for taking input, showing the desired output through view and performing the
required operations using the model. The go method works based on the choice entered by user and delegates to the view and model methods for
getting the output. The other methods such as create portfolio, load portfolio, save portfolio, get composition, get total value of portfolio
are written to call the respective model method for passing inputs from the controller and calling the view for those methods to show the output.
The controller also has validation methods that are used to validate the user inputs.

View :

1. IView - interface has the methods for showing the output to the user for various methods of model.

2. IViewImpl- is the implementation of the interface IView, which has methods for showing menu to the user,
portfolio composition, showing total value, list of portfolios, displaying error messages and printing the
messages for user interaction with menu.



The program has various features for stock addition and portfolio creation.

1. The program allows the user to create one or more portfolios, and have one or more stocks in the portfolio. The stocks can be added in the portfolio
along with their name/ticker symbol and quantity. The stocks once added in the portfolio cannot be removed from the portfolio or new stocks cannot be added
once the portfolio is created. For creating portfolio the user has to enter portfolio name that is unique and also enter the number of shares that will be added in
portfolio. Then enter the stock name and their quantity to the portfolio and then the portfolio is created.

2. The user can examine the composition of a portfolio by entering the portfolio number from the displayed portfolio list.
The composition gives the stock name along with the quantity of the stock.

3. The total value of a portfolio can be also found for date entered by the user. If the data for that date exists when the data is fetched using api calls,
then price is returned or the data is not found for that date message is printed. The api is called only once for a particular stock during the session and hence,
decreasing the number of api calls.
The price details for a stock is retrieved by calling the alpha vantage api which provides date wise price data. So, when the total value is called the alpha vantage api is used
to fetch the data for the stocks present in the particular user portfolio for which total value is to be found.

4. The user can also save the portfolio by entering which portfolio is to be saved. The portfolio is saved when the user provides a correct path for saving the portfolio.

5. The user can also load the portfolio from their system, this can be done by entering the path of the valid csv file having portfolio details. The user also has to give
name to the portfolio and the valid path of csv file that is to be loaded to create portfolio. The portfolio is then created having the composition of portfolio as the valid file
contents and the portfolio name that is entered by the user.

