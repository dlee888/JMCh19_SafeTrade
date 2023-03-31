/**
 * A price comparator for trade orders.
 *
 * @author Julian
 */
public class PriceComparator implements java.util.Comparator<TradeOrder> {

    private final boolean ascending;

    /**
     * Constructs a price comparator that compares two orders in ascending
     * order. Sets the private boolean ascending flag to true.
     */
    public PriceComparator()
    {
        ascending = true;
    }

    /**
     * Constructs a price comparator that compares two orders in ascending or
     * descending order. The order of comparison depends on the value of a given
     * parameter. Sets the private boolean ascending flag to asc.
     *
     * @param asc
     *            if true, make an ascending comparator; otherwise make a
     *            descending comparator.
     */
    public PriceComparator(boolean asc)
    {
        ascending = asc;
    }

    /**
     * Compares two trade orders
     *
     * @param order1
     *            the first order
     * @param order2
     *            the second order
     * @return 0 if both orders are market orders; <br>
     *         -1 if order1 is market and order2 is limit; <br>
     *         1 if order1 is limit and order2 is market; <br>
     *         the difference in prices, rounded to the nearest cent, if both
     *         order1 and order2 are limit orders. In the latter case, the
     *         difference returned is cents1 - cents2 or cents2 - cents1,
     *         depending on whether this is an ascending or descending
     *         comparator (ascending is true or false).
     */
    public int compare(TradeOrder order1, TradeOrder order2)
    {
        if (order1.isLimit() && order2.isLimit()) {
            if (ascending) {
                return (int)(100.0 * (order1.getPrice() - order2.getPrice()));
            }
            else {
                return (int)(100.0 * (order2.getPrice() - order1.getPrice()));
            }
        }
        return (order1.isLimit() ? 1 : 0) - (order2.isLimit() ? 1 : 0);
    }
}
