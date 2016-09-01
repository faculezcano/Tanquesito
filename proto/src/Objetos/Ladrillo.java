package Objetos;
import Sistema.ObjetoEstatico;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class Ladrillo extends ObjetoEstatico{
	
	protected Rectangle rect1;
	
	public Ladrillo(){
		rect1 = new Rectangle(0,0,32,32);
		rect1.setFill(new ImagePattern(new Image(getClass().getClassLoader().getResourceAsStream("img/ladrillo.png"))));
		DropShadow ds = new DropShadow();
		ds.setRadius(10);
		//rect1.setEffect(ds);
	}

	@Override
	public void setPosition(Point2D pos) {
		rect1.setTranslateX(pos.getX());
		rect1.setTranslateY(pos.getY());
	}

	@Override
	public void addToGroup(Group g) {
		g.getChildren().add(rect1);
	}

	@Override
	public Shape getForma() {
		return rect1;
	}
	
	public String toString(){
		return "Ladrillo";
	}

}
