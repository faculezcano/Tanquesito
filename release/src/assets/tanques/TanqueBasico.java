package assets.tanques;

import java.util.LinkedList;

import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import assets.TanqueEnemigo;

public class TanqueBasico extends TanqueEnemigo {
	
	protected int direccion;
	protected int ultimoGiro;
	protected int giroAleatorio;
	protected int cantPisadas;
	protected boolean calleSinSalida = false;

    /**
     *
     */
    public TanqueBasico() {
    	cuerpo = new Rectangle(0,0,64,64);
    	canon = new Rectangle(0,0,64,64);
    	vel_mov = 1;
    	puntos = 100;
    	vel_disparo = 1;
    	resistencia = 1;
    	
    	huella = new ImagePattern(new Image(getClass().getClassLoader().getResourceAsStream("img/huella.png")));
    	
    	pisadas = new LinkedList<Shape>();
    	
    	
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
		
		pisadas(p);

		super.setPosicion(p);
		
	}
    
    /*@Override
    public void setVelocidad(Point2D vel){
    	super.setVelocidad(vel);
    	setAngle(vel.angle(new Point2D(1,0)));
    }*/

	@Override
	public void colisiona() {
		setVelocidad(new Point2D(0,0));
		super.colisiona();
		
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
		g.getChildren().add(cuerpo);
		g.getChildren().add(canon);
	}

}