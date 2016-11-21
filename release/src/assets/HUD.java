package assets;

import assets.tanques.Jugador;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
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
	protected Rectangle resistencia;
	protected HBox balas = new HBox();
	
	protected Node node;
	
	public HUD(){
		super();
		resistencia = new Rectangle(0,0,0,5);
		resistencia.setFill(Color.LIGHTGREEN);
		VBox vidaResistencia = new VBox();
		vidaResistencia.getChildren().addAll(vida,resistencia);
		vidaResistencia.setMinWidth(100);
		HBox boxNivel = new HBox();
		boxNivel.getChildren().addAll(new Text("Nivel: "), lvl);
		HBox boxPuntos = new HBox();
		boxPuntos.getChildren().addAll(new Text("Puntos: "),puntos);
		getChildren().addAll(vidaResistencia,boxNivel,boxPuntos,balas);
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
		}
	}
	
	private void updateVida(){
		if(vidaAnterior != j.getVidas()){
			vida.getChildren().clear();
			for(int i = 0; i < j.getVidas(); i ++){
				Rectangle rVida = new Rectangle(0,0,16,16);
				rVida.setFill(Color.RED);
				vida.getChildren().add(rVida);
			}
			vidaAnterior = j.getVidas();
		}
	}
	
	private void updateResistencia(){
		if(resAnterior != j.getResistencia()){
			resistencia.setWidth(100.0*(double)j.getResistencia()/j.getNivel().getGolpesResiste());
			resAnterior = j.getResistencia();
		}
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
				Rectangle rBala = new Rectangle(0,0,16,16);
				rBala.setFill(Color.GRAY);
				balas.getChildren().add(rBala);
			}
			balasAnterior = balasDisponibles;
		}
	}
	
}
