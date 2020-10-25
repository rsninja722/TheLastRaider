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
    public int rotation;
    public int variation;

    public Tile(double x, double y, int type, int variation, int rotation) {
        super(x, y, Constants.TILE_SCALE, Constants.TILE_SCALE);
        this.type = tileTypes.values()[type];
        this.variation = variation;
        this.rotation = rotation;
    }

    public void draw(Graphics2D g) {
        if(this.rotation != 0 ) {
            AffineTransform t = g.getTransform();

            g.translate((int)this.rect.x + Constants.TILE_SCALE/2, (int)this.rect.y + Constants.TILE_SCALE/2);
            g.rotate(this.rotation * Math.PI / 2);

            Sprite spr = Sprites.get(this.type.toString().toLowerCase() + this.variation);
            g.drawImage(spr.img, Math.round(-spr.width/2), Math.round(-spr.height/2), null);

            g.setTransform(t);
        } else {
            
            g.drawImage(Sprites.get(this.type.toString().toLowerCase() + this.variation).img, (int)this.rect.x, (int)this.rect.y, null);
        }
    }
}
