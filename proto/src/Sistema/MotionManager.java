package Sistema;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentLinkedQueue;

import sun.misc.Queue;

import javafx.application.Platform;
import javafx.geometry.Point2D;

public class MotionManager implements Runnable{
	
	protected ConcurrentLinkedQueue<ObjetoDinamico> objs;
	protected int fps = 30;
	protected long time;
	protected Mapa map;
	
	public MotionManager(Mapa map){
		this.map = map;
		objs = new ConcurrentLinkedQueue<ObjetoDinamico>();
	}
	
	public void addObject(ObjetoDinamico o){
		objs.add(o);
	}

	public void run() {
		double deltaT = 1.0/fps;
		
		while(true){
			time = System.nanoTime();
			
			LinkedList<Object> borrar = new LinkedList<Object>();
			LinkedList<ObjetoDinamico> dinBorrar = new LinkedList<ObjetoDinamico>();

			for(ObjetoDinamico obj : objs){
				LinkedList<ObjetoEstatico> statics = map.colisiones(obj);
				ObjetoDinamico auxD = null;
				obj.setColision(auxD);
				ObjetoEstatico auxE = null;
				obj.setColision(auxE);
				for(ObjetoEstatico oE : statics){
					obj.setColision(oE);
					if(obj.toString() == "Bala"){
						borrar.add(obj);
						dinBorrar.add(obj);
						borrar.add(oE);
					}
				}
			}
			
			for(ObjetoDinamico oD : dinBorrar){
				objs.remove(oD);
			}
			
			Platform.runLater(new ObjectEraser(borrar,map));
			
			Platform.runLater(new Runnable(){

				@Override
				public void run() {
					for(ObjetoDinamico obj : objs){
						if(!obj.getColision()){
							Point2D vel = obj.getVelocidad();
							obj.setPosition(obj.getPosition().add(vel));
						}else{
							obj.setPosition(Math.round(obj.getPosition().getX()/32)*32, Math.round(obj.getPosition().getY()/32)*32);
							ObjetoDinamico auxD = null;
							obj.setColision(auxD);
							ObjetoEstatico auxE = null;
							obj.setColision(auxE);
						}
					}
				}
				
			});
			
			
			try {
				while((System.nanoTime()-time)<=deltaT*1000000000){
					Thread.sleep(10);
				}
			} catch (InterruptedException e) {}
		}
	}

}
