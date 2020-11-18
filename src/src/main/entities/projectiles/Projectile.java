package main.entities.projectiles;

import engine.drawing.Draw;
import engine.physics.Physics;
import main.entities.Entity;

public class Projectile extends Entity {

    double angle;

    double velX;
    double velY;

    int damage;

    String image = "debug0";

    public Projectile(double x, double y, int w, int h, double angle, double speed, int damage) {
        super(x, y, w, h);
        this.angle = angle;
        velX = Math.cos(angle) * speed;
        velY = Math.sin(angle) * speed;
        this.damage = damage;
    }

    @Override
    public boolean update() {
        rect.x += velX;
        rect.y += velY;

        for(int i=0;i<Entity.entities.size();i++) {
            Entity e = Entity.entities.get(i);
            if(e.rect.x == rect.x && e.rect.y == rect.y) {
                continue;
            }

            if(Physics.rectrect(rect, e.rect)) {
                if(e.damageable) {
                    e.hp -= damage;
                }
                return true;
            }
        }

        return false;
    }

    @Override
    public void draw() {
        super.draw();
        Draw.image(image, (int)rect.x, (int)rect.y, angle, 1);
    }
    
}
