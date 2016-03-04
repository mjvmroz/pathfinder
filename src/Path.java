import java.util.LinkedList;

/**
 * Created by Scarlett on 06/05/2014.
 */
public class Path {
    private Iterable<Node> nodes;
    private int cost;

    /**
     * Follow the parent chain from a given node to get a path
     * @param currentNode the resultant path
     */
    public Path(Node currentNode)
    {
        LinkedList<Node> chain = new LinkedList<Node>();
        int totalCost = 0;
        do
        {
            chain.addFirst(currentNode);
            Node parent = currentNode.getParent();
            if(parent != null)
            {
                for(NeighbourNode neighbour : currentNode.getNeighbours())
                {
                    if(neighbour.getNode() == parent) {
                        totalCost += neighbour.getCost();
                        break;
                    }
                }
            }
            currentNode = parent;
        } while (currentNode != null);
        setNodes(chain);
        setCost(totalCost);
    }

    public Path(Iterable<Node> nodes, int cost)
    {
        setNodes(nodes);
        setCost(cost);
    }

    public Iterable<Node> getNodes() {
        return nodes;
    }

    public void setNodes(Iterable<Node> nodes) {
        this.nodes = nodes;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    @Override
    public String toString()
    {
        String result = "";
        for(Node node : getNodes())
        {
            result += node.getName() + " ";
        }
        result += "(Cost = " + getCost() + ")";
        return result;
    }
}
