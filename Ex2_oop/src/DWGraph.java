import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

public class DWGraph implements DirectedWeightedGraph {
    public static void main(String[] args) {
        DWGraph graph = loadFile("data/in/G2.json");
        System.out.println(graph.numOfEdges);
    }

    private HashMap<Integer, NodeData> nodes;

    public HashMap<Integer, HashMap<Integer, EdgeData>> getEdges() {
        return edges;
    }

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


    public static DWGraph loadFile(String path) {
        DirectedWeightedGraph newG = null;
        try {
            //create a Gson object
            newG = new DWGraph();
            //read from file as JsonObject
            JsonObject json = new JsonParser().parse(new FileReader(path)).getAsJsonObject();
            JsonArray E = json.getAsJsonArray("Edges");
            JsonArray V = json.getAsJsonArray("Nodes");
            //run by json and convert it to Nodes
            for (JsonElement node : V) {
                String[] pos = ((JsonObject) node).get("pos").getAsString().split(",");
                GeoLocation location = new geoLo(Double.parseDouble(pos[0]), Double.parseDouble(pos[1]), Double.parseDouble(pos[2]));
                Node_Data newN = new Node_Data(((JsonObject) node).get("id").getAsInt(), location);
                newG.addNode(newN);
            }
            //run by json and convert it to Edges
            for (JsonElement edge : E) {
                JsonObject e = (JsonObject) edge;
                newG.connect(e.get("src").getAsInt(), e.get("dest").getAsInt(), e.get("w").getAsDouble());
            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return (DWGraph) newG;
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

    /**
     * Returns the node_data by the node id,
     * @param key - the node_id
     * @return the node_data by the node_id, null if none.
     */
    @Override
    public NodeData getNode(int key) {
        return this.nodes.get(key);
    }

    /**
     * Returns the edge (src,dest) between two nodes, null if none.
     * Complexity: this method run in O(1) time.
     * @param src - source node id.
     * @param dest - destination node id.
     * @return an edge, null if none.
     */
    @Override
    public EdgeData getEdge(int src, int dest) {
        if (this.nodes.containsKey(src) && this.nodes.containsKey(dest)) {//if we have this 2 nodes go to the edges HashMap and return the value there
            return this.edges.get(src).get(dest);
        }
        return null;
    }

    /**
     * Adds a new node to the graph with the given node_data.
     * This method simply do nothing if this graph already contains this node_data.
     * Complexity: this method run in O(1) time.
     * @param n - node_data
     */
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

    /**
     * Connects an edge with positive weight w between node src to node dest.
     * NOTE1: If there is already an edge between node src to node dest in this graph,
     * the method will update the weight according to the weight obtained.
     * NOTE2: This method simply do nothing if node src and node dest are the same node.
     * @param src - the source id's of the edge.
     * @param dest - the destination id's of the edge.
     * @param w - positive weight representing the cost (aka time, price, etc) between src-->dest.
     */
    @Override
    public void connect(int src, int dest, double w) {
        if (w < 0) {
            throw new RuntimeException("The weight cant be negative!");
        }
        if (src == dest) {
            return;
        }

        if (this.getEdge(src, dest) != null && edges.get(src).get(dest).getWeight() != w) {
            EdgeData ed = new edge(src, dest, w);
            edges.get(src).put(dest, ed);
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
        final int currentChange = this.changes;
        Iterator<NodeData> iter = nodes.values().iterator();
        return new Iterator<NodeData>() {
            NodeData last = null;

            public boolean hasNext() {
                if (currentChange != changes) {
                    throw new RuntimeException("The graph has changed while the iterator was running");
                }
                return iter.hasNext();
            }

            @Override
            public NodeData next() {
                if (currentChange != changes) {
                    throw new RuntimeException("The graph has changed while the iterator was running");
                }
                if(hasNext())
                last = iter.next();
                return last;
            }

            @Override
            public void remove() {
                if (currentChange != changes) {
                    throw new RuntimeException("The graph has changed while the iterator was running");
                }
                if (last != null) {
                    removeNode(last.getKey());
                }
                Iterator.super.remove();
            }
        };
    }

    @Override
    public Iterator<EdgeData> edgeIter() {
        return new Iterator<>() {

            private final Iterator<HashMap<Integer, EdgeData>> it = edges.values().iterator();
            private Iterator<EdgeData> edgeIt = null;
            private EdgeData value = null;
            public int counter = getMC();

            @Override
            public boolean hasNext() {
                if (getMC() != counter) {
                    throw new RuntimeException("The graph has changed while the iterator was running");
                }
                return it.hasNext() || (edgeIt == null || edgeIt.hasNext());
            }

            @Override
            public EdgeData next() {
                if (getMC() != counter) {
                    throw new RuntimeException("The graph has changed while the iterator was running");
                }
                if (this.hasNext()) {
                    if (edgeIt == null || !edgeIt.hasNext()) {
                        edgeIt = it.next().values().iterator();
                    }
                    value = edgeIt.next();
                    return value;
                }
                else throw new RuntimeException("dont have next value");
            }

            @Override
            public void remove() {
                if (getMC() != counter) {
                    throw new RuntimeException("The graph has changed while the iterator was running");
                }
                if (value != null) {
                    removeEdge(value.getSrc(), value.getDest());
                    this.counter = getMC();
                }
            }

            @Override
            public void forEachRemaining(Consumer<? super EdgeData> action) {
                if (getMC() != counter) {
                    throw new NullPointerException("The graph has changed while the iterator was running");
                }
                while (it.hasNext()) {
                    action.accept(next());
                }
            }
        };
    }

    @Override
    public Iterator<EdgeData> edgeIter(int node_id) {
        final int currentChange = this.changes;
        return new Iterator<EdgeData>() {
            Iterator<EdgeData> iter = edges.get(node_id).values().iterator();
            EdgeData last = null;

            @Override
            public boolean hasNext() {
                if (currentChange != changes) {
                    throw new RuntimeException("The graph has changed while the iterator was running");
                }

                return iter.hasNext();
            }

            @Override
            public EdgeData next() {
                if (currentChange != changes) {
                    throw new RuntimeException("The graph has changed while the iterator was running");
                }
                if(hasNext())
                last = iter.next();
                return last;
            }

            @Override
            public void remove() {
                if (currentChange != changes) {
                    throw new RuntimeException("The graph has changed while the iterator was running");
                }
                if (last != null) {
                    removeEdge(last.getSrc(), last.getDest());

                }
                Iterator.super.remove();
            }
        };
    }

    /**
     * Deletes the node (with the given ID) from the graph -
     * and removes all edges which starts or ends at this node.
     * @return the data of the removed node (null if none).
     * @param key - the deleted node id's.
     */
    @Override
    public NodeData removeNode(int key) {
        if (!this.nodes.containsKey(key)) {
            return null;
        }
        int size = edges.get(key).size();
        edges.remove(key);
        numOfEdges -= size;
        changes += size;
        Collection<Integer> col = edges.keySet();
        for (int i : col) {
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

    /**
     * Deletes the edge from the graph,
     * Complexity: this method run in O(1) time.
     * Note: if the edge does not exists in the graph - the method simply does nothing.
     * @param src - edge source node id's.
     * @param dest - edge destination node id's.
     * @return the data of the removed edge (null if none).
     */
    @Override
    public EdgeData removeEdge(int src, int dest) {
        EdgeData e = edges.get(src).remove(dest);
        if (e != null) {
            numOfEdges--;
        }
        return e;
    }

    /**
     * Returns the number of vertices (nodes) in the graph.
     * Complexity: this method run in O(1) time.
     * @return number of nodes in this graph.
     */
    @Override
    public int nodeSize() {
        return this.numOfNodes;
    }

    /**
     * Returns the number of edges.
     * Complexity: this method run in O(1) time.
     * @return number of edges in this graph.
     */
    @Override
    public int edgeSize() {
        return this.numOfEdges;
    }

    /**
     * Returns the Mode Count - for testing changes in the graph.
     * Complexity: this method run in O(1) time.
     * @return number of changes in this graph.
     */
    @Override
    public int getMC() {
        return this.changes;
    }
}
