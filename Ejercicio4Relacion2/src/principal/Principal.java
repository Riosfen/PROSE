package principal;

import java.util.Scanner;

public class Principal {

	public static Scanner teclado = new Scanner(System.in);
	
	public static void main(String[] args) {

		System.out.println("¿Cuántos jugadores jugaran?");
		int jugadores = teclado.nextInt();
		
		System.out.println("Número maximo a buscar:");
		int nuMax = teclado.nextInt();
		
		Juego juego = new Juego(jugadores, nuMax);
		juego.empezar();
		
	}

}
