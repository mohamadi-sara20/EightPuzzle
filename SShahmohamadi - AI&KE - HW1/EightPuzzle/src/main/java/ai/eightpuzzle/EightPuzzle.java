package ai.eightpuzzle;
import org.apache.commons.lang3.time.StopWatch;
import java.util.*;


public class EightPuzzle{

    private static Hashtable<Long, Integer> seen;
    private static boolean filterRepeatedStates = true;

    public static void main(String[] args) {


        while (true) {
            int[] input = getInput();
            if (input == null)
                continue;
            int[][] initState = create2dInitialState(input);

            if(!isSolvableGridOdd(initState)){
                    System.out.println("The initial state is not solvable. Please proceed with a new one.");
                    continue;
                }

            Node result = breadthFirstSearch(new Node(initState), true);

            String report = runAllAlgorithms(initState, true, getAnswerLength(result)) + "---------------------------------------\n";

            System.out.println(report);
        }
    }




    private static String runAllAlgorithms(int[][] initialState, boolean filter, int answerLength) {
        StopWatch stopWatch = new StopWatch();
        String report = String.format("Summary of all algorithms (%s): \n", (filter ? "Filtering repeated states" : "Keeping repeated states"));
        Node result;

        stopWatch.reset();
        stopWatch.start();
        result = aStarSearchDistanceHeuristic(initialState, filter);
        stopWatch.stop();
        printSolution(result, "A* with Distance Heuristic");
        report += String.format("%s: steps = %d -time = %s \n", "A* with Distance Heuristic", getAnswerLength(result), stopWatch.toString());

        stopWatch.reset();
        stopWatch.start();
        result = aStarSearchManhattanDistance(initialState, false);
        stopWatch.stop();
        printSolution(result, "A* with Manhattan Distance");
        report += String.format("%s: steps = %d -time = %s \n", "A* with Manhattan Distance", getAnswerLength(result), stopWatch.toString());

        stopWatch.reset();
        stopWatch.start();
        result = aStarSearchUnadmissible(initialState, filter);
        stopWatch.stop();
        printSolution(result, "A* with An Unadmissible Heuristic");
        report += String.format("%s: steps = %d -time = %s \n", "A* with An Unadmissible Heuristic", getAnswerLength(result), stopWatch.toString());

        stopWatch.reset();
        stopWatch.start();
        result = breadthFirstSearch(new Node(initialState), filter);
        stopWatch.stop();
        printSolution(result, "Breadth First Search");
        report += String.format("%s: steps = %d - time = %s \n", "Breadth-First Search", getAnswerLength(result), stopWatch.toString());

        stopWatch.reset();
        stopWatch.start();
        result = iterativeDeepeningSearch(new Node(initialState), answerLength, filter);
        stopWatch.stop();
        printSolution(result, "Iterative Deepening (repeated states skipped)");
        report += String.format("%s: steps = %d -time = %s \n", "Iterative Deepening", getAnswerLength(result), stopWatch.toString());

        stopWatch.reset();
        stopWatch.start();
        result = depthFirstSearch(new Node(initialState), true);
        stopWatch.stop();
        printSolution(result, "Depth first search");
        report += String.format("%s: steps = %d -time = %s \n", "Depth first search", getAnswerLength(result), stopWatch.toString());

        return report;
    }



    public static int[] getInput(){
        Scanner in = new Scanner(System.in);
        System.out.println("Enter an initial state as 8 numbers: ");
        int[] input = new int[8];
        for (int i = 0; i < 8; i++){
            input[i] = in.nextInt();
            if (input[i] < 1 || input[i] > 9){
                System.out.println(String.format("The number %d is not in acceptable range. Please try again.", input[i]));
                return null;
            }
            for (int j = 0; j < i; j++){
                if (input[j] == input[i]){
                    System.out.println(String.format("The number %d is repeated. Please try again.", input[i]));
                    return null;
                }
            }
        }
        return input;
    }

    public static void printSolution(Node n, String title){
        System.out.println(String.format("Solution steps found with <%s>:", title));
        if (n == null){
            System.out.println("No soltion found");
            return;
        }
        while (n != null) {
            printState(n.getState());
            n = n.getParent();
        }
    }



    public static int getAnswerLength(Node node){
        int answerLength = 0;
        while (node != null) {
            answerLength++;
            node = node.getParent();
        }
        return Math.max(0,answerLength - 1);
    }


    public static void printState(int[][] state) {
        for (int[] row : state) {
            for (int a : row) {
                System.out.print(a);
            }
            System.out.println();
        }
        System.out.println();
    }


    private static int[][] create2dInitialState(int[] rawInitialState){

        int length = (int) Math.sqrt(rawInitialState.length + 1);
        int[][] initialState = new int[length][length];
        for(int i = 0; i < rawInitialState.length; i++){
            int col, row;
            col = (rawInitialState[i] - 1) % 3;
            row = (rawInitialState[i] - 1) / 3;
            initialState[row][col] = i + 1;

        }

        return initialState;

    }


