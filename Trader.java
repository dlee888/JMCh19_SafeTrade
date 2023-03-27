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

    public String getName() {
        return screenName;
    }

    public String getPassword() {
        return password;
    }

    public void getQuote(String symbol) {
        getQuote(symbol);
    }

    public boolean hasMessages() {
        return (!mailbox.isEmpty());
    }

    public int compareTo(Trader other) {
        return (screenName.toLowerCase()).compareTo((other.getName()).toLowerCase());
    }

    public boolean equals(Trader other) {
        return (screenName.compareTo(other.getName()) == 0);
    }

    public void openWindow() {
        myWindow = new TraderWindow(null);
    }

    public void placeOrder(TradeOrder order) {
        placeOrder(order);
    }

    public void quit() {
        logout();
        myWindow = null;
    }

    public void recieveMessage(java.lang.String msg) {
        mailbox.add(msg);
        for (String s : mailbox) {
            System.out.println(s);
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
