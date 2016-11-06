package assets.tanques;

import javafx.scene.Group;
import javafx.scene.shape.Rectangle;
import assets.TanqueEnemigo;
import common.Mapa;

/**
 * 
 */
public class TanqueRapido extends TanqueEnemigo {
	
	public TanqueRapido(Mapa m) {
    	super(m);
		cuerpo = new Rectangle(0,0,64,64);
    	vel_mov = 3;
    	puntos = 200;
    	vel_disparo = 2;
    	resistencia = 1;
	}

	@Override
	public void colisiona() {
		// TODO Auto-generated method stub
		
	}
}