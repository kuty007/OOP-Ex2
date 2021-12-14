import org.junit.jupiter.api.Test;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

class DWGraphTest {

    DWGraph testedGraph = DWGraph.loadFile("data/in/G1.json");

    @Test
    void getNode() {
        geoLo gl = new geoLo(35.20752617756255,32.1025646605042,0.0);
        NodeData node = new Node_Data(2, gl);
        assertEquals(node.getKey(), testedGraph.getNode(2).getKey());
        assertEquals(node.getLocation().x(), testedGraph.getNode(2).getLocation().x());
    }

    @Test
    void getEdge() {
        assertEquals(1.5328553219807337, testedGraph.getEdge(8,9).getWeight());
        assertEquals(null, testedGraph.getEdge(16,14));
    }

    @Test
    void addNode() {
        geoLo gl = new geoLo(35.20752617756255,32.1025646605042,0.0);
        NodeData node = new Node_Data(2, gl);
        testedGraph.addNode(node);
        assertEquals(node.getLocation().x(), testedGraph.getNode(2).getLocation().x());
    }

    @Test
    void connect() {
        testedGraph.connect(16,14,8);
        assertEquals(16, testedGraph.getEdge(16,14).getSrc());
    }

    @Test
    void nodeIter() {
        Iterator<NodeData> it1= testedGraph.nodeIter();
        NodeData n1= it1.next();
        assertEquals(0, n1.getKey());
        assertEquals(35.19589389346247,n1.getLocation().x());
        NodeData n2= it1.next();
        assertEquals(1,n2.getKey());
        assertEquals(35.20319591121872,n2.getLocation().x());
    }

    @Test
    void edgeIter() {
        Iterator<EdgeData> it1 = testedGraph.edgeIter();
        EdgeData n1 = it1.next();
        assertEquals(16, n1.getSrc());
        assertEquals(15, n1.getDest());
    }

    @Test
    void testEdgeIter() {
        Iterator<EdgeData> it1= testedGraph.edgeIter(0);
        EdgeData n1= it1.next();
        assertEquals(0, n1.getSrc());
        assertEquals(16,n1.getDest());
    }

    @Test
    void removeNode() {
        assertEquals(5, testedGraph.removeNode(5).getKey());
        assertEquals(35.20797194027441, testedGraph.removeNode(6).getLocation().x());
    }

    @Test
    void removeEdge() {
        assertEquals(5, testedGraph.removeEdge(4,5).getDest());
        assertEquals(null , testedGraph.removeEdge(4,5));
    }

    @Test
    void nodeSize() {
        assertEquals(17, testedGraph.nodeSize());
        testedGraph.removeNode(16);
        assertEquals(16, testedGraph.nodeSize());
    }

    @Test
    void edgeSize() {
        assertEquals(36, testedGraph.edgeSize());
    }

    @Test
    void getMC() {
        assertEquals(0, testedGraph.getMC());
    }
}