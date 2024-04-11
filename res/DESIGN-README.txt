ASSIGNMENT - 6

The design is implemented as follows:

My design follows the MVC pattern in which the work of the controller is taking the input from
the user and passes the input to the model for processing and, the controller sends the processed
data received from the model to the view to show that processed data to the user.

For, the GUI based view creation, there is an addition of new features interface class in the controller.
The features interface contains of all the methods that are shown in the GUI based view. This interface 
is implemented by the new StockControllerImplGUI. The methods in the controller get the input from the 
view and further pass the inputs to the model for getting further output. The StockControllerImplGUI
uses setView method to provide view with all the callbacks using the add features method of the view.
The add features method further represents the primary menu which is shown when no portfolio is created. 
The user can select any choice based on the options shown on the home page. Based on the user's choice 
respective private methods are called. This methods delegate the input from the view to the controller 
and controller further processes it to the model. If the model produces an output message then that is
stored in the error message string and further shown in the view, and if the model successfully processes 
the output then that is represented on the screen through the use of success message.


Here my model is the InvestmentManager interface and my model represents an Agent/ manager, that manages
the user's portfolios, which is creating an Inflexible or flexible portfolio and storing a list,
providing the user with portfolio statistics like totalValue at a given date, cost basis, buys and
sells the specific amount of a specific stock on a specified date. Just like a manager it also gives
the statistics of a particular stock asked by the user, such as if a stock gained or lost on a date,
if the stock gained or lost over a given period, what the moving average of a stock for x days, what
are the crossover days for a given period and what was a stock performance over a given period.
Using these stats the model suggests to the user when to buy and when to sell that specified stock.
So one object of my model represents a manager/ agent helping the user invest his money.
My model contains a list of Portfolio objects and an object of Interface StockStatistics
(used to get the stats of a given stock) and methods that delegate the operation on a particular
portfolio to the Portfolio class and operation on a particular stock to the StockStatistics Class.

AbstractPortfolio is an abstract class that implements the portfolio interface and is extended by
the FlexiblePortfolioImpl and PortfolioImpl which implements the
flexible portfolio functionalities and inflexible portfolio functionalities respectively.

Here my controller is the StockController which takes the input from the user based on the menu
shown by the view and calls the method of the model that is assigned to do that particular work.
It also sends the message or output received from the model to the view to display that output
to the user.

Here my view is the Iview Interface which takes the message or data from the controller and displays
the user that information. The view initially shows the menu to the user and any message the
controller sends.

Upon starting the program the controller asks the view using the showMenu method to show the main
menu to the user. The controller's go method takes the choice as input from the user. The user can
choose to create a portfolio. Then he is given a choice to create a flexible or an inflexible
portfolio. For an inflexible portfolio, a builder is used to create it. The controller takes
the name of the portfolio from the user and creates a builder object. Now when a user enters the share
name and share quantity it validates the share name in the builder and adds it to a hashmap inside
the builder. Now when the user has entered all the values the method and portfolio are called which
builds a portfolio from the builder and adds it to the list of portfolios contained in my model
(PortfolioDirImpl). This way it is made immutable and no one can add or remove stocks from it.
But if the user chooses to create a flexible portfolio the the controller just takes the name of
the portfolio and checks if the name already exists or not, if the name doesn't exist then it
creates an empty portfolio. And to populate this flexible portfolio, user can buy stocks using option 6.


If the user wants to load the portfolio, the controller takes this input and asks the user if he
wants to load an inflexible or flexible portfolio, and the user inputs that choice.If user chooses
inFlexible then the controller uses loadInflexiblePortfolio method in the controller itself which
takes in the portfolio name from the user and
validates that the portfolio doesn't exist before. It then takes the path of the file from the
user and sends the path to the persistence class which does the reading of the CSV file. We currently
support reading and writing to a CSV file only. The format of the file that we support for
reading should be Stock name and quantity for inflexible. Now the read file is sent to the builder
load function which
validates the share name and stores all the data in the hashmap, and then we call and addPortfolio
method of the model to create the portfolio from the builder and add it to the portfolio list.
For a flexible portfolio, we take the transaction in the CSV file from the user in the format
Transaction Type(Buy/Sell), Ticker Symbol, Quantity of the share, the Transaction Date. If the
file doesn't have this format or any data is invalid the model throws error to the controller and
the controller shows this error to the user.

The builder used to create a new portfolio is a static method inside the InflexiblePortfolioImpl class which is
packaged private class.

Now if the user wants to see the composition of the portfolio, it provides that choice to the
controller and the date on which he wants the composition of the portfolio and the controller calls
 the examineComposition method in the controller itself which takes
