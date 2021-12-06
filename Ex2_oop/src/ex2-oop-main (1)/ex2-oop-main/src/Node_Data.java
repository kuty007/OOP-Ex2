import java.util.Objects;

public class Node_Data implements NodeData {
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
    @Override
    public int getKey() {
        return this.key;
    }

    @Override
    public GeoLocation getLocation() {
        return this.location;
    }

    @Override
    public void setLocation(GeoLocation p) {
        this.location = new geoLo(p.x(),p.y(),p.z());
    }

    @Override
    public double getWeight() {
        return this.weight;
    }

    @Override
    public void setWeight(double w) {
        this.weight = w;
    }

    @Override
    public String getInfo() {
        return this.info;
    }

    @Override
    public void setInfo(String s) {
        this.info = s;
    }

    @Override
    public int getTag() {
        return this.tag;
    }

    @Override
    public void setTag(int t) {
        this.tag = t;
    }
    public String toString() {
        return "[" + key + "]";
    }
    public int compareTo(NodeData o) {
        NodeData n = this;
        return Double.compare(n.getWeight(), o.getWeight());
    }
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node_Data Node_Data = (Node_Data) o;
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
