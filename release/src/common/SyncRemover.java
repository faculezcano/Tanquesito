package common;

import javafx.scene.Group;
import javafx.scene.shape.Shape;

public class SyncRemover implements Runnable {
	Group g;
	Shape s;
	public SyncRemover(Shape s, Group g){
		this.g = g;
		this.s = s;
	}

	@Override
	public void run() {
		if(g!=null && s!=null)
			g.getChildren().remove(s);
	}

}
