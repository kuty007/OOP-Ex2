public class Ex2 {

    public static void main(String[] args) {
        App app = new App();

        DWGraph testedGraph = DWGraph.loadFile("data/in/G1.json");

        app.setData(testedGraph);
        app.show();
    }

}
