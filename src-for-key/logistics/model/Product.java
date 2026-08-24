package logistics.model;

/**
 * Represents a product that can be ordered and shipped.
 * All fields are private. Access is only through public methods.
 */
public class Product {

    /*@ public model int modelProductId;
      @ represents modelProductId = id;
      @ public invariant modelProductId > 0;
      @*/

    private int id;

    /*@ public model String modelProductName;
      @ represents modelProductName = name;
      @ public invariant modelProductName.length() > 0;
      @*/
    private String name;

    /*@ public model int weight;
      @ represents modelProductWeight = weight;
      @ public invariant modelProductWeight > 0;
      @*/
    private int weight;  // weight in kilograms, must be > 0

    /*@ public normal_behavior
      @ requires id > 0;
      @ requires  name.length() > 0;
      @ requires weight > 0;
      @ assignable \nothing;
      @ ensures this.modelProductId == id;
      @ ensures this.modelProductName.equals(name);
      @ ensures this.modelProductWeight == weight;
     */
    public Product(int id, String name, int weight) {
        this.id     = id;
        this.name   = name;
        this.weight = weight;
    }

    /*@ public normal_behavior
      @ ensures \result == modelProductId;
      @ assignable \strictly_nothing;
     */
    public int getId() {
        return id;
    }

    /*@ public normal_behavior
      @ ensures \result.equals(modelProductName);
      @ assignable \strictly_nothing;
     */

    public String getName() {
        return name;
    }

    /*@ public normal_behavior
      @ ensures \result == modelProductWeight;
      @ assignable \strictly_nothing;
     */

    public int getWeight() {
        return weight;
    }

    public String toString() {
        return "Product[id=" + id + ", name=" + name + ", weight=" + weight + "kg]";
    }
}
