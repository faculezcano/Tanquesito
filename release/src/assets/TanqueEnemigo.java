package assets;

import java.util.Random;

import common.Mapa;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;

/**
 * 
 */
public abstract class TanqueEnemigo extends Tanque {
	
	protected Random rand = new Random();
	protected Mapa map;
	protected int distanciaTiro = 240;
	protected int punteria = 7; // 0 - 10
	protected boolean tiroLimpio = false;
	protected boolean puedeDisparar = false;
	
	//Linea para saber si tiene un tiro limpio
	protected Line tiro = new Line(0,0,0,0);
	
	protected TanqueEnemigo(Mapa map,double x, double y){
		super(x,y);
		this.map = map;
		tiro.setStrokeWidth(Bullet.SIZE.getY());
		tiro.setVisible(false);
		
		delayDisparo();		
		
	}
	
	protected void delayDisparo(){
		puedeDisparar=false;
		Thread delayDisparo = new Thread(new Runnable(){
			@Override
			public void run() {
					try {
						Thread.sleep(1000);
						puedeDisparar=true;
					} catch (InterruptedException e) {}
			}
		});
		delayDisparo.setName("Delay de disparo enemigo");
		delayDisparo.setDaemon(true);
		delayDisparo.start();
	}
	
	@Override
	public void apuntar(double x, double y){
		super.apuntar(x,y);
		/*tiro.setEndX(x);
		tiro.setEndY(y);*/
	}
	
	@Override
	public void mover(double deltaT){
		super.mover(deltaT);
		/*tiro.setStartX(getX());
		tiro.setStartY(getY());*/
	}
	
	
	/*@Override
	public void setPosicion(Point2D p){
		super.setPosicion(p);
		tiro.setStartX(p.getX());
		tiro.setStartY(p.getY());
	}*/
	
	@Override
	public Bullet disparar(){
		if(puedeDisparar){
			delayDisparo();	
			return super.disparar();
		}
		return null;
	}
	
	public Shape getLineaTiro(){
		return tiro;
	}
	
	public void setTiroLimpio(boolean tiro){
		tiroLimpio = tiro;
		//this.tiro.setStroke(Color.RED);
	}
	
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
	
	public void removeFromGroup(Group g){
		super.removeFromGroup(g);
		g.getChildren().remove(tiro);
	}
	
}