package assets.obstaculos;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import assets.Bullet;
import assets.Obstaculo;
import assets.Tanque;

/**
 * 
 */
public class Metal extends Obstaculo {
	protected Rectangle rect;
	
	public Metal (double x, double y){
		super(x,y);
		forma.setFill(new ImagePattern(new Image(getClass().getClassLoader().getResourceAsStream("img/metalWall.png"))));
		vida = 4;
	}

	@Override
	public void colisionaBala(Bullet b) {
		if(b != null){
			b.colisiona();
			if (b.getRompeMetal())	
				if(vida>0)
					vida--;
		}
	}

	@Override
	public void colisionaTanque(Tanque t) {
		t.colisiona();
	}
	
}