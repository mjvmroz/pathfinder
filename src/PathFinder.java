import java.io.IOException;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Scarlett on 06/05/2014.
 */
public class PathFinder {
    public static void main(String[] args) {
        if (args.length >= 4 && args.length <= 6) {
            String algorithm = args[0].toLowerCase();
            if(algorithm.equals("astar") || algorithm.equals("bb")) {
                int argOffset = 0;
                PrintStream workingStream = null;
                boolean persist = false;
                if (algorithm.equals("astar") && args.length == 6 || algorithm.equals("bb") && args.length == 5) {
                    String flags = args[1].toLowerCase();
                    if (flags.equals("-v") || flags.equals("-pv") || flags.equals("-p")) {
                        workingStream = flags.indexOf('v') != -1 ? System.out : null;
                        persist = flags.indexOf('p') != -1;
                        argOffset++;
                    } else {
                        printUsage();
                    }
                }
                try {
                    List<Path> paths;
                    PathfindingSolver solver = new PathfindingSolver(workingStream);
                    if(algorithm.equals("astar")) {
                        HashMap<String, Node> nodes = Utils.parseAStar(args[1 + argOffset], args[2 + argOffset]);
                        Node start = nodes.get(args[3 + argOffset]);
                        Node end = nodes.get(args[4 + argOffset]);
                        if (start == null || end == null) {
                            System.err.println("Both the start and end nodes must appear in the heuristics/edges file.");
                        }
                        paths = solver.solve(start, end, persist);
                    }
                    else {
                        HashMap<String, Node> nodes = Utils.parseBranchAndBound(args[1 + argOffset]);
                        Node start = nodes.get(args[2 + argOffset]);
                        Node end = nodes.get(args[3 + argOffset]);
                        if (start == null || end == null) {
                            System.err.println("Both the start and end nodes must appear in the edges file.");
                        }
                        paths = solver.solve(start, end, persist);
                    }
                    int i = 1;
                    for (Path path : paths) {
                        System.out.println("Solution #" + i++ + ":  " + path.toString());
                    }
                } catch (IllegalArgumentException e) {
                    System.err.println(e.getMessage());
                } catch (IOException e) {
                    System.err.println(e.getMessage());
                }
                System.exit(0);
            }
        }
        printUsage();
        System.exit(1);
    }

    private static void printUsage() {
        System.err.println();
        System.err.println("Usage: java PathFinder [astar|bb] [-pv] <edge_file> [<heuristic_file>] <start_id> <end_id>");
        System.err.println("Flags (optional):");
        System.err.println("    p: Persist - find several solutions, not just the first optimal one. Note that it will not necessarily find all solutions due to pruning.");
        System.err.println("    v: Verbose - print out partial paths whilst running the algorithm (slower, due to more path evaluations).");
        System.err.println("Modes (choose 1):");
        System.err.println("    astar: Perform an A* search. Requires a heuristics file path to be specified.");
        System.err.println("    bb:    Perform a branch and bound search with dynamic programming. No heuristics file should be specified.");
        System.err.println("Note that both input files must be clean (no headings), and the 'start' and 'end' ids should appear exactly as they do in the files supplied.");
        System.err.println();
    }
}
