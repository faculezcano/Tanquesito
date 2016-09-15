
import java.util.*;

/**
 * 
 */
public class Ladrillo extends ObjetoEstatico {

    /**
     * Default constructor
     */
    public Ladrillo() {
    }

    /**
     * @return
     */
    public abstract boolean colisionaVehiculo();

    /**
     * @return
     */
    public abstract boolean colisionaBala();

    /**
     * 
     */
    public abstract void colision();

}