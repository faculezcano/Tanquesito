import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;


public class Particle implements Runnable {
	
	protected Shape particula;
	
	public Particle(Shape s){
		particula = s;
	}

	@Override
	public void run() {
		try {
			for(int i = 0; i<10 ; i++){
				if(particula.getOpacity()>0)
					Platform.runLater(new Runnable(){
						@Override
						public void run() {
							// TODO Auto-generated method stub
							particula.setOpacity(particula.getOpacity()-0.06);
							Circle c = (Circle)particula;
							//c.setFill(Color.color(1,0.3+(0.6-c.getOpacity()),0.3*(0.6-c.getOpacity())));
							c.setRadius(c.getRadius()-0.4);
						}
						
					});
				else
					break;
					
				Thread.sleep(100);
				
				
			}
			if(particula.getParent() != null){
				Platform.runLater(new Runnable(){

					@Override
					public void run() {
						Group g = (Group)particula.getParent();
						g.getChildren().remove(particula);
					}
					
				});
			}
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
