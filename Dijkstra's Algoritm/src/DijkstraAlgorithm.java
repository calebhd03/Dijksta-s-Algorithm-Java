import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class DijkstraAlgorithm {
    public class Node {
        public class NodeLinks {
            Node nodePointingTo = null;
            int weight = Integer.MAX_VALUE;

            public NodeLinks(DijkstraAlgorithm.Node newNode, int weightOfEdge) {
                this.nodePointingTo = newNode;
                this.weight = weightOfEdge;
            }
        }
        
        int value = 0;
        ArrayList<NodeLinks> nodeLinks = new ArrayList<NodeLinks>();

        public void AddLink(Node newNode, int weightOfEdge)
        {
            nodeLinks.add(new NodeLinks(newNode, weightOfEdge));
        }


    }
    
    
    int vertices = 0;
    int sourceVertex = 0;
    int numberOfEdges = 0;
    
    ArrayList<Node> nodes = new ArrayList<Node>();
    
    public void CreateGraph() throws FileNotFoundException
    {
        File file = new File("filename.txt");
        Scanner inScanner = new Scanner(file);
        
        this.vertices = inScanner.nextInt();
        this.sourceVertex = inScanner.nextInt();
        this.numberOfEdges = inScanner.nextInt();
        
        for (int i = 0; i < this.numberOfEdges; i++) {
            nodes.add(new Node());
        }
        
        for (int i = 0; i < this.numberOfEdges; i++) {
            int startingNode = inScanner.nextInt();
            int endingNode = inScanner.nextInt();
            int lineWeight = inScanner.nextInt();

            nodes.get(startingNode - 1).AddLink(nodes.get(endingNode - 1), lineWeight);
        }
        
        
        inScanner.close();
    }
}