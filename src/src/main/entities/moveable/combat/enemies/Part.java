package main.entities.moveable.combat.enemies;

import engine.Utils;
import engine.drawing.Draw;
import engine.drawing.Sprites;
import engine.physics.Rect;
import main.Main;
import main.entities.Entity;
import main.entities.moveable.Moveable;
import main.map.Map;

public class Part extends Moveable {

    double angle; 
    double velocity;
    int part;

    public Part(double x, double y) {
        super(x, y, 8, 8);
        angle = Utils.rand(0, 360) / (Math.PI*2.0);
        velocity = Utils.rand(20, 60) / 10.0;
        part = Utils.rand(0, 5);
        ignoreCollision = true;
        forceTransfer = 1.0;
    }

    public static void addParts(double x, double y, int amount) {
        for(int i=0;i<amount;i++) {
            Entity.entities.add(new Part(x,y));
        }
    }

    @Override
    public boolean update() {

        rect.x += Math.cos(angle) * velocity;
        rect.y += Math.sin(angle) * velocity;

        for(int i = 0;i<Entity.entities.size();i++) {
            Entity e = Entity.entities.get(i);

            if(!e.ignoreCollision) {
                Rect.preventIntersection(rect, e.rect);
            }
        }

        velocity = Utils.friction(velocity, 0.1);

        if(velocity == 0) {
            // Map.levelGraphics[Main.level].drawImage(Sprites.get("part"+part).img, (int)rect.x, (int)rect.y, null);
            // return true;
        }

        return false;
    }

    @Override
    public void draw() {
        Draw.image("part"+part, (int)rect.x, (int)rect.y, angle, 1);
    }
    
}
