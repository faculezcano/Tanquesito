package assets;

/**
 * 
 */
public abstract class ObjetoEstatico {

    /**
     *
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