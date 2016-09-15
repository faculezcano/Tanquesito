
import java.util.*;

/**
 * 
 */
public class PowUpStar extends ObjetoEstatico {

    /**
     * Default constructor
     */
    public PowUpStar() {
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