package assets;

import javafx.scene.Group;

/**
 * 
 */
public abstract class ObjetoEstatico {
	
    /**
     * @return
     */
    public abstract boolean colisionaVehiculo();

    /**
     * @return
     */
    public abstract boolean colisionaBala();

    /**
     * 
     */
    public abstract void colision();

	public void addToGroup(Group g) {
		// TODO Auto-generated method stub
		
	}

}