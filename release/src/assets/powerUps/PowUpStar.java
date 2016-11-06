package assets.powerUps;

import assets.PowerUp;
import assets.Tanque;

/**
 * 
 */
public class PowUpStar extends PowerUp {

	@Override
	public void colisionaTanque(Tanque t) {
		if(t == map.getJugador()){
			map.getJugador().subirNivel();
		}
	}
	
}