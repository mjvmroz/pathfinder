import java.util.*;

/**
 * Created by Scarlett on 11/05/2014.
 */
public class HillClimbingSolver {

    /**
     * Find a maxima on a given terrain
     * @param terrain a terrain
     * @return A path to get to the found maxima, starting from a random point
     */
    public static LinkedList<Point> solve(Terrain terrain) {
        Random r = new Random();
        LinkedList<Point> path = new LinkedList<Point>();

        //Pick a random start point
        Point point = terrain.get(r.nextInt(terrain.getMaxX()), r.nextInt(terrain.getMaxY()));
        int currentZ = point.z;
        List<Point> neighbours;

        //While we still have options open to us, keep going up!
        while((neighbours = terrain.getUpwardNeighbours(point, path)).size() > 0) {
            path.addLast(point);
            List<Double> partitionBounds = new LinkedList<Double>();
            Point nextPoint = null;

            //Loop this just in case of double rounding errors.
            while (nextPoint == null) {
                Double current = 0.0;
                int totalDifference = 0;
                double selector = r.nextDouble();

                //Calculate the total difference in height provided by all the options
                for (Point neighbour : neighbours) {
                    totalDifference += (neighbour.z - point.z);
                }

                //If all the options are on a plateau with the current node, pick from them randomly with no weighting
                if(totalDifference == 0) {
                    nextPoint = neighbours.get(r.nextInt(neighbours.size()));
                }

                //Otherwise, pick from the neighbours with an improved z value with a weighting proportional to the
                //improvement afforded.
                else {
                    for (Point neighbour : neighbours) {
                        current += ((double) (neighbour.z - point.z)) / ((double) totalDifference);
                        if (selector <= current) {
                            nextPoint = neighbour;
                            break;
                        }
                    }
                }
            }
            point = nextPoint;
        }
        path.addLast(point);
        return path;
    }
}
