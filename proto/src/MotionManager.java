import java.util.LinkedList;

import javafx.application.Platform;
import javafx.geometry.Point2D;

public class MotionManager implements Runnable{
	
	protected LinkedList<ObjetoDinamico> objs = new LinkedList<ObjetoDinamico>();
	protected int fps = 30;
	protected long time;
	
	public MotionManager(){
		
	}
	
	public void addObject(ObjetoDinamico o){
		objs.addLast(o);
	}

	public void run() {
		/*for(ObjetoDinamico obj : objs){
			ObjetoDinamico pos = obj;
			while(pos != objs.getLast()){
				if()
			}
		}*/
		double deltaT = 1.0/fps;
		
		while(true){
			time = System.nanoTime();
			Platform.runLater(new Runnable(){

				@Override
				public void run() {
					// TODO Auto-generated method stub
					for(ObjetoDinamico obj : objs){
						Point2D vel = obj.getVelocidad();
						obj.setPosition(obj.getPosition().add(vel));
						
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
