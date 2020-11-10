package main.ui;

import engine.Input;
import engine.drawing.Draw;
import engine.physics.Physics;

import java.awt.Color;

public class Button extends Component {

    int size;
    Runnable callBack;

    public Button(int w, int h, float x, float y, String text, int size, Runnable callBack) {
        super(w, h, x, y);
        this.text = text;
        this.size = size;
        this.callBack = callBack;
    }

    @Override
    public void draw() {
        if(Physics.rectpoint(rect, Input.rawMousePos)) {
            if(Input.mouseDown(0)) {
                Draw.setColor(new Color(22, 26, 26));
            } else {
                Draw.setColor(new Color(65, 71, 71));
            }
        } else {
            Draw.setColor(new Color(43, 48, 48));
        }
        Draw.rect(rect);

        Draw.setColor(new Color(230,230,230));
        Draw.setFontSize(size);
        Draw.text(this.text, (int) (10 + rect.x - rect.w / 2), (int) rect.y + 12);

        Draw.setColor(new Color(76, 79, 79));
        Draw.rectOutline(rect);
    }

    @Override
    public void update() {
        if(Physics.rectpoint(rect, Input.rawMousePos)) {
            if(Input.mouseClick(0)) {
                this.callBack.run();
            }
        }
    }
}
