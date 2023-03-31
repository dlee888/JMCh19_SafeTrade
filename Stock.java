import java.lang.reflect.*;
import java.text.DecimalFormat;
import java.util.*;

/**
 * Represents a stock in the SafeTrade project
 *
 * @version 3/31/2023
 *
 * @author David
 */
public class Stock {
    /**
     * Decimal format for money...
     */
    public static DecimalFormat money = new DecimalFormat("0.00");

    private String stockSymbol;
    private String companyName;
    private double loPrice;
    private double hiPrice;
    private double lastPrice;
    private int volume;
    private PriorityQueue<TradeOrder> buyOrders;
    private PriorityQueue<TradeOrder> sellOrders;

    /**
     * Constructs a new stock with a given symbol, company name,
     * and starting price. Sets low price, high price,
     * and last price to the same opening price.
     * Sets "day" volume to zero.
     * Initializes a priority queue for sell orders
     * to an empty <code>PriorityQueue</code> with a
     * <code>PriceComparator</code> configured for comparing orders in ascending
     * order; initializes a priority queue for buy orders to an empty
     * <code>PriorityQueue</code> with a <code>PriceComparator</code> configured
     * for comparing orders in descending order.
     *
     * @param symbol the stock symbol.
     * @param name full company name.
     * @param price the opening price.
     */
    public Stock(String symbol, String name, double price)
    {
        stockSymbol = symbol;
        companyName = name;
        loPrice = price;
        hiPrice = price;
        lastPrice = price;
        volume = 0;
        buyOrders =
            new PriorityQueue<TradeOrder>(11, new PriceComparator(false));
        sellOrders =
            new PriorityQueue<TradeOrder>(11, new PriceComparator(true));
    }

    /**
     * Returns a quote string for this stock. The quote includes:
     * the company name for this stock; the stock symbol; last sale price;
     * the lowest and highest day prices; the lowest price in
     * a sell order (or "market") and the number of shares in it
     * (or "none" if there are no sell orders); the highest
     * price in a buy order (or "market") and the number of shares in it
     * (or "none" if there are no buy orders).
     * For example:<pre>
     * Giggle.com (GGGL)
     * Price: 10.00  hi: 10.00  lo: 10.00  vol: 0
     * Ask: 12.75 size: 300  Bid: 12.00 size: 500</pre>Or:<pre>
     * Giggle.com (GGGL)
     * Price: 12.00  hi: 14.50  lo: 9.00  vol: 500
     * Ask: none  Bid: 12.50 size: 200</pre>
     * @return the quote for this stock.
     */
    public String getQuote()
    {
        return String.format(
            "%s (%s)\nPrice: %.2f  hi: %.2f  lo: %.2f  vol: %d\nAsk: %s  Bid: %s",
            companyName, stockSymbol, lastPrice, hiPrice, loPrice, volume,
            formatOrder(sellOrders.peek()), formatOrder(buyOrders.peek()));
    }

    /**
     * Helper method to format an order. Returns "none" if no order
     * @param order
     * @return a string describing the order.
     */
    private String formatOrder(TradeOrder order)
    {
        if (order == null) {
            return "none";
        }
        return String.format("%.2f size: %d", order.getPrice(),
                             order.getShares());
    }

    /**
     * Places a trading order for this stock.
     * Adds the order to the appropriate priority queue depending
     * on whether this is a buy or sell order.
     * Notifies the trader
     * who placed the order that the order has been placed, by sending a
     * message to that trader. For example:
     * <pre>
     * New order:  Buy GGGL (Giggle.com)
     * 200 shares at $38.00</pre>
     * Or, for market orders:
     * <pre>
     * New order:  Sell GGGL (Giggle.com)
     * 150 shares at market</pre>
     * Executes pending orders by calling
     * <code>executeOrders</code>.
     *
     * @param order the trade order to be placed
     */
    public void placeOrder(TradeOrder order)
    {
        if (order.isBuy()) {
            buyOrders.add(order);
        }
        else {
            assert (order.isSell());
            sellOrders.add(order);
        }
        order.getTrader().receiveMessage(String.format(
            "New order:  %s %s (%s)\n%d shares at %s",
            order.isBuy() ? "Buy" : "Sell", stockSymbol, companyName,
            order.getShares(),
            order.isMarket() ? "market"
                             : String.format("%.2f", order.getPrice())));
        executeOrders();
    }

