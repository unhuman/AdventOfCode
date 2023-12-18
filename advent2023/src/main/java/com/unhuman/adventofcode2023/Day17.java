package com.unhuman.adventofcode2023;

import com.unhuman.adventofcode.aoc_framework.InputParser;
import com.unhuman.adventofcode.aoc_framework.representation.ConfigGroup;
import com.unhuman.adventofcode.aoc_framework.utility.InspectionMatrix;
import com.unhuman.adventofcode.aoc_framework.utility.Matrix;

import java.awt.Point;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Day17 extends InputParser {
    private static final String regex1 = "(\\d)";
    private static final String regex2 = null;

    private static final int DIRECTION_LIMIT = 3;

    enum Direction { UP, DOWN, LEFT, RIGHT }
    static final Direction UP_DOWN[] = { Direction.UP, Direction.DOWN };
    static final Direction LEFT_RIGHT[] = { Direction.LEFT, Direction.RIGHT };

    public Day17() {
        super(2023, 17, regex1, regex2);
    }

    public Day17(String filename) {
        super(filename, regex1, regex2);
    }

    static class Flow {
        protected Point point;
        protected Direction direction;

        public Flow(Flow other, Direction direction) {
            this(other.point.x, other.point.y, direction);
        }

        public Flow(Flow other) {
            this(other.point.x, other.point.y, other.direction);
        }

        public Flow(int x, int y, Direction direction) {
            this.point = new Point(x, y);
            this.direction = direction;
        }

        public Point getPoint() {
            return point;
        }

        public Direction getDirection() {
            return direction;
        }

        public Direction[] getTurns() {
            switch (direction) {
                case UP:
                case DOWN:
                    return LEFT_RIGHT;
                case LEFT:
                case RIGHT:
                    return UP_DOWN;
            }
            return null;
        }

        public void move() {
            switch (direction) {
                case UP: this.point.y--; break;
                case DOWN: this.point.y++; break;
                case LEFT: this.point.x--; break;
                case RIGHT: this.point.x++; break;
            }
        }

        public class Graph {

            private Set<Node> nodes = new HashSet<>();

            public void addNode(Node nodeA) {
                nodes.add(nodeA);
            }

            // getters and setters
        }

        @Override
        public boolean equals(Object obj) {
            Flow other = (Flow) obj;
            return (this.direction == other.direction && this.point.equals(other.point));
        }

        @Override
        public int hashCode() {
            return this.point.hashCode() * 17 + this.direction.hashCode();
        }
    }

    static class FlowTrack extends Flow {
        int run;

        FlowTrack(Flow flow, int run) {
            super(flow);
            this.run = run;
        }

        @Override
        public boolean equals(Object obj) {
            FlowTrack other = (FlowTrack) obj;
            return (super.equals(obj) && this.run == other.run);
        }

        @Override
        public int hashCode() {
            return super.hashCode() * 17 + run;
        }
    }

    static class Node {
        private String name;

        private List<Node> shortestPath = new LinkedList<>();

        private Integer distance = Integer.MAX_VALUE;

        Map<Node, Integer> adjacentNodes = new HashMap<>();

        public void addDestination(Node destination, int distance) {
            adjacentNodes.put(destination, distance);
        }

        public Node(String name) {
            this.name = name;
        }

        // getters and setters

        public String getName() {
            return name;
        }

        public List<Node> getShortestPath() {
            return shortestPath;
        }

        public void setShortestPath(List<Node> shortestPath) {
            this.shortestPath = shortestPath;
        }

        public Integer getDistance() {
            return distance;
        }

        public Map<Node, Integer> getAdjacentNodes() {
            return adjacentNodes;
        }

        public void setDistance(Integer distance) {
            this.distance = distance;
        }
    }

    public class Graph {

        private HashMap<String, Node> nodes = new LinkedHashMap<>();

        public void addNode(Node node) {
            nodes.put(node.getName(), node);
        }

        // getters and setters

        public Node getNode(String name) {
            return nodes.get(name);
        }
    }

    @Override
    public Object processInput1(ConfigGroup configGroup, ConfigGroup configGroup1) {
        Matrix matrix = new Matrix(configGroup, InspectionMatrix.DataType.DIGIT);
        Flow current = new Flow(0, 0, Direction.RIGHT);

        Node origin = new Node("Origin");
        origin.setDistance(0);
        Graph graph = setupGraph(matrix, origin);

        graph = calculateShortestPathFromSource(graph, origin);

        List<Node> shortestPath = origin.getShortestPath();

        Set<Node> destinationTargets = generateDestinationTargets(graph, matrix, DIRECTION_LIMIT);

        long shortestPathCount = Integer.MAX_VALUE;
        for (Node destinationTarget: destinationTargets) {
            List<Node> shortestPathBack = destinationTarget.getShortestPath();
            if ((shortestPathBack == null) || shortestPathBack.size() == 0) {
                continue;
            }
            int distance = shortestPathBack.get(shortestPathBack.size() - 1).getDistance();
            if (distance < shortestPathCount) {
                shortestPathCount = distance;
            }
        }
        shortestPathCount += matrix.getValue(matrix.getWidth() - 1, matrix.getHeight() - 1);
        return shortestPathCount;
    }

    int findShortestPath(Graph graph, Node origin, Set<Node> destinations) {
        return 0;
    }

    Set<Node> generateDestinationTargets(Graph graph, Matrix matrix, int directionLimit) {
        Set<Node> destinations = new HashSet<>();
        int x = matrix.getWidth() - 1;
        int y = matrix.getHeight() - 1;
        for (Direction dirCheck: Direction.values()) {
            for (int i = 1; i <= directionLimit; i++) {
                String nodeName = generateNodeName(x, y, dirCheck, i);
                Node destinationNode = graph.getNode(nodeName);
                if (destinationNode != null) {
                    destinations.add(destinationNode);
                }
            }
        }
        return destinations;
    }

    Graph setupGraph(Matrix matrix, Node originNode) {
        Graph graph = new Graph();

        // Set up all the nodes
        for (int y = 0; y < matrix.getHeight(); y++) {
            for (int x = 0; x < matrix.getWidth(); x++) {
                for (int i = 1; i <= 3; i++) {
                    if (matrix.isValid(new Point(x - i, y))) {
                        Node node = new Node(generateNodeName(x, y, Direction.RIGHT, i));
                        graph.addNode(node);
                    }
                    if (matrix.isValid(new Point(x + i, y))) {
                        Node node = new Node(generateNodeName(x, y, Direction.LEFT, i));
                        graph.addNode(node);
                    }
                    if (matrix.isValid(new Point(x, y - i))) {
                        Node node = new Node(generateNodeName(x, y, Direction.DOWN, i));
                        graph.addNode(node);
                    }
                    if (matrix.isValid(new Point(x, y + i))) {
                        Node node = new Node(generateNodeName(x, y, Direction.UP, i));
                        graph.addNode(node);
                    }
                }
            }
        }

        // starting point has one way egress
        graph.addNode(originNode);
        if (matrix.getWidth() > 1) {
            originNode.addDestination(graph.getNode(generateNodeName(1, 0, Direction.RIGHT, 1)), matrix.getValue(1, 0));
        }
        if (matrix.getHeight() > 1) {
            originNode.addDestination(graph.getNode(generateNodeName(0, 1, Direction.DOWN, 1)), matrix.getValue(0, 1));
        }

        for (int x = 0; x <= matrix.getWidth(); x++) {
            for (int y = 0; y <= matrix.getHeight(); y++) {
                for (int i = 1; i <= 3; i++) {
                    for (Direction dir: Direction.values()) {
                        String sourceName = generateNodeName(x, y, dir, i);
                        Node sourceNode = graph.getNode(sourceName);

                        if (sourceNode == null) {
                            continue;
                        }

                        // add destination if we are moving less than 3 (can't continue past 3)
                        if (i < 3) {
                            int xDest = x + ((dir == Direction.LEFT) ? -1 : 0) + ((dir == Direction.RIGHT) ? 1 : 0);
                            int yDest = y + ((dir == Direction.UP) ? -1 : 0) + ((dir == Direction.DOWN) ? 1 : 0);
                            if (matrix.isValid(new Point(xDest, yDest))) {
                                String destName = generateNodeName(xDest, yDest, dir, i + 1);
                                sourceNode.addDestination(graph.getNode(destName), matrix.getValue(xDest, yDest));
                            }
                        }

                        // Perform any turns we can find
                        Direction[] turns = (dir == Direction.LEFT || dir == Direction.RIGHT)
                                ? UP_DOWN : LEFT_RIGHT;
                        for (Direction turn: turns) {
                            int xTurn = x;
                            int yTurn = y;
                            switch (turn) {
                                case LEFT -> xTurn--;
                                case RIGHT -> xTurn++;
                                case UP -> yTurn--;
                                case DOWN -> yTurn++;
                            }

                            if (matrix.isValid(new Point(xTurn, yTurn))) {
                                String destName = generateNodeName(xTurn, yTurn, turn, 1);
                                try {
                                    sourceNode.addDestination(graph.getNode(destName), matrix.getValue(xTurn, yTurn));
                                } catch (NullPointerException npe) {
                                    // do nothing - this is easier than figuring out where things can go
                                }
                            }
                        }
                    }
                }
            }
        }
        return graph;
    }

    String generateNodeName(int x, int y, Direction dir, int steps) {
        return String.format("(%d, %d) - %s - %d", x, y, dir.name(), steps);
    }

    public Graph calculateShortestPathFromSource(Graph graph, Node source) {
        Set<Node> settledNodes = new HashSet<>();
        Set<Node> unsettledNodes = new HashSet<>();

        unsettledNodes.add(source);

        while (unsettledNodes.size() != 0) {
            Node currentNode = getLowestDistanceNode(unsettledNodes);
            unsettledNodes.remove(currentNode);
            for (Map.Entry<Node, Integer> adjacencyPair:
                    currentNode.getAdjacentNodes().entrySet()) {
                Node adjacentNode = adjacencyPair.getKey();

                if (adjacentNode == null) {
                    continue;
                }

                Integer edgeWeight = adjacencyPair.getValue();
                if (!settledNodes.contains(adjacentNode)) {
                    calculateMinimumDistance(adjacentNode, edgeWeight, currentNode);
                    unsettledNodes.add(adjacentNode);
                }
            }
            settledNodes.add(currentNode);
        }
        return graph;
    }


    private static Node getLowestDistanceNode(Set<Node> unsettledNodes) {
        Node lowestDistanceNode = null;
        int lowestDistance = Integer.MAX_VALUE;
        for (Node node: unsettledNodes) {
            int nodeDistance = node.getDistance();
            if (nodeDistance < lowestDistance) {
                lowestDistance = nodeDistance;
                lowestDistanceNode = node;
            }
        }
        return lowestDistanceNode;
    }

    private static void calculateMinimumDistance(Node evaluationNode,
                                                 Integer edgeWeigh, Node sourceNode) {
        Integer sourceDistance = sourceNode.getDistance();
        if (sourceDistance + edgeWeigh < evaluationNode.getDistance()) {
            evaluationNode.setDistance(sourceDistance + edgeWeigh);
            LinkedList<Node> shortestPath = new LinkedList<>(sourceNode.getShortestPath());
            shortestPath.add(sourceNode);
            evaluationNode.setShortestPath(shortestPath);
        }
    }

//
//    Long navigate(Matrix matrix, Flow current, int allowForward) {
//        if (!matrix.isValid(current.point)) {
//            return null;
//        }
//
//        // Target endpoint
//        if (current.point.x == matrix.getWidth() - 1 && current.point.y == matrix.getHeight() - 1) {
//            return 0L;
//        }
//
//        // track this point
//        if (touchedPoints.contains(current)) {
//            return null;
//        }
//        touchedPoints.add(current);
//
//        if (cache.containsKey(current)) {
//            return cache.get(current);
//        }
//
//        Long value = navigateInternal(matrix, current, allowForward);
//
//        if (value != null) {
//            cache.put(current, value);
//            System.out.println("Point: " + current.point + " dir: " + current.direction + " cache Value: " + value);
//        }
//
//        return value;
//    }
//
//    Long navigateInternal(Matrix matrix, Flow current, int allowForward) {
//        SortedSet<Long> heatLoss = new TreeSet<>();
//        Long value;
//
//        // move down this path (up to 3)
//        Long localValue = 0L;
//        if (allowForward > 0) {
//            Flow movingCurrent = new Flow(current);
//            movingCurrent.move();
//
//            // Short circuit if we are moving into an invalid space
//            if (!matrix.isValid(movingCurrent.point)) {
//                return null;
//            }
//
//            value = navigate(matrix, movingCurrent, allowForward - 1);
//            if (value != null) {
//                localValue += matrix.getValue(movingCurrent.point) - '0';
//                heatLoss.add(localValue + value);
//            }
//        }
//
//        // try turning from this point
//        Direction[] turns = current.getTurns();
//
//        Flow turn = new Flow(current, turns[0]);
//        value = navigate(matrix, turn, directionLimit);
//        if (value != null) {
//            heatLoss.add(value);
//        }
//
//        turn = new Flow(current, turns[1]);
//        value = navigate(matrix, turn, directionLimit);
//        if (value != null) {
//            heatLoss.add(value);
//        }
//
//        return (heatLoss.size() > 0) ? heatLoss.first() : null;
//    }

    @Override
    public Object processInput2(ConfigGroup configGroup, ConfigGroup configGroup1) {
        Matrix matrix = new Matrix(configGroup, InspectionMatrix.DataType.DIGIT);
        Flow current = new Flow(0, 0, Direction.RIGHT);

        Node origin = new Node("Origin");
        origin.setDistance(0);
        Graph graph = setupGraph2(matrix, origin);

        graph = calculateShortestPathFromSource(graph, origin);

        List<Node> shortestPath = origin.getShortestPath();

        Set<Node> destinationTargets = generateDestinationTargets(graph, matrix, 10);

        long shortestPathCount = Integer.MAX_VALUE;
        for (Node destinationTarget: destinationTargets) {
            List<Node> shortestPathBack = destinationTarget.getShortestPath();
            if ((shortestPathBack == null) || shortestPathBack.size() == 0) {
                continue;
            }
            int distance = shortestPathBack.get(shortestPathBack.size() - 1).getDistance();
            if (distance < shortestPathCount) {
                shortestPathCount = distance;
            }
        }
        shortestPathCount += matrix.getValue(matrix.getWidth() - 1, matrix.getHeight() - 1);
        return shortestPathCount;
    }

    Graph setupGraph2(Matrix matrix, Node originNode) {
        Graph graph = new Graph();

        // Set up all the nodes
        for (int y = 0; y < matrix.getHeight(); y++) {
            for (int x = 0; x < matrix.getWidth(); x++) {
                for (int i = 1; i <= 10; i++) {
                    if (matrix.isValid(new Point(x - i, y))) {
                        int minI = (4 - (matrix.getWidth() - 1 - x));
                        if (i >= minI) {
                            Node node = new Node(generateNodeName(x, y, Direction.RIGHT, i));
                            graph.addNode(node);
                        }
                    }
                    if (matrix.isValid(new Point(x + i, y))) {
                        int minI = 4 - x - 1;
                        if (i > minI) {
                            Node node = new Node(generateNodeName(x, y, Direction.LEFT, i));
                            graph.addNode(node);
                        }
                    }
                    if (matrix.isValid(new Point(x, y - i))) {
                        int minI = (4 - (matrix.getHeight() - 1 - y));
                        if (i >= minI) {
                            Node node = new Node(generateNodeName(x, y, Direction.DOWN, i));
                            graph.addNode(node);
                        }
                    }
                    if (matrix.isValid(new Point(x, y + i))) {
                        int minI = 4 - y - 1;
                        if (i > minI) {
                            Node node = new Node(generateNodeName(x, y, Direction.UP, i));
                            graph.addNode(node);
                        }
                    }
                }
            }
        }

        // starting point has one way egress
        graph.addNode(originNode);
        if (matrix.getWidth() > 1) {
            originNode.addDestination(graph.getNode(generateNodeName(1, 0, Direction.RIGHT, 1)), matrix.getValue(1, 0));
        }
        if (matrix.getHeight() > 1) {
            originNode.addDestination(graph.getNode(generateNodeName(0, 1, Direction.DOWN, 1)), matrix.getValue(0, 1));
        }

        for (int y = 0; y <= matrix.getHeight(); y++) {
            for (int x = 0; x <= matrix.getWidth(); x++) {
                for (Direction dir: Direction.values()) {
                    for (int i = 1; i <= 10; i++) {
                        String sourceName = generateNodeName(x, y, dir, i);
                        Node sourceNode = graph.getNode(sourceName);

                        if (sourceNode == null) {
                            continue;
                        }

                        // add destination if we are moving less than 10 (can't continue past 10)
                        if (i < 10) {
                            int xDest = x + ((dir == Direction.LEFT) ? -1 : 0) + ((dir == Direction.RIGHT) ? 1 : 0);
                            int yDest = y + ((dir == Direction.UP) ? -1 : 0) + ((dir == Direction.DOWN) ? 1 : 0);
                            if (matrix.isValid(new Point(xDest, yDest))) {
                                String destName = generateNodeName(xDest, yDest, dir, i + 1);
                                sourceNode.addDestination(graph.getNode(destName), matrix.getValue(xDest, yDest));
                            }
                        }

                        // Perform any turns we can find
                        if (i >= 4) {
                            Direction[] turns = (dir == Direction.LEFT || dir == Direction.RIGHT)
                                    ? UP_DOWN : LEFT_RIGHT;
                            for (Direction turn: turns) {
                                int xTurn = x;
                                int yTurn = y;
                                switch (turn) {
                                    case LEFT -> xTurn--;
                                    case RIGHT -> xTurn++;
                                    case UP -> yTurn--;
                                    case DOWN -> yTurn++;
                                }

                                if (matrix.isValid(new Point(xTurn, yTurn))) {
                                    String destName = generateNodeName(xTurn, yTurn, turn, 1);
                                    try {
                                        sourceNode.addDestination(graph.getNode(destName), matrix.getValue(xTurn, yTurn));
                                    } catch (NullPointerException npe) {
                                        // do nothing - this is easier than figuring out where things can go
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return graph;
    }
}
