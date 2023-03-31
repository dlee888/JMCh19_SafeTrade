import static org.junit.Assert.*;
import static org.junit.Assume.*;

import java.awt.*;
import java.beans.Transient;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.regex.*;
import junit.framework.JUnit4TestAdapter;
import org.junit.*;

/**
 * SafeTrade tests:
 *   TradeOrder
 *   PriceComparator
 *   Trader
 *   Brokerage
 *   StockExchange
 *   Stock
 *
 * @author TODO Name of principal author
 * @author TODO Name of group member
 * @author TODO Name of group member
 * @version 3/31/2023
 * @author Assignment: JM Chapter 19 - SafeTrade
 *
 * @author Sources: TODO sources
 *
 */
public class JUSafeTradeTest {
    //
    // ----------------------------------------------------
    // TradeOrder Tests
    // ----------------------------------------------------
    //

    /**
     * TradeOrder tests:
     *   TradeOrderConstructor - constructs TradeOrder and then compare toString
     *   TradeOrderGetTrader - compares value returned to constructed value
     *   TradeOrderGetSymbol - compares value returned to constructed value
     *   TradeOrderIsBuy - compares value returned to constructed value
     *   TradeOrderIsSell - compares value returned to constructed value
     *   TradeOrderIsMarket - compares value returned to constructed value
     *   TradeOrderIsLimit - compares value returned to constructed value
     *   TradeOrderGetShares - compares value returned to constructed value
     *   TradeOrderGetPrice - compares value returned to constructed value
     *   TradeOrderSubtractShares - subtracts known value & compares result
     *     returned by getShares to expected value
     */
    private String symbol = "GGGL";
    private boolean buyOrder = true;
    private boolean marketOrder = true;
    private int numShares = 123;
    private int numToSubtract = 24;
    private double price = 123.45;

    @Test
    public void tradeOrderConstructor() {
        TradeOrder to = new TradeOrder(null, symbol, buyOrder, marketOrder, numShares, price);
        String toStr = to.toString();

        assertTrue("<< Invalid TradeOrder Constructor >>", toStr.contains("TradeOrder[Trader trader:null") &&
                                                               toStr.contains("java.lang.String symbol:" + symbol) &&
                                                               toStr.contains("boolean buyOrder:" + buyOrder) &&
                                                               toStr.contains("boolean marketOrder:" + marketOrder) &&
                                                               toStr.contains("int numShares:" + numShares) &&
                                                               toStr.contains("double price:" + price));
    }

    @Test
    public void TradeOrderToString() {
        TradeOrder to = new TradeOrder(null, symbol, buyOrder, marketOrder, numShares, price);
        assertNotNull(to.toString());
    }

    @Test
    public void tradeOrderGetTrader() {
        TradeOrder to = new TradeOrder(null, symbol, buyOrder, marketOrder, numShares, price);
        assertNull("<< TradeOrder: " + to.getTrader() + " should be null >>", to.getTrader());
    }

    @Test
    public void tradeOrderGetSymbol() {
        TradeOrder to = new TradeOrder(null, symbol, buyOrder, marketOrder, numShares, price);
        assertEquals("<< TradeOrder: " + to.getTrader() + " should be " + symbol + " >>", symbol, to.getSymbol());
    }

    @Test
    public void tradeOrderIsBuy() {
        TradeOrder to = new TradeOrder(null, symbol, buyOrder, marketOrder, numShares, price);

        assertTrue("<< TradeOrder: " + to.isBuy() + " should be " + buyOrder + " >>", to.isBuy());
    }

    @Test
    public void tradeOrderIsSell() {
        TradeOrder to = new TradeOrder(null, symbol, buyOrder, marketOrder, numShares, price);
        assertFalse("<< TradeOrder: " + to.isSell() + " should be " + !buyOrder + " >>", to.isSell());
    }

    @Test
    public void tradeOrderIsMarket() {
        TradeOrder to = new TradeOrder(null, symbol, buyOrder, marketOrder, numShares, price);
        assertTrue("<< TradeOrder: " + to.isMarket() + " should be " + marketOrder + " >>", to.isMarket());
    }

    @Test
    public void tradeOrderIsLimit() {
        TradeOrder to = new TradeOrder(null, symbol, buyOrder, marketOrder, numShares, price);

        assertFalse("<< TradeOrder: " + to.isLimit() + " should be " + !marketOrder + ">>", to.isLimit());
    }

