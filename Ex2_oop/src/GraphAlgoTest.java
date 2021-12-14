import org.json.JSONException;


import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class GraphAlgoTest {

    DWGraph graph1 = DWGraph.loadFile("data/in/G1.json");
    GraphAlgo g1 = new GraphAlgo();
    DWGraph graph2 = DWGraph.loadFile("data/in/G2.json");
    GraphAlgo g2 = new GraphAlgo();
    DWGraph graph3 = DWGraph.loadFile("data/in/G3.json");
    GraphAlgo g3 = new GraphAlgo();



    @Test
    void init() {
        try {
            String outPath = "data/out/json.json";

            g3.init(graph3);
            g3.save(outPath);

            assertEquals(false, g3.toJson().compareTo("{}") == 0);

            assertEquals(true, Files.exists(Path.of(outPath)));

            DWGraph created = DWGraph.loadFile(outPath);

            GraphAlgo g = new GraphAlgo();
            g.init(created);

            assertEquals(false, g.toJson().compareTo("{}") == 0);
            assertEquals(true, g3.compareData(g));
        } catch (Exception ex) {
            assertEquals(true, false);
        }
    }

    @Test
    void getGraph() {
        g3.init(graph1);
        assertEquals(graph1,g3.getGraph());
    }

    @Test
    void copy() {
        DirectedWeightedGraph copied = g1.copy();

        DWGraph copiedGraph = (DWGraph)copied;

        assertEquals(g1.getGraph().nodeSize(), copiedGraph.nodeSize());
        assertEquals(g1.getGraph().edgeSize(), copiedGraph.edgeSize());
    }

    @Test
    void isConnected() {
        assertEquals(true,g1.isConnected());
        assertEquals(true, g2.isConnected());
        assertEquals(true, g3.isConnected());
    }

    @Test
    void shortestPathDist() {
        g1.init(graph1);
        g2.init(graph2);
        g3.init(graph3);
        assertEquals(0, g1.shortestPathDist(0,0));
        assertEquals(0, g2.shortestPathDist(1,1));
        assertEquals(0, g3.shortestPathDist(4,4));
    }

    @Test
    void shortestPath() {
        g1.init(graph1);
        g2.init(graph2);
        g3.init(graph3);

        assertEquals(2, g1.shortestPath(0,16).size());
        assertEquals(8, g2.shortestPath(5,12).size());
        assertEquals(4, g3.shortestPath(3,7).size());
    }

    @Test
    void center() {
        g1.init(graph1);

        NodeData centerNode = g1.center();

        assertEquals(8, centerNode.getKey());
    }

    @Test
    void tsp() {
        g1.init(graph1);

        List<NodeData> cities = new LinkedList<NodeData>();

        cities.add(graph1.getNode(0));
        cities.add(graph1.getNode(5));
        cities.add(graph1.getNode(3));
        List<NodeData> res = g1.tsp(cities);
        List<NodeData> ans = new ArrayList<>();
        ans.add(graph1.getNode(0));
        ans.add(graph1.getNode(1));
        ans.add(graph1.getNode(2));
        ans.add(graph1.getNode(3));
        ans.add(graph1.getNode(4));
        ans.add(graph1.getNode(5));


        assertEquals(ans.toString(), res.toString());
    }

    @Test
    void save() {
        try {
            String outPath = "data/out/json.json";

            g1.init(graph1);
            g1.save(outPath);

            assertEquals(false, g1.toJson().compareTo("{}") == 0);

            assertEquals(true, Files.exists(Path.of(outPath)));

            DWGraph created = DWGraph.loadFile(outPath);

            GraphAlgo g = new GraphAlgo();
            g.init(created);

            assertEquals(false, g.toJson().compareTo("{}") == 0);
            assertEquals(true, g1.compareData(g));
        } catch (Exception ex) {
            assertEquals(true, false);
        }
    }

    @Test
    void load() {
        try {
            String outPath = "data/out/json.json";

            g1.init(graph1);
            g1.save(outPath);

            assertEquals(false, g1.toJson().compareTo("{}") == 0);

            assertEquals(true, Files.exists(Path.of(outPath)));

            DWGraph created = DWGraph.loadFile(outPath);

            GraphAlgo g = new GraphAlgo();
            g.init(created);

            assertEquals(false, g.toJson().compareTo("{}") == 0);
            assertEquals(true, g1.compareData(g));

            GraphAlgo gAfter = new GraphAlgo();
            gAfter.load(outPath);

            assertEquals(true, g1.compareData(gAfter));

        } catch (Exception ex) {
            assertEquals(true, false);
        }
    }
}