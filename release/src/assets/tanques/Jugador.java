package assets.tanques;

import assets.Bullet;
import assets.Nivel;
import assets.NivelUno;
import assets.Tanque;
import javafx.geometry.Point2D;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;

/**
 * 
 */
public class Jugador extends Tanque {
	
	protected Nivel nivel;
    protected int disparos_simul;
    protected int vida;
    protected double Xinicial;
    protected double Yinicial;
    protected int ResistenciaInicial;

    public Jugador(double x, double y){
    	super(x,y);
    	resistencia= ResistenciaInicial = 1;
        puntos = 0;
        canonAng = 0;

        nivel = new NivelUno();
        vel_disparo = nivel.getVelocidadDisparo();
        vel_mov = nivel.getVelocidad();
    	disparos_simul = 1;
    	vida = 3;
    	Xinicial=x;
    	Yinicial=y;
    	
    	
    	cuerpo.setFill(new ImagePattern(new Image(getClass().getClassLoader().getResourceAsStream("img/cuerpo.png"))));
		canon.setFill(new ImagePattern(new Image(getClass().getClassLoader().getResourceAsStream("img/canon.png"))));
    
		DropShadow ds = new DropShadow();
		ds.setRadius(10);
		cuerpo.setEffect(ds);
		canon.setEffect(ds);

    }
    
    public Bullet disparar() {
    	Bullet b = super.disparar();
    	b.setRompeMetal(nivel.RompeMetal());
		return  b;
    }
    
    public void subirNivel(){
    	nivel = nivel.nextLvl();
    	vel_disparo = nivel.getVelocidadDisparo();
    }
    
    public Nivel getNivel() {
    	return nivel;
    }
		
	public int velMovimiento(){
		return nivel.getVelocidad();
	}
	
	@Override
	public void mover(){
		giroLentoCanon();
		
		if(origen == null){
			origen = new Point2D(getX(),getY());
		}
		
		//pisadas(p);
		
		super.mover();		
	}

	/*@Override
	public void setPosicion(Point2D p) {
		
		giroLentoCanon();
		
		if(origen == null){
			origen = new Point2D(p.getX(),p.getY());
		}
		
		//pisadas(p);

		super.setPosicion(p);
		
	}*/
	
	public void setPuntos(int p){
		puntos=p;
	}



	@Override
	public void afectar() {
		resistencia--;
		if(resistencia <= 0){
			vida--;
		}
		if(vida <= 0){
			//TODO: PERDIIII!
		}
	}
	
	public int getVida(){
		return vida;
	}
	
	public void setVida(int v){
		vida=v;
	}
	
	public double getXinicial(){
		return Xinicial;
	}
	public double getYinicial(){
		return Yinicial;
	}
	public int getResistenciaInicial(){
		return ResistenciaInicial;
	}
	
	
}