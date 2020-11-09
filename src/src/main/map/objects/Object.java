package main.map.objects;

import main.entities.Entity;
import main.entities.moveable.combat.enemies.EnemyHeavy;
import main.entities.moveable.combat.enemies.EnemyLight;
import main.entities.moveable.combat.enemies.EnemyNormal;
import main.entities.moveable.map.Desk;

public class Object {
    enum objectTypes {
        ENTRANCE, SPAWNER, DOOR, DESK
    }

    objectTypes type;
    int rotation;
    int variation;
    String data;

    public Object(double x, double y, int type, int variation, int rotation, String data) {
        x+=8;
        y+=8;
        this.type = objectTypes.values()[type];
        this.rotation = rotation;
        this.variation = variation;
        this.data = data;

        switch(this.type) {
            case ENTRANCE:
                break;
            case SPAWNER:
                switch(data) {
                    case "0":
                        Entity.entities.add(new EnemyLight(x, y, 10, 10));
                        break;
                    case "1":
                        Entity.entities.add(new EnemyNormal(x, y, 14, 14));
                        break;
                    case "2":
                        Entity.entities.add(new EnemyHeavy(x, y, 20, 20));
                        break;
                }
                break;
            case DOOR:
                break;
            case DESK:
                Entity.entities.add(new Desk(x, y, 14, 14, variation, rotation));
                break;
        }
    }
}
