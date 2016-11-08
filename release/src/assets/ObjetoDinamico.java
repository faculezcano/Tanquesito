package assets;

import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.shape.Shape;

/**
 * 
 */
public abstract class ObjetoDinamico {
	protected int resistencia;
    protected Point2D velocidad = new Point2D(0,0);
    
    public static double distancia(ObjetoDinamico o1, ObjetoDinamico o2){
    	return Math.sqrt(Math.pow(o1.getX()-o2.getX(), 2) + Math.pow(o1.getY()-o2.getY(), 2));
    }
    
    public static double distancia(double x, double y, ObjetoDinamico o2){
    	return Math.sqrt(Math.pow(x-o2.getX(), 2) + Math.pow(y-o2.getY(), 2));
    }
    
    public void setResistencia(int r){
    	resistencia=r;
    }

    /*public abstract void setPosicion(Point2D p);

    public abstract Point2D getPosicion();*/
    
    public abstract double getX();
    
    public abstract double getY();
    
    public abstract void setX(double x);
    
    public abstract void setY(double y);
    
    public void mover(){
    	setX(getX()+velocidad.getX());
    	setY(getY()+velocidad.getY());
    }

    public Point2D getVelocidad() {
        return velocidad;
    }

    /**
     * @param vel
     */
    public void setVelocidad(Point2D vel) {
        velocidad = vel;
    }

    /**
     * @return
     */
    public abstract Shape getForma();

    /**
     * 
     */
    public abstract void colisiona();
    
    /**
     * 
     */
    public abstract void afectar();
    
    public int getResistencia(){
    	return resistencia;
    }
    
    public abstract void addToGroup(Group g);
    
    public abstract void removeFromGroup(Group g);

}