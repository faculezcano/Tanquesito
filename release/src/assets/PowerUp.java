package assets;

import common.Mapa;


/**
 * 
 */
public abstract class PowerUp extends ObjetoEstatico {
	
	protected Mapa map;
    public abstract void accion();

    /**
     *
     */
    public PowerUp() {
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