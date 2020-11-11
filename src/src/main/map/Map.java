package main.map;

import main.entities.Wall;
import main.map.tiles.Tile;

import java.awt.image.BufferedImage;
import java.awt.Graphics2D;

public class Map {
    // map dimensions
    public static int w;
    public static int h;

    public static Tile[][] tiles;

    // anything part of the map that can't be a tile 
    public static Object[] objects;

    public static Wall[] walls;

    // images and graphics contexts for each level, so dead enemies can be drawn permanently
    public static BufferedImage[] levelImgs = new BufferedImage[4];
    public static Graphics2D[] levelGraphics = new Graphics2D[4];

    // if a level has been loaded before
    public static boolean[] loaded = { false, false, false, false };
}
