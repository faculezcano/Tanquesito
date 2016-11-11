package assets.powerUps;


import assets.PowerUp;
import assets.Tanque;
import common.Mapa;

/**
 * 
 */
public class PowUpLife extends PowerUp {

	protected PowUpLife(double x, double y, Mapa m) {
		super(x, y, m);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void colisionaTanque(Tanque t) {
		if(map.getJugador() == t){
			int nuevaVida = map.getJugador().getVidas()+1;
			map.getJugador().setVida(nuevaVida);
		}
		
	}

}