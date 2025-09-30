// Timothy Pham
// 09-30-25
// Assignment 3 - Collision()

public class Link {
    private int x, y;
    private int px, py;
    private double speed = 4.0;

    // animation
    public enum Dir { DOWN, UP, LEFT, RIGHT }
    private Dir facing = Dir.DOWN;
    private boolean moving = false;
    private int frame = 0;            
    private int frameTick = 0;        

    // size 
    public static final int W = 48;
    public static final int H = 48;

    public Link(int startX, int startY) {
        this.x = startX;
        this.y = startY;
    }

    
    public void update(boolean left, boolean right, boolean up, boolean down) {
        
        px = x; py = y;

        int dx = 0, dy = 0;
        if (left)  { dx -= 1; facing = Dir.LEFT; }
        if (right) { dx += 1; facing = Dir.RIGHT; }
        if (up)    { dy -= 1; facing = Dir.UP; }
        if (down)  { dy += 1; facing = Dir.DOWN; }

        moving = (dx != 0 || dy != 0);

        // move (no collisions yet)
        if (dx != 0) x += (int)Math.round(speed * Math.signum(dx));
        if (dy != 0) y += (int)Math.round(speed * Math.signum(dy));

        // update animation only while moving
        if (moving) {
            frameTick++;
            if (frameTick % 6 == 0) {           // advance roughly every 6 ticks
                frame = (frame + 1) % 5;        // 5 frames: 0..4
            }
        } else {
            frame = 0; // stand still
            frameTick = 0;
        }
    }

    // getters
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
        return "Link(x=" + x + ", y=" + y + ", facing=" + facing + ", frame=" + frame + ")";
    }
}
