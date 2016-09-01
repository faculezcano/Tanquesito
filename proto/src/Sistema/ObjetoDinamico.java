package Sistema;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.shape.Shape;


public abstract class ObjetoDinamico {
	
	protected Point2D velocidad = new Point2D(0,0);
	protected Point2D posicion;
	
	public void setVelocidad(Point2D vel){
		velocidad = vel;
	}
	
	public Point2D getVelocidad(){
		return velocidad;
	}
	
	public abstract void addToGroup(Group g);
	
	public abstract void translate(Point2D t);
	
	public abstract void setPosition(Point2D pos);
	
	public abstract Shape getForma();
	
	public void setPosition(double x, double y){
		setPosition(new Point2D(x,y));
	}
	
	public abstract void remove(Group g);
	
	public abstract Point2D getPosition();

}
