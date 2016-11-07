package common;

import java.util.LinkedList;

import javafx.animation.Interpolator;
import javafx.animation.Transition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Shape;
import javafx.util.Duration;

public class Animation extends Transition {

    private int count;

    private int lastIndex;

    private LinkedList<Image> sequence;
    
    protected Shape s;
    protected Image current;
    
    EventHandler<ActionEvent> finishEvent;
    
    public Animation(Shape s){
    	this.s = s;
    }

    public Animation( Shape s,LinkedList<Image> sequence, double durationMs) {
    	this.s = s;
        init( sequence, durationMs);
        if(sequence.size()>0){
        	s.setFill(new ImagePattern(sequence.getFirst()));
        }
    }

    protected void init( LinkedList<Image> sequence, double durationMs) {
        this.sequence = sequence;
        this.count = sequence.size();

        setCycleCount(1);
        setCycleDuration(Duration.millis(durationMs));
        setInterpolator(Interpolator.LINEAR);

    }
    
    protected void init(Image[] sequence, double durationMs){
    	LinkedList<Image> lista = new LinkedList<Image>();
    	for(int i = 0; i<sequence.length; i++){
    		lista.add(sequence[i]);
    	}
    	init(lista,durationMs);
    }

    protected void interpolate(double k) {

        final int index = Math.min((int) Math.floor(k * count), count - 1);
        if (index != lastIndex) {
            if(s!=null){
            	current = sequence.get(index);
            	pintar();
            }
            lastIndex = index;
        }
        if(index == sequence.size()-1 && finishEvent != null)
        	finishEvent.notifyAll();

    }
    
    public Shape getShape(){
    	return s;
    }
    
    public void onFinish(EventHandler<ActionEvent> e){
    	finishEvent = e;
    }
    
    protected void pintar(){
    	Platform.runLater(new Runnable(){

			@Override
			public void run() {
				s.setFill(new ImagePattern(current));
			}
    		
    	});
    }

}