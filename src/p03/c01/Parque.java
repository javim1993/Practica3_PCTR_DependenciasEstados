package src.p03.c01;

import java.util.Enumeration;
import java.util.Hashtable;

public class Parque implements IParque{

	private boolean personaEnPuerta = false;
	private int contadorPersonasTotales;
	private int CONTADORMAX=50;
	private Hashtable<String, Integer> contadoresPersonasPuerta;
	/**Cerrojo, objeto de cualquier tipo*/
	private static Object cerrojo =new Object();
	
	// TODO 
	private int contadorPersonasTotales;
	private Hashtable<String, Integer> contadoresPersonasPuerta;
	
	
	public Parque() {
		contadorPersonasTotales = 0;
		contadoresPersonasPuerta = new Hashtable<String, Integer>();
	}


	@Override
	public synchronized void entrarAlParque(String puerta) { // TODO

		// Si no hay entradas por esa puerta, inicializamos
		if (contadoresPersonasPuerta.get(puerta) == null) {
			contadoresPersonasPuerta.put(puerta, 0);
		}

		// Aumentamos el contador total y el individual
		comprobarAntesDeEntrar();
		if (contadorPersonasTotales < CONTADORMAX) {
			personaEnPuerta = true;
			contadorPersonasTotales++;
			contadoresPersonasPuerta.put(puerta, contadoresPersonasPuerta.get(puerta) + 1);

			// Imprimimos el estado del parque
			imprimirInfo(puerta, "Entrada");

		}
		personaEnPuerta = false;
		//notifyAll();
	}

	public void entrarAlParque(String puerta){		// TODO
		
		// Si no hay entradas por esa puerta, inicializamos
		if (contadoresPersonasPuerta.get(puerta) == null){
			contadoresPersonasPuerta.put(puerta, 0);
		}
		
		// TODO
				
		
		// Aumentamos el contador total y el individual
		contadorPersonasTotales++;		
		contadoresPersonasPuerta.put(puerta, contadoresPersonasPuerta.get(puerta)+1);
		
		// Imprimimos el estado del parque
		imprimirInfo(puerta, "Entrada");
		
		// TODO
		
		
		// TODO
		
	}
	
	public synchronized void salirDelParque(String puerta) { // TODO

		// Si no hay entradas por esa puerta, inicializamos
		if (contadoresPersonasPuerta.get(puerta) == null) {
		 contadoresPersonasPuerta.put(puerta, 0);
		 }

		comprobarAntesDeSalir();
		if (contadoresPersonasPuerta.get(puerta) >= 1) {
			personaEnPuerta = true;
			contadorPersonasTotales--;
			contadoresPersonasPuerta.put(puerta, contadoresPersonasPuerta.get(puerta) - 1);

			// Imprimimos el estado del parque
			imprimirInfo(puerta, "Salida");
		}
		personaEnPuerta = false;
		//notifyAll();

	}
	
	
	private void imprimirInfo (String puerta, String movimiento){
		System.out.println(movimiento + " por puerta " + puerta);
		System.out.println("--> Personas en el parque " + contadorPersonasTotales); //+ " tiempo medio de estancia: "  + tmedio);
		
		// Iteramos por todas las puertas e imprimimos sus entradas
		for(String p: contadoresPersonasPuerta.keySet()){
			System.out.println("----> Por puerta " + p + " " + contadoresPersonasPuerta.get(p));
		}
		System.out.println(" ");
	}
	
	private int sumarContadoresPuerta() {
		int sumaContadoresPuerta = 0;
			Enumeration<Integer> iterPuertas = contadoresPersonasPuerta.elements();
			while (iterPuertas.hasMoreElements()) {
				sumaContadoresPuerta += iterPuertas.nextElement();
			}
		return sumaContadoresPuerta;
	}
	
	protected void checkInvariante() {
		assert sumarContadoresPuerta() == contadorPersonasTotales : "INV: La suma de contadores de las puertas debe ser igual al valor del contador del parte";
		// TODO 
		// TODO
		
		
		
	}

	protected void comprobarAntesDeEntrar() {
		synchronized (cerrojo) {
			while (personaEnPuerta) {
				try {
					cerrojo.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			cerrojo.notifyAll();
		}
	}


	protected void comprobarAntesDeSalir(){
		//
		// TODO
		//
	}


}
