package principal;

public class HiloCalculaValorMaximo extends Thread {
	
	private Maximo maximo;
	private int maximoParcial;
	private int[] vEnteros;
	private int posInicio;
	private int posFinal;
	
	public HiloCalculaValorMaximo(Maximo maximo, int[] vEnteros, int posInicio, int posFinal, int i){
		
		this.maximoParcial = 0;
		this.maximo = maximo;
		this.vEnteros = vEnteros;
		this.posFinal = posFinal;
		this.posInicio = posInicio;
		this.setName("" + i);
		
	}
	
	@Override
	public void run() {
		
		int contador = 0;
		
		for (int i = posInicio; i < posFinal; i++) {
			if (vEnteros[i] > maximoParcial){
				maximoParcial = vEnteros[i];
			}
		}
		
		System.out.println("Hilo " + this.getName() + " -- numero maximo parcial: " + maximoParcial);
		
		maximo.actualizarMaximo(maximoParcial);
		
	}
	
}
