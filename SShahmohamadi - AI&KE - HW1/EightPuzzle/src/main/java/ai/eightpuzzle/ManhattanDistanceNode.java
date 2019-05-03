package ai.eightpuzzle;


public class ManhattanDistanceNode extends ComparableNode{

    public ManhattanDistanceNode(int[][] state){super(state);}

    public static int manhattanDistanceHeuristic(int[][] state){
        int costToGoal = 0;

        if(EightPuzzle.testGoal(state))
            return costToGoal;

        for(int i = 0; i < state.length; i++) {
            for (int j = 0; j < state.length; j++) {
                    if (state[i][j] != 0) {
                        int[] place = getCoordinates(state[i][j]);
                        costToGoal += Math.abs(i - place[0]) + Math.abs(j - place[1]);
                    }
            }

        }

        return costToGoal;
    }





    @Override
    public int compare(Node node1, Node node2){
        if(manhattanDistanceHeuristic(node1.getState()) + node1.getDepth() > manhattanDistanceHeuristic(node2.getState()) + node2.getDepth())
            return 1;
        if(manhattanDistanceHeuristic(node1.getState()) + node1.getDepth() < manhattanDistanceHeuristic(node2.getState()) + node2.getDepth())
            return -1;
        return 0;
    }

    @Override
    public int compareTo(Node node2){
        if(manhattanDistanceHeuristic(getState()) + getDepth() > manhattanDistanceHeuristic(node2.getState()) + node2.getDepth())
            return 1;
        if(manhattanDistanceHeuristic(getState()) + getDepth() < manhattanDistanceHeuristic(node2.getState()) + node2.getDepth())
            return -1;
        return 0;
    }



}