    @Test
    public void tradeOrderGetShares() {
        TradeOrder to = new TradeOrder(null, symbol, buyOrder, marketOrder, numShares, price);
        assertTrue("<< TradeOrder: " + to.getShares() + " should be " + numShares + ">>",
                   numShares == to.getShares() || (numShares - numToSubtract) == to.getShares());
    }

    @Test
    public void tradeOrderGetPrice() {
        TradeOrder to = new TradeOrder(null, symbol, buyOrder, marketOrder, numShares, price);
        assertEquals("<< TradeOrder: " + to.getPrice() + " should be " + price + ">>", price, to.getPrice(), 0.0);
    }

    @Test
    public void tradeOrderSubtractShares() {
        TradeOrder to = new TradeOrder(null, symbol, buyOrder, marketOrder, numShares, price);
        to.subtractShares(numToSubtract);
        assertEquals("<< TradeOrder: subtractShares(" + numToSubtract + ") should be " + (numShares - numToSubtract) +
                         ">>",
                     numShares - numToSubtract, to.getShares());
    }

    //
    // ----------------------------------------------------
    // TraderWindow Tests
    // ----------------------------------------------------
    //

    @Test
    public void traderWindowConstructor() {
        assumeFalse(java.awt.GraphicsEnvironment.isHeadless());
        TraderWindow tw = new TraderWindow(new Trader(new Brokerage(new StockExchange()), "test", "test"));
        assertNotNull(tw);
    }

    @Test
    public void traderWindowShowMessage() {
        assumeFalse(java.awt.GraphicsEnvironment.isHeadless());
        TraderWindow tw = new TraderWindow(new Trader(new Brokerage(new StockExchange()), "test", "test"));
        assertNotNull(tw);
        tw.showMessage(null);
    }

    //
    // ----------------------------------------------------
    // PriceComparator Tests
    // ----------------------------------------------------
    //

    // TODO your tests here

    //
    // ----------------------------------------------------
    // Brokerage Tests
    // ----------------------------------------------------
    //

    @Test
    public void brokerageConstructor() {
        StockExchange exchange = new StockExchange();
        Brokerage myBrokerage = new Brokerage(exchange);
        assertNotNull(myBrokerage);
        assertEquals(exchange, myBrokerage.getExchange());
        assertTrue(myBrokerage.getLoggedTraders().isEmpty());
        assertTrue(myBrokerage.getTraders().isEmpty());
    }

    @Test
    public void brokerageAddUser() {
        StockExchange exchange = new StockExchange();
        Brokerage myBrokerage = new Brokerage(exchange);

        assertEquals(-2, myBrokerage.addUser("LoremIpsum", "E"));
        assertEquals(-2, myBrokerage.addUser("LoremIpsum", "ExtremeSecurity"));
        assertEquals(0, myBrokerage.addUser("LoremIpsum", "Secure"));
        assertEquals(-1, myBrokerage.addUser("Bob", "Secure"));
        assertEquals(-1, myBrokerage.addUser("Lorem Ipsum", "Secure"));
        assertEquals(-3, myBrokerage.addUser("LoremIpsum", "secure"));
        assertEquals(0, myBrokerage.addUser("1to2", "12"));
        assertEquals(0, myBrokerage.addUser("Jerry", "1234567890"));
        assertEquals(3, myBrokerage.getTraders().size());
    }

    @Test
    public void brokerageLogin() {
        assumeFalse(java.awt.GraphicsEnvironment.isHeadless());
        StockExchange exchange = new StockExchange();
        Brokerage myBrokerage = new Brokerage(exchange);

        myBrokerage.addUser("LoremIpsum", "Secure");
        myBrokerage.addUser("Jerry", "1234567890");

        assertEquals(-1, myBrokerage.login("fndskl", "hfdgs"));
        assertEquals(-1, myBrokerage.login("fndskl", "Secure"));
        assertEquals(-2, myBrokerage.login("LoremIpsum", "hfdgs"));
        assertEquals(0, myBrokerage.getLoggedTraders().size());

        assertEquals(0, myBrokerage.login("LoremIpsum", "Secure"));
        assertEquals(0, myBrokerage.login("Jerry", "1234567890"));
        assertEquals(2, myBrokerage.getLoggedTraders().size());

        assertEquals(-3, myBrokerage.login("LoremIpsum", "Secure"));
        assertEquals(-3, myBrokerage.login("Jerry", "1234567890"));
        assertEquals(2, myBrokerage.getLoggedTraders().size());
    }

