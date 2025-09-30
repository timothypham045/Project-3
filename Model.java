// Timothy Pham
// 09-16-25
// Programming Project 2 - Map Editor

import java.util.ArrayList;

public class Model {

	public static final int TILE_W = 75;
	public static final int TILE_H = 75;

    private final ArrayList<Tree> trees;

    public Model() {
        trees = new ArrayList<Tree>();

        try {

            Json ob = Json.load("map.json");
            unmarshal(ob);
        } catch (Exception e) {
            System.out.println("No map.json found.");
        }
        }
    


    public void update() {
        // empty
    }

    public void clearTrees() {
        trees.clear();
    }

    public int getTreeCount() {
        return trees.size();
    }

    public Tree getTree(int i) {
        return trees.get(i);
    }

	public void addTreeAt(int mouseX, int mouseY) {
		int x = Math.floorDiv(mouseX, TILE_W) * TILE_W;
		int y = Math.floorDiv(mouseY, TILE_H) * TILE_H;

		if (hasTreeAt(x,y)) return;

		Tree t = new Tree(x, y);
        t.setW(TILE_W);
        t.setH(TILE_H);
        trees.add(t);
	}

	private boolean hasTreeAt (int x, int y) {
		for (Tree tr : trees) {
			if (tr.getX() == x && tr.getY() == y) return true;
		}
		return false;
	}

    public void removeTreeAt(int mouseX, int mouseY) {
        int x = Math.floorDiv(mouseX, TILE_W) * TILE_W;
        int y = Math.floorDiv(mouseY, TILE_H) * TILE_H;
        for (int i = 0; i < trees.size(); i++) {
            Tree tr = trees.get(i); 
            if (tr.getX() == x && tr.getY() == y) {
                trees.remove(i);
                return;
            }           
        }

    }

    public boolean removeTreeAtPoint(int mouseX, int mouseY) {
    for (int i = trees.size() - 1; i >= 0; i--) {
        Tree t = trees.get(i);
        if (t.containsPoint(mouseX, mouseY)) {
            trees.remove(i);
            return true;
        }
    }
    return false;
}

    public Json marshal() {
    Json root = Json.newObject();

    root.add("tileW", TILE_W);
    root.add("tileH", TILE_H);

    Json arr = Json.newList();
    for (int i = 0; i < trees.size(); i++) {
        arr.add(trees.get(i).marshal());
    }
    root.add("trees", arr);

    return root;
}


public void loadFromFile(String filename) {
    try {
        Json root = Json.load(filename); 
        unmarshal(root);
    } catch (Exception ex) {
        ex.printStackTrace(System.err);
    }
}

public void unmarshal(Json root) {
    trees.clear();
    Json arr = root.get("trees");
    for (int i = 0; i < arr.size(); i++) {
        trees.add(new Tree(arr.get(i)));  
    }
}

}