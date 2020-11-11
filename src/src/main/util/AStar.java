package main.util;

/**
 * based of pseudoscope, and javascript code from https://www.growingwiththeweb.com/2012/06/a-pathfinding-algorithm.html
 * 
 */

import java.util.ArrayList;

import engine.Utils;
import engine.physics.Point;
import main.map.Map;
import main.map.tiles.Tile;

public class AStar {

    // target position
    static int targetX = 0;
    static int targetY = 0;

    // positions to target along the path
    public static ArrayList<Point> path = new ArrayList<Point>();

    // 8 position around a tile
    static int[][] checkPositions = { { 0, -1 }, { -1, 0 }, { 1, 0 }, { 0, 1 }, { -1, -1 }, { 1, -1 }, { -1, 1 },
            { 1, 1 } };

    // distance between two points (without slow sqrt)
    static float heuristic(int x, int y, int endX, int endY) {
        return (float) (Math.abs(endX - x) + Math.abs(endY - y));
    }

    // search for a node
    static boolean arrayListHasNode(ArrayList<Node> arr, Node n) {
        for (int i = 0; i < arr.size(); i++) {
            if (arr.get(i).equals(n)) {
                return true;
            }
        }
        return false;
    }

    // returns an array list of points to travel between to get to a position
    public static ArrayList<Point> findPath(int startXX, int startYY, int endXX, int endYY) {
        int startX = (int) Utils.limit(startXX / 16, Map.w, 0);
        int startY = (int) Utils.limit(startYY / 16, Map.h, 0);
        int endX = (int) Utils.limit(endXX / 16, Map.w, 0);
        int endY = (int) Utils.limit(endYY / 16, Map.h, 0);

        targetX = endX;
        targetY = endY;

        path = new ArrayList<Point>();

        // nodes that have nodes on all sides
        ArrayList<Node> closedList = new ArrayList<Node>();

        // nodes that can have new nodes around
        ArrayList<Node> openList = new ArrayList<Node>();

        // create start
        Node start = new Node(startX, startY, null);
        start.g = 0;
        start.h = heuristic(startX, startY, targetX, targetY);
        start.f = start.g + start.h;
        openList.add(start);

        // create end
        Node goal = new Node(endX, endY, null);

        // go until solution is found, or there is nowhere left to search
        while (openList.size() > 0) {
            // find closest node
            int lowestF = 0;
            for (int i = 0; i < openList.size(); i++) {
                if (openList.get(i).f < openList.get(lowestF).f) {
                    lowestF = i;
                }
            }
            Node current = openList.get(lowestF);

            // if landed on goal, create and return path
            if (current.equals(goal)) {
                current.constructPath();
                return path;
            }

            // move current node from open to closed list
            closedList.add(openList.remove(lowestF));

            // look for new nodes around current one
            for (int i = 0; i < checkPositions.length; i++) {
                int checkX = current.x + checkPositions[i][0];
                int checkY = current.y + checkPositions[i][1];

                // if in bounds of map
                if (checkX > -1 && checkX < Map.w && checkY > -1 && checkY < Map.h) {
                    // if the position being checked is a floor 
                    if (Map.tiles[checkY][checkX].type == Tile.tileTypes.FLOOR) {
                        // create node
                        Node n = new Node(checkX, checkY, current);
                        // change heuristics if diagonal
                        if (i > 3) {
                            n.g += 0.414;
                            n.f += 0.414;
                        }

                        if (!arrayListHasNode(closedList, n)) {
                            if (!arrayListHasNode(openList, n)) {
                                openList.add(n);
                            } else {
                                // if the node is already in the open node, check if any nodes would be a parent that results in a shorter path
                                for (int j = 0; j < openList.size(); j++) {
                                    Node openNeighbor = openList.get(j);
                                    if (openNeighbor.x == checkX && openNeighbor.y == checkY) {
                                        if (n.g < openNeighbor.g) {
                                            openNeighbor.g = n.g;
                                            openNeighbor.parent = n.parent;
                                            // I know there should never be this many closing brackets
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return new ArrayList<Point>();
    }
}
