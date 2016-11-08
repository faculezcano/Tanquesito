package assets;

import common.Mapa;
import javafx.scene.shape.*;


/**
 * 
 */
public abstract class PowerUp extends ObjetoEstatico {
	
	protected Mapa map;
	protected Shape forma;
	
	protected PowerUp(double x, double y, Mapa m){
		map=m;
		forma = new Rectangle(x,y,32,32);
	}
	
	public Shape getForma(){
		return forma;
	}
	
	public void colisionaBala(Bullet b){
		
	}
}