    public static ArrayList<int[][]> generateNextStates(int[][] originalState, int depth) {
        ArrayList<int[][]> nextStates = new ArrayList<>();
        int row = 0, col = 0, temp;
        for (int i = 0; i < originalState.length; i++) {
            for (int j = 0; j < originalState.length; j++) {
                if (originalState[i][j] == 0) {
                    row = i;
                    col = j;
                }
            }
        }

        if (row != 2) {

            int[][] state = deepCopy(originalState);
            temp = state[row][col];
            state[row][col] = state[row + 1][col];
            state[row + 1][col] = temp;
            if (!isRepeated(state, depth + 1))
                nextStates.add(state);

        }
        if (row != 0) {
            int[][] state = deepCopy(originalState);
            temp = state[row][col];
            state[row][col] = state[row - 1][col];
            state[row - 1][col] = temp;
            if (!isRepeated(state, depth + 1))
                nextStates.add(state);
        }

        if (col != 0) {
            int[][] state = deepCopy(originalState);
            temp = state[row][col];
            state[row][col] = state[row][col - 1];
            state[row][col - 1] = temp;
            if (!isRepeated(state, depth + 1))
                nextStates.add(state);
        }

        if (col != 2) {
            int[][] state = deepCopy(originalState);
            temp = state[row][col];
            state[row][col] = state[row][col + 1];
            state[row][col + 1] = temp;
            if (!isRepeated(state, depth + 1))
                nextStates.add(state);
        }
        return nextStates;
    }


    public static int[][] deepCopy(int[][] array) {
        int[][] copyArray = new int[array.length][array.length];
        for (int i = 0; i < array.length; i++) {
            copyArray[i] = Arrays.copyOf(array[i], array.length);
        }
        return copyArray;
    }

    public static boolean testGoal(int[][] state) {
        for (int i = 0; i < state.length; i++) {
            for (int j = 0; j < state.length; j++) {
                if (i == state.length - 1 && j == state.length - 1) {
                    if (state[i][j] != 0)
                        return false;
                } else {
                    if (state[i][j] != i * 3 + j + 1)
                        return false;
                }
            }
        }
        return true;
    }

    private static long convertToNumeric(int[][] state){
        long numericState = 0;

        for(int i = 0; i < state.length; i++) {
            for (int j = 0; j < state.length; j++) {
                numericState = numericState * 10 +  state[i][j];
            }
        }

        return numericState;
    }



    public static boolean isRepeated(int[][] state, int depth){
        if (!filterRepeatedStates)
            return false;
        long numericState = convertToNumeric(state);
        if(seen == null)
            seen = new Hashtable<>();

        Integer existingDepth = seen.get(numericState);
        if(existingDepth != null && existingDepth <= depth)
            return true;
        seen.put(numericState, depth);
        return false;
    }



    public static boolean isSolvableGridOdd(int[][] initialState){
        int inversions = 0;
        ArrayList<Integer> ar = new ArrayList<>();

        for(int i = 0; i < initialState.length; i++){
            for (int j = 0; j < initialState.length; j++){
                if(initialState[i][j] != 0)
                    ar.add(initialState[i][j]);
            }
        }


        for(int i = 0; i < ar.size() ; i++){
            for(int j = i; j < ar.size(); j++){
                if(ar.get(i) > ar.get(j))
                    inversions++;
            }
        }

        if(inversions % 2 == 0)
            return true;
        return false;
    }





    public static Node breadthFirstSearch(Node initialState, boolean newFilterRepeatedStates) {
        filterRepeatedStates = newFilterRepeatedStates;
        seen = null;
        QueueLinkedList<Node> possibleStates = new QueueLinkedList();
        possibleStates.enqueue(initialState);
        isRepeated(initialState.getState(), 0);   // mark the initial state as seen

        initialState.setDepth(1);
        while (!possibleStates.isEmpty()) {
            Node node = possibleStates.dequeue();
            if (testGoal(node.getState()))
                return node;

            if(node.getParent() != null)
                node.setDepth(node.getParent().getDepth() + 1);

        //    System.out.println(node.getDepth());

            ArrayList<int[][]> arrayList = generateNextStates(node.getState(), node.getDepth());
            for (int[][] x : arrayList) {
                Node node1 = new Node(x);
                node1.setParent(node);
                possibleStates.enqueue(node1);
            }
        }
        return null;
    }


    public static Node depthFirstSearch(Node initialState, boolean newFilterRepeatedStates) {
        filterRepeatedStates = newFilterRepeatedStates;
        seen = null;
        ai.eightpuzzle.Stack<Node> possibleStates = new ai.eightpuzzle.Stack<>(100);
        possibleStates.push(initialState);
        isRepeated(initialState.getState(), 0);

        while (!possibleStates.isEmpty()) {
            Node node = possibleStates.pop();
            if (!testGoal(node.getState())) {
                ArrayList<int[][]> arrayList = generateNextStates(node.getState(), node.getDepth());

                for (int[][] n : arrayList) {
                    Node node1 = new Node(n);
                    node1.setParent(node);
                    possibleStates.push(node1);
                }
            } else
                return node;
        }
        return null;
    }