the user's choice of which portfolio it wants to see the composition of. This method calls the
getComposition method of my model and that delegates it to either FlexiblePortfolioImpl class or
PortfolioImpl class based on the portfolio selected by the user and it uses dynamic dispatch to do
so. This method in the class returns a hashmap of the stock ticker symbol and quantity present in
that particular portfolio. The controller sends this map from the model through the showComposition
method of view, and it displays it to the user.

If the user wants to get the total value of the portfolio he asks the controller to get the total
value and the controller takes in the date from the user and sends the choice of the portfolio and date
to the portfolioValue method of the model which delegates the portfolioValue functionality to the
method in either PortfolioImpl class or FlexiblePortfolioImpl class based on the portfolio selected
by the user and uses dynamic dispatch for it, this method also takes an object of type StockData
(which is a class in the controller package). Using this object we get the historical price data
for the ticker symbol. this class has a public method fetchHistoricalData which first checks if the
file for that ticker symbol exists for that particular date or not. If it doesn't exist then it calls
the API to get the data for that stock and saves the data into a file otherwise it reads the file and
loads that data into a Map containing the date, and the list of prices (opening, closing, low and high)
and returns it.  The total value is calculated based on the composition of the portfolio on that
particular date.

If the user wishes to save the portfolio it chooses which portfolio to save and gives a path to the
controller. The controller then gets the StringBuilder object from the save method of the model and
sends this object to the save method in the persistence class where it writes to the CSV
file the user has provided.

Now there is the functionality of cost basis which is valid for only flexible portfolios, so if the
user wants to get the cost basis of an inflexible portfolio, the model will give an error that the
cost basis cannot be found for the inflexible portfolio as the cost basis will be constant. The user
provides the portfolio choice and date till which he needs the cost basis, from the list of portfolios
he is being shown. The controller calls the get cost basis function of the model and that checks if
the portfolio selected is flexible or not. If the portfolio is inflexible then the model throws an
error and the controller displays it to the user. If the portfolio is flexible then it calls the
getCostBasis function of the flexiblePortfolioImpl class and it uses the transaction log in the
class to get all the buy transactions and calculate the total investment till that date and returns
to the controller to display it to the user.

Now for a flexible portfolio, we allow the user to buy or sell stock at any date in that portfolio.
So when the user chooses to buy or sell a stock, the controller asks the user for the portfolio he
wants to buy or sell the stock in the ticker symbol of the stock and the quantity and then the
transaction date. Then with these data, the controller calls the respective method in the model,
and the model checks if the portfolio is inflexible it throws an error to the controller, otherwise,
it delegates the work to the buy/sell method in flexiblePortfolioImpl class. The flexiblePortfolio
has a field that stores the composition of the portfolio on the date of every transaction.
So whenever there is a buy or a sell in that portfolio on a specific date, it stores the composition
for that date. So when there is a buy on a date it copies the composition present before that date
and adds it to the composition of that date as well as updates the composition of dates present in
the map after that date as all the future composition will consist of this share as well. The same
we do for sell, we copy the composition just before that date and sell the quantity requested by the
user, if it is not present it will give the user an error. If the quantity is present it will check
if the quantity will be present in the future as well or not, and if it is present then we will
deduct the quantity and save the composition for that date and deduct the quantity from the future
composition as well. So this way we have the composition on days of transactions and between
transactions composition remains the same. Also when buying and selling we add the transaction to
a logger which is a list of transaction objects, containing transaction type, share name,
quantity, and transaction date.

To get the historical data for buying, selling, getting value we are using Alpha vantage api. The
api call id made inside the FetchFromAlphaVantage class which implements FetchDataFromAPI interface
which is in the controller package and is packaged private and only used from the class StockData
which is also in the controller package. So fetching is done in isolation and this StockData class
implements from the IStockData interface. And to all the function of model which needs the
historical data we pass this interface object IStockData which is called to get the historical data.

For portfolio Performance, the user provides the portfolio choice and start date and end date
and the controller calls the performance method of the model, the model uses the performance
class which is packaged private inside the model package to get the performance of that
portfolio as a treemap of timestamps and the number of asterisks to be printed. This treemap
is sent to the controller and the controller uses the view to print the performance.

On exit of the program, all the folders inside the Data folder are deleted other than the folder
containing the historical data of different stocks that were fetched today. As the date changes the
folder is deleted upon exit as a new folder with that date's name will be created.

Our program supports all stocks in NYSE and NASDAQ, and it supports all the dates

