package main.map.objects;

import main.entities.Entity;

public class Object extends Entity {
    enum objectTypes {
        ENTRANCE, SPAWNER, DOOR, DESK
    }

    objectTypes type;
    int rotation;
    int variation;
    String data;

    public Object(double x, double y, int type, int rotation, int variation, String data) {
        super(x, y, 0, 0);
        this.type = objectTypes.values()[type];
        this.rotation = rotation;
        this.variation = variation;
        this.data = data;
    }
}
