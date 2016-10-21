package assets.tanques;

import java.util.LinkedList;

import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

import assets.*;


/**
 * 
 */
public class Jugador extends Tanque {
	
	protected Nivel nivel;
    protected int disparos_simul;
    protected int vida;

    /**
     * 
     */
    public Jugador() {
    	resistencia = 3;
        puntos = 0;
        bullets = new LinkedList<Bullet>();
       	cuerpo = new Rectangle(0,0,64,64);
        canon = new Rectangle(0,0,64,64);
        canonAng = 0;
        huella = new ImagePattern(new Image(getClass().getClassLoader().getResourceAsStream("img/huella.png")));
        pisadas = new LinkedList<Shape>();
        nivel = new NivelUno();
        vel_disparo = nivel.getVelocidadDisparo();
        vel_mov = nivel.getVelocidad();
    	disparos_simul = 1;
    	vida = 3;
    	
    	cuerpo.setFill(new ImagePattern(new Image(getClass().getClassLoader().getResourceAsStream("img/cuerpo.png"))));
		canon.setFill(new ImagePattern(new Image(getClass().getClassLoader().getResourceAsStream("img/canon.png"))));
    
		DropShadow ds = new DropShadow();
		ds.setRadius(10);
		cuerpo.setEffect(ds);
		canon.setEffect(ds);
    }
    
    public void subirNivel(){
    	nivel = nivel.nextLvl();
    	vel_disparo = nivel.getVelocidadDisparo();
    }
    
    public Nivel getNivel() {
    	return nivel;
    }
    
    //TODO: chequear esto
    public void addToGroup(Group g){
		g.getChildren().add(cuerpo);
		g.getChildren().add(canon);
	}
		
	public int velMovimiento(){
		return nivel.getVelocidad();
	}
	

	@Override
	public void setPosicion(Point2D p) {
		
		giroLentoCanon();
		
		if(origen == null){
			origen = new Point2D(p.getX(),p.getY());
		}
		
		pisadas(p);

		super.setPosicion(p);
		
	}
	
	public void setPuntos(int p){
		puntos=p;
	}



	@Override
	public void afectar() {
		resistencia--;
		if(resistencia <= 0){
			vida--;
		}
		if(vida <= 0){
			//TODO: PERDIIII!
		}
	}
}