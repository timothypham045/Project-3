// Timothy Pham
// 09-16-25
// Programming Project 2 - Map Editor 

public class Tree {
    private int x;
    private int y;
    private int w;
    private int h;

    public Tree(int x, int y) {
        this.x = x;
        this.y = y;
        this.w = 64;  
        this.h = 64;
    }

    public boolean containsPoint(int px, int py) {
        return px >= x && px < x + w &&
               py >= y && py < y + h;
    }

    public int getX() { return x; }
    public int getY() { return y; }
    public int getW() { return w; }
    public int getH() { return h; }

    public void setX(int x) { this.x = x; }
    public void setY(int y) { this.y = y; }
    public void setW(int w) { this.w = w; }
    public void setH(int h) { this.h = h; }

    public Json marshal() {
        Json ob = Json.newObject();
        ob.add("x", x);
        ob.add("y", y);
        ob.add("w", w);
        ob.add("h", h);
        return ob;
    }

    public Tree(Json ob) {
    this.x = (int) ob.getLong("x");
    this.y = (int) ob.getLong("y");
    this.w = (int) ob.getLong("w");
    this.h = (int) ob.getLong("h");
}
}