package assets;

import java.util.Random;

import common.Animation;
import common.SyncAdder;
import common.SyncRemover;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.CacheHint;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

/**
 * 
 */
public class Bullet extends ObjetoDinamico {
	
	public final static Point2D SIZE = new Point2D(10,5);
	
	protected Rectangle forma;
    protected Point2D origen;
	protected Random r = new Random();
	protected Tanque t;
	
	protected Image[] aniImpacto;
	protected final ImagePattern fill = new ImagePattern(new Image(getClass().getClassLoader().getResourceAsStream("img/bala.png")));
	//protected MediaPlayer disparoSound;
	
	//private LinkedList<Circle> humo;
	
	/**
	 * condicinal para determinar si rompe una pared de metal;
	 */
	private boolean rompe = false;
	
	//private Jugador tank;

    /**
     *
     */
    public Bullet(Tanque t,Point2D pos,Point2D vel) {
    	this.x = pos.getX();
    	this.y = pos.getY();
    	this.t = t;
    	forma = new Rectangle(0,0,SIZE.getX(),SIZE.getY());
    	forma.setX(x);
		forma.setY(y);
		forma.setFill(fill);
		forma.setCache(true);
		forma.setCacheHint(CacheHint.SPEED);
		resistencia = 1;
		//tank = (Jugador) t;
		
		//humo = new LinkedList<Circle>();

		origen = new Point2D(pos.getX(),pos.getY());	
		
		//disparoSound = new MediaPlayer(new Media("file:///"+ System.getProperty("user.dir").replace('\\', '/') +"/src/audio/8bit_bomb_explosion.wav"));
		//disparoSound = new MediaPlayer(new Media(getClass().getClassLoader().getResource("audio/8bit_bomb_explosion.wav").toURI().toString()));
		//disparoSound.setStartTime(Duration.millis(0));	//TODO: chequear si es necesario poner estos limites
		//disparoSound.setStopTime(Duration.seconds(1));	//
		//disparoSound.play();
		
		setVelocidad(vel);
		corregirAngulo();
		
    }
    
    public void setRompeMetal(boolean cond) {
    	rompe = cond; 			
    }
    
    public boolean getRompeMetal(){
    	return rompe;
    }
     
    private void corregirAngulo(){
    	double deltax = velocidad.getX();
		double deltay = - velocidad.getY();
		double angle = Math.atan2(deltay, deltax);
		angle = (angle < 0 ? -angle : (2*Math.PI - angle));
		forma.setRotate(Math.toDegrees(angle));
    }
    
    public double getX(){
		return x;
	}
	
	public double getY(){
		return y;
	}

    /**
     * @return
     */
    /*public Point2D getPosicion() {
    	return new Point2D(getX()-SIZE.getX()/2,getY()-SIZE.getY()/2);
	}*/

   
    /**
     * @return
     */
    public Shape getForma() {
		return forma;
	}

    @Override
    public void mover(double deltaT){
    	super.mover(deltaT);
    	forma.setX(x);
    	forma.setY(y);
    	/*if(origen.distance(p) >=SIZE.getY()*0.3){
			Circle nuevoHumo = crearHumo();
			Group g = (Group)forma.getParent();
			g.getChildren().add(nuevoHumo);
			FadeTransition fd = new FadeTransition(Duration.millis(2000));
			//fd.setFromValue(1.0);
			fd.setToValue(0.0);
			fd.setNode(nuevoHumo);
			fd.play();
			fd.setOnFinished(new EventHandler<ActionEvent>(){

				@Override
				public void handle(ActionEvent event) {
					FadeTransition fd = (FadeTransition)event.getSource();
					Group g = (Group)fd.getNode().getParent();
					g.getChildren().remove(fd.getNode());
				}
				
			});

			origen = new Point2D(p.getX(),p.getY());
		}*/
		
		//forma.setTranslateX(p.getX()+SIZE.getX()/2);
		//forma.setTranslateY(p.getY()+SIZE.getY()/2);
    	
    }

	/*@Override
	public void setPosicion(Point2D p) {
		
		//Sistema que deja humo durante el avance de la bala
		if(origen.distance(p) >=SIZE.getY()*0.3)

			origen = new Point2D(p.getX(),p.getY());
		}
		
		forma.setTranslateX(p.getX()+SIZE.getX()/2);
		forma.setTranslateY(p.getY()+SIZE.getY()/2);
	}*/
	
	@SuppressWarnings("unused")
	private Circle crearHumo(){
		Circle c = new Circle(getX()+SIZE.getX()/2-SIZE.getX()/2*Math.cos(Math.toRadians(forma.getRotate()))+r.nextGaussian(),getY()+SIZE.getY()/2-SIZE.getX()/2*Math.sin(Math.toRadians(forma.getRotate()))+r.nextGaussian(),SIZE.getY()*.8);
		double col = (r.nextInt(2)+2)/10.0;
		c.setFill(Color.color(col, col,col));
		c.setOpacity(0.6);
		return c;
	}
		


	@Override
	public void afectar() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void colisiona() {
		if (resistencia > 0) {
			resistencia--;
//			Thread t = new Thread (new Runnable(){
//				
//				public void run (){
//					Group g = (Group)forma.getParent();
////					while (!humo.isEmpty()) {
////						Shape s = humo.getFirst();
//						
////						if(s.getOpacity() <= 0){
////							humo.remove();
//							
//							Platform.runLater(new SyncRemover(s,g));
//							/*Platform.runLater(new Runnable() {
//
//								@Override
//								public void run() {
//									g.getChildren().remove(s);
//								}
//									
//							});*/
//						//}
////						for(Circle h:humo){
////							h.setOpacity(h.getOpacity()- 0.03);
////							h.setRadius(h.getRadius() - 0.1);
////						}
//						
//						try {
//							Thread.sleep(35);
//						} catch (InterruptedException e) {}
//					}	
//				}
//			}); 
//			t.setDaemon(true);
//			t.start();
			}
			if(resistencia <= 0){
				animacionImpacto();
				t.removeBullet(this);
			}
	}
	
	public void setAnimacionImpacto(Image[] a){
		aniImpacto = a;
	}
	
	protected void animacionImpacto(){
		if(aniImpacto!=null){
			Rectangle explo = new Rectangle(getX()-24-0*Math.cos(Math.toRadians(forma.getRotate())),getY()-24-0*Math.sin(Math.toRadians(forma.getRotate())),48,48);
			explo.setRotate(getForma().getRotate()-90);
	
			Animation ag = new Animation(explo,aniImpacto,1000);
			ag.setOnFinished(new EventHandler<ActionEvent>(){
	
				@Override
				public void handle(ActionEvent event) {
					Animation ag = (Animation)event.getSource();
					Group g = (Group)ag.getShape().getParent();
					Platform.runLater(new SyncRemover(ag.getShape(),g));
				}
				
			});
			Platform.runLater(new SyncAdder(explo,(Group)getForma().getParent()));
			ag.play();
		}
	}

	@Override
	public void addToGroup(Group g) {
		g.getChildren().add(forma);
	}

	@Override
	public void removeFromGroup(Group g) {
		g.getChildren().remove(forma);
	}

	@Override
	public void setX(double x) {
		this.x = x;
	}

	@Override
	public void setY(double y) {
		this.y = y;
	}
}
