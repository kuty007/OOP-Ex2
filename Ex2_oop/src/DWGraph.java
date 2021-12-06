import java.util.HashMap;
import java.util.Iterator;

public class DWGraph implements DirectedWeightedGraph {

    private HashMap<Integer, NodeData> nodes;
    private HashMap<Integer, HashMap<Integer, EdgeData>> edges;
    private int numOfNodes;
    private int numOfEdges;
    private int modeCounter;

    public DWGraph() {
        this.nodes = new HashMap<>();
        this.edges = new HashMap<>();
        this.numOfEdges = 0;
        this.numOfNodes = 0;
        this.modeCounter = 0;

}

    @Override
    public NodeData getNode(int key) {
        return this.nodes.get(key);
    }

    @Override
    public EdgeData getEdge(int src, int dest) {
        if (this.nodes.containsKey(src) && this.nodes.containsKey(dest)) {//if we have this 2 nodes go to the edges HashMap and return the value there
            return this.edges.get(src).get(dest);
        }
        return null;
    }

    @Override
    public void addNode(NodeData n) {


    }

    @Override
    public void connect(int src, int dest, double w) {

    }

    @Override
    public Iterator<NodeData> nodeIter() {
        return null;
    }

    @Override
    public Iterator<EdgeData> edgeIter() {
        return null;
    }

    @Override
    public Iterator<EdgeData> edgeIter(int node_id) {
        return null;
    }

    @Override
    public NodeData removeNode(int key) {
        return null;
    }

    @Override
    public EdgeData removeEdge(int src, int dest) {
        return null;
    }

    @Override
    public int nodeSize() {
        return 0;
    }

    @Override
    public int edgeSize() {
        return 0;
    }

    @Override
    public int getMC() {
        return 0;
    }
}
