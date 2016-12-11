package principal;

import java.util.Arrays;
import java.util.Random;

public class Principal {

	public static void main(String[] args) {

		Maximo maximo = new Maximo();
		HiloCalculaValorMaximo[] hilos = new HiloCalculaValorMaximo[10];
		int[] vEnteros = new int[100];
		
		for (int i = 0; i < vEnteros.length; i++) {
			vEnteros[i] = new Random().nextInt(500) + 1;
		}
		
		System.out.println(Arrays.toString(vEnteros));
			
		for (int i = 0; i < 10; i++) {
			hilos[i] = new HiloCalculaValorMaximo(maximo, vEnteros.clone(), (i*10), ((i*10)+10), i+1);
			hilos[i].start();
		}

		try {
		
			for (int i = 0; i < hilos.length; i++) {
			
				hilos[i].join();
			}
		
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("El numero mayor es: " + maximo.getMaximo());
		
	}

}
