import java.util.*;

public class GraphAlgo implements DirectedWeightedGraphAlgorithms {
    private DirectedWeightedGraph graph;

    public GraphAlgo() {
        this.graph = new DWGraph();
    }

    @Override
    public void init(DirectedWeightedGraph g) {
        this.graph = g;

    }

    @Override
    public DirectedWeightedGraph getGraph() {
        return graph;
    }

    @Override
    public DirectedWeightedGraph copy() {
        return new DWGraph(this.graph);
    }

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
        double d = Dijkstra((Node_Data)this.graph.getNode(src), (Node_Data)this.graph.getNode(dest));
        resetInfo();
        resetTag();
        resetWeight();
        if (d == Integer.MAX_VALUE) {
            return -1;
        }
        return d;

    }

    @Override
    public List<Node_Data> shortestPath(int src, int dest) {
        List<Node_Data> list = new LinkedList<>();
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
            list.add((Node_Data) this.graph.getNode(dest));
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
        if (isConnected() == false) {
            return null;
        }


    }
    @Override
    public List<NodeData> tsp(List<NodeData> cities) {
        return null;
    }

    @Override
    public boolean save(String file) {
        return false;
    }

    @Override
    public boolean load(String file) {
        return false;
    }

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
    private void resetInfo() {
        for (Iterator<NodeData> it = this.graph.nodeIter(); it.hasNext(); ) {
            Node_Data n = (Node_Data) it.next();
            n.setInfo("White");
        }
    }
    private void resetWeight() {
        for (Iterator<NodeData> it = this.graph.nodeIter(); it.hasNext(); ) {
            Node_Data n = (Node_Data) it.next();
            n.setWeight(Double.MAX_VALUE);
        }
    }

}
