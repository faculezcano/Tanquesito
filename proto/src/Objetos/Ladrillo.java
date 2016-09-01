package Objetos;
import ObjetoEstatico;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.shape.Rectangle;

public class Ladrillo extends ObjetoEstatico{
	
	protected Rectangle rect1 = new Rectangle(0,0,32,32);
	protected Rectangle rect2 = new Rectangle(32,0,32,32);
	protected Rectangle rect3 = new Rectangle(0,32,32,32);
	protected Rectangle rect4 = new Rectangle(32,32,32,32);
	
	public Ladrillo(){
		
	}

	@Override
	public void setPosition(Point2D pos) {
		rect1.setTranslateX(pos.getX());
		rect1.setTranslateX(pos.getY());
	}

	@Override
	public void addToGroup(Group g) {
		// TODO Auto-generated method stub
		
	}

}
