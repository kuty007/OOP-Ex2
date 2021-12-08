import org.json.JSONException;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class GraphAlgoTest {

    DWGraph graph1 = DWGraph.loadFile("data/in/G1.json");
    GraphAlgo g1 = new GraphAlgo();
    DWGraph graph2 = DWGraph.loadFile("data/in/G2.json");
    GraphAlgo g2 = new GraphAlgo();
    DWGraph graph3 = DWGraph.loadFile("data/in/G3.json");
    GraphAlgo g3 = new GraphAlgo();



    @Test
    void init() throws JSONException {
        g3.init(graph1);
        g3.save("data/out/json.json");
    }

    @Test
    void getGraph() {
        g3.init(graph1);
        g3.getGraph();
    }

    @Test
    void copy() {
    }

    @Test
    void isConnected() {

    }

    @Test
    void shortestPathDist() {
    }

    @Test
    void shortestPath() {
    }

    @Test
    void center() {
    }

    @Test
    void tsp() {
    }

    @Test
    void save() {
    }

    @Test
    void load() {
    }
}