# Assignment4

The assignment follows MVC architecture for portfolio creation, management and shares handling
for a portfolio.

The main program files created in the assignment are:

Model :


1. PortfolioDir :

Interface :  portfolio dir has the methods for delegating input to a particular portfolio through
the list of portfolios.
Responsibility : stores the data of list of portfolios created in the program,
through the portfolio directory array list.
Description : Interface which determines the list of portfolio's created and
is connected to the controller to connect the single portfolio object (model)
in the portfolio list. It has methods which delegate the input from controller
to the main model object that is a single portfolio, where the adding of new portfolio to portfolio
directory takes place, getting composition of portfolio present in portfolio list, getting portfolio
list, getting total value, which portfolio name exists and size of portfolio directory list.

Methods :
1. addPortfolio() : this method is used to add new portfolio to the portfolio directory and builder
object is used to create the new portfolio.
2. portfolioNameExists () : this method is used to check if the portfolio with a given name already
exists in list of portfolios that is portfolio directory.
3. getListOfPortfoliosName() : the method gives the list of portfolio names that have been already
created.
4. portfolioComposition() :this method takes in the portfolio number as the input and on based this
portfolio is called, for that it calls the portfolioComposition method of Portfolio which loads the
content of a particular portfolio.
5. getSize(): this method is used to get the size of number of portfolios in the array list of
directory.
6. portfolioValue() : this method takes in the input referring to portfolio number whose total
value is to be found and the date for which the total value is to be calculated and calls the
portfolioValue method of the Portfolio for further calculations and getting the price. The date is
taken as day, month and year in integers.
7. isEmpty() : this method checks if the portfolio directory list is empty.

12. deleteSessionCSVFilesFromStocklist() : this method is used to delete the csv files that are created
during a session when the stock data is fetched on the api call. The files are deleted when the
session is over.



2. PortfolioDirImpl :

Responsibility: Implements the methods defined in the PortfolioDir interface.
Description : it has methods which are implemented for adding portfolio to portfolio directory,
getting list of portfolios, composition of a portfolio, and portfolio value.
Also. getting the total value of a specific portfolio in the array list of
portfolios stored in this class.

Methods :
Implementations for all methods defined in PortfolioDir Interface.


3. Portfolio :

Interface : interface which acts as the main model and represents a single portfolio object.
Description :  The interface has methods that help in creating a single portfolio object,
adding shares to portfolio, seeing the composition of portfolio, and getting total value
of a portfolio. The adding of shares in portfolio takes place in its implementation.
Responsibility : it contains the methods for managing a single portfolio.

Methods :
1. portfolioComposition() : this method is used to get the portfolio composition of a portfolio
which contains the stock name and its quantity.
2. portfolioValue() : this method takes in the date for which the portfolio value is to be found
and returns the portfolio value.
3. getName() : this method is used to get the name of the portfolio.


4. PortfolioImpl :

Responsibility : Implements the methods defined in Portfolio interface.
Description : It also contains the portfolio builder class which helps in adding number of shares
to the portfolio, i.e., different stocks with  different quantity.

Methods :
Implementations for all methods defined in Portfolio Interface. Also, contains PortfolioBuilder
which is a static builder class in the portfolio. The builder contains a sharelist having
portfolio data along with the share name and quantity. The addShare method is used to add share
name and its quantity to the sharelist. The load method get the lines that contain the data present
in a file that is read from the controller. It validates the line that it has valid share name
/ ticker symbol and quantity and adds this data to sharelist. The build method
builds the portfolioImpl object with the portfolio name and sharelist which contains the data for
particular shares entered by user in that portfolio. The validate stock name method validates if the
stock name / ticker symbol passed for portfolio creation and while loading portfolio
is correct or not.

5. StockInterface :

Interface : interface has the methods that return the price of a particular stock on a particular
date and has methods used for fetching the stock price through api calls.
Responsibility: has the method for retrieving stock price data, decreasing the api calls by storing
the fetched data from api to csv files for particular stocks whose api has been called, this save
and load of file for fetching data takes place in file handler class of model.

Methods :
1. returnPrice() : this method takes in the date for which the portfolio value is to be found.
It returns the total price of a particular stock of a particular date.


