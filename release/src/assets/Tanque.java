package assets;

import java.util.LinkedList;

import common.Animation;
import common.Mapa;
import common.SyncAdder;
import common.SyncRemover;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.CacheHint;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.util.Duration;

/**
 * 
 */
public abstract class Tanque extends ObjetoDinamico {
	
	public static final int SIZE = 48;
	
    protected int vel_mov;
    protected int vel_disparo;
    protected int puntos;
    protected double tempX,tempY;
    protected LinkedList<Bullet> bullets;
    protected Rectangle cuerpo;
    protected Rectangle canon;
    protected double canonAng = 0;
    protected double angle = 0; 
    protected ImagePattern huella;
    protected LinkedList<Shape> pisadas;
    
    protected Image[] aniDisparo;
    
    
    protected Tanque(double x, double y){
    	this.x = x;
    	this.y = y;
    	vel_mov = 1;
    	vel_disparo = 1;
    	puntos = 100;
    	bullets = new LinkedList<Bullet>();
    	pisadas = new LinkedList<Shape>();
    	cuerpo = new Rectangle(this.x,this.y,SIZE,SIZE);
    	canon = new Rectangle(this.x,this.y,SIZE,SIZE);
    	cuerpo.setCache(true);
    	cuerpo.setCacheHint(CacheHint.ROTATE);
    	canon.setCache(true);
    	canon.setCacheHint(CacheHint.ROTATE);
    	huella = new ImagePattern(new Image(getClass().getClassLoader().getResourceAsStream("img/huella.png")));
    	
    	tempX = x;
    	tempY = y;
    }
    
    @Override
    public void mover(double deltaT){
    	/*double deltaX = x;
    	double deltaY = y;*/
    	super.mover(deltaT);
    	/*deltaX-=x;
    	deltaY-=y;*/
    	setAngle(angle);
    	/*cuerpo.setTranslateX(cuerpo.getTranslateX()-deltaX);
    	cuerpo.setTranslateY(cuerpo.getTranslateY()-deltaY);
    	canon.setTranslateX(cuerpo.getTranslateX()-deltaX);
    	canon.setTranslateY(cuerpo.getTranslateY()-deltaY);*/
    	cuerpo.setX(x);
    	cuerpo.setY(y);
    	canon.setX(x);
    	canon.setY(y);
    }
    
    /**
     * @param vel
     */
    public Bullet disparar() {
		double rad = Math.toRadians(canon.getRotate()+90);
		Point2D velBala = new Point2D(vel_disparo*Math.cos(rad), vel_disparo*Math.sin(rad));
		Point2D pos = new Point2D(getX()+SIZE/2+Bullet.SIZE.getX()/2+32*Math.cos(rad)-10,getY()+SIZE/2+Bullet.SIZE.getY()/2+32*Math.sin(rad)-5);
		final Bullet bala = new Bullet(this,pos,velBala);
		bullets.add(bala);
		
		animacionDisparo();
		
		return bala;
    }
    
