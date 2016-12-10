package principal;

import java.util.Random;

public class Cliente extends Persona implements Runnable {

	private Cuenta cuenta;
	private boolean terminado = false;
	
	public Cliente(Cuenta cuenta, String dni, String nombre){
		super(dni, nombre);
		this.cuenta = cuenta;
	}
	
	@Override
	public void run() {

		for (int i = 0; i < 3 && terminado == false; i++) {
			try {
				cuenta.ingresar(new Random().nextInt(500)+1, this.getDni());
				Thread.sleep(100);
				cuenta.retirar(new Random().nextInt(500)+1, this.getDni());
				Thread.sleep(100);
			} catch (InterruptedException e) {
				System.out.println(e.getMessage());
			} catch (CuentaException ex) {
				System.out.println(ex.getMessage());
				terminado = true;
			}
		}
		
	}

}