    public static Node depthLimitedSearch(Node initialState, int limit, boolean newFilterRepeatedStates) {
        filterRepeatedStates = newFilterRepeatedStates;
        seen = null;
        ai.eightpuzzle.Stack<Node> possibleStates = new Stack<>(limit + 1);
        possibleStates.push(initialState);
        isRepeated(initialState.getState(), 0);   // add initial state to the seen set

        initialState.setChildren(generateNextStates(initialState.getState(), 0));

        while (!possibleStates.isEmpty()) {
          Node node = possibleStates.peek();
            if (!testGoal(node.getState())) {
                if(node.getNextChildToBeVisited() < node.getChildren().size() && node.getDepth() <= limit){
                    int[][] nc = node.getChildren().get(node.getNextChildToBeVisited());
                    node.setNextChildToBeVisited(node.getNextChildToBeVisited() + 1);
                    Node nextChild = new Node(nc);
                    nextChild.setParent(node);
                    nextChild.setDepth(node.getDepth() + 1);
                    //filterRepeatedStates = false;
                    nextChild.setChildren(generateNextStates(nc, nextChild.getDepth()));
                    //filterRepeatedStates = newFilterRepeatedStates;
                    possibleStates.push(nextChild);
                    //isRepeated(nc);
                }

                else
                    possibleStates.pop();
            }
            else
                return node;
        }

        return null;
    }


    public static Node iterativeDeepeningSearch(Node initialState, int limit, boolean newFilterRepeatedStates) {
        filterRepeatedStates = newFilterRepeatedStates;


        Node result = null;

        for (int i = 1; newFilterRepeatedStates || i <= limit; i++) {
            seen = null;
            Node freshNode = new Node(initialState.getState());
            result = depthLimitedSearch(freshNode, i, newFilterRepeatedStates);
            if (result != null)
                break;
        }
        return result;
    }



    public static Node aStarSearchDistanceHeuristic(int[][] state, boolean newFilterRepeatedStates){
        filterRepeatedStates = newFilterRepeatedStates;
        seen = null;
        //long numericState = convertToNumeric(state);

        PriorityQueue<DistanceHeuristicNode> possibleStates = new PriorityQueue<>();
        DistanceHeuristicNode initial = new DistanceHeuristicNode(state);
        possibleStates.add(initial);
        isRepeated(state, 0);
        int c = 0;
        while (!possibleStates.isEmpty()) {
            DistanceHeuristicNode node = possibleStates.poll();
            if (testGoal(node.getState()))
                return node;
            c++;

            //  System.out.println(c);
            ArrayList<int[][]> arrayList = generateNextStates(node.getState(), node.getDepth());
            for (int[][] x : arrayList) {
                DistanceHeuristicNode node1 = new DistanceHeuristicNode(x);
                node1.setDepth(node.getDepth() + 1);
                node1.setParent(node);
                possibleStates.add(node1);
            }
        }
        return null;
    }



    public static Node aStarSearchManhattanDistance(int[][] state, boolean newFilterRepeatedStates){
        filterRepeatedStates = newFilterRepeatedStates;
        seen = null;

        PriorityQueue<ManhattanDistanceNode> possibleStates = new PriorityQueue<>();
        ManhattanDistanceNode initial = new ManhattanDistanceNode(state);
        possibleStates.add(initial);
        isRepeated(state, 0);

        while (!possibleStates.isEmpty()) {
            ManhattanDistanceNode node = possibleStates.poll();
            if (testGoal(node.getState()))
                return node;

            ArrayList<int[][]> arrayList = generateNextStates(node.getState(), node.getDepth());
            for (int[][] x : arrayList) {
                ManhattanDistanceNode node1 = new ManhattanDistanceNode(x);
                node1.setDepth(node.getDepth() + 1);
                node1.setParent(node);
                possibleStates.add(node1);
            }
        }
        return null;
    }


    public static Node aStarSearchUnadmissible(int[][] state, boolean newFilterRepeatedStates){
        filterRepeatedStates = newFilterRepeatedStates;
        seen = null;
        //long numericState = convertToNumeric(state);

        PriorityQueue<UnadmissibleDistanceNode> possibleStates = new PriorityQueue<>();
        UnadmissibleDistanceNode initial = new UnadmissibleDistanceNode(state);
        possibleStates.add(initial);
        isRepeated(state, 0);
        int c = 0;
        while (!possibleStates.isEmpty()) {
            UnadmissibleDistanceNode node = possibleStates.poll();
            if (testGoal(node.getState()))
                return node;
            c++;

            //  System.out.println(c);
            ArrayList<int[][]> arrayList = generateNextStates(node.getState(), node.getDepth());
            for (int[][] x : arrayList) {
                UnadmissibleDistanceNode node1 = new UnadmissibleDistanceNode(x);
                node1.setDepth(node.getDepth() + 1);
                node1.setParent(node);
                possibleStates.add(node1);
            }
        }
        return null;
    }
}


