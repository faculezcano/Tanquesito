package assets;

public abstract class Nivel {
	
	protected Nivel next;
	protected int num;
	
	public Nivel nextLvl(){
		return next;
	}
	
	public abstract int getVelocidad();
	
	public abstract int getVelocidadDisparo();
	
	public abstract int getGolpesResiste();
	
	public abstract int getDisparosSimul();
	
	public abstract boolean RompeMetal();
	
	public abstract String toString();
	
	public int getNum(){
		return num;
	}

}
