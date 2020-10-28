package main.entities.moveable.map;

import main.entities.moveable.Moveable;

public class Desk extends Moveable  {

    int variation;
    int rotation;

    public Desk(double x, double y, int w, int h,int variation, int rotation) {
        super(x, y, w, h);
        this.variation = variation;
        this.rotation = rotation;

        switch(variation) {
            case 0:
                rect.resize(14, 12);
                break;
            case 1:
                rect.resize(20, 20);
                break;
            case 2:
                rect.resize(30, 20);
                break;
        }
    }
    
}