CHANGES FROM THE PREVIOUS DESIGN:
1) Initially I had the fetching from the API functionality inside my model package,
but now I have created a new class Stock Data inside my Controller package as any data that
comes from outside the program must belong to the controller, So now storing the historical data,
fetching from API and getting historical data happens outside the model in the controller in
isolation. As it was initially happening inside the model package, it was happening for the
stocks of a portfolio, but in this assignment, we need historical data for other functions like
the stock analysis. So it is better now to get the fetch historical data from the controller as
now I can just pass the object to fetch this data to every method that requires it. So that is why
I made this change where now getting the historical data of a stock is in the controller package
and we are passing this class to the methods in the model. So to implement this functionality I
changed the signature of my getValue method which now takes a new parameter StockData. I have two
classes one StockData which returns the data either from file or uses the FetchDataFromApi interface
 to call the alphavantage api and get the data from the API and return that data.

2) So before we had this stock class which was storing the historical data and ticker symbol,
and in my portfolioImpl class I had a map containing this stock object as key and its quantity
as value. This StockImpl class was fetching the historical data and storing it, but now as
historical data is being fetched outside, this class just contained the ticker symbol, so I
removed this class altogether and changed the structure of my map to have String ticker symbol
as the key in place of the whole stock object.

3) I added methods like buyStock, sell stock, cost basis to my Portfolio interface and now my
portfolio interface is implemented by an abstractPortfolio class which is in turn extended by
the FlexiblePortfolio and InflexiblePortfolio class, but before only inflexible portfolio was
implementing the Portfolio interface. This is code refactoring as it improves the code and common
methods go in the abstract class and specific implementation and fields remain in the child class.
The inflexible also implements the buy sell, but now it throws an error if buy of an inflexible
portfolio is called.

4)Changed my model name from PortfolioDir to InvestmentManager as now my model also suggests
days to buy sell stock as compared to before when it only was keeping trach of list of portfolios.
Now my model represents an investment manager who manages a users portfolio and also helps him
invest, suggest days when to buy sell stock and gives the performance of the portfolio to the user.

5) I added the required new methods to the model interface like methods to get stock statistics
like crossover days, and it delegates the work to other classes inside model package. I did this
because I feel this functionality is not separate from the management of investment so I added
these method in the same model and this may my controller has only one model. I also added
functions for buying and selling shares into my model. So now my model interface has many new
methods, but it follows single responsibility and reduces coupling.

6) My portfolioComposition method in model now takes a date, as for flexible portfolio we can
see composition for any date, and it will be different and for an inflexible portfolio it doesn't
really matter if the date is there or not, so we kept a single function which takes in a date and
 returns the composition of the portfolio.

7) As far as my export method is concerned, the save initially was taking in a map of string and
integer and going through it and saving it to the file, but now I pass a string BUILDER object
containing the data to be saved. This way my save is not just restricted to the data structure it
is receiving, to save some other format of data in csv we had to write another function which would
take map of string and transaction objects, so to avoid that we changed the argument of the export
method and now it takes a string builder object and writes it into a file. So this made me add
a save function in my model which calls the save method in Portfolio interface and gets the
composition of either flexible or inflexible portfolio, and then converts the composition to a
string builder and returns it to the controller and controller calls the export method in
persistence class to export it to a file.

8)In my controller to incorporate new functionalities so my switch case statements changed.

9) My existing method getListOfPortfolio names changed, and now it returns a map of string and
string which contains the name of the portfolio and its type: flexible or inflexible. This helps
us to show the user this map so he can chose inflexible when needed and flexible when needed.This
way user will be kept informed that which portfolio is flexible and which is inflexible.

10) View had many changes as I needed to incorporate new functionalities. So now as different
type of portfolio came and buy sell came so my menu changed, the meu options changed. Now my list
of portfolio is a map so to print it in the view it must take a map so that also changed.

12) I created an Interface for my persistence class, as it was not there before and it is always
good to have an interface for all the classes.

12) There are no other major changes in my design other than the above-mentioned. I feel all the
changes were to incorporate new functionalities and making the code better, better designed and
cleaner.


The main program files created in the assignment are:

Model :


1. Investment Manager :

Interface:  Investment manager has the methods for delegating input to a particular portfolio through
the list of portfolios.

Responsibility: stores the data of list of portfolios created in the program,
through the portfolio directory array list.
Description: The PortfolioDir interface represents a portfolio manager.
It provides methods for managing portfolios within a portfolio directory.
These methods include adding portfolios, creating flexible portfolios, retrieving the list of
portfolio names, checking if the directory is empty, getting the size of the portfolio if a portfolio name
exists.
Additionally, it offers functionality for handling portfolio compositions, values, buying and
selling stocks, calculating cost basis, and analyzing stock trends. For stock trend analyzes the
methods are gain or lose on a date, gain or lose over a period, x-day moving average, crossover days
over a period, moving cross over days for a period. It also has methods for getting stock performance
over time and portfolio performance over time.

