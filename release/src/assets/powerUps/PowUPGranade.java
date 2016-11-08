package assets.powerUps;

import assets.PowerUp;
import assets.Tanque;
import assets.TanqueEnemigo;
import common.Mapa;

/**
 * 
 */
public class PowUPGranade extends PowerUp {

	public PowUPGranade(double x, double y, Mapa m){
		super(x,y,m);
	}
	
	
	@Override
	public void colisionaTanque(Tanque t) {
		if(t==map.getJugador()){
			for(TanqueEnemigo te: map.getEnemigos()){
				map.eliminarEnemigo(te);
			}
		}
		
	}
	
}