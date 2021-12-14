import java.awt.geom.Arc2D;
import java.util.List;

import com.google.gson.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.util.*;

public class GraphAlgo implements DirectedWeightedGraphAlgorithms {

    private DirectedWeightedGraph graph;

    public static void main(String[] args) {
        DWGraph graph = DWGraph.loadFile("data/in/G3.json");
        GraphAlgo g = new GraphAlgo();
        g.init(graph);
        List<NodeData> list = new ArrayList<NodeData>();
        list.add(graph.getNode(3));
        list.add(graph.getNode(1));
        list.add(graph.getNode(0));
        list.add(graph.getNode(2));
        list.add(graph.getNode(5));
        GraphAlgo gdsa;
        gdsa = new GraphAlgo("data/in/G3.json");
        //System.out.println(tsp(list));
    }


    public GraphAlgo() {
        this.graph = new DWGraph();
    }
    public GraphAlgo(String path){
        DWGraph graph = DWGraph.loadFile(path);
        this.graph = graph;
    }


    /**
     * Init the graph on which this set of algorithms operates.
     *
     * @param g - a weighted graph
     */
    @Override
    public void init(DirectedWeightedGraph g) {
        this.graph = g;

    }

    /**
     * Return the underlying graph of which this class works.
     *
     * @return a directed weighted graph.
     */
    @Override
    public DirectedWeightedGraph getGraph() {
        return graph;
    }

    /**
     * Compute a deep copy of this weighted graph.
     * The method does this by using the deep copy constructor in DWGraph_DS.
     *
     * @return identical graph.
     */
    @Override
    public DirectedWeightedGraph copy() {
        return new DWGraph(this.graph);
    }

    /**
     * Returns true if and only if (iff) there is a valid path from each node to each
     * other node.
     * The method uses BFS algorithm.
     *
     * @return true if strongly connected, false otherwise.
     */
    @Override
    public boolean isConnected() {
        if (this.graph.nodeSize() == 0) {
            return true;
        }
        for (Iterator<NodeData> it = this.graph.nodeIter(); it.hasNext(); ) {
            Node_Data n = (Node_Data) it.next();
            boolean b = this.bfs(n);
            resetTag();
            if (!b) {
                return false;
            }
        }
        return true;
    }

    private boolean isSubGraphConnected(List<NodeData> ln) {
        for (Iterator<NodeData> it = ln.iterator(); it.hasNext(); ) {
            Node_Data n = (Node_Data) it.next();
            boolean b = this.bfs(n);
            resetTag();
            if (!b) {
                return false;
            }
        }
        return true;
    }

    //Returns the length of the shortest path between src to dest.
    @Override
    public double shortestPathDist(int src, int dest) {
        if (this.graph.getNode(src) == null || this.graph.getNode(dest) == null) {
            throw new RuntimeException("One or more of your keys does not exist in the graph!");
        }
        if (src == dest) {
            return 0;
        }
        resetInfo();
        resetTag();
        resetWeight();
        double dij = Dijkstra((Node_Data) this.graph.getNode(src), (Node_Data) this.graph.getNode(dest));
        resetInfo();
        resetTag();
        resetWeight();
        if (dij == Integer.MAX_VALUE) {
            return -1;
        }
        return dij;
    }

