
import java.util.*;

/**
 * 
 */
public class Metal extends ObjetoEstatico {

    /**
     * Default constructor
     */
    public Metal() {
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