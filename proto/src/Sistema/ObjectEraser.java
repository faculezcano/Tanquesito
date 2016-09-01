package Sistema;

import java.util.LinkedList;

public class ObjectEraser implements Runnable {

	protected Mapa map;
	protected LinkedList<Object> objs;
	
	public ObjectEraser(LinkedList<Object> objs, Mapa map){
		this.objs = objs;
		this.map = map;
	}
	
	@Override
	public void run() {
		for(Object obj : objs){
			try{
				map.remove((ObjetoDinamico)obj);
			}catch(ClassCastException e){
				map.remove((ObjetoEstatico)obj);
			}
		}
	}

}
