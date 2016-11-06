package assets.tanques;

import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import assets.Bullet;
import assets.TanqueEnemigo;
import common.Mapa;

public class TanqueBasico extends TanqueEnemigo {
	
	protected int direccion;
	protected int ultimoGiro;
	protected int giroAleatorio;
	protected int cantPisadas;
	protected boolean calleSinSalida = false;

    /**
     *
     */
    public TanqueBasico(Mapa m) {
    	super(m);
    	vel_mov = 1;
    	puntos = 100;
    	vel_disparo = 1;
    	resistencia = 1;
    	
    	cuerpo.setFill(new ImagePattern(new Image(getClass().getClassLoader().getResourceAsStream("img/Enemigo.png"))));
    	canon.setFill(new ImagePattern(new Image(getClass().getClassLoader().getResourceAsStream("img/canon.png"))));
    	
    	direccion = rand.nextInt(4);
    	setVelocidad(velAleatoria(direccion));
    	
    	giroAleatorio = rand.nextInt(5)+2;
    	cantPisadas = 0;
    	
    }
    
    @Override
    protected void pisadas(Point2D pos){
    	if(origen.distance(pos)>=64){
    		calleSinSalida = false;
    		cantPisadas++;
    		if(cantPisadas>=giroAleatorio){
    			direccion=giroAleatorio();
    			setVelocidad(velAleatoria(direccion));
    			cantPisadas = 0;
    			giroAleatorio = rand.nextInt(5)+2;
    		}
    	}
    	super.pisadas(pos);
    }
    
    @Override
	public void setPosicion(Point2D p) {
		
		giroLentoCanon();
		
		if(origen == null){
			origen = new Point2D(p.getX(),p.getY());
		}
		
		
		if(map.getJugador().getPosicion().distance(getPosicion()) <= distanciaTiro){
			apuntar(map.getJugador().getPosicion());
			if(tiroLimpio && Math.abs(canonAng - canon.getRotate()) < 1){
				if(bullets.isEmpty()){
					Bullet b = disparar();
					map.addBullet(b);
				}
				this.tiro.setStroke(Color.GREEN);
			}
		}
		else{
			apuntar(getPosicion().add(getVelocidad()));
			this.tiro.setStroke(Color.BLACK);
		}
		
		//pisadas(p);

		super.setPosicion(p);
		
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
		super.colisiona();
		tiroLimpio = false;
		
		direccion=giroAleatorio();
		setVelocidad(velAleatoria(direccion));
		
		cantPisadas = 0;
		calleSinSalida=true;
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
		if(calleSinSalida){
			dir = (direccion+ultimoGiro)%4;
			if(dir < 0)
				dir = 4+dir;
		}
		else
			ultimoGiro = dir-direccion;
		
		return dir;
	}
	
	@Override
	public void addToGroup(Group g) {
		super.addToGroup(g);
		g.getChildren().add(tiro);
	}

}