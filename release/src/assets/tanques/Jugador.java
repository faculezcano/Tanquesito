package assets.tanques;

import java.util.LinkedList;

import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

import assets.Bullet;
import assets.Tanque;


/**
 * 
 */
public class Jugador extends Tanque {
	
	protected int nivel;
    protected int disparos_simul;
    protected int vida;

    /**
     * 
     */
    public Jugador() {
    	resistencia = 3;
        vel_mov = 4;
        vel_disparo = 6;
        puntos = 0;
        bullets = new LinkedList<Bullet>();
       	cuerpo = new Rectangle(0,0,64,64);
        canon = new Rectangle(0,0,64,64);;
        canonAng = 0;
        huella = new ImagePattern(new Image(getClass().getClassLoader().getResourceAsStream("img/huella.png")));;
        pisadas = new LinkedList<Shape>();
        nivel = 1;
    	disparos_simul = 1;
    	vida = 3;
    	
    	cuerpo.setFill(new ImagePattern(new Image(getClass().getClassLoader().getResourceAsStream("img/cuerpo.png"))));
		canon.setFill(new ImagePattern(new Image(getClass().getClassLoader().getResourceAsStream("img/canon.png"))));
    
		DropShadow ds = new DropShadow();
		ds.setRadius(10);
		cuerpo.setEffect(ds);
		canon.setEffect(ds);
    }
    
    //TODO: chequear esto
    public void addToGroup(Group g){
		g.getChildren().add(cuerpo);
		g.getChildren().add(canon);
	}

	@Override
	public void romper() {
		resistencia--;
		if(resistencia <= 0){
			vida--;
		}
		if(vida <= 0){
			//TODO: PERDIIII!
		}
	}

	@Override
	public void colision() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setPosicion(Point2D p) {
		
		giroLentoCanon();
		
		if(origen == null){
			origen = new Point2D(p.getX(),p.getY());
		}
		
		pisadas(p);

		cuerpo.setTranslateX(p.getX()-cuerpo.getWidth()/2);
		cuerpo.setTranslateY(p.getY()-cuerpo.getHeight()/2);
		canon.setTranslateX(p.getX()-canon.getWidth()/2);
		canon.setTranslateY(p.getY()-canon.getHeight()/2);
		
	}

}