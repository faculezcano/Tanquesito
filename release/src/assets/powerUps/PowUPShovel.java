package assets.powerUps;

import assets.PowerUp;
import assets.Tanque;
import common.Mapa;

/**
 * 
 */
public class PowUPShovel extends PowerUp {

	public PowUPShovel(double x, double y, Mapa m) {
		super(x, y, m);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void colisionaTanque(Tanque t) {
		if(t==map.getJugador()){
			map.reforzarAguila();
		}
		
	}
	
}