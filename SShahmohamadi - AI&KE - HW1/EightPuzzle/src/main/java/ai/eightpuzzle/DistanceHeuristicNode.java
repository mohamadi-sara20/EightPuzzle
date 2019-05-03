package ai.eightpuzzle;

public class DistanceHeuristicNode extends ComparableNode{

    public DistanceHeuristicNode(int[][] state){
        super(state);
    }


    public static int distanceHeuristic(int[][] state){

        int costToGoal = 0;

        if(EightPuzzle.testGoal(state))
            return costToGoal;

        for(int i = 0; i < state.length; i++){
            for(int j = 0; j < state.length; j++) {
                int[] place = new int[2];
                place = getCoordinates(state[i][j]);
                if (state[i][j] != 0 && (place[0] != i || place[1] != j))
                    costToGoal++;
            }
        }

        return costToGoal;
    }

    @Override
    public int compare(Node node1, Node node2){
        if(distanceHeuristic(node1.getState()) + node1.getDepth() > distanceHeuristic(node2.getState()) + node2.getDepth())
            return 1;
        else if(distanceHeuristic(node1.getState()) + node1.getDepth() < distanceHeuristic(node2.getState()) + node2.getDepth())
            return -1;
        return 0;

    }

    @Override
    public int compareTo(Node node2){
        if(distanceHeuristic(getState()) + getDepth() > distanceHeuristic(node2.getState()) + node2.getDepth())
            return 1;
        else if(distanceHeuristic(getState()) + getDepth() < distanceHeuristic(node2.getState()) + node2.getDepth())
            return -1;
        return 0;
    }

}
