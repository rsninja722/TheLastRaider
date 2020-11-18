package main.entities.projectiles;

public class Arrow extends Projectile {

    public Arrow(double x, double y, double angle) {
        super(x, y, 5, 5, angle, 5, 10);
        image = "arrow";
    }
    
}
