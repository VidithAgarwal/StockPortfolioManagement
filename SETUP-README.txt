SETUP FOR RUNNING THE JAR FILE:

The jar file should be present in the folder along with stocks.csv that has the list of stock names
along with the ticker symbol that is used in the program to verify if the stock name or ticker
symbol entered by the user is valid or not.

--- In order to run the program for creating a portfolio with 3 different stocks,
the steps are following:

1. First Main Menu will be shown as following to the user, here to create a new portfolio the
user has to enter 1.

Main Menu
1. Create a portfolio
2. Load a portfolio
3. Exit
Enter your choice:
1

2. After 1 is entered the name of portfolio to be given will be asked, as following , the name of
portfolio is given as College Fund

Enter the name of the portfolio:
College Fund

3. Then the number of stocks that will be added in the portfolio is asked, which can be a whole
number only.

Enter the number of stocks you want to have in this portfolio:
3

4. Now, 3 stock names and their quantity will be asked, the quantity can also be whole number only.

Enter the name of the share or ticker symbol:
Apple Inc
Enter the quantity of Apple Inc you have:
10
Enter the name of the share or ticker symbol:
GOOG
Enter the quantity of GOOG you have:
20
Enter the name of the share or ticker symbol:
Arrow Financial Corp
Enter the quantity of Arrow Financial Corp you have:
5

Here, the 3 different stock name/ ticker symbol entered are : Apple Inc, GOOG, Arrow Financial Corp.
And their respective quantities are: 10, 20, 5.

After this when the user clicks enter, the portfolio with name College Fund having these 3 stocks
is created.

--- In order to create a second portfolio with 2 different stocks, in continuation with the above
code, the flow will be as shown below, and once a single portfolio exists, different menu options
will be shown.

5. Main menu is shown, in order to create a second portfolio, enter 1 again as shown here :

Main Menu
1. Create a portfolio
2. Load a portfolio
3. Get composition of a portfolio
4. Get total value of a portfolio for certain date
5. Save a portfolio
6. Exit
Enter your choice:
1

6. The name of the portfolio to be created will be asked as is given as Oldage Fund, as shown here:

Enter the name of the portfolio:
Oldage Fund

7. The number of stocks to be included in this portfolio are asked, which is given as 2, as shown:

Enter the number of stocks you want to have in this portfolio:
2

8. The stock name/ ticker symbol and the quantity of the stocks is entered for 2 different stocks:

Enter the name of the share or ticker symbol:
AAON Inc
Enter the quantity of AAON Inc you have:
10
Enter the name of the share or ticker symbol:
Absci Corp
Enter the quantity of Absci Corp you have:
20

Here, after clicking enter the portfolio Oldage Fund is created with the 2 stocks,
The stock added are : AAON Inc and Absci Corp
The quantity of respective stocks is 10 and 20.

9. Now the main menu is shown again as represented, and in order to get the total value of a
portfolio we enter 4.

Main Menu
1. Create a portfolio
2. Load a portfolio
3. Get composition of a portfolio
4. Get total value of a portfolio for certain date
5. Save a portfolio
6. Exit
Enter your choice:
4

10. After 4 is entered, the list of existing portfolios is shown, from which we are required to
select the Oldage Fund portfolio that is second portfolio, to get its total value. So, for that
the No. of Portfolio shown along with Oldage Fund is entered that is 1 :

Get total value of a portfolio for certain date
The list of existing portfolios:
No. of Portfolio	Portfolio Name
0					College Fund
1					Oldage Fund
Enter the Portfolio number you want to select.
1

11. Now, in order to get the total value on a specific date the date is to be entered in correct
format mentioned, any wrong format or wrong date, will ask the user to enter a valid date again.
The date entered here is 2024-03-05, to get the portfolio value on that date as shown :

Enter the date for which you want to get the total price of the portfolio.
The date should be in this format yyyy-mm-dd:
2024-03-05

12. The total value of the Oldage fund for above entered date will be shown as follows:

Wait until the total value is calculated
The total value of portfolio is: $914.4000000000001

Main Menu
1. Create a portfolio
2. Load a portfolio
3. Get composition of a portfolio
4. Get total value of a portfolio for certain date
5. Save a portfolio
6. Exit
Enter your choice:
6

And after that again main menu will be printed for user to enter a new choice. Here in order to
exit the code, now we enter 6.

--- The stocks for which the program can run:
The stocks.csv file provided in the res folder has the complete stock list with the ticker symbol
and the stock name in order to validate the stock names/ ticker symbol entered by user while
adding the share name/ ticker symbol for creating portfolio or loading a file that already has
this data.

-- The dates for which the program can run:
There is no restriction provided on the dates for which the user can ask the value of the portfolio,
however, if the stock data is not found for a particular date which can be saturday/sunday,
then the message is printed that stock data not found for this date.

Also, for getting value of a portfolio which has certain stocks which weren't listed in the stock
data at that date then message will be passed that no data found for this stock name on this date,
and hence the value of portfolio won't be determined. If the value of that stock is to be found
for it even before its stock prices were listed.
