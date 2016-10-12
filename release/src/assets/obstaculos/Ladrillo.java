package assets.obstaculos;

import java.util.LinkedList;

import assets.Bullet;
import assets.Obstaculo;
import javafx.geometry.Point2D;

import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

/**
 * 
 */
public class Ladrillo extends Obstaculo {
	protected Rectangle rect1;
	
	
	public Ladrillo (){
		rect1 = new Rectangle(0,0,32,32);
		rect1.setFill(new ImagePattern(new Image(getClass().getClassLoader().getResourceAsStream("img/ladrillo.png"))));
		DropShadow ds = new DropShadow();
		ds.setRadius(10);
		vida = 1;
		
	}
	
	public void setPosicion(Point2D pos) {
		rect1.setTranslateX(pos.getX());
		rect1.setTranslateY(pos.getY());
	}
	



	


	public Shape getForma() {
		return rect1;
	}


	@Override
	public void impactar() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afectarTanque() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afectarBala() {
		// TODO Auto-generated method stub
		
	}
	


	@Override
	public LinkedList<com.sun.javafx.geom.Shape> getFormas() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public com.sun.javafx.geom.Shape colisionForma() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void colisionaBala(Bullet b) {
		if (b != null) {
			b.colisiona();
			if (vida > 0)
				vida--;
		}
	

	}

	

}