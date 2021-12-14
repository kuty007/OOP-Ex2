import java.util.Objects;

public class Node_Data implements NodeData {
    /**
     * basic node constructor
     */
    private int key;
    private geoLo location;
    private double weight = Double.MAX_VALUE;
    private String info = "White";
    private int tag = -1;
    private static int uniqueKey = 0;


    public Node_Data(GeoLocation l) {
        this.key = uniqueKey++;
        this.location = (geoLo) l;
    }

    /**
     * Deep copy constructor.
     * @param other - node_gata.
     */
    public Node_Data(NodeData other) {
        this.key = other.getKey();
        this.location = new geoLo(other.getLocation());
        this.weight = other.getWeight();
        this.info = other.getInfo();
        this.tag = other.getTag();
    }

    public Node_Data(int key, GeoLocation location, double weight, String info, int tag) {
        this.key = key;
        this.location = (geoLo) location;
        this.weight = weight;
        this.info = info;
        this.tag = tag;
    }

    public Node_Data(int id, GeoLocation location) {
        this.key = id;
        this.location = (geoLo) location;
    }

    /**
     * Returns the key (id) associated with this node.
     * @return key - the Location of the node
     */
    @Override
    public int getKey() {
        return this.key;
    }

    /**
     * Returns the location of this node, if
     * @return location - the location of the node
     */
    @Override
    public GeoLocation getLocation() {
        return this.location;
    }

    /**
     * Allows changing this node's location.
     * @param p - new location (position) of this node.
     */
    @Override
    public void setLocation(GeoLocation p) {
        this.location = new geoLo(p.x(), p.y(), p.z());
    }

    /**
     * Returns the weight associated with this node.
     * @return weight - the Weight of the node
     */
    @Override
    public double getWeight() {
        return this.weight;
    }

    /**
     * Allows changing this node's weight.
     * @param w - the new weight
     */
    @Override
    public void setWeight(double w) {
        this.weight = w;
    }

    /**
     * Returns the remark (meta data) associated with this node.
     * @return info - of the node.
     */
    @Override
    public String getInfo() {
        return this.info;
    }

    /**
     * Allows changing the remark (meta data) associated with this node.
     * @param s - the info of the node
     */
    @Override
    public void setInfo(String s) {
        this.info = s;
    }

    /**
     * Temporal data (aka color: e,g, white, gray, black)
     * which can be used be algorithms
     * @return tag - the tag of the node
     */
    @Override
    public int getTag() {
        return this.tag;
    }

    /**
     * Allows setting the "tag" value for temporal marking an node - common
     * practice for marking by algorithms.
     * @param t - the new value of the tag
     */
    @Override
    public void setTag(int t) {
        this.tag = t;
    }

    /**
     * return String of the data by the node
     * @return s - string of the node.
     */
    public String toString() {
        return "[" + key + "]";
    }

    public int compareTo(NodeData o) {
        NodeData n = this;
        return Double.compare(n.getWeight(), o.getWeight());
    }

    public boolean equals(Object o1) {
        if (this == o1) return true;
        if (o1 == null || getClass() != o1.getClass()) return false;
        Node_Data Node_Data = (Node_Data) o1;
        return key == Node_Data.key &&
                Double.compare(Node_Data.weight, weight) == 0 &&
                tag == Node_Data.tag &&
                location.equals(Node_Data.location) &&
                info.equals(Node_Data.info);
    }

    public int hashCode() {
        return Objects.hash(key, location, weight, info, tag);
    }
}
