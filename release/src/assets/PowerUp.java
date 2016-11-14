package assets;

import common.Mapa;
import javafx.scene.CacheHint;
import javafx.scene.shape.*;


/**
 * 
 */
public abstract class PowerUp extends ObjetoEstatico {
	
	public static final int SIZE = 24;
	
	protected Mapa map;
	protected Shape forma;
	
	protected PowerUp(double x, double y, Mapa m){
		map = m;
		forma = new Rectangle(x,y,SIZE,SIZE);
		forma.setCache(true);
		forma.setCacheHint(CacheHint.SPEED);
	}
	
	public Shape getForma(){
		return forma;
	}
	
	public void colisionaBala(Bullet b){
		
	}
}