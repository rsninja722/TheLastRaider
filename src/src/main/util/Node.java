package main.util;

import engine.physics.Point;

public class Node {
    public int x;
    public int y;
    double g;
    double h;
    public double f;
    Node parent;

    public Node(int xPos, int yPos, Node parentNode) {
        x = xPos;
        y = yPos;

        parent = parentNode;
        if (parent == null) {
            g = 0;
        } else {
            g = parent.g + 1;
        }

        h = AStar.heuristic(xPos, yPos, AStar.targetX, AStar.targetY);

        f = g + h;
    }

    public boolean equals(Node n) {
        return n.x == x && n.y == y;
    }

    public void constructPath() {
        if (parent != null) {
            AStar.path.add(0, new Point(x*16 + 8, y*16 + 8));
            parent.constructPath();
        }
    }

}