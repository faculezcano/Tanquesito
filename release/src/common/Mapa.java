package common;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import java.io.InputStreamReader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import com.sun.webkit.network.data.Handler;

import javafx.animation.AnimationTimer;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.CacheHint;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.util.Duration;
import assets.*;
import assets.obstaculos.*;
import assets.powerUps.*;
import assets.tanques.*;

/**
 * 
 */
@SuppressWarnings("unused")
public class Mapa {
	
	protected ConcurrentLinkedQueue<TanqueEnemigo> enemigos;
    protected ConcurrentLinkedQueue<Bullet> bullets;
    protected ConcurrentLinkedQueue<PowerUp> powerUps;
    protected ConcurrentLinkedQueue<Obstaculo> obstaculos;
    protected ConcurrentLinkedQueue<Obstaculo> limites;
    protected ConcurrentLinkedQueue<Point2D> posicionesLibres;
    
    protected ConcurrentLinkedQueue<Node> aAgregar;
    
    protected Jugador jugador;
    protected Obstaculo aguila;
    protected Tanque enemigo;
    
    protected double dificul = 4; 
    
    protected Group pisadasAgua;
    protected Group tanques;
    protected Group balasObstaculos;
    protected Group arboles;
    protected Group powerups;
    
    protected GifDecoder gifDecoder = new GifDecoder();
    protected LinkedList<Image> expT;
    protected Image[] aniDisparo;
    protected Image[] aniImpactoBala;
    
    protected boolean enemigosCongelados=false;
    
    protected Random rand = new Random();
    
    protected int habilitarPU = 0;
    
    protected AnimationTimer anim;
    protected Thread threadColisiones;
    
    protected EventHandler<ActionEvent> eventoPerder;
    protected EventHandler<ActionEvent> eventoGanar;
   
    
	/**
     * @param cantX 
     * @param cantY 
     * @param g
     */
    public Mapa(int cantX, int cantY, Group g) {
        pisadasAgua = new Group();
        tanques = new Group();
        balasObstaculos = new Group();
        arboles = new Group();
        powerups = new Group();
        
        g.getChildren().addAll(pisadasAgua, tanques, balasObstaculos, arboles, powerups );
        
        enemigos = new ConcurrentLinkedQueue<TanqueEnemigo>();
        bullets = new ConcurrentLinkedQueue<Bullet>();
        obstaculos = new ConcurrentLinkedQueue<Obstaculo>();
        powerUps = new ConcurrentLinkedQueue<PowerUp>();
        limites = new ConcurrentLinkedQueue<Obstaculo>();
        posicionesLibres = new ConcurrentLinkedQueue<Point2D>();
        aAgregar = new ConcurrentLinkedQueue<Node>();
        
        //startColisiones();
        expT = new LinkedList<Image>() ;
        try {
	        File carpeta = new File("src/img/ExplosionSimpleProto1/");
	        File[] imgs = carpeta.listFiles();
	        Arrays.sort(imgs);
	        for (File img:imgs){
	        	expT.addLast(new Image(new FileInputStream(img)));
			} 
	    }
        catch (FileNotFoundException | NullPointerException e) {
			e.printStackTrace();
		}
       
        
        aniDisparo = cargarGif("img/explo.gif");
        aniImpactoBala = cargarGif("img/explo3.gif");
        
//        Thread gc = new Thread(new Runnable(){
//
//			@Override
//			public void run() {
//				while(true){
//					try {
//						Thread.sleep(3000);
//						System.gc();
//					} catch (InterruptedException e) {}
//				}
//			}});
//        gc.setDaemon(true);
//        gc.start();
        
    }
    
    public static double distancia(double x1, double y1, double x2, double y2){
    	return Math.sqrt(Math.pow(x1-x2, 2)+Math.pow(y1-y2, 2));
    }
    
