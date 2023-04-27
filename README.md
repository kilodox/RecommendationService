# RecommendationService
Here is a description for every created class:
1. RecommendationServiceApplication class:
    This class is the enty point for the application.
    In the first lines of the main method I am reading the crypto prices from a file in a specific directory.
    Then I calculate and print the statistics for the crypto prices in the console (max/min/new/old)
    So far this is the solution for the first 2 tasks.
    After that I am starting the Spring Boot application, which starts the web server and processes the requests to the application.
2. Crypto class
    This class stores all the information for the cryptos.
    It has: 
    -constuctor
    -getters, setters
    -a method named "addPriceTimestamp" which adds price and timestamp to a HashMap of prices
    -a method named "calculateOldNewMaxMinPrices" which updates the oldest timestamp and price, newest timestamp and price, minimum price, and maximum price of the crypto
    -"getNormalizedRange" and "setNormalizedRange" methods are used to get and set the normalized range of the cryptocurrency
3. CryptoDataReader class
    The purpose of this class is to read crypto data from csv files.
    Each csv file represents a specific cryptocurrency, and the file name is used as the symbol for that cryptocurrency.
    For each csv file, the class creates a new Crypto object and adds it to the list of cryptos. Then it reads the data from the csv file and adds each timestamp and price value of the cryptos to a HashMap of prices, using the addPriceTimestamp() method.
4. CryptoStatsCalculator class
    The purpose of this class is to provide calculation for the list of Cryptos and printing statistical information about these objects.
    The "calculateStats" method takes a list of Crypto objects and iterates over each one, calling the calculateOldNewMaxMinPrices method on each Crypto object. This method calculates and sets the oldest price, newest price, minimum price, and maximum price among all the Cryptos is the Hashmap of prices in the Crypto class.
    The "printStats" method iterates over each crypto object in the list and prints the symbol, old, new, min and max prices.
5.  CryptoController class
    This class is a REST controller that handles requests related to crypto statistics. It has two GET endpoints:
     1. /api/crypto-stats - This endpoint returns the normalized price range statistics for all the cryptocurrencies in the system. 
     It first reads the cryptocurrency data from a file, calculates the statistics, sorts the results by descending normalized range, 
     and returns the results in a map format.
      e.g.  http://localhost:8080/api/crypto-stats -> {"ETH":0.6383810110763016,"XRP":0.5060541310541311,"DOGE":0.5046511627906975,"LTC":0.4651837524177949,"BTC":0.43412110435594536}
         
     2.  /api/{symbol}/stats - This endpoint returns the price statistics for a specific cryptocurrency depending on its symbol. 
      It first reads the cryptocurrency data from a file, calculates the statistics, filters the result for the requested symbol, 
      and returns the statistics for the matching cryptocurrency in a map format.
        e.g. http://localhost:8080/api/BTC/stats -> {"newestPrice":38415.79,"minPrice":33276.59,"oldestPrice":46813.21,"maxPrice":47722.66}
