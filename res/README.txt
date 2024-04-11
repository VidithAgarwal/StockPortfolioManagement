ASSIGNMENT - 6

The assignment follows MVC architecture for portfolio creation both flexible and inflexible,
management (save/load portfolio), creating dollar cost averaging, investment strategies ( for flexible portfolios),
stock and portfolio statistic, performance evaluation for stock and portfolio,
composition of portfolio, total value and shares handling for a portfolio.

The program has various features for stock addition, portfolio creation (flexible and inflexible),
persisting the portfolio, getting total value of portfolio, getting composition of a portfolio
and loading a portfolio, cost basis for flexible portfolio, also buying and selling of stocks for
flexible portfolio, and creating dollar cost averaging, investment strategies ( for flexible portfolios).

The stock statistics features are also provided such as gain or lose on
a date,gain or lose over a period, x-day moving average on a given date, crossover for a specified time
period, moving crossover for a time period. Performance over time for stock and portfolios.

The user gets two view options, one is text based view and other is GUI based view. Default view provided
to the user is GUI based view. Which contains the functionality related to flexible portfolio. And
also the stock statistic analysis.
Whereas the text based view is called when -text is typed in command line as arguments , this text -
based view contains functionality for both the flexible and inflexible portfolio. But only does not
have the additional functionality of dollar cost averaging and investment strategy adding for flexible
portfolios.

1. Creating inflexible portfolio , adding shares and their quantity :
The program allows the user to create one or more portfolios, and have one or more stocks in the
 portfolio. The stocks can be added in the portfolio along with their name/ticker symbol and
 quantity. The stocks once added in the portfolio cannot be removed from the portfolio or new
 stocks cannot be added once the portfolio is created. For creating portfolio the user has to enter
 portfolio name that is unique and also enter the number of shares that will be added in portfolio.
 Then enter the stock name and their quantity to the portfolio and then the portfolio is created.
 The stock name is validated that a particular stock name or ticker symbol entered by user exists
 and the quantity of the share is validated to be taken as a whole number only.

2. Examine Composition of Portfolio on certain date:
The user can examine the composition of a portfolio by entering the portfolio number from the
displayed portfolio list and a date for which they want to know the composition.
The composition gives the stock's ticker symbol along with the quantity of the stock that was entered
while creating the portfolio for inflexible portfolio and shows the stock quantity and ticker symbol
which are bought for the flexible portfolio until a specific date.

3. Total Value of Portfolio on certain date :
The total value for a portfolio can be found by entering the portfolio number from the list of portfolios
shown for which the total value is to be calculated.

The total value of a inflexible portfolio can be found for date entered by the user. If the data for
that date exists when the data is fetched using api calls, then price is returned or the data is
not found for that date message is printed.

The total value of a flexible portfolio can be found by entering the date for which total price is
to be found. The total value is returned if data is available for the date, or no price data found
message is printed. The total value is calculated based on composition until that date of the portfolio.
And it changes based on the buy and sell of stocks in the portfolio.

The api is called only once for a particular stock during the day and hence,
decreasing the number of api calls. The price details for a stock is retrieved by calling the alpha
vantage api which provides date wise price data. So, when the total value is called the alpha
vantage api is used to fetch the data for the stocks present in the particular user portfolio for
which total value is to be found. When the api is called for a stock, the data is saved in a file,
and a hashmap price data for faster operation.
When saving the data a file we save it into a directory name with the current date,
and while searching for that file we check for the directory with the current date as its name.
So if the date changes we won't find the data for that stock and will call the alpha advantage api
and store that data in a new folder with the current date's name. So this way we update our data
on a daily basis and api calls are reduced.

4. Saving a portfolio :
The user can also save the portfolio by entering the portfolio number which is to be saved from
the portfolio list. The portfolio is saved when the user provides a correct path for saving the portfolio.
The inflexible portfolio contains stock quantity and ticker symbol for the stocks that were added in
inflexible portfolio. The flexible portfolio contains the stock quantity, ticker symbol, buy or sell
transaction, and date of the transaction.

5. Loading a portfolio :
The user can also load the portfolio from their system, this can be done by entering the path of
the valid csv file having portfolio details, also the user has to enter which portfolio is to be loaded
flexible or inflexible.
The user also has to give name to the portfolio and the valid path of csv file that is to be loaded
to create portfolio. The portfolio is then created having the composition of portfolio as the valid file
contents and the portfolio name that is entered by the user.
For inflexible portfolio while loading the portfolio the file should contain stock quantity and ticker
symbol which is validated while loading the file.
For flexible portfolio while loading the portfolio the file should contain 4 fields, stock quantity,
ticker symbol/stock name, whether the transaction for that stock was buy/sell, and transaction date.


6. Creating a flexible portfolio, buying and selling stocks on certain date:
For creating  a flexible portfolio, user has to enter create a portfolio, and further enter
flexible portfolio which is to be created. Now, the user has to enter the name of the portfolio
which he/she wants to be created which should be unique and not be used by another portfolio.
The portfolio is created.
Now to buy stocks the user has to enter that option from the main menu, for buying stock the user has
to first enter for which portfolio he/she is willing to purchase stocks. Then enter the stock name/
ticker symbol, amount to be bought and date on which he/she wants to buy stock and if date, stock name is valid the
stocks will be added to that portfolio.
Now to sell stocks the user has to enter the option to sell stock from the main menu, for selling stocks
user has to enter for which portfolio he/she wants to sell the stocks. Then enter the stock name/ ticker symbol,
amount to be sold and date for selling. If the ticker symbol is valid, along with quantity and date
then sell transaction is successful for that portfolio.

