package common;

import java.awt.image.BufferedImage;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.shape.Shape;

public class AnimatedGif extends Animation {

    public AnimatedGif(Shape s, String filename, double durationMs) {
    	super(s);
        GifDecoder d = new GifDecoder();
        d.read( getClass().getClassLoader().getResourceAsStream(filename));

        Image[] sequence = new Image[ d.getFrameCount()];
        for( int i=0; i < d.getFrameCount(); i++) {

            WritableImage wimg = null;
            BufferedImage bimg = d.getFrame(i);
            sequence[i] = SwingFXUtils.toFXImage( bimg, wimg);

        }
        
        if(sequence.length > 0){
        	current = sequence[0];
        	pintar();
    	}

        super.init( sequence, durationMs);
    }
    
    

}
