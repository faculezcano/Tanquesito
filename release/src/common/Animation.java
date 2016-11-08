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

    private Image[] sequence;
    
    protected Shape s;
    protected Image current;
    
    EventHandler<ActionEvent> finishEvent;
    
    public Animation(Shape s){
    	this.s = s;
    }

    public Animation( Shape s,LinkedList<Image> sequence, double durationMs) {
    	this.s = s;
        init( sequence, durationMs);
    }
    
    public Animation( Shape s,Image[] sequence, double durationMs) {
    	this.s = s;
        init( sequence, durationMs);
    }

    protected void init( LinkedList<Image> sequence, double durationMs) {
    	Image[] seqImg = new Image[sequence.size()];
    	for(int i = 0; i<sequence.size(); i++){
    		seqImg[i]=sequence.get(i);
    	}
    	init(seqImg,durationMs);
    }
    
    protected void init(Image[] sequence, double durationMs){
    	
    	this.sequence = sequence;
        this.count = sequence.length;

        setCycleCount(1);
        setCycleDuration(Duration.millis(durationMs));
        setInterpolator(Interpolator.LINEAR);
        
        if(sequence!=null && s != null && sequence.length > 0){
        	current = sequence[0];
        	pintar();
        }
        
    }

    protected void interpolate(double k) {

        final int index = Math.min((int) Math.floor(k * count), count - 1);
        if (index != lastIndex) {
            if(s!=null){
            	current = sequence[index];
            	pintar();
            }
            lastIndex = index;
        }
        if(index == sequence.length-1 && finishEvent != null)
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