Methods :
1. addPortfolio() : this method is used to add new portfolio to the portfolio directory and builder
object is used to create the new portfolio.
2. portfolioNameExists () : this method is used to check if the portfolio with a given name already
exists in list of portfolios that is portfolio directory.
3. getListOfPortfoliosName() : the method gives the list of portfolio names that have been already
created, both flexible and inflexible list of portfolios.
4. portfolioComposition() :this method takes in the portfolio number as the input and the date for which
portfolio composition is to be found and on based this portfolio is called,
for that it calls the portfolioComposition method of Portfolio which loads the content of a particular portfolio.
5. getSize(): this method is used to get the size of number of portfolios in the array list of
directory.
6. portfolioValue() : this method takes in the input referring to portfolio number whose total
value is to be found and the date for which the total value is to be calculated and calls the
portfolioValue method of the Portfolio for further calculations and getting the price. The date is
taken as day, month and year in integers.
7. isEmpty() : this method checks if the portfolio directory list is empty.
8. createFlexiblePortfolio() : Creates a new flexible portfolio with the given name.
9. buyStock() : Buys stocks and adds them to the particular flexible portfolio.
10. sellStock() :Sells stocks from the specified flexible portfolio.
11. costBasis() : Calculates the cost basis of the flexible portfolio at the specified index for
the given date.
11. gainOrLose() : this method calculates the gain or loss of a stock for the given date.
12. gainOrLoseOverAPeriod() :this method calculates gain or loss of a stock  over a specified period.
13. xDayMovingAvg() : this method calculates the X-day moving average for a given stock on a specific date.
14. crossoverOverPeriod() : this method calculates crossover events over a
specified period for a given stock, whether it is a buy/ sell opportunity.
15. movingCrossOver() :this method calculates moving crossovers over a specified period for a given stock,
whether it is a buy/ sell opportunity.
16. stockPerformance() : this method calculates the performance of a specific stock over a given period.
17. portfolioPerformance() : this method calculates performance of a portfolio over a given period.
18. scaleForStockPerformance() : this method determines appropriate scale for displaying the
performance of a specific stock.
19. scaleForPortfolioPerformance() : this method determines appropriate scale for displaying the
performance of a portfolio.
20. save() : Saves the portfolio, based on flexible or inflexible portfolio.
21. loadPortfolio() : this method loads a portfolio from data provided by a list of strings and a
StockData object, based on type of portfolio to be loaded.
22.createDollarCostAverageStrategy() : method adds a new investment strategy to a portfolio in the
portfolio directory. It executes a dollar-cost averaging investment strategy for the given portfolio,
 with parameters such as the buying list (map of stocks to buy with respective percentages),
 start and end dates of the investment strategy, frequency of investments in days, total investment
  amount, and the stock data API to fetch historical prices.
 23. investWithDCAStrategy(): method executes an investment using the Dollar Cost Averaging (DCA)
 strategy. It takes parameters such as the frequency of investment (e.g., monthly, weekly, based
 on the number of days), a map containing stocks and their corresponding percentages of investment,
 the date of investment, the total amount to be invested, and the stock data API for accessing stock data.

2. InvestmentManagerImpl :

Responsibility: Implements the methods defined in the InvestmentManager interface.
Description : it has methods which are implemented for adding portfolio to portfolio directory,
getting list of portfolios, composition of a portfolio, and portfolio value.
Also. getting the total value of a specific portfolio in the array list of
portfolios stored in this class.
Additionally, it offers functionality for buying and selling stocks, calculating cost basis,
and analyzing stock trends. For stock trend analyzes the methods are gain or lose on a date,
gain or lose over a period, x-day moving average, crossover days
over a period, moving cross over days for a period. It also has methods for getting stock performance
over time and portfolio performance over time.

Methods :
Implementations for all methods defined in PortfolioDir Interface.


3. Portfolio :

Interface : interface which acts as the main model for portfolios and represents a single
portfolio object.
Description :  The interface has methods that help in creating a single portfolio object,
adding shares to portfolio, seeing the composition of portfolio, getting total value
of a portfolio, obtaining the portfolio's name, and saving the portfolio. Additionally,
it includes methods for buying and selling stocks, calculating cost basis,
and checking if the portfolio is flexible.
Responsibility : it contains the methods for managing a single portfolio. This portfolio can be
either a flexible portfolio or an inflexible portfolio.

