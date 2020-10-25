package main.map;

import java.util.ArrayList;

import main.Constants;
import main.entities.map.Wall;
import main.map.tiles.Tile;
import main.map.tiles.Tile.tileTypes;

public class CollisionOptimizer {
    public static Wall[] createCollisions(Tile[][] tiles) {
        ArrayList<Wall> wallsList = new ArrayList<Wall>();

        int h = tiles.length-1;
        int w = tiles[0].length-1;


        // array of booleans for if there is a wall tile
        boolean[][] wallTiles = new boolean[h][w];
        
        // array of booleans for if a tile has gotten a collision made for it
        boolean[][] hasCollision = new boolean[h][w];
    
        // fill arrays
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                wallTiles[y][x] = tiles[y][x].type == tileTypes.WALL;
                hasCollision[y][x] = false;
            }
        }
    
        // try to make large rectangles that cover multiple walls to make collision more efficient
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                if (!hasCollision[y][x] && wallTiles[y][x]) {
                    // find right limit
                    int xPos = x;
                    while (xPos < w && wallTiles[y][xPos]) {
                        xPos++;
                    }
                    xPos--;
    
                    // find bottom limit
                    int yPos = y;
                    boolean fullRow = true;
                    // go down row by row
                    while (yPos < h && wallTiles[yPos][xPos] && fullRow) {
                        yPos++;
                        // go through the whole row, make sure it is full
                        int rowX = xPos;
                        while (rowX > -1 && wallTiles[yPos][rowX]) {
                            rowX--;
                        }
                        // if the row is not full, stop
                        if (rowX + 1 != x) {
                            fullRow = false;
                            yPos--;
                        }
                    }
    
                    // track what tiles have gotten collision
                    for (int y2 = y; y2 < yPos + 1; y2++) {
                        for (int x2 = x; x2 < xPos + 1; x2++) {
                            hasCollision[y2][x2] = true;
                        }
                    }
    
                    // find collider dimensions
                    double colX = (x + xPos + 1) / 2.0 * (double)Constants.TILE_SCALE;
                    double colY = (y + yPos + 1) / 2.0 * (double)Constants.TILE_SCALE;
                    int colW = (xPos - x + 1 )* Constants.TILE_SCALE;
                    int colH = (yPos - y + 1) * Constants.TILE_SCALE;
    
                    // add collider
                    wallsList.add(new Wall(colX, colY, colW, colH));
                }
            }
        }

        Wall[] walls = new Wall[wallsList.size()];
        walls = wallsList.toArray(walls);
        return walls;
    }
}
