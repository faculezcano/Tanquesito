package assets;

public class NivelUno extends Nivel {
	
	public NivelUno(){
		next = new NivelDos();
	}

	@Override
	public int getVelocidad() {
		// TODO Auto-generated method stub
		return 2;
	}

	@Override
	public int getVelocidadDisparo() {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public int getGolpesResiste() {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public int getDisparosSimul() {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public boolean RompeMetal() {
		return false;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "1";
	}

}