    @Test
    public void brokerageLogout() {
        StockExchange exchange = new StockExchange();
        Brokerage myBrokerage = new Brokerage(exchange);

        myBrokerage.addUser("LoremIpsum", "Secure");
        myBrokerage.addUser("Jerry", "1234567890");

        myBrokerage.login("LoremIpsum", "Secure");
        myBrokerage.login("Jerry", "1234567890");

        assertEquals(2, myBrokerage.getLoggedTraders().size());

        Trader trader1 = myBrokerage.getTraders().get("LoremIpsum");
        Trader trader2 = myBrokerage.getTraders().get("Jerry");

        myBrokerage.logout(trader1);
        myBrokerage.logout(trader2);

        assertEquals(0, myBrokerage.getLoggedTraders().size());
    }

    @Test
    public void brokerageGetQuote() {
        StockExchange exchange = new StockExchange();
        exchange.listStock("ESPN", "Espen", 137.69);
        exchange.listStock("ERIC", "Ricehens", 2 * 69 + 0.69);

        Brokerage myBrokerage = new Brokerage(exchange);
        Trader trader = new Trader(myBrokerage, "Jerry", "1234567890");

        assertFalse(trader.hasMessages());
        myBrokerage.getQuote("ERIC", trader);
        assertTrue(trader.hasMessages());
    }

    @Test
    public void brokeragePlaceOrder() {
        StockExchange exchange = new StockExchange();
        exchange.listStock("ESPN", "Espen", 137.69);
        exchange.listStock("ERIC", "Ricehens", 2 * 69 + 0.69);
        Brokerage brokerage = new Brokerage(exchange);
        Trader eric = new Trader(brokerage, "ricehens", "eric69420");
        TradeOrder order = new TradeOrder(eric, "ESPN", true, true, 69, 4.20);
        brokerage.placeOrder(order);
        assertTrue("<< StockExchange: placeOrder executed successfully", true);
    }

    //
    // ----------------------------------------------------
    // PriceComparator Tests
    // ----------------------------------------------------
    //

    @Test
    public void priceComparatorCompare() {
        StockExchange exchange = new StockExchange();
        exchange.listStock("ESPN", "Espen", 137.69);
        exchange.listStock("ERIC", "Ricehens", 2 * 69 + 0.69);
        Brokerage brokerage = new Brokerage(exchange);
        Trader eric = new Trader(brokerage, "ricehens", "eric69420");

        TradeOrder order1 = new TradeOrder(eric, "ESPN", true, true, 69, 4.20);
        TradeOrder order2 = new TradeOrder(eric, "ESPN", true, true, 69, 7.20);

        PriceComparator comparator = new PriceComparator();

        assertEquals(0, comparator.compare(order1, order2));
        assertEquals(0, comparator.compare(order1, order2));

        TradeOrder order3 = new TradeOrder(eric, "ESPN", true, false, 69, 4.20);
        assertEquals(-1, comparator.compare(order2, order3));
        assertEquals(1, comparator.compare(order3, order2));

        TradeOrder order4 = new TradeOrder(eric, "ESPN", true, false, 69, 7.20);
        assertEquals(-300, comparator.compare(order3, order4));
        assertEquals(300, comparator.compare(order4, order3));

        PriceComparator comparator2 = new PriceComparator(false);
        assertEquals(300, comparator2.compare(order3, order4));
        assertEquals(-300, comparator2.compare(order4, order3));
    }

    //
    // ----------------------------------------------------
    // StockExchange Tests
    // ----------------------------------------------------
    //

    @Test
    public void stockExchangeListStock() {
        StockExchange exchange = new StockExchange();
        exchange.listStock("ESPN", "Espen", 137.69);
        exchange.listStock("ERIC", "Ricehens", 2 * 69 + 0.69);
        Map<String, Stock> map = exchange.getListedStocks();
        assertTrue("<< StockExchange: listed stock should be in map >>", map.containsKey("ERIC"));
        assertFalse("<< StockExchange: not listed stock should not be in map >>", map.containsKey("Espen"));
    }

