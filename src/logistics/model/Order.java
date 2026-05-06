package logistics.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents a customer request to ship one or more products.
 * Uses a dynamic list to store products, allowing for flexible order sizes.
 */
public class Order {

    private int orderId;
    private List<Product> items;
    //TODO: Remove generics,  ArrayList needs to speicifed
    public Order(int orderId) {
        this.orderId = orderId;
        this.items = new ArrayList<>();
    }

    //@ ensures items.content.length == 0;
    public boolean hasOrders() {
        return !items.isEmpty();
    }

    /**
     * Adds a product to this order.
     * Does nothing if the product is null.
     */
    public void addItem(Product p) {
        if (p != null) {
            items.add(p);
        }
    }

    public int getOrderId() {
        return orderId;
    }

    /**
     * Returns the total number of items in the order.
     */
    public int getQuantity() {
        return items.size();
    }

    /**
     * Returns the product at position index, or null if index is out of range.
     */
    public Product getItem(int index) {
        if (index >= 0 && index < items.size()) {
            return items.get(index);
        }
        return null;
    }

    /**
     * Returns an unmodifiable view of the products in this order.
     * This follows good encapsulation practices by preventing external modification.
     */

   // TODO: Just Return the list itself Specification would be
    public List<Product> getAllItems() {
        return Collections.unmodifiableList(items);
    }

    /**
     * Calculates the total weight of the order.
     * Demonstrates a simple logic that can be easily specified in JML.
     /*TODo for later improve the for loop with a better code and then specify */
    /*first implement this with simple for loop and specify it */
    public int getTotalWeight() {
        return items.stream()
                .mapToInt(Product::getWeight)
                .sum();
    }

    @Override
    public String toString() {
        return "Order[id=" + orderId + ", quantity=" + items.size() + ", totalWeight=" + getTotalWeight() + "kg]";
    }
}
