package main.map;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;

import main.map.objects.ObjectParser;
import main.map.tiles.TileParser;

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

            System.out.println(mapData);

            // String tileData = mapData.split(";")[0];
            // Map.tiles = TileParser.loadTilesFromString(tileData);

            // String objectData = mapData.split(";")[1];
            // Map.objects = ObjectParser.loadObjectsFromString(objectData);

            // RenderMap.render();
            // RenderMap.saveImage();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}