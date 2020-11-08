package main.map;

import main.entities.Wall;
import main.map.tiles.Tile;

import java.awt.image.BufferedImage;

public class Map {
    public static int w;
    public static int h;

    public static Tile[][] tiles;
    public static Object[] objects;

    public static Wall[] walls;
    
    public static BufferedImage img;
}
