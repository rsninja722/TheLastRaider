package main.entities.moveable.combat.enemies;

import engine.drawing.Draw;
import main.entities.Damage;
import main.entities.moveable.combat.Enemy;

public class EnemyHeavy extends Enemy {

    public EnemyHeavy(double x, double y, int w, int h) {
        super(x, y, w, h);

        range = 32;
        walkCountInterval = 20;
        speed = 0.5;
        forceTransfer = 0.25;
        setHP(40);
    }

    @Override
    public void attack() {
        if(attackTime == 0) {
            attackTime = 120;
        } else {
            attackTime--;
        }
        if (attackTime % 2 == 0 && attackTime < 30) {
            double ang = angle + (30 - attackTime) * 0.2;
            Damage.damages.add(new Damage(rect.x + Math.cos(ang) * 16.0, rect.y + Math.sin(ang) * 16.0, 4, 4, 2, true));
            Damage.damages.add(new Damage(rect.x + Math.cos(ang-Math.PI) * 16.0, rect.y + Math.sin(ang-Math.PI) * 16.0, 4, 4, 2, true));
        }
    }

    @Override
    public void draw() {
        if(attackTime > 0) {
            if(attackTime > 35) {
                Draw.image("heavyWindup", (int)rect.x, (int)rect.y, angle, 1);
            } else if (attackTime > 30) {
                Draw.image("heavy", (int)rect.x, (int)rect.y, angle, 1);
            } else {
                Draw.image("heavyAttack", (int)rect.x , (int)rect.y, angle + (30 - attackTime) * 0.2 , 1);
            }
        } else {
            if(moving) {
                Draw.image("heavyWalk"+walkCycle, (int)rect.x, (int)rect.y, angle, 1);
            } else {
                Draw.image("heavy", (int)rect.x, (int)rect.y, angle, 1);
            }
        }
        
        baseDraw();
    }

}
