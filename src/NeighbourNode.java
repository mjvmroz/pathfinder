/**
 * Created by Scarlett on 06/05/2014.
 */
public class NeighbourNode
{
	private Node node;
	private int cost;

	public NeighbourNode(Node node, int cost)
	{
		setNode(node);
		setCost(cost);
	}

	public Node getNode()
	{
		return node;
	}

	public void setNode(Node node)
	{
		this.node = node;
	}

	public int getCost()
	{
		return cost;
	}

	public void setCost(int cost)
	{
		this.cost = cost;
	}
}
