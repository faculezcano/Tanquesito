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
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;


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
    protected Jugador jugador;
    protected Tanque enemigo;
    
    
    protected Group pisadasAgua;
    protected Group tanques;
    protected Group balasObstaculos;
    protected Group arboles;
    protected Group powerups;
    
    GifDecoder gifDecoder = new GifDecoder();
    protected LinkedList<Image> expT;
    protected Image[] aniDisparo;
    protected Image[] aniImpactoBala;
    
    protected boolean enemigosCongelados=false;
    protected boolean jugadorInvulnerable=false;
    
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
        
        startColisiones();
        expT = new LinkedList<Image>() ;
        try {
	        File carpeta = new File("src/img/ExplosionSimpleProto1");
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
    
    public boolean colisiona(ObjetoEstatico oe, ObjetoDinamico od){
    	
    	if( distancia(oe,od)> 96){
    		noentran++;
    		return false;
    	}
    	
    	Rectangle r1 = (Rectangle)oe.getForma();
    	Rectangle r2 = (Rectangle)od.getForma();
    	
    	return colisiona(r1,r2);
    	
    	//entran++;
    	//return colisionaShape(oe.getForma(),od.getForma());
    }
    
    public boolean colisiona(Rectangle r1, Rectangle r2){
    	return ((int)r1.getX() < (int)r2.getX() + (int)r2.getWidth() &&
 			   (int)r1.getX() + (int)r1.getWidth() > (int)r2.getX() &&
 			   (int)r1.getY() < (int)r2.getY() + (int)r2.getHeight() &&
 			   (int)r1.getHeight() + (int)r1.getY() > (int)r2.getY());
    }
    
    public boolean colisiona(ObjetoDinamico o1, ObjetoDinamico o2){
    	if(ObjetoDinamico.distancia(o1, o2) > 96){
    		noentran++;
    		return false;
    	}
    	Rectangle r1 = (Rectangle)o1.getForma();
    	Rectangle r2 = (Rectangle)o2.getForma();
    	
    	return colisiona(r1,r2);
    	//entran++;
    	//return colisionaShape(o1.getForma(),o2.getForma());
    }
    
    int entran = 0;
	int noentran = 0;
    
    private void startColisiones(){
    	Thread colisiones = new Thread(new Runnable() {

			@Override
			public void run() {
				
				final int fps = 30;
				long time;
				double deltaT = 1.0/fps;
				
				
				
				LinkedList<TanqueEnemigo> colisionesEnemigo= new LinkedList<TanqueEnemigo>();
				
				while(true) { 
					
					time = System.nanoTime();
					
					for(Bullet b:bullets){
						
						colisionesTanquesBullet(b);
						
						colisionesObstBullet(b);
						
						
//						if(!jugadorInvulnerable){
//							colisionesJugadorBullet(b);
//						}
						
					}
					
					for(PowerUp pu: powerUps){
						//if(colisiona(pu.getForma(),jugador.getForma())){
						if(colisiona(pu,jugador)){
							pu.colisionaTanque(jugador);
							eliminarPowerUp(pu);
						}
					}
					
					for(Obstaculo o : obstaculos){
						//if(colisiona(o.getForma(),jugador.getForma()))
						if(colisiona(o,jugador))
							o.colisionaTanque(jugador);
						
						for(TanqueEnemigo ene: enemigos){
							
							//if(colisiona(o.getForma(),ene.getLineaTiro())){
							/*if(distancia(o,ene) < 256 && colisionaShape(o.getForma(),ene.getLineaTiro())){
								ene.setTiroLimpio(false);
							}*/
							
							//if(colisiona(o.getForma(),ene.getForma())){
							if(colisiona(o,ene)){
								ene.setTiroLimpio(false);
								o.colisionaTanque(ene);
								ene.setTiroLimpio(false);
							}
							
							//if(colisiona(jugador.getForma(),ene.getForma())){
							if(colisiona(jugador,ene)){
								ene.colisiona();
								jugador.colisiona();
							}
							//ene.apuntar(jugador.getPosicion());
						}
					}
					
					for(TanqueEnemigo ene1: enemigos){
						for(TanqueEnemigo ene2: enemigos){
							if(ene1 != ene2 && colisiona(ene1,ene2)){
								ene1.colisiona();
								ene2.colisiona();
							}
						}
					}
					
					for(final Bullet b: bullets){
						Platform.runLater(new Runnable() {
							@Override
							public void run() {
								//Point2D vel = b.getVelocidad();
								//b.setPosicion(b.getPosicion().add(vel.multiply(30.0/fps)));
								b.mover();
							}
						});
					}
					
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							//Point2D vel = jugador.getVelocidad();
							//jugador.setPosicion(jugador.getPosicion().add(vel.multiply(30.0/fps)));
							jugador.mover();
						}
					});
					
					if(!enemigosCongelados){
						for(final TanqueEnemigo en: enemigos){
							Platform.runLater(new Runnable() {
								@Override
								public void run() {
									//Point2D velEn=en.getVelocidad();
									//en.setPosicion(en.getPosicion().add(velEn.multiply(30.0/fps)));
									en.mover();
								}
							});
						}
					}
					
					
					
					/*if(noentran+entran > 0){
						double est = ((double)entran)/(entran+noentran);
						System.out.println(est*100+"% Total: " + (entran+noentran));
					}*/

					entran = 0;
					noentran=0;
					
					try {
						while((System.nanoTime()- time) <= deltaT*1000000000){
							Thread.sleep(1);
						}
					} catch (InterruptedException e) {}
					
				}
    		
			}});
    	colisiones.setName("Colisiones");
    	colisiones.setDaemon(true);
    	colisiones.start();
    }
    
    private void colisionesObstBullet(Bullet b){
    	LinkedList<Obstaculo> colisionesBala = new LinkedList<Obstaculo>();
    	for(Obstaculo o: obstaculos){
			//if (colisiona(b.getForma(), o.getForma())){
    		if (colisiona(o,b)){
					colisionesBala.add(o);
			}		
		
		}
		
		while (!colisionesBala.isEmpty()){
			Obstaculo ob = colisionesBala.remove();
			ob.colisionaBala(b);
			
			if (ob.GetVida() == 0) {
				Platform.runLater(new SyncRemover(ob.getForma(),balasObstaculos));
				obstaculos.remove(ob);
			}
			
			if (b.getResistencia() == 0) {
				Platform.runLater(new SyncRemover(b.getForma(),balasObstaculos));
				bullets.remove(b);
			}
		}
    }
    