    public Image[] cargarGif(String filename){
    	gifDecoder.read( getClass().getClassLoader().getResourceAsStream(filename));

        Image[] sequence = new Image[ gifDecoder.getFrameCount()];
        for( int i=0; i < gifDecoder.getFrameCount(); i++) {

            WritableImage wimg = null;
            BufferedImage bimg = gifDecoder.getFrame(i);
            sequence[i] = SwingFXUtils.toFXImage( bimg, wimg);

        }
        return sequence;
    }
    
    public TanqueEnemigo crearEnemigo(){
    	TanqueEnemigo enemigo=new TanqueBasico(this,400,150);
    	addEnemigo(enemigo);
    	//g.getChildren().add(enemigo.getForma());
    	
    	return enemigo;
    	
    }
    
    public void stopColisiones(){
    	anim.stop();
    	threadColisiones.interrupt();
    }
    
    /*protected boolean colisiona(Shape s1, Shape s2){
    	Shape interseccion = Shape.intersect(s1, s2);
    	return !interseccion.getBoundsInLocal().isEmpty();
    	//return s1.getBoundsInParent().intersects(s2.getBoundsInParent());
    }*/
    
    protected boolean colisionaShape(Shape s1, Shape s2){
    	//Shape interseccion = Shape.intersect(s1, s2);
    	//return !interseccion.getBoundsInLocal().isEmpty();
    	return false;
    	//return s1.getBoundsInParent().intersects(s2.getBoundsInParent());
    }
    
    public double distancia(ObjetoEstatico oe, ObjetoDinamico od){
    	Rectangle r = (Rectangle)oe.getForma();
    	return ObjetoDinamico.distancia(r.getX(),r.getY(),od);
    }
    
    public boolean colisiona(int x1,int y1, int w1, int h1, int x2, int y2, int w2, int h2){
    	return (x1 < x2 + w2 &&
   			   x1 + w1 > x2 &&
   			   y1 < y2 + h2 &&
   			   h1 + y1 > y2);
    }
    
    public boolean colisiona(Rectangle r1, Rectangle r2){
    	return ((int)r1.getX() < (int)r2.getX() + (int)r2.getWidth() &&
 			   (int)r1.getX() + (int)r1.getWidth() > (int)r2.getX() &&
 			   (int)r1.getY() < (int)r2.getY() + (int)r2.getHeight() &&
 			   (int)r1.getHeight() + (int)r1.getY() > (int)r2.getY());
    }
    
    public boolean colisiona(int x, int y, int width, int height,Rectangle r2){
    	return (x < (int)r2.getX() + (int)r2.getWidth() &&
  			   x + width > (int)r2.getX() &&
  			   y < (int)r2.getY() + (int)r2.getHeight() &&
  			   height + y > (int)r2.getY());
    }
    
    public boolean colisiona(int x, int y, int width, int height, ObjetoDinamico od){
    	/*if( ObjetoDinamico.distancia(x,y,od)> 96){
    		noentran++;
    		return false;
    	}*/

    	Rectangle r2 = (Rectangle)od.getForma();
    	
    	return colisiona(x,y,width,height,r2);
    }
    
    public boolean colisiona(int x, int y, int width, int height, ObjetoEstatico oe){

    	
    	Rectangle r1 = (Rectangle)oe.getForma();
    	
    	return colisiona(x,y,width,height,r1);
    }
    
    public boolean colisiona(ObjetoEstatico o, Tanque t){
    	Rectangle r = (Rectangle)o.getForma();
    	return colisiona((int)r.getX(),(int)r.getY(),(int)r.getWidth(),(int)r.getHeight(),(int)t.getX(),(int)t.getY(),Tanque.SIZE,Tanque.SIZE);
    }
    
    public boolean colisiona(ObjetoEstatico o, Bullet b){
    	Rectangle r = (Rectangle)o.getForma();
    	return colisiona((int)r.getX(),(int)r.getY(),(int)r.getWidth(),(int)r.getHeight(),(int)b.getX(),(int)b.getY(),(int)Bullet.SIZE.getX(),(int)Bullet.SIZE.getY());
    }
    
