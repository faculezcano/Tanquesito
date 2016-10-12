package assets;

import java.util.LinkedList;

import javafx.scene.shape.Shape;

/**
 * 
 */
public abstract class ObjetoEstatico {

    /**
     * 
     */
    public abstract void afectarTanque();
    
    /**
     * 
     */
    public abstract void afectarBala();
    
    /**
     * 
     * @return
     */
    public abstract LinkedList<Shape> getFormas();
    
    public abstract Shape getForma();
    
    /**
     * 
     * @return
     */
    public abstract Shape colisionForma();
    
    public abstract void colisionaBala(Bullet b);
    
    
	

}