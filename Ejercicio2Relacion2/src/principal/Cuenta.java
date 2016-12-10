package principal;

public class Cuenta {
	
	private int saldo;
	
	public Cuenta(int saldo){
		this.saldo = saldo;
	}

	public synchronized void ingresar(int dinero, String dni) throws CuentaException {

		if (dinero < 0){
			throw new CuentaException("ERROR!!");
		}
		
		saldo += dinero;
		System.out.println("Persona con dni: " + dni + " ha ingresado " + dinero + "euros en la cuenta. Saldo=" + saldo);
		
	}

	public void retirar(int dinero, String dni) throws CuentaException {

		if (saldo - dinero < 0){
			throw new CuentaException("Persona con dni: " + dni + " no tiene saldo suficiente para retirar " + dinero + " euros.");
		}
		
		saldo -= dinero;
		System.out.println("Persona con dni: " + dni + " ha retirado " + dinero + "euros en la cuenta. Saldo=" + saldo);
		
	}

}
