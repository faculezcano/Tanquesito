package assets.powerUps;

import assets.PowerUp;
import assets.Tanque;
import common.Mapa;

/**
 * 
 */
public class PowUpHelm extends PowerUp {

	public PowUpHelm(double x, double y, Mapa m) {
		super(x, y, m);
		
	}

	@Override
	public void colisionaTanque(Tanque t) {
		if(t==map.getJugador()){
			map.Invulnerable();
		}
		
	}
	
}