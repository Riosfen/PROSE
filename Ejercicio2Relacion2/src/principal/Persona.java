package principal;

public class Persona {

	private String dni;
	private String nombre;
	
	public Persona(String dni, String nombre){
		
		this.dni = dni;
		this.nombre = nombre;
		
	}
	
	public String getDni() {
		return dni;
	}
	public String getNombre() {
		return nombre;
	}
	
}
