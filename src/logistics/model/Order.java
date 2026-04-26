package logistics.model;

/**
 * Represents a customer request to ship one or more products.
 * Items are stored in a fixed-size private array.
 * quantity always reflects the number of items added.
 */
public class Order {

    private static final int MAX_ITEMS = 20;

    private int       orderId;
    private Product[] items;
    private int       quantity;

    public Order(int orderId) {
        this.orderId  = orderId;
        this.items    = new Product[MAX_ITEMS];
        this.quantity = 0;
    }

    /**
     * Adds a product to this order.
     * Does nothing if the order is already full.
     */
    public void addItem(Product p) {
        if (p != null && quantity < MAX_ITEMS) {
            items[quantity] = p;
            quantity++;
        }
    }

    public int getOrderId() {
        return orderId;
    }

    public int getQuantity() {
        return quantity;
    }

    /**
     * Returns the product at position index, or null if index is out of range.
     */
    public Product getItem(int index) {
        if (index >= 0 && index < quantity) {
            return items[index];
        }
        return null;
    }

    public String toString() {
        return "Order[id=" + orderId + ", quantity=" + quantity + "]";
    }
}
