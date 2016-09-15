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
     * 
     */
    public abstract void afectarTanque();
    
    /**
     * 
     */
    public abstract void afectarBala();

}