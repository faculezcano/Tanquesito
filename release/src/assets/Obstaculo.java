package assets;

import javafx.geometry.Point2D;
import javafx.scene.CacheHint;
import javafx.scene.Node;
import javafx.scene.shape.*;

/**
 * 
 */
public abstract class Obstaculo extends ObjetoEstatico {

	public static final int SIZE = 24;
	
    protected int vida;
    protected Rectangle forma;
    
    protected Obstaculo(double x, double y){
    	forma = new Rectangle(x,y,SIZE,SIZE);
    	forma.setCache(true);
    	forma.setCacheHint(CacheHint.SPEED);
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