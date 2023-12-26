package com.unhuman.adventofcode2023;

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
import java.util.LinkedHashSet;
import java.util.List;
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
        Set<String> endpoints = new HashSet<>();

        MutableGraph<String> graph = GraphBuilder
                .undirected()
                .expectedNodeCount(item.size())
                .build();

        for (ItemLine line : item) {
            String common = line.get(0);
            endpoints.add(common);
            graph.addNode(common);
            for (int i = 1; i < line.size(); i++) {
                String connected = line.get(i);
                graph.addNode(connected);
                graph.hasEdgeConnecting(common, connected);

                endpoints.add(connected);
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

//        for (int i = 0; i < connections.size() - 2; i++) {
//            graph.removeEdge(connections.get(i).conn1, connections.get(i).conn2);
//            for (int j = i + 1; j < connections.size() - 1; j++) {
//                System.out.println("Processing i: " + i + " j: " + j + " / " + (connections.size() - 1));
//                graph.removeEdge(connections.get(j).conn1, connections.get(j).conn2);
//                for (int k = j + 1; k < connections.size(); k++) {
//                    graph.removeEdge(connections.get(k).conn1, connections.get(k).conn2);
//
//                    // process
//
//
//                    // after processing, restore the edge
//                    graph.hasEdgeConnecting(connections.get(k).conn1, connections.get(k).conn2);
//                }
//                graph.hasEdgeConnecting(connections.get(j).conn1, connections.get(j).conn2);
//            }
//            graph.hasEdgeConnecting(connections.get(i).conn1, connections.get(i).conn2);
//        }


        for (int i = 0; i < connections.size() - 2; i++) {
            removeConnection(endpointConnections, connections.get(i));
//            System.out.println("Processing i " + i + " / " + (connections.size() - 2));
            for (int j = i + 1; j < connections.size() - 1; j++) {
                System.out.println("Processing i: " + i + " j: " +  j + " / " + (connections.size() - 1));
                removeConnection(endpointConnections, connections.get(j));
                for (int k = j + 1; k < connections.size(); k++) {
//                    System.out.println("Processing k " + k + " / " + (connections.size()));
                    removeConnection(endpointConnections, connections.get(k));

                    List<Set<String>> groups = getGroups(endpointConnections, new LinkedHashSet<>(endpoints));
                    if (groups.size() == 2) {
                        System.out.print("Removed Connection " + i + "/" + j + "/" + k + " ");
                        System.out.print("i: " + i + " " + connections.get(i) + " ");
                        System.out.print("j: " + j + " " + connections.get(j) + " ");
                        System.out.print("k: " + k + " " + connections.get(k) + " ");
                        System.out.println();
                        System.out.println("Score: " + groups.get(0).size() * groups.get(1).size());
                        return groups.get(0).size() * groups.get(1).size();
                    }

                    restoreConnection(endpointConnections, connections.get(k));
                }
                restoreConnection(endpointConnections, connections.get(j));
            }
            restoreConnection(endpointConnections, connections.get(i));
        }

        return 1;
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
