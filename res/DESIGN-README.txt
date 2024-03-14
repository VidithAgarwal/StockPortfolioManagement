ASSIGNMENT - 4 

The design is implemented as follows:

My design follows the MVC pattern in which the work of the controller is of taking the input from
the user and passing the input to the model for processing and, the controller send the processed
data received from the model to the view to show that processed data to the user.

Here my model is the PortfolioDir interface and my model represents list of portfolio of the current
user. My model contains a list of Portfolio objects and methods which delegates the operation on a
particular portfolio to the Portfolio class. My portfolio class contains name and a hashmap
sharesList which maps the Stock object with the quantity of that particular stock object. The stock
object contains the name of the stock(ticker symbol) and a hashmap priceData in which it contains the
date and the price for that date.(Historical data).

Here my controller is the StockController which takes the input from the user based on the menu
showed by the view and calls the method of the model which is assigned to do that particular work.
And it also sends the message or output received from the model to the view to display that output
to the user.

Here my view is the Iview Interface which takes the message or data from the controller and displays
the user that information. The view initially shows the menu to the user and any message the
controller sends.

Upon starting the program the controller asks the view using the showMenu method to show the main
menu to the user. The controller's go method takes the choice as input from the user. The user can
choose to create a portfolio which uses a builder to create a single portfolio. The controller take
the name of the portfolio from the user and creates a builder object. Now when user enter the share
name and share quantity it validates the share name in the builder and adds it to a hashmap inside
the builder. Now when the user has entered all the values the method addPortfolio is called which
builds a portfolio from the builder and adds it to the list of portfolios contained in my model
(PortfolioDirImpl).

If the user wants to load the portfolio, controller takes this input from the user and calls the
loadPortfolio method in controller itself which takes in the portfolio name from the user and
validates that the portfolio doesn't exist from before. It then takes the path of the file from the
user and send the path to the persistence class which does the reading of the csv file. We currently
support reading and writing to a csv file only. And the format of the file that we support for
reading should be Stock name,quantity. Now the read file is sent to the builder load function which
validates the share name and stores all the data in the hashmap, and then we call addPortfolio
method of the model to create the portfolio from the builder and add it to the portfolio list.

The builder used to create a new portfolio is a static method inside the portfolio class which is
packaged private class.

Now if the user wants to see the composition of the portfolio, it provides that choice to the
controller and controller calls the examineComposition method in the controller itself which takes
the user choice of which portfolio it wants to see the composition of. This method calls the
getComposition method of my model and that delegates it to the portfolio getComposition method which
returns a hashmap of stock ticker symbol and quantity present in that particular portfolio. The
controller send this map from model the showComposition method of view, and it displays it to the
user.

If the user wants to get the total value of the portfolio he asks the controller to get the total
value and controller takes in the date from the user and send the choice of the portfolio and date
to the portfolioValue method of the model which delegates the portfolioValue functionality to the
method in Portfolio class, and it calls a method getPrice for each stock inside the portfolio which
takes in the date for which the price is required. the returnPrice method checks if the file for
that stock is present or not, if it is not present it call the fetch data method which uses Alpha
advantage api to get the historical price data. It then stores that historical data in a newly
created file and also in the hashmap priceData. If priceData already has data then the api is not
called or if the file exists the api is not called and the price for that particular date and stock
is returned to the portfolio class which adds it to the total value. Similarly, this is done for each
stock in the portfolio and the computed value is sent to the controller which sends it to the view
to display it to the user.

If the user wishes to save the portfolio it chooses which portfolio to save and gives a path to the
controller. The controller then gets the composition of that portfolio from the getComposition
method and sends that map to the save method in the persistence class where it writes to the csv
file user has provided.

Our program supports all stocks in NYSE and NASDAQ, and it supports all the dates


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

1. StockController : This class represents a controller with a single action method.
It's designed to handle specific
requests within an application. The go() method is the primary entry point for the
action this controller performs.

Methods

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