Methods :
1. portfolioComposition() : this method is used to get the portfolio composition of a portfolio
which contains the stock name and its quantity.
2. portfolioValue() : this method takes in the date for which the portfolio value is to be found
and returns the portfolio value.
3. getName() : this method is used to get the name of the portfolio.
4. save() : this method is used to save the portfolio, based on the type of portfolio to be saved
the implementation varies.
5. buyStock() : this method is used to buy stock for a flexible portfolio.
6. sellStock() : this method is used to sell stock from a flexible portfolio
7. costBasis() : this method calculates the cost basis of a flexible portfolio.
8. isFlexible() : Checks if the portfolio is flexible.
9. load() : this method loads a portfolio from data provided by a list of strings and a
StockData object. The implementation varies based on the type of portfolio to be loaded.
10. strategicalInvestment() : The strategicalInvestment method executes a dollar-cost averaging
investment strategy based on the provided buying schedule, current date, and a given stock data API
to fetch historical prices. It takes three parameters: schedule, which defines the buying schedule
specifying the investment strategy; strategy, representing the strategy interface used to generate
 transactions; and api, which is the stock data API utilized to fetch historical prices.


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
is correct or not. Additionally, it also has buy stock , sell stock, cost basis methods that throw
an exception if these functionalities are retrieved for an inflexible portfolio. Also, if a flexible
portfolio is loaded through the load method of inflexible portfolio it throws an exception.
The is flexible method is used to check that this is not a flexible portfolio. The save method saves the
inflexible portfolio as the ticker symbols and the quantity of stocks for that particular stock and
returns the data to be saved as a string builder.

5. StockStatistic :
Interface: The StockStatistic interface provides methods to calculate various statistics related to
 stocks, for finding the stock trend statistics.
Description: The StockStatistic interface provides a framework for calculating various statistical
measures related to stocks, facilitating analysis of stock market data, basically providing stock
trends for a particular stock.
Responsibility: This interface is responsible for defining methods that enable the computation
of stock-related statistics such as gain/loss, moving averages, and identifying crossover points.

Methods:

1. gainOrLoseOnDate(): Calculates gain or loss of a stock on a specific date.
2. gainOrLoseOverPeriod(): Calculates gain or loss of a stock over a period of time.
3. xDayMovingAvg(): Computes the X-day moving average of a stock on a specific date, using the last
 x-number of days provided.
4. crossoverOverPeriod(): Identifies crossover points over a specified period for a stock and finds
whether a particular date in the period has sell or buy opportunity.
5. movingCrossoversOverPeriod() : Identifies moving crossovers over a specified period for a stock,
 using the x-day( shorter moving average days), y-days (larger moving average days) and identifies
 the crossover days in the period with which type of crossover occurred a buy/sell crossover on that date.

6. StockStatisticsImpl :
Interface: it implements all the methods of StockStatistic interface.
Description :The StockStatisticsImpl class implements the StockStatistic interface,
providing methods for calculating various statistics related to stocks,
particularly for analyzing stock trends.
Responsibility:
This class is responsible for implementing methods defined in the StockStatistic interface
to perform calculations such as determining gain/loss, computing moving averages,
and identifying crossover points for a given stock based on provided price data.

Methods :
The gainOrLoseOnDate method provides that on a particular day whether a stock gained, lost or remain
unchanged and also shows no price data found if for that date price data is not available for the stock.

The gainOrLoseOverPeriod method provides the output that a particular stock gained / lost in a particular
duration of time. If the start date entered has no price data available it moves to next day in the
time period. If the end date of period has no price data on that date, it moves one day backward
in the period. It shows a stock gained / lost/ remained unchanged over a period of time.
If there is no price data available at all for the given period then it shows a
message no price data available for that period.

The xDayMovingAvg method calculates the x-day moving average for a specified stock using historical
price data. It begins its computation from a given date, iteratively collecting closing prices for
the specified number of days (x). If the closing price for a particular day is not available,
it looks for the closest previous valid date with available price data. If there is insufficient
data available, it throws an IllegalArgumentException.
Finally, it computes the average of collected closing prices and returns the result.

The crossoverOverPeriod method identifies crossover points (buy/sell signals) within a specified
period for a given stock, based on its 30-day moving average. It iterates through each date within
the period, computing the moving average using the xDayMovingAvg method. It then compares the closing
prices of the current and previous days with the moving average to determine if a crossover has
occurred. If so, it adds the date and corresponding action ("buy" or "sell") to a TreeMap.
In cases where price data is missing or incomplete, it handles these scenarios,
either by skipping the date or throwing an IllegalArgumentException if essential data is absent.
Finally, it returns the TreeMap containing crossover information.

The movingCrossoversOverPeriod method identifies moving average crossovers within a specified period
for a given stock, where the shorter moving average (x) crosses over the longer moving average (y).
It computes the moving averages for both x and y days at each date within the period using the
xDayMovingAvg method. Then, it compares the current and previous day's moving averages for both x
and y days to determine if a crossover has occurred. If a crossover is detected, it adds the date and
corresponding action (buy/sell) to a TreeMap. The method sees that x is shorter than y,
throwing an IllegalArgumentException if not.

