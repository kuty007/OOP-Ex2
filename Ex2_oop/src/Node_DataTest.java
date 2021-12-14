import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Node_DataTest {

    geoLo a1 = new geoLo (19,20,22);
    geoLo a2 = new geoLo(1,2,3);
    geoLo a3 = new geoLo(5,6,7);
    geoLo a4 = new geoLo(8,9,10);
    Node_Data b1= new Node_Data(2, a4);
    Node_Data b2 = new Node_Data(3, a3);
    Node_Data b3 = new Node_Data(4, a2);
    Node_Data b4 = new Node_Data(5, a1);

    @Test
    void getKey() {
        assertEquals(2, b1.getKey());
        assertEquals(3, b2.getKey());
        assertEquals(4, b3.getKey());
        assertEquals(5, b4.getKey());
    }

    @Test
    void getLocation() {
        assertEquals(8, b1.getLocation().x());
        assertEquals(9, b1.getLocation().y());
        assertEquals(6, b2.getLocation().y());
        assertEquals(3, b3.getLocation().z());
    }

    @Test
    void setLocation() {
        geoLo P6 = new geoLo(3,4,5);
        b2.setLocation(P6);
        assertEquals(3, b2.getLocation().x());
        assertEquals(4, b2.getLocation().y());
    }

    @Test
    void getWeight() {
        assertEquals(Double.MAX_VALUE, b1.getWeight());
        assertEquals(Double.MAX_VALUE, b2.getWeight());
        assertEquals(Double.MAX_VALUE, b3.getWeight());
        assertEquals(Double.MAX_VALUE, b4.getWeight());
    }

    @Test
    void setWeight() {
        b1.setWeight(4);
        b2.setWeight(5);
        b3.setWeight(6);
        b4.setWeight(7);
        assertEquals(4, b1.getWeight());
        assertEquals(5, b2.getWeight());
        assertEquals(6, b3.getWeight());
        assertEquals(7, b4.getWeight());
    }

    @Test
    void getInfo() {
        assertEquals("White", b1.getInfo());
        assertEquals("White", b2.getInfo());
        assertEquals("White", b3.getInfo());
        assertEquals("White", b4.getInfo());
    }

    @Test
    void setInfo() {
        b1.setInfo("3");
        b2.setInfo("4");
        b3.setInfo("5");
        b4.setInfo("6");
        assertEquals("3", b1.getInfo());
        assertEquals("4", b2.getInfo());
        assertEquals("5", b3.getInfo());
        assertEquals("6", b4.getInfo());
    }

    @Test
    void getTag() {
        assertEquals(-1, b1.getTag());
        assertEquals(-1, b2.getTag());
        assertEquals(-1, b3.getTag());
        assertEquals(-1, b4.getTag());
    }

    @Test
    void setTag() {
        b1.setTag(3);
        b2.setTag(4);
        b3.setTag(5);
        b4.setTag(6);
        assertEquals(3, b1.getTag());
        assertEquals(4, b2.getTag());
        assertEquals(5, b3.getTag());
        assertEquals(6, b4.getTag());
    }
}