package assets.tanques;

import assets.Bullet;
import assets.Nivel;
import assets.NivelUno;
import assets.Tanque;
import javafx.animation.FadeTransition;
import javafx.scene.Group;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.util.Duration;

/**
 * 
 */
public class Jugador extends Tanque {
	
	protected Nivel nivel;
    protected int disparos_simul;
    protected int vidas;
    protected double Xinicial;
    protected double Yinicial;
    protected boolean esInvulnerable = false;
    
    protected Group formas = new Group();
    
    protected FadeTransition efectoInvulnerable = new FadeTransition();
    

    public Jugador(double x, double y){
    	super(x,y);
    	
        puntos = 0;
        canonAng = 0;
        vidas = 3;
        
        nivel = new NivelUno();
        
        resistencia = nivel.getGolpesResiste();
        vel_disparo = nivel.getVelocidadDisparo();
        vel_mov = nivel.getVelocidad();
    	disparos_simul = nivel.getDisparosSimul();
    	
    	
    	Xinicial=x;
    	Yinicial=y;
    	
    	
    	cuerpo.setFill(new ImagePattern(new Image(getClass().getClassLoader().getResourceAsStream("img/cuerpo.png"))));
		canon.setFill(new ImagePattern(new Image(getClass().getClassLoader().getResourceAsStream("img/canon.png"))));
    
		/*DropShadow ds = new DropShadow();
		ds.setRadius(10);
		cuerpo.setEffect(ds);
		canon.setEffect(ds);*/

		formas.getChildren().addAll(cuerpo,canon);
		
    	efectoInvulnerable.setNode(formas);
    	efectoInvulnerable.setAutoReverse(true);
    	efectoInvulnerable.setFromValue(1);
    	efectoInvulnerable.setRate(8);
    	efectoInvulnerable.setToValue(0.2);
    	efectoInvulnerable.setCycleCount(-1);
    	efectoInvulnerable.setDuration(Duration.seconds(5));

    }
    
    public void setInvunerable(boolean v){
    	if(v == true)
    		efectoInvulnerable.playFromStart();
    	else{
    		efectoInvulnerable.stop();
    		efectoInvulnerable.getNode().setOpacity(1);
    	}
    	esInvulnerable = v;
    }
    
    public boolean esInvunerable(){
    	return esInvulnerable;
    }
    
    public Bullet disparar() {
    	Bullet b = super.disparar();
    	b.setRompeMetal(nivel.RompeMetal());
		return  b;
    }
    
    public void subirNivel(){
    	nivel = nivel.nextLvl();
    	resistencia = nivel.getGolpesResiste();
        vel_disparo = nivel.getVelocidadDisparo();
        vel_mov = nivel.getVelocidad();
    	disparos_simul = nivel.getDisparosSimul();
    }
    
    public Nivel getNivel() {
    	return nivel;
    }
		
	public int velMovimiento(){
		return nivel.getVelocidad();
	}
	
	@Override
	public void mover(double deltaT){
		giroLentoCanon();
		
		//pisadas(p);
		
		super.mover(deltaT+0.2);		
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
	
	public void addToGroup(Group g){
		g.getChildren().add(formas);
	}
	
	public void setPuntos(int p){
		puntos = p;
	}

	@Override
	public void afectar() {
		resistencia--;
		if(resistencia <= 0)
			resistencia = 0;

	}
	
	public void perderVida(){
		if (vidas > 0)
			vidas -= 1; 
		nivel = new NivelUno();
		resistencia = nivel.getGolpesResiste();
        vel_disparo = nivel.getVelocidadDisparo();
        vel_mov = nivel.getVelocidad();
    	disparos_simul = nivel.getDisparosSimul();
    	
		puntos = 0;
		
	}
	
	public int getVidas(){
		return vidas;
	}
	
	public void setVida(int v){
		vidas = v;
	}
	
	public double getXinicial(){
		return Xinicial;
	}
	public double getYinicial(){
		return Yinicial;
	}
	public int getResistencia(){
		return resistencia;
	}
	
}