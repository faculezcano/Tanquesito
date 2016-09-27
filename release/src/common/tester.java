package common;

import java.awt.MouseInfo;

import assets.*;
import assets.tanques.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class tester extends Application {
	
	protected double xDiscrepance = 0.0;
	protected double yDiscrepance = 0.0;
	protected Jugador j;
	protected Scene s;
	protected Group g;
	protected Mapa map;
	protected TanqueEnemigo enemigo;

	@Override
	public void start(Stage stage) throws Exception {
		stage.setTitle("Tanquesito");
		stage.centerOnScreen();
		
		g = new Group();
		s = new Scene(g,500,500, Color.GREY);
		
		j = new Jugador();
		j.addToGroup(g);
		g.getChildren().add(enemigo.getForma());
		
		map = new Mapa(8,8,g);
		
		map.setJugador(j);
		map.addEnemigo(enemigo);
		enemigo.setPosicion(new Point2D(32,97));
		
		map.cargarMapa("src/mapas/ProtoMap.txt");
		
		bindearMouse();
		bindearTeclado();
		
		stage.setScene(s);
		stage.setResizable(false);
		stage.show();
	}
	
	private void bindearMouse(){
		s.setOnMouseMoved(new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent e) {
				j.apuntar(new Point2D(e.getX(),e.getY()));
				Point2D mousePos = new Point2D(MouseInfo.getPointerInfo().getLocation().getX(),MouseInfo.getPointerInfo().getLocation().getY());
				xDiscrepance = mousePos.getX()-e.getX();
				yDiscrepance = mousePos.getY()-e.getY();
			}
			
		});
		
		s.setOnMouseClicked(new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent e) {
				if(e.getButton() == MouseButton.PRIMARY){
					Platform.runLater(new Runnable(){

						@Override
						public void run() {
							Bullet b = j.disparar();
							map.addBullet(b);
							g.getChildren().add(b.getForma());
						}
						
					});
					
					
				}
			}
			
		});
	}
	
	private void bindearTeclado(){
		
		s.setOnKeyPressed(new EventHandler<KeyEvent>(){
			@Override
			public void handle(KeyEvent e) {
				Point2D vel = j.getVelocidad();
				if(e.getCode()==KeyCode.W){
					vel = new Point2D(0, -j.velMovimiento());
					j.setAngle(180);
				}else if(e.getCode()==KeyCode.S){
					vel = new Point2D(0, j.velMovimiento());
					j.setAngle(0);
				}else if(e.getCode()==KeyCode.A){
					vel = new Point2D(-j.velMovimiento(),0);
					j.setAngle(90);
				}else if(e.getCode()==KeyCode.D){
					vel = new Point2D(j.velMovimiento(),0);
					j.setAngle(-90);
				}
				//Point2D mousePos = new Point2D(MouseInfo.getPointerInfo().getLocation().getX(),MouseInfo.getPointerInfo().getLocation().getY());
				//j.pointTo(mousePos.getX()-xDiscrepance, mousePos.getY()-yDiscrepance);
				j.setVelocidad(vel);
			}
			
		});
		
		s.setOnKeyReleased(new EventHandler<KeyEvent>(){
			@Override
			public void handle(KeyEvent e) {
				Point2D vel = j.getVelocidad();
				vel = new Point2D(0,0);
				j.setVelocidad(vel);
				//Point2D mousePos = new Point2D(MouseInfo.getPointerInfo().getLocation().getX(),MouseInfo.getPointerInfo().getLocation().getY());
				//j.pointTo(mousePos.getX()-xDiscrepance, mousePos.getY()-yDiscrepance);
			}
			
		});
		
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}

}
