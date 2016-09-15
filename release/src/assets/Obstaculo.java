package assets;


/**
 * 
 */
public abstract class Obstaculo extends ObjetoEstatico {

    /**
     * 
     */
    protected int vida;

    /**
     * 
     */
    public abstract void impactar();

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