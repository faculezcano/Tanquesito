package assets.obstaculos;

import java.util.LinkedList;

import javafx.geometry.Point2D;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

import assets.Bullet;
import assets.Obstaculo;
import assets.Tanque;

/**
 * 
 */
public class Metal extends Obstaculo {
	protected Rectangle rect;
	
	public Metal () {
		rect = new Rectangle(0,0,32,32);
		rect.setFill(new ImagePattern(new Image(getClass().getClassLoader().getResourceAsStream("img/metalWall.png"))));
		DropShadow ds = new DropShadow();
		ds.setRadius(10);
		vida = 4;
	}
	
	public void setPosicion(Point2D pos) {
		rect.setTranslateX(pos.getX());
		rect.setTranslateY(pos.getY());
	}

	@Override
	public LinkedList<Shape> getFormas() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Shape getForma() {
		return rect;
	}

	@Override
	public Shape colisionForma() {
		// TODO Auto-generated method stub
		return null;
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