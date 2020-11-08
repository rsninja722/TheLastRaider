package main.entities.moveable.combat.enemies;

import main.entities.moveable.combat.Enemy;

public class EnemyLight extends Enemy {

    public EnemyLight(double x, double y, int w, int h) {
        super(x, y, w, h);
        
        range = 20;
        walkCountInterval = 10;
        speed = 1.5;
        name = "light";
    }
    
}
