package logistics.model;

 // Represents a physical location with a name and a numeric postcod
public class Location {

    private final String name;
    private final int postcode; // Changed from String to int


    public Location(String name, int postcode) { // Constructor updated to accept int
        this.name = name;
        this.postcode = postcode;
    }

    public String getName() {
        return name;
    }

    public int getPostcode() { // Getter updated to return int
        return postcode;
    }

    @Override
    public String toString() {
        return name + " (" + postcode + ")";
    }
}
