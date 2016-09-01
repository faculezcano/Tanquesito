import java.util.LinkedList;

import javafx.application.Platform;
import javafx.geometry.Point2D;

public class MotionManager implements Runnable{
	
	protected LinkedList<ObjetoDinamico> objs = new LinkedList<ObjetoDinamico>();
	
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
		while(true){
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
				Thread.sleep(10);
			} catch (InterruptedException e) {}
		}
	}

}
