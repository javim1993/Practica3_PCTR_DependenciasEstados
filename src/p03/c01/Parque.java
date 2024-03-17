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
	// Hashtable para almacenar el número de personas que ingresan por cada puerta
	private Hashtable<String, Integer> contadoresPersonasPuerta;
	
	
	public Parque() {
		contadorPersonasTotales = 0;
		contadoresPersonasPuerta = new Hashtable<String, Integer>();
	}


	/**
	 * Metodo entrarAlParque implementado de la interfaz IParque
	 * @param puerta puerta de entrada
	 */
	@Override
	public synchronized void entrarAlParque(String puerta) { // TODO

		// Si no hay entradas por esa puerta, inicializamos
		if (contadoresPersonasPuerta.get(puerta) == null) {
			contadoresPersonasPuerta.put(puerta, 0);
		}
		if (contadoresPersonasPuerta.get(puerta) < 20 || contadorPersonasTotales <= CONTADORMAX) {
			// Aumentamos el contador total y el individual
			comprobarAntesDeEntrar();
			contadorPersonasTotales++;
			contadoresPersonasPuerta.put(puerta, contadoresPersonasPuerta.get(puerta) + 1);
		}
		// Imprimimos el estado del parque
		imprimirInfo(puerta, "Entrada");
		checkInvariante();
	}

	/**
	 * Metodo salirDelParque implementado de la interfaz IParque
	 * @param puerta puerta de salida
	 */
	@Override
	public synchronized void salirDelParque(String puerta) { 

		if (contadoresPersonasPuerta.get(puerta) != null && contadoresPersonasPuerta.get(puerta) > 0) {
			comprobarAntesDeSalir();
			contadorPersonasTotales--;
			contadoresPersonasPuerta.put(puerta, contadoresPersonasPuerta.get(puerta) - 1);

			// Imprimimos el estado del parque
			imprimirInfo(puerta, "Salida");
			checkInvariante();
		}
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
		//aforo máximo
		assert sumarContadoresPuerta() == contadorPersonasTotales
				: "INV: La suma de contadores de las puertas debe ser igual al valor del contador del parte";
		//menor o igual a 20 personas por puerta
		for (String p : contadoresPersonasPuerta.keySet()) {
			assert contadoresPersonasPuerta.get(p).equals(contadoresPersonasPuerta): "Las personas no superan 20 en la puerta "+p;
		}
		
		assert contadorPersonasTotales >= 0
				: "INV: El número total de personas no puede ser negativo";

		//assert personas mayor a 0
		for (String p : contadoresPersonasPuerta.keySet()) {
			assert contadoresPersonasPuerta.get(p)>=0: "Las personas no son negativas en puerta "+p;
		}

	}
	/**
	 * Método comprobarAntesDeEntrar 
	 * Comprueban que antes de entrar no se llegue al valor máximo
	 * Si llega se duermen los threads de entrada
	 */
	protected void comprobarAntesDeEntrar() {
		while (contadorPersonasTotales == CONTADORMAX)
			try {
				System.out.println("Sleep IN");
				wait();
				System.out.println("No sleep IN");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		System.out.println("IN despertar puertas");
	}

	/**
	 * Método comprobarAntesDeSalir 
	 * Comprueban que antes de entrar no se llegue al valor 0
	 * Si llega se duermen los threads de salida
	 */
	protected void comprobarAntesDeSalir() {
		while (contadorPersonasTotales == 0) {
			try {
				System.out.println("Sleep OUT");
				wait();
				System.out.println("No sleep OUT");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("OUT despertar puertas");
	}
}