    /**
     * Returns the shortest path between src to dest - as an ordered List of nodes:
     * src --> n1 --> n2 --> ... --> dest.
     * Note: if no such path --> null.
     * The method uses a combination of BFS and Dijkstra's algorithms.
     * Note2: Dijkstra method changes the value of each node's tag, info and weight.
     * Thus the method calls resetTag, resetInfo and resetPre functions that resets the tag ,the info and the weight
     * that changed.
     * The method uses Dijkstra algorithm to build a List od nodes: dest --> ... -->src
     * Thus the method need to reverse the list later.
     * Complexity: O((|V|+|E|)log|V|), |V|=number of nodes, |E|=number of edges.
     *
     * @param src  - start node
     * @param dest - end (target) node
     * @return List of nodes.
     */
    @Override
    public List<NodeData> shortestPath(int src, int dest) {
        List<NodeData> list = new LinkedList<>();
        if (this.graph.getNode(src) == null) {
            throw new RuntimeException("This graph does not contain key " + src);
        }
        if (this.graph.getNode(dest) == null) {
            throw new RuntimeException("This graph does not contain key " + dest);
        }
        if (shortestPathDist(src, dest) == -1) {
            return null;
        }
        if (src == dest) {
            list.add(this.graph.getNode(dest));
            return list;
        }
        Dijkstra((Node_Data) this.graph.getNode(src), (Node_Data) this.graph.getNode(dest));
        Node_Data src2 = (Node_Data) this.graph.getNode(src);
        Node_Data dest2 = (Node_Data) this.graph.getNode(dest);
        List<Node_Data> reverseList = new LinkedList<>();
        Node_Data temp = dest2;
        while (temp.getTag() != -1) {
            reverseList.add(temp);
            temp = (Node_Data) this.graph.getNode(temp.getTag());
        }
        Node_Data[] arr = reverseList.toArray(Node_Data[]::new);
        list.add(src2);
        for (int i = arr.length - 1; i >= 0; i--) {
            list.add(arr[i]);
        }
        resetInfo();
        resetTag();
        resetWeight();
        return list;
    }

    @Override
    public NodeData center() {
        if (!isConnected()) {
            return null;
        }
        HashMap<NodeData, Double> centerNode = new HashMap<>();
        for (int i = 0; i < graph.nodeSize(); i++) {
            Double max = Double.MIN_VALUE;
            for (int j = 0; j < graph.nodeSize(); j++) {
                if (i != j) {
                    Double maxDis = shortestPathDist(graph.getNode(i).getKey(), graph.getNode(j).getKey());
                    if (maxDis > max) {
                        max = maxDis;
                    }
                }
            }
            centerNode.put(graph.getNode(i), max);
        }
        return Collections.min(centerNode.entrySet(), Map.Entry.comparingByValue()).getKey();
    }

    @Override
    public List<NodeData> tsp(List<NodeData> cities) {
        if (cities.isEmpty()) {
            return null;
        }
        ArrayList<NodeData> path = new ArrayList<>();
        NodeData start = cities.get(0);
        cities.remove(start);
        path.add(start);
        while (cities.size() > 0) {
            int closet = -1;
            double min = Double.MAX_VALUE;
            for (int i = 0; i < cities.size(); i++) {
                double minDis = shortestPathDist(start.getKey(), (cities.get(i).getKey()));
                if (minDis < min) {
                    min = minDis;
                    closet = i;
                }
            }
            List<NodeData> add = (shortestPath(start.getKey(), cities.get(closet).getKey()));
            add.remove(0);
            path.addAll(add);
            start = cities.get(closet);
            cities.remove(start);
        }
        return path;

    }

