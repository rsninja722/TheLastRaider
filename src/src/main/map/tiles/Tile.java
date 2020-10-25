package main.map.tiles;

import java.awt.Graphics;

import engine.drawing.Sprites;
import main.Constants;
import main.entities.Entity;

public class Tile extends Entity {

    enum tileTypes {
        VOID, FLOOR, WALL
    }

    tileTypes type;
    int rotation;
    int variation;

    public Tile(double x, double y, int type, int rotation, int variation) {
        super(x, y, Constants.TILE_SCALE, Constants.TILE_SCALE);
        this.type = tileTypes.values()[type];
        this.rotation = rotation;
        this.variation = variation;
    }

    public void draw(Graphics g) {
        g.drawImage(Sprites.get(this.type.toString().toLowerCase() + this.variation).img, (int)this.rect.x, (int)this.rect.y, null);
    }
}
