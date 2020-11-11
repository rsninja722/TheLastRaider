package main.ui;

import engine.Input;
import engine.Utils;
import engine.drawing.Draw;
import engine.physics.Physics;

import java.awt.Color;

public class Slider extends Component {

    Runnable callBack;

    public Slider(int w, int h, float x, float y, String text, Runnable callBack) {
        super(w, h, x, y);
        this.text = text;
        this.callBack = callBack;
    }

    @Override
    public void draw() {
        // slider bar
        Draw.setColor(new Color(76, 79, 79));
        Draw.rect((int) rect.x, (int) rect.y, rect.w, 4);

        // slider handel
        if (Physics.rectpoint(rect, Input.rawMousePos)) {
            if (Input.mouseDown(0)) {
                Draw.setColor(new Color(60, 60, 60));
            } else {
                Draw.setColor(new Color(110, 110, 110));
            }
        } else {
            Draw.setColor(new Color(80, 80, 80));
        }
        Draw.rect((int) Utils.mapRange(percent, 0, 1, rect.x - rect.w / 2, rect.x + rect.w / 2), (int) rect.y, 10, 10);

        // slider text
        Draw.setColor(new Color(230, 230, 230));
        Draw.setFontSize(1);
        Draw.text(this.text, (int) (rect.x - rect.w - this.text.length() * 8), (int) rect.y + 12);
    }

    @Override
    public void update() {
        // move handle when clicked
        if (Physics.rectpoint(rect, Input.rawMousePos)) {
            if (Input.mouseDown(0)) {
                percent = (float) Utils.mapRange(Input.rawMousePos.x - (rect.x - rect.w / 2), 0, rect.w, 0.0, 1.0);
                callBack.run();
            }
        }
    }
}
