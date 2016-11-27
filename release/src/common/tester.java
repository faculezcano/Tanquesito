package common;

import java.awt.AWTException;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Robot;

import assets.*;
import assets.tanques.*;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class tester extends Application {
	
	protected double xDiscrepance = 0.0;
	protected double yDiscrepance = 0.0;
	protected Jugador j;
	protected Scene s;
	protected Stage stage;
	protected Group g;
	protected Mapa map;
	protected TanqueEnemigo enemigo;
	protected TanqueEnemigo enemigo2;
	protected Text puntos;
	protected HUD hud;

	@Override	
	public void start(Stage stage) throws Exception {
		stage.setTitle("Tanquesito");
		stage.centerOnScreen();
		stage.setWidth(1038);
		stage.setHeight(628);
		stage.setResizable(false);
		
		this.stage = stage;
		this.stage.setResizable(false);
		this.stage.setWidth(1038);
		this.stage.setHeight(628);
		g = new Group();
		
		s = new Scene(g,1024,600, Color.OLIVE);
		
		s.setCursor(Cursor.CROSSHAIR);
		//BorderPane bp = new BorderPane();
		
		Group groupMapa = new Group();
		//bp.setCenter(groupMapa);
		map = new Mapa(8,8,groupMapa);
		map.setOnPerder(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent event) {
				System.out.println("tester: EventoPerder capturado");
				map.stopColisiones();
				Platform.runLater(new SyncRemover(groupMapa,g));
				
			}
			
		});
		
		hud = new HUD();
		//bp.setBottom(hud);
		
		j = new Jugador(48,48);
		
		hud.setJugador(j);
		hud.update();
		map.setJugador(j);
		
		g.getChildren().addAll(groupMapa,hud);
		//g.getChildren().add(bp);
		//map.addEnemigo(enemigo);
		//enemigo.setPosicion(new Point2D(32,97));
		
		map.cargarMapa("mapas/ProtoMap.txt");
		
		bindearMouse();
		bindearTeclado();
		
		stage.setScene(s);
		//stage.setResizable(false);
		stage.show();
		
		map.startColisiones();
		
		
		AnimationTimer hudUpdater = (new AnimationTimer(){
			@Override
			public void handle(long arg0) {
				hud.update();
				acomodarMouse();
			}
		});
		
		hudUpdater.start();
		
	}
	
	private void bindearMouse(){
		s.setOnMouseMoved(new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent e) {
				j.apuntar(e.getX(),e.getY());
				Point2D mousePos = new Point2D(MouseInfo.getPointerInfo().getLocation().getX(),MouseInfo.getPointerInfo().getLocation().getY());
				xDiscrepance = mousePos.getX()-e.getX();
				yDiscrepance = mousePos.getY()-e.getY();
				
			}
			
		});
		
		
		
		/*s.setOnMouseExited(new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent e) {
				acomodarMouse();
				
			}
		});*/
		
		s.setOnMouseClicked(new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent e) {
				if(e.getButton() == MouseButton.PRIMARY){
					Platform.runLater(new Runnable(){
						
						
						@Override
						public void run() {
							if(j.getNivel().getDisparosSimul()> j.MisBalas().size()){
						
								Bullet b = j.disparar();
								map.addBullet(b);
								
				
							}
							
							
						}
						
					});
					
					
				}
			}
			
		});
	}
	
	protected void acomodarMouse(){
		Point mousePos = MouseInfo.getPointerInfo().getLocation();
		int nuevaX = (int)mousePos.getX();
		int nuevaY = (int)mousePos.getY();

		if(mousePos.getX()<s.getWindow().getX())
			nuevaX = (int)s.getWindow().getX()+10;
		else if(mousePos.getX()>s.getWindow().getWidth()+s.getWindow().getX())
			nuevaX = (int)s.getWindow().getWidth() + (int)s.getWindow().getX()-10;
		
		if(mousePos.getY()<s.getWindow().getY())
			nuevaY = (int)s.getWindow().getY()+50;
		else if(mousePos.getY()>s.getWindow().getHeight()+s.getWindow().getY())
			nuevaY = (int)s.getWindow().getHeight() + (int)s.getWindow().getY()-10;
		
		if(nuevaX != (int)mousePos.getX() || nuevaY != (int)mousePos.getY()){
			try {
				new Robot().mouseMove(nuevaX, nuevaY);
			} catch (AWTException e1) {}
		}
	}
	
	protected KeyCode ultima;
	
	private void bindearTeclado(){
		
		s.setOnKeyPressed(new EventHandler<KeyEvent>(){
			@Override
			public void handle(KeyEvent e) {
				Point2D vel = j.getVelocidad();
				if(e.getCode()==KeyCode.W){
					vel = new Point2D(0, -j.velMovimiento());
					ultima = e.getCode();
					//j.setAngle(180);
				}else if(e.getCode()==KeyCode.S){
					vel = new Point2D(0, j.velMovimiento());
					ultima = e.getCode();
					//j.setAngle(0);
				}else if(e.getCode()==KeyCode.A){
					vel = new Point2D(-j.velMovimiento(),0);
					ultima = e.getCode();
					//j.setAngle(90);
				}else if(e.getCode()==KeyCode.D){
					vel = new Point2D(j.velMovimiento(),0);
					ultima = e.getCode();
					//j.setAngle(-90);
				}
				j.setVelocidad(vel);
				
				if(e.getCode()==KeyCode.P){
					
					if(!map.getEnemigos().isEmpty()){
						map.eliminarEnemigo(map.getEnemigos().poll());
					}
				}
				if(e.getCode()==KeyCode.L){
					
					map.getJugador().subirNivel();
				}
					
				
				double mouseX = MouseInfo.getPointerInfo().getLocation().getX();
				double mouseY = MouseInfo.getPointerInfo().getLocation().getY();
				j.apuntar(mouseX-xDiscrepance, mouseY-yDiscrepance);
			}
			});
			
		
		
		s.setOnKeyReleased(new EventHandler<KeyEvent>(){
			@Override
			public void handle(KeyEvent e) {
				Point2D vel = j.getVelocidad();
				if(ultima == e.getCode()){
					vel = new Point2D(0,0);
				}
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
