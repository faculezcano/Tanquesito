package assets.powerUps;


import assets.PowerUp;
import assets.Tanque;
import common.Mapa;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;

/**
 */
public class PowUpLife extends PowerUp {

	public PowUpLife(double x, double y, Mapa m) {
		super(x, y, m);
		forma.setFill(new ImagePattern (new Image(getClass().getClassLoader().getResourceAsStream("img/PowUpVida.png"))));
	}

	@Override
	public void colisionaTanque(Tanque t) {
		if(map.getJugador() == t){
			int nuevaVida = map.getJugador().getVidas()+1;
			map.getJugador().setVida(nuevaVida);
		}
		
	}

}