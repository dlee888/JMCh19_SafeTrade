import static org.junit.Assert.*;
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

    @Test
    public void brokerageConstructor() {
        StockExchange exchange = new StockExchange();
        Brokerage myBrokerage = new Brokerage(exchange);
        assertNotNull(myBrokerage);
        assertEquals(myBrokerage.getExchange(), exchange);
        assertTrue(myBrokerage.getLoggedTraders().isEmpty());
        assertTrue(myBrokerage.getTraders().isEmpty());
    }

    @Test
    public void brokerageAddUser() {
        StockExchange exchange = new StockExchange();
        Brokerage myBrokerage = new Brokerage(exchange);

        assertEquals(myBrokerage.addUser("LoremIpsum", "E"), -2);
        assertEquals(myBrokerage.addUser("LoremIpsum", "ExtremeSecurity"), -2);
        assertEquals(myBrokerage.addUser("LoremIpsum", "Secure"), 0);
        assertEquals(myBrokerage.addUser("Bob", "Secure"), -1);
        assertEquals(myBrokerage.addUser("Lorem Ipsum", "Secure"), -1);
        assertEquals(myBrokerage.addUser("LoremIpsum", "secure"), -3);
        assertEquals(myBrokerage.addUser("1to2", "12"), 0);
        assertEquals(myBrokerage.addUser("Jerry", "1234567890"), 0);
        assertEquals(myBrokerage.getTraders().size(), 3);
    }

    @Test
    public void brokerageLogin() {
        StockExchange exchange = new StockExchange();
        Brokerage myBrokerage = new Brokerage(exchange);

        myBrokerage.addUser("LoremIpsum", "Secure");
        myBrokerage.addUser("Jerry", "1234567890");

        assertEquals(myBrokerage.login("fndskl", "hfdgs"), -1);
        assertEquals(myBrokerage.login("fndskl", "Secure"), -1);
        assertEquals(myBrokerage.login("LoremIpsum", "hfdgs"), -2);
        assertEquals(myBrokerage.login("LoremIpsum", "Secure"), 0);
        assertEquals(myBrokerage.login("Jerry", "1234567890"), 0);
        assertEquals(myBrokerage.login("LoremIpsum", "Secure"), -3);
        assertEquals(myBrokerage.login("Jerry", "1234567890"), -3);
        assertEquals(myBrokerage.getLoggedTraders().size(), 2);
    }

    @Test
    public void brokerageLogout() {
        StockExchange exchange = new StockExchange();
        Brokerage myBrokerage = new Brokerage(exchange);

        myBrokerage.addUser("LoremIpsum", "Secure");
        myBrokerage.addUser("Jerry", "1234567890");

        assertEquals(myBrokerage.getLoggedTraders().size(), 2);
    }

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
        System.out.println(exchange);
    }

    // --Test Stock

    // TODO your tests here

    // Remove block comment below to run JUnit test in console
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(JUSafeTradeTest.class);
    }

    public static void main(String args[]) {
        org.junit.runner.JUnitCore.main("JUSafeTradeTest");
    }
}
