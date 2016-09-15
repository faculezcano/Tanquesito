package assets.tanques;

import javafx.geometry.Point2D;

import assets.Tanque;


/**
 * 
 */
public class Jugador extends Tanque {
	
	protected int nivel;
    protected int disparos_simul;
    protected int vida;

    /**
     * 
     */
    public Jugador() {
    	nivel = 1;
    	disparos_simul = 1;
    	vida = 3;
    }

	@Override
	public void romper() {
		resistencia--;
		if(resistencia <= 0){
			vida--;
		}
		if(vida <= 0){
			//TODO: PERDIIII!
		}
	}

	@Override
	public void colision() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setPosicion(Point2D p) {
		
		giroLentoCanon();
		
		if(origen == null){
			origen = new Point2D(p.getX(),p.getY());
		}
		
		pisadas(p);
		
		cuerpo.setTranslateX(p.getX()-cuerpo.getWidth());
		cuerpo.setTranslateY(p.getY()-cuerpo.getHeight());
		canon.setTranslateX(p.getX()-canon.getWidth());
		canon.setTranslateY(p.getY()-canon.getHeight());
		
	}

}