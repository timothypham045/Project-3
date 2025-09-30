// Timothy Pham
// 09-30-25
// Programming Project 3 - Collision()

import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

public class Controller implements KeyListener, MouseListener {
    
    private boolean keepGoing = true;
    private final Model model;
    private View view;
    private boolean editMode = false;  
    private boolean addMode  = true;
    private boolean keyLeft, keyRight, keyUp, keyDown;

    public Controller(Model m) {
        this.model = m;
    }

    public void setView(View v) { this.view = v; }

    
    public boolean isEditMode() { return editMode; }
    public boolean isAddMode()  { return addMode;  }
    public String  currentItemName() { return "Tree"; }

    
    public boolean isKeyLeft()  { return keyLeft;  }
    public boolean isKeyRight() { return keyRight; }
    public boolean isKeyUp()    { return keyUp;    }
    public boolean isKeyDown()  { return keyDown;  }

    
    public boolean update() {
        model.update(keyLeft, keyRight, keyUp, keyDown);
        return keepGoing;
    }

    // ---------- Mouse ----------
    @Override
    public void mousePressed(MouseEvent e) {
        if (!editMode || view == null) return;

        
        int worldX = e.getX() + view.getScrollX();
        int worldY = e.getY() + view.getScrollY();

        if (addMode) {
            model.addTreeAt(worldX, worldY);
        } else {
            model.removeTreeAtPoint(worldX, worldY);
        }
        e.getComponent().repaint();
    }

    @Override public void mouseReleased(MouseEvent e) { }
    @Override public void mouseEntered(MouseEvent e)  { }
    @Override public void mouseExited(MouseEvent e)   { }
    @Override
    public void mouseClicked(MouseEvent e) {
       
        if (e.getY() < 100) {
            System.out.println("break here");
        }
    }

    // -Keys
    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        char ch = Character.toLowerCase(e.getKeyChar());

        // Quit
        if (code == KeyEvent.VK_ESCAPE || ch == 'q') {
            System.exit(0);
        }

        // Edit-mode toggles
        if (ch == 'e') {                     
            editMode = !editMode;
            if (editMode) addMode = true;    // entering edit mode defaults to add
        } else if (ch == 'a' && editMode) {  // add trees
            addMode = true;
        } else if (ch == 'r' && editMode) {  // remove trees
            addMode = false;
        } else if (ch == 'c' && editMode) {  // clear trees
            model.clearTrees();
        }

        // Save / Load
        if (ch == 's') {
            try {
                Json root = model.marshal();
                root.save("map.json");
                System.out.println("Saved map.json (" + model.getTreeCount() + " tree(s))");
            } catch (Exception ex) {
                ex.printStackTrace(System.err);
            }
        } else if (ch == 'l') {
            model.loadFromFile("map.json");
            if (view != null) view.repaint();
        }

        
        if (code == KeyEvent.VK_LEFT)  keyLeft  = true;
        if (code == KeyEvent.VK_RIGHT) keyRight = true;
        if (code == KeyEvent.VK_UP)    keyUp    = true;
        if (code == KeyEvent.VK_DOWN)  keyDown  = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        
        if (code == KeyEvent.VK_LEFT)  keyLeft  = false;
        if (code == KeyEvent.VK_RIGHT) keyRight = false;
        if (code == KeyEvent.VK_UP)    keyUp    = false;
        if (code == KeyEvent.VK_DOWN)  keyDown  = false;
    }

    @Override public void keyTyped(KeyEvent e) { }
}