6. StockImpl:

Responsibility : it implements the StockInterface to fetch stock price data using API calls.
Description : it stores the price data of a stock until the current date in a CSV file using
the file handler class methods that is present in model package , and also stores the data in a
price-data object that has date and closing price for the stock.
The data is stored in map object price-data for faster fetching of price whenever required for
an already called stock data. Utilizes Alpha Vantage API for retrieving stock price data.

Methods :
All methods defined in StockInterface are implemented here.


Controller :

1. StockController : interface has the ?.

2. StockControllerImpl : has the model, view and scanner object for taking input,
showing the desired output through view and performing the required operations using the model.
he go method works based on the choice entered by user and delegates to the view and model methods
for getting the output. The other methods such as create portfolio, load portfolio, save portfolio,
get composition, get total value of portfolio, save, examine composition 
are written to call the respective model method for passing inputs from the controller and calling
the view for those methods to show the output, the output from the model is passed to the view by 
the controller for showing it to user. It also has method to delete the session files that are 
created during a session for stock fetched data when api is called. The controller also has
validation methods that are used to validate the user inputs. 

3. Persistence : this class has methods that help in loading and saving the csv files for
 persisting the stock data that is associated with a portfolio , this function is done by 
 exportAsCSV method. The loadFromCSV method loads the user provided CSV file that is to be used to 
 create a portfolio. So, here the file is read and returns the lines of file that contain stock name
 / ticker symbol and its quantity is returned.
 

View :

1. IView :
Interface: interface has the methods for showing the output to the user for various methods of 
model.
Methods : 
1. showPrimaryMenu() : this method displays the primary menu, when there is no portfolio currently 
created.
2. showSecondaryMenu() : this method displays the secondary menu when at least a single portfolio 
exists / or is created.
3. showComposition() : this method displays the composition of a portfolio.
4. showTotalValue() : this method displays the total value of a portfolio.
5. showListOfPortfolios() : this method displays a list of portfolios that have been created.
6. displayError() : this method displays an error message.
7. print() : this method prints a message.

2. IViewImpl :
 Responsibility : IViewImpl is the implementation of the interface IView, which has methods for 
 showing menu to the user, portfolio composition, showing total value, list of portfolios, 
 displaying error messages and printing the messages for user interaction with menu.



The program has various features for stock addition and portfolio creation.

1. The program allows the user to create one or more portfolios, and have one or more stocks in the
 portfolio. The stocks can be added in the portfolio along with their name/ticker symbol and 
 quantity. The stocks once added in the portfolio cannot be removed from the portfolio or new 
 stocks cannot be added once the portfolio is created. For creating portfolio the user has to enter 
 portfolio name that is unique and also enter the number of shares that will be added in portfolio. 
 Then enter the stock name and their quantity to the portfolio and then the portfolio is created.

2. The user can examine the composition of a portfolio by entering the portfolio number from the 
displayed portfolio list. The composition gives the stock name along with the quantity of the stock.

3. The total value of a portfolio can be also found for date entered by the user. If the data for 
that date exists when the data is fetched using api calls, then price is returned or the data is 
not found for that date message is printed. The api is called only once for a particular stock 
during the session and hence, decreasing the number of api calls. The price details for a stock 
is retrieved by calling the alpha vantage api which provides date wise price data. So, when the 
total value is called the alpha vantage api is used to fetch the data for the stocks present in the 
particular user portfolio for which total value is to be found.

4. The user can also save the portfolio by entering which portfolio is to be saved. 
The portfolio is saved when the user provides a correct path for saving the portfolio.

5. The user can also load the portfolio from their system, this can be done by entering the path of 
the valid csv file having portfolio details. The user also has to give name to the portfolio and 
the valid path of csv file that is to be loaded to create portfolio. 
The portfolio is then created having the composition of portfolio as the valid file contents and 
the portfolio name that is entered by the user.

Additional Details:

1. Error Handling: Ensures robust error handling for invalid user inputs and
data retrieval failures.

2. API Efficiency: Optimizes API usage by caching stock price data during a session
to minimize API calls.

3. User Interaction: Provides clear user interfaces for seamless interaction with the program 
through the command line argument interaction.







