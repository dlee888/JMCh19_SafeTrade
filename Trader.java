import java.lang.reflect.*;
import java.util.*;

/**
 * Represents a stock trader.
 * @author Yvonne
 */
public class Trader implements Comparable<Trader>
{
    private Brokerage brokerage;
    private String screenName, password;
    private TraderWindow myWindow;
    private Queue<String> mailbox;

    public Trader(Brokerage brokerage, java.lang.String name, java.lang.String pswd)
    {
        this.brokerage = brokerage;
        screenName = name;
        password = pswd;
    }


    public String getName() //
    {
        return screenName;
    }
    
    public String getPassword() //
    {
        return password;
    }

    public void getQuote(String symbol) 
    {
        brokerage.getQuote(symbol, this);
    }

    public boolean hasMessages() //
    {
        return(!mailbox.isEmpty());
    }

    public int compareTo(Trader other) //
    {
        return (screenName.toLowerCase()).compareTo((other.getName()).toLowerCase());
    }

    public boolean equals(Trader other) //
    {
        return (screenName.compareToIgnoreCase(other.getName()) == 0);
    }

    public void openWindow()
    {
        myWindow = new TraderWindow(this);
        while(mailbox.peek() != null)
        {
            myWindow.showMessage(mailbox.remove());
        }
    }

    public void placeOrder(TradeOrder order)
    {
        brokerage.placeOrder(order);
    }

    public void quit()
    {
        brokerage.logout(this);
        myWindow = null;
    }

    public void receiveMessage(java.lang.String msg)
    {
        mailbox.add(msg);
        if(myWindow != null)
        {
            while(!mailbox.isEmpty())
            {
                myWindow.showMessage(mailbox.remove());
            }
        }
    }




    //
    // The following are for test purposes only
    //
    protected Queue<String> mailbox()
    {
        return mailbox;
    }
    
    /**
     * <p>
     * A generic toString implementation that uses reflection to print names and
     * values of all fields <em>declared in this class</em>. Note that
     * superclass fields are left out of this implementation.
     * </p>
     * 
     * @return a string representation of this Trader.
     */
    public String toString()
    {
        String str = this.getClass().getName() + "[";
        String separator = "";

        Field[] fields = this.getClass().getDeclaredFields();

        for ( Field field : fields )
        {
            try
            {
                if ( field.getType().getName().equals( "Brokerage" ) )
                    str += separator + field.getType().getName() + " "
                        + field.getName();
                else
                    str += separator + field.getType().getName() + " "
                        + field.getName() + ":" + field.get( this );
            }
            catch ( IllegalAccessException ex )
            {
                System.out.println( ex );
            }

            separator = ", ";
        }

        return str + "]";
    }
}
