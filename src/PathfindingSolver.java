import java.io.PrintStream;
import java.util.*;

/**
 * Created by Scarlett on 06/05/2014.
 */
public class PathfindingSolver
{

    private PrintStream workingStream;

    public PathfindingSolver()
    {
        workingStream = null;
    }

    public PathfindingSolver(PrintStream workingStream)
    {
        this.workingStream = workingStream;
    }

    /**
     * Update a neighbour with its new cost and parent
     * @param currentNode the current node
     * @param neighbourNode the target neighbour node
     * @param neighbourCost the cost to get to the neighbour from the current node
     */
    private void updateNeighbour(Node currentNode, Node neighbourNode, int neighbourCost)
    {
        neighbourNode.setG(currentNode.getG() + neighbourCost);
        neighbourNode.setParent(currentNode);
    }

    /**
     * Update a neighbour with its new cost and parent
     * @param currentNode the current node
     * @param neighbour the target neighbour node
     */
    private void updateNeighbour(Node currentNode, NeighbourNode neighbour)
    {
        updateNeighbour(currentNode, neighbour.getNode(), neighbour.getCost());
    }

    /**
     * Check if a neighbour should be updated, and if so, perform the update
     * @param currentNode the current node
     * @param neighbour the target neighbour node
     */
    private void processNeighbour(Node currentNode, NeighbourNode neighbour)
    {
        int newCost = currentNode.getG() + neighbour.getCost();
        int currentCost = neighbour.getNode().getG();
        if(newCost < currentCost)
        {
            updateNeighbour(currentNode, neighbour);
        }
    }

    /**
     * Perform an A* search with the supplied start and goal
     * @param start the start node
     * @param goal the aspired goal node
     * @return a path from the start to the end, if one exists
     */
    public List<Path> solve(Node start, Node goal)
    {
        return  solve(start, goal, false);
    }

    /**
     * Perform and A* search with the supplied start and goal
     * @param start the start node
     * @param goal the aspired goal node
     * @param persist whether or not to persist after finding the first solution
     * @return a path from the start to the end, if one exists
     */
    public List<Path> solve(Node start, Node goal, boolean persist)
    {
        List<Path> solutions = new LinkedList<Path>();
        Set<Node> closed = new HashSet<Node>();
        PriorityQueue<Node> open = new PriorityQueue<Node>();
        open.add(start);

        Node currentNode;
        while ((currentNode = open.poll()) != null)
        {
            closed.add(currentNode);
            if(currentNode == goal)
            {
                Path path = new Path(currentNode);
                solutions.add(path);
                if(!persist) {
                    break;
                }
            }
            else {
                for (NeighbourNode neighbour : currentNode.getNeighbours()) {
                    Node neighbourNode = neighbour.getNode();
                    boolean inClosed;
                    if (persist && neighbourNode == goal) {
                        closed.add(neighbourNode);
                        Node clone = new Node(neighbourNode);
                        updateNeighbour(currentNode, clone, neighbour.getCost());
                        Path path = new Path(clone);
                        solutions.add(path);
                    }
                    if ((inClosed = closed.contains(neighbourNode)) || open.contains(neighbourNode)) {
                        processNeighbour(currentNode, neighbour);
                    } else {
                        updateNeighbour(currentNode, neighbour);
                        open.add(neighbourNode);
                    }

                }
                if (workingStream != null) {
                    workingLog("Partial path: " + new Path(currentNode).toString());
                }
            }
        }
        return solutions;
    }

    /**
     * Log a message to the working stream, if it exists
     * @param line The line to log
     */
    private void workingLog(String line)
    {
        if(workingStream != null)
        {
            workingStream.println(line);
        }
    }
}
