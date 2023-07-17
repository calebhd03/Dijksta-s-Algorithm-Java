public class App {
    public static void main(String[] args) throws Exception {
        DijkstraAlgorithm dA = new DijkstraAlgorithm();
        dA.CreateGraph();
        dA.PrintTree();
        dA.GenerateSpanningTree();
        dA.OutPutSpanningTree();
    }
}
