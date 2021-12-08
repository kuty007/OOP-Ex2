import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

public class DWGraph implements DirectedWeightedGraph {
    public static void main(String[] args) {
        DWGraph graph = loadFile("data/in/G2.json");
        System.out.println(graph.numOfEdges);
    }

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
        final int currentChange = this.changes;
        return new Iterator<EdgeData>() {
            Iterator<HashMap<Integer, EdgeData>> iter = edges.values().iterator();
            ;
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
                last = (EdgeData) iter.next();
                return last;
            }

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
