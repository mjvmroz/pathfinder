import java.io.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Scarlett on 10/05/2014.
 */
public class Utils {

    /**
     * Parse an A* heuristics file
     * @param heuristicsFile The file from which to fetch heuristics
     * @return A mapping of node names to nodes
     * @throws IOException
     */
    private static HashMap<String, Node> parseHeuristics(String heuristicsFile) throws IOException {
        HashMap<String, Node> nodes = new HashMap<String, Node>();
        BufferedReader br = new BufferedReader(new FileReader(heuristicsFile));
        Pattern p = Pattern.compile("(\\S+) (\\d+)");
        String line;
        while ((line = br.readLine()) != null)
        {
            Matcher m = p.matcher(line);
            if (m.matches()) {
                String nodeKey = m.group(1);
                int heuristic = Integer.parseInt(m.group(2));
                nodes.put(nodeKey, new AStarNode(nodeKey, heuristic));
            }
            else
                throw new IOException("Failed to parse line of heuristic file '" + line + "'");
        }
        br.close();
        return nodes;
    }

    /**
     * Parse edges from a general pathfinding edges file
     * @param edgesFile The file from which to fetch edges
     * @param nodes the list of nodes to add relationships to
     * @param <N> The type of Node used in the nodes list
     * @throws IOException
     */
    private static void parseEdges(String edgesFile, HashMap<String, Node> nodes) throws IOException
    {
        BufferedReader br = new BufferedReader(new FileReader(edgesFile));
        Pattern p = Pattern.compile("(\\S+) (\\S+) (\\d+)");
        String line;
        while ((line = br.readLine()) != null)
        {
            Matcher m = p.matcher(line);
            if (m.matches()) {
                String node1Key = m.group(1);
                String node2Key = m.group(2);
                int cost = Integer.parseInt(m.group(3));
                Node node1 = nodes.get(node1Key);
                Node node2 = nodes.get(node2Key);
                if(node1 == null) {
                    node1 = new Node(node1Key);
                    nodes.put(node1Key, node1);
                }
                if(node2 == null) {
                    node2 = new Node(node2Key);
                    nodes.put(node2Key, node2);
                }
                node1.getNeighbours().add(new NeighbourNode(node2, cost));
                node2.getNeighbours().add(new NeighbourNode(node1, cost));
            }
            else
                throw new IOException("Failed to parse line of edge file: '" + line + "'");
        }
        br.close();
    }

    /**
     * Perform all the parsing required for A*, given the files required
     * @param edgesFile The file from which to fetch edges
     * @param heuristicsFile The file from which to fetch heuristics
     * @return A mapping of node names to A* nodes
     * @throws IOException
     */
    public static HashMap<String, Node> parseAStar(String edgesFile, String heuristicsFile) throws IOException
    {
        HashMap<String, Node> nodes = parseHeuristics(heuristicsFile);
        parseEdges(edgesFile, nodes);
        return nodes;
    }

    /**
     * Perform all the parsing required for B&B, given the file required
     * @param edgesFile The file from which to fetch edges
     * @return A mapping of node names to nodes
     * @throws IOException
     */
    public static HashMap<String, Node> parseBranchAndBound(String edgesFile) throws IOException
    {
        HashMap<String, Node> nodes = new HashMap<String, Node>();
        parseEdges(edgesFile, nodes);
        return nodes;
    }

    /**
     * Save a list of hill-climbing points to a file
     * @param saveFile The file to write the hill-climbing solution to
     * @param solution The solution to write
     * @throws IOException
     */
    public static void saveHillClimbingSolution(String saveFile, LinkedList<Point> solution) throws IOException{
        FileWriter fw = new FileWriter(saveFile);
        String end = System.getProperty("line.separator");
        for(Point p : solution) {
            fw.write(p.toString() + end);
        }
        fw.close();
    }

    /**
     * Perform all the parsing required for a hill climbing problem
     * @param mapFile The file from which to parse the map data
     * @return A terrain build from the supplied data
     * @throws IOException
     */
    public static Terrain parseHillClimbing(String mapFile) throws  IOException
    {
        Terrain terrain = new Terrain();
        BufferedReader br = new BufferedReader(new FileReader(mapFile));
        Pattern p = Pattern.compile("(\\d+) (\\d+) (\\d+)");
        String line;
        boolean firstLine = true;
        int currentX = 0,
            currentY = 0,
            xIndex = 0,
            yIndex = 0;
        while ((line = br.readLine()) != null)
        {
            Matcher m = p.matcher(line);
            if (m.matches()) {
                Integer x = new Integer(m.group(1)),
                        y = new Integer(m.group(2)),
                        z = new Integer(m.group(3));
                if(firstLine)
                {
                    firstLine = false;
                }
                else
                {
                    if(y < currentY) {
                        yIndex++;
                        xIndex = 0;
                    }
                }
                currentX = x;
                currentY = y;
                Point point = new Point();
                point.x = x;
                point.y = y;
                point.z = z;
                point.xIndex = xIndex;
                point.yIndex = yIndex;
                terrain.put(xIndex++, yIndex, point);
            }
            else
                throw new IOException("Failed to parse line of map file '" + line + "'");
        }
        br.close();
        return terrain;
    }
}
