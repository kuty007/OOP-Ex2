import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Iterator;

public class App extends JComponent {

    // Data
    public DWGraph graph;

    public double minPosX = Double.MAX_VALUE;
    public double maxPosX = Double.MIN_VALUE;
    public double minPosY = Double.MAX_VALUE;
    public double maxPosY = Double.MIN_VALUE;

    // App Data
    private boolean opened = false;

    // App Elements
    private JFrame frame = null;
    private String title = "TITLE";

    private JPanel mainPanel = null;

    private JMenuBar menu;
    private JMenu menuFile, menuFunctions, menuEdit;


    public App() {
    }

    public void init() {
        if (opened) {
            return;
        }

        // Frame
        frame = new JFrame();
        frame.setSize(800, 800);
        frame.setTitle(title);

        // Menu
        menu = new JMenuBar();

        menuFile = new JMenu("File");

        JMenuItem item1 = new JMenuItem("item1");
        item1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                item1Click();
            }
        });

        JMenuItem item2 = new JMenuItem("item2");
        item2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                item2Click();
            }
        });
        // TODO

        menuFile.add(item1);
        menuFile.add(item2);

        menuFunctions = new JMenu("Functions");
        // TODO

        menuEdit = new JMenu("Edit");
        // TODO

        menu.add(menuFile);
        menu.add(menuFunctions);
        menu.add(menuEdit);

        frame.setJMenuBar(menu);

        // Main Panel
        mainPanel = new JPanel();
        mainPanel.setBounds(0, 0, frame.getWidth(), frame.getHeight());

        frame.add(mainPanel);
    }

    public void clean() {
        mainPanel.removeAll();
    }

    public void setData(DWGraph graph) {

        init();
        clean();

        this.graph = graph;

        // Print the graph
        getMinMax();

/*
        Iterator<NodeData> itForNodes = graph.nodeIter();
        while (itForNodes.hasNext()) {
            Graphics g = null;
            addNode((NodeData) itForNodes.next(), g);
            Iterator<EdgeData> itForEdges = graph.edgeIter(itForNodes.next().getKey());
            while (itForEdges.hasNext()) {
                addEdge(itForEdges.next());

 */
            }



    private void getMinMax() {
        Iterator itForNodes = graph.nodeIter();
        while (itForNodes.hasNext()) {
            NodeData node = (NodeData) itForNodes.next();
            if (minPosX > node.getLocation().x()) {
                minPosX = node.getLocation().x();
            }
            if (maxPosX < node.getLocation().x()) {
                maxPosX = node.getLocation().x();
            }

            if (minPosY > node.getLocation().y()) {
                minPosY = node.getLocation().y();
            }
            if (maxPosY < node.getLocation().y()) {
                maxPosY = node.getLocation().y();
            }
        }
    }
/*
    private void addNode(NodeData node, Graphics g) {
        // TODO: Add Node with Image
        // TODO: Add JLabel with empty text. add on click or
        //       context menu option on the label
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setPaint(Color.blue);
        int height, width;
        height = 800;
        width = 800;
        double xs = width / Math.abs(maxPosX - minPosX);
        double ys = height / Math.abs(maxPosY - minPosY);
        double x = node.getLocation().x();
        double y = node.getLocation().y();
        int scaled_y = (int) (x * xs);
        int scaled_x = (int) (y * ys);
        g2d.setPaint(Color.gray);
        g2d.fillOval(scaled_x, scaled_y, 15, 15);
        g2d.setPaint(Color.orange);
        g2d.drawOval(scaled_x, scaled_y, 15, 15);
        g2d.setPaint(Color.BLACK);
        g2d.drawString(String.valueOf(node.getKey()), scaled_x, scaled_y);


    }

 */

    private void addEdge(EdgeData edge) {
        int height, width;
        height = 800;
        width = 800;
        double x = width / Math.abs(maxPosX - minPosX);
        double y = height / Math.abs(maxPosY - minPosY);

    }

    public void paintNodes(Graphics g) {

        int height, width;
        height = 800;
        width = 800;
        double xs = width / Math.abs(maxPosX - minPosX);
        double ys = height / Math.abs(maxPosY - minPosY);
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setPaint(Color.blue);
        Iterator it = graph.nodeIter();
        while (it.hasNext()) {
            NodeData node = (NodeData) it.next();
            double x = node.getLocation().x();
            double y = node.getLocation().y();
            int scaled_y = (int) (x * xs);
            int scaled_x = (int) (y * ys);
            g2d.setPaint(Color.gray);
            g2d.fillOval(scaled_x, scaled_y, 15, 15);
            g2d.setPaint(Color.orange);
            g2d.drawOval(scaled_x, scaled_y, 15, 15);
            g2d.setPaint(Color.BLACK);
            g2d.drawString(String.valueOf(node.getKey()), scaled_x, scaled_y);
        }
    }




    public void show() {
        frame.setVisible(true);
    }

    // Events
    private void item1Click() {

    }

    private void item2Click() {

    }
}
