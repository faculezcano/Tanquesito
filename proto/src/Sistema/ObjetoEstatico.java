package Sistema;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.shape.Shape;


public abstract class ObjetoEstatico {
	
	public abstract void setPosition(Point2D pos);
	
	public abstract void addToGroup(Group g);
	
	public abstract Shape getForma();

}
