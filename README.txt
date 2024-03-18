# Assignment4

The assignment follows MVC architecture for portfolio creation, management and shares handling
for a portfolio.

The program has various features for stock addition, portfolio creation, persisting the portfolio,
getting total value of portfolio, getting composition of a portfolio and loading a portfolio.

1. Creating a portfolio , adding shares and their quantity :
The program allows the user to create one or more portfolios, and have one or more stocks in the
 portfolio. The stocks can be added in the portfolio along with their name/ticker symbol and
 quantity. The stocks once added in the portfolio cannot be removed from the portfolio or new
 stocks cannot be added once the portfolio is created. For creating portfolio the user has to enter
 portfolio name that is unique and also enter the number of shares that will be added in portfolio.
 Then enter the stock name and their quantity to the portfolio and then the portfolio is created.
 The stock name is validated that a particular stock name or ticker symbol entered by user exists
 and the quantity of the share is validated to be taken as a whole number only.

2. Examine Composition of Portfolio:
The user can examine the composition of a portfolio by entering the portfolio number from the
displayed portfolio list. The composition gives the stock's ticker symbol along with the 
quantity of the stock that was entered while creating the portfolio.

3. Total Value of Portfolio on certain date :
The total value of a portfolio can be also found for date entered by the user. If the data for
that date exists when the data is fetched using api calls, then price is returned or the data is
not found for that date message is printed. The api is called only once for a particular stock
during the day and hence, decreasing the number of api calls. The price details for a stock
is retrieved by calling the alpha vantage api which provides date wise price data. So, when the
total value is called the alpha vantage api is used to fetch the data for the stocks present in the
particular user portfolio for which total value is to be found. When the api is called for a 
stock, the data is saved in a file, and a hashmap price data for faster operation. When saving the
data a file we save it into a directory name with the current date, and while searching for that
file we check for the directory with the current date as its name. So if the date changes we won't
find the data for that stock and will call the alpha advantage api and store that data in a new
folder with the current date's name. So this way we update our data on a daily basis and api calls
are reduced.

4. Saving a portfolio :
The user can also save the portfolio by entering the portfolio number which is to be saved from 
the portfolio list.
The portfolio is saved when the user provides a correct path for saving the portfolio.
Any csv file other than stock.csv in the same folder as the jar file gets deleted upon exit. So if
you save the portfolio in the same folder as the jar file it will get deleted after exit.

5. Loading a portfolio :
The user can also load the portfolio from their system, this can be done by entering the path of
the valid csv file having portfolio details. The user also has to give name to the portfolio and
the valid path of csv file that is to be loaded to create portfolio.
The portfolio is then created having the composition of portfolio as the valid file contents and
the portfolio name that is entered by the user.

Additional Details:

1. Error Handling: Ensures robust error handling for invalid user inputs and
data retrieval failures.

2. API Efficiency: Optimizes API usage by caching stock price data for a day
to minimize API calls.

3. User Interaction: Provides clear user interfaces for seamless interaction with the program
through the command line argument interaction.










