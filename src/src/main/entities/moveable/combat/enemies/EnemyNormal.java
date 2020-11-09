package main.entities.moveable.combat.enemies;

import main.entities.moveable.combat.Enemy;

public class EnemyNormal extends Enemy {

    public EnemyNormal(double x, double y, int w, int h) {
        super(x, y, w, h);

        range = 20;
        walkCountInterval = 15;
        speed = 1.0;
        forceTransfer = 0.6;
        name = "normal";
        setHP(20);
    }

}
