package src.p03.c01;

public class SistemaLanzador {
	public static void main(String[] args) {
		/** Interfaz de tipo IParque */
		IParque parque = new Parque(); 
		/** Comienzo de las puertas */
		char letra_puerta = 'A';
		/** Máximo de puertas */
		int MAXPUERTAS = 5;	
		System.out.println("¡Parque abierto!");

		
		/** Se limita el número de puertas a 5 */
		//for (int i = 0; i < Integer.parseInt(args[0]); i++) {
		for (int i = 0; i < MAXPUERTAS; i++) {
			String puerta = ""+((char) (letra_puerta++));
			
			// Creación de hilos de entrada
			ActividadEntradaPuerta entradas = new ActividadEntradaPuerta(puerta, parque);
			ActividadSalidaPuerta salidas = new ActividadSalidaPuerta(puerta, parque);
			new Thread (entradas).start();
			new Thread (salidas).start();		
		}
	}	
}
