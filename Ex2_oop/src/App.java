import org.json.JSONException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class App extends JFrame implements ActionListener {

    // Data
    public DWGraph graph;
    public double minPosX = Double.MAX_VALUE;
    public double maxPosX = Double.MIN_VALUE;
    public double minPosY = Double.MAX_VALUE;
    public double maxPosY = Double.MIN_VALUE;


    // App Elements
    private JFrame frame = null;
    private String title = "Graph GUI";
    private JPanel mainPanel = null;
    private JMenuBar menu;
    private JMenu menuFile, menuFunctions, menuEdit;
    JMenuItem center;
    JMenuItem TSP;
    JMenuItem IsConnected;
    JMenuItem save;
    JMenuItem load;
    JMenuItem addNode;
    JMenuItem showLocation;
    JMenuItem addedge;
    JMenuItem removeedge;
    JMenuItem removeNode;
    JMenuItem shortestPath;
    JMenuItem shortestPathDist;
    GraphAlgo origNGraph;
    GraphAlgo copYGraph;
    //ADDnode;
    //JTextField removenode;
    int height;
    int width;
    int radius = 10;
    NodeData CenterNODE = null;
    int showLocat;
    boolean loc;
    ArrayList<NodeData> tsppath;



    public App(DWGraph gr) {
        this.graph =gr;
        DWGraph g = graph;
        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        height = size.height / 2;
        width = size.width / 2;
        this.setSize(width, height);
        menu = new JMenuBar();
        menuFile = new JMenu("File");
        menuFunctions = new JMenu("Functions");
        showLocation = new JMenuItem(" show Location");
        shortestPath = new JMenuItem("shortest Path");
        menuEdit = new JMenu("Edit");
        mainPanel = new JPanel();
        center = new JMenuItem("center");
        TSP = new JMenuItem("TSP");
        addedge = new JMenuItem("Add Edge");
        removeedge = new JMenuItem(" Remove Edge");
        addedge.addActionListener(this);
        removeedge.addActionListener(this);
        IsConnected = new JMenuItem("IsConnected");

        save = new JMenuItem("Save");
        load = new JMenuItem("Load");
        addNode = new JMenuItem("Add Node");
        removeNode = new JMenuItem("Remove Node");
        shortestPathDist = new JMenuItem("shortestPathDist");
        center.addActionListener(this);
        TSP.addActionListener(this);
        IsConnected.addActionListener(this);
        save.addActionListener(this);
        load.addActionListener(this);
        addNode.addActionListener(this);
        removeNode.addActionListener(this);
        shortestPathDist.addActionListener(this);
        showLocation.addActionListener(this);
        shortestPath.addActionListener(this);
        menuFile.add(save);
        menuFile.add(load);
        menuFunctions.add(TSP);
        menuFunctions.add(center);
        menuFunctions.add(shortestPathDist);
        menuFunctions.add(showLocation);
        menuFunctions.add(IsConnected);
        menuFunctions.add(TSP);
        menuFunctions.add(shortestPath);
        menuEdit.add(addNode);
        menuEdit.add(removeNode);
        menuEdit.add(addedge);
        menuEdit.add(removeedge);
        menu.add(menuFile);
        menu.add(menuFunctions);
        menu.add(menuEdit);
        this.setJMenuBar(menu);
        mainPanel = new JPanel();
        menu.setBounds(0, 0, width, height);
        this.add(mainPanel);
        this.add(new App.DrewGraph(g));
        this.setVisible(true);
        this.setTitle("GUI Graph");
        this.setResizable(false);
        //this.add(button);
        showLocat = 0;
        loc = false;
    }


    public class DrewGraph extends JPanel {
        public DrewGraph(DWGraph g) {
            origNGraph = new GraphAlgo();
            copYGraph = new GraphAlgo();
            origNGraph.init(g);
            copYGraph.init(g);
            try {
                getMinMAx();
            } catch (Exception e) {
                e.printStackTrace();
            }
            repaint();
        }


        public void getMinMAx() throws Exception {
            Iterator<NodeData> it = origNGraph.getGraph().nodeIter();
            while (it.hasNext()) {
                NodeData node = it.next();
                if (maxPosX < node.getLocation().x()) {
                    maxPosX = node.getLocation().x();
                }
                if (maxPosY < node.getLocation().y()) {
                    maxPosY = node.getLocation().y();
                }
                if (minPosX > node.getLocation().x()) {
                    minPosX = node.getLocation().x();
                }
                if (minPosY > node.getLocation().y()) {
                    minPosY = node.getLocation().y();
                }
            }
        }


        @Override
        public void paint(Graphics gg) {
            super.paintComponents(gg);
            double Xscaled = ((getWidth() - 20) / Math.abs(maxPosX - minPosX) * 0.7);
            double Yscaled = ((getHeight() - 20) / Math.abs(maxPosY - minPosY) * 0.7);
            Graphics2D drew = (Graphics2D) gg;
            drew.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            Iterator<NodeData> iterator = origNGraph.getGraph().nodeIter();
            while (iterator.hasNext()) {
                NodeData node = iterator.next();
                gg.setColor(new Color(13, 162, 176, 161));
                double x = (node.getLocation().x() - minPosX) * Xscaled * 0.97 + 27;
                double y = (node.getLocation().y() - minPosY) * Yscaled * 0.97 + 27;
                gg.fillOval((int) x, (int) y, 20, 20);
                gg.setColor(new Color(236, 144, 22, 247));
                gg.setFont(new Font("David", Font.BOLD, 15));
                gg.drawString("" + node.getKey(), (int) (x + 3), (int) (y + 13));
                if (loc) {
                    String location = node.getLocation().x() + "," + node.getLocation().y();
                    gg.setColor(new Color(0, 0, 0));
                    gg.drawString(location, (int) x - 10, (int) y + 30);

                }
            }
            if (CenterNODE != null) {
                double x = (CenterNODE.getLocation().x() - minPosX) * Xscaled * 0.97 + 27;
                double y = (CenterNODE.getLocation().y() - minPosY) * Yscaled * 0.97 + 27;
                gg.setColor(new Color(176, 13, 124, 161));
                gg.fillOval((int) x, (int) y, 20, 20);
                gg.setColor(new Color(236, 144, 22, 247));
                gg.setFont(new Font("David", Font.BOLD, 15));
                gg.drawString("" + CenterNODE.getKey(), (int) (x + 3), (int) (y + 13));
                gg.setColor(new Color(0, 0, 0));
                gg.drawString("Center", (int) x - 5, (int) y + 25);

            }
            Iterator<EdgeData> itr = origNGraph.getGraph().edgeIter();
            while (itr.hasNext()) {
                EdgeData edge = itr.next();
                double XSrc, XDst, YSrc, YDst;
                XSrc = (origNGraph.getGraph().getNode(edge.getSrc()).getLocation().x() - minPosX) * Xscaled + 30;
                YSrc = (origNGraph.getGraph().getNode(edge.getSrc()).getLocation().y() - minPosY) * Yscaled + 30;
                XDst = (origNGraph.getGraph().getNode(edge.getDest()).getLocation().x() - minPosX) * Xscaled + 30;
                YDst = (origNGraph.getGraph().getNode(edge.getDest()).getLocation().y() - minPosY) * Yscaled + 30;
                int x1 = (int) XSrc;
                int y1 = (int) YSrc;
                int x2 = (int) XDst;
                int y2 = (int) YDst;
                gg.setColor(Color.black);
                drew.setStroke(new BasicStroke((float) 0.85));
                drawArrow(gg, x1, y1, x2, y2, 5, 5);
            }
            /*
            if (!tsppath.isEmpty()) {
                for (int i = 0; i < tsppath.size() - 1; i++) {
                    EdgeData edge = origNGraph.getGraph().getEdge(tsppath.get(i).getKey(), tsppath.get(i + 1).getKey());
                    int XSrc, XDst, YSrc, YDst;
                    XSrc = (int) ((origNGraph.getGraph().getNode(edge.getSrc()).getLocation().x() - minPosX) * Xscaled + 30);
                    YSrc = (int) ((origNGraph.getGraph().getNode(edge.getSrc()).getLocation().y() - minPosY) * Yscaled + 30);
                    XDst = (int) ((origNGraph.getGraph().getNode(edge.getDest()).getLocation().x() - minPosX) * Xscaled + 30);
                    YDst = (int) ((origNGraph.getGraph().getNode(edge.getDest()).getLocation().y() - minPosY) * Yscaled + 30);
                    gg.setColor(Color.red);
                    drew.setStroke(new BasicStroke((float) 0.85));
                    drawArrow(gg, XSrc, YSrc, XDst, YDst, 5, 5);

             */
        }

    }


    private void drawArrow(Graphics g, int x1, int y1, int x2, int y2, int d, int h) {
        int X = x2 - x1, Y = y2 - y1;
        double D = Math.sqrt(X * X + Y * Y);
        double xm = D - d, xn = xm, ym = h, yn = -h, x;
        double sin = Y / D, cos = X / D;

        x = xm * cos - ym * sin + x1;
        ym = xm * sin + ym * cos + y1;
        xm = x;

        x = xn * cos - yn * sin + x1;
        yn = xn * sin + yn * cos + y1;
        xn = x;

        int[] xpoints = {x2, (int) xm, (int) xn};
        int[] ypoints = {y2, (int) ym, (int) yn};

        g.drawLine(x1, y1, x2, y2);
        g.fillPolygon(xpoints, ypoints, 3);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == center) {
            try {
                if (CenterNODE == null) {
                    NodeData centerN = origNGraph.center();
                    this.CenterNODE = centerN;
                    repaint();
                } else {
                    this.CenterNODE = null;
                    repaint();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        if (e.getSource() == showLocation) {
            this.showLocat += 1;
            if (showLocat % 2 != 0) {
                loc = true;
            } else {
                loc = false;
            }

            repaint();

        }
        if (e.getSource() == IsConnected) {
            try {
                boolean IsC = origNGraph.isConnected();
                JDialog d = new JDialog(this, "isConnected");
                d.setSize(200, 100);
                if (IsC) {
                    JLabel l = new JLabel("This Graph is Connected");
                    d.add(l);
                } else {
                    JLabel lb = new JLabel("This Graph is not Connected");
                    d.add(lb);
                }
                d.setVisible(true);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        if (e.getSource() == addNode) {
            try {
                int id;
                double x;
                double y;
                String Id = JOptionPane.showInputDialog(this, "enter id:");
                String xl = JOptionPane.showInputDialog(this, "enter: x location");
                String yl = JOptionPane.showInputDialog(this, "enter y location:");
                id = Integer.parseInt(Id);
                x = Double.parseDouble(xl);
                y = Double.parseDouble(yl);
                GeoLocation l = new geoLo(x, y, 0);
                NodeData newnode = new Node_Data(id, l);
                this.origNGraph.getGraph().addNode(newnode);
                repaint();
            } catch (HeadlessException | NumberFormatException ex) {
                ex.printStackTrace();
            }
        } else if (e.getSource() == removeNode) {
            int id;
            String Id = JOptionPane.showInputDialog(this, "enter id:");
            id = Integer.parseInt(Id);
            this.origNGraph.getGraph().removeNode(id);
            if (CenterNODE != null && CenterNODE.getKey() == id) {
                CenterNODE = null;
            }
            repaint();
        }
        if (e.getSource() == removeedge) {
            int ids, idd;
            String Ids = JOptionPane.showInputDialog(this, "enter src node:");
            String Idd = JOptionPane.showInputDialog(this, "enter dst node:");
            ids = Integer.parseInt(Ids);
            idd = Integer.parseInt(Idd);
            this.origNGraph.getGraph().removeEdge(ids, idd);
            repaint();
        } if (e.getSource() == addedge) {
            int ids, idd;
            String Ids = JOptionPane.showInputDialog(this, "enter src node:");
            String Idd = JOptionPane.showInputDialog(this, "enter dst node:");
            ids = Integer.parseInt(Ids);
            idd = Integer.parseInt(Idd);
            this.origNGraph.getGraph().connect(ids, idd,0);
            repaint();
        }
        if (e.getSource() == shortestPathDist) {
            String IdS = JOptionPane.showInputDialog(this, "enter start node id:");
            String IdF = JOptionPane.showInputDialog(this, "enter dst node id:");
            int ids = Integer.parseInt(IdS);
            int idd = Integer.parseInt(IdF);
            double ans = this.origNGraph.shortestPathDist(ids, idd);
            JOptionPane.showMessageDialog(this, " the shortest Path is " + ans);

        }
        if (e.getSource() == TSP) {
            String tovist = JOptionPane.showInputDialog(this, "enter nodes to visit like this: 1,3,7,9");
            String[] lis = tovist.split(",");
            ArrayList<NodeData> tsp = new ArrayList<NodeData>();
            for (int i = 0; i < lis.length; i++) {
                tsp.add(this.origNGraph.getGraph().getNode(Integer.parseInt(lis[i])));
            }
            this.tsppath = (ArrayList<NodeData>) this.origNGraph.tsp(tsp);
            JOptionPane.showMessageDialog(this, " the shortest Path is " + (this.tsppath.toString()));
        }
        if (e.getSource() == shortestPath) {
            String IdS = JOptionPane.showInputDialog(this, "enter start node id:");
            String IdF = JOptionPane.showInputDialog(this, "enter dst node id:");
            int ids = Integer.parseInt(IdS);
            int idd = Integer.parseInt(IdF);
            ArrayList<NodeData> ans = (ArrayList<NodeData>) this.origNGraph.shortestPath(ids, idd);
            JOptionPane.showMessageDialog(this, " the shortest Path is " + (ans.toString()));

        }


        if (e.getSource() == save) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File("."));
            int response = fileChooser.showSaveDialog(null); // select file to Open.
            if (response == JFileChooser.APPROVE_OPTION) {
                String jsonPath = fileChooser.getSelectedFile().getAbsolutePath();
                try {
                    this.origNGraph.save(jsonPath);
                } catch (JSONException ex) {
                    ex.printStackTrace();
                }
            }


        }
        if (e.getSource() == load) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File("."));
            int response = fileChooser.showOpenDialog(null); // select file to Open.
            if (response == JFileChooser.APPROVE_OPTION) {
                String jsonPath = fileChooser.getSelectedFile().getAbsolutePath();
                DWGraph n = DWGraph.loadFile(jsonPath);
                try {
                    new DrewGraph(n);
                    setVisible(true);
                    repaint();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

        }


    }


    public static void run(DWGraph gr) {
        new App(gr);
    }
}






