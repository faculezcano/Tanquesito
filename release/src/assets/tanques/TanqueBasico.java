package assets.tanques;

import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import assets.Bullet;
import assets.Tanque;
import assets.TanqueEnemigo;
import common.Mapa;

public class TanqueBasico extends TanqueEnemigo {
	
	protected int direccion;
	protected int ultimoGiro;
	protected int giroAleatorio;
	protected int cantPisadas;
	protected boolean calleSinSalida = false;
	protected boolean disparoPorChoque = false;

    /**
     *
     */
    public TanqueBasico(Mapa m,double x, double y) {
    	super(m,x,y);
  
    	vel_mov = 1;
    	puntos = 100;
    	vel_disparo = 1;
    	resistencia = 1;
    	
    	cuerpo.setFill(new ImagePattern(new Image(getClass().getClassLoader().getResourceAsStream("img/enemigo_tanque.png"))));
    	canon.setFill(new ImagePattern(new Image(getClass().getClassLoader().getResourceAsStream("img/enemigo_canon3.png"))));
    	
    	direccion = rand.nextInt(4);
    	setVelocidad(velAleatoria(direccion));
    	
    	giroAleatorio = rand.nextInt(5)+2;
    	cantPisadas = 0;
    	
    }
    
    @Override
    protected void pisadas(double x, double y){
    	/*if(origen.distance(pos)>=64){
    		calleSinSalida = false;
    		cantPisadas++;
    		if(cantPisadas>=giroAleatorio){
    			direccion=giroAleatorio();
    			setVelocidad(velAleatoria(direccion));
    			cantPisadas = 0;
    			giroAleatorio = rand.nextInt(5)+2;
    		}
    	}*/
    	super.pisadas(x,y);
    }
    
    @Override
    public void mover(double deltaT){
    	giroLentoCanon();
		
		double distanciaJug = distancia(map.getJugador(),this); //map.getJugador().getPosicion().distance(getPosicion());
		if(!map.getJugador().esInvunerable() && distanciaJug <= distanciaTiro){
			apuntar(map.getJugador().getX(),map.getJugador().getY());
			if(tiroLimpio && Math.abs(canonAng - canon.getRotate()) < 1){
				if(bullets.isEmpty()){
					Bullet b = disparar();
					if(b!=null){
						disparoPorChoque = false;
						map.addBullet(b);
					}
				}
				this.tiro.setStroke(Color.GREEN);
			}
		}
		else{
			apuntar(getX()+Tanque.SIZE/2+getVelocidad().getX(),getY()+Tanque.SIZE/2+getVelocidad().getY());
			this.tiro.setStroke(Color.BLACK);
		}
		
		//pisadas(p);

		super.mover(deltaT);
		
		tiroLimpio = true;
    }

	@Override
	public void colisiona() {
		
		int i = rand.nextInt(2);
		
		if(i == 0 && bullets.isEmpty()){
			Bullet b = disparar();
			if(b!=null){
				disparoPorChoque = true;
				map.addBullet(b);
			}
		}
		
		if(i!=0){
			disparoPorChoque = false;
		}
		
		Point2D velvieja = getVelocidad();
		
		tiroLimpio = false;
		setVelocidad(new Point2D(0,0));
		tiroLimpio = false;
		super.colisiona();
		tiroLimpio = false;
		
		setVelocidad(velvieja);
		
		if(!disparoPorChoque){
		direccion=giroAleatorio();
		setVelocidad(velAleatoria(direccion));
		
		tiroLimpio = false;
		cantPisadas = 0;
		calleSinSalida=true;
		}
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