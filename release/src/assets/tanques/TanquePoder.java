package assets.tanques;

import javafx.geometry.Point2D;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import assets.ObjetoEstatico;
import assets.TanqueEnemigo;

/**
 * 
 */
public class TanquePoder extends TanqueEnemigo {
	
	public TanquePoder () {
		cuerpo = new Rectangle(0,0,64,64);
    	vel_mov = 2;
    	puntos = 300;
    	vel_disparo = 3;
    	resistencia = 1;
	}

	@Override
	public void romper() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setPosicion(Point2D p) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Point2D getPosicion() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Shape getForma() {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public void afectar() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void colisiona() {
		// TODO Auto-generated method stub
		
	}
	
}