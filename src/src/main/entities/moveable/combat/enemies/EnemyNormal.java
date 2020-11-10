package main.entities.moveable.combat.enemies;

import engine.drawing.Draw;
import main.entities.Damage;
import main.entities.moveable.combat.Enemy;

public class EnemyNormal extends Enemy {

    public EnemyNormal(double x, double y, int w, int h) {
        super(x, y, w, h);

        range = 24;
        walkCountInterval = 15;
        speed = 1.0;
        forceTransfer = 0.6;
        setHP(20);
    }

    @Override
    public void attack() {
        if(attackTime == 0) {
            attackTime = 90;
        } else {
            attackTime--;
        }
        if (attackTime == 13 || attackTime == 8 || attackTime == 3) {
            double ang = angle + (-7 + attackTime) / 10.0;
            Damage.damages.add( new Damage(rect.x + Math.cos(ang) * 10.0, rect.y + Math.sin(ang) * 10.0, 4, 4, 5, true));
        }
    }

    @Override
    public void draw() {
        if(attackTime > 0 && attackTime < 50) {
            if(attackTime > 20) {
                Draw.image("normalWindup", (int)rect.x, (int)rect.y, angle, 1);
            } else if(attackTime > 10) {
                Draw.image("normalAttack0", (int)rect.x, (int)rect.y, angle - (20-attackTime)/10.0, 1);
            } else {
                Draw.image("normalAttack1", (int)rect.x, (int)rect.y, angle - (20-attackTime)/10.0, 1);
            }
        } else {
            if(moving) {
                Draw.image("normalWalk"+walkCycle, (int)rect.x, (int)rect.y, angle, 1);
            } else {
                Draw.image("normal", (int)rect.x, (int)rect.y, angle, 1);
            }
        }
        
        baseDraw();
    }
}
