package main.map.objects;

import main.Constants;

public class ObjectParser {
    static public Object[] loadObjectsFromString(String str) {
        String[] objects = str.split(",");

        Object[] returnObjects = new Object[objects.length];

        for (int i = 0; i < objects.length; i++) {
            String[] data = objects[i].split("~");

            if(data.length == 5) {
                returnObjects[i] = new Object(Integer.parseInt(data[0]) * Constants.TILE_SCALE,
                Integer.parseInt(data[1]) * Constants.TILE_SCALE, Integer.parseInt(data[2]),
                Integer.parseInt(data[3]), Integer.parseInt(data[4]), "");
            } else {
            returnObjects[i] = new Object(Integer.parseInt(data[0]) * Constants.TILE_SCALE,
                    Integer.parseInt(data[1]) * Constants.TILE_SCALE, Integer.parseInt(data[2]),
                    Integer.parseInt(data[3]), Integer.parseInt(data[4]), data[5]);
            }
        }

        return returnObjects;
    }

}
