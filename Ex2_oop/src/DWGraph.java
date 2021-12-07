import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

public class DWGraph implements DirectedWeightedGraph {

    private HashMap<Integer, NodeData> nodes;
    private HashMap<Integer, HashMap<Integer, EdgeData>> edges;
    private int numOfNodes;
    private int numOfEdges;
    private int changes;

    public DWGraph() {
        this.nodes = new HashMap<>();
        this.edges = new HashMap<>();
        this.numOfEdges = 0;
        this.numOfNodes = 0;
        this.changes = 0;


    }

    public DWGraph(DirectedWeightedGraph other) {
        this.nodes = new HashMap<>();
        this.edges = new HashMap<>();
        nodesDeepCopy(other, this.nodes);
        edgesDeepCopy(other, this.edges);
        this.numOfEdges = other.edgeSize();
        this.numOfNodes = other.nodeSize();
    }
    private HashMap<Integer, Node_Data> nodesDeepCopy(DirectedWeightedGraph other, HashMap nodes) {
        HashMap<Integer, Node_Data> h = nodes;
        for (Iterator<NodeData> it = other.nodeIter(); it.hasNext(); ) {
            Node_Data n = (Node_Data) it.next();
            this.addNode(n);
        }
        return h;
    }
    private HashMap<Integer, HashMap<Integer, EdgeData>> edgesDeepCopy(DirectedWeightedGraph other, HashMap edges) {
        HashMap<Integer, HashMap<Integer, EdgeData>> h = edges;
        int key;
        for (Iterator<NodeData> it = this.nodeIter(); it.hasNext(); ) {
            NodeData n = it.next();
            key = n.getKey();
            for (Iterator<EdgeData> iter = other.edgeIter(key); iter.hasNext(); ) {
                EdgeData e = iter.next();
                EdgeData edge = new edge(e);
                this.edges.get(e.getSrc()).put(e.getDest(), edge);
            }
        }
        return h;
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
        if (nodes.containsKey(n.getKey())) {
            return;
        }
        nodes.put(n.getKey(), n);
        edges.put(n.getKey(), new HashMap<>());
        changes++;
        numOfNodes++;
    }


    @Override
    public void connect(int src, int dest, double w) {
        if (w < 0) {
            throw new RuntimeException("The weight cant be negative!");
        }
        if (src == dest) {
            return;
        }

        if (this.getEdge(src, dest) != null && edges.get(src).get(dest).getWeight() != w) {
            EdgeData e = new edge(src, dest, w);
            edges.get(src).put(dest, e);
            changes++;
        } else if (nodes.containsKey(src) && nodes.containsKey(dest) && this.getEdge(src, dest) == null) {
            EdgeData e = new edge(src, dest, w);
            edges.get(src).put(dest, e);
            changes++;
            numOfEdges++;
        }
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
        if (!this.nodes.containsKey(key)) {
            return null;
        }
        int size = edges.get(key).size();
        edges.remove(key);
        numOfEdges -= size;
        changes += size;
        Collection<Integer> c = edges.keySet();
        for (int i : c) {
            if (edges.get(i).containsKey(key)) {
                edges.get(i).remove(key);
                changes++;
                numOfEdges--;
            }
        }
        NodeData n = nodes.remove(key);
        if (n != null) {
            numOfNodes--;
            changes++;
        }
        return n;
    }


    @Override
    public EdgeData removeEdge(int src, int dest) {
        EdgeData e = edges.get(src).remove(dest);
        if (e != null) {
            numOfEdges--;
        }
        return e;
    }

    @Override
    public int nodeSize() {
        return this.numOfNodes;
    }

    @Override
    public int edgeSize() {
        return this.numOfEdges;
    }

    @Override
    public int getMC() {
        return this.changes;
    }
}
