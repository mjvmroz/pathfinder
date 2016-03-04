/**
 * Created by Scarlett on 06/05/2014.
 */
public class AStarNode extends Node
{
    /**
     * The anticipated distance from this node to the goal node
     */
	private int h;

    /**
     * Create a new A* Node with the supplied name and heuristic
     * @param name name
     * @param h heuristic
     */
	public AStarNode(String name, int h)
	{
        super(name);
		setH(h);
	}

    /**
     * Get the anticipated total distance to the goal if this node is chosen to pass through
     * @return
     */
    @Override
	public int getF()
	{
		return getG() + getH();
	}

    /**
     * Get the current distance from the start node
     * @return
     */
	public int getH()
	{
		return h;
	}

    /**
     * Set the current distance from the start node
     * @param h
     */
	public void setH(int h)
	{
		this.h = h;
	}
}
