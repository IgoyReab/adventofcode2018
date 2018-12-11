package nl.yogibear;

import com.google.common.io.Files;
import lombok.Data;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Data
class Node {
    private Integer numberOfChilds;
    private Integer numberOfMetadata;
    private ArrayList<Node> ChildNodes;
    private ArrayList<Integer> Metadata;
    private Integer value;
    private Boolean leaf;
    private Integer valuePrevious;
    private Node previousNode ;

    public Node() {
        ChildNodes = new ArrayList<Node>();
        Metadata = new ArrayList<Integer>();
        this.value = 0;
        this.valuePrevious = 0;
        this.previousNode = null;
        this.numberOfChilds = 0;
        this.numberOfMetadata = 0;
        this.leaf = false;
    }

    public int getMetaSize() {return this.Metadata.size(); }
    public Node getChildNode(int AtIndex) {
        return ChildNodes.get(AtIndex);
    }
    public Integer getMetadata(int AtIndex) {
        return Metadata.get(AtIndex);
    }
    public void setChildNodes(Node childNode) {
        ChildNodes.add(childNode);
    }
    public void setMetadata(Integer meta) {
        Metadata.add(meta);
    }

    public void printNode(String desc){
        System.out.println(desc + " #childs:" + this.getNumberOfChilds() + " #meta:" + this.getNumberOfMetadata() + " value:" + this.getValue());
        for (int x=0;x<this.getNumberOfMetadata();x++) {
            System.out.println("Metadata at " + x + " = " + this.getMetadata(x));
        }
    }
}

public class Day_8 {
    static Integer sumMetaData (Integer n, List<Integer> l) {               //recursive function to build the tree
        if (l.size() != 0) {
            int numChilds = l.get(0).intValue();
            l.remove(0);
            int numMetadata = l.get(0).intValue();
            l.remove(0);

            for (int x = 0; x < numChilds; x++) {
                n = sumMetaData(n, l);
            }

            for (int x = 0; x < numMetadata; x++) {
                n = n + l.get(0).intValue();
                l.remove(0);
            }

        }
        return n;

    }

    private static void processNode (Node previousNode, List<Integer> l) {               //recursive function to build the tree

        if (l.size() != 0) {
            previousNode.setNumberOfChilds(l.get(0).intValue());
            l.remove(0);
            previousNode.setNumberOfMetadata(l.get(0).intValue());
            l.remove(0);

            for (int x = 0; x < previousNode.getNumberOfChilds(); x++) {
                Node newNode = new Node();
                previousNode.setChildNodes(newNode);
                processNode(newNode, l);
            }

            for (int x = 0; x < previousNode.getNumberOfMetadata(); x++) {
                previousNode.setMetadata(l.get(0).intValue());
                l.remove(0);
            }
        }
    }

    private static int calculateTheValue (Node node) {
        int value = 0;

        if (node.getNumberOfChilds() == 0) {
            for (int x = 0; x < node.getNumberOfMetadata(); x++) {
                value = value + node.getMetadata(x).intValue();
            }
        } else {
            for (int x = 0; x < node.getNumberOfMetadata(); x++) {
                if (node.getMetadata(x) <= node.getNumberOfChilds()) {
                    value = value + calculateTheValue(node.getChildNode(node.getMetadata(x)-1));
                }
            }
        }
        return value;
    }

    public static void main(String[] args) throws IOException{
        LocalTime start = LocalTime.now();

        List<String> nodeData = null;

        nodeData = Files.readLines(new File("src/main/resources/day8.txt"), Charset.forName("utf-8"));

        if (nodeData.size() != 1) System.out.println("Something went wrong with the inputfile");

        String[] e = nodeData.get(0).split(" ");
        List<Integer> nodeList = new ArrayList<Integer>();

        for (int i = 0; i < e.length; i++) {
            nodeList.add(Integer.parseInt(e[i]));
        }

        int sumOfMetadata = sumMetaData(0,nodeList);

        System.out.println("Part 1: the sum of the metadata is " + sumOfMetadata);

        LocalTime finish = LocalTime.now();

        System.out.println("Duration (ms): " + Duration.between(start, finish).toMillis());

        start = LocalTime.now();

        System.out.println("Part 2 : ");

        for (int i = 0; i < e.length; i++) {
            nodeList.add(Integer.parseInt(e[i]));
        }

        Node root = new Node();

        processNode(root, nodeList);

        System.out.println("The value of the root node : " + calculateTheValue(root) );

        finish = LocalTime.now();

        System.out.println("Duration (ms): " + Duration.between(start, finish).toMillis());
    }
}
