package assets;

import java.util.Random;

import assets.tanques.Jugador;
import common.Mapa;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;

/**
 * 
 */
public abstract class TanqueEnemigo extends Tanque {
	
	protected Random rand = new Random();
	protected Mapa map;
	protected int distanciaTiro = 320;
	protected int punteria = 7; // 0 - 10
	protected boolean tiroLimpio = true;
	
	//Linea para saber si tiene un tiro limpio
	protected Line tiro = new Line(0,0,0,0);
	
	protected TanqueEnemigo(Mapa map){
		this.map = map;
		tiro.setStrokeWidth(10);
		tiro.setVisible(false);
	}
	
	@Override
	public void apuntar(Point2D pos){
		super.apuntar(pos);
		tiro.setEndX(pos.getX());
		tiro.setEndY(pos.getY());
	}
	
	@Override
	public void setPosicion(Point2D p){
		super.setPosicion(p);
		tiro.setStartX(p.getX());
		tiro.setStartY(p.getY());
	}
	
	public Shape getLineaTiro(){
		return tiro;
	}
	
	public void setTiroLimpio(boolean tiro){
		tiroLimpio = tiro;
		this.tiro.setStroke(Color.RED);
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
	
	public void afectar(){
		
	}
	
}