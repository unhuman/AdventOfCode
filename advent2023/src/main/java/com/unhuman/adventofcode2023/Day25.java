package com.unhuman.adventofcode2023;

import com.google.common.graph.EndpointPair;
import com.google.common.graph.GraphBuilder;
import com.google.common.graph.MutableGraph;
import com.unhuman.adventofcode.aoc_framework.InputParser;
import com.unhuman.adventofcode.aoc_framework.representation.ConfigGroup;
import com.unhuman.adventofcode.aoc_framework.representation.GroupItem;
import com.unhuman.adventofcode.aoc_framework.representation.ItemLine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class Day25 extends InputParser {
    private static final String regex1 = "(?:[^\\w]*?)(\\w+)";
    private static final String regex2 = null;

    public Day25() {
        super(2023, 25, regex1, regex2);
    }

    public Day25(String filename) {
        super(filename, regex1, regex2);
    }

    record Connection(String conn1, String conn2) {
    }

    @Override
    public Object processInput1(ConfigGroup configGroup, ConfigGroup configGroup1) {
        // easier to assume there's only one group
        ArrayList<Connection> connections = new ArrayList<>();
        HashMap<String, List<String>> endpointConnections = new HashMap<>();
        GroupItem item = configGroup.get(0);
        LinkedHashMap<String, Integer> endpoints = new LinkedHashMap<>();

        MutableGraph<String> graph = GraphBuilder
                .undirected()
                .expectedNodeCount(item.size())
                .build();

        for (ItemLine line : item) {
            String common = line.get(0);
            endpoints.put(common, 1);
            graph.addNode(common);
            for (int i = 1; i < line.size(); i++) {
                String connected = line.get(i);
                graph.addNode(connected);
                graph.putEdge(common, connected);

                endpoints.put(connected, 1);
                if (!endpointConnections.containsKey(common)) {
                    endpointConnections.put(common, new ArrayList<>());
                }
                endpointConnections.get(common).add(connected);
                if (!endpointConnections.containsKey(connected)) {
                    endpointConnections.put(connected, new ArrayList<>());
                }
                endpointConnections.get(connected).add(common);

                connections.add(new Connection(common, connected));
//                connections.add(new Connection(connected, common));
            }
        }

        // Karger
        Random r = new Random();
        while(graph.nodes().size() > 2){
            int i = r.nextInt(graph.edges().size());
            EndpointPair<String> e = (EndpointPair<String>) graph.edges().toArray()[i];
            mergeVertex(graph, endpoints, e.nodeU(), e.nodeV());
        }

        return endpoints.get(graph.edges().stream().findFirst().get().nodeU())
                * endpoints.get(graph.edges().stream().findFirst().get().nodeV());


//        Traverser<String> traverser = Traverser.forGraph(graph);
//        Iterable<String> reaches1 = traverser.breadthFirst(graph.edges().stream().findFirst().get().nodeU());
//        Iterable<String> reaches2 = traverser.breadthFirst(graph.edges().stream().findFirst().get().nodeV());
//        AtomicInteger count1 = new AtomicInteger();
//        AtomicInteger count2 = new AtomicInteger();
//
//        reaches1.forEach(string -> count1.incrementAndGet());
//        reaches2.forEach(string -> count2.incrementAndGet());

//        return count1.get() * count2.get();

//        Set<String> lookFors = new HashSet<>(10);
//        for (int i = 0; i < connections.size() - 2; i++) {
//            lookFors.add(connections.get(i).conn1);
//            lookFors.add(connections.get(i).conn2);
//            graph.removeEdge(connections.get(i).conn1, connections.get(i).conn2);
//            for (int j = i + 1; j < connections.size() - 1; j++) {
//                System.out.println("Processing Graph i=" + i + " j=" + j + " / " + (connections.size() - 1));
//                lookFors.add(connections.get(j).conn1);
//                lookFors.add(connections.get(j).conn2);
//                graph.removeEdge(connections.get(j).conn1, connections.get(j).conn2);
//                for (int k = j + 1; k < connections.size(); k++) {
//                    lookFors.add(connections.get(k).conn1);
//                    lookFors.add(connections.get(k).conn2);
//                    graph.removeEdge(connections.get(k).conn1, connections.get(k).conn2);
//
//                    // process
//                    Traverser<String> traverser = Traverser.forGraph(graph);
//
//                    // TODO: Need to only look through lookFors.
//                    Iterable<String> reaches = traverser.breadthFirst(connections.get(k).conn1);
//
//                    int counter = 0; // include self
//                    for (String value: reaches) {
//                        ++counter;
//                    }
//                    if (counter != endpoints.size()) {
//                        System.out.println("i = " + i);
//                        System.out.println("j = " + j);
//                        System.out.println("k = " + k);
//                        System.out.println("here!!! " + counter);
//                        return counter * (endpoints.size() - counter);
//                    }
//
//                    // after processing, restore the edge
//                    graph.putEdge(connections.get(k).conn1, connections.get(k).conn2);
//
//                    lookFors.remove(connections.get(k).conn1);
//                    lookFors.remove(connections.get(k).conn2);
//                }
//                graph.putEdge(connections.get(j).conn1, connections.get(j).conn2);
//                lookFors.remove(connections.get(j).conn1);
//                lookFors.remove(connections.get(j).conn2);
//            }
//            graph.putEdge(connections.get(i).conn1, connections.get(i).conn2);
//            lookFors.remove(connections.get(i).conn1);
//            lookFors.remove(connections.get(i).conn2);
//        }
//
//
//        for (int i = 0; i < connections.size() - 2; i++) {
//            removeConnection(endpointConnections, connections.get(i));
////            System.out.println("Processing i " + i + " / " + (connections.size() - 2));
//            for (int j = i + 1; j < connections.size() - 1; j++) {
//                System.out.println("Processing Data i=" + i + " j=" +  j + " / " + (connections.size() - 1));
//                removeConnection(endpointConnections, connections.get(j));
//                for (int k = j + 1; k < connections.size(); k++) {
////                    System.out.println("Processing k " + k + " / " + (connections.size()));
//                    removeConnection(endpointConnections, connections.get(k));
//
//                    List<Set<String>> groups = getGroups(endpointConnections, new LinkedHashSet<>(endpoints));
//                    if (groups.size() == 2) {
//                        System.out.print("Removed Connection " + i + "/" + j + "/" + k + " ");
//                        System.out.print("i: " + i + " " + connections.get(i) + " ");
//                        System.out.print("j: " + j + " " + connections.get(j) + " ");
//                        System.out.print("k: " + k + " " + connections.get(k) + " ");
//                        System.out.println();
//                        System.out.println("Score: " + groups.get(0).size() * groups.get(1).size());
//                        return groups.get(0).size() * groups.get(1).size();
//                    }
//
//                    restoreConnection(endpointConnections, connections.get(k));
//                }
//                restoreConnection(endpointConnections, connections.get(j));
//            }
//            restoreConnection(endpointConnections, connections.get(i));
//        }
//
//        return 1;
    }

    void mergeVertex(MutableGraph<String> graph, Map<String, Integer> endpoints, String vertex1, String vertex2) {
        List<EndpointPair<String>> edges = graph.edges().stream().toList();
        for (int i = 0; i < edges.size(); i++) {
            EndpointPair<String> e = edges.get(i);

            if (e.nodeU().equals(vertex1) || e.nodeV().equals(vertex1)) {
                if (e.nodeU().equals(vertex2) || e.nodeV().equals(vertex2)) {
                    graph.removeEdge(e);
                    endpoints.put(vertex2, endpoints.get(vertex1) + endpoints.get(vertex2));
                } else {
                    graph.removeEdge(e);
                    String otherNode = e.adjacentNode(vertex1);
                    graph.putEdge(vertex2, otherNode);
                }
            }
        }
        graph.removeNode(vertex1);
    }


    HashMap<String, List<String>> copyConnectionsTree(HashMap<String, List<String>> tree) {
        HashMap<String, List<String>> copy = new HashMap<>();
        tree.forEach((k, v) -> copy.put(k, new ArrayList<>(v)));
        return copy;
    }

    List<Set<String>> getGroups(HashMap<String, List<String>> endpointConnections, Set<String> endpoints) {
        List<Set<String>> response = new ArrayList<>();

        endpoints = new HashSet<>(endpoints);

        while (true) {
            if (endpoints.size() == 0) {
                return response;
            }

            String endpointRemove = endpoints.stream().findFirst().get();

            Set<String> connectedEndpoints = findConnectedEndpoints(endpointConnections, endpointRemove);
            endpoints.removeAll(connectedEndpoints);
            response.add(connectedEndpoints);
        }
    }

    Set<String> findConnectedEndpoints(HashMap<String, List<String>> endpointConnections,
                                       String endpointRemove) {
        Set<String> results = new HashSet<>();
        Set<String> investigate = Collections.singleton(endpointRemove);

        while (investigate.size() > 0) {
            Set<String> nextInvestigate = new LinkedHashSet<>();
            for (String lookAt: investigate) {
                results.add(lookAt);
                List<String> nextRound = new ArrayList<>(endpointConnections.get(lookAt));
                nextRound.removeAll(results);
                nextInvestigate.addAll(nextRound);
            }
            investigate = nextInvestigate;
        }

        return results;
    }

    void restoreConnection(HashMap<String, List<String>> endpointConnections, Connection connection) {
        endpointConnections.get(connection.conn1).add(connection.conn2);
        endpointConnections.get(connection.conn2).add(connection.conn1);
    }

    void removeConnection(HashMap<String, List<String>> endpointConnections, Connection connection) {
        endpointConnections.get(connection.conn1).remove(connection.conn2);
        endpointConnections.get(connection.conn2).remove(connection.conn1);
    }

    @Override
    public Object processInput2(ConfigGroup configGroup, ConfigGroup configGroup1) {
        return 2;
    }
}
