package assets.obstaculos;

import assets.Bullet;
import assets.Obstaculo;
import assets.Tanque;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;

/**
 * 
 */
public class Agua extends Obstaculo {
	
	public Agua(double x, double y){
		super(x,y);
		forma.setFill(new ImagePattern(new Image(getClass().getClassLoader().getResourceAsStream("img/agua.png"))));
	}

	@Override
	public void colisionaBala(Bullet b) {
		
	}

	@Override
	public void colisionaTanque(Tanque t) {
		if(t!=null)
			t.colisiona();
	}

}