package ai.eightpuzzle;


import java.util.ArrayList;

public class Node{

//    public Node(){}
    public Node(int[][] state){ this.state = state; }

    private ArrayList<int[][]> children;
    private int[][] state;
    private int depth;
    private int nextChildToBeVisited;
    private Node parent;

    public void setChildren(ArrayList<int[][]> children){this.children = children;}
    public void setState(int[][] state){this.state = state;}
    public void setDepth(int depth){this.depth = depth;}
    public void setNextChildToBeVisited(int lastChildVisited){this.nextChildToBeVisited = lastChildVisited;}
    public void setParent(Node parent){this.parent = parent;}


    public ArrayList<int[][]> getChildren(){return this.children;}
    public int[][] getState(){return this.state;}
    public int getDepth(){return this.depth;}
    public int getNextChildToBeVisited(){return this.nextChildToBeVisited;}
    public Node getParent(){return this.parent;}




}



