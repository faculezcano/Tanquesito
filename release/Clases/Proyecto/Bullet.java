
import java.util.*;

/**
 * 
 */
public class Bullet extends ObjetoDinamico {

    /**
     * Default constructor
     */
    public Bullet() {
    }

    /**
     * 
     */
    protected Shape forma;

    /**
     * 
     */
    protected Point2D origen;



    /**
     * @param p
     */
    public abstract void setPosicion(Point2D p);

    /**
     * @return
     */
    public abstract Point2D getPosicion();

    /**
     * @param Group g
     */
    public abstract void addToGroup(void Group g);

    /**
     * @param Group g
     */
    public abstract void remove(void Group g);

    /**
     * @return
     */
    public abstract Shape getForma();

    /**
     * 
     */
    public abstract void colision();

}