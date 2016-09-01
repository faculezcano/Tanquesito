import java.util.Random;

import javafx.geometry.Point2D;


public class Npc implements Runnable {
	
	protected ObjetoDinamico obj;
	
	public Npc(ObjetoDinamico tanque){
		obj = tanque;
	}

	@Override
	public void run() {
		Random r = new Random();
		int dir = r.nextInt(4);
		while(true){
			switch(dir){
			case 0:
				obj.setVelocidad(new Point2D(1,0));
				break;
			case 1:
				obj.setVelocidad(new Point2D(-1,0));
				break;
			case 2:
				obj.setVelocidad(new Point2D(0,1));
				break;
			default:
				obj.setVelocidad(new Point2D(0,-1));				
			}
			dir = r.nextInt(4);
			
			try {
				Thread.sleep(r.nextInt(1000)+200);
			} catch (InterruptedException e) {}
		}
	}

}
