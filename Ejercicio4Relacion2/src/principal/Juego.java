package principal;

import java.util.Random;

public class Juego {

	private int jugadores;
	private int nuMax;
	private int numAcertar;
	private boolean terminado;
	private Jugador[] vJugador;
	
	public Juego(int jugadores, int nuMax) {

		this.jugadores = jugadores;
		this.nuMax = nuMax;
		numAcertar = new Random().nextInt(nuMax)+1;
		terminado = false;
		vJugador = new Jugador[jugadores];
	}

	public synchronized void empezar() {

		for (int i = 0; i < vJugador.length; i++) {
			vJugador[i] = new Jugador(i+1, this);
			vJugador[i].start();
		}
		
		this.notifyAll();
		
	}
	
	public synchronized void jugar(int numEscogido, int numJugador) {

		if (terminado == false){
			System.out.println("El jugador " + numJugador + " a mostrado el: " + numEscogido);	
		}
		if (numEscogido == numAcertar){
			terminado = true;
			System.out.println("El jugaodr: " + numJugador + " a acertado!!\nFin del juego!");
			System.out.println("Juego terminado, el número era:" + numAcertar);
		}
		
		this.notify();
	}
	
	public int generarAleatorio(){
		return new Random().nextInt(nuMax)+1;
	}
	
	public boolean isTerminado() {
		return terminado;
	}

}
