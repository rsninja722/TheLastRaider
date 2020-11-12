package main.map.tiles;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import engine.drawing.Sprite;
import engine.drawing.Sprites;
import main.Constants;
import main.entities.Entity;

public class Tile extends Entity {

    public enum tileTypes {
        VOID, FLOOR, WALL
    }

    public tileTypes type;
    // number from 0-3
    public int rotation;
    // what image should be displayed
    public int variation;

    public Tile(double x, double y, int type, int variation, int rotation) {
        super(x, y, Constants.TILE_SCALE, Constants.TILE_SCALE);
        this.type = tileTypes.values()[type];
        this.variation = variation;
        this.rotation = rotation;
    }

    public void draw(Graphics2D g) {
        // draw rotated tile
        if (rotation != 0) {
            AffineTransform t = g.getTransform();

            g.translate((int) rect.x + Constants.TILE_SCALE / 2, (int) rect.y + Constants.TILE_SCALE / 2);
            g.rotate(rotation * Math.PI / 2);

            Sprite spr = Sprites.get(type.toString().toLowerCase() + variation);
            g.drawImage(spr.img, Math.round(-spr.width / 2), Math.round(-spr.height / 2), null);

            g.setTransform(t);
            // normal draw
        } else {
            g.drawImage(Sprites.get(type.toString().toLowerCase() + variation).img, (int) rect.x, (int) rect.y, null);
        }
    }
}
