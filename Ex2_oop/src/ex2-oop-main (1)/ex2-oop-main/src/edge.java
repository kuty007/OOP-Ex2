import java.util.Objects;

public class edge implements EdgeData{
    private int src;
    private int dest;
    private double w;
    private String info;
    private int tag;

    public edge(int s, int d, double w){
        if(w < 0){
            throw new RuntimeException("Edge weight must be a positive value");
        }
        this.src = s;
        this.dest = d;
        this.w = w;
        this.info = "White";
        this.tag = -1;
    }
    public edge(EdgeData other){
        edge e = (edge)other;
        this.src = e.src;
        this.dest = e.dest;
        this.w = other.getWeight();
        this.info = other.getInfo();
        this.tag = other.getTag();
    }
    @Override
    public int getSrc() {
        return this.src;
    }

    @Override
    public int getDest() {
        return this.dest;
    }

    @Override
    public double getWeight() {
        return this.w;
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
        return  +src + "--" + this.w + "-->" + dest;
    }
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        edge edge = (edge) o;
        return Double.compare(edge.w, w) == 0 &&
                tag == edge.tag &&
                src == edge.src  &&
                dest == edge.dest &&
                info.equals(edge.info);
    }
    public int hashCode() {
        return Objects.hash(src, dest, w, info, tag);
    }
}
