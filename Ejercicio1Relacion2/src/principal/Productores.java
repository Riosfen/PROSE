package principal;

import java.util.Random;

public class Productores extends Thread {

	private Deposito deposito;
	private int nombre;
	
	public Productores(Deposito deposito, int nombre){
		this.deposito = deposito;
		this.nombre = nombre;
	}
	
	@Override
	public void run() {
		int producido = new Random().nextInt(10) + 1;
		this.setName("Productor Nº: " + nombre);
		this.deposito.producir(this,producido);
	}
	
}
