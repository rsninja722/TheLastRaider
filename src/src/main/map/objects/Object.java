package main.map.objects;

import main.entities.Entity;
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
                break;
            case DOOR:
                break;
            case DESK:
                Entity.entities.add(new Desk(x, y, 14, 14, variation, rotation));
                break;
        }
    }
}
