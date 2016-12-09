package principal;

import java.util.Random;

public class Consumidores extends Thread {

	private Deposito deposito;
	private int nombre;
	
	public Consumidores(Deposito deposito, int nombre){
		this.deposito = deposito;
		this.nombre = nombre;
	}
	
	@Override
	public void run() {
		int consume = new Random().nextInt(10) + 1;
		this.setName("Consumidor Nº: " + nombre);
		deposito.consumir(this, consume);
	}
	
}
