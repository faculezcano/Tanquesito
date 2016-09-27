package assets.tanques;

import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import assets.ObjetoEstatico;
import assets.TanqueEnemigo;

/**
 * 
 */
public class TanqueBasico extends TanqueEnemigo {
	

    /**
     *
     */
    public TanqueBasico() {
    	cuerpo = new Rectangle(0,0,64,64);
    	vel_mov = 4;
    	puntos = 100;
    	
    	cuerpo.setFill(new ImagePattern(new Image(getClass().getClassLoader().getResourceAsStream("img/Enemigo.png"))));
    }

	@Override
	public void romper() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public Point2D getPosicion() {
		// TODO Auto-generated method stub
		return null;
	}	

	@Override
	public void colision(ObjetoEstatico oe) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afectar() {
		// TODO Auto-generated method stub
		
	}

}