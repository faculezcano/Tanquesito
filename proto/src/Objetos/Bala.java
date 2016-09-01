package Objetos;

import java.util.LinkedList;
import java.util.Random;

import Sistema.ObjetoDinamico;

import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.*;

public class Bala extends ObjetoDinamico{
	protected Shape bala = new Rectangle(0,0,20,10);
	protected Point2D origen;
	protected Random r = new Random();
	
	private LinkedList<Circle> humo = new LinkedList<Circle>();
	
	public Bala(Point2D pos,Point2D vel){
		bala.setTranslateX(pos.getX());
		bala.setTranslateY(pos.getY());
		double deltax = vel.getX();
		double deltay = - vel.getY();
		double angle = Math.atan2(deltay, deltax);
		angle = (angle < 0 ? -angle : (2*Math.PI - angle));
		bala.setRotate(Math.toDegrees(angle));
		
		//bala.setRotate(vel.angle(new Point2D(-1,0)));
		origen = new Point2D(pos.getX(),pos.getY());
		setVelocidad(vel);
		bala.setFill(new ImagePattern(new Image(getClass().getClassLoader().getResourceAsStream("img/bala.png"))));
	}
	
	public void addToGroup(Group g){
		g.getChildren().add(bala);
	}
	
	public double getX(){
		return bala.getTranslateX();
	}
	
	public double getY(){
		return bala.getTranslateY();
	}
	
	@Override
	public void setPosition(Point2D pos) {
		
		if(origen.distance(pos) >=6){
			//int dispersion = 3;
			Circle c = new Circle(getX()+10-10*Math.cos(Math.toRadians(bala.getRotate()))+r.nextGaussian()*2,getY()+5-10*Math.sin(Math.toRadians(bala.getRotate()))+r.nextGaussian()*2,8);
			double col = (r.nextInt(2)+2)/10.0;
			c.setFill(Color.color(col, col,col));
			c.setOpacity(0.6);
			
			humo.addLast(c);
			
			Group g = (Group)bala.getParent();
			//System.out.println(origen);
						
			g.getChildren().add(c);
			
			Shape s = humo.getFirst();
			if(s.getOpacity() <= 0){
				humo.remove(s);
				g.getChildren().remove(s);
			}
			
			for(Circle h : humo){
				h.setOpacity(h.getOpacity()-0.03);
				h.setRadius(h.getRadius()-0.1);
			}
			//Particle p = new Particle(c);
			//Thread t = new Thread(p);
			//t.setDaemon(true);
			//t.start();
			origen = new Point2D(pos.getX(),pos.getY());
		}
		bala.setTranslateX(pos.getX()+5);
		bala.setTranslateY(pos.getY()+5);
	}

	@Override
	public Point2D getPosition() {
		// TODO Auto-generated method stub
		return new Point2D(getX()-5,getY()-5);
	}

	@Override
	public void translate(Point2D t) {
		// TODO Auto-generated method stub
		bala.setTranslateX(t.getX());
		bala.setTranslateY(t.getY());
	}

}
