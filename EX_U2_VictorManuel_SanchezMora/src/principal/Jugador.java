package principal;

public class Jugador extends Thread{
	
	private Tablero juego;
	private int miNumero;
	private int numEscogido;
	private int casilla;
	private int turno;
	
	public Jugador(int nombre, Tablero juego, int casilla){
		this.juego = juego;
		this.casilla = casilla;
		this.miNumero = nombre;
		this.setName("" + nombre);
		turno = 0;
	}
	
	@Override
	public void run() {

		try {
		
			while(juego.isTerminado() == false){
				numEscogido = juego.tirarDados();
				juego.jugar(numEscogido, this);
				turno++;
				this.sleep(1000);
			}
		
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
	
	public int getCasilla() {
		return casilla;
	}

	public void actualizar(int numDados) {
		casilla += numDados;
	}
	public int getTurno() {
		return turno;
	}
	
}
