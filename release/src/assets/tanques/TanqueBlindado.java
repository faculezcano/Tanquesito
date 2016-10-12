package assets.tanques;

import javafx.scene.Group;
import javafx.scene.shape.Rectangle;
import assets.TanqueEnemigo;

/**
 * 
 */
public class TanqueBlindado extends TanqueEnemigo {

	public TanqueBlindado (){
		cuerpo = new Rectangle(0,0,64,64);
    	vel_mov = 1;
    	puntos = 400;
    	vel_disparo = 2;
    	resistencia = 4;	
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