package assets.tanques;

import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import assets.Bullet;
import assets.TanqueEnemigo;
import common.Mapa;

/**
 * 
 */
public class TanqueBlindado extends TanqueEnemigo {
	
	protected int direccion;
	protected int ultimoGiro;
	protected int giroAleatorio;
	protected int cantPisadas;
	protected boolean calleSinSalida = false;

	public TanqueBlindado(Mapa m,double x, double y) {
    	super(m,x,y);
    	vel_mov = 1;
    	puntos = 400;
    	vel_disparo = 2;
    	resistencia = 4;
    	
    	cuerpo.setFill(new ImagePattern(new Image(getClass().getClassLoader().getResourceAsStream("img/enemigo_tanque.png"))));
    	canon.setFill(new ImagePattern(new Image(getClass().getClassLoader().getResourceAsStream("img/enemigo_canon.png"))));
    	
    	direccion = rand.nextInt(4);
    	setVelocidad(velAleatoria(direccion));
    	
    	giroAleatorio = rand.nextInt(5)+2;
    	cantPisadas = 0;
	}
    
	@Override
    public void mover(double deltaT){
    	giroLentoCanon();
		
		double distanciaJug = distancia(map.getJugador(),this); //map.getJugador().getPosicion().distance(getPosicion());
		if( distanciaJug <= distanciaTiro){
			apuntar(map.getJugador().getX(),map.getJugador().getY());
			if(tiroLimpio && Math.abs(canonAng - canon.getRotate()) < 1){
				if(bullets.isEmpty()){
					Bullet b = disparar();
					if(b!=null){
						map.addBullet(b);
						//if(distanciaJug >= distanciaTiro*.75)
							setVelocidad(getVelocidad().multiply(-1));
					}
				}
				this.tiro.setStroke(Color.GREEN);
			}
		}
		else{
			apuntar(getX()+getVelocidad().getX(),getY()+getVelocidad().getY());
			this.tiro.setStroke(Color.BLACK);
		}
		
		//pisadas(p);

		super.mover(deltaT);
		
		tiroLimpio = true;
    }
    
    /*@Override
    public void setVelocidad(Point2D vel){
    	super.setVelocidad(vel);
    	setAngle(vel.angle(new Point2D(1,0)));
    }*/

	@Override
	public void colisiona() {
		
		tiroLimpio = false;
		setVelocidad(new Point2D(0,0));
		tiroLimpio = false;
		super.colisiona();
		tiroLimpio = false;
		
		direccion=giroAleatorio();
		setVelocidad(velAleatoria(direccion));
		
		tiroLimpio = false;
		cantPisadas = 0;
		calleSinSalida=true;
		//tiroLimpio = false;
	}
	
	protected int giroAleatorio(){
		//Algoritmo que dobla aleatoriamente
		int dir = 0;
		if(direccion == 0 || direccion == 2)
			if(rand.nextBoolean())
				dir = 1;
			else
				dir = 3;
		else
			if(rand.nextBoolean())
				dir = 0;
			else
				dir = 2;
		/*if(calleSinSalida){
			dir = (direccion+ultimoGiro)%4;
			if(dir < 0)
				dir = 4+dir;
		}
		else
			ultimoGiro = dir-direccion;
		*/
		return dir;
	}
	
	@Override
	public void addToGroup(Group g) {
		super.addToGroup(g);
		g.getChildren().add(tiro);
	}
}