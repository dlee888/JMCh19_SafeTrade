import java.lang.reflect.*;
import java.util.*;

/**
 * Represents a stock trader.
 * @author Yvonne
 */
public class Trader implements Comparable<Trader> {
    private Brokerage brokerage;
    private String screenName, password;
    private TraderWindow myWindow;
    private Queue<String> mailbox;

    /**
     * This is the trader constructor
     * @param brokerage
     *              This is the brokerage parameter
     * @param name
     *             This is the given name
     * @param pswd
     *          This is the password given
     */
    public Trader(Brokerage brokerage, java.lang.String name, java.lang.String pswd) {
        this.brokerage = brokerage;
        screenName = name;
        password = pswd;
        mailbox = new LinkedList<String>();
    }

    /**
     * This gets the name
     * @return
     *      The Name
     */
    public String getName() //
    {
        return screenName;
    }

    /**
     * This gets the password
     * @return
     *      the password
     */
    public String getPassword() //
    {
        return password;
    }

    /**
     * this gets the quote
     * @param symbol
     *              The requested quote
     */
    public void getQuote(String symbol) {
        brokerage.getQuote(symbol, this);
    }

    /**
     * Checks if mailbox has Messages
     * @return
     *       Return true if mail is empty, false otherwise
     */
    public boolean hasMessages() //
    {
        return (!mailbox.isEmpty());
    }

    /**
     * Compare name to eachother but ignore case
     * @return
     *         -1 if less than, 0, if equal, 1 if greater than
     */
    public int compareTo(Trader other) //
    {
        return (screenName.toLowerCase()).compareTo((other.getName()).toLowerCase());
    }

    /**
     * Checks if traders are equal or not
     * @param other
     *          compare name to other's name
     * @return
     *        Returns true if names are equal, false otherwise
     */
    public boolean equals(Trader other) //
    {
        return (screenName.compareToIgnoreCase(other.getName()) == 0);
    }

    /**
     * Opens the winder
     */
    public void openWindow() {
        if (java.awt.GraphicsEnvironment.isHeadless()) {
            return;
        }
        myWindow = new TraderWindow(this);
        while (mailbox.peek() != null) {
            myWindow.showMessage(mailbox.remove());
        }
    }

    /**
     * Places an order by cakkubg brokerage's place order
     * @param order
     *          the order you want to place
     */
    public void placeOrder(TradeOrder order) {
        brokerage.placeOrder(order);
    }

    /**
     * Quits from the window
     */
    public void quit() {
        brokerage.logout(this);
        myWindow = null;
    }

    /*
     * Adds the message to mailbox and also calls show message on each one of them
     */
    public void receiveMessage(java.lang.String msg) {
        mailbox.add(msg);
        if (myWindow != null) {
            while (!mailbox.isEmpty()) {
                myWindow.showMessage(mailbox.remove());
            }
        }
    }

    //
    // The following are for test purposes only
    //
    protected Queue<String> mailbox() {
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
    public String toString() {
        String str = this.getClass().getName() + "[";
        String separator = "";

        Field[] fields = this.getClass().getDeclaredFields();

        for (Field field : fields) {
            try {
                if (field.getType().getName().equals("Brokerage"))
                    str += separator + field.getType().getName() + " " + field.getName();
                else
                    str += separator + field.getType().getName() + " " + field.getName() + ":" + field.get(this);
            } catch (IllegalAccessException ex) {
                System.out.println(ex);
            }

            separator = ", ";
        }

        return str + "]";
    }
}