    public boolean colisiona(Bullet b, Tanque t){
    	return colisiona((int)b.getX(),(int)b.getY(),(int)Bullet.SIZE.getX(),(int)Bullet.SIZE.getY(),(int)t.getX(),(int)t.getY(),Tanque.SIZE,Tanque.SIZE);
    }
    
    public boolean colisiona(Tanque t1, Tanque t2){
    	return colisiona((int)t1.getX(),(int)t1.getY(),Tanque.SIZE,Tanque.SIZE,(int)t2.getX(),(int)t2.getY(),Tanque.SIZE,Tanque.SIZE);
    }
    
    int entran = 0;
	int noentran = 0;
	
	protected Object monitor = new Object();
	protected boolean colisionOk = false;
    
    public void startColisiones(){
    	
    	Runnable colisiones = (new Runnable(){

			@Override
			public void run() {
				while(true){
				
				
				/*try {
					synchronized(monitor){
						monitor.wait();
					}
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}*/
				
				colisionOk = true;
				
				try {
					while(colisionOk == true)
						Thread.sleep(1);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
				}
				}
			}
    		
    	});
    	
    	
    	
    	threadColisiones = new Thread(colisiones);
    	threadColisiones.setDaemon(true);
    	threadColisiones.setName("colisiones");
    	//threadColisiones.start();
    	
    	anim = (new AnimationTimer(){
    		
    		/*final int fps = 30;
			double deltaT = 1.0/fps;*/
			
			int libreX = rand.nextInt(43);
			int libreY = rand.nextInt(23);
			
			int x,y,w,h,xPU,yPU;
			
			boolean esLibre;
			boolean librePU;
			
			long time = System.nanoTime();

			@Override
			public void handle(long now) {
				
				
				
				/*Thread t = new Thread(colisiones);
				t.setDaemon(true);
				t.start();*/
				/*synchronized(monitor){
					monitor.notify();
				}*/
				
				controlColisiones();
				colisionOk = true;
					
				if(colisionOk){
					double deltaT = (now - time)/50000000.0;
					//double deltaTPU=(now - time)/(40000000.0*jugador.getNivel().getNum()); // tiempo entre creacion de power up
					if(habilitarPU >= 4){
						xPU=(rand.nextInt(43)*Obstaculo.SIZE);
						yPU=(rand.nextInt(23)*Obstaculo.SIZE);
					}
					librePU=true;
					time = now;
					
					esLibre = true;
					x=libreX*Obstaculo.SIZE;
					y=libreY*Obstaculo.SIZE;
					
					while(!aAgregar.isEmpty()){
						balasObstaculos.getChildren().add(aAgregar.poll());
					}
					
					for(Obstaculo o : obstaculos){
						
						if(esLibre && colisiona(x,y,Tanque.SIZE,Tanque.SIZE,o))
							esLibre=false;
						if(librePU && colisiona(xPU,yPU,PowerUp.SIZE,PowerUp.SIZE,o)){
							librePU=false;
						}
						
					}

					for(Bullet b: bullets){
						b.mover(deltaT);
					}
					
					jugador.mover(deltaT);
		
					if(!enemigosCongelados){
						for(TanqueEnemigo en: enemigos){
							if(esLibre && ObjetoDinamico.distancia(x, y,en) < 240)
								esLibre=false;
							if(librePU && ObjetoDinamico.distancia(xPU, yPU,en) < 240){
								librePU=false;
							}
							en.mover(deltaT);
						}
					}
					
					if(esLibre && jugador != null && ObjetoDinamico.distancia(x, y,jugador) < 240)
						esLibre=false;
					if(librePU && jugador != null && ObjetoDinamico.distancia(xPU, yPU,jugador) < 240)
						esLibre=false;
					
					if(esLibre){
						addEnemigoAleatorio(x,y);
					}
					if(librePU){
						addPowerUPAleatorio(xPU,yPU);
					}
					
					if(enemigos.size() <= 5){
						libreX = rand.nextInt(43);
						libreY = rand.nextInt(23);
					}
					colisionOk = false;
				}

			}});

    	anim.start();
    }
    
