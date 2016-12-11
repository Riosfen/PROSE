package principal;

public class Maximo {

	private int maximo;
	
	public Maximo(){
		maximo = 0;
	}
	
	public synchronized void actualizarMaximo(int numero){
		
		if (numero > maximo){
			maximo = numero;
		}
		
	}
	
	public int getMaximo() {
		return maximo;
	}
	
}