7. Performance :

Description :  The Performance class in the model package provides methods for analyzing
the performance of stocks and portfolios over time. It offers functionalities for calculating
the performance of stocks within specified time frames, as well as determining the performance
of portfolios. Additionally, it includes methods for determining scales for visualizing performance
data, and converting the price data based on scale, for better representation.

Methods:
1.stockPerformance(): Computes the performance of a stock within a specified time frame.
It takes historical price data of the stock, start date, and end date as input and returns a
TreeMap containing the selected stock data, with timestamps and prices on those days.
2. portfolioPerformance(): Computes the performance of a portfolio within a specified time frame.
It takes a Portfolio object, start date, and end date as input and returns a TreeMap containing the
 selected portfolio data, with timestamps and portfolio values on those days.
3. determineScale(): Determines the scale for visualizing performance data over time.
It takes a TreeMap containing price data as input and returns the scale value for better representation.
4. determineValueBasedOnScale(): Scales the prices for the timestamps based on the calculated scale.
It takes a TreeMap containing price data and a scale factor as input and returns a TreeMap
containing scaled prices with timestamps.
5.sortTreeMapByMonthAndYear(): Sorts a TreeMap containing data by month and year.
It takes a TreeMap containing data with timestamps in the format "MMM yyyy" and returns the sorted
TreeMap, by sorting the dates in proper chronological order.

8. FlexiblePortfolioImpl :

Description:
This class is responsible for managing and processing data related to flexible portfolio.
Responsibility:
The class has methods for flexible portfolio management, including buying and selling stocks,
computing portfolio composition, cost basis, and getting total value.

Methods:

1. buyStock(): Buys a specified quantity of a stock on a given date for a flexible portfolio..
2. sellStock(): Sells a specified quantity of a stock on a given date for a flexible portfolio.
3. portfolioComposition(): gets the composition of the portfolio on a specified date.
4. costBasis(): Calculates the total cost basis of the portfolio up to a specified date.
5. portfolioValue(): Calculates the total value of the portfolio on a specified date.
6. save(): Generates a string representation of the portfolio transactions for saving to a file.
7. isFlexible(): Indicates whether the portfolio is flexible or any other type of portfolio.
8. load(): this method loads portfolio transactions from a list of string arrays representing
transaction data, and creates a new flexible portfolio based on the data.

9. Transaction :
Description:
The Transaction class, has details about a transaction, such as the transaction type
(buy/sell), the ticker symbol of the stock, the quantity of shares involved,
and the date of the transaction.

Responsibility:
This class ensures that transaction data is encapsulated in a Transaction object, providing methods to
retrieve transaction details such as type, ticker symbol, quantity, and date.
Methods:

1.getType(): Returns the type of the transaction buy/sell.
2. getStock(): Returns the ticker symbol of the stock involved in transaction.
3. getQuantity(): Returns the quantity of shares involved in transaction.
4. getDate(): Returns the date of the transaction.

10. AbstractPortfolio :

Description: abstract class implementing common functionality for both flexible and
inflexible portfolios.
Responsibility: it has common portfolio functionality such as retrieving
closing prices of stocks, computing portfolio values, and validating stock names. Also, it has a
method for creating deep copies of maps.

Methods:

1. getName(): gets the name of the portfolio.
2. getClosingPriceOnDate(): gets the closing price of a stock on a specified date.
3. computeValue(): this method computes the total value of the portfolio on a specified date
based on the composition and stock prices.
4. validateStockName(): Validates if a given share name exists in the stocks.csv file and
returns its ticker symbol.
5. deepCopy(): Creates deep copy of map to ensure independent copies of composition of portfolio.

11. Strategy :
The Strategy interface, situated in the model package, delineates the blueprint for implementing
investment strategies within the system. It encapsulates the method applyStrategy(LocalDate date,
 Schedule schedule, IStockData api), responsible for generating transactions based on a given date,
 schedule, and access to stock data via the provided interface IStockData api.
 This method orchestrates the application of the strategy, producing a list of transactions as its
 output, each representing a specific action such as buying or selling stocks in accordance with the
  implemented investment strategy.

12. Schedule :
The Schedule interface, located in the model package, serves as a blueprint for implementing
buying strategies within the investment system. It facilitates the investment of a specific amount
in an existing flexible portfolio on a designated date by specifying the weights for distributing the
investment across various stocks within the portfolio. This interface encapsulates methods such as
 getAmount(), getFrequencyDays(), getStartDate(), getEndDate(), getLastRunDate(), and getBuyingList()
 which respectively retrieve the amount to invest, the frequency of transactions in days, the start
 and end dates of the buying strategy, the date of the last execution of the strategy, and a map
 representing the buying list indicating the assets to buy and their corresponding investment amounts.

