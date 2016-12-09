package principal;

public class Principal {

	public static void main(String[] args) {
		
		Deposito deposito = new Deposito();
		
		for (int i = 1; i <= 15; i++) {
			
			Productores pr = new Productores(deposito, i);
			pr.start();
			Consumidores cn = new Consumidores(deposito, i);
			cn.start();
		}
		
	}
	
}
