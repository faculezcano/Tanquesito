package common;

import java.util.*;

import javafx.scene.Group;

import assets.*;
import assets.tanques.*;

/**
 * 
 */
public class Mapa {
	
	protected List<TanqueEnemigo> enemigos;
    protected Group group;
    protected List<Bullet> bullets;
    protected Jugador jugador;
    protected List<PowerUp> powerUps;
    protected List<Obstaculo> obstaculos;

	/**
     * @param cantX 
     * @param cantY 
     * @param g
     */
    public Mapa(int cantX, int cantY, Group g) {
        // TODO implement here
    }

    /**
     * @param o
     */
    public void addEnemigo(TanqueEnemigo o) {
        // TODO implement here
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
    public void cargarMapa(String file) {
        // TODO implement here
    }

    /**
     * @return
     */
    public List<TanqueEnemigo> getEnemigos() {
        // TODO implement here
        return null;
    }

    /**
     * @return
     */
    public Jugador getJugador() {
        // TODO implement here
        return null;
    }

    /**
     * @param j
     */
    public void setJugador(Jugador j) {
        // TODO implement here
    }

}