13. DollarCostAverageStrategy :
The DollarCostAverageStrategy class encapsulates the logic for a Dollar Cost Averaging investment
 strategy, which involves investing a fixed amount of money at regular intervals. It applies the
 strategy to generate transactions based on given parameters such as the current date, the scheduling
  for strategy execution, and an interface for accessing stock data. The method applyStrategy calculates
   and generates transactions for the strategy, ensuring that the investments are made according to the
   defined schedule and buying list while handling any encountered invalid stock names appropriately.

14. BuyingStrategy :
The BuyingStrategy interface defines the structure for representing a buying strategy for
investments. It specifies methods to retrieve various parameters of the buying strategy such as the
 amount of money to be invested, the frequency of transactions in days, the start and end dates of
 the strategy, the last execution date, and a map representing the buying list, which contains symbols
  of assets to buy along with corresponding amounts to invest in each asset. This interface allows for
  the implementation of flexible and customizable buying strategies within investment portfolios.

14. BuySchedule :
The responsibility of the BuySchedule class is to represent a schedule for buying stocks,
encapsulating information such as the investment amount, buying frequency in days, start and
 end dates of the buying strategy, the last run date, and a map containing stocks to buy along with
  their respective percentages. Additionally, it ensures that the provided buying list is correctly
   formatted and scaled to sum up to 100%, throwing exceptions for invalid inputs.


Controller :

1. StockController : This class represents a controller with a single action method.
It's designed to handle specific
requests within an application. The execute() method is the primary entry point for the
action this controller performs.

2. StockControllerImpl : has the model, view and scanner object for taking input,
showing the desired output through view and performing the required operations using the model.
he execute method works based on the choice entered by user and delegates to the view and model
methods
for getting the output. The other methods such as create portfolio, load portfolio, save portfolio,
get composition, get total value of portfolio, save, examine composition
are written to call the respective model method for passing inputs from the controller and calling
the view for those methods to show the output, the output from the model is passed to the view by
the controller for showing it to user. On exit of the program all the folders inside the Data folder
is deleted other than the folder containing the historical data of different stocks that were
fetched today. As the date changes the folder is deleted upon exit as a new folder with that date's
name will be created.

3. Persistence : this class has methods that help in loading and saving the csv files for
 persisting the stock data that is associated with a portfolio , this function is done by
 exportAsCSV method. The loadFromCSV method loads the user provided CSV file that is to be used to
 create a portfolio. So, here the file is read and returns the lines of file that contain stock name
 / ticker symbol and its quantity is returned.

 Methods:

1.exportAsCSV(): This method exports the given composition data to a CSV file at the specified path.
 It takes the file path where the CSV file will be created and a StringBuilder containing the data
 to be written to the file. The data is written to the CSV file in the format specified by
 the StringBuilder. If the file path does not end with ".csv" or is invalid,
 an IllegalArgumentException is thrown. After writing the data to the file,
 it prints a success message indicating that the data has been exported successfully.
2.save(): This method saves historical data to a CSV file at the specified path.
It takes the file path where the CSV file will be created and a Map containing historical data,
 where dates are mapped to lists of price values. The historical data is iterated over,
 and each entry is formatted into a CSV row and written to the file. If the parent directory of the
 specified file path does not exist, it creates the directories recursively.
 3. loadFromCSV(): This method loads data from a CSV file located at the specified file path.
 It takes the file path of the CSV file to be loaded. It reads the lines from the CSV file, and
 splits each line into an array of strings. These arrays are stored in a list,
 which is returned once all lines have been read. If the file does not exist or the file format
 is not CSV, appropriate exceptions are thrown.

4. StockData :
Description : The StockData class provides methods for fetching historical stock data and
storing it in a CSV file.
Responsibilities: Fetch historical stock data from an API. Store fetched data in a CSV file.
Retrieve historical stock data from the CSV file.

Methods :

1. fetchHistoricalData(): This method fetches historical stock data for the specified ticker symbol.
 It first checks if the data for the ticker symbol is already available in a CSV file.
 If not, it fetches the data from an API, stores it in a CSV file, and then loads the data from the file.
 Finally, it returns a TreeMap containing historical stock data, with dates as keys and price information as values.
2. fetchData(): This private method is used to call the API for fetching stock data using
 the API key. It constructs the API URL with the provided ticker symbol and API key, reads the data
  from the URL, and then calls the storeFetchedData() method to store the fetched data in a CSV file.
3. storeFetchedData(): This private method stores the data obtained from the API call in a CSV file.
 It takes a StringBuilder containing the data fetched from the API as input, parses the data, and
 stores it in a CSV file using a Persistence object.
