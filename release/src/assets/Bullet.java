package assets;


import java.util.LinkedList;
import java.util.Random;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.util.Duration;

/**
 * 
 */
public class Bullet extends ObjetoDinamico {
	
	protected Shape forma;
    protected Point2D origen;
	protected Random r = new Random();
	protected MediaPlayer disparoSound;
	private LinkedList<Circle> humo;

    /**
     *
     */
    public Bullet(Point2D pos,Point2D vel) {
    	forma = new Rectangle(0,0,20,10);
    	forma.setTranslateX(pos.getX());
		forma.setTranslateY(pos.getY());
		forma.setFill(new ImagePattern(new Image(getClass().getClassLoader().getResourceAsStream("img/bala.png"))));
		
		humo = new LinkedList<Circle>();

		origen = new Point2D(pos.getX(),pos.getY());	
		
		disparoSound = new MediaPlayer(new Media("file:///"+ System.getProperty("user.dir").replace('\\', '/') +"/src/audio/8bit_bomb_explosion.wav"));
		disparoSound.setStartTime(Duration.millis(0));	//TODO: chequear si es necesario poner estos limites
		disparoSound.setStopTime(Duration.seconds(1));	//
		disparoSound.play();
		
		setVelocidad(vel);
		corregirAngulo();
		
    }
    
    private void corregirAngulo(){
    	double deltax = velocidad.getX();
		double deltay = - velocidad.getY();
		double angle = Math.atan2(deltay, deltax);
		angle = (angle < 0 ? -angle : (2*Math.PI - angle));
		forma.setRotate(Math.toDegrees(angle));
    }
    
    public double getX(){
		return forma.getTranslateX();
	}
	
	public double getY(){
		return forma.getTranslateY();
	}

    /**
     * @return
     */
    public Point2D getPosicion() {
    	return new Point2D(getX()-5,getY()-5);
	}

   
    /**
     * @return
     */
    public Shape getForma() {
		return forma;
	}

    /**
     * 
     */
    public void colision() {
	}

	@Override
	public void setPosicion(Point2D p) {
		
		//Sistema que deja humo durante el avance de la bala
		if(origen.distance(p) >=6){

			Circle nuevoHumo = crearHumo();
			Group g = (Group)forma.getParent();
			g.getChildren().add(nuevoHumo);
			humo.addLast(crearHumo());
			
			Shape s = humo.getFirst();
			if(s.getOpacity() <= 0){
				humo.remove(s);
				g.getChildren().remove(s);
			}
			
			for(Circle h : humo){
				h.setOpacity(h.getOpacity()-0.03);
				h.setRadius(h.getRadius()-0.1);
			}

			origen = new Point2D(p.getX(),p.getY());
		}
		
		forma.setTranslateX(p.getX()+5);
		forma.setTranslateY(p.getY()+5);
	}
	
	private Circle crearHumo(){
		Circle c = new Circle(getX()+10-10*Math.cos(Math.toRadians(forma.getRotate()))+r.nextGaussian()*2,getY()+5-10*Math.sin(Math.toRadians(forma.getRotate()))+r.nextGaussian()*2,8);
		double col = (r.nextInt(2)+2)/10.0;
		c.setFill(Color.color(col, col,col));
		c.setOpacity(0.6);
		return c;
	}

}