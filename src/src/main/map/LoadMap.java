package main.map;

import java.io.FileReader;
import java.io.IOException;

import engine.drawing.Sprites;
import engine.physics.Point;
import main.Main;
import main.entities.Entity;
import main.entities.Wall;
import main.entities.moveable.map.Stairs;
import main.map.objects.ObjectParser;
import main.map.tiles.TileParser;
import java.awt.image.BufferedImage;

public class LoadMap {

    // loads a .txt level file (found in assets/levels, the .json files are used by the editor)
    public static void loadMap(String filePath) {
        try {
            // get file as string
            FileReader reader = new FileReader(filePath);

            StringBuilder str = new StringBuilder();

            int i;
            while ((i = reader.read()) != -1) {
                str.append((char) i);
            }

            reader.close();

            String mapData = str.toString();

            // get tiles
            String tileData = mapData.split(";")[0];
            // load all tiles
            Map.tiles = TileParser.loadTilesFromString(tileData);
            // create optimized collision boxes for walls, and add them as entities as well 
            Wall[] w = CollisionOptimizer.createCollisions(Map.tiles);
            for (int j = 0; j < w.length; j++) {
                Entity.entities.add(w[j]);
            }

            // load objects
            String objectData = mapData.split(";")[1];
            Map.objects = ObjectParser.loadObjectsFromString(objectData);

            // resolve and collisions
            for (int j = 0; j < Entity.entities.size(); j++) {
                if (Entity.entities.get(j).moveable) {
                    Entity.entities.get(j).handleCollision();
                }
            }

            // if it is the first time loading, create image and graphics context
            if (!Map.loaded[Main.level]) {
                Map.levelImgs[Main.level] = new BufferedImage(Map.w * 16, Map.h * 16, 2);
                Map.levelGraphics[Main.level] = Map.levelImgs[Main.level].createGraphics();
                Map.levelGraphics[Main.level].drawImage(Sprites.get(Main.level + "bottom").img, 0, 0, null);
            }

            // find where to position player
            Point spawn = Stairs.getSpawnPos();
            Main.player.rect.x = spawn.x;
            Main.player.rect.y = spawn.y;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
