package logistics.model;

/**
 * Represents a product that can be ordered and shipped.
 * All fields are private. Access is only through public methods.
 */
public class Product {

    /*@ private invariant id > 0; @*/
    /*@ private invariant name.length() > 0; @*/
    /*@ private invariant weight > 0; @*/

    private  int id;
    private String name;
    private int weight;  // weight in kilograms, must be > 0

    /*@ public normal_behavior
      @ requires id > 0;
      @ requires name.length() > 0;
      @ requires weight > 0;
      @ assignable \nothing;
      @ ensures this.getId() == id;
      @ ensures this.getName().equals(name);
      @ ensures this.getWeight() == weight;
     */
    public Product(int id, String name, int weight) {
        this.id     = id;
        this.name   = name;
        this.weight = weight;
    }

    /*@ public normal_behavior
      @ ensures \result == id;
      @ assignable \strictly_nothing;
     */
    public /*@ pure @*/ int getId() {
        return id;
    }

    /*@ public normal_behavior
      @ ensures \result.equals(name);
      @ assignable \strictly_nothing;
     */

    public /*@ pure @*/ String getName() {
        return name;
    }

    /*@ public normal_behavior
      @ ensures \result == weight;
      @ assignable \strictly_nothing;
     */

    public /*@ pure @*/ int getWeight() {
        return weight;
    }

    public String toString() {
        return "Product[id=" + id + ", name=" + name + ", weight=" + weight + "kg]";
    }
}