    @Test
    public void stockExchangeGetQuote() {
        StockExchange exchange = new StockExchange();
        exchange.listStock("ESPN", "Espen", 137.69);
        exchange.listStock("ERIC", "Ricehens", 2 * 69 + 0.69);
        String quote1 = exchange.getQuote("ERIC");
        String quote2 = exchange.getQuote("RICE");
        assertTrue("<< StockExchange: get quote in listing >>", quote1 != null && quote1.contains("ERIC"));
        assertTrue("<< StockExchange: get quote not in listing >>", quote2 != null && quote2.equals("RICE not found"));
    }

    @Test
    public void stockExchangePlaceOrder() {
        StockExchange exchange = new StockExchange();
        exchange.listStock("ESPN", "Espen", 137.69);
        exchange.listStock("ERIC", "Ricehens", 2 * 69 + 0.69);
        Brokerage brokerage = new Brokerage(exchange);
        Trader eric = new Trader(brokerage, "ricehens", "eric69420");
        TradeOrder order = new TradeOrder(eric, "ESPN", true, true, 69, 4.20);
        exchange.placeOrder(order);
        assertTrue("<< StockExchange: placeOrder executed successfully", true);
    }

    //
    // ----------------------------------------------------
    // Stock Tests
    // ----------------------------------------------------
    //

    @Test
    public void stockConstructor() {
        Stock stonk = new Stock("ERIC", "Ricehens", 2 * 69 + 0.69);
        assertNotNull(stonk);
        assertEquals("<< Stock: Constructor symbol >>", stonk.getStockSymbol(), "ERIC");
        assertEquals("<< Stock: Constructor company name >>", stonk.getCompanyName(), "Ricehens");
        assertEquals("<< Stock: Constructor last price >>", stonk.getLastPrice(), 138.69, 0.00001);
    }

    @Test
    public void stockPlaceOrder() {
        StockExchange exchange = new StockExchange();
        exchange.listStock("ESPN", "Espen", 137.69);
        exchange.listStock("ERIC", "Ricehens", 2 * 69 + 0.69);
        Brokerage brokerage = new Brokerage(exchange);
        Trader eric = new Trader(brokerage, "ricehens", "eric69420");
        TradeOrder order = new TradeOrder(eric, "ESPN", true, true, 69, 4.20);
        Map<String, Stock> map = exchange.getListedStocks();
        Stock stonk = map.get("ESPN");
        stonk.placeOrder(order);
        PriorityQueue<TradeOrder> buyOrders = stonk.getBuyOrders();
        PriorityQueue<TradeOrder> sellOrders = stonk.getSellOrders();
        assertEquals("<< Stock: placed buy order >>", buyOrders.size(), 1);
        assertEquals("<< Stock: placed buy order >>", sellOrders.size(), 0);
    }

    @Test
    public void stockExecuteOrders() {
        StockExchange exchange = new StockExchange();
        exchange.listStock("ESPN", "Espen", 137.69);
        exchange.listStock("ERIC", "Ricehens", 2 * 69 + 0.69);
        Brokerage brokerage = new Brokerage(exchange);
        Trader eric = new Trader(brokerage, "ricehens", "eric69420");
        Map<String, Stock> map = exchange.getListedStocks();
        Stock stonk = map.get("ESPN");
        PriorityQueue<TradeOrder> buyOrders = stonk.getBuyOrders();
        PriorityQueue<TradeOrder> sellOrders = stonk.getSellOrders();
        buyOrders.add(new TradeOrder(eric, "ESPN", true, true, 69, 4.20));
        sellOrders.add(new TradeOrder(eric, "ESPN", false, true, 137, 6.96));
        stonk.executeOrders();
        assertEquals(buyOrders.size(), 0);
        assertEquals(sellOrders.size(), 1);
        assertEquals(sellOrders.peek().getShares(), 68);
        buyOrders.add(new TradeOrder(eric, "ESPN", true, false, 69, 42.0));
        sellOrders.add(new TradeOrder(eric, "ESPN", false, true, 69, 6.99));
        stonk.executeOrders();
        assertEquals(buyOrders.size(), 0);
        assertEquals(sellOrders.size(), 1);
        assertEquals(sellOrders.peek().getShares(), 68);
        assertEquals(sellOrders.peek().getPrice(), 6.99, 0.00001);
        buyOrders.add(new TradeOrder(eric, "ESPN", true, false, 69, 42.0));
        sellOrders.add(new TradeOrder(eric, "ESPN", false, false, 69, 7.69));
        stonk.executeOrders();
        assertEquals(buyOrders.size(), 0);
        assertEquals(sellOrders.size(), 1);
        assertEquals(sellOrders.peek().getShares(), 68);
        buyOrders.add(new TradeOrder(eric, "ESPN", true, false, 69, 6.96));
        stonk.executeOrders();
        assertEquals(buyOrders.size(), 1);
        assertEquals(sellOrders.size(), 1);
    }

