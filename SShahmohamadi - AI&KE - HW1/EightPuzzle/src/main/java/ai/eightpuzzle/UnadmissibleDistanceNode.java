package ai.eightpuzzle;

import java.security.SecureRandom;

public class UnadmissibleDistanceNode extends ComparableNode {
    public UnadmissibleDistanceNode(int[][] state){
        super(state);
    }

    public static int unadmissibleDistance(int[][] state){
        int costToGoal = 0;
        SecureRandom secureRandom = new SecureRandom();

        if(EightPuzzle.testGoal(state))
            return costToGoal;
        int count = 0;
        for(int i = 0; i < state.length; i++) {
            for (int j = 0; j < state.length; j++) {
                int[] place = new int[2];
                place = getCoordinates(state[i][j]);
                if (state[i][j] != 0) {
                    costToGoal += Math.abs(i - place[0]) + Math.abs(j - place[1]) + secureRandom.nextInt();
                }
            }


        }
        return costToGoal;
    }



    @Override
    public int compare(Node node1, Node node2){
        if(unadmissibleDistance(node1.getState()) + node1.getDepth() > unadmissibleDistance(node2.getState()) + node2.getDepth())
            return 1;
        if(unadmissibleDistance(node1.getState()) + node1.getDepth() < unadmissibleDistance(node2.getState()) + node2.getDepth())
            return -1;
        return 0;
    }

    @Override
    public int compareTo(Node node2){
        if(unadmissibleDistance(getState()) + getDepth() > unadmissibleDistance(node2.getState()) + node2.getDepth())
            return 1;
        if(unadmissibleDistance(getState()) + getDepth() < unadmissibleDistance(node2.getState()) + node2.getDepth())
            return -1;
        return 0;
    }


}
