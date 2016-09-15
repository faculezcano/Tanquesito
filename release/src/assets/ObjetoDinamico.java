package assets;

import javafx.geometry.Point2D;
import javafx.scene.shape.Shape;

/**
 * 
 */
public abstract class ObjetoDinamico {
	
    /**
     * 
     */
    protected Point2D velocidad = new Point2D(0,0);



    /**
     * @param p
     */
    public abstract void setPosicion(Point2D p);

    /**
     * @return
     */
    public abstract Point2D getPosicion();
    
    public double getX(){
    	return getPosicion().getX();
    }
    
    public double getY(){
    	return getPosicion().getY();
    }

    /**
     * @return
     */
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
    public abstract void colision();

}