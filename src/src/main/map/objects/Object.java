package main.map.objects;

import main.Main;
import main.entities.Entity;
import main.entities.moveable.combat.enemies.EnemyHeavy;
import main.entities.moveable.combat.enemies.EnemyLight;
import main.entities.moveable.combat.enemies.EnemyNormal;
import main.entities.moveable.map.Desk;
import main.entities.moveable.map.Door;
import main.entities.moveable.map.Stairs;
import main.map.Map;

public class Object {
    enum objectTypes {
        ENTRANCE, SPAWNER, DOOR, DESK, BLACKOUT
    }

    objectTypes type;
    int rotation;
    int variation;
    String data;

    public Object(double x, double y, int type, int variation, int rotation, String data) {
        // center object on tile
        x += 8;
        y += 8;
        this.type = objectTypes.values()[type];
        this.rotation = rotation;
        this.variation = variation;
        // any extra information needed for a specific object
        this.data = data;

        switch (this.type) {
            case ENTRANCE:
                String[] d = data.split("@");
                Stairs.stairsList.add(new Stairs(x, y, Integer.parseInt(d[2]), Integer.parseInt(d[3]), Integer.parseInt(d[1]), Integer.parseInt(d[0])));
                break;
            case SPAWNER:
                if (!Map.loaded[Main.level]) {
                    switch (data) {
                        case "0":
                            Entity.entities.add(new EnemyLight(x, y, 14, 14));
                            break;
                        case "1":
                            Entity.entities.add(new EnemyNormal(x, y, 15, 15));
                            break;
                        case "2":
                            Entity.entities.add(new EnemyHeavy(x, y, 20, 20));
                            break;
                    }
                }
                break;
            case DOOR:
                Door.doorList.add(new Door(x, y, 14, 14, variation == 0 ? true : false, rotation));
                break;
            case DESK:
                if (!Map.loaded[Main.level]) {
                    Entity.entities.add(new Desk(x, y, 14, 14, variation, rotation));
                }
                break;
            case BLACKOUT:
                break;
        }
    }
}
