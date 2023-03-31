import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.regex.*;

import org.junit.*;

import static org.junit.Assert.*;
import junit.framework.JUnit4TestAdapter;

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
public class JUSafeTradeTest
{
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
    public void tradeOrderConstructor()
    {
        TradeOrder to = new TradeOrder( null, symbol, buyOrder, marketOrder,
            numShares, price );
        String toStr = to.toString();

        assertTrue( "<< Invalid TradeOrder Constructor >>",
                    toStr.contains( "TradeOrder[Trader trader:null" )
                        && toStr.contains( "java.lang.String symbol:" + symbol )
                        && toStr.contains( "boolean buyOrder:" + buyOrder )
                        && toStr.contains( "boolean marketOrder:" + marketOrder )
                        && toStr.contains( "int numShares:" + numShares )
                        && toStr.contains( "double price:" + price ) );
    }
    
    @Test
    public void TradeOrderToString()
    {
        TradeOrder to = new TradeOrder( null, symbol, buyOrder, marketOrder,
            numShares, price );
        assertNotNull( to.toString() );
    }

    @Test
    public void tradeOrderGetTrader()
    {
        TradeOrder to = new TradeOrder( null, symbol, buyOrder, marketOrder,
            numShares, price );
        assertNull( "<< TradeOrder: " + to.getTrader() + " should be null >>",
                    to.getTrader() );
    }

    @Test
    public void tradeOrderGetSymbol()
    {
        TradeOrder to = new TradeOrder( null, symbol, buyOrder, marketOrder,
            numShares, price );
        assertEquals( "<< TradeOrder: " + to.getTrader() + " should be "
            + symbol + " >>", symbol, to.getSymbol() );
    }

    @Test
    public void tradeOrderIsBuy()
    {
        TradeOrder to = new TradeOrder( null, symbol, buyOrder, marketOrder,
            numShares, price );

        assertTrue( "<< TradeOrder: " + to.isBuy() + " should be " + buyOrder
            + " >>", to.isBuy() );
    }

    @Test
    public void tradeOrderIsSell()
    {
        TradeOrder to = new TradeOrder( null, symbol, buyOrder, marketOrder,
            numShares, price );
        assertFalse( "<< TradeOrder: " + to.isSell() + " should be "
            + !buyOrder + " >>", to.isSell() );
    }

    @Test
    public void tradeOrderIsMarket()
    {
        TradeOrder to = new TradeOrder( null, symbol, buyOrder, marketOrder,
            numShares, price );
        assertTrue( "<< TradeOrder: " + to.isMarket() + " should be "
            + marketOrder + " >>", to.isMarket() );
    }

    @Test
    public void tradeOrderIsLimit()
    {
        TradeOrder to = new TradeOrder( null, symbol, buyOrder, marketOrder,
            numShares, price );

        assertFalse( "<< TradeOrder: " + to.isLimit() + " should be "
            + !marketOrder + ">>", to.isLimit() );
    }

    @Test
    public void tradeOrderGetShares()
    {
        TradeOrder to = new TradeOrder( null, symbol, buyOrder, marketOrder,
            numShares, price );
        assertTrue( "<< TradeOrder: " + to.getShares() + " should be "
            + numShares + ">>", numShares == to.getShares()
            || ( numShares - numToSubtract ) == to.getShares() );
    }

    @Test
    public void tradeOrderGetPrice()
    {
        TradeOrder to = new TradeOrder( null, symbol, buyOrder, marketOrder,
            numShares, price );
        assertEquals( "<< TradeOrder: " + to.getPrice() + " should be " + price
            + ">>", price, to.getPrice(), 0.0 );
    }

    @Test
    public void tradeOrderSubtractShares()
    {
        TradeOrder to = new TradeOrder( null, symbol, buyOrder, marketOrder,
            numShares, price );
        to.subtractShares( numToSubtract );
        assertEquals( "<< TradeOrder: subtractShares(" + numToSubtract
            + ") should be " + ( numShares - numToSubtract ) + ">>", numShares
            - numToSubtract, to.getShares() );
    }
    
