package common;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentLinkedQueue;

import javafx.application.Platform;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.effect.DropShadow;
import javafx.scene.shape.Shape;

import assets.*;
import assets.obstaculos.Ladrillo;
import assets.tanques.*;

/**
 * 
 */
public class Mapa {
	
	protected ConcurrentLinkedQueue<TanqueEnemigo> enemigos;
    protected ConcurrentLinkedQueue<Bullet> bullets;
    protected ConcurrentLinkedQueue<PowerUp> powerUps;
    protected ConcurrentLinkedQueue<Obstaculo> obstaculos;
    protected Jugador jugador;
    protected Group g;
    protected Tanque enemigo;
    
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
    
    private boolean colisiona(ObjetoDinamico od, ObjetoEstatico oe){
    	//Shape interseccion = Shape.intersect(od.getForma(), oe.getForma());
		//return !interseccion.getBoundsInLocal().isEmpty();
    	return false;
    }
    
    private void startColisiones(){
    	Thread colisiones = new Thread(new Runnable(){

			@Override
			public void run() {
				
				int fps = 30;
				long time;
				double deltaT = 1.0/fps;
				
				while(true){
					
					time = System.nanoTime();
					
					for(Bullet b:bullets){
						LinkedList<Obstaculo> colisiones = new LinkedList<Obstaculo>();
						for(Obstaculo o: obstaculos){
							colisiones.add(o);
						}
						if(!colisiones.isEmpty())
							b.colision();
						for(Obstaculo o: colisiones){
							//o.colision();
					}
					
					Platform.runLater(new Runnable(){

						@Override
						public void run() {
							
							for(Bullet b: bullets){
								Point2D vel = b.getVelocidad();
								b.setPosicion(b.getPosicion().add(vel));
							}
							
							Point2D vel = jugador.getVelocidad();
							jugador.setPosicion(jugador.getPosicion().add(vel));
							
							for(TanqueEnemigo en: enemigos){
								Point2D velEn=en.getVelocidad();
								en.setPosicion(en.getPosicion().add(velEn));
							}
							
						}
						
					});
					
					try {
						while((System.nanoTime()-time)<=deltaT*1000000000){
							Thread.sleep(10);
						}
					} catch (InterruptedException e) {}
					
				}
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
    	enemigos.remove(o);
    }
    
    public void addBullet(Bullet b){
    	bullets.add(b);
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
    	Group gr = new Group();
		
		try {
			
			FileReader f;
			f = new FileReader(archivo);
			
			BufferedReader b = new BufferedReader(f);
			
			int fila = 0;
			String cadena = b.readLine();
			enemigo = enemigos.poll();
			
			while(cadena != null){
				for(int col=0;col<cadena.length();col++){
					System.out.print(cadena.charAt(col));
					if (cadena.charAt(col) == '1') {
						Ladrillo l = new Ladrillo();
						gr.getChildren().add(l.getForma());
						obstaculos.add(l);
						l.setPosicion(new Point2D(0+col*32,0+fila*32));
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

}