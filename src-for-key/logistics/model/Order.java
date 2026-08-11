package logistics.model;

import java.util.ArrayList;
//import java.util.Collections;
import java.util.List;

/**
 * Represents a customer request to ship one or more products.
 * Uses a dynamic list to store products, allowing for flexible order sizes.
 */
public class Order {


    //@ public model \seq productsInOrder;
    //@ represents productsInOrder = items.content;

    /*@ private invariant orderId > 0; @*/
    private /*@ spec_public @*/ int orderId;

    //@ private invariant (\forall int i; 0 <= i < productsInOrder.length; \seq_get(productsInOrder, i) != null );
    private /*@ spec_public @*/  List items;

    /*@ private normal_behavior
       requires orderId > 0;
       assignable this.orderId, this.items;
       ensures this.orderId == orderId;
       ensures productsInOrder == \seq_empty;
    */
    public Order(int orderId) {
        this.orderId = orderId;
        this.items = new ArrayList();
    }


    // TODO: specify with model fields

    /*@ public normal_behavior
      @ assignable \strictly_nothing;
      @ ensures \result != \seq_empty;
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
      @ assignable items.content;
      @ ensures productsInOrder == \seq_concat(\old(productsInOrder), \seq_singleton(p));
    @*/
    public void addItem(Product p) {
        if (p != null) {
            items.add(p);
        }
    }

    /*@ public normal_behavior
      @ ensures \result == orderId;
      @ assignable \strictly_nothing;
    @*/
    public int getOrderId() {
        return orderId;
    }

    /**
     * Returns the total number of items in the order.
     */

    /*@ public normal_behavior
      @ ensures \result == productsInOrder.length;
      @ assignable \strictly_nothing;
    @*/
    public int getQuantity() {
        return items.size();
    }

    /**
     * Returns the product at position index, or null if index is out of range.
     */

    /*@ public normal_behavior
      @  requires (0 <= index && index < productsInOrder.length);
      @  ensures \result == \seq_get(productsInOrder, index);
      @  assignable \strictly_nothing;
      @ also
      @ public normal_behavior
      @  requires (index < 0 || index >= productsInOrder.length);
      @  ensures \result == null;
      @  assignable \strictly_nothing;
      @ also
      @ public normal_behavior
      @  requires (0 <= index && index < productsInOrder.length) ||  (index < 0 || index >= productsInOrder.length);
      @  ensures \old((0 <= index && index < productsInOrder.length)) ==> \result == \seq_get(productsInOrder, index);
      @  ensures \old((index < 0 || index >= productsInOrder.length)) ==>  \result == null;
      @  assignable \strictly_nothing;
      @*/
    public /*@ nullable @*/ Product getItem(int index) {
        if (index >= 0 && index < items.size()) {
            return (Product) items.get(index);
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
       @  ensures \result.size() == productsInOrder.length;
       @  ensures (\forall int i; 0 <= i < productsInOrder.length; \result.get(i) == \seq_get(productsInOrder, i));
       @  ensures \fresh(\result);
      @*/

    public List getAllItems() {
        return null;
                //Collections.unmodifiableList(items);
    }

//------------------------------------------------------
    /*@ public normal_behavior
      @ assignable \strictly_nothing;
      @ ensures \result == (productsInOrder.length == 0);
    */
    public boolean isEmpty() {
        return items.isEmpty();
    }


  /*
  *  //using arrays
     public Product[] getAllItems() {
     Product[] list_all_items = new Product[items.size()];
        for (int i = 0; i < items.size(); i++) {
            list_all_items[i] = items.get(i);
        }
        return list_all_items;
    }
  * */








    /**
     * Calculates the total weight of the order.
     * Demonstrates a simple logic that can be easily specified in JML.
     * TODO for later improve the for loop with a better code and then specify
     * first implement this with simple for loop and specify it
     * */

    /*@ public normal_behavior
    @   requires (\forall int i; 0 <= i < productsInOrder.length; ((Product)\seq_get(productsInOrder, i)).getWeight() >= 0);
    @   ensures \result == (\sum int i; 0 <= i && i < productsInOrder.length; ((Product)\seq_get(productsInOrder, i)).getWeight());
    */
    public int getTotalWeight() {
        int totalWeight = 0;
        for (int i = 0; i < items.size(); i++) {
            Product product = (Product) items.get(i);
            if (product != null) {
                totalWeight += product.getWeight();
            }
        }
        return totalWeight;
    }

   //  @Override
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
