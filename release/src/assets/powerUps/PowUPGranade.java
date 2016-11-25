package assets.powerUps;

import assets.PowerUp;
import assets.Tanque;
import assets.TanqueEnemigo;
import common.Mapa;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;

/**
 * 
 */
public class PowUPGranade extends PowerUp {

	public PowUPGranade(double x, double y, Mapa m){
		super(x,y,m);
		forma.setFill(new ImagePattern (new Image(getClass().getClassLoader().getResourceAsStream("img/Granada.png"))));
	}
	
	
	@Override
	public void colisionaTanque(Tanque t) {
		if(t == map.getJugador()){
			for(TanqueEnemigo te: map.getEnemigos()){
				map.eliminarEnemigo(te);
			}
		}
		
	}
	
}