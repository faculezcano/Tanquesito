package common;

import assets.ObjetoDinamico;
import javafx.scene.Group;
import javafx.scene.shape.Shape;

public class SyncAdder implements Runnable {
	Group g;
	Shape s;
	ObjetoDinamico o;
	
	public SyncAdder(Shape s, Group g){
		this.g = g;
		this.s = s;
	}
	
	public SyncAdder(ObjetoDinamico o, Group g){
		this.g = g;
		this.o = o;
	}

	@Override
	public void run() {
		if(g!=null && s!=null){
			g.getChildren().add(s);
		}else if(o!=null && g!=null){
			o.addToGroup(g);
		}
		
	}

}
