package assets.obstaculos;

import assets.Bullet;
import assets.Obstaculo;
import assets.Tanque;
import javafx.scene.paint.Color;

public class Block extends Obstaculo {
	
	
	public Block(double x, double y){
		super(x,y);
		forma.setFill(Color.BLACK);
	}

	@Override
	public void colisionaBala(Bullet b) {
		// TODO Auto-generated method stub

	}

	@Override
	public void colisionaTanque(Tanque t) {
		// TODO Auto-generated method stub

	}

}
