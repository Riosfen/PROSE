package principal;

import java.util.Random;

public class Juego {

	private int jugadores;
	private int nuMax;
	private int numAcertar;
	private boolean terminado = false;
	private Jugador[] vJugador;
	
	public Juego(int jugadores, int nuMax) {

		this.jugadores = jugadores;
		this.nuMax = nuMax;
		numAcertar = new Random().nextInt(nuMax)+1;
		vJugador = new Jugador[jugadores];
	}

	public synchronized void empezar() {

		for (int i = 0; i < vJugador.length; i++) {
			vJugador[i] = new Jugador(i+1, this);
			vJugador[i].start();
		}
		
	}
	
	public synchronized void jugar(int numEscogido, int numJugador) throws JugadorException{
		
		if (numEscogido == numAcertar){
			System.out.println("El jugador " + numJugador + " a acertado: " + numEscogido);
			throw new JugadorException("Juego terminado, a ganado el jugador:" + numJugador);
		}else{
			System.out.println("El jugador " + numJugador + " a mostrado el: " + numEscogido);	
		}
		
		
	}
	
	public void finPartida(){
		terminado = true;
	}
	
	public int generarAleatorio(){
		return new Random().nextInt(nuMax)+1;
	}
	
	public boolean isTerminado() {
		return terminado;
	}
	
	public void setTerminado(boolean terminado) {
		this.terminado = terminado;
	}

}
