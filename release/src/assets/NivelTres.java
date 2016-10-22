package assets;

public class NivelTres extends Nivel {
	
	public NivelTres(){
		next = new NivelCuatro();
	}

	@Override
	public int getVelocidad() {
		// TODO Auto-generated method stub
		return 2;
	}

	@Override
	public int getVelocidadDisparo() {
		// TODO Auto-generated method stub
		return 2;
	}

	@Override
	public int getGolpesResiste() {
		// TODO Auto-generated method stub
		return 2;
	}

	@Override
	public int getDisparosSimul() {
		// TODO Auto-generated method stub
		return 2;
	}

	@Override
	public boolean RompeMetal() {
		return false;
	}

}
