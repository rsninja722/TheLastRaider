package main.map;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.Constants;

public class RenderMap {
    public static void render() {
        if (Map.tiles != null) {
            Map.img = new BufferedImage(Map.tiles[0].length * Constants.TILE_SCALE,
                    Map.tiles.length * Constants.TILE_SCALE, 1);

            Graphics2D g = Map.img.createGraphics();

            for (int y = 0; y < Map.tiles.length; y++) {
                for (int x = 0; x < Map.tiles[0].length; x++) {
                    Map.tiles[y][x].draw(g);
                }
            }
        }
    }

    public static void saveImage() {
        try {
            File outputfile = new File("map.png");
            ImageIO.write(Map.img, "png", outputfile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
