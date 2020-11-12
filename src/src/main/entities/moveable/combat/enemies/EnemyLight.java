package main.entities.moveable.combat.enemies;

import engine.drawing.Draw;
import main.entities.Damage;
import main.entities.moveable.combat.Enemy;

public class EnemyLight extends Enemy {

    public EnemyLight(double x, double y, int w, int h) {
        super(x, y, w, h);

        range = 32;
        walkCountInterval = 5;
        speed = 2.0;
        forceTransfer = 0.8;
        setHP(10);
    }

    // jab attack
    @Override
    public void attack() {
        if (attackTime == 0) {
            attackTime = 70;
        } else {
            attackTime--;
        }
        if (attackTime == 7 || attackTime == 3) {
            double multi = 18 - attackTime;
            Damage.damages
                    .add(new Damage(rect.x + Math.cos(angle) * multi, rect.y + Math.sin(angle) * multi, 4, 4, 5, true));
        }
    }

    @Override
    public void draw() {
        if (attackTime > 0 && attackTime < 40) {
            if (attackTime > 10) {
                Draw.image("lightWindup", (int) rect.x, (int) rect.y, angle, 1);
            } else {
                Draw.image("lightAttack", (int) (rect.x + Math.cos(angle) * (10 - attackTime)),(int) (rect.y + Math.sin(angle) * (10 - attackTime)), angle, 1);
            }
        } else {
            if (moving) {
                Draw.image("lightWalk" + walkCycle, (int) rect.x, (int) rect.y, angle, 1);
            } else {
                Draw.image("light", (int) rect.x, (int) rect.y, angle, 1);
            }
        }

        baseDraw();
    }
}
