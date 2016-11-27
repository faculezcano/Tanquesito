package common;
import java.io.*;
import java.util.concurrent.ConcurrentLinkedQueue;

import assets.Obstaculo;
import assets.obstaculos.*;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.effect.*;
import javafx.scene.image.Image;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.stage.*;

public class Editor extends Application {

	protected Scene s;
	protected Group g;
	protected int[][] map = new int[45][28];
	protected Obstaculo current;
	protected int currentInt = 1;
	
	protected Stage stage;
	
	protected Group pisadasAgua;
    protected Group balasObstaculos;
    protected Group arboles;
    protected SubScene sceneMapa;
    
    protected Paint fillNuevo = new ImagePattern(new Image(getClass().getClassLoader().getResourceAsStream("img/new.png")));
    protected Paint fillAbrir = new ImagePattern(new Image(getClass().getClassLoader().getResourceAsStream("img/open.png")));
    protected Paint fillGuardar = new ImagePattern(new Image(getClass().getClassLoader().getResourceAsStream("img/save.png")));
    
    protected ConcurrentLinkedQueue<Obstaculo> obstaculos = new ConcurrentLinkedQueue<Obstaculo>();
    
    protected int ultiX = 0;
    protected int ultiY = 0;
    
    protected boolean huboCambio = false;
    
    public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		this.stage = stage;
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
        
        Alert al = new Alert(AlertType.CONFIRMATION);
        al.setContentText("¿Esta seguro que no quiere guardar el mapa?");
        
        Shape tool = new Rectangle(0,0,24,24);
        tool.setFill(fillNuevo);
        tool.setOnMouseClicked(new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent arg0) {
				if(huboCambio){
					al.setTitle("Nuevo mapa");
					al.setHeaderText(al.getTitle());
					al.showAndWait();
				}
				
				if(!huboCambio || al.getResult() == ButtonType.OK){
					vaciarMapa();
					createObstaculo();
					huboCambio = false;
				}
				
			}
        });
        toolbar.getChildren().add(tool);
        
        tool = new Rectangle(0,0,24,24);
        tool.setFill(fillAbrir);
        tool.setOnMouseClicked(new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent arg0) {
				if(huboCambio){
					al.setTitle("Abrir mapa");
					al.setHeaderText(al.getTitle());
					al.showAndWait();
				}
				
				if(!huboCambio || al.getResult() == ButtonType.OK){
					FileChooser fc = new FileChooser();
					fc.setTitle("Abrir mapa");
					File file = fc.showOpenDialog(stage);
					cargarMapa(file);
				}
			}
        });
        toolbar.getChildren().add(tool);
        
        tool = new Rectangle(0,0,24,24);
        tool.setFill(fillGuardar);
        tool.setOnMouseClicked(new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent arg0) {
				FileChooser fc = new FileChooser();
				fc.setTitle("Guardar mapa");
				File file = fc.showSaveDialog(stage);
				guardarMapa(file);
			}
        });
        toolbar.getChildren().add(tool);
        
        tool = new Line(0,0,0,24);
        tool.setStroke(Color.GRAY);
        toolbar.getChildren().add(tool);
        
        
        tool = new Ladrillo(0,0).getForma();
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
	
	protected void vaciarMapa(){
		pisadasAgua.getChildren().clear();
		balasObstaculos.getChildren().clear();
		arboles.getChildren().clear();
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
				
				ultiX = (int)s.getX();
				ultiY = (int)s.getY();
			}
			
		});
		
		sceneMapa.setOnMouseClicked(new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent e) {
				if(e.getButton() == MouseButton.PRIMARY){
					Rectangle s = (Rectangle)current.getForma();
					int x = (int)s.getX();
					int y = (int)s.getY();
					
					s.setOpacity(1);
					map[(x/24)+1][(y/24)+1] = currentInt;
					eliminarObstaculo(x,y);
					obstaculos.add(current);
					createObstaculo();
					huboCambio = true;
					
				}else if(e.getButton() == MouseButton.SECONDARY){
					Rectangle s = (Rectangle)current.getForma();
					int x = (int)s.getX();
					int y = (int)s.getY();
					map[(x/24)+1][(y/24)+1] = 0;
					eliminarObstaculo(x,y);
					huboCambio = true;
				}
			}
			
		});
		
	}
	
	protected void createObstaculo(){
		switch(currentInt){
		case 1:
			current=new Ladrillo(ultiX,ultiY);
			balasObstaculos.getChildren().add(current.getForma());
			break;
		case 2:
			current=new Metal(ultiX,ultiY);
			balasObstaculos.getChildren().add(current.getForma());
			break;
		case 3:
			current=new Arbol(ultiX,ultiY);
			arboles.getChildren().add(current.getForma());
			break;
		case 4:
			current=new Agua(ultiX,ultiY);
			pisadasAgua.getChildren().add(current.getForma());
			break;
		case 8:
			current=new AguilaNasi(ultiX,ultiY);
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
	
	public void cargarMapa(File file){
		try{
			InputStreamReader f = new InputStreamReader(new FileInputStream(file));
			
			BufferedReader b = new BufferedReader(f);
			
			vaciarMapa();
			
			int fila = -1;
			String cadena = b.readLine();
			//enemigo = enemigos.poll();
			
			while(cadena != null){
				for(int col=-1;col<cadena.length()-1;col++){

					Obstaculo obstaculo;
					try{
						map[col+1][fila+1]= Integer.parseInt(String.valueOf(cadena.charAt(col+1)));
					}catch(NumberFormatException e){
						
					}
					switch (cadena.charAt(col+1)){
					
					case '9':
						
						break;
					case '1':
						obstaculo = new Ladrillo(col*Obstaculo.SIZE,fila*Obstaculo.SIZE);
						balasObstaculos.getChildren().add(obstaculo.getForma());
						obstaculos.add(obstaculo);
						break;
					case '2':
						obstaculo = new Metal(col*Obstaculo.SIZE,fila*Obstaculo.SIZE);
						balasObstaculos.getChildren().add(obstaculo.getForma());
						obstaculos.add(obstaculo);
						break;
					case '3':
						obstaculo = new Arbol(col*Obstaculo.SIZE,fila*Obstaculo.SIZE);
						arboles.getChildren().add(obstaculo.getForma());
						obstaculos.add(obstaculo);
						break;
					case '4':
						obstaculo = new Agua(col*Obstaculo.SIZE,fila*Obstaculo.SIZE);
						pisadasAgua.getChildren().add(obstaculo.getForma());
						obstaculos.add(obstaculo);
						break;
					case '8':
						obstaculo = new AguilaNasi(col*Obstaculo.SIZE,fila*Obstaculo.SIZE);
						pisadasAgua.getChildren().add(obstaculo.getForma());
						break;
					}					
				}
				
				fila++;
				cadena=b.readLine();
			}
			
			b.close();
			createObstaculo();
			huboCambio = false;
			
		}catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	protected void guardarMapa(File file){
		try{
		    PrintWriter writer = new PrintWriter(file.getAbsolutePath(), "UTF-8");
		    writer.println(this);
		    writer.close();
		    huboCambio = false;
		} catch (IOException e) {}
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
