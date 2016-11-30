package assets;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import assets.tanques.Jugador;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class HUD extends HBox{
	protected Jugador j;
	protected int vidaAnterior = -1;
	protected int resAnterior = -1;
	protected String lvlAnterior = "-1";
	protected int balasAnterior = -1;
	protected int puntosAnterior = -1;
	
	protected HBox vida = new HBox();
	protected Text lvl = new Text();
	protected Text puntos = new Text();
	protected Text tiempo = new Text();
	protected Rectangle resistencia;
	protected HBox balas = new HBox();
	
	protected Node node;
	
	protected Date inicio = new Date();
	
	protected ImagePattern imgVida = new ImagePattern(new Image(getClass().getClassLoader().getResourceAsStream("img/vida.png")));
	protected ImagePattern imgBala = new ImagePattern(new Image(getClass().getClassLoader().getResourceAsStream("img/bala.png")));
	
	public HUD(){
		super();
		resistencia = new Rectangle(0,0,0,5);
		resistencia.setFill(Color.LIGHTGREEN);
		Rectangle resistenciaBack = new Rectangle(0,0,58,5);
		resistenciaBack.setFill(Color.DARKGRAY);
		
		Group groupRes = new Group();
		groupRes.getChildren().addAll(resistenciaBack,resistencia);
		
		vida.setSpacing(5);
		balas.setSpacing(5);
		balas.setPadding(new Insets(3,0,0,0));
		VBox vidaResistencia = new VBox(5);
		vidaResistencia.getChildren().addAll(vida,groupRes);
		//vidaResistencia.setMinWidth();
		VBox boxNivel = new VBox();
		Text lblNivel = new Text("Nivel");
		lblNivel.setStroke(Color.WHITE);
		lvl.setStroke(Color.WHITE);
		boxNivel.getChildren().addAll(lblNivel, lvl);
		VBox boxPuntos = new VBox();
		Text lblPuntos = new Text("Puntos");
		lblPuntos.setStroke(Color.WHITE);
		puntos.setStroke(Color.WHITE);
		boxPuntos.getChildren().addAll(lblPuntos,puntos);
		
		tiempo.setStroke(Color.WHITE);
		
		getChildren().addAll(vidaResistencia,boxNivel,boxPuntos,balas,tiempo);
		setSpacing(20);
		setAlignment(Pos.CENTER_LEFT);
	}
	
	public void setJugador (Jugador j){
		this.j = j;
	}
	
	public void update(){
		if(j!=null){
			updateVida();
			updateResistencia();
			updateLvl();
			updatePuntos();
			updateBalas();
			updateTiempo();
		}
	}
	
	private void updateVida(){
		if(vidaAnterior != j.getVidas()){
			vida.getChildren().clear();
			for(int i = 0; i < j.getVidas(); i ++){
				Rectangle rVida = new Rectangle(0,0,16,16);
				rVida.setFill(imgVida);
				vida.getChildren().add(rVida);
			}
			vidaAnterior = j.getVidas();
		}
	}
	
	private void updateResistencia(){
		//if(resAnterior != j.getResistencia()){
			resistencia.setWidth(58*(double)j.getResistencia()/j.getNivel().getGolpesResiste());
			//resAnterior = j.getResistencia();
		//}
	}
	
	private void updatePuntos(){
		if(puntosAnterior != j.getPuntos()){
			puntos.setText(String.valueOf(j.getPuntos()));
			puntosAnterior = j.getPuntos();
		}
	}
	
	private void updateLvl(){
		if(!lvlAnterior.equals(j.getNivel().toString())){
			lvl.setText(j.getNivel().toString());
			lvlAnterior = j.getNivel().toString();
		}
	}
	
	private void updateBalas(){
		int balasDisponibles = j.getNivel().getDisparosSimul()-j.MisBalas().size();
		if(balasAnterior != balasDisponibles){
			balas.getChildren().clear();
			for(int i = 0; i < balasDisponibles; i ++){
				Rectangle rBala = new Rectangle(0,0,20,10);
				
				rBala.setFill(imgBala);
				balas.getChildren().add(rBala);
			}
			balasAnterior = balasDisponibles;
		}
	}
	
	private void updateTiempo(){
		long diferencia = (new Date()).getTime() - inicio.getTime();
		int segs = (int)TimeUnit.MILLISECONDS.toSeconds(diferencia);
		String seg = ((segs/60 < 10) ? "0" : "") + segs/60;
		String min = ((segs%60 < 10) ? "0" : "") + segs%60;
		tiempo.setText(seg+":"+min);
	}
	
}
