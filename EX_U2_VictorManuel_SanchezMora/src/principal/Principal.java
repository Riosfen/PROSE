package principal;

import java.util.Scanner;

public class Principal {
	
	public static Scanner teclado = new Scanner(System.in);
	
	public static void main(String[] args) {

		System.out.println("¿Cuántos jugadores jugaran?");
		int jugadores = teclado.nextInt();
		
		Tablero juego = new Tablero(jugadores);
		juego.empezar();

	}

}
