package Sistema;

import java.util.LinkedList;

import javafx.application.Platform;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Path;
import javafx.scene.shape.Shape;

import Objetos.Ladrillo;

public class Mapa {
	
	protected MotionManager motMan;
	protected LinkedList<ObjetoEstatico> objs;
	protected Group g;
	
	public Mapa(int bloquesX, int bloquesY, Group g){
		motMan = new MotionManager(this);
		objs = new LinkedList<ObjetoEstatico>();
		this.g = g;
		Thread thMotMan = new Thread(motMan);
		thMotMan.setDaemon(true);
		thMotMan.start();
	}
	
	public void generarMapa(){
		
		//TODO borrar testeo
		for(int i = 0; i<8; i++)
			for(int j = 0; j<8; j++){
				Ladrillo l = new Ladrillo();
				add(l);
				l.setPosition(new Point2D(128+i*32,128+j*32));
			}
				
		
		
		//TODO escribir algoritmo de generacion de mapa
	}
	
	public void cargarMapa(String archivo) throws InvalidMapException{
		//TODO cargar un mapa de un archivo
	}
	
	public LinkedList<ObjetoEstatico> colisiones(ObjetoDinamico obj){
		LinkedList<ObjetoEstatico> colisiones = new LinkedList<ObjetoEstatico>();
		
		for(ObjetoEstatico objEstatico : objs){
			Shape interseccion = Shape.intersect(objEstatico.getForma(), obj.getForma());
			if(!interseccion.getBoundsInLocal().isEmpty())
				colisiones.add(objEstatico);
		}
		
		return colisiones;
	}
	
	public void add(ObjetoDinamico obj){
		motMan.addObject(obj);
		obj.addToGroup(g);
	}
	
	public void add(ObjetoEstatico obj){
		objs.addLast(obj);
		obj.addToGroup(g);
	}
	
	public void remove(ObjetoEstatico obj){
		objs.remove(obj);
		g.getChildren().remove(obj.getForma());
	}
	
	public void remove(ObjetoDinamico obj){
		obj.remove(g);
	}

}