    /**
     * Saves this weighted (directed) graph to the given
     * file name - in JSON format
     *
     * @param file - the file name.
     * @return true - iff the file was successfully saved.
     */
    @Override
    public boolean save(String file) throws JSONException {

        String json = toJson();

        try {
            FileWriter fw = new FileWriter(file);
            fw.write(json);
            fw.flush();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * Load a graph to this graph algorithm.
     * if the file was successfully loaded - the underlying graph
     * of this class will be changed (to the loaded one), in case the
     *
     * @param file - file name of JSON file
     * @return true - iff the graph was successfully loaded.
     */
    @Override
    public boolean load(String file) {
        try {
            //create a Gson object
            DirectedWeightedGraph newG = new DWGraph();
            //read from file as JsonObject
            JsonObject json = new JsonParser().parse(new FileReader(file)).getAsJsonObject();
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
            this.graph = newG;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }
        return true;
        // try
        //            FileReader fr = new FileReader(new File("data/in/data.json"));
        //
        //            String json = "";
        //            while(fr.ready())
        //            {
        //                json += (char)fr.read();
        //            }
        //
        //           // JsonElement element = JsonParser.
        //
        //            //System.out.println(element.toString());
        //        }
        //
        //        catch(Exception ex)
        //        {
        //            ex.printStackTrace();
        //            return false;

        //return true;

    }

    /**
     * This private method resets the values of all the tags of the nodes in the graph.
     * Reset the value = change it back to default value: -1.
     */
    private void resetTag() {
        for (Iterator<NodeData> it = this.graph.nodeIter(); it.hasNext(); ) {
            Node_Data n = (Node_Data) it.next();
            n.setTag(-1);
        }
    }

    private boolean bfs(Node_Data n) {
        Queue<Node_Data> queue = new LinkedList<>();
        n.setTag(1);
        int counter = 1;
        queue.add(n);
        while (!queue.isEmpty()) {
            Node_Data temp = queue.poll();
            Iterator<EdgeData> edges = this.graph.edgeIter(temp.getKey());
            for (Iterator<EdgeData> it = edges; it.hasNext(); ) {
                edge next = (edge) it.next();
                Node_Data dest = (Node_Data) this.graph.getNode(next.getDest());
                if (dest.getTag() == -1) {
                    dest.setTag(1);
                    queue.add(dest);
                    counter++;
                }
            }
        }
        return (counter == this.graph.nodeSize());
    }

    private double Dijkstra(Node_Data src, Node_Data dest) {
        double shortest = Integer.MAX_VALUE;
        PriorityQueue<Node_Data> pq = new PriorityQueue<>(this.graph.nodeSize(), new Comparator<Node_Data>() {
            @Override
            public int compare(Node_Data o1, Node_Data o2) {
                return Double.compare(o1.getWeight(), o2.getWeight());
            }
        });
        src.setWeight(0.0);
        pq.add(src);
        while (!pq.isEmpty()) {
            Node_Data temp = pq.poll();
            for (Iterator<EdgeData> it = this.graph.edgeIter(temp.getKey()); it.hasNext(); ) {
                EdgeData e = it.next();
                Node_Data n = (Node_Data) this.graph.getNode(e.getDest());
                if (n.getInfo() == "White") {
                    if (n.getWeight() > temp.getWeight() + e.getWeight()) {
                        n.setWeight(Math.min(n.getWeight(), temp.getWeight() + e.getWeight()));
                        n.setTag(temp.getKey());
                    }
                    pq.add(n);
                }
            }
            temp.setInfo("Black");
            if (temp.getKey() == dest.getKey()) {
                return temp.getWeight();
            }
        }
        return shortest;
    }

    /**
     * This private method resets the value of info in each node in the graph.
     * Reset the value = change it back to default value: White
     */
    private void resetInfo() {
        for (Iterator<NodeData> it = this.graph.nodeIter(); it.hasNext(); ) {
            Node_Data n = (Node_Data) it.next();
            n.setInfo("White");
        }
    }

    /**
     * This private method resets the value of weight in each node in the graph.
     * Reset the value = change it back to default value: Double.MAX_VALUE (infinity).
     */
    private void resetWeight() {
        for (Iterator<NodeData> it = this.graph.nodeIter(); it.hasNext(); ) {
            Node_Data n = (Node_Data) it.next();
            n.setWeight(Double.MAX_VALUE);
        }
    }

    public String toJson() {
        try {
            JSONObject jsonObject = new JSONObject();
            JSONObject node;
            JSONObject edge;
            JSONArray nodes = new JSONArray();
            JSONArray edges = new JSONArray();
            for (Iterator<NodeData> it = this.graph.nodeIter(); it.hasNext(); ) {
                Node_Data n = (Node_Data) it.next();
                node = new JSONObject();
                node.put("pos", n.getLocation());
                node.put("id", n.getKey());
                node.put("weight", n.getWeight());
                nodes.put(node);
                for (Iterator<EdgeData> iter = this.graph.edgeIter(n.getKey()); iter.hasNext(); ) {
                    edge e = (edge) iter.next();
                    edge = new JSONObject();
                    edge.put("src", e.getSrc());
                    edge.put("w", e.getWeight());
                    edge.put("dest", e.getDest());
                    edges.put(edge);
                }
            }
            jsonObject.put("Nodes", nodes);
            jsonObject.put("Edges", edges);

            return jsonObject.toString(2);
        } catch (Exception ex) {
            return "{}";
        }
    }

    public boolean compareData(GraphAlgo other) {
        return toJson().compareTo(other.toJson()) == 0;
    }
}



