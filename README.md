# Pathfinder
Produced for a Curtin University Artificial and Machine Intelligence assignment in 2014. Probably totally shit. Apparently I elected to avoid Maven, so I obviously did one thing right.

## Usage
All input files should be clean of any headings. The A* and Branch and Bound scripts have the flags required to persist in finding solutions and output partial paths _[**note:** iirc, this was a dumb requirement of the assignment that I had to support after implementing an optimal solution finder]_. The hill climbing program will output the found maxima and write the path to it to 'seq.txt'. _[**note:** another dumb requirement, if I recall]_

### Compile
`make`

### Clean:
`make clean`

### Perform A* Search:
`./astar-search <edges_file> <heuristics_file> <start_node> <end_node>`

### Perform Branch and Bound Search:
`./dyn-branch-search <edges_file> <start_node> <end_node>`

### Perform Stochastic Hill Traversal:
`./stochastic-hill <map_file>`


## Included Test Files
- `pathfinder-test.edges` &mdash; an example edges file for testing the pathfinders
- `pathfinder-test.heuristics` &mdash; a corresponding example heuristics file for testing the pathfinders
- `terrain.xyz` &mdash; a terrain for testing the trashy hill-climber
- `terrain2.xyz` &mdash; a bigger terrain