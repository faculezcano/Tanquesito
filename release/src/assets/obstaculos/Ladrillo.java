package assets.obstaculos;

import assets.Bullet;
import assets.Obstaculo;
import assets.Tanque;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;

/**
 * 
 */
public class Ladrillo extends Obstaculo {

	public Ladrillo (double x, double y){
		super(x,y);
		forma.setFill(new ImagePattern(new Image(getClass().getClassLoader().getResourceAsStream("img/ladrillo.png"))));
		vida = 1;
	}

	@Override
	public void colisionaBala(Bullet b) {
		if(b!=null){
			b.colisiona();
			if(vida>0)
				vida--;
		}
	}

	@Override
	public void colisionaTanque(Tanque t) {
		t.colisiona();
	}
}