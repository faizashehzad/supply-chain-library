package logistics.model;

/**
 * Represents a physical location with a name and a postcode.
 */
public class Location {

    private final String name;
    //TODO: replace by int
    private final String postcode;


    public Location(String name, String postcode) {
        this.name = name;
        this.postcode = postcode;
    }

    public String getName() {
        return name;
    }

    public String getPostcode() {
        return postcode;
    }

    @Override
    public String toString() {
        return name + " (" + postcode + ")";
    }
}
