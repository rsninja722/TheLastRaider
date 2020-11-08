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

    static int targetX = 0;
    static int targetY = 0;
    public static ArrayList<Point> path = new ArrayList<Point>();

    static int[][] checkPositions = { { 0, -1 }, { -1, 0 }, { 1, 0 }, { 0, 1 }, { -1, -1 }, { 1, -1 }, { -1, 1 },
            { 1, 1 } };

    static float heuristic(int x, int y, int endX, int endY) {
        return (float) (Math.abs(endX - x) + Math.abs(endY - y));
    }

    static boolean arrayListHasNode(ArrayList<Node> arr, Node n) {
        for (int i = 0; i < arr.size(); i++) {
            if (arr.get(i).equals(n)) {
                return true;
            }
        }
        return false;
    }

    public static ArrayList<Point> findPath(int startXX, int startYY, int endXX, int endYY) {
        int startX = (int) Utils.limit(startXX / 16, Map.w, 0);
        int startY = (int) Utils.limit(startYY / 16, Map.h, 0);
        int endX = (int) Utils.limit(endXX / 16, Map.w, 0);
        int endY = (int) Utils.limit(endYY / 16, Map.h, 0);

        targetX = endX;
        targetY = endY;

        path = new ArrayList<Point>();

        ArrayList<Node> closedList = new ArrayList<Node>();
        ArrayList<Node> openList = new ArrayList<Node>();

        Node start = new Node(startX, startY, null);
        start.g = 0;
        start.h = heuristic(startX, startY, targetX, targetY);
        start.f = start.g + start.h;
        openList.add(start);

        Node goal = new Node(endX, endY, null);

        while(openList.size() > 0) {
            int lowestF = 0;
            for (int i = 0; i < openList.size(); i++) {
                if (openList.get(i).f < openList.get(lowestF).f) {
                    lowestF = i;
                }
            }

            Node current = openList.get(lowestF);

            if (current.equals(goal)) {
                current.constructPath();
                return path;
            }

            closedList.add(openList.remove(lowestF));

            for (int i = 0; i < checkPositions.length; i++) {
                int checkX = current.x + checkPositions[i][0];
                int checkY = current.y + checkPositions[i][1];
                if (checkX > -1 && checkX < Map.w && checkY > -1 && checkY < Map.h) {
                    if (Map.tiles[checkY][checkX].type == Tile.tileTypes.FLOOR) {
                        Node n = new Node(checkX, checkY, current);
                        if(i > 3) {
                            n.g += 0.414;
                        }
                        if (!arrayListHasNode(closedList, n)) {
                            if (!arrayListHasNode(openList, n)) {
                                openList.add(n);
                            } else {
                                for (int j = 0; j < openList.size(); j++) {
                                    Node openNeighbor = openList.get(j);
                                    if (openNeighbor.x == checkX && openNeighbor.y == checkY) {
                                        if (n.g < openNeighbor.g) {
                                            openNeighbor.g = n.g;
                                            openNeighbor.parent = n.parent;
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
