// Timothy Pham
// 09-30-25
// Programming Project 3: Collision()

import javax.swing.JFrame;
import java.awt.Toolkit;

public class Game extends JFrame {
	
	private Model model;
	private View view;
	private Controller controller;
	private boolean keepGoing;

	
	public Game()
	{
		model = new Model();
		controller = new Controller(model);
		view = new View(model, controller);
		controller.setView(view);
		this.setTitle("A3 - Collision Detection and Fixing");
		this.setSize(1280, 720);
		this.setFocusable(true);
		this.getContentPane().add(view);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		keepGoing = true;

		this.setFocusable(true);
		this.requestFocusInWindow();

		this.addKeyListener(controller);
		view.addMouseListener(controller);
		view.addKeyListener(controller);   
		view.setFocusTraversalKeysEnabled(false);       

		
	}

	// Game Loop
	public void run()
{
	do
	{
		keepGoing = controller.update();
		model.update(
        controller.isKeyLeft(),
        controller.isKeyRight(),
        controller.isKeyUp(),
        controller.isKeyDown()
    );
		view.centerOn(model.getLink().getX(), model.getLink().getY());
		view.repaint(); 
		Toolkit.getDefaultToolkit().sync(); 

		
		try
		{
			Thread.sleep(16); //edited from 50 to 16 for Link sprite faster
		} catch(Exception e) {
			e.printStackTrace();
			System.exit(1);
		}

	}
	while(keepGoing);
}

	public static void main(String[] args)
	{
		Game g = new Game();
		g.run();
	}
}