    protected void controlColisiones(){
    	for(Bullet b:bullets){
			
			colisionesTanquesBullet(b);
			
			colisionesObstBullet(b);
		}
		
		
		for(PowerUp pu: powerUps){
			if(colisiona(pu,jugador)){
				pu.colisionaTanque(jugador);
				eliminarPowerUp(pu);
			}
		}
		
		for(Obstaculo o : obstaculos){
			
			if(colisiona(o,jugador))
				o.colisionaTanque(jugador);
			
			for(TanqueEnemigo ene: enemigos){
				
				
				if(colisiona(o,ene)){
					double antesX = ene.getX(),antesY = ene.getY();
					ene.setTiroLimpio(false);
					o.colisionaTanque(ene);
					ene.setTiroLimpio(false);
					if((antesX != ene.getX() || antesY != ene.getY()) &&colisiona(o,ene))
						eliminarEnemigo(ene);
				}

				if(colisiona(jugador,ene)){
					ene.colisiona();
					jugador.colisiona();
				}
			}
		}
		
		if(aguila!=null && colisiona(aguila,jugador)){
			aguila.colisionaTanque(jugador);
		}
		
		for(TanqueEnemigo ene1: enemigos){
			if(colisiona(aguila,ene1)){
				aguila.colisionaTanque(ene1);
			}
			for(TanqueEnemigo ene2: enemigos){
				if(ene1 != ene2 && colisiona(ene1,ene2)){
					ene1.colisiona();
					ene2.colisiona();
				}
			}
		}
    }
    
    public void setOnPerder(EventHandler<ActionEvent> e){
    	eventoPerder = e;
    }
    
    protected void perder(){
    	if(eventoPerder != null){
    		eventoPerder.handle(new ActionEvent(this,null));
    	}
    	
    	
    	
    }
    
    protected void ganar(){
    	if(eventoGanar != null){
    		eventoGanar.handle(new ActionEvent(this,null));
    	}
    }
    
    private void colisionesObstBullet(Bullet b){
    	LinkedList<Obstaculo> colisionesBala = new LinkedList<Obstaculo>();
    	for(Obstaculo o: obstaculos){
			//if (colisiona(b.getForma(), o.getForma())){
    		if (colisiona(o,b)){
					colisionesBala.add(o);
			}		
		
		}
    	
    	if(aguila!=null && colisiona(aguila,b)){
    		if(!jugador.MisBalas().contains(b)){
    			perder();
    			colisionesBala.add(aguila);
    		}
    	}
		
		while (!colisionesBala.isEmpty()){
			Obstaculo ob = colisionesBala.remove();
			
			ob.colisionaBala(b);
			
			if (ob.GetVida() == 0) {
				removeObstaculo(ob);
			}
			
			if (b.getResistencia() == 0) {
				Platform.runLater(new SyncRemover(b.getForma(),balasObstaculos));
				bullets.remove(b);
			}
		}
    }
    
