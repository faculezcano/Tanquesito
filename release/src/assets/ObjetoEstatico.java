package assets;

import javafx.scene.shape.Shape;

/**
 * 
 */
public abstract class ObjetoEstatico {
    
    public abstract Shape getForma();
    
    public abstract void colisionaBala(Bullet b);
    
    public abstract void colisionaTanque(Tanque t);
	
}