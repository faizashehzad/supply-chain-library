package logistics.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents a customer request to ship one or more products.
 * Uses a dynamic list to store products, allowing for flexible order sizes.
 */
public class Order_ghost_fields {

    //@ public ghost instance \seq orderItems;

    /*@ public invariant orderId > 0; @*/
    private /*@ spec_public @*/ int orderId;

    /*@ private invariant items != null @*/
    //@ private invariant (\forall int i; 0 <= i < orderItems.length; \seq_get(orderItems, i) != null ); @*/
    private /*@ spec_public @*/ List items;


    /*@ private normal_behavior
      @ requires orderId > 0;
      @ assignable this.orderId, this.items, this.orderItems;
      @ ensures this.orderId == orderId;
      @ ensures this.items == \seq_empty;
    */
    public Order_ghost_fields(int orderId) {
        this.orderId = orderId;
        this.items = new ArrayList();
        /*@ set orderItems = \seq_empty; @*/
    }


    // TODO: specify with ghost
    /*@ public normal_behavior
      @ assignable \strictly_nothing;
      @ ensures \result == (orderItems.length > 0);
    */

    public boolean hasOrders() {
        return !items.isEmpty();
    }

    /**
     * Adds a product to this order.
     * Does nothing if the product is null.
     */

    /*@ public normal_behavior
      @ requires p != null;
      @ assignable items.content, orderItems;
      @ ensures orderItems == \seq_concat(\old(orderItems), \seq_singleton(p));
    @*/

    public void addItem(logistics.model.Product p) {
        if (p != null) {
            items.add(p);
            //@ set orderItems = \seq_concat(\old(orderItems), \seq_singleton(p));
        }

    }

    /*@ public normal_behavior
      @ ensures \result == orderId;
      @ assignable \strictly_nothing;
    */
    public int getOrderId() {
        return orderId;
    }

    /**
     * Returns the total number of items in the order.
     */
    /*@ public normal_behavior
      @ assignable \strictly_nothing;
      @ ensures \result == orderItems.length;
    */
    public int getQuantity() {
        return items.size();
    }

    /**
     * Returns the product at position index, or null if index is out of range.
     */
    // TODO: specify with ghost fields

    /*@ public normal_behavior
      @ requires (0 <= index && index < orderItems.length)
      @ ensures \result == \seq_get(orderItems, index);
      @ assignable \strictly_nothing;
      @ also
      @ public normal_behavior
      @ requires (index < 0 || index >= orderItems.length);
      @ ensures \result == null;
      @ assignable \strictly_nothing;
      @ ensures (0 <= index && index < orderItems.length) ==> (\result != null && \result == \seq_get(orderItems, index));
      @ ensures (index < 0 || index >= orderItems.length) ==> (\result == null);
    */
    public logistics.model.Product /*@ nullable @*/ getItem(int index) {
        if (index >= 0 && index < items.size()) {
            return (logistics.model.Product) items.get(index);
        }
        return null;
    }
//-----------------------------------
    /**
     * Returns an unmodifiable view of the products in this order.
     * This follows good encapsulation practices by preventing external modification.
     */

   // TODO: Just Return the list itself Specification would be


      /*@ public normal_behavior
       @  assignable \strictly_nothing;
       @  ensures \result != null;
       @  ensures \result.size() == orderItems.length;
       @  ensures (\forall int i; 0 <= i < orderItems.length; \result.get(i) == \seq_get(orderItems, i));
       @  ensures \fresh(\result) == false;
      @*/
    public List getAllItems() {
        return Collections.unmodifiableList(items);
    }

//------------------------------------------------------


  /*@ public normal_behavior
  @   requires items != null;
  @   ensures (\forall int i; 0 <= i && i < items.size(); list_all_items[i] = items.get(i));
  @   ensures \result == list_all_items && items.size();
  @   assignable \nothing;
  @*/


    /*

    //using arrays
    public Product[] getAllItems() {
    Product[] list_all_items = new Product[items.size()];
        for (int i = 0; i < items.size(); i++) {
            list_all_items[i] = items.get(i);
        }
        return list_all_items;
    }

*/

    /**
     * Calculates the total weight of the order.
     * Demonstrates a simple logic that can be easily specified in JML.
     * TODO for later improve the for loop with a better code and then specify
     * first implement this with simple for loop and specify it
     * */

    /*@ public normal_behavior
    @ assignable \strictly_nothing;
    @ ensures \result >= 0;
    @ ensures \result == (\sum int i; 0 <= i < orderItems.length; (\seq_get(orderItems, i)).getWeight());
    */
    public int getTotalWeight() {
        int totalWeight = 0;
        for (int i = 0; i < items.size(); i++) {
            logistics.model.Product product = (logistics.model.Product) items.get(i);
            if (product != null) {
                totalWeight += product.getWeight();
            }
        }
        return totalWeight;
    }

    @Override
    public String toString() {
        return "Order[id=" + orderId + ", quantity=" + items.size() + ", totalWeight=" + getTotalWeight() + "kg]";
    }


    /**
    public int getTotalWeight() {
        return items.stream()
                .mapToInt(Product::getWeight)
                .sum();
    } */
}
