package assets.obstaculos;

import assets.Bullet;
import assets.Obstaculo;
import assets.Tanque;

/**
 * 
 */
public class Agua extends Obstaculo {

	@Override
	public void colisionaBala(Bullet b) {
		
	}

	@Override
	public void colisionaTanque(Tanque t) {
		if(t!=null)
			t.colisiona();
	}

}