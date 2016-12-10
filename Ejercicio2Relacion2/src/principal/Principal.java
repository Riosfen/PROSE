package principal;

public class Principal {

	public static void main(String[] args) {

		Cuenta cuenta = new Cuenta(0);
		
		new Thread (new Cliente(cuenta, "AAA", "PEPE")).start();
		new Thread (new Cliente(cuenta, "BBB", "ANTONIO")).start();
		new Thread (new Cliente(cuenta, "CCC", "MANUELA")).start();

	}

}
