package main.map.tiles;

import main.Constants;

public class TileParser {

    static public Tile[][] loadTilesFromString(String str) {
        String[] tiles = str.split(",");

        int w = Integer.parseInt(tiles[0]);
        int h = Integer.parseInt(tiles[1]);

        Tile[][] tileMatrix = new Tile[h][w];

        int x = 0;
        int y = 0;

        for (int i = 0; i < tiles.length; i++) {
            String[] tileData = tiles[i].split(".");

            switch (tileData.length) {
                case 1:
                    tileMatrix[y][x] = new Tile(x*Constants.TILE_SCALE,y*Constants.TILE_SCALE,Integer.parseInt(tileData[0]), 0, 0);
                    break;
                case 2:
                    tileMatrix[y][x] = new Tile(x*Constants.TILE_SCALE,y*Constants.TILE_SCALE,Integer.parseInt(tileData[0]), Integer.parseInt(tileData[1]), 0);
                    break;
                case 3:
                    tileMatrix[y][x] = new Tile(x*Constants.TILE_SCALE,y*Constants.TILE_SCALE,Integer.parseInt(tileData[0]), Integer.parseInt(tileData[1]),
                            Integer.parseInt(tileData[2]));
                    break;
            }

            if (++x == w) {
                x = 0;
                y++;
            }
        }

        return tileMatrix;
    }

}
