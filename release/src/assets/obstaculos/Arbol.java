package assets.obstaculos;


import assets.Bullet;
import assets.Obstaculo;
import assets.Tanque;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;

/**
 * 
 */
public class Arbol extends Obstaculo {

	public Arbol(){
		forma.setFill(new ImagePattern(new Image(getClass().getClassLoader().getResourceAsStream("img/arbol.png"))));
	}
	
	@Override
	public void colisionaBala(Bullet b) {
		
	}

	@Override
	public void colisionaTanque(Tanque t) {
		
	}

}