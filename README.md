# OOP-Ex2

> Made by Eitan Wechsler and Asaf Yekutiel

### Introduction
This project is an assignment in an object-oriented course at Ariel University.
The project is about implementing a directed weithted graph and exploring some graph algorithms.

### The package api
This package contains all the implementation of the graph.
it represent the graph as adjacency list using hash maps.
The class DWGraph - Represent the graph implemented as adejency list.
The class GraphAlgo - Contaions several algorithms that can be operate on a given graph.
The class edge - Represnts an edge in a graph.
The class Node_Data - Represnts a node in a graph.
The class geoLo - Represnts a 3d location.

### Algorithm used:
some variations of the BFS algorithm and dijkstra algorithms.
* `bfs(weighted_graph g)` : This private method based on breadth-first search.
BFS is an algorithm for traversing or searching graph data structures.
The method checks whether or not the graph is strongly linked,
in other words it checks whether there is a path between node to each other node.
The method use counter that count the number of nodes that connected to the source node.
If counter value equal to the number of nodes in this graph that means that the source node connected.
To check if the whole graph is strongly connected needs to run the method on all the nodes in the graph.
The method stored a queue of the visited nodes:
1. Pop the first node from the queue.
2. Gets a collection of this node edges.
3. Goes through all the nodes that have an edge from the pop node.
4. Check if the node has already been visited, if so skip it(tag = 1 -> visited, tag = -1 -> not visited).
  Otherwise mark it as visited (update his own tag) and add the node to the queue.
5. Add this node's neighbors to the queue and repeat these steps
The method use counter that count the number of nodes that connected to the source node.
After the queue is empty check if the counter value equal to the number of nodes in this graph
that means that the source node connected.
If so the method will return true, Otherwise false.
Note: The method change the tag values.
Complexity: O(|V|+|E|), |V|=number of nodes, |E|=number of edges.

* `Dijkstra(node_info src, node_info dest)` : This private method based on Dijkstra's algorithm.
Dijkstra's algorithm is an algorithm for finding the shortest paths between nodes in a graph.
In other words it finds the shortest paths between the source node and the destination node.
The method uses the weight of each node to update his current distance from the source node.
The method stored a priority queue(priority is determined by the weight) of the visited nodes:
1. Pop the first node from the queue.
2. Visit each one of this nodes neighbors:
3. Check if the node has already been visited, if so skip it(tag = Black -> visited, tag = White -> not visited).
4. Updates his weight to be the distance between the node and the source node.
5. Updates his tag To be the node's id from which he came to.
6. Add this node to the queue.
7. After going through all the neighbors of the node, updates that we visited this node by change his info to "Black" and therefore will not visit it again.
8. Repeat these steps until the queue is empty or has reached the destination node.
If the queue is empty it means it did not reach the destination node (the graph is not connected), return infinity.
Otherwise returns the tag of the destination node.
Note: The method change the info, tag and pre values.
Complexity: O((|V|+|E|)log|V|), |V|=number of nodes, |E|=number of edges.

* `resetTag()` : private method resets the values of all the tags of the nodes in the graph.
  Reset the value = change it back to default value: -1.
* `resetWeight()` : private method resets the value of weight in each node in the graph.
  Reset the value = change it back to default value: Double.MAX_VALUE (infinity).
* `resetInfo()` : private method resets the value of info in each node in the graph.
  Reset the value = change it back to default value: "White".
  
  ## performances

| Graph Size | isConnected | Center  | shortestPathDist | shortestPath |  tsp   | 
|------------|-------------|---------|------------------|--------------|--------|
| 1000       |   653 ms    |too long |     68 ms        |    64 ms     |too long| 
| 10k        |   too long  |too long |    1 sec 378 ms  |  2sec 508 ms |too long|
| 100k       |   too long  |too long |      ""          |      ""      |too long|
| 1M         |   too long  |too long |      ""          |      ""      |too long|
| G1 (16)    |   32 ms     |  42 ms  |     46 ms        |    17 ms     |  32 ms |
| G2 (31)    |   32 ms     |  54 ms  |     32 ms        |    31 ms     |  47 ms |
| G3 (48)    |   17 ms     | 196 ms  |     29 ms        |    48 ms     |  54 ms |

  
  ## External info:
- More about graph : https://en.wikipedia.org/wiki/Directed_graph
- More about Dijkstra's algorithm : https://en.wikipedia.org/wiki/Dijkstra%27s_algorithm
- More about BFS algorithm : https://en.wikipedia.org/wiki/Breadth-first_search
- More about HashMap : https://docs.oracle.com/javase/8/docs/api/java/util/HashMap.html
