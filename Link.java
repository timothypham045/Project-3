// Timothy Pham
// 09-30-25
// Programming Project 3 - Collision()

public class Link {
    private int x, y;
    private int px, py;
    private double speed = 4.0;

    //animation
    public enum Dir { DOWN, UP, LEFT, RIGHT }
    private Dir facing = Dir.DOWN;
    private boolean moving = false;
    private int frame = 0;
    private int frameTick = 0;

    //size
    public static final int W = 48;
    public static final int H = 48;

    public Link(int startX, int startY) {
        this.x = startX;
        this.y = startY;
        this.px = x;
        this.py = y;
    }

    public void beginStep() {
        px = x;
        py = y;
    }

    public void update(boolean left, boolean right, boolean up, boolean down) {
        int dx = 0, dy = 0;
        if (left)  { dx -= 1; facing = Dir.LEFT; }
        if (right) { dx += 1; facing = Dir.RIGHT; }
        if (up)    { dy -= 1; facing = Dir.UP; }
        if (down)  { dy += 1; facing = Dir.DOWN; }

        moving = (dx != 0 || dy != 0);

        if (dx != 0) x += (int)Math.round(speed * Math.signum(dx));
        if (dy != 0) y += (int)Math.round(speed * Math.signum(dy));

        for (Tree t : trees) {
            if (overlaps(x, y, W, H, t.getx(), t.getY(), t.getW(), t.getH())) {
                if (px + W <= t.getX()) {
                    x = t.getX() - W;
                } else if (px >= t.getX() + t.getW()) {
                    x = t.getX() + t.getW();
                } else if (py + H <= t.getY()) {
                    y = t.getY() - H;
                } else if (py >= t.getY() + t.getH()) {
                    y = t.getY() + t.getH();
                }
            }



        }
        if (moving) {
            frameTick++;
            if (frameTick % 3 == 0) {
                frame = (frame + 1) % 11;
            }
        } else {
            frame = 0;
            frameTick = 0;
        }
    }

    public void resolveCollision(Tree t) {
        int tx = t.getX(), ty = t.getY(), tw = t.getW(), th = t.getH();
        boolean cameFromLeft  = px + W <= tx;
        boolean cameFromRight = px >= tx + tw;
        boolean cameFromAbove = py + H <= ty;
        boolean cameFromBelow = py >= ty + th;

        if (cameFromLeft && !cameFromAbove && !cameFromBelow) {
            x = tx - W;
        } else if (cameFromRight && !cameFromAbove && !cameFromBelow) {
            x = tx + tw;
        } else if (cameFromAbove && !cameFromLeft && !cameFromRight) {
            y = ty - H;
        } else if (cameFromBelow && !cameFromLeft && !cameFromRight) {
            y = ty + th;
        } else {
            int dxLeft  = Math.abs((tx - (x + W)));
            int dxRight = Math.abs(((tx + tw) - x));
            int dyUp    = Math.abs((ty - (y + H)));
            int dyDown  = Math.abs(((ty + th) - y));
            int min = Math.min(Math.min(dxLeft, dxRight), Math.min(dyUp, dyDown));
            if (min == dxLeft)      x = tx - W;
            else if (min == dxRight) x = tx + tw;
            else if (min == dyUp)    y = ty - H;
            else                     y = ty + th;
        }
    }

    public int left()   { return x; }
    public int right()  { return x + W; }
    public int top()    { return y; }
    public int bottom() { return y + H; }

    public boolean overlaps(Tree t) {
        int ax1 = left(),  ay1 = top(),    ax2 = right(),  ay2 = bottom();
        int bx1 = t.getX(), by1 = t.getY(), bx2 = t.getX() + t.getW(), by2 = t.getY() + t.getH();
        if (ax2 <= bx1) return false;
        if (ax1 >= bx2) return false;
        if (ay2 <= by1) return false;
        if (ay1 >= by2) return false;
        return true;
    }

    public int getX() { return x; }
    public int getY() { return y; }
    public int getPX() { return px; }
    public int getPY() { return py; }
    public Dir getFacing() { return facing; }
    public int getFrame() { return frame; }
    public boolean isMoving() { return moving; }

    public void setPos(int nx, int ny) { x = nx; y = ny; }
    public double getSpeed() { return speed; }
    public void setSpeed(double s) { speed = s; }

    @Override
    public String toString() {
        return "Link(x=" + x + ", y=" + y +
               ", px=" + px + ", py=" + py +
               ", facing=" + facing +
               ", frame=" + frame +
               ", moving=" + moving +
               ", speed=" + speed + ")";
    }
}
