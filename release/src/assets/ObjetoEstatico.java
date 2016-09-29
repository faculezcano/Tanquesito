package assets;

import java.util.LinkedList;

import com.sun.javafx.geom.Shape;

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
    
    public abstract javafx.scene.shape.Shape getForma();
    
    /**
     * 
     * @return
     */
    public abstract Shape colisionForma();
    
    
	

}