7. Cost Basis of a flexible portfolio:
Cost basis is the total amount invested until certain date in a particular flexible portfolio.
To get the cost basis, the portfolio number is to be selected for which cost basis is to be found.
If cost basis is calculated for inflexible portfolio it will show a message that cost basis cannot be
calculated.
For flexible portfolio, to get the cost basis the user has to first select the portfolio and then
enter the date for which user wants to know cost basis of portfolio. The total money invested
until that date for the respective portfolio will be shown to the user.

8. Stock Statistics :
a. Gain or lose on date for a stock:
The user can get gain or lose for a particular stock on a date, by entering that option from the stock
statistic menu, the valid stock name or ticker symbol, and the valid date for which they want to know the details.
The results are printed on the screen based on this data.

b. Gain or lose over a time period for a stock:
The user can get gain or lose for a particular stock over a period, by entering that option from the
stock statistic menu. For this user has to enter valid ticker symbol/ stock name, start date and end date for
which the user wants to know the gain or lose of a stock. The results are printed on the screen based on
this data.

c. X-day moving average on a date for particular stock:
The user can get x-day moving average by entering that option from the stock statistic menu.
Further, enter the ticker symbol/stock name for which user want to get the x-day moving average, enter
the date from which last x-moving days average should be considered, and also x positive days. The value
for the x-day moving average is the printed on the screen.

d. Crossover days for a particular stock over a specific time period:
The user can get the total crossovers (dates) that occur between certain time period, the user has to enter
this option from the stock statistic menu. Then, the user has to enter the stock name/ ticker symbol
for which they want to know the crossover over a time period, enter the start data and end date
for the time period. And if there are any crossover opportunities the same will be printed on the screen
with the date of crossover and whether it was a sell or buy opportunity.

e. Moving crossover for a stock over a specific time period:
The user can get the total moving crossovers (dates) that occur between certain time period, for which
user has to enter this option from the stock statistic menu. The user has to then enter the stock name/
ticker symbol for which they want to get this data, the start date and end date for providing the
specific time period, the number of x days for having the shorter moving average period, and the
number of y days for longer moving average (y should be greater than x). After, this data is
entered the output is printed with crossover opportunities if any for that period. The date and the
type of opportunity (buy/sell) on that date is printed.

f. Stock Performance for a particular stock, for a specific period of time:
The user can get stock performance for a stock over a period, by entering this option from the stock
statistic menu. Then, the user has to enter the name of share/ ticker symbol, the start date,
and end date for providing the specific time period. Then, the stock performance over that period
for the entered stock is displayed using a bar graph representation having equally parted time-stamps
and (*) asterisks to represents the variation in stock value, along with the scale.

9. Portfolio Performance, for a specific period of time:
The user can get portfolio performance for flexible and inflexible portfolio, by entering this option
from the main menu, using they have at least one portfolio created. The user has to enter the portfolio
number for which they want to get the performance. Then, enter the start date and end date for providing
the time period. The portfolio performance is displayed using a bar graph representation having equally
parted time-stamps and using asterisks (*) to represents the variation in portfolio value, along with
the scale.
For inflexible portfolio, the performance is calculated by getting the total value of portfolio on the
time-stamp dates over the period.
For flexible portfolio, the performance is calculated by getting the total value of portfolio on the
time-stamp dates over the period. The total value depends on the composition of the portfolio, and changes
as stocks are bought and sold from the portfolio.

10. Creating Dollar Cost Averaging Strategy for flexible portfolio : The user can create a dollar cost
averaging strategy at the time for creating a flexible portfolio, by entering start date for strategy,
end date for strategy,
number of days (frequency) based on which strategy is to be carried out, amount for creating the
strategy that is how much money is to be invested. And for all the stocks in the portfolio ask the user
to enter the percentage of a particular stocks they want to get invested. The percentage should add
upto 100%. Here, when creating portfolio the users cannot buy fractional shares explicitly,
however this strategy does allow purchases of fractional shares depending on the amount specified.
That depends on the percentage and the amount entered for investment. That is offers creating
"start-to-finish" dollar-cost averaging as a single operation.


11. Investment with dollar cost averaging strategy for flexible portfolio : Here, the user has to enter
the inputs as mentioned above. The only difference is the portfolio is already created and therefore,
for that flexible portfolio additionally the dollar cost averaging strategy is added. For this the user
has to select from the existing portfolios list and enters the inputs as similar to above-mentioned
function of creating dollar cost averaging strategy for flexible portfolio. The strategy may be
ongoing, i.e. the user may not specify an end date . So, in that cases the end date is taken as the
current date. The program additionally also supports the addition of new strategies in future without
any change in the existing code.

12 : GUI-based view : The user gets to access the functionality using the GUI based view.
The user gets options to create a flexible portfolio, load a flexible portfolio, and create a portfolio
with dollar cost average strategy at the beginning. Once, there is a single portfolio created the user
gets different options to select from. The user can get composition of a portfolio, buy stocks, sell
stocks, get cost basis, get total value, save portfolio, investment in a portfolio with dollar cost
average strategy, and get stock analysis.
Based on the choice selected the user has to enter the required fields to get the required output.
The date field is taken to be selected from calendar. The portfolio is to be selected as a drop-down
to select portfolio. The composition is shown in a tabular form, save and load allow to take in csv files
only. There are various features provided by the gui, like it throws error messages when user enters
some invalid input and also shows success messages to user when user enters correct values and gets the
required output.


Additional Details:

1. Error Handling: Ensures robust error handling for invalid user inputs and
data retrieval failures.

2. API Efficiency: Optimizes API usage by caching stock price data during a session
to minimize API calls.

3. User Interaction: Provides clear user interfaces for seamless interaction with the program
through the command line argument interaction.





