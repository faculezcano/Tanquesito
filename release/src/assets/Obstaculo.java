package assets;

import javafx.geometry.Point2D;
import javafx.scene.shape.*;

/**
 * 
 */
public abstract class Obstaculo extends ObjetoEstatico {

    protected int vida;
    protected Rectangle forma;
    
    protected Obstaculo(){
    	forma = new Rectangle(0,0,32,32);
    	vida = 1;
    }
    
    public int GetVida() {
    	return vida;
    }
    
    public Shape getForma(){
    	return forma;
    }
    
	public void setPosicion(Point2D pos) {
		forma.setTranslateX(pos.getX());
		forma.setTranslateY(pos.getY());
	}
}