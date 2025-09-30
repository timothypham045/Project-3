// Timothy Pham
// 09-30-25
// Programming Project 3 - Collision ()

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import javax.swing.JPanel;

public class View extends JPanel {

    
    public static final int ROOM_W = 1280;
    public static final int ROOM_H = 720;
    public static final int ROOMS_X = 2;
    public static final int ROOMS_Y = 2;

    private int scrollPosX = 0;
    private int scrollPosY = 0;

    public int getScrollX() { return scrollPosX; }
    public int getScrollY() { return scrollPosY; }

    public void scrollLeft()  { scrollPosX = Math.max(0, scrollPosX - ROOM_W); }
    public void scrollRight() { scrollPosX = Math.min((ROOMS_X - 1) * ROOM_W, scrollPosX + ROOM_W); }
    public void scrollUp()    { scrollPosY = Math.max(0, scrollPosY - ROOM_H); }
    public void scrollDown()  { scrollPosY = Math.min((ROOMS_Y - 1) * ROOM_H, scrollPosY + ROOM_H); }

    
    private final Model model;
    private final Controller controller;

    
    private BufferedImage treeImage;

    
    private static final int FRAMES_PER_DIR = 11; //11 per direction
    private BufferedImage[] linkDown, linkUp, linkLeft, linkRight;

    
    private enum Facing { DOWN, UP, LEFT, RIGHT }
    private Facing facing = Facing.DOWN;

    
    private int linkFrame = 0;
    private int frameDelay = 0;
    private static final int FRAME_DELAY_MAX = 5; 

public void centerOn(int worldX, int worldY) {
    int viewW = ROOM_W;
    int viewH = ROOM_H;
    int worldW = ROOMS_X * ROOM_W;
    int worldH = ROOMS_Y * ROOM_H;
    scrollPosX = clamp(worldX - viewW / 2, 0, Math.max(0, worldW - viewW));
    scrollPosY = clamp(worldY - viewH / 2, 0, Math.max(0, worldH - viewH));
    }


private static int clamp(int v, int lo, int hi) {
    if (v < lo) return lo;
    if (v > hi) return hi;
    return v;
}


    public View(Model m, Controller c) {
        this.model = m;
        this.controller = c;

        setBackground(new Color(60, 120, 60));

        try {
            // Tree image
            treeImage = ImageIO.read(new File("images/tree.png"));

            // Link frames 
            linkDown  = new BufferedImage[FRAMES_PER_DIR];
            linkUp    = new BufferedImage[FRAMES_PER_DIR];
            linkLeft  = new BufferedImage[FRAMES_PER_DIR];
            linkRight = new BufferedImage[FRAMES_PER_DIR];

            for (int i = 0; i < FRAMES_PER_DIR; i++) {
                
                linkDown[i]  = ImageIO.read(new File("images/link"  + (1 + i) + ".png"));
                linkUp[i]    = ImageIO.read(new File("images/link" + (34 + i) + ".png"));
                linkLeft[i]  = ImageIO.read(new File("images/link" + (12 + i) + ".png"));
                linkRight[i] = ImageIO.read(new File("images/link" + (23 + i) + ".png"));
            }
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        
        if (treeImage != null) {
            for (int i = 0; i < model.getTreeCount(); i++) {
                Tree tree = model.getTree(i);
                g.drawImage(
                    treeImage,
                    tree.getX() - scrollPosX,
                    tree.getY() - scrollPosY,
                    tree.getW(), tree.getH(),
                    null
                );
            }
        }

        
        if (model.getLink() != null) {
            
            boolean moving = false;
            if (controller != null) {
                if (controller.isKeyLeft())  { moving = true; facing = Facing.LEFT;  }
                if (controller.isKeyRight()) { moving = true; facing = Facing.RIGHT; }
                if (controller.isKeyUp())    { moving = true; facing = Facing.UP;    }
                if (controller.isKeyDown())  { moving = true; facing = Facing.DOWN;  }
            }

            if (moving) {
                frameDelay++;
                if (frameDelay >= FRAME_DELAY_MAX) {
                    frameDelay = 0;
                    linkFrame = (linkFrame + 1) % FRAMES_PER_DIR;
                }
            } else {
                linkFrame = 0;  
                frameDelay = 0;
            }

            BufferedImage frame = currentLinkFrame();
            if (frame != null) {
                int drawX = model.getLink().getX() - scrollPosX;
                int drawY = model.getLink().getY() - scrollPosY;
                g.drawImage(frame, drawX, drawY, Link.W, Link.H, null);
            }
        }

        
        if (controller != null && controller.isEditMode()) {
            g.setColor(controller.isAddMode() ? new Color(0, 160, 0) : new Color(180, 0, 0));
            g.fillRect(0, 0, 100, 100);
            
            if (treeImage != null) g.drawImage(treeImage, 10, 10, 80, 80, null);
        }
    }

    private BufferedImage currentLinkFrame() {
        if (linkDown == null) return null;
        switch (facing) {
            case UP:    return linkUp[linkFrame];
            case LEFT:  return linkLeft[linkFrame];
            case RIGHT: return linkRight[linkFrame];
            case DOWN:
            default:    return linkDown[linkFrame];
        }
    }
}
