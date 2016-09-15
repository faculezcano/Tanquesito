package assets.obstaculos;

import assets.ObjetoEstatico;
import assets.Obstaculo;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Node;
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
	}
	
	public void setPosicion(Point2D pos) {
		rect1.setTranslateX(pos.getX());
		rect1.setTranslateY(pos.getY());
	}
	
	
	public void addToGroup(Group g) {
		g.getChildren().add(rect1);
	}

	@Override
	public boolean colisionaVehiculo() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean colisionaBala() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void colision() {
		// TODO Auto-generated method stub
		
	}


	public Shape getForma() {
		return rect1;
	}


	@Override
	public void impactar() {
		// TODO Auto-generated method stub
		
	}

}