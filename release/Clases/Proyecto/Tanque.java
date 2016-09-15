package ProyectoTDP;


import java.util.*;

import javafx.geometry.Point2D;

/**
 * 
 */
public abstract class Tanque extends MotionManager {

    /**
     * Default constructor
     */
    private Tanque() {
    }

    /**
     * 
     */
    protected int resistencia;

    /**
     * 
     */
    protected int vel_mov;

    /**
     * 
     */
    protected int vel_disparo;

    /**
     * 
     */
    protected int puntos;

    /**
     * 
     */
    protected Point2D origen;

    /**
     * 
     */
    protected List<Bullet> bullets;




    /**
     * @param vel
     */
    public void disparar(Point2D vel) {
        // TODO implement here
    }

    /**
     * @param ang
     */
    public void setCanonAngle(double ang) {
        // TODO implement here
    }

    /**
     * @param ang
     */
    public void setAngle(double ang) {
        // TODO implement here
    }

    /**
     * @param pos
     */
    public void apuntar(Point2D pos) {
        // TODO implement here
    }

    /**
     * 
     */
    public abstract void dañar();

    /**
     * @return
     */
    public int getPuntos() {
        // TODO implement here
        return 0;
    }

}