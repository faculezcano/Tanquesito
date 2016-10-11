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
    	vel_mov = 1;
    	puntos = 100;
    	vel_disparo = 1;
    	resistencia = 1;
    	
    	
    	cuerpo.setFill(new ImagePattern(new Image(getClass().getClassLoader().getResourceAsStream("img/Enemigo.png"))));
    }

	@Override
	public void romper() {
		// TODO Auto-generated method stub
		
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