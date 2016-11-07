package common;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentLinkedQueue;

import javafx.application.Platform;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

import assets.*;
import assets.obstaculos.Agua;
import assets.obstaculos.Arbol;
import assets.obstaculos.Ladrillo;
import assets.obstaculos.Metal;
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
    protected Group g;
    protected Group gr;
    protected Tanque enemigo;
    protected Group ge;
    
	/**
     * @param cantX 
     * @param cantY 
     * @param g
     */
    public Mapa(int cantX, int cantY, Group g) {
        this.g = g;
        enemigos = new ConcurrentLinkedQueue<TanqueEnemigo>();
        bullets = new ConcurrentLinkedQueue<Bullet>();
        obstaculos = new ConcurrentLinkedQueue<Obstaculo>();
        
        startColisiones();
    }
    
    public TanqueEnemigo crearEnemigo(){
    	TanqueEnemigo enemigo=new TanqueBasico(this,400,150);
    	this.addEnemigo(enemigo);
    	enemigo.addToGroup(g);
    	//g.getChildren().add(enemigo.getForma());
    	
    	return enemigo;
    	
    }
    
    protected boolean colisiona(Shape s1, Shape s2){
    	Shape interseccion = Shape.intersect(s1, s2);
    	return !interseccion.getBoundsInLocal().isEmpty();
    	//return s1.getBoundsInParent().intersects(s2.getBoundsInParent());
    }
    
    private void startColisiones(){
    	Thread colisiones = new Thread(new Runnable() {

			@Override
			public void run() {
				
				final int fps = 30;
				long time;
				double deltaT = 1.0/fps;
				
				
				LinkedList<Obstaculo> colisionesBala = new LinkedList<Obstaculo>();
				LinkedList<TanqueEnemigo> colisionesEnemigo= new LinkedList<TanqueEnemigo>();
				
				while(true) { 
					
					time = System.nanoTime();
					
					for(final Bullet b:bullets){
						
						for(Obstaculo o: obstaculos){
							if (colisiona(b.getForma(), o.getForma())){
									colisionesBala.add(o);
									/*if((jugador.MisBalas().contains(b))){
										jugador.MisBalas().remove(b);
									}*/
							}		
						
						}
						
						if(!jugador.MisBalas().contains(b) && colisiona(b.getForma(), jugador.getForma())){
							//TODO: matar jugador
							jugador.afectar();
							b.colisiona();
							if(b.getResistencia() <= 0){
								Platform.runLater(new SyncRemover(b.getForma(),g));
								bullets.remove(b);	
							}
						}
						
						for(TanqueEnemigo tenemigo: enemigos){
							if((jugador.MisBalas().contains(b))&&(colisiona(b.getForma(),tenemigo.getForma()))){
								int nuevaRT=tenemigo.getResistencia()-b.getResistencia();
								int nuevaRB=b.getResistencia()-tenemigo.getResistencia();
								tenemigo.setResistencia(nuevaRT);
								//b.setResistencia(nuevaRB);
								b.colisiona();
								if(tenemigo.getResistencia()<=0){
									eliminarEnemigo(tenemigo);
								}
								if(b.getResistencia()<=0){
									Platform.runLater(new SyncRemover(b.getForma(),g));
									bullets.remove(b);	
									/*jugador.MisBalas().remove(b);*/
								}
								
								
							}
							
						}
						
						
						
						while (!colisionesBala.isEmpty()){
							Obstaculo ob = colisionesBala.remove();
							ob.colisionaBala(b);
							
							if (ob.GetVida() == 0) {
								Platform.runLater(new SyncRemover(ob.getForma(),gr));
								obstaculos.remove(ob);
							}
							
							if (b.getResistencia() == 0) {
								Platform.runLater(new SyncRemover(b.getForma(),g));
								bullets.remove(b);
							}
						}
					
					}	
					
					for(Obstaculo o : obstaculos){
						if(colisiona(o.getForma(),jugador.getForma()))
							o.colisionaTanque(jugador);
						
						for(TanqueEnemigo ene: enemigos){
							
							if(colisiona(o.getForma(),ene.getLineaTiro())){
								ene.setTiroLimpio(false);
							}
							
							if(colisiona(o.getForma(),ene.getForma())){
								ene.setTiroLimpio(false);
								o.colisionaTanque(ene);
								ene.setTiroLimpio(false);
							}
							
							if(colisiona(jugador.getForma(),ene.getForma())){
								ene.colisiona();
								jugador.colisiona();
							}
							//ene.apuntar(jugador.getPosicion());
						}
					}
					
					for(final Bullet b: bullets){
						Platform.runLater(new Runnable() {
							@Override
							public void run() {
								Point2D vel = b.getVelocidad();
								b.setPosicion(b.getPosicion().add(vel.multiply(30.0/fps)));
							}
						});
					}
					
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							Point2D vel = jugador.getVelocidad();
							jugador.setPosicion(jugador.getPosicion().add(vel.multiply(30.0/fps)));
						}
					});
					
					for(final TanqueEnemigo en: enemigos){
						Platform.runLater(new Runnable() {
							@Override
							public void run() {
								Point2D velEn=en.getVelocidad();
								en.setPosicion(en.getPosicion().add(velEn.multiply(30.0/fps)));
							}
						});
					}
					
					/*Platform.runLater(new Runnable() {

						@Override
						public void run() {
							
							for(Bullet b: bullets){
								Point2D vel = b.getVelocidad();
								b.setPosicion(b.getPosicion().add(vel.multiply(30.0/fps)));
							}
							
							Point2D vel = jugador.getVelocidad();
							jugador.setPosicion(jugador.getPosicion().add(vel.multiply(30.0/fps)));
							
							for(TanqueEnemigo en: enemigos){
								Point2D velEn=en.getVelocidad();
								en.setPosicion(en.getPosicion().add(velEn.multiply(30.0/fps)));
							}
							
						}
						
					});*/
					
					try {
						while((System.nanoTime()- time) <= deltaT*1000000000){
							Thread.sleep(1);
						}
					} catch (InterruptedException e) {}
					
				}
    		
			}});
    	colisiones.setDaemon(true);
    	colisiones.start();
    }

    /**
     * @param o
     */
    public void addEnemigo(TanqueEnemigo o) {
        enemigos.add(o);
    }
    
    public void eliminarEnemigo(TanqueEnemigo o){
    	int puntosEnemigo = o.getPuntos();
    	jugador.setPuntos((jugador.getPuntos())+puntosEnemigo);
    	Platform.runLater(new SyncRemover(o,g));
    	enemigos.remove(o);
    	System.out.println(jugador.getPuntos());
    }
    
    public void addBullet(final Bullet b){
    	bullets.add(b);
    	Platform.runLater(new Runnable(){

			@Override
			public void run() {
				g.getChildren().add(b.getForma());
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
    	gr = new Group();
		
		try {
			
			//FileReader f;
			//f = new FileReader(archivo);
			
			InputStreamReader f = new InputStreamReader(getClass().getClassLoader().getResourceAsStream(archivo));
			
			BufferedReader b = new BufferedReader(f);
			
			int fila = 0;
			String cadena = b.readLine();
			//enemigo = enemigos.poll();
			
			while(cadena != null){
				for(int col=0;col<cadena.length();col++){
					System.out.print(cadena.charAt(col));
					Obstaculo nuevo = null;
					switch (cadena.charAt(col)){
					case '1':
						nuevo = new Ladrillo(col*Obstaculo.SIZE,fila*Obstaculo.SIZE);
						break;
					case '2':
						nuevo = new Metal(col*Obstaculo.SIZE,fila*Obstaculo.SIZE);
						break;
					case '3':
						nuevo = new Arbol(col*Obstaculo.SIZE,fila*Obstaculo.SIZE);
						break;
					case '4':
						nuevo = new Agua(col*Obstaculo.SIZE,fila*Obstaculo.SIZE);
						break;
					}			
					if(nuevo != null){
						gr.getChildren().add(nuevo.getForma());
						obstaculos.add(nuevo);
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
		gr.setEffect(ds);
		g.getChildren().add(gr);
    }
    
    public void eliminarObstaculo(){
    	
    	if(!obstaculos.isEmpty()){
	    	Obstaculo obst= obstaculos.element();
	    	gr.getChildren().remove(obst.getForma());
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
    }
    
    public ConcurrentLinkedQueue<Obstaculo> getObstaculos(){
    	return obstaculos;
    }
    
    public ConcurrentLinkedQueue<Bullet> getBullets(){
    	return bullets;
    }
    

}