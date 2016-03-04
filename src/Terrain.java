import java.util.*;

/**
 * Created by Scarlett on 11/05/2014.
 */
public class Terrain {
    private final Map<Integer, Map<Integer, Point>> map;

    /**
     * Get the maximum x index of the map
     * @return
     */
    public int getMaxX()
    {
        return map.size();
    }

    /**
     * Get the maximum y index of the map
     * @return
     */
    public int getMaxY()
    {
        return map.containsKey(0)
            ? map.get(0).size()
            : 0;
    }

    public Terrain() {
        map = new HashMap<Integer, Map<Integer, Point>>();
    }

    /**
     * Add a new point to the terrain
     * @param x the x index to add the point at
     * @param y the y index to add the point at
     * @param point the point to add at the specified indices
     * @return
     */
    public Point put(Integer x, Integer y, Point point) {
        Map<Integer, Point> subMap;
        if (map.containsKey(x)) {
            subMap = map.get(x);
        } else {
            subMap = new HashMap<Integer, Point>();
            map.put(x, subMap);
        }
        return subMap.put(y, point);
    }

    /**
     * Get the point at a given x/y index pair
     * @param x the x index to find the point at
     * @param y the y index to find the point at
     * @return the corresponding point
     */
    public Point get(Integer x, Integer y) {
        if (map.containsKey(x)) {
            return map.get(x).get(y);
        } else {
            return null;
        }
    }

    /**
     * Get all upward and equal neighbours of a given point, with a set of exclusions
     * @param p a point to find the neighbours of
     * @param blacklist a list to ignore points in
     * @return a list of neighbouring, non-blacklisted points
     */
    public List<Point> getUpwardNeighbours(Point p, List<Point> blacklist) {
        int[] offsets = {-1, 0, 1};
        List<Point> points = new LinkedList<Point>();
        for(int i : offsets) {
            for(int j : offsets) {
                if(!(i == 0 && j == 0)) {
                    //Only get a point if it is immediately orthogonally or diagonally adjacent, and is not on the blacklist
                    Point neighbour = get(p.xIndex+i, p.yIndex+j);
                    if(neighbour != null && neighbour.z >= p.z && !blacklist.contains(neighbour)) {
                        points.add(neighbour);
                    }
                }
            }
        }
        return points;
    }
}
