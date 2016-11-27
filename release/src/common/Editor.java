package common;
import java.util.concurrent.ConcurrentLinkedQueue;

import assets.Obstaculo;
import assets.obstaculos.*;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.*;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;

public class Editor extends Application {

	protected Scene s;
	protected Group g;
	protected int[][] map = new int[45][28];
	protected Obstaculo current;
	protected int currentInt = 1;
	
	protected Group pisadasAgua;
    protected Group balasObstaculos;
    protected Group arboles;
    protected SubScene sceneMapa;
    
    protected ConcurrentLinkedQueue<Obstaculo> obstaculos = new ConcurrentLinkedQueue<Obstaculo>();
    
    public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		stage.setTitle("Tanquesito - Editor de mapa");
		
		g = new Group();
		s = new Scene(g,1024,644, Color.BLACK);
		
		pisadasAgua = new Group();
        balasObstaculos = new Group();
        arboles = new Group();
        DropShadow ds = new DropShadow();
		ds.setRadius(10);
		InnerShadow is = new InnerShadow ();
		is.setRadius(15);
		pisadasAgua.setEffect(is);
		arboles.setEffect(ds);
		balasObstaculos.setEffect(ds);	
        
        Group mapa = new Group(pisadasAgua,balasObstaculos,arboles);
        
        sceneMapa = new SubScene(mapa,1024,600);    
        sceneMapa.setFill(Color.GRAY);

        VBox vb = new VBox();
        vb.getChildren().addAll(sceneMapa,crearToolbar());
        g.getChildren().addAll(vb);
        
        //g.getChildren().addAll(sceneMapa);//,crearToolbar());
        
        ponerBlocks();
		
		createObstaculo();
		
		bindearMouse();
		
		stage.setScene(s);
		
		stage.show();
		
	}
	
	protected HBox crearToolbar(){
		HBox toolbar = new HBox();
        toolbar.setMinHeight(32);
        toolbar.setMinWidth(1024);
        toolbar.setLayoutY(600);
        toolbar.setSpacing(10);
        toolbar.setPadding(new Insets(10,10,10,10));
        
        Shape tool = new Ladrillo(0,0).getForma();
        tool.setOnMouseClicked(new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent arg0) {
				if(currentInt != 1){
					deleteCurrent();
					currentInt = 1;
					createObstaculo();
				}
			}
        });
        toolbar.getChildren().add(tool);
        
        tool = new Metal(0,0).getForma();
        tool.setOnMouseClicked(new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent arg0) {
				if(currentInt != 2){
					deleteCurrent();
					currentInt = 2;
					createObstaculo();
				}
			}
        });
        toolbar.getChildren().add(tool);
        
        tool = new Arbol(0,0).getForma();
        tool.setOnMouseClicked(new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent arg0) {
				if(currentInt != 3){
					deleteCurrent();
					currentInt = 3;
					createObstaculo();
				}
			}
        });
        toolbar.getChildren().add(tool);
        
        tool = new Agua(0,0).getForma();
        tool.setOnMouseClicked(new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent arg0) {
				if(currentInt != 4){
					deleteCurrent();
					currentInt = 4;
					createObstaculo();
				}
			}
        });
        toolbar.getChildren().add(tool);
        
        tool = new AguilaNasi(0,0).getForma();
        tool.setOnMouseClicked(new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent arg0) {
				if(currentInt != 8){
					deleteCurrent();
					currentInt = 8;
					createObstaculo();
				}
			}
        });
        toolbar.getChildren().add(tool);
        
        return toolbar;
	}
	
	protected void ponerBlocks(){
		for(int i = 0; i<map.length; i++){
			map[i][0] = 9;
			map[i][map[0].length-1] = 9;
		}
		for(int i = 0; i<map[0].length; i++){
			map[0][i] = 9;
			map[map.length-1][i] = 9;
		}
	}
	
	protected void bindearMouse(){
		sceneMapa.setOnMouseMoved(new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent e) {
				Rectangle s = (Rectangle)current.getForma();
				
				s.setX(Math.floor(e.getSceneX()/24)*24);
				s.setY(Math.floor(e.getSceneY()/24)*24);
			}
			
		});
		
		sceneMapa.setOnMouseClicked(new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent e) {
				if(e.getButton() == MouseButton.PRIMARY){
					Rectangle s = (Rectangle)current.getForma();
					s.setOpacity(1);
					map[(int)(s.getX()/24)+1][(int)(s.getY()/24)+1] = currentInt;
					
					obstaculos.add(current);
					
					createObstaculo();
				}else if(e.getButton() == MouseButton.SECONDARY){
					Rectangle s = (Rectangle)current.getForma();
					int x = (int)s.getX();
					int y = (int)s.getY();
					map[(x/24)+1][(y/24)+1] = 0;
					eliminarObstaculo(x,y);
				}
			}
			
		});
		
	}
	
	protected void createObstaculo(){
		switch(currentInt){
		case 1:
			current=new Ladrillo(0,0);
			balasObstaculos.getChildren().add(current.getForma());
			break;
		case 2:
			current=new Metal(0,0);
			balasObstaculos.getChildren().add(current.getForma());
			break;
		case 3:
			current=new Arbol(0,0);
			arboles.getChildren().add(current.getForma());
			break;
		case 4:
			current=new Agua(0,0);
			pisadasAgua.getChildren().add(current.getForma());
			break;
		case 8:
			current=new AguilaNasi(0,0);
			balasObstaculos.getChildren().add(current.getForma());
			break;
		}
		if(current != null){
			current.getForma().setOpacity(0.5);
		}
	}
	
	protected void eliminarObstaculo(int x, int y){
		for(Obstaculo o: obstaculos){
			Rectangle r = (Rectangle)o.getForma();
			if(r.getX() == x && r.getY() == y){
				obstaculos.remove(o);
				Group g = (Group)r.getParent();
				g.getChildren().remove(r);
			}
		}
	}
	
	protected void deleteCurrent(){
		switch(currentInt){
		case 1:
			balasObstaculos.getChildren().remove(current.getForma());
			break;
		case 2:
			balasObstaculos.getChildren().remove(current.getForma());
			break;
		case 3:
			arboles.getChildren().remove(current.getForma());
			break;
		case 4:
			pisadasAgua.getChildren().remove(current.getForma());
			break;
		case 8:
			balasObstaculos.getChildren().remove(current.getForma());
			break;
		}
	}
	
	public String toString(){
		String s = "";
		for(int j = 0;j < map[0].length;j++){
			for(int i = 0; i < map.length;i++){
				s+=map[i][j];
			}
			s+='\n';
		}
		return s;
	}

}
