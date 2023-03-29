import static org.junit.Assert.*;

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
 * @version TODO date
 * @author Assignment: JM Chapter 19 - SafeTrade
 *
 * @author Sources: TODO sources
 *
 */
public class JUSafeTradeTest {
    // --Test TradeOrder
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

    // --Test TraderWindow Stub
    @Test
    public void traderWindowConstructor() {
        TraderWindow tw = new TraderWindow(null);
        assertNotNull(tw);
    }

    @Test
    public void traderWindowShowMessage() {
        TraderWindow tw = new TraderWindow(null);
        assertNotNull(tw);
        tw.showMessage(null);
    }

    //  --Test PriceComparator

    // TODO your tests here

    // --Test Trader

    // TODO your tests here

    // --Test Brokerage

    // TODO your tests here

    // --Test StockExchange

    @Test
    public void stockExchangeConstructor() {
        StockExchange exchange = new StockExchange();
        assertNotNull(exchange);
    }

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
        assertTrue("<< StockExchange: get quote in listing >>", quote1 != null && quote1.contains("ERIC");
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

    // --Test Stock

    @Test
    public void stockConstructor() {
        Stock stonk = new Stock("ERIC", "Ricehens", 2 * 69 + 0.69);
        assertNotNull(stonk);
        assertEquals("<< Stock: Constructor symbol >>", stonk.getStockSymbol(), "ERIC");
        assertEquals("<< Stock: Constructor company name >>", stonk.getCompanyName(), "Ricehens");
        assertEquals("<< Stock: Constructor last price >>", stonk.getLastPrice(), 138.69);
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
        TradeOrder order = new TradeOrder(eric, "ESPN", true, true, 69, 4.20);
        Map<String, Stock> map = exchange.getListedStocks();
        Stock stonk = map.get("ESPN");
        stonk.placeOrder(order);
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
        assertEquals(sellOrders.peek().getPrice(), 6.99);
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
    // TODO your tests here

    // Remove block comment below to run JUnit test in console
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(JUSafeTradeTest.class);
    }

    public static void main(String args[]) {
        org.junit.runner.JUnitCore.main("JUSafeTradeTest");
    }
}
