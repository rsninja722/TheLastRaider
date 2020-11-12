package main.map.tiles;

import main.Constants;
import main.map.Map;

public class TileParser {

    static public Tile[][] loadTilesFromString(String str) {
        String[] tiles = str.split(",");

        // first to numbers in tile list are width and height
        int w = Integer.parseInt(tiles[0]);
        int h = Integer.parseInt(tiles[1]);
        Map.w = w;
        Map.h = h;

        Tile[][] tileMatrix = new Tile[h][w];

        int x = 0;
        int y = 0;

        // create tile depending on how much information there is
        for (int i = 2; i < tiles.length; i++) {
            // double slash is need because . doesn't work well with regex
            String[] tileData = tiles[i].split("\\.");

            switch (tileData.length) {
                case 1:
                    tileMatrix[y][x] = new Tile(x * Constants.TILE_SCALE, y * Constants.TILE_SCALE, Integer.parseInt(tiles[i]), 0, 0);
                    break;
                case 2:
                    tileMatrix[y][x] = new Tile(x * Constants.TILE_SCALE, y * Constants.TILE_SCALE, Integer.parseInt(tileData[0]), Integer.parseInt(tileData[1]), 0);
                    break;
                case 3:
                    tileMatrix[y][x] = new Tile(x * Constants.TILE_SCALE, y * Constants.TILE_SCALE, Integer.parseInt(tileData[0]), Integer.parseInt(tileData[1]), Integer.parseInt(tileData[2]));
                    break;
            }

            // track column and row
            if (++x == w) {
                x = 0;
                y++;
            }
        }

        return tileMatrix;
    }

}
