package main.entities.moveable.combat.enemies;

import main.entities.moveable.combat.Enemy;

public class EnemyHeavy extends Enemy {

    public EnemyHeavy(double x, double y, int w, int h) {
        super(x, y, w, h);
        
        range = 25;
        walkCountInterval = 20;
        speed = 0.75;
        name = "heavy";
    }
    
}
