package common;

import assets.ObjetoDinamico;
import javafx.scene.Group;
import javafx.scene.Node;

public class SyncRemover implements Runnable {
	Group g;
	Node s;
	ObjetoDinamico o;
	
	public SyncRemover(Node s, Group g){
		this.g = g;
		this.s = s;
	}
	
	public SyncRemover(ObjetoDinamico o, Group g){
		this.g = g;
		this.o = o;
	}

	@Override
	public void run() {
		if(g!=null && s!=null){
			if (!g.getChildren().isEmpty())
				g.getChildren().remove(s);
		}else if(o!=null && g!=null){
			o.removeFromGroup(g);
		}
		
	}

}
