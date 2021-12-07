import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

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
        if (this.graph.edgeIter()| this.graph.getV().contains(dest)) {
            throw new RuntimeException("One or more of your keys does not exist in the graph!");
        }
        if(src == dest){
            return 0;
        }
        resetInfo();
        resetTag();
        resetWeight();
        double d = Dijkstra(this.graph.getNode(src), this.graph.getNode(dest));
        resetInfo();
        resetTag();
        resetWeight();
        if (d == Integer.MAX_VALUE) {
            return -1;
        }
        return d;

    }

    @Override
    public List<NodeData> shortestPath(int src, int dest) {
        return null;
    }

    @Override
    public NodeData center() {
        return null;
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
}