    // --Test TraderWindow Stub
    @Test
    public void traderWindowConstructor()
    {
        TraderWindow tw = new TraderWindow( null );
        assertNotNull( tw );
    }

    @Test
    public void traderWindowShowMessage()
    {
        TraderWindow tw = new TraderWindow( null );
        assertNotNull( tw );
        tw.showMessage( null );
    }

    //  --Test PriceComparator
    
    // TODO your tests here


    // --Test Trader
    private String screenName = "Trader test";
    private String password = "123456";
    private StockExchange stock = new StockExchange();
    private Brokerage broke = new Brokerage(stock); 
    
    @Test
    public void traderConstructorTest()
    {
        Trader trade = new Trader(broke, screenName, password);
        String str = trade.toString();
        assertTrue( "<< Constructor for trader error:", str.contains("password") && str.contains(password) && str.contains("screenName") && str.contains(screenName));
    }    

    @Test
    public void getNameTraderTest()
    {
        Trader trade = new Trader(broke, screenName, password);
        assertEquals( "<< Trader getName error:", trade.getName(), "Trader test");
    }  

    @Test
    public void getPassTraderTest()
    {
        Trader trade = new Trader(broke, screenName, password);
        assertEquals( "<< Trader getPass error:", trade.getPassword(), "123456");
    }  

    @Test
    public void compareToTraderTest()
    {
        Trader trade = new Trader(broke, screenName, password);
        Trader other = new Trader(null, "Trader test", "123456");
        assertTrue( "<< Trader compareTo error:", trade.compareTo(other) != 0);
    }  
    
    @Test
    public void equalsTraderTest()
    {
        Trader trade = new Trader(broke, screenName, password);
        Trader other = new Trader(null, "Trader test", "123456");
        assertEquals( "<< Trader equals error:", trade.equals(other) == false);
    }  

    @Test
    public void hasMessagesTraderTest()
    {
        Trader trade = new Trader(new Brokerage(new StockExchange()), screenName, password);
        assertTrue( "<< Trader has no messages: ", !trade.hasMessages());
        trade.receiveMessage("hi");
        assertTrue( "<< Trader has messages, should be true:", trade.hasMessages());
    }

    @Test
    public void openWindowTraderTest()
    {
        Trader trade = new Trader(broke, screenName, password);
        trade.openWindow();
        assertTrue( "<< Trader has no messages", !trade.hasMessages());
    }

    @Test
    public void recieveMessageTraderTest()
    {
        Trader trade = new Trader(new Brokerage(new StockExchange()), screenName, password);
        trade.receiveMessage("hi");
        assertTrue( "<< Trader has messages", trade.hasMessages());
    }

    @Test
    public void getQuoteTraderTest()
    {
        Trader trade = new Trader(new Brokerage(new StockExchange()), screenName, password);
        trade.getQuote(symbol);
        assertTrue( "<< Trader getQuote: ", trade.hasMessages());
        trade.openWindow();
        trade.getQuote(symbol);
        assertTrue( "<< Trader getQuote: ", !trade.hasMessages());
    }

    @Test
    public void placeOrderTraderTest()
    {
        Trader trade = new Trader(new Brokerage(new StockExchange()), screenName, password);
        TradeOrder tradeOrder = new TradeOrder(trade, symbol, buyOrder, marketOrder, numShares, price);
        assertTrue( "<< Trader has messages, should be true: ", trade.hasMessages());
    }

    @Test
    public void quitTraderTest()
    {
        Brokerage b = new Brokerage(new StockExchange();
        Trader trade = new Trader(b, screenName, password);
        trade.quit();
        assertEquals( "<< Trader is logged out: ", b.getLoggedTraders.contains(trade) != true);
    }



    
    // --Test Brokerage
    
    // TODO your tests here
    
    
    // --Test StockExchange
    
    // TODO your tests here
    
    
    // --Test Stock
    
    // TODO your tests here

    
    // Remove block comment below to run JUnit test in console
/*
    public static junit.framework.Test suite()
    {
        return new JUnit4TestAdapter( JUSafeTradeTest.class );
    }
    
    public static void main( String args[] )
    {
        org.junit.runner.JUnitCore.main( "JUSafeTradeTest" );
    }
*/
}

