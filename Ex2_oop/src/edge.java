import java.util.Objects;

public class edge implements EdgeData {
    private int src;
    private int dest;
    private double w;
    private String info;
    private int tag;

    public edge(int s, int d, double w) {
        if (w < 0) {
            throw new RuntimeException("Edge weight must be a positive value only");
        }
        this.src = s;
        this.dest = d;
        this.w = w;
        this.info = "White";
        this.tag = -1;
    }

    public edge(EdgeData other) {
        edge e1 = (edge) other;
        this.src = e1.src;
        this.dest = e1.dest;
        this.w = other.getWeight();
        this.info = other.getInfo();
        this.tag = other.getTag();
    }
    /**
     * return the id of the source node of this edge.
     * @return src - id of src node.
     */
    @Override
    public int getSrc() {
        return this.src;
    }

    /**
     * return the id of the destination node of this edge
     * @return dst - id of dest node.
     */
    @Override
    public int getDest() {
        return this.dest;
    }

    /**
     * return the weight of the edge
     * @return weight - the weight of this edge (positive value).
     */
    @Override
    public double getWeight() {
        return this.w;
    }

    /**
     * Returns the remark (meta data) associated with this edge.
     * @return info - string of the info by the edge.
     */
    @Override
    public String getInfo() {
        return this.info;
    }

    /**
     * Allows changing the remark (meta data) associated with this edge.
     * @param s - String of the new info
     */
    @Override
    public void setInfo(String s) {
        this.info = s;
    }

    /**
     * Temporal data (aka color: e,g, white, gray, black)
     * which can be used be algorithms
     * @return tag - int of the tag by this edge.
     */
    @Override
    public int getTag() {
        return this.tag;
    }

    /**
     * This method allows setting the "tag" value for temporal marking an edge - common
     * practice for marking by algorithms.
     * @param t - the new value of the tag
     */
    @Override
    public void setTag(int t) {
        this.tag = t;
    }

    /**
     * Returns a string representation of this edge
     * @return A string representation of this edge
     */
    public String toString() {
        return +src + "--" + this.w + "-->" + dest;
    }

    public boolean equals(Object o1) {
        if (this == o1) return true;
        if (o1 == null || getClass() != o1.getClass()) return false;
        edge edge = (edge) o1;
        return Double.compare(edge.w, w) == 0 &&
                tag == edge.tag &&
                src == edge.src &&
                dest == edge.dest &&
                info.equals(edge.info);
    }

    public int hashCode() {
        return Objects.hash(src, dest, w, info, tag);
    }
}
