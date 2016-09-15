
import java.util.*;

/**
 * 
 */
public abstract class PowerUp extends ObjetoEstatico {

    /**
     * Default constructor
     */
    public PowerUp() {
    }

    /**
     * 
     */
    public Mapa map;

    /**
     * 
     */
    public abstract void accion();

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