
import java.util.*;

/**
 * 
 */
public class PowUPGranade extends ObjetoEstatico {

    /**
     * Default constructor
     */
    public PowUPGranade() {
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