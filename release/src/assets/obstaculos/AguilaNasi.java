package assets.obstaculos;

import assets.Bullet;
import assets.Obstaculo;
import assets.Tanque;

/**
 * 
 */
public class AguilaNasi extends Obstaculo {
	
	public AguilaNasi(double x, double y){
		super(x,y);
	}

	@Override
	public void colisionaBala(Bullet b) {
		if(b!=null){
			b.colisiona();
			if(vida>0){
				vida--;
			//}else{
				//morir
			//}
				
		}
	}
	}

	@Override
	public void colisionaTanque(Tanque t) {
		t.colisiona();
	}

}