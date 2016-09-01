import java.awt.MouseInfo;
import java.util.Random;

import Objetos.*;
import Sistema.*;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.*;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;


public class tester extends Application {

	/**
	 * @param args
	 */
	
	protected double xDiscrepance = 0.0;
	protected double yDiscrepance = 0.0;
	protected Jugador j;
	protected Group g;
	protected Mapa map;
	//protected MotionManager mm = new MotionManager();
	
	private void createMap(int cantx, int canty){
		int size = 64;
		//LinkedList<Point2D> usadas = new LinkedList<Point2D>();
		Random r = new Random();
		for(int i = 0; i<30; i++){
			int coorX = r.nextInt(cantx);
			int coorY = r.nextInt(canty);
			Point2D dir;// = new Point2D(r.nextInt(3)-1,r.nextInt(3)-1);
			switch(r.nextInt(4)){
			case 0:
				dir = new Point2D(1,0);
				break;
			case 1:
				dir = new Point2D(0,1);
				break;
			case 2:
				dir = new Point2D(-1,0);
				break;
			default:
				dir = new Point2D(0,-1);
				break;
			}
			/*while(dir.magnitude()==0)
				dir = new Point2D(r.nextInt(3)-1,r.nextInt(3)-1);*/
			
			int cant;
			double tipo = r.nextGaussian();
			
			if(tipo < -1)
				cant = r.nextInt(3)+1;
			else if(tipo > 1)
				cant = r.nextInt(3)+1;
			else{
				Double d = 3*r.nextGaussian();
				cant = Math.abs(d.intValue()+5);
			}
			
			for(int j = 0; j < cant; j++){
				Shape s = new Rectangle(coorX*size,coorY*size,size,size);
				
				if(tipo < -1)
					s.setFill(Color.DARKSLATEBLUE);
				else if(tipo > 1)
					s.setFill(Color.DARKGREEN);
				else
					s.setFill(Color.DARKORANGE.darker());
				
				g.getChildren().add(s);
				coorX += dir.getX();
				coorY += dir.getY();
			}
			
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}
	
	public void start(Stage stage) {
		stage.setTitle("Tanquesito");
		
		g = new Group();
		
		j = new Jugador(new Point2D(230,225));
		//mm.addObject(j);
		
		//Bala b = new Bala(new Point2D(250,250),new Point2D(0,0));
		//b.addToGroup(g);
		//mm.addObject(b);
		//Npc n = new Npc(b);
		//Thread npc = new Thread(n);
		//npc.setDaemon(true);
		//npc.start();
		
		
		
		//createMap(20,20);
		
		map = new Mapa(8,8,g);
		map.generarMapa();
		
		map.add(j);
		

		final Scene s = new Scene(g,500,500, Color.GREY);
		s.setOnMouseMoved(new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent e) {
				j.pointTo(e.getX(), e.getY());
				Point2D mousePos = new Point2D(MouseInfo.getPointerInfo().getLocation().getX(),MouseInfo.getPointerInfo().getLocation().getY());
				xDiscrepance = mousePos.getX()-e.getX();
				yDiscrepance = mousePos.getY()-e.getY();
			}
			
		});
		s.setOnKeyPressed(new EventHandler<KeyEvent>(){
			@Override
			public void handle(KeyEvent e) {
				Point2D vel = j.getVelocidad();
				if(e.getCode()==KeyCode.W){
					vel = new Point2D(0, -4);
					j.setAngle(180);
				}else if(e.getCode()==KeyCode.S){
					vel = new Point2D(0, 4);
					j.setAngle(0);
				}else if(e.getCode()==KeyCode.A){
					vel = new Point2D(-4,0);
					j.setAngle(90);
				}else if(e.getCode()==KeyCode.D){
					vel = new Point2D(4,0);
					j.setAngle(-90);
				}
				Point2D mousePos = new Point2D(MouseInfo.getPointerInfo().getLocation().getX(),MouseInfo.getPointerInfo().getLocation().getY());
				j.pointTo(mousePos.getX()-xDiscrepance, mousePos.getY()-yDiscrepance);
				j.setVelocidad(vel);
			}
			
		});
		
		s.setOnMouseClicked(new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent e) {
				if(e.getButton() == MouseButton.PRIMARY){
					Platform.runLater(new Runnable(){

						@Override
						public void run() {
							// TODO Auto-generated method stub
							Bala b = j.disparar();
							//b.addToGroup(g);
							//mm.addObject(b);
							map.add(b);
						}
						
					});
					
					
				}
			}
			
		});
		
		s.setOnKeyReleased(new EventHandler<KeyEvent>(){
			@Override
			public void handle(KeyEvent e) {
				Point2D vel = j.getVelocidad();
				vel = new Point2D(0,0);
				j.setVelocidad(vel);
				/*if(e.getCode()==KeyCode.W){
					vel.setLocation(vel.getX(), 0);
					j.setAngle(0);
				}else if(e.getCode()==KeyCode.S){
					vel.setLocation(vel.getX(), 0);
					j.setAngle(180);
				}else if(e.getCode()==KeyCode.A){
					vel.setLocation(0,vel.getY());
					j.setAngle(90);
				}else if(e.getCode()==KeyCode.D){
					vel.setLocation(0,vel.getY());
					j.setAngle(-90);
				}*/
				Point2D mousePos = new Point2D(MouseInfo.getPointerInfo().getLocation().getX(),MouseInfo.getPointerInfo().getLocation().getY());
				j.pointTo(mousePos.getX()-xDiscrepance, mousePos.getY()-yDiscrepance);
			}
			
		});
		
		stage.setScene(s);
		stage.setResizable(false);
		stage.show();		
		//Thread motionThread = new Thread(mm);
		//motionThread.setDaemon(true);
		//motionThread.start();
	}

}