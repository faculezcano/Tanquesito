package assets;

import java.util.LinkedList;

import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

/**
 * 
 */
public abstract class Tanque extends ObjetoDinamico {
	
	protected int resistencia;
    protected int vel_mov;
    protected int vel_disparo;
    protected int puntos;
    protected Point2D origen;
    protected LinkedList<Bullet> bullets;
    protected Rectangle cuerpo;
    protected Rectangle canon;
    protected double canonAng = 0;
    protected ImagePattern huella;
    protected LinkedList<Shape> pisadas;
    
    /**
     * @param vel
     */
    public Bullet disparar() {
    	double velDisparo = 8;
		double rad = Math.toRadians(canon.getRotate()+90);
		Point2D velBala = new Point2D(velDisparo*Math.cos(rad), velDisparo*Math.sin(rad));
		Point2D pos = new Point2D(getX()+35*Math.cos(rad)-10,getY()+35*Math.sin(rad)-5);
		Bullet bala = new Bullet(pos,velBala.multiply(vel_disparo));
		return bala;
    }

    /**
     * @param ang
     */
    public void setCanonAngle(double ang) {
    	if(ang < 0){
			ang +=360;
		}
		canonAng = ang;
    }
    
    protected void giroLentoCanon(){
    	double diff = canonAng-canon.getRotate();// calculo la diferencia
		
		if(Math.abs(diff) > 4.0){// si la diferencia es grosa
			double nuevoAng = 0;
			
			if(diff < 0)	// si la diferencia es negativa, osea -90 por ej, la paso a positiva -90 = 270
				diff +=360;
			
			if(diff < 180)	// aca elijo para que lado girar dependiendo que me quede mas corto
				nuevoAng = (canon.getRotate()+4.0)%360.0;
			else
				nuevoAng = (canon.getRotate()-4.0)%360.0;
			
			if(nuevoAng < 0)	// si el nuevo angulo es negativo lo paso a positivo
				nuevoAng+=360;
			
			canon.setRotate(nuevoAng);
		}else{	// si la diferencia es chica, lo asigno
			canon.setRotate(canonAng);
		}
    }
    
    protected void pisadas(Point2D pos){
    	if(origen.distance(pos)>=64){
			Rectangle pisada = new Rectangle(getX()-32,getY()-32,64,64);
			pisada.setFill(huella);
			pisada.setRotate(cuerpo.getRotate());
			
			Group g = (Group)cuerpo.getParent();
			
			g.getChildren().add(pisada);
			pisada.toBack();
			pisadas.addLast(pisada);
			
			Shape s = pisadas.getLast();
			if(s.getOpacity()<=0){
				g.getChildren().remove(s);
				pisadas.remove(s);
			}
			
			for(Shape pis : pisadas){
				pis.setOpacity(pis.getOpacity()-0.1);
			}
			
			origen = new Point2D(pos.getX(),pos.getY());
			
		}
    }

    /**
     * @param ang
     */
    public void setAngle(double ang) {
    	if(cuerpo.getRotate()!=ang){
			cuerpo.setRotate(ang);
			origen = new Point2D(getX(),getY());
			setPosicion(new Point2D(Math.round(getX()/32)*32,Math.round(getY()/32)*32));
			origen = new Point2D(getX(),getY());
		}
    }

    /**
     * @param pos
     */
    public void apuntar(Point2D pos) {
    	//Correccion de la base ortogonal de la gui y el angulo devuelto por arcTan
    	double deltax = pos.getX() - getX();
		double deltay = -pos.getY() + getY();
		double angle = Math.atan2(deltay, deltax);
		angle = (angle < 0 ? -angle : (2*Math.PI - angle));
		
		setCanonAngle(Math.toDegrees(angle)-90);
    }
    
    @Override
	public Shape getForma() {
		return cuerpo;
	}
    
    // TODO: fijarse si implementar este metodo general aca o que siga abstracto
    /*@Override
	public void setPosicion(Point2D p) {
		
	}*/

	@Override
	public Point2D getPosicion() {
		return new Point2D(cuerpo.getTranslateX()+cuerpo.getWidth(),cuerpo.getTranslateY()+cuerpo.getHeight());
	}

    /**
     * 
     */
    public abstract void romper();

    /**
     * @return
     */
    public int getPuntos() {
        return puntos;
    }

}