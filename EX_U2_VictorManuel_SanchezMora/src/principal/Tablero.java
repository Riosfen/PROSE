package principal;

import java.util.Random;

public class Tablero {

	private int jugadores;
	private int casillas;
	private boolean terminado;
	private Jugador[] vJugador;
	
	public Tablero(int jugadores) {
		this.casillas = new Random().nextInt(70)+30;
		this.jugadores = jugadores;
		vJugador = new Jugador[jugadores];
		terminado = false;
	}

	public synchronized void empezar() {

		for (int i = 0; i < vJugador.length; i++) {
			vJugador[i] = new Jugador(i+1, this, 0);
			vJugador[i].start();
		}
		
	}
	
	public synchronized void jugar(int numDados, Jugador jugador) {

		if (terminado == false){
			System.out.println("Turno: " + jugador.getTurno() + " Jugador " + jugador.getName() + " esta en la casilla: " + jugador.getCasilla() + " a  sacado: " + numDados + " puntos");
			jugador.actualizar(numDados);
		}
		if (jugador.getCasilla() >= casillas){
			terminado = true;
			System.out.println("El jugaodr: " + jugador.getName() + " a ganado!! su posicion es: " + jugador.getCasilla() + "\nFin del juego!");
		}
		
	}
	
	public int tirarDados(){
		return new Random().nextInt(10)+2;
	}
	
	public boolean isTerminado() {
		return terminado;
	}

}
