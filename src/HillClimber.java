import java.io.IOException;
import java.util.LinkedList;

/**
 * Created by Scarlett on 11/05/2014.
 */
public class HillClimber {
    public static void main(String[] args) {
        if(args.length == 1) {
            try {
                //Generate a terrain
                Terrain terrain = Utils.parseHillClimbing(args[0]);

                //Run a solve against the terrain and print out the highest point found
                LinkedList<Point> path = HillClimbingSolver.solve(terrain);
                System.out.println("Maxima found: " + path.peekLast().toString());
                Utils.saveHillClimbingSolution("seq.txt", path);
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }
        else {
            //Print out the correct usage
            System.err.println("Usage: java HillClimber <mapFile>");
            System.exit(1);
        }
    }
}
