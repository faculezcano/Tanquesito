package assets;

import common.Mapa;
import javafx.scene.shape.*;


/**
 * 
 */
public abstract class PowerUp extends ObjetoEstatico {
	
	protected Mapa map;
	protected Shape forma;
	
	public PowerUp(){
		forma = new Rectangle(0,0,32,32);
	}
	
	public Shape getForma(){
		return forma;
	}
	
	public void colisionaBala(Bullet b){
		
	}
}