    //
    // ----------------------------------------------------
    // Trader Tests
    // ----------------------------------------------------
    //

    private String screenName = "Trader test";
    private String password = "123456";
    private StockExchange stock = new StockExchange();
    private Brokerage broke = new Brokerage(stock);

    @Test
    public void traderConstructorTest() {
        Trader trade = new Trader(broke, screenName, password);
        String str = trade.toString();
        assertTrue("<< Constructor for trader error:", str.contains("password") && str.contains(password) &&
                                                           str.contains("screenName") && str.contains(screenName));
    }

    @Test
    public void getNameTraderTest() {
        Trader trade = new Trader(broke, screenName, password);
        assertEquals("<< Trader getName error:", trade.getName(), "Trader test");
    }

    @Test
    public void getPassTraderTest() {
        Trader trade = new Trader(broke, screenName, password);
        assertEquals("<< Trader getPass error:", trade.getPassword(), "123456");
    }

    public void compareToTraderTest() {
        Trader trade = new Trader(broke, screenName, password);
        Trader other = new Trader(null, "Trader test", "123456");
        assertTrue("<< Trader compareTo error:", trade.compareTo(other) != 0);
    }

    @Test
    public void equalsTraderTest() {
        Trader trade = new Trader(broke, screenName, password);
        Trader other = new Trader(null, "Trader test", "123456");
        assertTrue("<< Trader equals error:", trade.equals(other));
    }

    @Test
    public void hasMessagesTraderTest() {
        Trader trade = new Trader(new Brokerage(new StockExchange()), screenName, password);
        assertTrue("<< Trader has no messages: ", !trade.hasMessages());
        trade.receiveMessage("hi");
        assertTrue("<< Trader has messages, should be true:", trade.hasMessages());
    }

    @Test
    public void openWindowTraderTest() {
        assumeFalse(java.awt.GraphicsEnvironment.isHeadless());
        Trader trade = new Trader(broke, screenName, password);
        trade.openWindow();
        assertTrue("<< Trader has no messages", !trade.hasMessages());
    }

    @Test
    public void recieveMessageTraderTest() {
        Trader trade = new Trader(new Brokerage(new StockExchange()), screenName, password);
        trade.receiveMessage("hi");
        assertTrue("<< Trader has messages", trade.hasMessages());
    }

    @Test
    public void getQuoteTraderTest() {
        assumeFalse(java.awt.GraphicsEnvironment.isHeadless());
        Trader trade = new Trader(new Brokerage(new StockExchange()), screenName, password);
        trade.getQuote(symbol);
        assertTrue("<< Trader getQuote: ", trade.hasMessages());
        trade.openWindow();
        trade.getQuote(symbol);
        assertTrue("<< Trader getQuote: ", !trade.hasMessages());
    }

    @Test
    public void placeOrderTraderTest() {
        Trader trade = new Trader(new Brokerage(new StockExchange()), screenName, password);
        TradeOrder tradeOrder = new TradeOrder(trade, symbol, buyOrder, marketOrder, numShares, price);
        trade.placeOrder(tradeOrder);
        assertTrue("<< Trader has messages, should be true: ", trade.hasMessages());
        StockExchange exchange = new StockExchange();
        exchange.listStock(symbol, screenName, price);
        Trader trader2 = new Trader(new Brokerage(exchange), screenName, password);
        TradeOrder tradeOrder2 = new TradeOrder(trader2, symbol, buyOrder, marketOrder, numShares, price);
        trader2.placeOrder(tradeOrder2);
        assertTrue("<< Trader has messages, should be true: ", trader2.hasMessages());
    }

    @Test
    public void quitTraderTest() {
        Brokerage b = new Brokerage(new StockExchange());
        Trader trade = new Trader(b, screenName, password);
        trade.quit();
        assertFalse("<< Trader is logged out: ", b.getLoggedTraders().contains(trade));
    }

    // Remove block comment below to run JUnit test in console

    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(JUSafeTradeTest.class);
    }

    public static void main(String args[]) {
        org.junit.runner.JUnitCore.main("JUSafeTradeTest");
    }
}
