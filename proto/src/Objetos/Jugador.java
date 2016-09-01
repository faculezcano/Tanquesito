package Objetos;

import java.util.LinkedList;

import Sistema.ObjetoDinamico;

import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.*;

public class Jugador extends ObjetoDinamico{
	
	protected Shape cuerpo = new Rectangle(0,0,64,64);
	protected Shape canon = new Rectangle(0,0,64,64);
	protected Point2D origen;
	protected LinkedList<Shape> pisadas = new LinkedList<Shape>();
	protected ImagePattern huella = new ImagePattern(new Image(getClass().getClassLoader().getResourceAsStream("img/huella.png")));
	
	protected double canonAng = 0;
	
	public Jugador(Point2D pos){
		cuerpo.setFill(new ImagePattern(new Image(getClass().getClassLoader().getResourceAsStream("img/cuerpo.png"))));
		canon.setFill(new ImagePattern(new Image(getClass().getClassLoader().getResourceAsStream("img/canon.png"))));
		
		DropShadow ds = new DropShadow();
		ds.setRadius(10);
		cuerpo.setEffect(ds);
		canon.setEffect(ds);
		//cuerpo.setStroke(Color.BLACK);
		//canon.setStroke(Color.BLACK);
	}
	
	public void setCanonAngle(double deg){
		canon.setRotate(deg);
		//canon.setTranslateX(getX()+15*Math.cos(Math.toRadians(deg+90)));
		//canon.setTranslateY(getY()+15*Math.sin(Math.toRadians(deg+90)));
	}
	
	public Bala disparar(){
		double velDisparo = 8;
		double rad = Math.toRadians(canon.getRotate()+90);
		Point2D vel = new Point2D(velDisparo*Math.cos(rad), velDisparo*Math.sin(rad));
		Point2D pos = new Point2D(getX()+35*Math.cos(rad)-10,getY()+35*Math.sin(rad)-5);
		Bala bala = new Bala(pos,vel);
		return bala;
	}
	
	public void addToGroup(Group g){
		g.getChildren().add(cuerpo);
		g.getChildren().add(canon);
	}
		
	public void setAngle(double deg){
		if(cuerpo.getRotate()!=deg){
			cuerpo.setRotate(deg);
			origen = new Point2D(getX(),getY());
			setPosition(new Point2D(Math.round(getX()/32)*32,Math.round(getY()/32)*32));
			origen = new Point2D(getX(),getY());
		}
	}
	
	public void pointTo(double x, double y){
		double deltax = x - getX();
		double deltay = -y + getY();
		double angle = Math.atan2(deltay, deltax);
		angle = (angle < 0 ? -angle : (2*Math.PI - angle));
		setCanonAngle(Math.toDegrees(angle)-90);
	}
	
	public double getX(){
		return cuerpo.getTranslateX()+32;
	}
	
	public double getY(){
		return cuerpo.getTranslateY()+32;
	}

	@Override
	public void setPosition(Point2D pos) {
		
		if(origen == null){
			origen = new Point2D(pos.getX(),pos.getY());
		}
		
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
		
		//double deg = canon.getRotate();
		cuerpo.setTranslateX(pos.getX()-32);
		cuerpo.setTranslateY(pos.getY()-32);
		canon.setTranslateX(pos.getX()-32);
		canon.setTranslateY(pos.getY()-32);
		//canon.setTranslateX(pos.getX()+15*Math.cos(Math.toRadians(deg+90)));
		//canon.setTranslateY(pos.getY()+15*Math.sin(Math.toRadians(deg+90)));
	}

	@Override
	public Point2D getPosition() {
		// TODO Auto-generated method stub
		return new Point2D(getX(),getY());
	}

	@Override
	public void translate(Point2D t) {
		// TODO Auto-generated method stub
		cuerpo.setTranslateX(t.getX());
		cuerpo.setTranslateY(t.getY());
		canon.setTranslateX(t.getX());
		canon.setTranslateY(t.getY());
	}

}
