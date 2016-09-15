
import java.util.*;

/**
 * 
 */
public abstract class ObjetoEstatico extends Mapa {

    /**
     * Default constructor
     */
    public ObjetoEstatico() {
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