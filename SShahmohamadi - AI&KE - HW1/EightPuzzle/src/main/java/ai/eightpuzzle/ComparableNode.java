package ai.eightpuzzle;

import java.util.Comparator;

public abstract class ComparableNode extends Node implements Comparator<Node>, Comparable<Node> {
    public ComparableNode(int[][] state){
        super(state);
    }

    @Override
    public abstract int compare(Node node1, Node node2);

    public static int[] getCoordinates(int number){
        int[] place = {0, 0};
        switch (number) {
            case 1:
                break;
            case 2:
                place[1] = 1;
                break;
            case 3:
                place[1] = 2;
                break;
            case 4:
                place[0] = 1;
                place[1] = 0;
                break;
            case 5:
                place[0] = 1;
                place[1] = 1;
                break;
            case 6:
                place[0] = 1;
                place[1] = 2;
                break;
            case 7:
                place[0] = 2;
                place[1] = 0;
                break;
            case 8:
                place[0] = 2;
                place[1] = 1;
                break;
            case 0:
                place[0] = 2;
                place[1] = 2;
                break;
        }
        return place;
    }

}
