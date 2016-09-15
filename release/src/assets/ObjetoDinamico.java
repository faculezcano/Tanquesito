package assets;


import com.sun.javafx.geom.Shape;

import javafx.geometry.Point2D;

/**
 * 
 */
public abstract class ObjetoDinamico {

    /**
     * Default constructor
     */
    public ObjetoDinamico() {
    }

    /**
     * 
     */
    protected Point2D velocidad;



    /**
     * @param p
     */
    public abstract void setPosicion(Point2D p);

    /**
     * @return
     */
    public abstract Point2D getPosicion();


    /**
     * @return
     */
    public Point2D getVelocidad() {
        // TODO implement here
        return null;
    }

    /**
     * @param vel
     */
    public void setVelocidad(Point2D vel) {
        // TODO implement here
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