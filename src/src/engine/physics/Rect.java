package engine.physics;

/** rectangle object used for physics and drawing */
public class Rect {
	public double x;
	public double y;
	public int w;
	public int h;
    
	public double halfW;
	public double halfH;

    /**
     * Creates a new rectangle object centered on x,y that can be used in physics and drawing
     * @param x center x
     * @param y center y
     * @param w width
     * @param h height
     */
	public Rect(double x, double y, int w, int h) {
		this.x = x;
		this.y = y;
		this.w = w;
        this.h = h;
        
        // calculate half width/height once
		this.halfW = w / 2.0;
		this.halfH = h / 2.0;
	}

    /**
     * changes width and height, and recalculates the half width and half hight
     * @param w width
     * @param h height
     */     
	public void resize(int w, int h) {
		this.w = w;
		this.h = h;
		this.halfW = w / 2;
		this.halfH = h / 2;
    }
    
    public static void preventIntersection(Rect move, Rect stay) {
        int difLeft = (int)Math.abs((move.x+move.halfW) - (stay.x-stay.halfW));
        int difRight = (int)Math.abs((move.x-move.halfW) - (stay.x+stay.halfW));
        int difTop = (int)Math.abs((move.y+move.halfH) - (stay.y-stay.halfH));
        int difBottom = (int)Math.abs((move.y-move.halfH) - (stay.y+stay.halfH));

        int smallest = Math.min(difLeft, Math.min(difRight, Math.min(difBottom, difTop)));

        if(smallest == difLeft) {
            move.x = stay.x-stay.halfW-move.halfW-0.1;
        }
        if(smallest == difRight) {
            move.x = stay.x+stay.halfW+move.halfW+0.1;
        }
        if(smallest == difTop) {
            move.y = stay.y-stay.halfH-move.halfH-0.1;
        }
        if(smallest == difBottom) {
            move.y = stay.y+stay.halfH+move.halfH+0.1;
        }
    }
}
