package assets.obstaculos;

import assets.ObjetoEstatico;

/**
 * 
 */
public class Agua extends ObjetoEstatico {

    /**
     *
     */
    public Agua() {
    }

    /**
     * @return
     */
    public boolean colisionaVehiculo() {
		return false;
	}

    /**
     * @return
     */
    public  boolean colisionaBala() {
		return false;
	}
    	

    /**
     * 
     */
    public void colision(){}

}