    /**
     * Executes as many pending orders as possible. <br>
     * <ol type="1">
     *   <li> Examines the top sell order and the top buy order in
     *      the respective priority queues.</li>
     * <ol type="i">
     *      <li>If both are limit orders and the buy order price is greater or
     * equal to the sell order price, executes the order (or a part of it) at
     * the sell order price.</li> <li>If one order is limit and the other is
     * market, executes the order (or a part of it) at the limit order
     * price</li> <li>If both orders are market, executes the order (or a part
     * of it) at the last sale price.</li>
     * </ol>
     *   <li> Figures out how many shares can be traded, which
     *      is the smallest of the numbers of shares in the two orders.</li>
     *   <li> Subtracts the traded number of shares from each order;
     *      Removes each of the orders with 0 remaining shares from the
     * respective queue.</li> <li> Updates the day's low price, high price, and
     * volume.</li> <li> Sends a message to each of the two traders involved in
     * the transaction. For example: <pre> You bought: 150 GGGL at 38.00 amt
     * 5700.00</pre>
     *
     *   <b>Note:</b> The dollar amounts should be formatted to two decimal
     * places (eg. 12.40, not 12.4)<br> <br> <li> Repeats steps 1-5 for as long
     * as possible, that is as long as there is any movement in the buy / sell
     * order queues. (The process gets stuck when the top buy order and sell
     * order are both limit orders and the ask price is higher than the bid
     *      price.)</li>
     * </ol>
     */
    public void executeOrders()
    {
        TradeOrder buyOrder = buyOrders.peek();
        TradeOrder sellOrder = sellOrders.peek();
        if (buyOrder == null || sellOrder == null) {
            return;
        }
        int sharesTraded =
            Math.min(buyOrder.getShares(), sellOrder.getShares());
        double price = 0;
        if (buyOrder.isLimit() && sellOrder.isLimit()) {
            price = sellOrder.getPrice();
            if (buyOrder.getPrice() < price) {
                return;
            }
        }
        else if (buyOrder.isLimit() && sellOrder.isMarket()) {
            price = buyOrder.getPrice();
        }
        else if (buyOrder.isMarket() && sellOrder.isLimit()) {
            price = sellOrder.getPrice();
        }
        else {
            price = lastPrice;
        }
        buyOrder.subtractShares(sharesTraded);
        sellOrder.subtractShares(sharesTraded);
        if (buyOrder.getShares() == 0) {
            buyOrders.remove();
        }
        if (sellOrder.getShares() == 0) {
            sellOrders.remove();
        }
        hiPrice = Math.max(hiPrice, price);
        loPrice = Math.min(loPrice, price);
        volume += sharesTraded;
        buyOrder.getTrader().receiveMessage(
            String.format("You bought: %d %s at %.2f amt %.2f", sharesTraded,
                          stockSymbol, price, sharesTraded * price));
        sellOrder.getTrader().receiveMessage(
            String.format("You sold: %d %s at %.2f amt %.2f", sharesTraded,
                          stockSymbol, price, sharesTraded * price));
        executeOrders();
    }

    //
    // The following are for test purposes only
    //

    /**
     * Getter method for the stock symbol
     * @return the stock symbol
     */
    protected String getStockSymbol()
    {
        return stockSymbol;
    }

    /**
     * Getter method for the company name
     * @return the company name
     */
    protected String getCompanyName()
    {
        return companyName;
    }

    /**
     * Getter method for the loPrice
     * @return the loPrice
     */
    protected double getLoPrice()
    {
        return loPrice;
    }

    /**
     * Getter method for the hiPrice
     * @return the hiPrice
     */
    protected double getHiPrice()
    {
        return hiPrice;
    }

    /**
     * Getter method for the lastPrice
     * @return the lastPrice
     */
    protected double getLastPrice()
    {
        return lastPrice;
    }

    /**
     * Getter method for the volume
     * @return the volume
     */
    protected int getVolume()
    {
        return volume;
    }

    /**
     * Getter method for the buyOrders
     * @return the buyOrders
     */
    protected PriorityQueue<TradeOrder> getBuyOrders()
    {
        return buyOrders;
    }

    /**
     * Getter method for the sellOrders
     * @return the sellOrders
     */
    protected PriorityQueue<TradeOrder> getSellOrders()
    {
        return sellOrders;
    }

    /**
     * <p>
     * A generic toString implementation that uses reflection to print names and
     * values of all fields <em>declared in this class</em>. Note that
     * superclass fields are left out of this implementation.
     * </p>
     *
     * @return a string representation of this Stock.
     */
    public String toString()
    {
        String str = this.getClass().getName() + "[";
        String separator = "";

        Field[] fields = this.getClass().getDeclaredFields();

        for (Field field : fields) {
            try {
                str += separator + field.getType().getName() + " " +
                       field.getName() + ":" + field.get(this);
            }
            catch (IllegalAccessException ex) {
                System.out.println(ex);
            }

            separator = ", ";
        }

        return str + "]";
    }
}
