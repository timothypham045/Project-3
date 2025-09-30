// Timothy Pham
// 09-16-25
// Programming Project 2 - Map Editor 

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

    private BufferedImage treeImage;
    private Model model;
    private Controller controller;

    public View(Model m, Controller c) {
        this.model = m;
        this.controller = c;

    setBackground(new Color(60, 120, 60));

        try {
            treeImage = ImageIO.read(new File("images/tree.png"));
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
                g.drawImage(treeImage, tree.getX() - scrollPosX, tree.getY() - scrollPosY, tree.getW(), tree.getH(), null);
            }
        }

        
        if (controller != null && controller.isEditMode()) {
            if (controller.isAddMode()) {
                g.setColor(new Color(0, 160, 0)); 
            } else {
                g.setColor(new Color(180, 0, 0)); 
            }
            g.fillRect(0, 0, 100, 100);

            if (treeImage != null) {
                g.drawImage(treeImage, 10, 10, 80, 80, null);
            }

        }
    }
}
