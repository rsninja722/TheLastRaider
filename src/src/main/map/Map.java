package main.map;

import main.entities.Wall;
import main.map.tiles.Tile;

import java.awt.image.BufferedImage;
import java.awt.Graphics2D;


public class Map {
    public static int w;
    public static int h;

    public static Tile[][] tiles;
    public static Object[] objects;

    public static Wall[] walls;
    
    public static BufferedImage[] levelImgs = new BufferedImage[4];
    public static Graphics2D[] levelGraphics = new Graphics2D[4];

    public static boolean[] loaded = {false,false,false,false};
}
