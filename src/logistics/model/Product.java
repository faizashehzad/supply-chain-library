package logistics.model;

/**
 * Represents a product that can be ordered and shipped.
 * All fields are private. Access is only through public methods.
 */
public class Product {

    private int id;
    private String name;
    private int weight;  // weight in kilograms, must be > 0

    public Product(int id, String name, int weight) {
        this.id     = id;
        this.name   = name;
        this.weight = weight;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getWeight() {
        return weight;
    }

    public String toString() {
        return "Product[id=" + id + ", name=" + name + ", weight=" + weight + "kg]";
    }
}
