
SETUP FOR RUNNING THE JAR FILE:

The jar file should be present in the folder along with stocks.csv that has the list of stock names
along with the ticker symbol that is used in the program to verify if the stock name or ticker
symbol entered by the user is valid or not. 
Additionally, the project libraries should also contain
the jdatepicker-1.3.4.jar file for using the calendar based date picker in GUI, for asking the users
to select date. The jar file is present in the res folder along with the stocks.csv file.

For running the GUI Based view the user can simply run the jar file by the command 
java -jar Filename.jar 
and the default view opened for user access is the GUI Based view investment application.
Further, to use the text based view
the user has to enter an additional extension along with the jar that is -text :
java -jar Filename.jar -text
for getting the text based view, which works as the controller related to the text based view is
called, along with text view, using the execute method.
Further additionally to get the GUI based view through the terminal also the user can enter -gui along with
the .jar file extension to get the GUI based view. The GUI based view is called when the controller
related to GUI is used along with GUI view, using the set view method.

--- The stocks for which the program can run:
The stocks.csv file provided in the res folder has the complete stock list with the ticker symbol
and the stock name in order to validate the stock names/ ticker symbol entered by user while
adding the share name/ ticker symbol for creating portfolio/ loading a file/ getting stock statistics
or any other method that takes in ticker symbol or stock name as input.

-- The dates for which the program can run:
There is no restriction provided on the dates for which the user can ask the value of the portfolio,
however, if the stock data is not found for a particular date which can be saturday/sunday/holiday,
then the message is printed that stock data not found for this date.

Also, for getting value of a portfolio which has certain stocks which weren't listed in the stock
data at that date then message will be passed that no data found for this stock name on this date,
and hence the value of inflexible portfolio won't be determined. If the value of that stock is to be found
for it even before its stock prices were listed.

For flexible portfolio, the user won't be able to buy stocks before the listing date of stocks.
For other functionalities such as stock statistics and performance the user can enter the end date
for period only until current date, and not enter any future date.

There are also some cases for which stock data won't be available on a certain date due to holiday, in such
cases the date is taken to be one day prior or after based on the method's implementation which
take a time period, (start date and end date) from user as input.
