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
public class TanquePoder extends TanqueEnemigo {
	
	protected int direccion;
	protected int ultimoGiro;
	protected int giroAleatorio;
	protected int cantPisadas;
	protected boolean calleSinSalida = false;
	protected boolean disparoPorChoque = false;
	
	public TanquePoder (Mapa m,double x, double y) {
		super(m,x,y);
    	//cuerpo = new Rectangle(0,0,64,64);
    	vel_mov = 2;
    	puntos = 300;
    	vel_disparo = 3;
    	resistencia = 1;
    	
    	cuerpo.setFill(new ImagePattern(new Image(getClass().getClassLoader().getResourceAsStream("img/enemigo_tanque.png"))));
    	canon.setFill(new ImagePattern(new Image(getClass().getClassLoader().getResourceAsStream("img/KV-2_Cannon.png"))));
    	
    	direccion = rand.nextInt(4);
    	setVelocidad(velAleatoria(direccion));
    	
    	giroAleatorio = rand.nextInt(5)+2;
    	cantPisadas = 0;
	}
	
	
	 public void mover(double deltaT){
		 giroLentoCanon();
			
		 double distanciaJug = distancia(map.getJugador(),this); //map.getJugador().getPosicion().distance(getPosicion());
		 if( distanciaJug <= distanciaTiro){
			 apuntar(map.getJugador().getX(),map.getJugador().getY());
			 if(tiroLimpio && Math.abs(canonAng - canon.getRotate()) < 1){
				 if(bullets.isEmpty()){
					 Bullet b = disparar();
					 if(b!=null){
						 disparoPorChoque = false;
						 map.addBullet(b);
						 if(distanciaJug >= distanciaTiro*.75)
							 setVelocidad(getVelocidad().multiply(-1));
					 }
				 }
				 this.tiro.setStroke(Color.GREEN);
			 }
		 }
		 else {
			 apuntar(getX()+getVelocidad().getX(),getY()+getVelocidad().getY());
			 this.tiro.setStroke(Color.BLACK);
		 }
			
	

		super.mover(deltaT);
			
		tiroLimpio = true;
	 }

	 @Override
	 public void colisiona() {
			
		 int i = rand.nextInt(2);
			
		 if(i == 0 && bullets.isEmpty()) {
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
			
			return dir;
		}
		
		@Override
		public void addToGroup(Group g) {
			super.addToGroup(g);
			g.getChildren().add(tiro);
		}
}