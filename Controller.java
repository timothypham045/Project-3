// Timothy Pham
// 09-30-25
// Programming Project 3 - Collision()

import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

public class Controller implements KeyListener, MouseListener
{
	// State
	private boolean keepGoing = true;
	//private View view;
	private Model model;
	private View view;

	private boolean editMode = false;
	private boolean addMode = true;

	public Controller(Model m)
	{
		this.model = m;

	}

	public void setView(View v) {
		this.view = v;
	}

	public boolean isEditMode() {return editMode;}
	public boolean isAddMode() {return addMode;}
	public String currentItemName() {return "Tree"; }

	public boolean update()
	{	
		return keepGoing;
	}

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



	public void mouseReleased(MouseEvent e) {    }
	public void mouseEntered(MouseEvent e) {    }
	public void mouseExited(MouseEvent e) {    }
	public void mouseClicked(MouseEvent e) { 
		if (e.getY() < 100) {
			System.out.println("break here");
		}

	   }


	public void keyPressed(KeyEvent e)
{
	// int code = e.getKeyCode();
	char ch = Character.toLowerCase(e.getKeyChar());

	if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            System.exit(0);
}

	char c = Character.toLowerCase(e.getKeyChar());
	if (c == 'q'){
		System.exit(0);
	}


	if (c == 'e'){
		editMode = !editMode;
		if (editMode) addMode = true;
	} else if (c == 'a') {
		if (editMode) addMode = true;
	} else if (c == 'r') {
		if (editMode) addMode = false;
	} else if (c == 'c') {
		if (editMode) model.clearTrees();
	  
	}

	/* 
	  if (view != null) {
        if (code == KeyEvent.VK_4 || code == KeyEvent.VK_NUMPAD4) view.scrollLeft();
        if (code == KeyEvent.VK_6 || code == KeyEvent.VK_NUMPAD6) view.scrollRight();
        if (code == KeyEvent.VK_8 || code == KeyEvent.VK_NUMPAD8) view.scrollUp();
        if (code == KeyEvent.VK_2 || code == KeyEvent.VK_NUMPAD2) view.scrollDown();
    }
*/
	//Save state
    if (ch == 's') {
        try {
            Json root = model.marshal();
            root.save("map.json");  
            System.out.println("Saved map.json (" + model.getTreeCount() + " tree(s))");
        } catch (Exception ex) {
            ex.printStackTrace(System.err);
        }
    }

	if (ch == 'l') {
    model.loadFromFile("map.json");
    if (view != null) view.repaint();
}
}

	public void keyReleased(KeyEvent e)
	{

}

public void keyTyped(KeyEvent e)
{    }
}



