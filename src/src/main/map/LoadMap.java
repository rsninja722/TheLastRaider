package main.map;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;

import engine.drawing.Sprites;
import engine.physics.Point;
import main.Main;
import main.entities.Entity;
import main.entities.Wall;
import main.entities.moveable.map.Stairs;
import main.map.objects.ObjectParser;
import main.map.tiles.TileParser;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;

public class LoadMap {

    public static void loadMap(String filePath) {
        try {
            FileReader reader = new FileReader(filePath);

            StringBuilder str = new StringBuilder();

            int i;
            while ((i = reader.read()) != -1) {
                str.append((char) i);
            }

            reader.close();

            String mapData = str.toString();

            String tileData = mapData.split(";")[0];
            Map.tiles = TileParser.loadTilesFromString(tileData);
            Wall[] w = CollisionOptimizer.createCollisions(Map.tiles);
            for(int j=0;j< w.length;j++) {
                Entity.entities.add(w[j]);
            }

            String objectData = mapData.split(";")[1];
            Map.objects = ObjectParser.loadObjectsFromString(objectData);

            RenderMap.render();

            for(int j=0;j<Entity.entities.size();j++) {
                if(Entity.entities.get(j).moveable) {
                    Entity.entities.get(j).handleCollision();
                }
            }

            if(!Map.loaded[Main.level]) { 
                Map.levelImgs[Main.level] = new BufferedImage(Map.w*16, Map.h*16, 2);
                Map.levelGraphics[Main.level] = Map.levelImgs[Main.level].createGraphics();
                Map.levelGraphics[Main.level].drawImage(Sprites.get(Main.level+"bottom").img, 0, 0, null);
            }

            Point spawn = Stairs.getSpawnPos();
            Main.player.rect.x = spawn.x;
            Main.player.rect.y = spawn.y;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
