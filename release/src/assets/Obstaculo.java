package assets;


/**
 * 
 */
public abstract class Obstaculo extends ObjetoEstatico {

    /**
     * Default constructor
     */
    public Obstaculo() {
    }

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