    protected void addPowerUPAleatorio(double xPU, double yPU){
    	if(habilitarPU==4){
    		
    		
    		Rectangle rect= new Rectangle(xPU-((PowerUp.SIZE*17)/2)+12,yPU-((PowerUp.SIZE*17)/2)+12,PowerUp.SIZE*17,PowerUp.SIZE*17);
    		rect.setFill(new ImagePattern (new Image(getClass().getClassLoader().getResourceAsStream("img/destello.png"))));
    		
    		rect.setCache(true);
    		rect.setCacheHint(CacheHint.SPEED);
    		powerups.getChildren().add(rect);
    		
    		FadeTransition ft = new FadeTransition(Duration.seconds(1.5), rect);
    	     ft.setFromValue(1.0);
    	     ft.setToValue(0.3);
    	     ft.setCycleCount(5);
    	     ft.setAutoReverse(true);
    	     ft.play();
    	     
    	     ft.onFinishedProperty().set(new EventHandler<ActionEvent>() {
				
				@Override
				public void handle(ActionEvent event) {
					ft.stop();
					Platform.runLater(new SyncRemover(rect,powerups));
		    		powerUps.remove(rect);
					
				}
			});;
    	     
    	     
    	     
    	     
    		int randomPU = rand.nextInt(5);
    		switch(randomPU){
    		
	    		case 0:
	    			PowerUp granada= new PowUpGranade(xPU,yPU,this);
	    			powerups.getChildren().add(granada.getForma());
					powerUps.add(granada);
					break;
	    		
	    		case 1:
	    			PowerUp casco= new PowUpHelm(xPU,yPU,this);
	    			powerups.getChildren().add(casco.getForma());
					powerUps.add(casco);
					break;
	    			
	    		case 2:
	    			PowerUp vida= new PowUpLife(xPU,yPU,this);
	    			powerups.getChildren().add(vida.getForma());
					powerUps.add(vida);
					break;
	    			
	    		case 3:
	    			PowerUp pala= new PowUpShovel(xPU,yPU,this);
	    			powerups.getChildren().add(pala.getForma());
					powerUps.add(pala);
					break;
	    			
	    		case 4:
	    			PowerUp estrella= new PowUpStar(xPU,yPU,this);
	    			powerups.getChildren().add(estrella.getForma());
					powerUps.add(estrella);
					break;
	    			
	    		case 5:
	    			PowerUp tiempo= new PowUPTime(xPU,yPU,this);
	    			powerups.getChildren().add(tiempo.getForma());
					powerUps.add(tiempo);
					break;
    		}
    		
    		
    		habilitarPU=0;
    	}
    }
    
    
    

	int n = 0;
    protected void addEnemigoAleatorio(double x, double y){
    	if(enemigos.size() < 5){
    		TanqueEnemigo t = null; 
    		double lv = rand.nextGaussian() + dificul;
    		
    		//System.out.println("num:"+n+" Lv del enemigo:"+ lv);
    		n++;
    		//lv = Math.abs(lv); 
    		
    		if (lv > 4)
    			lv = 4;
    		else
    			if (lv < 0)
    				lv = 0;
    		
    		if (lv < 1)
    			t = new TanqueBasico(this,x,y);
    		else
    			if (lv < 2)
    				t = new TanqueRapido(this,x,y);
    			else
    				if(lv < 3) 
    					t = new TanquePoder(this,x,y);
    				else
    					if(lv <=4)
    						t = new TanqueBlindado(this,x,y);
    		
    		addEnemigo(t);
    	}
    	
    }
    
    private void colisionesJugadorBullet(Bullet b){
    	if(!(jugador.MisBalas().contains(b))){
    		if(colisiona(b,jugador)){
    			//jugador.afectar();
    			
    			if(!jugador.esInvunerable()){
//	    			int nuevaResistencia=jugador.getResistencia()-b.getResistencia();
//	        		jugador.setResistencia(nuevaResistencia);
    				jugador.afectar();
    				System.out.println("Resistencia del jugador actual:"+jugador.getResistencia());
		    		if(jugador.getResistencia()<=0){
		    			this.matarJugador();
		    		}
    			}
    			
    			b.colisiona();
    			if(b.getResistencia()<=0){
					Platform.runLater(new SyncRemover(b.getForma(),balasObstaculos));
					bullets.remove(b);	
				}
    		}
    	}
    }
    
    public void matarJugador(){
    	jugador.perderVida();
    	jugador.setX(jugador.getXinicial());
		jugador.setY(jugador.getYinicial());
		Invulnerable(5);
		if(jugador.getVidas() == 0){
			perder();
			
		}
    	
    	Rectangle r = new Rectangle(jugador.getX()-64,jugador.getY()-64,128,128);
		Animation ani = new Animation (r,expT,500);
		ani.play();
		
		ani.setOnFinished(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent event) {
				Animation ani = (Animation)event.getSource();
				Group g = (Group)ani.getShape().getParent();
				Platform.runLater(new SyncRemover(ani.getShape(),g));
			}
			
		});
		
