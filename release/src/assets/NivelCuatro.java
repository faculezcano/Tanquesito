package assets;

public class NivelCuatro extends Nivel {
	
	
	public NivelCuatro(){
		next = this;
	}

	@Override
	public int getVelocidad() {
		// TODO Auto-generated method stub
		return 2;
	}

	@Override
	public int getVelocidadDisparo() {
		// TODO Auto-generated method stub
		return 3;
	}

	@Override
	public int getGolpesResiste() {
		// TODO Auto-generated method stub
		return 4;
	}

	@Override
	public int getDisparosSimul() {
		// TODO Auto-generated method stub
		return 3;
	}
	

	@Override
	public boolean RompeMetal() {
		return true;
	}
	

}
