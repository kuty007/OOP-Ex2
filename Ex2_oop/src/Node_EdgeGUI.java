import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.util.Iterator;
public class Node_EdgeGUI extends JComponent {
    private static DWGraph graph;
    private int height;
    private int width;
    private static Iterator<NodeData> NodeIterator;
    private static Iterator<EdgeData> EdgeIterator;
    public static double Xmax = Double.MIN_VALUE;
    public static double Ymax = Double.MIN_VALUE;
    public static double Xmin = Double.MAX_VALUE;
    public static double Ymin = Double.MAX_VALUE;

    public static void scale() {
        NodeIterator = graph.nodeIter();
        while (NodeIterator.hasNext()) {
            NodeData node = NodeIterator.next();
            if (Xmax < node.getLocation().x()) {
                Xmax = node.getLocation().x();
            }
            if (Ymax < node.getLocation().y()) {
                Ymax = node.getLocation().y();
            }
            if (Xmin > node.getLocation().x()) {
                Xmin = node.getLocation().x();
            }
            if (Ymin > node.getLocation().y()) {
                Ymin = node.getLocation().y();
            }
        }
    }

    public double[] scales() {
        double[] scales = new double[2];
        scales[0] = width / Math.abs(Xmax - Xmin);
        scales[1] = height / Math.abs(Ymax - Ymin);
        return scales;
    }

    public void paintNodes(Graphics g) {
        scale();
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setPaint(Color.blue);

        Iterator it = graph.nodeIter();
        while (it.hasNext()) {
            NodeData node = (NodeData) it.next();
            double x = node.getLocation().x();
            double y = node.getLocation().y();
            int scaled_y = (int) (x * scales()[0]);
            int scaled_x = (int) (y * scales()[1]);
            g2d.setPaint(Color.gray);
            g2d.fillOval(scaled_x, scaled_y, 15, 15);
            g2d.setPaint(Color.orange);
            g2d.drawOval(scaled_x, scaled_y, 15, 15);
            g2d.setPaint(Color.BLACK);
            g2d.drawString(String.valueOf(node.getKey()), scaled_x, scaled_y);
        }
    }
    public void paintEdges(Graphics g) {
        scale();
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setPaint(Color.BLACK);
        EdgeIterator = graph.edgeIter();
        while (EdgeIterator.hasNext()) {
            EdgeData edge = EdgeIterator.next();
            double xSrc = graph.getNode(edge.getSrc()).getLocation().x();
            double ySrc = graph.getNode(edge.getSrc()).getLocation().y();
            double xDst = graph.getNode(edge.getDest()).getLocation().x();
            double yDst = graph.getNode(edge.getDest()).getLocation().y();
            int scaled_xSrc = (int) (xSrc * scales()[0]);
            int scaled_xDst = (int) (xDst * scales()[0]);
            int scaled_ySrc = (int) (ySrc * scales()[1]);
            int scaled_yDst = (int) (yDst * scales()[1]);
            g2d.draw(new Line2D.Double(scaled_xSrc, scaled_ySrc, scaled_xDst, scaled_yDst));
        }
    }
}




