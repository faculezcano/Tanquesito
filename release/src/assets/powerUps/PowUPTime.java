package assets.powerUps;


import assets.PowerUp;
import assets.Tanque;
import common.Mapa;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;

/**
 * 
 */
public class PowUPTime extends PowerUp {

	public PowUPTime(double x, double y, Mapa m) {
		super(x, y, m);
		forma.setFill(new ImagePattern (new Image(getClass().getClassLoader().getResourceAsStream("img/PowUpT.png"))));
	}

	@Override
	public void colisionaTanque(Tanque t) {
		if(t==map.getJugador()){
			map.CongelarEnemigos();
		}
		
	}
	
}