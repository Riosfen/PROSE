package principal;

public class Deposito {

	private int capacidad;
	
	public synchronized void producir(Productores productores, int producido) {

		while (capacidad != 0){
			try {
				this.wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		capacidad = producido;		
		System.out.println("Soy el " + productores.getName() + " y he producido: " + capacidad);
		notify();
		
	}

	public synchronized void consumir(Consumidores consumidores, int consume) {
	
		while (capacidad == 0){
			try {
				this.wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		System.out.println("Soy el " + consumidores.getName() + " y he consumido: " + capacidad);
		capacidad = 0;
		notify();
		
	}

}
