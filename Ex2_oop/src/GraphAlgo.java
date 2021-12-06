import java.util.List;

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
        return false;
    }

    @Override
    public double shortestPathDist(int src, int dest) {
        return 0;
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
}
