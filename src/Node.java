import java.util.HashSet;

/**
 * Created by Scarlett on 10/05/2014.
 */
public class Node implements Comparable<Node>
{
    private Node parent;
    private int g;
    private String name;
    private HashSet<NeighbourNode> neighbours;

    public Node()
    {
        setParent(null);
        setG(0);
        setName("");
        setNeighbours(new HashSet<NeighbourNode>());
    }

    public Node(String name)
    {
        setParent(null);
        setG(0);
        setName(name);
        setNeighbours(new HashSet<NeighbourNode>());
    }

    public Node(Node node)
    {
        setParent(node.getParent());
        setG(node.getG());
        setName(node.getName());
        setNeighbours(node.getNeighbours());
    }

    public Node getParent()
    {
        return parent;
    }

    public void setParent(Node parent)
    {
        this.parent = parent;
    }

    public int getF()
    {
        return g;
    }

    public int getG()
    {
        return g;
    }

    public void setG(int g)
    {
        this.g = g;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public HashSet<NeighbourNode> getNeighbours()
    {
        return neighbours;
    }

    public void setNeighbours(HashSet<NeighbourNode> neighbours)
    {
        this.neighbours = neighbours;
    }

    @Override
    public int compareTo(Node other)
    {
        return this.getF() - other.getF();
    }

    @Override
    public String toString()
    {
        return getName();
    }
}
