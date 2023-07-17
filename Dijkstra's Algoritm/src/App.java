import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class App {
    public static void main(String[] args) throws IOException{
        CreateGraph();
        GenerateSpanningTree();
        OutPutSpanningTree();
    }

    static int vertices = 0;
    static int sourceVertex = 0;
    static int numberOfEdges = 0;
    
    static ArrayList<Node> nodes = new ArrayList<>();
    
    //creates a graph based off the input file
    public static void CreateGraph() throws FileNotFoundException
    {
        ///takes input file
        System.out.println("\ngenerating graph");
        File file = new File("Dijkstra's Algoritm/src/cop3503-asn2-input.txt");
        Scanner inScanner = new Scanner(file);

        //debug input file to console
        System.out.println("Input file = " + file.getName());
        System.out.println("Path = " + file.getPath());
        
        //starting values
        vertices = inScanner.nextInt();
        sourceVertex = inScanner.nextInt();
        numberOfEdges = inScanner.nextInt();
        
        //create empty nodes
        for (int i = 0; i < vertices; i++) {
            Node nodeToAdd = new Node();
            nodeToAdd.value = i+1;
            nodes.add(nodeToAdd);
        }
        
        //fill out each edge
        for (int i = 0; i < numberOfEdges; i++) {
            int startingNode = inScanner.nextInt();
            int endingNode = inScanner.nextInt();
            int lineWeight = inScanner.nextInt();

            nodes.get(startingNode - 1).AddLink(nodes.get(endingNode - 1), lineWeight);
            nodes.get(endingNode - 1).AddLink(nodes.get(startingNode - 1), lineWeight);
        }
        
        inScanner.close();
    }

    //generates the spanning tree from the given nodes
    public static void GenerateSpanningTree()
    {
        System.out.println("\ngenerating spanning tree");

        //starting values
        int visistedVertices = 0;
        Node sourceNode = nodes.get(sourceVertex-1);
        sourceNode.distance = 0;
        Node currentNode = sourceNode;
        
        //while unvisited vertices
        while(visistedVertices < vertices)
        {
            //loop through each neighbor
            for(int i=0; i<currentNode.nodeLinks.size(); i++)
            {
                Node targetNode = currentNode.nodeLinks.get(i).nodePointingTo;
                if(!targetNode.visited)
                {
                    int weight = currentNode.nodeLinks.get(i).weight;
                    //check if a new shorted path has been found
                    if(targetNode.distance > currentNode.distance  + weight || targetNode.distance == Integer.MAX_VALUE)
                    {
                        targetNode.distance = currentNode.distance + weight;
                        targetNode.parentNode = currentNode;
                    }
                }
            }
            
            //set current node as visisted
            if(!currentNode.visited)
            {
                currentNode.visited = true;
                visistedVertices += 1;
            }

            //chose next currentNode
            Node nextNode = null;
            int nextNodeDistance = Integer.MAX_VALUE;
            for(Node nextTargetNode : nodes)
            {
                if(!nextTargetNode.visited && nextTargetNode.distance < nextNodeDistance)
                {
                    nextNode =nextTargetNode;
                    nextNodeDistance = nextNode.distance;
                }
            }

            //set next Node
            if(nextNode != null)
            {
                currentNode = nextNode;
            }
        }
    }

    //outputs the spanning tree to 
    public static void OutPutSpanningTree() throws IOException
    {
        //creates new output file
        System.out.println("\noutputting spanning tree to file");
        File outFile = new File("Dijkstra's Algoritm/src/cop3503-asn2-output-deters-caleb.txt");
        FileWriter outputFile = new FileWriter(outFile);

        //debug to console
        System.out.println("Output file = " + outFile.getName());
        System.out.println("Path = " + outFile.getPath());

        //fills output file 
        outputFile.write(vertices + "");
        for (Node node : nodes) {
            outputFile.write("\n");
            outputFile.write(node.value + " ");
            if(node.distance == 0)
            {
                outputFile.write("-1 ");
            }
            else
            {
                outputFile.write(node.distance + " ");
            }

            if(node.parentNode == null)
            {
                outputFile.write("-1 ");
            }
            else
            {
                outputFile.write(node.parentNode.value + "");
            }
        }

        System.out.println();
        outputFile.close();
    }

    //Node object that holds the values and links
    public static class Node {

        //The links that connect between nodes
        public class NodeLink {
            Node nodePointingTo = null;
            int weight = Integer.MAX_VALUE;
            public NodeLink(Node newNode, int weightOfEdge) {
                this.nodePointingTo = newNode;
                this.weight = weightOfEdge;
            }
        }
        

        int value = 0;
        int distance = Integer.MAX_VALUE;
        boolean visited = false;
        Node parentNode = null;
        
        ArrayList<NodeLink> nodeLinks = new ArrayList<>();

        //adds a link between this node and the node given
        public void AddLink(Node newNode, int weightOfEdge)
        {
            nodeLinks.add(new NodeLink(newNode, weightOfEdge));
        }
    }
}