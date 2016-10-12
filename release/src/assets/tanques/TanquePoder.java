package assets.tanques;

import javafx.scene.Group;
import javafx.scene.shape.Rectangle;
import assets.TanqueEnemigo;

/**
 * 
 */
public class TanquePoder extends TanqueEnemigo {
	
	public TanquePoder () {
		cuerpo = new Rectangle(0,0,64,64);
    	vel_mov = 2;
    	puntos = 300;
    	vel_disparo = 3;
    	resistencia = 1;
	}

	@Override
	public void colisiona() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addToGroup(Group g) {
		// TODO Auto-generated method stub
		
	}	
}