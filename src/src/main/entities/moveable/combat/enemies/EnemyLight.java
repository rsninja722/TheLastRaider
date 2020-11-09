package main.entities.moveable.combat.enemies;

import main.entities.moveable.combat.Enemy;

public class EnemyLight extends Enemy {

    public EnemyLight(double x, double y, int w, int h) {
        super(x, y, w, h);

        range = 24;
        walkCountInterval = 5;
        speed = 2.0;
        forceTransfer = 0.8;
        name = "light";
        setHP(10);
    }

}
