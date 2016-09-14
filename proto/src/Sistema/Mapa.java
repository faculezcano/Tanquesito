package Sistema;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentLinkedQueue;

import javafx.application.Platform;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.scene.shape.Path;
import javafx.scene.shape.Shape;

import Objetos.Ladrillo;

public class Mapa {
	
	protected MotionManager motMan;
	protected ConcurrentLinkedQueue<ObjetoEstatico> objs;
	protected Group g;
	
	public Mapa(int bloquesX, int bloquesY, Group g){
		motMan = new MotionManager(this);
		objs = new ConcurrentLinkedQueue<ObjetoEstatico>();
		this.g = g;
		Thread thMotMan = new Thread(motMan);
		thMotMan.setDaemon(true);
		thMotMan.start();
	}
	
	public void generarMapa(){
		
		//TODO borrar testeo
		Group gr = new Group();
		for(int i = 0; i<8; i++)
			for(int j = 0; j<8; j++){
				Ladrillo l = new Ladrillo();
				gr.getChildren().add(l.getForma());
				objs.add(l);
				//add(l);
				l.setPosition(new Point2D(128+i*32,128+j*32));
			}
		DropShadow ds = new DropShadow();
		ds.setRadius(10);
		gr.setEffect(ds);
		g.getChildren().add(gr);
		
		
		//TODO escribir algoritmo de generacion de mapa
	}
	
	
	public void cargarMapa(String archivo) throws InvalidMapException, IOException, FileNotFoundException{
		//TODO cargar un mapa de un archivo
		
		Group gr = new Group();
		
		FileReader f = new FileReader(archivo);
		BufferedReader b = new BufferedReader(f);
		
		int fila = 0;
		String cadena = b.readLine();
		
		while(cadena != null){
			for(int col=0;col<cadena.length();col++){
				System.out.print(cadena.charAt(col));
				if (cadena.charAt(col) == '1') {
					Ladrillo l = new Ladrillo();
					gr.getChildren().add(l.getForma());
					objs.add(l);
					l.setPosition(new Point2D(0+col*32,0+fila*32));
				}
					
			}
			System.out.println();
			fila++;
			cadena=b.readLine();
		}
		b.close();
		
		DropShadow ds = new DropShadow();
		ds.setRadius(10);
		gr.setEffect(ds);
		g.getChildren().add(gr);
		
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
		objs.add(obj);
		obj.addToGroup(g);
	}
	
	public void remove(ObjetoEstatico obj){
		objs.remove(obj);
		Group parent = (Group)obj.getForma().getParent();
		parent.getChildren().remove(obj.getForma());
	}
	
	public void remove(ObjetoDinamico obj){
		obj.remove(g);
	}

}