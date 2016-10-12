package assets;

import java.util.LinkedList;

import javafx.scene.shape.Shape;

/**
 * 
 */
public abstract class ObjetoEstatico {

    public abstract LinkedList<Shape> getFormas();
    
    public abstract Shape getForma();

    public abstract Shape colisionForma();
    
    public abstract void colisionaBala(Bullet b);
    
    public abstract void colisionaTanque(Tanque t);
	
}