    protected void animacionDisparo(){
    	if(aniDisparo!=null){
    		double rad = Math.toRadians(canon.getRotate()+90);
    		Rectangle explo = new Rectangle(getX()+SIZE/2-8+(32+Bullet.SIZE.getX()/2)*Math.cos(rad),getY()+SIZE/2-8+32*Math.sin(rad),16,16);
    		explo.setRotate(canon.getRotate()+180);
    		Animation ag = new Animation(explo,aniDisparo,500);
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
    
    public void setAnimacionDisparo(Image[] a){
    	aniDisparo = a;
    }
    
    public void removeBullet(Bullet b){
    	bullets.remove(b);
    }

    /**
     * @param ang
     */
    public void setCanonAngle(double ang) {
    	if(ang < 0){
			ang +=360;
		}
		canonAng = ang;
    }
    
    public int velMovimiento(){
    	return vel_mov;
    }
    
    protected void giroLentoCanon(){
    	double diff = canonAng-canon.getRotate();// calculo la diferencia
		
		if(Math.abs(diff) > 4.0){// si la diferencia es grosa
			double nuevoAng = 0;
			
			if(diff < 0)	// si la diferencia es negativa, osea -90 por ej, la paso a positiva -90 = 270
				diff +=360;
			
			if(diff < 180)	// aca elijo para que lado girar dependiendo que me quede mas corto
				nuevoAng = (canon.getRotate()+4.0)%360.0;
			else
				nuevoAng = (canon.getRotate()-4.0)%360.0;
			
			if(nuevoAng < 0)	// si el nuevo angulo es negativo lo paso a positivo
				nuevoAng+=360;
			
			canon.setRotate(nuevoAng);
		}else{	// si la diferencia es chica, lo asigno
			canon.setRotate(canonAng);
		}
    }
    
    protected void pisadas(double x, double y){
    	if(Mapa.distancia(tempX, tempY, x, y)>=SIZE){
			final Rectangle pisada = new Rectangle(getX()-SIZE/2,getY()-SIZE/2,SIZE,SIZE);
			pisada.setFill(huella);
			pisada.setRotate(cuerpo.getRotate());
			
			
			Platform.runLater(new Runnable(){

				@Override
				public void run() {
					Group g = (Group)cuerpo.getParent();
					g.getChildren().add(pisada);
					pisada.toBack();
				}
				
			});
			
			
			FadeTransition fd = new FadeTransition(Duration.seconds(10));
			//fd.setFromValue(1.0);
			fd.setToValue(0.0);
			fd.setNode(pisada);
			fd.play();
			fd.setOnFinished(new EventHandler<ActionEvent>(){

				@Override
				public void handle(ActionEvent event) {
					FadeTransition fd = (FadeTransition)event.getSource();
					Group g = (Group)fd.getNode().getParent();
					g.getChildren().remove(fd.getNode());
				}
				
			});
			
			/*pisadas.addLast(pisada);
			
			Shape s = pisadas.getLast();
			if(s.getOpacity()<=0){
				g.getChildren().remove(s);
				pisadas.remove(s);
			}
			
			for(Shape pis : pisadas){
				pis.setOpacity(pis.getOpacity()-0.1);
			}*/
			
			tempX = x;
			tempY = y;
			
		}
    }
    
    @Override
    public void setVelocidad(Point2D vel){
    	super.setVelocidad(vel);
    	if(vel.magnitude()!=0){
    		if(vel.getX()>0)
    			angle = 270;
    		else if (vel.getX()<0)
    			angle = 90;
    		if(vel.getY() > 0)
    			angle = 0;
    		else if(vel.getY()<0)
    			angle = 180;
    	}
    }

    /**
     * @param ang
     */
    public void setAngle(double ang) {
    	if(cuerpo.getRotate()!=ang){
			cuerpo.setRotate(ang);
			this.setX(Math.round(getX()/(SIZE/2))*(SIZE/2));
			this.setY(Math.round(getY()/(SIZE/2))*(SIZE/2));
		}
    }

    /**
     * @param pos
     */
    public void apuntar(double x, double y) {
    	//Correccion de la base ortogonal de la gui y el angulo devuelto por arcTan
    	double deltax = x - getX();
		double deltay = -y + getY();
		double angle = Math.atan2(deltay, deltax);
		angle = (angle < 0 ? -angle : (2*Math.PI - angle));
		
		setCanonAngle(Math.toDegrees(angle)-90);
    }
    
    @Override
	public Shape getForma() {
		return cuerpo;
	}
    
    // TODO: fijarse si implementar este metodo general aca o que siga abstracto
    /*@Override
	public void setPosicion(Point2D p) {
    	cuerpo.setX(p.getX()-cuerpo.getWidth()/2);
		cuerpo.setY(p.getY()-cuerpo.getHeight()/2);
		canon.setX(p.getX()-canon.getWidth()/2);
		canon.setY(p.getY()-canon.getHeight()/2);
	}*/
    
    public void setX(double x){
    	this.x = x;
    	/*cuerpo.setX(x-cuerpo.getWidth()/2);
    	canon.setX(x-canon.getWidth()/2);*/
    }
    
    public void setY(double y){
    	/*
    	cuerpo.setY(y-cuerpo.getHeight()/2);
    	canon.setY(y-canon.getHeight()/2);*/
    	this.y = y;
    }
    
    public double getX(){
    	/*return cuerpo.getX()+cuerpo.getWidth()/2;*/
    	return x;
    }
    
    public double getY(){
    	//return cuerpo.getY()+cuerpo.getHeight()/2;
    	return y;
    }

	/*@Override
	public Point2D getPosicion() {
		return new Point2D(cuerpo.getX()+cuerpo.getWidth()/2,cuerpo.getY()+cuerpo.getHeight()/2);
		//return new Point2D(cuerpo.getX(),cuerpo.getY());
		//return new Point2D(cuerpo.getTranslateX()+cuerpo.getWidth()/2,cuerpo.getTranslateY()+cuerpo.getHeight()/2);
	}*/

    public int getPuntos() {
        return puntos;
    }
    
    public void colisiona(){
    	this.setX(Math.round(getX()/(SIZE/2))*(SIZE/2));
    	this.setY(Math.round(getY()/(SIZE/2))*(SIZE/2));
    	//this.setPosicion(new Point2D(Math.round(getX()/(SIZE/2))*(SIZE/2),Math.round(getY()/(SIZE/2))*(SIZE/2)));
    	//if(velocidad.magnitude() == 0){
    	/*Platform.runLater(new Runnable(){
			@Override
			public void run() {
				//setPosicion(new Point2D(Math.round(getX()/(SIZE/2))*(SIZE/2),Math.round(getY()/(SIZE/2))*(SIZE/2)));
			}
    	});*/
    		
    	/*}else{
    		setPosicion(getPosicion().add(getVelocidad().multiply(-1)));
    	}*/
    }
    
    public void afectar(){
    	if(resistencia>0)
    		resistencia--;
    }
    
    public LinkedList<Bullet> MisBalas(){
    	return bullets;
    }
    
    public void addToGroup(Group g){
		g.getChildren().add(cuerpo);
		g.getChildren().add(canon);
	}
    
    public void removeFromGroup(Group g){
		g.getChildren().remove(cuerpo);
		g.getChildren().remove(canon);
	}

}