//    private void colisionesJugadorBullet(Bullet b){
//    	if(!(jugador.MisBalas().contains(b))){
//    		if(colisiona(jugador,b)){
//    			//jugador.afectar();
//    			
//    			//if(!jugadorInvulnerable){
//	    			int nuevaResistencia=jugador.getResistencia()-b.getResistencia();
//	        		jugador.setResistencia(nuevaResistencia);
//		    		if(jugador.getResistencia()<=0){
//		    			this.matarJugador();
//		    		}
//    			//}
//    			
//    			b.colisiona();
//    			if(b.getResistencia()<=0){
//					Platform.runLater(new SyncRemover(b.getForma(),balasObstaculos));
//					bullets.remove(b);	
//				}
//    		}
//    	}
//    }
    
    public void matarJugador(){
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
		
		Platform.runLater(new SyncAdder(r,tanques));
		if(jugador.getVida()>0){
			jugador.setX(jugador.getXinicial());
			jugador.setY(jugador.getYinicial());
			jugador.setResistencia(jugador.getResistenciaInicial());
		}else{
			jugador=null;
		}
    }
    
    private void colisionesTanquesBullet(Bullet b){
    	if(jugador.MisBalas().contains(b)){
			for(TanqueEnemigo tenemigo: enemigos){
				//if(colisiona(tenemigo.getForma(),b.getForma())){
				if(colisiona(tenemigo,b)){
					tenemigo.afectar();
					b.colisiona();
					if(tenemigo.getResistencia()<=0){
						eliminarEnemigo(tenemigo);		
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
			if(!jugadorInvulnerable){
				jugador.afectar();
				if(jugador.getResistencia()<=0){
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
    	
    	
    	Rectangle r = new Rectangle(o.getX()-64,o.getY()-64,128,128);
		Animation ani = new Animation (r,expT,500);
		ani.play();
		
		Platform.runLater(new SyncAdder(r,tanques));
		
		
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
    	Platform.runLater(new Runnable(){

			@Override
			public void run() {
				balasObstaculos.getChildren().add(b.getForma());
			}
    		
    	});
    	
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
					case '5':
						PowerUp granada= new PowUpHelm(col*Obstaculo.SIZE,fila*Obstaculo.SIZE,this);
						powerups.getChildren().add(granada.getForma());
						powerUps.add(granada);
						break;
					}		
				}
				System.out.println();
				fila++;
				cadena=b.readLine();
			}
			b.close();
			
		} catch (FileNotFoundException e) {
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
    
    public void eliminarObstaculo(){
    	
    	if(!obstaculos.isEmpty()){
	    	Obstaculo obst = obstaculos.element();
	    	balasObstaculos.getChildren().remove(obst.getForma());
	    	obstaculos.remove(obst);
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
    
    public void Invulnerable(){
    	jugadorInvulnerable=true;
    	Thread t=new Thread(new Runnable(){
    		public void run(){
    			try{
    				Thread.sleep(10000);
    			}catch(InterruptedException e){}  
    			
    			jugadorInvulnerable=false;
    		}
    	});
    	t.setDaemon(true);
    	t.start();    	
    }
    
    
    
    
    

}