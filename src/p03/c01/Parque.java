package src.p03.c01;

import java.util.Enumeration;
import java.util.Hashtable;

/**
 * Clase Parque
 *  
 * @author <a href="jmf1017@alu.ubu.es">Javier Muñoz</a>
 * @author <a href="jgh1006@alu.ubu.es">Josue Gabriel Granados</a>
 * @since 1.0
 * @version 1.0
 * 
 *          Clase que implementa IParque
 */
public class Parque implements IParque{

	/**Entero que cuenta el número de personas totales en el parque */
	private int contadorPersonasTotales;
	/**Entero que da el valor máximo de personas en el parque */
	private int CONTADORMAX=50;
	/**Se usa paraguardar un número de personas en una puerta */
	private Hashtable<String, Integer> contadoresPersonasPuerta;
	
	/**
 	* Método constructor de la clase parque
  	*/
	public Parque() {
		contadorPersonasTotales = 0;
		contadoresPersonasPuerta = new Hashtable<String, Integer>();
	}


	/**
	 * Metodo entrarAlParque implementado de la interfaz IParque
	 * @param puerta puerta de entrada
	 */
	@Override
	public synchronized void entrarAlParque(String puerta) {

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
	
	/**
 	* Método que imprime la información de una puerta
  	* @param puerta puerta de entrada o salida
   	* @param movimiento si es entrada o salida
  	*/
	private void imprimirInfo (String puerta, String movimiento){
		System.out.println(movimiento + " por puerta " + puerta);
		System.out.println("--> Personas en el parque " + contadorPersonasTotales); //+ " tiempo medio de estancia: "  + tmedio);
		
		// Iteramos por todas las puertas e imprimimos sus entradas
		for(String p: contadoresPersonasPuerta.keySet()){
			System.out.println("----> Por puerta " + p + " " + contadoresPersonasPuerta.get(p));
		}
		System.out.println(" ");
	}

	/**
 	* Método que gestiona los contadores de una puerta
  	* @return int devuelve los contadores de la puerta que este usando
  	*/
	private int sumarContadoresPuerta() {
		int sumaContadoresPuerta = 0;
			Enumeration<Integer> iterPuertas = contadoresPersonasPuerta.elements();
			while (iterPuertas.hasMoreElements()) {
				sumaContadoresPuerta += iterPuertas.nextElement();
			}
		return sumaContadoresPuerta;
	}

	/**
 	* Metodo que chequea que no se sobrepase de los valores límite, 
  	* entre 0 y 50 personas en el parque
  	*/
	protected void checkInvariante() {
		//aforo máximo
		assert sumarContadoresPuerta() == contadorPersonasTotales
				: "INV: La suma de contadores de las puertas debe ser igual al valor del contador del parte";
		assert contadorPersonasTotales >= 0 : "INV: El número total de personas no puede ser negativo";

	}
	/**
	 * Método comprobarAntesDeEntrar 
	 * Comprueban que antes de entrar no se llegue al valor máximo
	 * Si llega se duermen los threads de entrada
	 */
	protected void comprobarAntesDeEntrar() {
		while (contadorPersonasTotales == CONTADORMAX)
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
	}

	/**
	 * Método comprobarAntesDeSalir 
	 * Comprueban que antes de entrar no se llegue al valor 0
	 * Si llega se duermen los threads de salida
	 */
	protected void comprobarAntesDeSalir() {
		while (contadorPersonasTotales == 0) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
