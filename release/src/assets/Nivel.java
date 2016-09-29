package assets;

public abstract class Nivel {
	
	protected Nivel next;
	
	public Nivel nextLvl(){
		return next;
	}
	
	public abstract int getVelocidad();
	
	public abstract int getVelocidadDisparo();
	
	public abstract int getGolpesResiste();
	
	public abstract int getDisparosSimul();

}
