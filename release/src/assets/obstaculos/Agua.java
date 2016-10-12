package assets.obstaculos;

import java.util.LinkedList;

import javafx.scene.shape.Shape;

import assets.Bullet;
import assets.Obstaculo;
import assets.Tanque;

/**
 * 
 */
public class Agua extends Obstaculo {

	@Override
	public LinkedList<Shape> getFormas() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Shape getForma() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Shape colisionForma() {
		// TODO Auto-generated method stub
		return null;
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