//		Platform.runLater(new SyncAdder(r,tanques));
//		if(jugador.getVida()>0){
//			jugador.setX(jugador.getXinicial());
//			jugador.setY(jugador.getYinicial());
//			//jugador.setResistencia(jugador.getResistenciaInicial());
//		}else{
//			jugador=null;
//		}
    }
    
    
    
    private void colisionesTanquesBullet(Bullet b){
    	if(jugador.MisBalas().contains(b)){
			for(TanqueEnemigo tenemigo: enemigos){
				//if(colisiona(tenemigo.getForma(),b.getForma())){
				if(colisiona(b,tenemigo)){
					tenemigo.afectar();
					b.colisiona();
					if(tenemigo.getResistencia()<=0){
						eliminarEnemigo(tenemigo);
						habilitarPU++;
						System.out.println("cantidad de enemigos muertos:"+ habilitarPU);
					}
					if(b.getResistencia()<=0){
						Platform.runLater(new SyncRemover(b.getForma(),balasObstaculos));
						bullets.remove(b);	
					}
				}
			}
		}else //if(colisiona(b.getForma(), jugador.getForma())){
			if(colisiona(b,jugador)){
				//TODO: matar jugador
				if(!jugador.esInvunerable()){
					jugador.afectar();
					if(jugador.getResistencia()==0){
						this.matarJugador();
					}
				
			}
			
			
			b.colisiona();
			if(b.getResistencia() <= 0){
				Platform.runLater(new SyncRemover(b.getForma(),balasObstaculos));
				bullets.remove(b);	
			}
		}
    }

    /**
     * @param o
     */
    public void addEnemigo(TanqueEnemigo o) {
        enemigos.add(o);
        //Platform.runLater(new SyncAdder(o,tanques));
        o.addToGroup(tanques);
        o.setAnimacionDisparo(aniDisparo);
    }
    
    public void eliminarPowerUp(PowerUp p){
    	if(!powerUps.isEmpty()){
    		Platform.runLater(new SyncRemover(p.getForma(),powerups));
    		powerUps.remove(p);
    	}
    }
    
    public void eliminarEnemigo(TanqueEnemigo o){
    	int puntosEnemigo = o.getPuntos();
    	jugador.setPuntos((jugador.getPuntos())+puntosEnemigo);
    	Platform.runLater(new SyncRemover(o,tanques));
    	enemigos.remove(o);
    	System.out.println(jugador.getPuntos());
    	
    	
    	Rectangle r = new Rectangle(o.getX()-40,o.getY()-40,128,128);
		Animation ani = new Animation (r,expT,500);
		ani.play();
		
		//Platform.runLater(new SyncAdder(r,tanques));
		tanques.getChildren().add(r);
		
		
		ani.setOnFinished(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent event) {
				Animation ani = (Animation)event.getSource();
				Group g = (Group)ani.getShape().getParent();
				Platform.runLater(new SyncRemover(ani.getShape(),g));
			}
			
		});
		
    }
    
    public void addBullet(final Bullet b){
    	bullets.add(b);
    	b.setAnimacionImpacto(aniImpactoBala);
    	/*Platform.runLater(new Runnable(){

			@Override
			public void run() {
				balasObstaculos.getChildren().add(b.getForma());
			}
    		
    	});*/
    	//balasObstaculos.getChildren().add(b.getForma());
    	aAgregar.add(b.getForma());
    }

    /**
     * 
     */
    public void generarMapa() {
        // TODO implement here
    }

    /**
     * @param String file
     */
    public void cargarMapa(String archivo) {
		
		try {
			
			
			
			//FileReader f;
			//f = new FileReader(archivo);
			
			posicionesLibres.clear();
			
			InputStreamReader f = new InputStreamReader(getClass().getClassLoader().getResourceAsStream(archivo));
			
			BufferedReader b = new BufferedReader(f);
			
			int fila = -1;
			String cadena = b.readLine();
			//enemigo = enemigos.poll();
			
			while(cadena != null){
				for(int col=-1;col<cadena.length()-1;col++){
					System.out.print(cadena.charAt(col+1));
					Obstaculo obstaculo;
					switch (cadena.charAt(col+1)){
					
					case '9':
						obstaculo = new Block(col*Obstaculo.SIZE,fila*Obstaculo.SIZE);
						balasObstaculos.getChildren().add(obstaculo.getForma());
						obstaculos.add(obstaculo);
						limites.add(obstaculo);
						break;
					case '1':
						obstaculo = new Ladrillo(col*Obstaculo.SIZE,fila*Obstaculo.SIZE);
						balasObstaculos.getChildren().add(obstaculo.getForma());
						obstaculos.add(obstaculo);
						break;
					case '2':
						obstaculo = new Metal(col*Obstaculo.SIZE,fila*Obstaculo.SIZE);
						balasObstaculos.getChildren().add(obstaculo.getForma());
						obstaculos.add(obstaculo);
						break;
					case '3':
						obstaculo = new Arbol(col*Obstaculo.SIZE,fila*Obstaculo.SIZE);
						arboles.getChildren().add(obstaculo.getForma());
						obstaculos.add(obstaculo);
						break;
					case '4':
						obstaculo = new Agua(col*Obstaculo.SIZE,fila*Obstaculo.SIZE);
						pisadasAgua.getChildren().add(obstaculo.getForma());
						obstaculos.add(obstaculo);
						break;
					case '8':
						if(aguila == null){
							obstaculo = new AguilaNasi(col*Obstaculo.SIZE,fila*Obstaculo.SIZE);
							pisadasAgua.getChildren().add(obstaculo.getForma());
							aguila = obstaculo;
						}
						break;
					case 'g':
						PowerUp granada= new PowUpGranade(col*Obstaculo.SIZE,fila*Obstaculo.SIZE,this);
						powerups.getChildren().add(granada.getForma());
						powerUps.add(granada);
						break;
					case 'v':
						PowerUp star= new PowUpStar(col*Obstaculo.SIZE,fila*Obstaculo.SIZE,this);
						powerups.getChildren().add(star.getForma());
						powerUps.add(star);
						break;
					case 'p':
						PowerUp pala= new PowUpShovel(col*Obstaculo.SIZE,fila*Obstaculo.SIZE,this);
						powerups.getChildren().add(pala.getForma());
						powerUps.add(pala);
						break;
					case 't':
						PowerUp time= new PowUPTime(col*Obstaculo.SIZE,fila*Obstaculo.SIZE,this);
						powerups.getChildren().add(time.getForma());
						powerUps.add(time);
						break;
					case 'l':
						PowerUp vida = new PowUpLife(col*Obstaculo.SIZE,fila*Obstaculo.SIZE,this);
						powerups.getChildren().add(vida.getForma());
						powerUps.add(vida);
						break;
					case 'c':
						PowerUp casco = new PowUpHelm(col*Obstaculo.SIZE,fila*Obstaculo.SIZE,this);
						powerups.getChildren().add(casco.getForma());
						powerUps.add(casco);
						break;	
					}	
					if(cadena.charAt(col+1) == '0' || cadena.charAt(col+1) == '3'){
						posicionesLibres.add(new Point2D(col*Obstaculo.SIZE,fila*Obstaculo.SIZE));
					}
					
				}
				
				
				System.out.println();
				fila++;
				cadena=b.readLine();
			}
			
			b.close();
			}
			
		 catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		DropShadow ds = new DropShadow();
		ds.setRadius(10);
		InnerShadow is = new InnerShadow ();
		is.setRadius(15);
		pisadasAgua.setEffect(is);
		arboles.setEffect(ds);
		tanques.setEffect(ds);
		balasObstaculos.setEffect(ds);		
    }

    protected void reemplazarMetal(double x, double y){
    	Obstaculo sacar = getObstaculo(x,y);
    	if(!limites.contains(sacar)){
			removeObstaculo(sacar);
			addObstaculo(new Metal(x,y),balasObstaculos);
    	}
    }
    
    public void reforzarAguila(){
    	if(aguila  != null){
    		Rectangle formaAguila = (Rectangle)aguila.getForma();
    		double aguilaX = formaAguila.getX();
    		double aguilaY = formaAguila.getY();
    		Obstaculo nuevo = null;
    		Obstaculo sacar = null;
    		double x,y;
    		for(int i = -2; i < 2; i++){
    			x = i*Obstaculo.SIZE+aguilaX;
    			y = -2*Obstaculo.SIZE+aguilaY;
    			reemplazarMetal(x,y);
    			
    			x = i*Obstaculo.SIZE+aguilaX;
    			y = 2*Obstaculo.SIZE+aguilaY;
    			reemplazarMetal(x,y);
    			
    			x = -2*Obstaculo.SIZE+aguilaX;
    			y = i*Obstaculo.SIZE+aguilaY;
    			reemplazarMetal(x,y);
    			
    			x = 2*Obstaculo.SIZE+aguilaX;
    			y = i*Obstaculo.SIZE+aguilaY;
    			reemplazarMetal(x,y);
    		}
    	}
    }

    /**
     * @return
     */
    public ConcurrentLinkedQueue<TanqueEnemigo> getEnemigos() {
        return enemigos;
    }

    /**
     * @return
     */
    public Jugador getJugador() {
        return jugador;
    }

    /**
     * @param j
     */
    public void setJugador(Jugador j) {
       	jugador = j;
       	j.addToGroup(tanques);
       	j.setAnimacionDisparo(aniDisparo);
    }
    
    public ConcurrentLinkedQueue<Obstaculo> getObstaculos(){
    	return obstaculos;
    }
    
    public ConcurrentLinkedQueue<Bullet> getBullets(){
    	return bullets;
    }
    
    public void CongelarEnemigos(){
    	enemigosCongelados=true;
    	Thread t=new Thread(new Runnable(){
    		public void run(){
    			try{
    				Thread.sleep(5000);
    			}catch(InterruptedException e){}  
    			
    			enemigosCongelados=false;
    		}
    	});
    	t.setDaemon(true);
    	t.start();
    }
    
    public void Invulnerable(int segundos){
    	jugador.setInvunerable(true);
    	Thread t=new Thread(new Runnable(){
    		public void run(){
    			try{
    				Thread.sleep(segundos*1000);
    			}catch(InterruptedException e){}  
    			
    			jugador.setInvunerable(false);
    		}
    	});
    	t.setDaemon(true);
    	t.start();    	
    }
    
    protected void addObstaculo(Obstaculo o, Group g){
    	//Platform.runLater(new SyncAdder(o.getForma(),g));
    	g.getChildren().add(o.getForma());
		obstaculos.add(o);
    }
    
    protected void removeObstaculo(Obstaculo o){
    	if(o!=null){
    		Rectangle forma = (Rectangle)o.getForma();
        	posicionesLibres.add(new Point2D(forma.getX(),forma.getY()));
    		Group g = (Group)o.getForma().getParent();
	    	Platform.runLater(new SyncRemover(o.getForma(),g));
			obstaculos.remove(o);
    	}
    }
    
    protected Obstaculo getObstaculo(double x, double y){
    	Rectangle forma = null;
    	for(Obstaculo o: obstaculos){
    		forma = (Rectangle)o.getForma();
    		if(forma.getX() == x && forma.getY() == y)
    			return o;
    	}
    	return null;
    }
}