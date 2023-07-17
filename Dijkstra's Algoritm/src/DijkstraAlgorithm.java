import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class DijkstraAlgorithm {
    public class Node {
        public class NodeLink {
            Node nodePointingTo = null;
            int weight = Integer.MAX_VALUE;
            public NodeLink(DijkstraAlgorithm.Node newNode, int weightOfEdge) {
                this.nodePointingTo = newNode;
                this.weight = weightOfEdge;
            }
        }
        
        int value = 0;
        int distance = Integer.MAX_VALUE;
        boolean visited = false;
        Node parentNode = null;
        
        ArrayList<NodeLink> nodeLinks = new ArrayList<>();

        public void AddLink(Node newNode, int weightOfEdge)
        {
            nodeLinks.add(new NodeLink(newNode, weightOfEdge));
        }

        public NodeLink FindLinkBetweenNodes(Node otherNode)
        {
            for(int i=0; i<nodeLinks.size(); i++)
            {
                if(nodeLinks.get(i).nodePointingTo == otherNode)
                    return nodeLinks.get(i);
            }
            return null;
        }

        public void PrintNode()
        {
            System.out.println("-------");
            System.out.println("value = " + this.value);
            System.out.println(" distance = " + this.distance);
            System.out.println("visited = " + this.visited);
            System.out.println("links = " + this.nodeLinks.size());
            for (int i=0; i<this.nodeLinks.size(); i++) {
                System.out.println(" " + this.nodeLinks.get(i).nodePointingTo.value + " : weight " + this.nodeLinks.get(i).weight);
            }
        }
    }
    
    
    int vertices = 0;
    int sourceVertex = 0;
    int numberOfEdges = 0;
    
    ArrayList<Node> nodes = new ArrayList<>();
    
    //creates a graph based off the input file
    public void CreateGraph() throws FileNotFoundException
    {
        System.out.println("-------\ngenerating graph");
        File file = new File("Dijkstra's Algoritm/src/cop3503-asn2-input.txt");
        System.out.println("Input file = " + file.getName());
        System.out.println("Path = " + file.getPath());
        Scanner inScanner = new Scanner(file);
        
        this.vertices = inScanner.nextInt();
        this.sourceVertex = inScanner.nextInt();
        this.numberOfEdges = inScanner.nextInt();
        
        for (int i = 0; i < this.vertices; i++) {
            Node nodeToAdd = new Node();
            nodeToAdd.value = i+1;
            nodes.add(nodeToAdd);
        }
        
        for (int i = 0; i < this.numberOfEdges; i++) {
            int startingNode = inScanner.nextInt();
            int endingNode = inScanner.nextInt();
            int lineWeight = inScanner.nextInt();

            nodes.get(startingNode - 1).AddLink(nodes.get(endingNode - 1), lineWeight);
            nodes.get(endingNode - 1).AddLink(nodes.get(startingNode - 1), lineWeight);

        }
        
        inScanner.close();
    }

    public void GenerateSpanningTree()
    {
        System.out.println("-------\ngenerating spanning tree");
        int visistedVertices = 0;
        Node sourceNode = nodes.get(sourceVertex-1);
        sourceNode.distance = 0;
        Node currentNode = sourceNode;
        

        //while unvisited vertices
        while(visistedVertices < this.vertices)
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

    public void OutPutSpanningTree() throws IOException
    {
        File outFile = new File("Dijkstra's Algoritm/src/cop3503-asn2-output-deters-caleb.txt");
        FileWriter outputFile = new FileWriter(outFile);
        System.out.println("Output file = " + outFile.getName());
        System.out.println("Path = " + outFile.getPath());
        for (Node node : nodes) {
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
                outputFile.write("-1\n");
            }
            else
            {
                outputFile.write(node.parentNode.value + "\n");
            }
        }

        outputFile.close();
    }

    public void PrintTree()
    {
        for (Node node : nodes) {
            node.PrintNode();
        }
    }
}