package assets;

import java.util.Random;

import javafx.geometry.Point2D;

/**
 * 
 */
public abstract class TanqueEnemigo extends Tanque {
	
	protected Random rand = new Random();
	
	/**
	 * Genera una velocidad en direccion segun direccion pasada por parametro
	 * @param dir direccion (0=arriba,1=derecha,2=abajo,3=izquierda)
	 * @return vector velocidad.
	 */
	protected Point2D velAleatoria(int dir){
		switch(dir){
		case 0:
			return (new Point2D(0,-1).multiply(vel_mov));
		case 1:
			return (new Point2D(1,0).multiply(vel_mov));
		case 2:
			return (new Point2D(0,1).multiply(vel_mov));
		default:
			return (new Point2D(-1,0).multiply(vel_mov));
		}
	}
	
	public void afectar(){
		
	}
	
}