4. loadDataFromFile(): This private method is used to load data from a CSV file for a particular
ticker symbol present in the current date's folder. It constructs the file path based on the
ticker symbol and current date, then uses a Persistence object to load the data from the CSV file
and populates the priceData object with the retrieved data.
5. populateMap(): This private method populates the priceData TreeMap with historical
stock data extracted from a given line of data. It takes an array of strings representing a line
of historical stock data as input and parses the data to extract the date, opening price, closing
price, high price, and low price. It then stores this data in the priceData TreeMap.
6. isCSVFileExists(): This private method is used to check if a CSV file exists for a
particular ticker symbol in the data folder. It constructs the file path based on the ticker symbol
and current date, then checks if the file exists.
It returns true if the CSV file exists, otherwise false.

5. Features : The responsibility of the Features interface is to define a set of methods that
interact with the user interface (GUI) to facilitate various actions related to portfolio management.
These actions include creating flexible portfolios, exporting portfolio data to files, buying and
selling stocks, investing with Dollar-Cost Averaging (DCA) strategy, examining portfolio composition,
retrieving total portfolio value and cost basis, analyzing gains or losses for stocks, calculating
moving averages and crossover points, handling error and success messages, retrieving portfolio names,
loading portfolios from files, and creating portfolios with specified investment strategies.
These methods delegate user input to model methods for performing the corresponding actions.

6. StockControllerImplGUI :
The StockControllerImplGUI class implements the Features interface and serves as a controller for
 managing investment manager-related operations in a graphical user interface (GUI) environment.
 It handles tasks such as creating flexible portfolios, exporting portfolio data, buying and selling
  stocks, retrieving total portfolio value and cost basis, analyzing gains or losses for stocks,
  calculating moving averages and crossover points, handling error and success messages,
  loading portfolios from files, and creating portfolios with specified investment strategies. It
  interacts with the model (InvestmentManager) to perform these operations and communicates with
  the view component (IViewGUI) to display information and receive user input. Additionally,
  it contains methods for validating user input, managing error messages, and setting the view component.







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
5. showListOfPortfolios() : this method displays a list of portfolios that have been created along
 with the type of portfolio.
6. displayError() : this method displays an error message.
7. print() : this method prints a message.
8. barGraph() : this method displays a bar graph using the input got from the controller to show
the stock and portfolio performance using asterisks.
9. choosePortfolioType() : this method displays the type of portfolio to choose from flexible or
inflexible.
10. showStockStat() : this method displays the menu option for stock statistics trend.
11. showXDayMovingAvg() : this method displays the x-day moving average for a particular stock.
12. printTreeMapEntries() : this method displays the crossover output for the program, with the
 string date and other string value showing the type of crossover on the particular date.

2. IViewImpl :
 Responsibility : IViewImpl is the implementation of the interface IView, which has methods for
 showing menu to the user, portfolio composition, showing total value, list of portfolios,
 displaying error messages, showing the bar graph for portfolio and stock performance to the user,
  showing the stock statistics menu, showing the x-day moving average, crossover days, displaying
  portfolio types to choose from and printing the messages for user interaction with menu.

3. IViewGUI :
Interface: The IViewGUI interface defines methods for interacting with the graphical user
interface (GUI) components of the application.
Methods:
1. addFeatures(): This method sets the Features instance for the GUI
and connects the view with the controller. It defines callback functions based on actions to
perform when used in the view.

4. GUIView: The GUIView implements the IViewGUI and further has various private methods that help in
creating a proper user-friendly view. And also methods to direct the input from the view to the controller
for all the different methods.
The GUIView class in the view package of the investment application is responsible for managing the
 graphical user interface (GUI) elements and interactions. It creates various components such as
 buttons, text fields, and labels, and handles user actions like creating portfolios, buying and
 selling stocks, saving portfolios, and implementing investment strategies like Dollar Cost
 Averaging (DCA). Additionally, it communicates with the Features class to perform corresponding
 actions and displays relevant information to the user, ensuring a seamless and intuitive user
  experience throughout the application. It has all the methods that are represented using GUI View
  persisting to the flexible portfolio.

5. CreateStrategyPage : The CreateStrategyPage class is used for creating
investment strategies within the graphical user interface (GUI) of the application.
It provides functionality to display forms for inputting portfolio details, including stock names,
 quantities, total amount, frequency days, start and end dates. Additionally, it handles user
 interactions for submitting and confirming the strategy, validates user input,
 and interacts with the Features instance to perform actions such as creating portfolios and
 managing strategies. Furthermore, it manages UI components and displays selected dates using date
 pickers, ensuring a smooth user experience throughout the strategy creation process.










