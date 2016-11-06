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
    
    public void setResistencia(int r){
    	resistencia=r;
    }

    public abstract void setPosicion(Point2D p);

    public abstract Point2D getPosicion();
    
    public double getX(){
    	return getPosicion().getX();
    }
    
    public double getY(){
    	return getPosicion().getY();
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