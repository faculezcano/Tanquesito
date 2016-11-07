package common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
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
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

import assets.*;
import assets.obstaculos.Agua;
import assets.obstaculos.Arbol;
import assets.obstaculos.Block;
import assets.obstaculos.Ladrillo;
import assets.obstaculos.Metal;
import assets.powerUps.PowUPGranade;
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
    
    protected LinkedList<Image> expT;
    
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
        
        g.getChildren().addAll(pisadasAgua, tanques, balasObstaculos, arboles );
        
        enemigos = new ConcurrentLinkedQueue<TanqueEnemigo>();
        bullets = new ConcurrentLinkedQueue<Bullet>();
        obstaculos = new ConcurrentLinkedQueue<Obstaculo>();
        powerUps = new ConcurrentLinkedQueue<PowerUp>();
        
        startColisiones();
        expT = new LinkedList<Image>() ;
        try {
	        File carpeta = new File("src/img/ExplosionSimpleProto1");
	        for (File img:carpeta.listFiles()){
	        	expT.addLast(new Image(new FileInputStream(img)));
			} 
	    }
        catch (FileNotFoundException | NullPointerException e) {
			e.printStackTrace();
		}
        
    }
    
    public TanqueEnemigo crearEnemigo(){
    	TanqueEnemigo enemigo=new TanqueBasico(this,400,150);
    	this.addEnemigo(enemigo);
    	
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
								Platform.runLater(new SyncRemover(b.getForma(),balasObstaculos));
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
									Rectangle r = new Rectangle(tenemigo.getX()-64,tenemigo.getY()-64,128,128);
									Animation ani = new Animation (r,expT,500);
									ani.play();
									Platform.runLater(new SyncAdder(r,tanques));
									eliminarEnemigo(tenemigo);
								}
								if(b.getResistencia()<=0){
									Platform.runLater(new SyncRemover(b.getForma(),balasObstaculos));
									bullets.remove(b);	
									/*jugador.MisBalas().remove(b);*/
								}
								
								
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
					
					for(PowerUp pu: powerUps){
						if(colisiona(pu.getForma(),jugador.getForma())){
							pu.colisionaTanque(jugador);
							eliminarPowerUp(pu);
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
        o.addToGroup(tanques);
    }
    
    public void eliminarPowerUp(PowerUp p){
    	if(!powerUps.isEmpty()){
    		Platform.runLater(new SyncRemover(p.getForma(),balasObstaculos));
    		powerUps.remove(p);
    	}
    }
    
    public void eliminarEnemigo(TanqueEnemigo o){
    	int puntosEnemigo = o.getPuntos();
    	jugador.setPuntos((jugador.getPuntos())+puntosEnemigo);
    	Platform.runLater(new SyncRemover(o,tanques));
    	enemigos.remove(o);
    	System.out.println(jugador.getPuntos());
    }
    
    public void addBullet(final Bullet b){
    	bullets.add(b);
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
						PowerUp granada= new PowUPGranade(col*Obstaculo.SIZE,fila*Obstaculo.SIZE,this);
						balasObstaculos.getChildren().add(granada.getForma());
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
    }
    
    public ConcurrentLinkedQueue<Obstaculo> getObstaculos(){
    	return obstaculos;
    }
    
    public ConcurrentLinkedQueue<Bullet> getBullets(){
    	return bullets;
    }
    

}