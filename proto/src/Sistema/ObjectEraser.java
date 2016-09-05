package Sistema;

import java.util.LinkedList;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

public class ObjectEraser implements Runnable {

	protected Mapa map;
	protected LinkedList<Object> objs;
	protected MediaPlayer roturaSound;
	
	public ObjectEraser(LinkedList<Object> objs, Mapa map){
		this.objs = objs;
		this.map = map;
	}
	
	@Override
	public void run() {
		for(Object obj : objs){
			try{
				roturaSound = new MediaPlayer(new Media("file:///"+ System.getProperty("user.dir").replace('\\', '/') +"/src/audio/wood2.wav"));
				
				roturaSound.setStartTime(Duration.millis(0));
				roturaSound.setStopTime(Duration.seconds(1));
				
				
				
				roturaSound.play();
				
				map.remove((ObjetoDinamico)obj);
			}catch(ClassCastException e){
				map.remove((ObjetoEstatico)obj);
			}
		}
	}

}
