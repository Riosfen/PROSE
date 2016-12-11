package principal;

public class Jugador extends Thread{
	
	private Juego juego;
	private int miNumero;
	private int numEscogido;
	
	public Jugador(int nombre, Juego juego){
		this.juego = juego;
		this.miNumero = nombre;
		this.setName("" + nombre);
	}
	
	@Override
	public void run() {

		try {
		
			while(juego.isTerminado() == false){
				numEscogido = juego.generarAleatorio();
				juego.jugar(numEscogido, miNumero);
				this.sleep(1000);
			}
		
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JugadorException e) {
			System.out.println("El jugaodr: " + getName() + " a acertado!!\nFin del juego!");
			juego.setTerminado(true);
		}
		
	}
	
}
